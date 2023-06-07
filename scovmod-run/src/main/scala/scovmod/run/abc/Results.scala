package scovmod.run.abc

import java.nio.file.Path
import java.sql.Timestamp
import java.util

import scovmod.model.output.modules.councilarea.{CouncilAreaSummaryResult, CouncilAreaValueType, DailyResult}

import scala.collection.mutable
import scala.io.Source

case class Results(byDay: Map[Long, DailyResults], isObserved: Boolean)

case class DailyResults(mildInfectiousByCommunity: Map[Int, Int]/*, dzWithInfectedByCommunity: Map[Int, Int]*/){}

object DailyResults{
  val empty = DailyResults(Map.empty[Int, Int]/*,Map.empty[Int, Int]*/)
}

object Results {
  def fromSimulated(data: CouncilAreaSummaryResult): Results = {
    import scala.collection.JavaConversions._
    val resultMap: util.Map[DailyResult, Integer] = data.getSimulatedDailyResultPerCouncilArea
    //val allDays = Seq(18475l, 18482l, 18489l, 18496l, 18503l, 18510l, 18517l,18524l,18531l)
    //val allDays = Seq(/*18498l, 18505l, 18512l,*/18519l,18526l,18533l,18540l)//,18547l,18554l,18561l)
    //val allDays = Seq(18643l,18650l,18657l,18664l,18671l,18678l,18685l)
    //val allDays = Seq(18762l,18769l,18776l,18783l,18790l)
    val allDays = Seq(18972l,18979l,18986l,18993l)
    val byDay: Map[Long, DailyResults] = allDays.map { day =>
      val mildInfectious: mutable.Map[DailyResult, Integer] = {
        resultMap.filter{case (dailyResult,value) =>
          dailyResult.getType == CouncilAreaValueType.NEW_MILD_INFECTIOUS && dailyResult.getDate.toEpochDay == day
        }
      }
      //      val infectedDZs: mutable.Map[DailyResult, Integer] =
//        resultMap.filter{case (dailyResult,value) =>
//          dailyResult.getType == CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_INFECTED && dailyResult.getDate.toEpochDay == day
//        }

      def keysAsCommunity(map: Map[DailyResult, Integer]) = map.map { case (k, v) => k.getCouncilAreaID -> v.toInt }

      day -> DailyResults(
        keysAsCommunity(mildInfectious.toMap)
        /*,keysAsCommunity(infectedDZs.toMap)*/
      )
    }.toMap
    val res = Results(byDay, false)
    res
  }

  def fromObserved(path: Path): Results = {
    val headerMap = Source.fromFile(path.toFile)
        .getLines()
        .next()
        .split(",")
        .map(_.trim.replaceAll("\"", ""))
        .zipWithIndex
        .toMap
    val dateIdx = headerMap("date")
    val areaIdx = headerMap("area")
    val measurementIdx = headerMap("measurement")
    val valueIdx = headerMap("value")

    trait Measurement
    object Measurement {
      def parse(string: String): Option[Measurement] = {
        string match {
          case "NEW_MILD_INFECTIOUS" => Some(MildInfectious)
         // case "NUMBER_DZ_AREAS_WITH_INFECTED" => Some(DZAreasWithInfected)
          case _ => None
        }
      }
    }

    case object MildInfectious extends Measurement
    //case object DZAreasWithInfected extends Measurement

    case class Row(day: Int, communityId: Int, measurement: Measurement, value: Int)
    object RowParser {
      def fromLine(line: String): Option[Row] = {
        val toks = line.split(",").map(_.trim)
        val measurementOpt = Measurement.parse(toks(measurementIdx).replaceAll("\"", ""))
        val day: Long = Timestamp.valueOf(toks(dateIdx)+" 00:00:00").toLocalDateTime.toLocalDate.toEpochDay

        measurementOpt.map(mesaurement =>
          Row(
            day.toInt,
            toks(areaIdx).toInt,
            mesaurement,
            toks(valueIdx).toInt
          )
        )
      }
    }

    val rowsByDay: Map[Long, Seq[Row]] = Source.fromFile(path.toFile)
        .getLines
        .drop(1)
        .map(RowParser.fromLine)
        .collect{ case Some(row) => row } // Throw away rows containing stats which are not needed
        .toSeq
        .groupBy(_.day)

    val byDay: Map[Long, DailyResults] = rowsByDay.map { case (day, rows) =>
      val mildInfectiousLines: Seq[Row] = rows.filter(_.measurement == MildInfectious)
      // val infectedDZLines: Seq[Row] = rows.filter(_.measurement == DZAreasWithInfected)

//      assume(deadLines.size == rows.size && deadIZLines.size == rows.size) //Not sure about this?

      def valueByCommunity(rows: Seq[Row]): Map[Int, Int] = {
        rows.map(row =>
          row.communityId -> row.value
        ).toMap
      }

      day -> DailyResults(
        valueByCommunity(mildInfectiousLines)
        /*,valueByCommunity(infectedDZLines)*/
      )
    }
    Results(byDay, isObserved = true)
  }
}
