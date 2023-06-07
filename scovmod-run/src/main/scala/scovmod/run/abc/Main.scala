package scovmod.run.abc

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import com.typesafe.config.ConfigFactory
import scovmod.model.input.config.FittingParameters
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json
import sampler.abc.ABC
import sampler.abc.ABCConfig
import sampler.abc.Generation
import sampler.abc.StandardReport
import sampler.abc.UseModelPrior
import scovmod.run.util.{PopulationReader, PopulationWriter}

object Main extends App with PopulationWriter {
  if (args.length < 1)
     println("Fitting App must be supplied with 1 arguments; the path to scovmod-fit.json file")
  val scovModRunJsonPath = args(0)
  val log = LoggerFactory.getLogger(getClass.getName)

  val config: JsValue =
    Json.parse(FileUtils.readFileToString(new File(scovModRunJsonPath), Charset.defaultCharset()))

  val outDir = Paths.get((config \ "output-dir").as[String])
  Files.createDirectories(outDir)

  //Decide if starting from scratch or resuming
  val startingGen: Generation[FittingParameters] = {
    val startFromStr = (config \ "start-from").as[String]
    if(startFromStr.trim.toLowerCase == "prior"){
      log.info("Using model prior")
      UseModelPrior()
    } else {
      log.info("Resuming from generation "+startFromStr)
      //Assuming it's a path
      val prevGenJson = FileUtils.readFileToString(new File(startFromStr), Charset.defaultCharset())
      PopulationReader.fromString(prevGenJson)
    }
  }

  val model: SCoVModModel = {
    val observedSummaryStatsFile = Paths.get((config \ "fitting-data" \ "observed-stats-by-councilarea").as[String])
    log.info("Fitting against observations file: "+observedSummaryStatsFile)

    val observedResults = Results.fromObserved(observedSummaryStatsFile)

    val modelConfigJsonStr = FileUtils.readFileToString(Paths.get((config \ "scovmod-model-config").as[String])
        .toFile, Charset.defaultCharset())

    new SCoVModModel(observedResults, modelConfigJsonStr)
  }

  ABC.apply(
      model,
      ABCConfig(ConfigFactory.load().getConfig("scovmod-model")),
      Some(StandardReport[FittingParameters](outDir)),
      startingGen
  )
}