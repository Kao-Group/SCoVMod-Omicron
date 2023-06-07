package scovmod.run.util

import play.api.libs.json.JsValue
import sampler.abc.Population
import sampler.io.{Tokenable, Tokens}
import scovmod.model.input.config.FittingParameters
object PopulationReader {
  def parser(toks: Map[String, JsValue]) =

    new FittingParameters(
      toks("StoEMI_Mult").as[Double],
      toks("S-to-E_SI_Day").as[Double],
      toks("S-to-E_SI_Night").as[Double],
      toks("E-to-MI").as[Double],
      toks("MI-to-R").as[Double],
      toks("MI-to-SI").as[Double],
      toks("numSeeds").as[Double],
      toks("numRecSeeds").as[Double],
     // toks("firstBetaMult").as[Double],
     // toks("secondBetaMult").as[Double],
      toks("trans_mod").as[Double]

    )

  def fromString(jsonString: String) = {
    Population.fromJson(jsonString, parser _)
  }
}

trait PopulationWriter {
  implicit val writer: Tokenable[FittingParameters] = new Tokenable[FittingParameters]{
    def getTokens(p: FittingParameters) = Tokens.named(
      "StoEMI_Mult" -> p.getsToE_MI_Factor(),
      "S-to-E_SI_Day" -> p.getsToE_SI_day(),
      "S-to-E_SI_Night" -> p.getsToE_SI_night(),
      "E-to-MI" -> p.geteToMI(),
      "MI-to-R" -> p.getMiToR,
      "MI-to-SI" -> p.getMiToSI,
      "numSeeds"-> p.getNumSeeds,
      "numRecSeeds" -> p.getNumRecoveredSeeds,
   //   "firstBetaMult" -> p.getFirstBetaMultiplier,
   //   "secondBetaMult" -> p.getSecondBetaMultiplier,
      "trans_mod" -> p.getTransModifier
    )
  }
}
