package scovmod.run.util

import java.io.{File, FileFilter}
import java.nio.charset.Charset
import java.nio.file.{Path, Paths}

import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.WildcardFileFilter
import play.api.libs.json.{JsObject, JsValue, Json}

object JsonAggregator extends App{

  /*
{
  "meta" : {
    "model-name" : "???",
    "model-version" : "",
    "created" : "2017-08-21T19:38:00.037",
    "other-info" : "..."

  },
  "structure": {
    "inputs" : [
      {
        "name" : "alpha",
        "type" : "continuous"
      },
      {
        "name" : "beta",
        "type" : "continuous"
      },
      {
        "name" : "gamma",
        "type" : "continuous"
      },
      {
        "name" : "delta",
        "type" : "continuous"
      }
    ],
    "outputs" : [
      {
        "name" : "National breakdowns by year"
      },
      {
        "name" : "National failed tests by year"
      },
      {
        "name" : "National reactors by year"
      }
    ]
  },
  "data" : [
    {
      "in" : [0.1, 0.5, 0.2, 0.4 ... 0.6],
      "out" : [
        [1234,2345,3456,34567,5678,6789,7890,1234,2345],
        [1234,2345,3456,34567,5678,6789,7890,1234,2345]
        [1234,2345,3456,34567,5678,6789,7890,1234,2345]
      ]
    },{
      "in" : [0.1, 0.5, 0.2, 0.4 ... 0.6],
      "out" : [
        [1234,2345,3456,34567,5678,6789,7890,1234,2345],
        [1234,2345,3456,34567,5678,6789,7890,1234,2345]
        [1234,2345,3456,34567,5678,6789,7890,1234,2345]
      ]
    },{
      "in" : [0.1, 0.5, 0.2, 0.4 ... 0.6],
      "out" : [
        [1234,2345,3456,34567,5678,6789,7890,1234,2345],
        [1234,2345,3456,34567,5678,6789,7890,1234,2345]
        [1234,2345,3456,34567,5678,6789,7890,1234,2345]
      ]
    }
  ]
}
   */



  val wordkingDir: Path = Paths.get("/efs/TBMI/fitting-results/20170821_FitSpatialSeeding/fitAssessment010Test/")
  val fileFilter: FileFilter = new WildcardFileFilter(s"countyValues_*.json")
  val candidateFiles: Seq[File] = wordkingDir.toFile
      .listFiles(fileFilter)

  val accumulatedResults: Seq[JsObject] = {
    val groupDataResults: Seq[JsObject] =  candidateFiles.map { file =>
      val jsonStr = FileUtils.readFileToString(file, Charset.defaultCharset())
      val oneRunJson = fromJson(jsonStr)
      oneRunJson
    }
    groupDataResults
  }

  def fromJson(jsonStr: String): JsObject = {
    val json = Json.parse(jsonStr)
    val inputDetails = (json  \ "data" \ "inputs").as[Seq[JsValue]]
    val outputDetails = (json  \ "data" \ "outputs").as[Seq[JsValue]]
    Json.obj(
      "inputs" -> inputDetails,
      "outputs" -> outputDetails
    )
  }

  def toJSON(): JsValue = {

    val structureJson = Json.obj(
      "inputs" -> Json.arr(
        Json.obj("name" -> "sToT", "type" -> "continuous"),
        Json.obj("name" -> "tToI", "type" -> "continuous"),
        Json.obj( "name" -> "envFarm", "type" -> "continuous"),
        Json.obj("name" -> "envParish", "type" -> "continuous"),
        Json.obj("name" -> "envDecay", "type" -> "continuous"),
        Json.obj("name" -> "numSeeds", "type" -> "continuous"),
        Json.obj("name" -> "infSens", "type" -> "continuous"),
        Json.obj("name" -> "testSensMult", "type" -> "continuous")
      ),
      "outputs" -> Json.arr(
        Json.obj("name" -> "National breakdowns by year"),
        Json.obj("name" -> "National failed tests by year"),
        Json.obj("name" -> "National reactors by year")
      )
    )

    val allJson: JsValue = Json.obj(
      "structure" -> structureJson,
      "data" -> accumulatedResults
    )

    allJson
  }


  val json = Json.prettyPrint(toJSON())
  FileUtils.write(wordkingDir.resolve("aggregated.json").toFile, json)
}
