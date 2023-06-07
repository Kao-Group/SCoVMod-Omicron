package scovmod.run.runEnsemble

import scovmod.model.input.config.Parameters
import scovmod.model.output.modules.communityForRCalc.CommunitySummary
import scovmod.model.output.modules.community.{CommunitySummaryResult, DailyResult}

import java.io.BufferedWriter
import java.nio.charset.Charset
import java.nio.file.{Files, Path, Paths}
import java.util
import scovmod.model.untested.Factory

case object FitAssessmentOALevelRCalc extends Task {
  val rScriptNames = Seq("riskAreaFitPlot.r", "epiCurvesPlot.r", "scatterPlot.r")

  def runModel(runId: Int, particleWriterCSV: BufferedWriter, job: Job): Unit = {
    import job._

    val out = new CommunitySummary
    val params = parameterDist.sample ///NB Must remove when fitted to use parameterDist again
   // val params = parameters
   val simulated = Factory.buildModel(inputData, configParams, params, covidVariantParams, out).run
    //val simulated = Factory.buildModel(inputData, configParams, params, out).run
    val dailySimCommunityResult = simulated.asInstanceOf[CommunitySummaryResult]
    import scala.collection.JavaConverters._
    val simResult: util.Map[DailyResult, Integer] = dailySimCommunityResult.getSimulatedDailyResultPerCommunity
    writeSampledParticleToFile(params,runId,particleWriterCSV)
    writeResults(simResult.asScala.toMap, params, runId, outDir)
  }

  def writeResults(results: Map[DailyResult, Integer], params: Parameters, runId: Int, outDir: Path){
    if(! Files.exists(outDir)) Files.createDirectories(outDir)
    val writerCSV = Files.newBufferedWriter(Paths.get(outDir+"/"+f"communityValues_$runId%03d.csv"), Charset.defaultCharset)
    writerCSV.write("run_id, day, area, measurement, value")
    writerCSV.newLine
    writerCSV.flush
    results.foreach {
      case (dailyResult, count) =>
        writerCSV.write(
          runId + "," +
            dailyResult.getDate + "," +
            dailyResult.getCommunityId + "," +
            dailyResult.getType + "," +
            count
        )
        writerCSV.newLine
        writerCSV.flush
    }
  }

  def writeSampledParticleToFile(posteriorSample: Parameters, sampleId: Int, particleWriterCSV: BufferedWriter){
    particleWriterCSV.newLine
    particleWriterCSV.flush
    particleWriterCSV.write(
      sampleId + "," +
        posteriorSample.geteToMI_AdultRate() + "," +
        posteriorSample.getMiToR_AdultRate+ "," +
        posteriorSample.getMiToSI_AdultRate + "," +
        posteriorSample.getsToE_Mild_AdultRate_Day() + "," + //This is actually the factor to get mild from severe
        posteriorSample.getsToE_Severe_AdultRate_Day() + "," +
        posteriorSample.getsToE_Severe_AdultRate_Night()
    )
    particleWriterCSV.newLine
    particleWriterCSV.flush
  }
}