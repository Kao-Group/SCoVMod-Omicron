package scovmod.run.runEnsemble

import java.nio.charset.Charset
import java.nio.file.{Files, Path, Paths}
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import sampler._
import sampler.distribution.Distribution
import sampler.maths.Random
import sampler.r.script.RScript
import scopt.Read
import scovmod.model.input.config.CovidVariantParameters
import scovmod.run.util.PopulationReader

import java.io.BufferedWriter
//import scovmod.run.util.PopulationReader
import scovmod.model.input.config.{ConfigParameters, InputData, Parameters}

import scala.collection.parallel.ForkJoinTaskSupport
import scala.concurrent.forkjoin.ForkJoinPool
import scala.io.Source

//object Test {
//
//  def main(args: Array[String]) {
//    import RunEnsemble._
//
//    println(RunEnsemble.parser)
//
//    val t = parser.parse(
//      "-c /stuff/scovmod.json -p /stuff/Gen042.json -d /stuff --operation spatialdiagnostics -n 1 -r 5".split(" "),
//      Job.empty)
//
//    println(t)
//  }
//}

object RunEnsemble extends App {
  val log = LoggerFactory.getLogger(getClass.getName)

  implicit val pathRead: Read[Path] =
    Read.reads[Path] { str => Paths.get(str) }

  val parser = new scopt.OptionParser[Job]("runEnsemble") {
    opt[Path]('d', "paramDist") required() valueName ("<paramDistFile>") action { (x, c) =>
      c.copy(posterior = x)
    } text ("Mandatory parameters distribution (ABC Posterior) file ")

    opt[Path]('o', "outDir") valueName ("<outPath>") optional() action { (x, c) =>
      c.copy(outDir = x)
    } text ("Optional name of the output path - default is /output")

    opt[Task]('t', "task") required() valueName ("<task>") action { (x, c) =>
      c.copy(task = x)
    } text ("Ensemble task to perform - Epicurves or FitAssessment")

    opt[Path]('c', "config") valueName ("[configFile]") optional() action { (x, c) =>
      c.copy(tbmiConfig = x)
    } text ("Optional TBMI config file - default is ../etc/scovmod.json")

    opt[Int]('p', "parallelism") valueName ("[parallelism]") optional() action { (x, c) =>
      c.copy(noCores = x)
    } text ("Number of models to run in parallel - default 2 (4 threads total)")

    opt[Int]('r', "reps") valueName ("[numReps]") optional() action { (x, c) =>
      c.copy(noRuns = x)
    } text ("Optional number of model reps - default is 10")
  }

  parser
    .parse(args, Job.empty)
    .foreach{job =>
      log.info("Running job: "+job)
      run(job)
    }

  def run(job: Job): Unit ={
//    import job._
    val particleWriterCSV: BufferedWriter = Files.newBufferedWriter(Paths.get(job.outDir+"/sampledParticles.csv"), Charset.defaultCharset)
    particleWriterCSV.write("Run_id,eToMI,MIToR,MIToSI,BetaMildToSevereMult,BetaSevereDay,BetaSevereNight")
    val jobIds = (1 to job.noRuns).par
    jobIds.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(job.noCores))
    jobIds.foreach{i =>
      log.info(s"Thread ${Thread.currentThread().getId()} starting job $i")
      job.runModel(i,particleWriterCSV)
    }

//    log.info(s"Running aggregation")
//    job.runAggregation(job)

//    log.info(s"Running R plotting script: ${job.task.rScriptNames}")
//    job.runPlotting()
  }
}

case class Job(
  posterior: Path,
  task: Task,
 // outDir: Path = Paths.get("/", "/home/thomdoherty/results_noLockdown_OALevelIn_HBLevelOut_Trimmed"),
 // tbmiConfig: Path = Paths.get("/home/thomdoherty/_CODE/SCoVMod-run/build/etc/scovmod.json"),
  outDir: Path = Paths.get("/", "output"),
  tbmiConfig: Path = Paths.get("etc/scovmod.json"),
  noCores: Int = 2,
  noRuns: Int = 10
){

  lazy val scovmodConfigStr = FileUtils.readFileToString(tbmiConfig.toFile, Charset.defaultCharset())
  def inputData = InputData.fromJSON(scovmodConfigStr)
  def configParams = ConfigParameters.fromJSON(scovmodConfigStr)
  val modelParams = Parameters.fromJSON(scovmodConfigStr)
  def covidVariantParams = CovidVariantParameters.fromJSON(scovmodConfigStr,modelParams)

  lazy val parameterDist = {
    val population = PopulationReader.fromString(
        FileUtils.readFileToString(posterior.toFile, Charset.defaultCharset())
    )
    val defaultParams = Parameters.fromJSON(scovmodConfigStr);

    Distribution
        .uniform(population.weightedParticles.toIndexedSeq)
        .map(_.scored.params)
        .map(_.makeFullParameterSet(defaultParams))
  }

//    lazy val parameters = { ///NB Must remove when fitted to use parameterDist above
//      val defaultParams = Parameters.fromJSON(scovmodConfigStr);
//      defaultParams
//    }

  def runModel(runId: Int, particleWriterCSV: BufferedWriter) = task.runModel(runId, particleWriterCSV: BufferedWriter, this)

//  def runAggregation(job: Job): Unit = task.runAggregation(job)

//  def runPlotting() = {
//    task.copySupportingData(outDir)
//
//    task.rScriptNames.foreach{name =>
//      val scriptTxt: String = Source.fromInputStream(getClass.getResourceAsStream("/"+name)).mkString
//      RScript(scriptTxt, outDir.resolve(name+".r"))
//    }
//  }
}

object Job {
  val empty = Job(null, null)
}

trait Task{
  val rScriptNames: Seq[String]

//  def copyResourceByName(name: String, outDir: Path): Unit ={
//    val lines = Source.fromInputStream(getClass.getResourceAsStream("/plotData/"+name)).getLines()
//    Files.newBufferedWriter(outDir.resolve(name))
//
//    import scala.collection.JavaConversions._
//    FileUtils.writeLines(
//      outDir.resolve(name).toFile,
//      lines.toList
//    )
//  }

//  def copySupportingData(outDir: Path): Unit // Some tasks need different data

  def runModel(runId: Int, particleWriterCSV: BufferedWriter, job: Job): Unit
//  def runAggregation(job: Job): Unit = {} //Not all tasks will need an aggregation step, so provide empty impl by default

  implicit val r = Random
}
object Task{
  implicit val operationRead: Read[Task] =
    Read.reads[Task] { _.toLowerCase match {
      case "fitassessmentcalevel" => FitAssessmentCALevel
      case "fitassessmentoalevel" => FitAssessmentOALevel
      case "fitassessmentoalevelrcalc" => FitAssessmentOALevelRCalc
      case "fitassessmentcalevelallstates" => FitAssessmentCALevelAllStates
      case "fitassessmentdzlevelallstates" => FitAssessmentDZLevelAllStates
      case other => throw new Exception("Could not parse operation: "+other)
    }}
}