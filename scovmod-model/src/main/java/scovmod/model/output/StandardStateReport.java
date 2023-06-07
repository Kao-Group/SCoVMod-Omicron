package scovmod.model.output;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.vaccination.VaccinationManager;

public class StandardStateReport {

    private final StatisticsCollector stats;
    private final StateQuery stateQuery;
    private final VaccinationManager vm;
    private final HealthBoardLookup hbl;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;
    private int totalInfectious = 0;
    private int totalInfected = 0;
    private int totalRecovered = 0;
    private int totalDead = 0;
    private int totalPopulation = 0;
    private int totalExposed = 0;
    private int totalHospitalised = 0;
    private int totalInfectious_variant = 0;
    private int totalInfected_variant = 0;
    private int totalRecovered_variant = 0;
    private int totalDead_variant = 0;
    private int totalExposed_variant = 0;
    private int totalHospitalised_variant = 0;
    private int totalSusceptible = 0;
    private int totalSusceptibleVaccinated = 0;
    private int totalSusceptbileNonVaccinated = 0;


    public StandardStateReport(StatisticsCollector stats, StateQuery stateQuery, VaccinationManager vm, HealthBoardLookup hbl, StartLocationsAndAgeClasses slaac) {
        this.stats = stats;
        this.stateQuery = stateQuery;
        this.vm = vm;
        this.hbl = hbl;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
    }

