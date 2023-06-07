package scovmod.model;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.lockdown.LockdownTriggerManager;
import scovmod.model.lockdown.PopulationSizePerAreaLookup;
import scovmod.model.lockdown.RegionalInfectionsTracker;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.MovementEventManager;
import scovmod.model.movements.MovementStepper;
import scovmod.model.output.*;
import scovmod.model.seeding.recovered.InitialiseToRecoveredManager;
import scovmod.model.seeding.reseeding.ReSeedManager;
import scovmod.model.seeding.variant.VariantSeedManager;
import scovmod.model.setup.Initialiser;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.time.TaskTimeManager;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;
import scovmod.model.transition.TransitionManager;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.vaccination.EfficacyProtection;
import scovmod.model.vaccination.VaccinationManager;

import static scovmod.model.input.config.OutputFrequency.DAILY;
import static scovmod.model.input.config.OutputFrequency.WEEKLY;
import static scovmod.model.state.infection.InfectionState.*;

public class Model {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String modelId;
    private final Initialiser initialiser;
    private final ConfigParameters config;
    private final MovementStepper movements;
    private final TimeManager time;
    private final StateQuery stateQuery;
    private final MovementEventManager mover;
    private final TransitionManager transitionManager;
    private final StatisticsCollector stats;
    private final TimeConversions tcv;
    private final TaskTimeManager ttm;
    private final HealthBoardLookup hbl;
    private final Int2IntMap laLookupMap;
    private final ReSeedManager rm;
    private final VariantSeedManager vsm;
    private final LockdownTriggerManager ltm;
    private final RegionalInfectionsTracker rit;
    private final PopulationSizePerAreaLookup ppsa;
    private final VaccinationManager vm;
    private final StartLocationsAndAgeClasses slaac;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;
    private ModelReportManager mrm;
    private OutputModuleType outputModule;

    public Model(
            String modelId,
            Initialiser initialiser,
            ConfigParameters config,
            MovementStepper movements,
            TimeManager time,
            StateQuery stateQuery,
            MovementEventManager mover,
            TransitionManager transitionManager,
            StatisticsCollector stats,
            TimeConversions tcv,
            TaskTimeManager ttm,
            HealthBoardLookup hbl,
            ReSeedManager rm,
            VariantSeedManager vsm,
            LockdownTriggerManager ltm,
            RegionalInfectionsTracker rit,
            PopulationSizePerAreaLookup ppsa,
            VaccinationManager vm,
            StartLocationsAndAgeClasses slaac,
            ModelReportManager mrm,
            OutputModuleType outputModule
            ) {
        this.modelId = modelId;
        this.initialiser = initialiser;
        this.config = config;
        this.movements = movements;
        this.time = time;
        this.stateQuery = stateQuery;
        this.mover = mover;
        this.transitionManager = transitionManager;
        this.stats = stats;
        this.tcv = tcv;
        this.ttm = ttm;
        laLookupMap = hbl.getLALookupMap();
        this.hbl = hbl;
        this.rm = rm;
        this.vsm = vsm;
        this.ltm = ltm;
        this.rit = rit;
        this.ppsa = ppsa;
        this.vm = vm;
        this.slaac = slaac;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
        this.mrm = mrm;
        this.outputModule = outputModule;
    }

    public IResult run() {

        //Initialisation
        initialiser.seedPeople(config, movements.getTimeStepSize());
        // Set up map for population per CA
        ppsa.initialisePopSizePerAreas();
        // Initialise historic vaccinations - i.e those that should have happened before model start time.
        vm.initialiseHistoricVaccinations();
        while (time.getTimeStep() < config.getLastTimeStep()) {
            final long startTimeStep = System.currentTimeMillis();

            if (log.isDebugEnabled()) {
                log.debug("There are currently " + stateQuery.getAllInfectiousLocations().size()
                        + " mild or severe infectious communities in time step " + time.getTimeStep());
                log.debug("There are currently " + stateQuery.getAllHospitalisedLocations().size()
                        + " communities with hospitalised in time step " + time.getTimeStep());
            }

            //Get movements for this timestep
            Set<LocationIncomingPersons> personsMoving = movements.getNextTimeStepMovements().getMovements();

            //Process movements for this timestep
            final long startTimeMoves = System.currentTimeMillis();
            IntSet allExposedLocations = stateQuery.getAllExposedLocations();
            IntSet allMildInfectiousLocations = stateQuery.getAllMildInfectiousLocations();
            IntSet allSevereInfectiousLocations = stateQuery.getAllSevereInfectiousLocations();

            IntSet allExposedVariantLocations = stateQuery.getAllExposedVariantLocations();
            IntSet allMildInfectiousVariantLocations = stateQuery.getAllMildInfectiousFromVariantLocations();
            IntSet allSevereInfectiousVariantLocations = stateQuery.getAllSevereInfectiousFromVariantLocations();

            mover.doMovements(
                    personsMoving,
                    allExposedLocations,
                    allMildInfectiousLocations,
                    allSevereInfectiousLocations,
                    allExposedVariantLocations,
                    allMildInfectiousVariantLocations,
                    allSevereInfectiousVariantLocations);
            ;
            final long endTimeMoves = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("Total execution time for executing movements: " + (double) (endTimeMoves - startTimeMoves) / 1000);
            }

            //Process disease transition movements, including pressure from 'visiting' people
            final long startTimePeopleTransitions = System.currentTimeMillis();
            transitionManager.doTransitions(personsMoving);
            final long endTimePeopleTransitions = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("Total execution time for processing people state transitions: " + (double) (endTimePeopleTransitions - startTimePeopleTransitions) / 1000);
            }

            final long startTimeUpdateLockdownStatus= System.currentTimeMillis();
            //ltm.updateAreaLockdownStatuses();
            final long endTimeUpdateLockdownStatus = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("Total execution time for processing updates of lockdown statuses: " + (double) (endTimeUpdateLockdownStatus - startTimeUpdateLockdownStatus) / 1000);
            }


            final long startTimeSeedVariant = System.currentTimeMillis();
            vsm.SeedVariantInfections();
            final long endTimeSeedVariant = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("Total execution time for processing seeding of variants: " + (double) (endTimeSeedVariant - startTimeSeedVariant) / 1000);
            }

            final long startTimeVaccinations =  System.currentTimeMillis();
            vm.vaccinatePeoplePerTimestep();
            final long endTimeVaccinations = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("Total execution time for processing vaccinations: " + (double) (endTimeVaccinations - startTimeVaccinations) / 1000);
            }

            final long endTimeStep = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("******* Total execution time for time step: " + (double) (endTimeStep - startTimeStep) / 1000);
            }
            int numberTimeSteps = 0;
            if(config.getOutputFrequency()== DAILY) {numberTimeSteps = 2;}
            else if (config.getOutputFrequency()== WEEKLY) {numberTimeSteps=14;}
            else {numberTimeSteps=14;}
            if ( (time.getTimeStep() - config.getFirstTimeStep()) % numberTimeSteps == 0 ) totalStatesReporting(); //NOTE - Very Important: Only actually call every 14th time step to increment daily total.

            time.advanceOneStep();
            //stats.newTimeStep();
        }
        return stats.buildResult();
    }

    private void totalStatesReporting() { //TODO split this off into its own class as it has become monstrous
        mrm.updateForTimestep(outputModule);
    }

    public String getModelId() {
        return modelId;
    }
}
