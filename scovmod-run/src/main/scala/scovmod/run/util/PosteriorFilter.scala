//package scovmod.run.util
//
//import java.nio.charset.Charset
//import java.nio.file.Paths
//
//import org.apache.commons.io.FileUtils
//import play.api.libs.json.Json
//import sampler.abc.{Population, Weighted}
//import scovmod.model.input.config.FittingParameters
//
//object PosteriorFilter extends App with PopulationWriter{
//
//  val inputDir = Paths.get("/home/ubuntu/scovmod_20210914_v00218_delta5/")
//  val inputFileName = "Gen021.json"
//  val outputFile = inputDir.resolve(inputFileName+".out.ppPropLessThan5Percent")
//
//  val posterior: Population[FittingParameters] = PopulationReader.fromString(
//    FileUtils.readFileToString(inputDir.resolve(inputFileName).toFile, Charset.defaultCharset())
//  )
////
////  val currentParam: Seq[Double] = posterior.weightedParticles.map { wtPar =>
////    val param = wtPar.scored.params.getPartialVersusFullProtectionProp
////    param
////  }
////
////
////  val percentiles = Seq(0.05, 0.5, 0.95)
////  val result = {
////    val empirical = currentParam.toEmpirical
////    empirical.percentile(percentiles)
////  }
////
// // println("result: "+result)
//
//   println("Before: "+posterior.weightedParticles.seq.size)
//  val filteredParticles: Seq[Weighted[FittingParameters]] = posterior.weightedParticles.filter{wtPar =>
//    val ppProp = wtPar.scored.params.getPartialVersusFullProtectionProp
//    (ppProp < 0.035)
//  }
//
//  println("After: "+filteredParticles.seq.size)
//
//
//  //  val updatedParticles: Seq[Weighted[FittingParameters]] = posterior.weightedParticles.map{wtPar =>
////    val params: FittingParameters = wtPar.scored.params
////    import params._
////    val newParams = new FittingParameters(
////      getSToTRate,
////      getTToIRate,
////      getInfectiousSensitivity,
////      getTestSensitiveSensitivityMultiplier,
////      getBadgerToBadger,
////      getBadgerToCattle,
////      getCattleToBadger,
////      getBadgerDecay,
////      getBadgerPopGrowthRate,
////       getNumSeeds
////    )
////    wtPar.copy(scored = wtPar.scored.copy(params = newParams))
////  }
//
//  //val updated: Population[FittingParameters] = posterior.copy(weightedParticles = updatedParticles)
//  val updated: Population[FittingParameters] = posterior.copy(weightedParticles = filteredParticles)
//
////  FileUtils.writeStringToFile(
////    outputFile.toFile,
////    Json.prettyPrint(updated.toJSON()),
////    Charset.defaultCharset
////  )
//}