    public void updateReport(int location,OutputModuleType outputModuleType) {
         totalInfectious = 0;
         totalInfected = 0;
         totalRecovered = 0;
         totalDead = 0;
         totalPopulation = 0;
         totalExposed = 0;
         totalHospitalised = 0;
         totalInfectious_variant = 0;
         totalInfected_variant = 0;
         totalRecovered_variant = 0;
         totalDead_variant = 0;
         totalExposed_variant = 0;
         totalHospitalised_variant = 0;
         totalSusceptible = 0;
         totalSusceptibleVaccinated = 0;
         totalSusceptbileNonVaccinated = 0;
        int noE_Y = 0;
        int noE_A = 0;
        int noE_E = 0;
        int noMI_Y = 0;
        int noMI_A = 0;
        int noMI_E = 0;
        int noSI_Y = 0;
        int noSI_A = 0;
        int noSI_E = 0;
        int noH_Y = 0;
        int noH_A = 0;
        int noH_E = 0;
        int noR_Y = 0;
        int noR_A = 0;
        int noR_E = 0;
        int noT_Y = 0;
        int noT_A = 0;
        int noT_E = 0;
        int noD_Y = 0;
        int noD_A = 0;
        int noD_E = 0;
        int noSus = 0;
        int noE_Y_var = 0;
        int noE_A_var = 0;
        int noE_E_var = 0;
        int noMI_Y_var = 0;
        int noMI_A_var = 0;
        int noMI_E_var = 0;
        int noSI_Y_var = 0;
        int noSI_A_var = 0;
        int noSI_E_var = 0;
        int noH_Y_var = 0;
        int noH_A_var = 0;
        int noH_E_var = 0;
        int noR_Y_var = 0;
        int noR_A_var = 0;
        int noR_E_var = 0;
        int noT_Y_var = 0;
        int noT_A_var = 0;
        int noT_E_var = 0;
        int noD_Y_var = 0;
        int noD_A_var = 0;
        int noD_E_var = 0;
        LocalPopulation localPop = stateQuery.getCopyOfLocalPopulation(location);
        noE_Y = localPop.getAllInState(InfectionState.EXPOSED_YOUNG).size();
        if (noE_Y > 0) {
            stats.currentExposedYoung(noE_Y, location,outputModuleType);
        }
        noE_Y_var = localPop.getAllInState(InfectionState.EXPOSED_YOUNG_VARIANT).size();
        if (noE_Y_var > 0) {
            stats.currentExposedYoungVariant(noE_Y_var, location,outputModuleType);
        }
        noE_A = localPop.getAllInState(InfectionState.EXPOSED_ADULT).size();
        if (noE_A > 0) {
            stats.currentExposedAdult(noE_A, location,outputModuleType);
        }
        noE_A_var = localPop.getAllInState(InfectionState.EXPOSED_ADULT_VARIANT).size();
        if (noE_A_var > 0) {
            stats.currentExposedAdultVariant(noE_A_var, location,outputModuleType);
        }
        noE_E = localPop.getAllInState(InfectionState.EXPOSED_ELDERLY).size();
        if (noE_E > 0) {
            stats.currentExposedElderly(noE_E, location,outputModuleType);
        }
        noE_E_var = localPop.getAllInState(InfectionState.EXPOSED_ELDERLY_VARIANT).size();
        if (noE_E_var > 0) {
            stats.currentExposedElderlyVariant(noE_E_var, location,outputModuleType);
        }
        if (noE_Y + noE_A + noE_E > 0) {
            stats.currentExposed(noE_Y + noE_A + noE_E, location,outputModuleType);
        }
        if (noE_Y_var + noE_A_var + noE_E_var > 0) {
            stats.currentExposedVariant(noE_Y_var + noE_A_var + noE_E_var, location,outputModuleType);
        }
        noMI_Y = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG).size();
        if (noMI_Y > 0) {
            stats.currentMildInfectiousYoung(noMI_Y, location,outputModuleType);
        }
        noMI_A = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT).size();
        if (noMI_A > 0) {
            stats.currentMildInfectiousAdult(noMI_A, location,outputModuleType);
        }
        noMI_E = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY).size();
        if (noMI_E > 0) {
            stats.currentMildInfectiousElderly(noMI_E, location,outputModuleType);
        }
        if (noMI_Y + noMI_A + noMI_E > 0) {
            stats.currentMildInfectious(noMI_Y + noMI_A + noMI_E, location,outputModuleType);
        }
        noMI_Y_var = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT).size();
        if (noMI_Y_var > 0) {
            stats.currentMildInfectiousYoungVariant(noMI_Y_var, location,outputModuleType);
        }
        noMI_A_var = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT_VARIANT).size();
        if (noMI_A_var > 0) {
            stats.currentMildInfectiousAdultVariant(noMI_A, location,outputModuleType);
        }
        noMI_E_var = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY_VARIANT).size();
        if (noMI_E_var > 0) {
            stats.currentMildInfectiousElderlyVaraiant(noMI_E_var, location,outputModuleType);
        }
        if (noMI_Y_var + noMI_A_var + noMI_E_var > 0) {
            stats.currentMildInfectiousVariant(noMI_Y_var + noMI_A_var + noMI_E_var, location,outputModuleType);
        }
        noSI_Y = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_YOUNG).size();
        if (noSI_Y > 0) {
            stats.currentSevereInfectiousYoung(noSI_Y, location,outputModuleType);
        }
        noSI_A = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ADULT).size();
        if (noSI_A > 0) {
            stats.currentSevereInfectiousAdult(noSI_A, location,outputModuleType);
        }
        noSI_E = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ELDERLY).size();
        if (noSI_E > 0) {
            stats.currentSevereInfectiousElderly(noSI_E, location,outputModuleType);
        }
        noSI_Y_var = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_YOUNG_VARIANT).size();
        if (noSI_Y_var > 0) {
            stats.currentSevereInfectiousYoungVariant(noSI_Y_var, location,outputModuleType);
        }
        noSI_A_var = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ADULT_VARIANT).size();
        if (noSI_A_var > 0) {
            stats.currentSevereInfectiousAdultVariant(noSI_A_var, location,outputModuleType);
        }
        noSI_E_var = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT).size();
        if (noSI_E_var > 0) {
            stats.currentSevereInfectiousElderlyVariant(noSI_E_var, location,outputModuleType);
        }
        noT_Y = localPop.getAllInState(InfectionState.TRACED_YOUNG).size();
        if (noT_Y > 0) {
            stats.currentTracedYoung(noT_Y, location);
        }
        noT_A = localPop.getAllInState(InfectionState.TRACED_ADULT).size();
        if (noT_A > 0) {
            stats.currentTracedAdult(noT_A, location);
        }
        noT_E = localPop.getAllInState(InfectionState.TRACED_ELDERLY).size();
        if (noT_E > 0) {
            stats.currentTracedElderly(noT_E, location);
        }
        if (noT_Y + noT_A + noT_E > 0) {
            stats.currentTraced(noT_Y + noT_A + noT_E, location);
        }
        if (noSI_Y + noSI_A + noSI_E > 0) {
            stats.currentSevereInfectious(noSI_Y + noSI_A + noSI_E, location,outputModuleType);
        }
        if (noMI_Y + noMI_A + noMI_E + noSI_Y + noSI_A + noSI_E > 0) {
            stats.currentInfectious(noMI_Y + noMI_A + noMI_E + noSI_Y + noSI_A + noSI_E, location,outputModuleType);
        }
        if (noSI_Y_var + noSI_A_var + noSI_E_var > 0) {
            stats.currentSevereInfectiousVariant(noSI_Y_var + noSI_A_var + noSI_E_var, location,outputModuleType);
        }
        if (noMI_Y_var + noMI_A_var + noMI_E_var + noSI_Y_var + noSI_A_var + noSI_E_var > 0) {
            stats.currentInfectiousVariant(noMI_Y_var + noMI_A_var + noMI_E_var + noSI_Y_var + noSI_A_var + noSI_E_var, location,outputModuleType);
        }
        noH_Y = localPop.getAllInState(InfectionState.HOSPITALISED_YOUNG).size();
        if (noH_Y > 0) {
            stats.currentHospitalisedYoung(noH_Y, location,outputModuleType);
        }
        noH_A = localPop.getAllInState(InfectionState.HOSPITALISED_ADULT).size();
        if (noH_A > 0) {
            stats.currentHospitalisedAdult(noH_A, location,outputModuleType);
        }
        noH_E = localPop.getAllInState(InfectionState.HOSPITALISED_ELDERLY).size();
        if (noH_E > 0) {
            stats.currentHospitalisedElderly(noH_E, location,outputModuleType);
        }
        if (noH_Y + noH_A + noH_E > 0) {
            stats.currentHospitalised(noH_Y + noH_A + noH_E, location,outputModuleType);
        }
        noH_Y_var = localPop.getAllInState(InfectionState.HOSPITALISED_YOUNG_VARIANT).size();
        if (noH_Y_var > 0) {
            stats.currentHospitalisedYoungVariant(noH_Y_var, location,outputModuleType);
        }
        noH_A_var = localPop.getAllInState(InfectionState.HOSPITALISED_ADULT_VARIANT).size();
        if (noH_A_var > 0) {
            stats.currentHospitalisedAdultVariant(noH_A_var, location,outputModuleType);
        }
        noH_E_var = localPop.getAllInState(InfectionState.HOSPITALISED_ELDERLY_VARIANT).size();
        if (noH_E_var > 0) {
            stats.currentHospitalisedElderlyVariant(noH_E_var, location,outputModuleType);
        }
        if (noH_Y_var + noH_A_var + noH_E_var > 0) {
            stats.currentHospitalisedVariant(noH_Y_var + noH_A_var + noH_E_var, location,outputModuleType);
        }
        noR_Y = localPop.getAllInState(InfectionState.RECOVERED_YOUNG).size();
        if (noR_Y > 0) {
            stats.currentRecoveredYoung(noR_Y, location,outputModuleType);
        }
        noR_A = localPop.getAllInState(InfectionState.RECOVERED_ADULT).size();
        if (noR_A > 0) {
            stats.currentRecoveredAdult(noR_A, location,outputModuleType);
        }
        noR_E = localPop.getAllInState(InfectionState.RECOVERED_ELDERLY).size();
        if (noR_E > 0) {
            stats.currentRecoveredElderly(noR_E, location,outputModuleType);
        }
        if (noR_Y + noR_A + noR_E > 0) {
            stats.currentRecovered(noR_Y + noR_A + noR_E, location,outputModuleType);
        }
        noR_Y_var = localPop.getAllInState(InfectionState.RECOVERED_YOUNG_VARIANT).size();
        if (noR_Y_var > 0) {
            stats.currentRecoveredYoungVariant(noR_Y_var, location,outputModuleType);
        }
        noR_A_var = localPop.getAllInState(InfectionState.RECOVERED_ADULT_VARIANT).size();
        if (noR_A_var > 0) {
            stats.currentRecoveredAdultVariant(noR_A_var, location,outputModuleType);
        }
        noR_E_var = localPop.getAllInState(InfectionState.RECOVERED_ELDERLY_VARIANT).size();
        if (noR_E_var > 0) {
            stats.currentRecoveredElderlyVariant(noR_E_var, location,outputModuleType);
        }
        if (noR_Y_var + noR_A_var + noR_E_var > 0) {
            stats.currentRecoveredVariant(noR_Y_var + noR_A_var + noR_E_var, location,outputModuleType);
        }
        noD_Y = localPop.getAllInState(InfectionState.DEAD_YOUNG).size();
        if (noD_Y > 0) {
            stats.currentDeadYoung(noD_Y, location,outputModuleType);
        }
        noD_A = localPop.getAllInState(InfectionState.DEAD_ADULT).size();
        if (noD_A > 0) {
            stats.currentDeadAdult(noD_A, location,outputModuleType);
        }
        noD_E = localPop.getAllInState(InfectionState.DEAD_ELDERLY).size();
        if (noD_E > 0) {
            stats.currentDeadElderly(noD_E, location,outputModuleType);
        }
        if (noD_Y + noD_A + noD_E > 0) {
            stats.currentDead(noD_Y + noD_A + noD_E, location,outputModuleType);
        }
        noD_Y_var = localPop.getAllInState(InfectionState.DEAD_YOUNG_VARIANT).size();
        if (noD_Y_var > 0) {
            stats.currentDeadYoungVariant(noD_Y_var, location,outputModuleType);
        }
        noD_A_var = localPop.getAllInState(InfectionState.DEAD_ADULT_VARIANT).size();
        if (noD_A_var > 0) {
            stats.currentDeadAdultVariant(noD_A_var, location,outputModuleType);
        }
        noD_E_var = localPop.getAllInState(InfectionState.DEAD_ELDERLY_VARIANT).size();
        if (noD_E_var > 0) {
            stats.currentDeadElderlyVariant(noD_E_var, location,outputModuleType);
        }
        if (noD_Y_var + noD_A_var + noD_E_var > 0) {
            stats.currentDeadVariant(noD_Y_var + noD_A_var + noD_E_var, location,outputModuleType);
        }
        stats.currentDeadCA(noD_Y + noD_A + noD_E, location); // Actually want zeroes - otherwise mucks up metric in fit with missing days
        //  stats.currentInfectedCA(noD_Y+noD_A+noD_E,location); // Actually want zeroes - otherwise mucks up metric in fit with missing days
        totalInfectious +=  noMI_Y + noMI_A + noMI_E + noSI_Y + noSI_A + noSI_E;// + noH_Y + noH_A + noH_E;
        totalRecovered += noR_Y + noR_A + noR_E;
        totalDead += noD_Y + noD_A + noD_E;
        totalExposed += noE_Y + noE_A + noE_E;
        totalHospitalised += noH_Y + noH_A + noH_E;

        totalInfectious_variant +=  noMI_Y_var + noMI_A_var + noMI_E_var + noSI_Y_var + noSI_A_var + noSI_E_var;// + noH_Y + noH_A + noH_E;
        totalRecovered_variant += noR_Y_var + noR_A_var + noR_E_var;
        totalDead_variant += noD_Y_var + noD_A_var + noD_E_var;
        totalExposed_variant += noE_Y_var + noE_A_var + noE_E_var;
        totalHospitalised_variant += noH_Y_var + noH_A_var + noH_E_var;

        //This part for infected DZ's per CA
        if (noD_Y > 0 || noD_A > 0 || noD_E > 0) {
            int dz = hbl.getDZAreaFromOA(location);
            stats.currentNumberDZAreasWithDeadPerCouncilArea(dz, location);
        }
    }

    public int getTotalInfectious() {
        return totalInfectious;
    }

    public int getTotalInfected() {
        return totalInfected;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public int getTotalDead() {
        return totalDead;
    }

    public int getTotalPopulation() {
        return totalPopulation;
    }

    public int getTotalExposed() {
        return totalExposed;
    }

    public int getTotalHospitalised() {
        return totalHospitalised;
    }

    public int getTotalInfectious_variant() {
        return totalInfectious_variant;
    }

    public int getTotalInfected_variant() {
        return totalInfected_variant;
    }

    public int getTotalRecovered_variant() {
        return totalRecovered_variant;
    }

    public int getTotalDead_variant() {
        return totalDead_variant;
    }

    public int getTotalExposed_variant() {
        return totalExposed_variant;
    }

    public int getTotalHospitalised_variant() {
        return totalHospitalised_variant;
    }

    public int getTotalSusceptible() {
        return totalSusceptible;
    }

    public int getTotalSusceptibleVaccinated() {
        return totalSusceptibleVaccinated;
    }

    public int getTotalSusceptbileNonVaccinated() {
        return totalSusceptbileNonVaccinated;
    }
// totalPopulation = noSus + totalExposed + totalInfectious + totalRecovered - totalDead;



}
