//package scovmod.run.abc
//
//import org.slf4j.LoggerFactory
//
//object DeadMetric {
//
//  val log = LoggerFactory.getLogger(getClass.getName)
//
//  def apply(simulated: Results, observed: Results): Double = {
//    assume(simulated.isObserved == false && observed.isObserved == true)
//
//    val allDays = observed.byDay.keySet.toList.sorted
//   // val fittingDays = Seq(18323, 18324, 18325, 18326, 18327, 18328) // TODO CHANGE THIS TO EPOCH DAYS?
//   //val fittingDays = 18332 to 18362
//   // val fittingDays = 18346 to 18350
//   val fittingDays = Seq(18330, 18337, 18344, 18351, 18358)
//    // Later fit
//    // val fittingDaysPostLockdown = Seq(18358, 18365, 18372, 18379, 18386, 18393, 18400, 18407)
//    // val fittingDaysNew = Seq(18330, 18337, 18344, 18351, 18358, 18365, 18372, 18379, 18386, 18393, 18400, 18407)
//
//
//    val score = fittingDays.map { day =>
//      assessDailyFit(
//        simulated.byDay.getOrElse(day, DailyResults.empty), //This allows for the potential to have no results logged in a given year
//        observed.byDay(day))
//    }.sum
//
//    log.info("Fitting to days: "+fittingDays+"   Score was "+score)
//
//    score
//  }
//
//  def assessDailyFit(simulated: DailyResults, observed: DailyResults): Double = {
//    val maxDeadByCouncilArea= if(observed.deathsByCommunity.values.seq.isEmpty) 0 else observed.deathsByCommunity.values.max
//    val maxNoDZWithDeadByCouncilArea=if(observed.dzWithDeathsByCommunity.values.seq.isEmpty) 0 else observed.dzWithDeathsByCommunity.values.max
//    val councilAreas: Seq[Int] = 5 to 50 //Health board codes in scotland go from 15 to 32 (18,21,23 and 27 missing)
//    councilAreas.map { ca =>
//      val obsDeadSum = observed.deathsByCommunity.getOrElse(ca, 0)
//      val simDeadSum = simulated.deathsByCommunity.getOrElse(ca, 0)
//      val obsNoDZWithDeadSum = observed.dzWithDeathsByCommunity.getOrElse(ca, 0)
//      val simNoDZWithDeadSum = simulated.dzWithDeathsByCommunity.getOrElse(ca, 0)
//     // println("CA: "+ca)
//      //println("obsDeadSum: " + obsDeadSum + " simDeadSum: " + simDeadSum + " obsNoDZWithDeadSum: " + obsNoDZWithDeadSum + " simNoDZWithDeadSum: " + simNoDZWithDeadSum)
//      //println("maxDeadByCouncilArea: "+maxDeadByCouncilArea+" maxNoDZWithDeadByCouncilArea: "+maxNoDZWithDeadByCouncilArea)
//
//      val sum = math.pow((obsDeadSum - simDeadSum) * maxNoDZWithDeadByCouncilArea, 2)
//      +math.pow((obsNoDZWithDeadSum - simNoDZWithDeadSum) * maxDeadByCouncilArea, 2)
//     // println("DiffSquared: "+sum)
//      sum
//    }.sum
//  }
//}
