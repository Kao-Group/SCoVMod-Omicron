package scovmod.run.abc

import org.slf4j.LoggerFactory

object Metric {

  val log = LoggerFactory.getLogger(getClass.getName)

  def apply(simulated: Results, observed: Results): Double = {
    assume(simulated.isObserved == false && observed.isObserved == true)
    //println("observed: "+observed.toString)
    //println("simulated: "+simulated.toString)

    val allDays = observed.byDay.keySet.toList.sorted
   // val allDays = Seq(18470l, 18477l, 18484l, 18491l, 18498l, 18505l, 18512l,18519l,18526l)
    //val fittingDays = Seq(18762,18769,18776,18783,18790)
    val fittingDays = Seq(18972,18979,18986,18993)

    //val fittingDays = Seq(/*18498, 18505, 18512,*/18519,18526,18533,18540)//,18547,18554,18561)
    //val fittingDays = Seq(18475, 18482, 18489, 18496, 18503, 18510, 18517,18524,18531)

    val score = fittingDays.map { day =>
      assessDailyFit(
        simulated.byDay.getOrElse(day, DailyResults.empty), //This allows for the potential to have no results logged in a given year
        observed.byDay(day))
    }.sum

    log.info("Fitting to days: "+fittingDays+"   Score was "+score)

    score
  }

  def assessDailyFit(simulated: DailyResults, observed: DailyResults): Double = {
    val councilAreas: Seq[Int] = 5 to 50  // Seq(10,28,29,36,49,50)
    councilAreas.map { ca =>
      val obsMildInfectiousSum = observed.mildInfectiousByCommunity.getOrElse(ca, 0)
      val simMildInfectiousSum = simulated.mildInfectiousByCommunity.getOrElse(ca, 0)
     // val obsNoDZWithInfectedSum = observed.dzWithInfectedByCommunity.getOrElse(ca, 0)
     // val simNoDZWithInfectedSum = simulated.dzWithInfectedByCommunity.getOrElse(ca, 0)
     // println("CA: "+ca)
      //println("obsDeadSum: " + obsDeadSum + " simDeadSum: " + simDeadSum + " obsNoDZWithDeadSum: " + obsNoDZWithDeadSum + " simNoDZWithDeadSum: " + simNoDZWithDeadSum)
      //println("maxDeadByCouncilArea: "+maxDeadByCouncilArea+" maxNoDZWithDeadByCouncilArea: "+maxNoDZWithDeadByCouncilArea)
     // log.info("obsMildInfectiousSum: " + obsMildInfectiousSum + " simMildInfectiousSum: " + simMildInfectiousSum)

      val sum = math.pow((obsMildInfectiousSum - simMildInfectiousSum) /** maxNoDZWithInfectedByCouncilArea*/, 2)
      /*+math.pow((obsNoDZWithInfectedSum - simNoDZWithInfectedSum) * maxInfectedByCouncilArea, 2)*/
     // log.info("DiffSquared: "+sum+" for ca"+ca)
      sum
    }.sum
  }
}
