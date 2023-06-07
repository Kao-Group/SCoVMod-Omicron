package scovmod.run.abc

import org.apache.commons.math3.distribution.{BetaDistribution, NormalDistribution}
import org.apache.commons.math3.random.{MersenneTwister, RandomGenerator, SynchronizedRandomGenerator}
import org.slf4j.LoggerFactory
import sampler.abc.{Model, Prior}
import sampler.distribution.Distribution
import sampler.maths.Random
import scovmod.ScovModRunner
import scovmod.model.input.config.{ConfigParameters, CovidVariantParameters, FittingParameters, InputData, Parameters}
import scovmod.model.output.modules.councilarea.{CouncilAreaSummary, CouncilAreaSummaryResult}
//import scovmod.model.output.modules.healthboard.{HealthBoardSummary, HealthBoardSummaryResult}

class SCoVModModel(observed: Results, modelConfigJsonStr: String) extends Model[FittingParameters] {
  val log = LoggerFactory.getLogger(getClass.getName)

  trait PertKernel{
    def sample(): Double
    def density(at: Double): Double
  }
  case class UniformRange(lower: Double, upper: Double) {
    assume(lower < upper)
    val width = upper - lower

    def sample(implicit r: Random) = r.nextDouble(lower, upper)

    def densityOf(d: Double) = if (d > upper || d < lower) 0.0 else 1.0 / width

    val kernel = new PertKernel{
      private val normal = {
        val syncRand: RandomGenerator = new SynchronizedRandomGenerator(new MersenneTwister())
        new NormalDistribution(syncRand, 0, width / 20, NormalDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY)
      }
      def sample = normal.sample

      def density(at: Double) = normal.density(at)
    }
  }

  case class BetaDistributionRange(a: Double, b: Double) {

    val beta = {
      val syncRand: RandomGenerator = new SynchronizedRandomGenerator(new MersenneTwister())
      new BetaDistribution(syncRand, a, b)
    }

    def sample(implicit r: Random) = beta.sample

    def densityOf(d: Double) = if (d >= 1 || d <= 0) 0.0 else beta.density(d)

    val kernel = new PertKernel{
      private val normal = {
        val syncRand: RandomGenerator = new SynchronizedRandomGenerator(new MersenneTwister())
        new NormalDistribution(syncRand, 0, 1 / 20.0, NormalDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY)
      }
      def sample = normal.sample

      def density(at: Double) = normal.density(at)
    }
  }
  val sToEMIMultRange = UniformRange(0, 2.6) //Note this param is now a factor of the sToESI parameter - so prior has to be 0 to 1
  val sToESIDayRange = UniformRange(0, 6)
  val sToESINghtRange = UniformRange(0, 6)
  val eToMIRange = UniformRange(0.07, 0.6)
  val mIToRRange = UniformRange(0.035, 1.1)
  val mIToSIRange = UniformRange(0.035, 0.5)
  val numSeedsRange = UniformRange(10.0, 20000.0)
  val numRecSeedsRange = UniformRange(2000000.0, 5000000.0) //NB Check this
  //val firstBetaMultRange = UniformRange(0.1, 1.5)
 // val secondBetaMultRange = UniformRange(0.01, 1.5)
  val transModRange = UniformRange(0, 0.12)

  val prior = new Prior[FittingParameters] {
    def density(p: FittingParameters) = {
      sToEMIMultRange.densityOf(p.getsToE_MI_Factor()) *
        sToESIDayRange.densityOf(p.getsToE_SI_day()) *
        sToESINghtRange.densityOf(p.getsToE_SI_night()) *
        eToMIRange.densityOf(p.geteToMI()) *
        mIToRRange.densityOf(p.getMiToR) *
        mIToSIRange.densityOf(p.getMiToSI) *
        numSeedsRange.densityOf(p.getNumSeeds) *
        numRecSeedsRange.densityOf(p.getNumRecoveredSeeds) *
      //  firstBetaMultRange.densityOf(p.getFirstBetaMultiplier) *
      //  secondBetaMultRange.densityOf(p.getSecondBetaMultiplier) *
        transModRange.densityOf(p.getTransModifier)
    }

    val distribution: Distribution[FittingParameters] = Distribution.from(random =>
      new FittingParameters(
        sToEMIMultRange.sample(random),
        sToESIDayRange.sample(random),
        sToESINghtRange.sample(random),
        eToMIRange.sample(random),
        mIToRRange.sample(random),
        mIToSIRange.sample(random),
        numSeedsRange.sample(random),
        numRecSeedsRange.sample(random),
       // firstBetaMultRange.sample(random),
      //  secondBetaMultRange.sample(random),
        transModRange.sample(random)
      )
    )
  }

