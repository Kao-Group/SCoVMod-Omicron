
package scovmod.model.output;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;

import java.time.LocalDate;

public interface IOutputModule {
    public IResult buildResult();
    public void currentTotalSusceptible(LocalDate date, int totalSusceptible, int location);
    public void currentSusceptibleYoung(LocalDate date, int totalSusceptible, int location);
    public void currentSusceptibleAdult(LocalDate date, int totalSusceptible, int location);
    public void currentSusceptibleElderly(LocalDate date, int totalSusceptible, int location);
    public void currentTotalVaccinatedSusceptible(LocalDate date, int totalVacSusceptible, int location);
    public void currentVaccinatedSusceptibleYoung(LocalDate date, int totalVacSusceptible, int location);
    public void currentVaccinatedSusceptibleAdult(LocalDate date, int totalVacSusceptible, int location);
    public void currentVaccinatedSusceptibleElderly(LocalDate date, int totalVacSusceptible, int location);
    public void currentTotalVaccinatedNonSusceptible(LocalDate date, int totalVacNonSusceptible, int location);
    public void currentVaccinatedNonSusceptibleYoung(LocalDate date, int totalVacSusceptible, int location);
    public void currentVaccinatedNonSusceptibleAdult(LocalDate date, int totalVacSusceptible, int location);
    public void currentVaccinatedNonSusceptibleElderly(LocalDate date, int totalVacSusceptible, int location);

    public void currentTotalFullyProtected(LocalDate date, int total, int location);
    public void currentTotalPartialProtected(LocalDate date, int total, int location);
    public void currentTotalPartialImmLossVacProtected(LocalDate date, int total, int location);
    public void currentTotalPartialImmLossNatProtected(LocalDate date, int total, int location);
    public void currentTotalFullyProtectedYoung(LocalDate date, int total, int location);
    public void currentTotalFullyProtectedAdult(LocalDate date, int total, int location);
    public void currentTotalFullyProtectedElderly(LocalDate date, int total, int location);
    public void currentTotalPartialProtectedYoung(LocalDate date, int total, int location);
    public void currentTotalPartialProtectedAdult(LocalDate date, int total, int location);
    public void currentTotalPartialProtectedElderly(LocalDate date, int total, int location);
    public void currentTotalPartialImmLossVacProtectedYoung(LocalDate date, int total, int location);
    public void currentTotalPartialImmLossVacProtectedAdult(LocalDate date, int total, int location);
    public void currentTotalPartialImmLossVacProtectedElderly(LocalDate date, int total, int location);
    public void currentTotalPartialImmLossNatProtectedYoung(LocalDate date, int total, int location);
    public void currentTotalPartialImmLossNatProtectedAdult(LocalDate date, int total, int location);
    public void currentTotalPartialImmLossNatProtectedElderly(LocalDate date, int total, int location);

    public void currentTotalExposed(LocalDate date, int totalExposed, int location);
    public void currentTotalInfectious(LocalDate date, int totalInfectious, int location);
    public void currentTotalMildInfectious(LocalDate date, int totalInfectious, int location);
    public void currentTotalSevereInfectious(LocalDate date, int totalInfectious, int location);
    public void currentTotalHospitalised(LocalDate date, int totalHospitalised, int location);
    public void currentTotalRecovered(LocalDate date, int totalRecovered, int location);
    public void currentTotalDead(LocalDate toTimeStepStartDate, int totalDead, int location);
    //public void currentSusceptibleYoung(LocalDate date, int totalSusceptible, int location);
    public void currentExposedYoung(LocalDate date, int totalExposed, int location);
    public void currentMildInfectiousYoung(LocalDate date, int totalInfectious, int location);
    public void currentSevereInfectiousYoung(LocalDate date, int totalInfectious, int location);
    public void currentHospitalisedYoung(LocalDate date, int totalHospitalised, int location);
    public void currentRecoveredYoung(LocalDate date, int totalRecovered, int location);
    public void currentDeadYoung(LocalDate date, int totalInfectious, int location);
  //  public void currentSusceptibleAdult(LocalDate date, int totalSusceptible, int location);
    public void currentExposedAdult(LocalDate date, int totalExposed, int location);
    public void currentMildInfectiousAdult(LocalDate date, int totalInfectious, int location);
    public void currentSevereInfectiousAdult(LocalDate date, int totalInfectious, int location);
    public void currentHospitalisedAdult(LocalDate date, int totalHospitalised, int location);
    public void currentRecoveredAdult(LocalDate date, int totalRecovered, int location);
    public void currentDeadAdult(LocalDate date, int totalInfectious, int location);
 //   public void currentSusceptibleElderly(LocalDate date, int totalSusceptible, int location);
    public void currentExposedElderly(LocalDate date, int totalExposed, int location);
    public void currentMildInfectiousElderly(LocalDate date, int totalInfectious, int location);
    public void currentSevereInfectiousElderly(LocalDate date, int totalInfectious, int location);
    public void currentHospitalisedElderly(LocalDate date, int totalHospitalised, int location);
    public void currentRecoveredElderly(LocalDate date, int totalRecovered, int location);
    public void currentDeadElderly(LocalDate date, int totalInfectious, int location);
    public void currentPopulation(LocalDate date, int totalPopulation, int location);


