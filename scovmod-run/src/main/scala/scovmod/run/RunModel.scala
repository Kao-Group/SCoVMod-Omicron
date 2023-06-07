package scovmod.run

import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{Files, Path, Paths}
import org.slf4j.LoggerFactory
import scovmod.model.input.config.{ConfigParameters, CovidVariantParameters, InputData, Parameters}
import scovmod.model.output.modules.community.{CommunitySummary, CommunitySummaryResult, DailyResult}
import scovmod.model.untested.Factory

import scala.annotation.tailrec

object RunModel extends App {

  val log = LoggerFactory.getLogger(getClass.getName)

  if (args.length != 1) {
    log.error("Expected exactly one argument, a path to a scovmod.json on disk.  Exiting")
    System.exit(1)
  }

  println(args)

  val configPath = Paths.get(args(0))
  assume(Files.exists(configPath), s"Config file $configPath doens't exist")
  val config = Files.readString(configPath, StandardCharsets.UTF_8)

  run(config)


  def run(jsonStr: String) {
    val out = new CommunitySummary()

    val inputData = InputData.fromJSON(jsonStr)
    val configParams = ConfigParameters.fromJSON(jsonStr)
    val modelParams = Parameters.fromJSON(jsonStr)
    val covidVariantParams = CovidVariantParameters.fromJSON(jsonStr,modelParams)

    val outDir: Path = Paths.get("out")
    if(! Files.exists(outDir)) Files.createDirectories(outDir)
    Files.writeString(outDir.resolve("config.json"), jsonStr, StandardCharsets.UTF_8)

    val simulated = Factory
      .buildModel(inputData, configParams, modelParams, covidVariantParams, out)
      //.buildModel(inputData, configParams, modelParams, out)
      .run

    val annualSimCountyResult = simulated.asInstanceOf[CommunitySummaryResult]
    import scala.collection.JavaConverters._
    val simResult = annualSimCountyResult.getSimulatedDailyResultPerCommunity
    writeResults(simResult.asScala.toMap, outDir)
  }

  def writeResults(results: Map[DailyResult, Integer], outDir: Path) {
    val writerCSV = Files.newBufferedWriter(outDir.resolve("countyValues.csv"), Charset.defaultCharset)
    writerCSV.write("run_id, Date, community, measurement, value")
    writerCSV.newLine
    writerCSV.flush
    results.foreach {
      case (annualResult, count) =>
        writerCSV.write(
            annualResult.getDate + "," +
            annualResult.getCommunityId + "," +
            annualResult.getType + "," +
            count
        )
        writerCSV.newLine
        writerCSV.flush
    }
  }
}