  def perturb(p: FittingParameters) = {
    new FittingParameters(
      p.getsToE_MI_Factor() + sToEMIMultRange.kernel.sample,
      p.getsToE_SI_day() + sToESIDayRange.kernel.sample,
      p.getsToE_SI_night() + sToESINghtRange.kernel.sample,
      p.geteToMI + eToMIRange.kernel.sample,
      p.getMiToR + mIToRRange.kernel.sample,
      p.getMiToSI + mIToSIRange.kernel.sample,
      p.getNumSeeds + numSeedsRange.kernel.sample,
      p.getNumRecoveredSeeds + numRecSeedsRange.kernel.sample,
      p.getTransModifier + transModRange.kernel.sample
    )
  }

  def perturbDensity(a: FittingParameters, b: FittingParameters) =
    sToEMIMultRange.kernel.density(a.getsToE_MI_Factor - b.getsToE_MI_Factor) *
      sToESIDayRange.kernel.density(a.getsToE_SI_day - b.getsToE_SI_day) *
      sToESINghtRange.kernel.density(a.getsToE_SI_night - b.getsToE_SI_night) *
      eToMIRange.kernel.density(a.geteToMI - b.geteToMI) *
      mIToRRange.kernel.density(a.getMiToR - b.getMiToR) *
      mIToSIRange.kernel.density(a.getMiToSI - b.getMiToSI)*
      numSeedsRange.kernel.density(a.getNumSeeds - b.getNumSeeds) *
      numRecSeedsRange.kernel.density(a.getNumRecoveredSeeds - b.getNumRecoveredSeeds) *
      transModRange.kernel.density(a.getTransModifier - b.getTransModifier)

  def distanceToObservations(abcParams: FittingParameters) = Distribution.from { random: Random =>
    val inputData = InputData.fromJSON(modelConfigJsonStr)
    val configParams = ConfigParameters.fromJSON(modelConfigJsonStr)
    val defaultParams = Parameters.fromJSON(modelConfigJsonStr)
    val covidVariantParams = CovidVariantParameters.fromJSON(modelConfigJsonStr,defaultParams)

    val out = new CouncilAreaSummary()

    val simulated = {
      val modelParams = abcParams.makeFullParameterSet(defaultParams)
      val result = ScovModRunner.apply(inputData, configParams, modelParams, covidVariantParams, out)
      //val result = ScovModRunner.apply(inputData, configParams, modelParams, out)
      Results.fromSimulated(result.asInstanceOf[CouncilAreaSummaryResult])
    }
    Metric.apply(simulated, observed)
  }

//  def distanceToObservations(abcParams: FittingParameters) = Distribution.from { random: Random =>
//    val inputData = InputData.fromJSON(modelConfigJsonStr)
//    val configParams = ConfigParameters.fromJSON(modelConfigJsonStr)
//    val defaultParams = Parameters.fromJSON(modelConfigJsonStr)
//    val out = new HealthBoardSummary()
//
//    val simulated = {
//      val modelParams = abcParams.makeFullParameterSet(defaultParams)
//      val result = ScovModRunner.apply(inputData, configParams, modelParams, out)
//      Results.fromSimulated(result.asInstanceOf[HealthBoardSummaryResult])
//    }
//    Metric.apply(simulated, observed)
//  }
}
