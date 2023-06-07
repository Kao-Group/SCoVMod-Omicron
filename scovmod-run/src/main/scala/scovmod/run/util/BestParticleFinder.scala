//package scovmod.run.util
//
//import java.nio.charset.Charset
//import java.nio.file.Paths
//
//import org.apache.commons.io.FileUtils
//import scovmod.model.input.config.FittingParameters
//
//object BestParticleFinder extends App {
//
//  val inputDir = Paths.get("/efs/TBMI/fitting-results/20180723_BMN_Stochastic_LRAOn_4kmHexSide_NoLRASeed_2006Start/sanityCheck")
//  val inputFileName = "Gen020.json"
//
//  val posterior = PopulationReader.fromString(
//    FileUtils.readFileToString(inputDir.resolve(inputFileName).toFile, Charset.defaultCharset())
//  )
//
//  val best: FittingParameters = posterior.weightedParticles.sortBy(_.scored.meanScore).drop(10).head.scored.params
//
//  import best._
//
//  println {
//    s"""
//SToERate = $getSToERate,
//EToIRate = $getEToIRate,
//InfectiousSensitivity = $getInfectiousSensitivity,
//BadgerToBadger = $getBadgerToBadger,
//BadgerToCattle = $getBadgerToCattle,
//CattleToBadger = $getCattleToBadger,
//BadgerDecay = $getBadgerDecay,
//NumSeeds = $getNumSeeds"""
//  }
//}
