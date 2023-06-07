package scovmod.model.output;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.vaccination.EfficacyProtection;
import scovmod.model.vaccination.VaccinationManager;

public class VaccinatedAndSusceptibleStateReport {


    private final StatisticsCollector stats;
    private final StateQuery stateQuery;
    private final VaccinationManager vm;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;

    public VaccinatedAndSusceptibleStateReport(
            StatisticsCollector stats,
            StateQuery stateQuery,
            VaccinationManager vm,
            StartLocationsAndAgeClasses slaac) {
        this.stats = stats;
        this.stateQuery = stateQuery;
        this.vm = vm;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
    }

    public void updateReport(int location,OutputModuleType outputModuleType){
        int noSus = 0;
        int noSus_Y = 0;
        int noSus_A = 0;
        int noSus_E = 0;
        int noSusVac_Y = 0;
        int noSusVac_A = 0;
        int noSusVac_E = 0;
        int noSusNonVac_Y = 0;
        int noSusNonVac_A = 0;
        int noSusNonVac_E = 0;
        int noEffFull = 0;
        int noEffFull_Y = 0;
        int noEffFull_A = 0;
        int noEffFull_E = 0;
        int noEffPart = 0;
        int noEffPart_Y = 0;
        int noEffPart_A = 0;
        int noEffPart_E = 0;
        int noEffPartImmLossVac = 0;
        int noEffPartImmLossVac_Y = 0;
        int noEffPartImmLossVac_A = 0;
        int noEffPartImmLossVac_E = 0;
        int noEffPartImmLossNat = 0;
        int noEffPartImmLossNat_Y = 0;
        int noEffPartImmLossNat_A = 0;
        int noEffPartImmLossNat_E = 0;

        LocalPopulation localPop = stateQuery.getCopyOfLocalPopulation(location);
        IntSet allInStateSus = localPop.getAllInState(InfectionState.SUSCEPTIBLE);
        IntSet allPersonIds = localPop.getAllPersonIds();
        Int2IntMap vaccinatedPeopleMap = vm.getVaccinatedPeople();
        noSus = allInStateSus.size();
        for(int personID:allPersonIds){
            AgeClass ageClass = peopleAgeClasses.get(personID);
            EfficacyProtection currentEfficacy = vm.getVaccinationEfficacy(personID);
            //Get what age group the person is in
            switch (ageClass) {
                case YOUNG:
                    if(allInStateSus.contains(personID)) {
                        noSus_Y++;
                        if (vaccinatedPeopleMap.containsKey(personID)) {
                            noSusVac_Y++;
                        }
                    }
                    if(currentEfficacy==EfficacyProtection.FULL) {noEffFull_Y++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL) {noEffPart_Y++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY) {noEffPartImmLossVac_Y++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY) {noEffPartImmLossNat_Y++;}
                    break;
                case ADULT:
                    if(allInStateSus.contains(personID)) {
                        noSus_A++;
                        if (vaccinatedPeopleMap.containsKey(personID)) {
                            noSusVac_A++;
                        }
                    }
                    if(currentEfficacy==EfficacyProtection.FULL) {noEffFull_A++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL) {noEffPart_A++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY) {noEffPartImmLossVac_A++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY) {noEffPartImmLossNat_A++;}
                    break;
                case ELDERLY:
                    if(allInStateSus.contains(personID)) {
                        noSus_E++;
                        if (vaccinatedPeopleMap.containsKey(personID)) {
                            noSusVac_E++;
                        }
                    }
                    if(currentEfficacy==EfficacyProtection.FULL) {noEffFull_E++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL) {noEffPart_E++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY) {noEffPartImmLossVac_E++;}
                    if(currentEfficacy==EfficacyProtection.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY) {noEffPartImmLossNat_E++;}
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for logging of susceptible stats");
            }
        }
        noSusNonVac_Y = noSus_Y - noSusVac_Y;
        noSusNonVac_A = noSus_A- noSusVac_A;
        noSusNonVac_E = noSus_E - noSusVac_E;
        noEffFull = noEffFull_Y + noEffFull_A + noEffFull_E;
        noEffPart = noEffPart_Y + noEffPart_A + noEffPart_E;
        noEffPartImmLossVac = noEffPartImmLossVac_Y + noEffPartImmLossVac_A + noEffPartImmLossVac_E;
        noEffPartImmLossNat = noEffPartImmLossNat_Y + noEffPartImmLossNat_A + noEffPartImmLossNat_E;
        if(noSus>0) {
            stats.currentSusceptible(noSus ,location,outputModuleType);
        }
        if(noSus_Y>0) {
            stats.currentSusceptibleYoung(noSus_Y ,location,outputModuleType);
        }
        if(noSus_A>0) {
            stats.currentSusceptibleAdult(noSus_A ,location,outputModuleType);
        }
        if(noSus_E>0) {
            stats.currentSusceptibleElderly(noSus_E, location,outputModuleType);
        }
        if(noSusVac_Y>0) {
            stats.currentVaccinatedSusceptibleYoung(noSusVac_Y ,location,outputModuleType);
        }
        if(noSusVac_A>0) {
            stats.currentVaccinatedSusceptibleAdult(noSusVac_A ,location,outputModuleType);
        }
        if(noSusVac_E>0) {
            stats.currentVaccinatedSusceptibleElderly(noSusVac_E ,location,outputModuleType);
        }
        if(noSusNonVac_Y>0) {
            stats.currentVaccinatedNonSusceptibleYoung(noSusNonVac_Y ,location,outputModuleType);
        }
        if(noSusNonVac_A>0) {
            stats.currentVaccinatedNonSusceptibleAdult(noSusNonVac_A ,location,outputModuleType);
        }
        if(noSusNonVac_E>0) {
            stats.currentVaccinatedNonSusceptibleElderly(noSusNonVac_E ,location,outputModuleType);
        }

        if(noEffFull>0) {
            stats.currentTotalFullyProtected(noEffFull ,location,outputModuleType);
        }
        if(noEffFull_Y>0) {
            stats.currentTotalFullyProtectedYoung(noEffFull_Y ,location,outputModuleType);
        }
        if(noEffFull_A>0) {
            stats.currentTotalFullyProtectedAdult(noEffFull_A ,location,outputModuleType);
        }
        if(noEffFull_E>0) {
            stats.currentTotalFullyProtectedElderly(noEffFull_E ,location,outputModuleType);
        }
        if(noEffPart>0) {
            stats.currentTotalPartialProtected(noEffPart ,location,outputModuleType);
        }
        if(noEffPart_Y>0) {
            stats.currentTotalPartialProtectedYoung(noEffPart_Y ,location,outputModuleType);
        }
        if(noEffPart_A>0) {
            stats.currentTotalPartialProtectedAdult(noEffPart_A ,location,outputModuleType);
        }
        if(noEffPart_E>0) {
            stats.currentTotalPartialProtectedElderly(noEffPart_E ,location,outputModuleType);
        }
        if(noEffPartImmLossVac>0) {
            stats.currentTotalPartialImmLossVacProtected(noEffPartImmLossVac ,location,outputModuleType);
        }
        if(noEffPartImmLossVac_Y>0) {
            stats.currentTotalPartialImmLossVacProtectedYoung(noEffPartImmLossVac_Y ,location,outputModuleType);
        }
        if(noEffPartImmLossVac_A>0) {
            stats.currentTotalPartialImmLossVacProtectedAdult(noEffPartImmLossVac_A ,location,outputModuleType);
        }
        if(noEffPartImmLossVac_E>0) {
            stats.currentTotalPartialImmLossVacProtectedElderly(noEffPartImmLossVac_E ,location,outputModuleType);
        }

        if(noEffPartImmLossNat>0) {
            stats.currentTotalPartialImmLossNatProtected(noEffPartImmLossNat ,location,outputModuleType);
        }
        if(noEffPartImmLossNat_Y>0) {
            stats.currentTotalPartialImmLossNatProtectedYoung(noEffPartImmLossNat_Y ,location,outputModuleType);
        }
        if(noEffPartImmLossNat_A>0) {
            stats.currentTotalPartialImmLossNatProtectedAdult(noEffPartImmLossNat_A ,location,outputModuleType);
        }
        if(noEffPartImmLossNat_E>0) {
            stats.currentTotalPartialImmLossNatProtectedElderly(noEffPartImmLossNat_E ,location,outputModuleType);
        }
    }
}
