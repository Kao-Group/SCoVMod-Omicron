//package scovmod.run
//
//import java.nio.charset.Charset
//import java.nio.file.Paths
//
//import org.apache.commons.io.FileUtils
//import play.api.libs.json.Json
//import scovmod.TBMIRunner
//import scovmod.model.input.config.{ConfigParameters, InputData, Parameters}
//
//object SimpleRunner extends App {
//
//  val jsonPath = Paths.get("20170411_debugging-scovmod.json")
//  val jsonStr = FileUtils.readFileToString(jsonPath.toFile(), Charset.defaultCharset)
//  val jsonParsed = Json.parse(jsonStr)
//
//  val outDir = Paths.get("debugging-output")
//
//  val inputData = InputData.fromJSON(jsonStr)
//  val configParams = ConfigParameters.fromJSON(jsonStr)
//  val modelParams = Parameters.fromJSON(jsonStr)
//
//  val out = new IntegrationTestCSV(outDir)
//
//  TBMIRunner.apply(
//    inputData,
//    configParams,
//    modelParams,
//    out
//  )
//}