    public void currentNumberDZAreasWithDeadPerCouncilArea(LocalDate date, int totalPopulation, int location);
    public void newLockdown(LocalDate date, int location);
    public void lockdownEndDuration(LocalDate localDate, int durationInTimeSteps, int locationID);
    public void currentTracedYoung(LocalDate toTimeStepStartDate, int noT_y, int location);
    public void currentTracedAdult(LocalDate toTimeStepStartDate, int noT_a, int location);
    public void currentTracedElderly(LocalDate toTimeStepStartDate, int noT_e, int location);
    public void currentTraced(LocalDate toTimeStepStartDate, int traced, int location);
    public void newMildInfectiousCA(LocalDate toTimeStepStartDate, int councilAreaFromOA, int personID);
    public void newMildInfectiousDZ(LocalDate toTimeStepStartDate, int councilAreaFromOA, int personID);
    public void newMildInfectiousOA(LocalDate toTimeStepStartDate, int oa, int personID);
   public void newMildInfectiousVariantCA(LocalDate toTimeStepStartDate, int councilAreaFromOA, int personID);
   public void newMildInfectiousVariantDZ(LocalDate toTimeStepStartDate, int councilAreaFromOA, int personID);
   public void newMildInfectiousVariantOA(LocalDate toTimeStepStartDate, int oa, int personID);
    public void aggregateMildInfectiousEvents(LocalDate date, Int2DoubleMap caseMultiplierMap);
    public void aggregateMildInfectiousVariantEvents(LocalDate date, Int2DoubleMap caseMultiplierMap);




    //Variant stats
    public void currentTotalExposedVariant(LocalDate date, int totalExposed, int location);
    public void currentTotalInfectiousVariant(LocalDate date, int totalInfectious, int location);
    public void currentTotalMildInfectiousVariant(LocalDate date, int totalInfectious, int location);
    public void currentTotalSevereInfectiousVariant(LocalDate date, int totalInfectious, int location);
    public void currentTotalHospitalisedVariant(LocalDate date, int totalHospitalised, int location);
    public void currentTotalRecoveredVariant(LocalDate date, int totalRecovered, int location);
    public void currentTotalDeadVariant(LocalDate toTimeStepStartDate, int totalDead, int location);
    public void currentExposedYoungVariant(LocalDate date, int totalExposed, int location);
    public void currentMildInfectiousYoungVariant(LocalDate date, int totalInfectious, int location);
    public void currentSevereInfectiousYoungVariant(LocalDate date, int totalInfectious, int location);
    public void currentHospitalisedYoungVariant(LocalDate date, int totalHospitalised, int location);
    public void currentRecoveredYoungVariant(LocalDate date, int totalRecovered, int location);
    public void currentDeadYoungVariant(LocalDate date, int totalInfectious, int location);
    public void currentExposedAdultVariant(LocalDate date, int totalExposed, int location);
    public void currentMildInfectiousAdultVariant(LocalDate date, int totalInfectious, int location);
    public void currentSevereInfectiousAdultVariant(LocalDate date, int totalInfectious, int location);
    public void currentHospitalisedAdultVariant(LocalDate date, int totalHospitalised, int location);
    public void currentRecoveredAdultVariant(LocalDate date, int totalRecovered, int location);
    public void currentDeadAdultVariant(LocalDate date, int totalInfectious, int location);
    public void currentExposedElderlyVariant(LocalDate date, int totalExposed, int location);
    public void currentMildInfectiousElderlyVariant(LocalDate date, int totalInfectious, int location);
    public void currentSevereInfectiousElderlyVariant(LocalDate date, int totalInfectious, int location);
    public void currentHospitalisedElderlyVariant(LocalDate date, int totalHospitalised, int location);
    public void currentRecoveredElderlyVariant(LocalDate date, int totalRecovered, int location);
    public void currentDeadElderlyVariant(LocalDate date, int totalInfectious, int location);

    public void currentTotalDeadCA(LocalDate toTimeStepStartDate, int totalDead, int location);

    public void currentTotalRNASignal(LocalDate date, double value, String site);
}
