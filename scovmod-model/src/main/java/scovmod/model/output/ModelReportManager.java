package scovmod.model.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.lockdown.LockdownTriggerManager;
import scovmod.model.lockdown.RegionalInfectionsTracker;
import scovmod.model.state.StateQuery;
import scovmod.model.wastewater.WasteWaterSignalCalculator;

public class ModelReportManager {

    private StandardStateReport ssr;
    private VaccinatedAndSusceptibleStateReport  vasr;
    private final StatisticsCollector stats;
    private final StateQuery stateQuery;
    private final LockdownTriggerManager ltm;
    private final RegionalInfectionsTracker rit;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final WasteWaterSignalCalculator wwsc;

    public ModelReportManager(
            StandardStateReport ssr,
            VaccinatedAndSusceptibleStateReport vasr,
            StatisticsCollector stats,
            StateQuery stateQuery,
            LockdownTriggerManager ltm,
            RegionalInfectionsTracker rit, WasteWaterSignalCalculator wwsc) {
        this.ssr = ssr;
        this.vasr = vasr;
        this.stats = stats;
        this.stateQuery = stateQuery;
        this.ltm = ltm;
        this.rit = rit;
        this.wwsc = wwsc;
    }

    public void updateForTimestep(OutputModuleType outputModuleType){
        int totalInfectious = 0;
        int totalInfected = 0;
        int totalRecovered = 0;
        int totalDead = 0;
        int totalPopulation = 0;
        int totalExposed = 0;
        int totalHospitalised = 0;
        int totalInfectious_variant = 0;
        int totalInfected_variant = 0;
        int totalRecovered_variant = 0;
        int totalDead_variant = 0;
        int totalExposed_variant = 0;
        int totalHospitalised_variant = 0;
        int totalSusceptible = 0;
        int totalSusceptibleVaccinated = 0;
        int totalSusceptbileNonVaccinated = 0;
        stats.aggregateMildInfectiousEvents();
        stats.aggregateMildInfectiousEvents_variant();
        wwsc.aggregateTotalRNASignalPerSite();
        wwsc.resetTotalSignalperSiteMap();
        ltm.updateAreaLockdownStatuses();
        rit.resetMildInfectiousEvents();
        for (int location : stateQuery.getAllActiveLocationIds()) {
            ssr.updateReport(location,outputModuleType);
            vasr.updateReport(location,outputModuleType);
            totalInfectious += ssr.getTotalInfectious();
            totalInfected +=ssr.getTotalInfected();
            totalRecovered += ssr.getTotalRecovered();
            totalDead += ssr.getTotalDead();
            totalPopulation += ssr.getTotalPopulation();
            totalExposed += ssr.getTotalExposed();
            totalHospitalised += ssr.getTotalHospitalised();
            totalInfectious_variant += ssr.getTotalInfectious_variant();
            totalInfected_variant += ssr.getTotalInfected_variant();
            totalRecovered_variant += ssr.getTotalRecovered_variant();
            totalDead_variant +=ssr.getTotalDead_variant();
            totalExposed_variant += ssr.getTotalExposed_variant();
            totalHospitalised_variant += ssr.getTotalHospitalised_variant();
            totalSusceptible += ssr.getTotalSusceptible();
            totalSusceptibleVaccinated += ssr.getTotalSusceptibleVaccinated();
            totalSusceptbileNonVaccinated += ssr.getTotalSusceptbileNonVaccinated();
        }
                  log.debug("Number susceptible: " + totalSusceptible);
          log.debug("Number susceptible vaccinated: " + totalSusceptibleVaccinated);
          log.debug("Number susceptible non vaccianted: " + totalSusceptbileNonVaccinated);
        log.debug("Number exposed: " + totalExposed);
        log.debug("Number exposed variant: " + totalExposed_variant);
        /*log.debug("Number number exposed young : " + noE_Y);
        log.debug("Number number exposed adult : " + noE_A);
        log.debug("Number number exposed elderly : " + noE_E);*/
        log.debug("Number infectious: " + totalInfectious);
        log.debug("Number infectious variant: " + totalInfectious_variant);
       /* log.debug("Number number mild young : " + noMI_Y);
        log.debug("Number number mild adult : " + noMI_A);
        log.debug("Number number mild elderly : " + noMI_E);
        log.debug("Number number severe young : " + noSI_Y);
        log.debug("Number number severe adult : " + noSI_A);
        log.debug("Number number severe elderly : " + noSI_E);*/
        log.debug("Number recovered: " + totalRecovered);
        log.debug("Number recovered variant: " + totalRecovered_variant);
        /*log.debug("Number number recovered young : " + noR_Y);
        log.debug("Number number recovered adult : " + noR_A);
        log.debug("Number number recovered elderly : " + noR_E);*/
        log.debug("Number hospitalised: " + totalHospitalised);
        log.debug("Number hospitalised variant: " + totalHospitalised_variant);
        /*log.debug("Number number hospitalised young : " + noH_Y);
        log.debug("Number number hospitalised adult : " + noH_A);
        log.debug("Number number hospitalised elderly : " + noH_E);*/
        log.debug("Number Dead: " + totalDead);
        log.debug("Number Dead variant: " + totalDead_variant);
       /* log.debug("Number number dead young : " + noD_Y);
        log.debug("Number number dead adult : " + noD_A);
        log.debug("Number number dead elderly : " + noD_E);*/
        //log.debug("Total population: " + totalPopulation);
    }
}
