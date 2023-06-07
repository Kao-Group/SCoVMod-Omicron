/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model;



import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import org.junit.Before;

import org.junit.Test;
import org.mockito.InOrder;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.lockdown.LockdownTriggerManager;
import scovmod.model.lockdown.PopulationSizePerAreaLookup;
import scovmod.model.lockdown.RegionalInfectionsTracker;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.MovementEventManager;
import scovmod.model.movements.MovementStepper;
import scovmod.model.movements.TimeStepMovements;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.output.ModelReportManager;
import scovmod.model.output.OutputModuleType;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.seeding.recovered.InitialiseToRecoveredManager;
import scovmod.model.seeding.reseeding.ReSeedManager;
import scovmod.model.seeding.variant.VariantSeedManager;
import scovmod.model.setup.Initialiser;
import scovmod.model.state.StateQuery;
import scovmod.model.time.TaskTimeManager;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;
import scovmod.model.transition.TransitionManager;
import scovmod.model.vaccination.VaccinationManager;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scovmod.model.output.OutputModuleType.OA;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.setOf;

public class ModelTest {

    private int STOCHASTIC_INCREMENT = 15;
    private long CURRENT_TIMESTEP = 1100;
    private long LAST_TIMESTEP = 1101;
    private LocalDate CURRENT_DATE = LocalDate.of(2003, Month.JANUARY, 1);
    private LocalDate LAST_DATE = LocalDate.of(2008, Month.JANUARY, 1);
    private LocalDate LAST_DATE_WITHLOOP = LocalDate.of(2013, Month.JANUARY, 1);
    private static final String MODEL_ID = "m1";

    private int LOCATION = 100;

    private OutputModuleType OUTPUT_MODULE_TYPE = OA;

    @Before
    public void setup() {
    }

    @Test
    public void run_noScenario() {
        TimeManager timeMgr = mock(TimeManager.class);
        Initialiser initialiser = mock(Initialiser.class);
        ConfigParameters configParams = mock(ConfigParameters.class);
        MovementEventManager mover = mock(MovementEventManager.class);
        MovementStepper movStepper = mock(MovementStepper.class);
        StatisticsCollector stats = mock(StatisticsCollector.class);
        TimeConversions tcv = mock(TimeConversions.class);
        TransitionManager tm = mock(TransitionManager.class);
        StateQuery sq = mock(StateQuery.class);
        HealthBoardLookup hbl = mock(HealthBoardLookup.class);
        ReSeedManager rm = mock(ReSeedManager.class);
        VariantSeedManager vsm = mock(VariantSeedManager.class);
        LockdownTriggerManager ltm = mock(LockdownTriggerManager.class);
        RegionalInfectionsTracker rit = mock(RegionalInfectionsTracker.class);
        PopulationSizePerAreaLookup pspa = mock(PopulationSizePerAreaLookup.class);
        VaccinationManager vm = mock(VaccinationManager.class);
        StartLocationsAndAgeClasses slaac = mock(StartLocationsAndAgeClasses.class);
        ModelReportManager mrm = mock(ModelReportManager.class);

        TaskTimeManager ttm = mock(TaskTimeManager.class);

        when(tcv.toTimeStepStartDate(CURRENT_TIMESTEP)).thenReturn(CURRENT_DATE);
        when(tcv.toTimeStepStartDate(LAST_TIMESTEP)).thenReturn(LAST_DATE);
        when(timeMgr.getTimeStep()).thenReturn(CURRENT_TIMESTEP, LAST_TIMESTEP);
        when(configParams.getFirstTimeStep()).thenReturn(CURRENT_TIMESTEP);
        when(configParams.getLastTimeStep()).thenReturn(LAST_TIMESTEP);
        when(movStepper.getTimeStepSize()).thenReturn(STOCHASTIC_INCREMENT);
        when(sq.getAllActiveLocationIds()).thenReturn(intSetOf(LOCATION));

        Set<LocationIncomingPersons> ias = setOf(
                new LocationIncomingPersons(LOCATION, intSetOf(10001), null));

        TimeStepMovements tsm1 = mock(TimeStepMovements.class);
        when(movStepper.getNextTimeStepMovements()).thenReturn(tsm1);
        when(tsm1.getMovements()).thenReturn(ias);

        Model instance = new Model(
                MODEL_ID,
                initialiser,
                configParams,
                movStepper,
                timeMgr,
                sq,
                mover,
                tm,
                stats,
                tcv,
                ttm,
                hbl,
                rm,
                vsm,
                ltm,
                rit,
                pspa,
                vm,
                slaac,
                mrm,
                OUTPUT_MODULE_TYPE);
        instance.run();

        InOrder inOrder = inOrder(initialiser, pspa, mover, mover, tm, rm ,vsm,stats,vm);

        inOrder.verify(initialiser).seedPeople(configParams, STOCHASTIC_INCREMENT);
        inOrder.verify(pspa).initialisePopSizePerAreas();
        inOrder.verify(vm).initialiseHistoricVaccinations();
        inOrder.verify(mover).doMovements(ias,null,null,null,null,null,null);
        inOrder.verify(tm).doTransitions(ias);
       // inOrder.verify(rm).reseedPeopleInfections();
        inOrder.verify(vsm).SeedVariantInfections();
        inOrder.verify(vm).vaccinatePeoplePerTimestep();
    }
}
