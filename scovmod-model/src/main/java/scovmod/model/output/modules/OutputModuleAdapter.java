package scovmod.model.output.modules;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import scovmod.model.output.IOutputModule;
import scovmod.model.output.IResult;
import java.time.LocalDate;

public class OutputModuleAdapter implements IOutputModule {

    @Override
    public IResult buildResult() {
        return null;
    }

    @Override
    public void currentTotalSusceptible(LocalDate date, int totalSusceptible, int location) { }
    @Override
    public void currentSusceptibleYoung(LocalDate date, int totalSusceptible, int location){}
    @Override
    public void currentSusceptibleAdult(LocalDate date, int totalSusceptible, int location){}
    @Override
    public void currentSusceptibleElderly(LocalDate date, int totalSusceptible, int location){}
    @Override
    public void currentTotalVaccinatedSusceptible(LocalDate date, int totalVacSusceptible, int location){}
    @Override
    public void currentVaccinatedSusceptibleYoung(LocalDate date, int totalVacSusceptible, int location){}
    @Override
    public void currentVaccinatedSusceptibleAdult(LocalDate date, int totalVacSusceptible, int location){}
    @Override
    public void currentVaccinatedSusceptibleElderly(LocalDate date, int totalVacSusceptible, int location){}
    @Override
    public void currentTotalVaccinatedNonSusceptible(LocalDate date, int totalVacNonSusceptible, int location){}
    @Override
    public void currentVaccinatedNonSusceptibleYoung(LocalDate date, int totalVacSusceptible, int location){}
    @Override
    public void currentVaccinatedNonSusceptibleAdult(LocalDate date, int totalVacSusceptible, int location){}
    @Override
    public void currentVaccinatedNonSusceptibleElderly(LocalDate date, int totalVacSusceptible, int location){}
    @Override
    public void currentTotalFullyProtected(LocalDate date, int total, int location) {}
    @Override
    public void currentTotalPartialProtected(LocalDate date, int total, int location) {}
    @Override
    public void currentTotalPartialImmLossVacProtected(LocalDate date, int total, int location) {}
    @Override
    public void currentTotalPartialImmLossNatProtected(LocalDate date, int total, int location) {}
    @Override
    public void currentTotalFullyProtectedYoung(LocalDate date, int total, int location) {}
    @Override
    public void currentTotalFullyProtectedAdult(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalFullyProtectedElderly(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalPartialProtectedYoung(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalPartialProtectedAdult(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalPartialProtectedElderly(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalPartialImmLossVacProtectedYoung(LocalDate date, int total, int location) {}
    @Override
    public void currentTotalPartialImmLossVacProtectedAdult(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalPartialImmLossVacProtectedElderly(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalPartialImmLossNatProtectedYoung(LocalDate date, int total, int location) {}
    @Override
    public void currentTotalPartialImmLossNatProtectedAdult(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalPartialImmLossNatProtectedElderly(LocalDate date, int total, int location) { }
    @Override
    public void currentTotalExposed(LocalDate date, int totalExposed, int location) { }
    @Override
    public void currentTotalInfectious(LocalDate date, int totalInfectious, int location) { }
    @Override
    public void currentTotalMildInfectious(LocalDate date, int totalInfectious, int location) { }
    @Override
    public void currentTotalSevereInfectious(LocalDate date, int totalInfectious, int location) { }
    @Override
    public void currentTotalHospitalised(LocalDate date, int totalHospitalised, int location) { }
    @Override
    public void currentTotalRecovered(LocalDate date, int totalRecovered, int location) { }
    @Override
    public void currentTotalDead(LocalDate toTimeStepStartDate, int totalDead, int location) { }
    @Override
    public void currentExposedYoung(LocalDate date, int totalExposed, int location) { }
    @Override
    public void currentMildInfectiousYoung(LocalDate date, int totalInfectious, int location) { }
    @Override
    public void currentSevereInfectiousYoung(LocalDate date, int totalInfectious, int location) { }
    @Override
    public void currentHospitalisedYoung(LocalDate date, int totalHospitalised, int location) { }
    @Override
    public void currentRecoveredYoung(LocalDate date, int totalRecovered, int location) { }
    @Override
    public void currentDeadYoung(LocalDate date, int totalInfectious, int location) { }

//    @Override
//    public void currentSusceptibleAdult(LocalDate date, int totalSusceptible, int location) { }

    @Override
    public void currentExposedAdult(LocalDate date, int totalExposed, int location) { }

    @Override
    public void currentMildInfectiousAdult(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentSevereInfectiousAdult(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentHospitalisedAdult(LocalDate date, int totalHospitalised, int location) { }

    @Override
    public void currentRecoveredAdult(LocalDate date, int totalRecovered, int location) { }

    @Override
    public void currentDeadAdult(LocalDate date, int totalInfectious, int location) { }

//    @Override
//    public void currentSusceptibleElderly(LocalDate date, int totalSusceptible, int location) { }

    @Override
    public void currentExposedElderly(LocalDate date, int totalExposed, int location) { }

    @Override
    public void currentMildInfectiousElderly(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentSevereInfectiousElderly(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentHospitalisedElderly(LocalDate date, int totalHospitalised, int location) { }

    @Override
    public void currentRecoveredElderly(LocalDate date, int totalRecovered, int location) { }

    @Override
    public void currentDeadElderly(LocalDate date, int totalInfectious, int location) { }

    @Override
    public void currentPopulation(LocalDate date, int totalPopulation, int location) { }

    @Override
    public void currentNumberDZAreasWithDeadPerCouncilArea(LocalDate date, int totalPopulation, int location) { }

//    @Override
//    public void currentNumberDZAreasWithInfectedPerCouncilArea(LocalDate date, int totalPopulation, int location) { }

    @Override
    public void newLockdown(LocalDate date, int location) { }

    @Override
    public void lockdownEndDuration(LocalDate localDate, int durationInTimeSteps, int locationID) { }

    @Override
    public void currentTracedYoung(LocalDate toTimeStepStartDate, int noT_y, int location) { }

    @Override
    public void currentTracedAdult(LocalDate toTimeStepStartDate, int noT_a, int location) {}

    @Override
    public void currentTracedElderly(LocalDate toTimeStepStartDate, int noT_e, int location) {}

    @Override
    public void currentTraced(LocalDate toTimeStepStartDate, int traced, int location) {}

    @Override
    public void newMildInfectiousCA(LocalDate toTimeStepStartDate, int councilAreaFromOA, int personID) { }

    @Override
    public void newMildInfectiousDZ(LocalDate toTimeStepStartDate, int dzFromOA, int personID) { }

    @Override
    public void newMildInfectiousOA(LocalDate toTimeStepStartDate, int oa, int personID) { }

    @Override
    public void newMildInfectiousVariantCA(LocalDate toTimeStepStartDate, int councilAreaFromOA, int personID) {

    }

    @Override
    public void newMildInfectiousVariantDZ(LocalDate toTimeStepStartDate, int councilAreaFromOA, int personID) {

    }

    @Override
    public void newMildInfectiousVariantOA(LocalDate toTimeStepStartDate, int oa, int personID) {

    }

    @Override
    public void aggregateMildInfectiousEvents(LocalDate date, Int2DoubleMap caseMultiplier) { }

    @Override
    public void aggregateMildInfectiousVariantEvents(LocalDate date, Int2DoubleMap caseMultiplier) { }


    //Variants
    public void currentTotalExposedVariant(LocalDate date, int totalExposed, int location){}
    public void currentTotalInfectiousVariant(LocalDate date, int totalInfectious, int location){}
    public void currentTotalMildInfectiousVariant(LocalDate date, int totalInfectious, int location){}
    public void currentTotalSevereInfectiousVariant(LocalDate date, int totalInfectious, int location){}
    public void currentTotalHospitalisedVariant(LocalDate date, int totalHospitalised, int location){}
    public void currentTotalRecoveredVariant(LocalDate date, int totalRecovered, int location){}
    public void currentTotalDeadVariant(LocalDate toTimeStepStartDate, int totalDead, int location){}
    public void currentExposedYoungVariant(LocalDate date, int totalExposed, int location){}
    public void currentMildInfectiousYoungVariant(LocalDate date, int totalInfectious, int location){}
    public void currentSevereInfectiousYoungVariant(LocalDate date, int totalInfectious, int location){}
    public void currentHospitalisedYoungVariant(LocalDate date, int totalHospitalised, int location){}
    public void currentRecoveredYoungVariant(LocalDate date, int totalRecovered, int location){}
    public void currentDeadYoungVariant(LocalDate date, int totalInfectious, int location){}
    public void currentExposedAdultVariant(LocalDate date, int totalExposed, int location){}
    public void currentMildInfectiousAdultVariant(LocalDate date, int totalInfectious, int location){}
    public void currentSevereInfectiousAdultVariant(LocalDate date, int totalInfectious, int location){}
    public void currentHospitalisedAdultVariant(LocalDate date, int totalHospitalised, int location){}
    public void currentRecoveredAdultVariant(LocalDate date, int totalRecovered, int location){}
    public void currentDeadAdultVariant(LocalDate date, int totalInfectious, int location){}
    public void currentExposedElderlyVariant(LocalDate date, int totalExposed, int location){}
    public void currentMildInfectiousElderlyVariant(LocalDate date, int totalInfectious, int location){}
    public void currentSevereInfectiousElderlyVariant(LocalDate date, int totalInfectious, int location){}
    public void currentHospitalisedElderlyVariant(LocalDate date, int totalHospitalised, int location){}
    public void currentRecoveredElderlyVariant(LocalDate date, int totalRecovered, int location){}
    public void currentDeadElderlyVariant(LocalDate date, int totalInfectious, int location){}

    @Override
    public void currentTotalDeadCA(LocalDate toTimeStepStartDate, int totalDead, int location) {}

    @Override
    public void currentTotalRNASignal(LocalDate date, double value, String site){}
}
