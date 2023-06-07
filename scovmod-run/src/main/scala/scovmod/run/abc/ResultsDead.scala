//package scovmod.run.abc
//
//import java.nio.file.Path
//import java.sql.Timestamp
//import java.time.LocalDate
//import java.util
//
//import scovmod.model.output.modules.councilarea.{DailyResult, CouncilAreaSummaryResult, CouncilAreaValueType}
////import scovmod.model.output.modules.healthboard.{DailyResult, HealthBoardSummaryResult, HealthBoardValueType}
//
//import scala.collection.mutable
//import scala.io.Source
//
//case class Results(byDay: Map[Long, DailyResults], isObserved: Boolean)
//case class DailyResults(deathsByCommunity: Map[Int, Int], dzWithDeathsByCommunity: Map[Int, Int]){}
//object DailyResults{
//  val empty = DailyResults(Map.empty[Int, Int],Map.empty[Int, Int])
//}
//
//object Results {
//  def fromSimulated(data: CouncilAreaSummaryResult): Results = {
//    import scala.collection.JavaConversions._
//    val resultMap: util.Map[DailyResult, Integer] = data.getSimulatedDailyResultPerCouncilArea
//    val allDays = Seq(18330l, 18337l, 18344l, 18351l, 18358l) // Start of epidemic
//    //val allDays = Seq(18358l, 18365l, 18372l, 18379l, 18386l, 18393l, 18400l, 18407l) // Later in epidemic
//    // val allDaysNew = Seq(18330l, 18337l, 18344l, 18351l, 18358l,18365l, 18372l, 18379l, 18386l, 18393l, 18400l, 18407l)
//    val byDay: Map[Long, DailyResults] = allDays.map { day =>
//      val dead: mutable.Map[DailyResult, Integer] =
//        resultMap.filter{case (dailyResult,value) =>
//          dailyResult.getType == CouncilAreaValueType.DEAD && dailyResult.getDate.toEpochDay == day
//        }
//      val deadDZs: mutable.Map[DailyResult, Integer] =
//        resultMap.filter{case (dailyResult,value) =>
//          dailyResult.getType == CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_DEAD && dailyResult.getDate.toEpochDay == day
//        }
//
//      def keysAsCommunity(map: Map[DailyResult, Integer]) = map.map { case (k, v) => k.getCouncilAreaID -> v.toInt }
//
//      day -> DailyResults(
//        keysAsCommunity(dead.toMap)
//        ,keysAsCommunity(deadDZs.toMap)
//      )
//    }.toMap
//    val res = Results(byDay, false)
//    res
//  }
//
//  def fromObserved(path: Path): Results = {
//    val headerMap = Source.fromFile(path.toFile)
//        .getLines()
//        .next()
//        .split(",")
//        .map(_.trim.replaceAll("\"", ""))
//        .zipWithIndex
//        .toMap
//    val dateIdx = headerMap("date")
//    val areaIdx = headerMap("area")
//    val measurementIdx = headerMap("measurement")
//    val valueIdx = headerMap("value")
//
//    trait Measurement
//    object Measurement {
//      def parse(string: String): Option[Measurement] = {
//        string match {
//          case "DEAD" => Some(Dead)
//          case "NUMBER_DZ_AREAS_WITH_DEAD" => Some(DZAreasWithDead)
//          case _ => None
//        }
//      }
//    }
//
//    case object Dead extends Measurement
//    case object DZAreasWithDead extends Measurement
//
//    case class Row(day: Int, communityId: Int, measurement: Measurement, value: Int)
//    object RowParser {
//      def fromLine(line: String): Option[Row] = {
//        val toks = line.split(",").map(_.trim)
//        val measurementOpt = Measurement.parse(toks(measurementIdx).replaceAll("\"", ""))
//        val day: Long = Timestamp.valueOf(toks(dateIdx)+" 00:00:00").toLocalDateTime.toLocalDate.toEpochDay
//
//        measurementOpt.map(mesaurement =>
//          Row(
//            day.toInt,
//            toks(areaIdx).toInt,
//            mesaurement,
//            toks(valueIdx).toInt
//          )
//        )
//      }
//    }
//
//    val rowsByDay: Map[Long, Seq[Row]] = Source.fromFile(path.toFile)
//        .getLines
//        .drop(1)
//        .map(RowParser.fromLine)
//        .collect{ case Some(row) => row } // Throw away rows containing stats which are not needed
//        .toSeq
//        .groupBy(_.day)
//
//    val byDay: Map[Long, DailyResults] = rowsByDay.map { case (day, rows) =>
//      val deadLines: Seq[Row] = rows.filter(_.measurement == Dead)
//      val deadDZLines: Seq[Row] = rows.filter(_.measurement == DZAreasWithDead)
//
////      assume(deadLines.size == rows.size && deadIZLines.size == rows.size) //Not sure about this?
//
//      def valueByCommunity(rows: Seq[Row]): Map[Int, Int] = {
//        rows.map(row =>
//          row.communityId -> row.value
//        ).toMap
//      }
//
//      day -> DailyResults(
//        valueByCommunity(deadLines)
//        ,valueByCommunity(deadDZLines)
//      )
//    }
//    Results(byDay, isObserved = true)
//  }
//}
//
//
////object Results {
////  def fromSimulated(data: HealthBoardSummaryResult): Results = {
////    import scala.collection.JavaConversions._
////    val resultMap: util.Map[DailyResult, Integer] = data.getSimulatedDailyResultPerHealthBoard
////    val allDays = Seq(18330l, 18337l, 18344l, 18351l, 18358l)
////    val byDay: Map[Long, DailyResults] = allDays.map { day =>
////      val dead: mutable.Map[DailyResult, Integer] =
////        resultMap.filter{case (dailyResult,value) =>
////          dailyResult.getType == HealthBoardValueType.DEAD && dailyResult.getDate.toEpochDay == day
////        }
////      //      val deadLAs: mutable.Map[DailyResult, Integer] =
////      //        resultMap.filter{case (dailyResult,value) =>
////      //          dailyResult.getType == HealthBoardValueType.NUMBER_LA_AREAS_WITH_DEAD && dailyResult.getDate.toEpochDay == day
////      //        }
////
////      def keysAsCommunity(map: Map[DailyResult, Integer]) = map.map { case (k, v) => k.getHealthBoardID -> v.toInt }
////
////      day -> DailyResults(
////        keysAsCommunity(dead.toMap)
////        /*,keysAsCommunity(deadLAs.toMap)*/
////      )
////    }.toMap
////    val res = Results(byDay, false)
////    res
////  }
////
////  def fromObserved(path: Path): Results = {
////    val headerMap = Source.fromFile(path.toFile)
////      .getLines()
////      .next()
////      .split(",")
////      .map(_.trim.replaceAll("\"", ""))
////      .zipWithIndex
////      .toMap
////    val dateIdx = headerMap("date")
////    val areaIdx = headerMap("area")
////    val measurementIdx = headerMap("measurement")
////    val valueIdx = headerMap("value")
////
////    trait Measurement
////    object Measurement {
////      def parse(string: String): Option[Measurement] = {
////        string match {
////          case "DEAD" => Some(Dead)
////          case "NUMBER_LA_AREAS_WITH_DEAD" => Some(LAAreasWithDead)
////          case _ => None
////        }
////      }
////    }
////
////    case object Dead extends Measurement
////    case object LAAreasWithDead extends Measurement
////
////    case class Row(day: Int, communityId: Int, measurement: Measurement, value: Int)
////    object RowParser {
////      def fromLine(line: String): Option[Row] = {
////        val toks = line.split(",").map(_.trim)
////        val measurementOpt = Measurement.parse(toks(measurementIdx).replaceAll("\"", ""))
////        val day: Long = Timestamp.valueOf(toks(dateIdx)+" 00:00:00").toLocalDateTime.toLocalDate.toEpochDay
////
////        measurementOpt.map(mesaurement =>
////          Row(
////            day.toInt,
////            toks(areaIdx).toInt,
////            mesaurement,
////            toks(valueIdx).toInt
////          )
////        )
////      }
////    }
////
////    val rowsByDay: Map[Long, Seq[Row]] = Source.fromFile(path.toFile)
////      .getLines
////      .drop(1)
////      .map(RowParser.fromLine)
////      .collect{ case Some(row) => row } // Throw away rows containing stats which are not needed
////      .toSeq
////      .groupBy(_.day)
////
////    val byDay: Map[Long, DailyResults] = rowsByDay.map { case (day, rows) =>
////      val deadLines: Seq[Row] = rows.filter(_.measurement == Dead)
////      // val deadLALines: Seq[Row] = rows.filter(_.measurement == LAAreasWithDead)
////
////      //      assume(deadLines.size == rows.size && deadIZLines.size == rows.size) //Not sure about this?
////
////      def valueByCommunity(rows: Seq[Row]): Map[Int, Int] = {
////        rows.map(row =>
////          row.communityId -> row.value
////        ).toMap
////      }
////
////      day -> DailyResults(
////        valueByCommunity(deadLines)
////        /*,valueByCommunity(deadLALines)*/
////      )
////    }
////    Results(byDay, isObserved = true)
////  }
////}