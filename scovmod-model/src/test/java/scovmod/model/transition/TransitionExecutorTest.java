package scovmod.model.transition;

import static scovmod.model.state.infection.InfectionState.*;
import static scovmod.model.util.TestUtils.intSetOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import scovmod.model.input.config.Parameters;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.transition.infected.*;
import scovmod.model.transition.infected.variant.*;
import scovmod.model.transition.susceptible.InfectionPressure;
import scovmod.model.transition.susceptible.SusceptiblePersonTransitionExecutor;
import scovmod.test.FakeLocalPopulation;
import scovmod.model.util.math.Random;

public class TransitionExecutorTest {

    private int timeStep = 15;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int LOCATION_3 = 300;

    private final int PERSON_1 = 11;
    private final int PERSON_2 = 12;
    private final int PERSON_2a = 123;

    private final int PERSON_3 = 13;
    private final int PERSON_3a = 131;
    private final int PERSON_3b = 1311;
    private final int PERSON_3ab = 1312;
    private final int PERSON_4 = 14;
    private final int PERSON_5 = 15;
    private final int PERSON_5a = 151;
    private final int PERSON_5b = 152;
    private final int PERSON_5c = 1521;
    private final int PERSON_5d = 1522;
    private final int PERSON_5e = 1523;
    private final int PERSON_6 = 16;
    private final int PERSON_7 = 17;
    private final int PERSON_8 = 18;
    private final int PERSON_8b = 181;

    @Mock
    StateQuery sq;
    @Mock
    StateModifier sm;
    @Mock
    Random rnd;
    @Mock
    Parameters params;
    @Mock
    StateManagementFactory smf;
    @Mock
    SusceptiblePersonTransitionExecutor sate;
    @Mock
    ExposedToMildInfectiousTransitionExecutor eate;
    @Mock
    ExposedToRecoveredTransitionExecutor eare;
    @Mock
    ExposedToTracedTransitionExecutor ete;
    @Mock
    MildInfectiousToSevereTransitionExecutor mitste;
    @Mock
    MildInfectiousToRecoveredTransitionExecutor mitre;
    @Mock
    MildInfectiousToTracedTransitionExecutor mitte;
    @Mock
    SevereInfectiousToRecoveredTransitionExecutor sitrte;
    @Mock
    SevereInfectiousToHospitalisedTransitionExecutor sithte;
    @Mock
    SevereInfectiousToDeadTransitionExecutor sitdte;
    @Mock
    SevereInfectiousToTracedTransitionExecutor sittte;
    @Mock
    HospitalisedToRecoveredTransitionExecutor htrte;
    @Mock
    HospitalisedToDeadTransitionExecutor htdte;
    @Mock
    TracedToRecoveredTransitionExecutor ttrte;
    @Mock
    TracedToHospitalisedTransitionExecutor tthte;
    @Mock
    TracedToDeadTransitionExecutor ttdte;
    @Mock
    RecoveredToSusceptibleTransitionExecutor rtste;
    @Mock
    ExposedToMildInfectiousVariantTransitionExecutor veate;
    @Mock
    ExposedToRecoveredVariantTransitionExecutor veare;
    @Mock
    ExposedToTracedVariantTransitionExecutor vete;
    @Mock
    MildInfectiousToSevereVariantTransitionExecutor vmitste;
    @Mock
    MildInfectiousToRecoveredVariantTransitionExecutor vmitre;
    @Mock
    MildInfectiousToTracedVariantTransitionExecutor vmitte;
    @Mock
    SevereInfectiousFromVariantToRecoveredTransitionExecutor vsitrte;
    @Mock
    SevereInfectiousFromVariantToHospitalisedTransitionExecutor vsithte;
    @Mock
    SevereInfectiousFromVariantToDeadTransitionExecutor vsitdte;
    @Mock
    SevereInfectiousFromVariantToTracedTransitionExecutor vsittte;
    @Mock
    HospitalisedToRecoveredVariantTransitionExecutor vhtrte;
    @Mock
    HospitalisedToDeadVariantTransitionExecutor vhtdte;
    @Mock
    TracedToRecoveredFromVariantTransitionExecutor vttrte;
    @Mock
    TracedToHospitalisedFromVariantTransitionExecutor vtthte;
    @Mock
    TracedToDeadFromVariantTransitionExecutor vttdte;
    @Mock
    RecoveredFromVariantToSusceptibleTransitionExecutor vrtste;


    FakeLocalPopulation pop1, pop2, pop3;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        pop1 = new FakeLocalPopulation();
        pop1.testSet(PERSON_1, SUSCEPTIBLE);
        pop1.testSet(PERSON_2, EXPOSED_YOUNG);
        pop1.testSet(PERSON_2a, EXPOSED_YOUNG_VARIANT);
        pop1.testSet(PERSON_3, MILD_INFECTIOUS_ADULT);
        pop1.testSet(PERSON_3b, MILD_INFECTIOUS_ADULT_VARIANT);
        pop1.testSet(PERSON_3a, HOSPITALISED_ADULT);
        pop1.testSet(PERSON_3ab, HOSPITALISED_ADULT_VARIANT);

        pop2 = new FakeLocalPopulation();
        pop2.testSet(PERSON_4, SUSCEPTIBLE);
        pop2.testSet(PERSON_5, EXPOSED_ADULT);
        pop2.testSet(PERSON_5c, EXPOSED_ADULT_VARIANT);
        pop2.testSet(PERSON_5a, SEVERE_INFECTIOUS_ELDERLY);
        pop2.testSet(PERSON_5d, SEVERE_INFECTIOUS_ELDERLY_VARIANT);
        pop2.testSet(PERSON_5b, TRACED_ELDERLY);
        pop2.testSet(PERSON_5e, TRACED_ELDERLY_VARIANT);

        pop3 = new FakeLocalPopulation();
        pop3.testSet(PERSON_6, SUSCEPTIBLE);
        pop3.testSet(PERSON_7, SUSCEPTIBLE);
        pop3.testSet(PERSON_8, RECOVERED_ADULT);
        pop3.testSet(PERSON_8b, RECOVERED_ADULT_VARIANT);
    }


    @Test
    public void doLocationBasedSusceptibleTransitions() {
        InfectionPressure location1Pressure = new InfectionPressure(0, 0.1);
        InfectionPressure location2Pressure = new InfectionPressure(0, 0.15);
        InfectionPressure location3Pressure = new InfectionPressure(0, 0.45);

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        Int2ObjectMap<InfectionPressure> pressureMap= new Int2ObjectOpenHashMap<>();
        pressureMap.put(LOCATION_1, location1Pressure);
        pressureMap.put(LOCATION_2, location2Pressure);
        pressureMap.put(LOCATION_3, location3Pressure);

        instance.doSusceptibleTransitions(pressureMap);

        verify(sate).apply(PERSON_1, location1Pressure);
        verify(sate).apply(PERSON_4, location2Pressure);
        verify(sate).apply(PERSON_6, location3Pressure);
        verify(sate).apply(PERSON_7, location3Pressure);
        Mockito.verifyNoMoreInteractions(sate);
    }

    @Test
    public void doLocationBasedExposedToMildTransitions() {
        when(sq.getExposedYoungLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getExposedAdultLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getExposedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doExposedToMildTransitions();

        verify(eate).apply(PERSON_2);
        verify(eate).apply(PERSON_5);
        Mockito.verifyNoMoreInteractions(eate);
    }

    @Test
    public void doLocationBasedExposedToRecoveredTransitions() {
        when(sq.getExposedYoungLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getExposedAdultLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getExposedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doExposedToRecoveredTransitions();

        verify(eare).apply(PERSON_2);
        verify(eare).apply(PERSON_5);
        Mockito.verifyNoMoreInteractions(eare);
    }

    @Test
    public void doLocationBasedExposedToTracedTransitions() {
        when(sq.getExposedYoungLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getExposedAdultLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getExposedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doExposedToTracedTransitions();

        verify(ete).apply(PERSON_2);
        verify(ete).apply(PERSON_5);
        Mockito.verifyNoMoreInteractions(ete);
    }

    @Test
    public void doLocationBasedMildToRecoveredTransitions() {
        when(sq.getMildInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getMildInfectiousElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doMildToRecoveredTransitions();

        verify(mitre).apply(PERSON_3);
        Mockito.verifyNoMoreInteractions(mitre);
    }

    @Test
    public void doLocationBasedMildToSevereTransitions() {
        when(sq.getMildInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getMildInfectiousElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doMildToSevereTransitions();

        verify(mitste).apply(PERSON_3);
        Mockito.verifyNoMoreInteractions(mitste);
    }

    @Test
    public void doLocationBasedMildToTracedTransitions() {
        when(sq.getMildInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getMildInfectiousElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doMildToTracedTransitions();

        verify(mitte).apply(PERSON_3);
        Mockito.verifyNoMoreInteractions(mitte);
    }

    @Test
    public void doLocationBasedSevereToRecoveredTransitions() {
        when(sq.getSevereInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doSevereToRecoveredTransitions();

        verify(sitrte).apply(PERSON_5a);
        Mockito.verifyNoMoreInteractions(sitrte);
    }

    @Test
    public void doLocationBasedSevereToHospitalisedTransitions() {
        when(sq.getSevereInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doSevereToHospitalisedTransitions();

        verify(sithte).apply(PERSON_5a);
        Mockito.verifyNoMoreInteractions(sithte);
    }

    @Test
    public void doLocationBasedSevereToDeadTransitions() {
        when(sq.getSevereInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doSevereToDeadTransitions();

        verify(sitdte).apply(PERSON_5a);
        Mockito.verifyNoMoreInteractions(sitdte);
    }

    @Test
    public void doLocationBasedSevereToTracedTransitions() {
        when(sq.getSevereInfectiousYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doSevereToTracedTransitions();

        verify(sittte).apply(PERSON_5a);
        Mockito.verifyNoMoreInteractions(sittte);
    }

    @Test
    public void doLocationBasedHospitalisedToRecoveredTransitions() {
        when(sq.getHospitalisedYoungLocations()).thenReturn(intSetOf());
        when(sq.getHospitalisedAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getHospitalisedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doHospitalisedToRecoveredTransitions();

        verify(htrte).apply(PERSON_3a);
        Mockito.verifyNoMoreInteractions(htrte);
    }

    @Test
    public void doLocationBasedHospitalisedToDeadTransitions() {
        when(sq.getHospitalisedYoungLocations()).thenReturn(intSetOf());
        when(sq.getHospitalisedAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getHospitalisedElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doHospitalisedToDeadTransitions();

        verify(htdte).apply(PERSON_3a);
        Mockito.verifyNoMoreInteractions(htdte);
    }

    @Test
    public void doLocationBasedTracedToRecoveredTransitions() {
        when(sq.getTracedYoungLocations()).thenReturn(intSetOf());
        when(sq.getTracedAdultLocations()).thenReturn(intSetOf());
        when(sq.getTracedElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doTracedToRecoveredTransitions();

        verify(ttrte).apply(PERSON_5b);
        Mockito.verifyNoMoreInteractions(ttrte);
    }

    @Test
    public void doLocationBasedTracedToHospitalisedTransitions() {
        when(sq.getTracedYoungLocations()).thenReturn(intSetOf());
        when(sq.getTracedAdultLocations()).thenReturn(intSetOf());
        when(sq.getTracedElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doTracedToHospitalisedTransitions();

        verify(tthte).apply(PERSON_5b);
        Mockito.verifyNoMoreInteractions(tthte);
    }

    @Test
    public void doLocationBasedTracedToDeadTransitions() {
        when(sq.getTracedYoungLocations()).thenReturn(intSetOf());
        when(sq.getTracedAdultLocations()).thenReturn(intSetOf());
        when(sq.getTracedElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doTracedToDeadTransitions();

        verify(ttdte).apply(PERSON_5b);
        Mockito.verifyNoMoreInteractions(ttdte);
    }

    @Test
    public void doLocationBasedRecovereToSusceptibleTransitions() {
        when(sq.getRecoveredYoungLocations()).thenReturn(intSetOf());
        when(sq.getRecoveredAdultLocations()).thenReturn(intSetOf(LOCATION_3));
        when(sq.getRecoveredElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doRecoveredToSusceptibleTransitions();

        verify(rtste).apply(PERSON_8);
        Mockito.verifyNoMoreInteractions(rtste);
    }


    @Test
    public void doLocationBasedExposedToMildFromVariantTransitions() {
        when(sq.getExposedToVariantYoungLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getExposedToVariantAdultLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getExposedToVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doExposedToMildFromVariantTransitions();

        verify(veate).apply(PERSON_2a);
        verify(veate).apply(PERSON_5c);
        Mockito.verifyNoMoreInteractions(veate);
    }

    @Test
    public void doLocationBasedExposedToRecoveredFromVariantTransitions() {
        when(sq.getExposedToVariantYoungLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getExposedToVariantAdultLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getExposedToVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doExposedToRecoveredFromVariantTransitions();

        verify(veare).apply(PERSON_2a);
        verify(veare).apply(PERSON_5c);
        Mockito.verifyNoMoreInteractions(veare);
    }

    @Test
    public void doLocationBasedExposedToTracedFromVariantTransitions() {
        when(sq.getExposedToVariantYoungLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getExposedToVariantAdultLocations()).thenReturn(intSetOf(LOCATION_2));
        when(sq.getExposedToVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doExposedToTracedFromVariantTransitions();

        verify(vete).apply(PERSON_2a);
        verify(vete).apply(PERSON_5c);
        Mockito.verifyNoMoreInteractions(vete);
    }

    @Test
    public void doLocationBasedMildToRecoveredFromVariantTransitions() {
        when(sq.getMildInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousFromVariantAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getMildInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doMildToRecoveredFromVariantTransitions();

        verify(vmitre).apply(PERSON_3b);
        Mockito.verifyNoMoreInteractions(vmitre);
    }

    @Test
    public void doLocationBasedMildToSevereFromVariantTransitions() {
        when(sq.getMildInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousFromVariantAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getMildInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doMildToSevereFromVariantTransitions();

        verify(vmitste).apply(PERSON_3b);
        Mockito.verifyNoMoreInteractions(vmitste);
    }

    @Test
    public void doLocationBasedMildToTracedFromVariantTransitions() {
        when(sq.getMildInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousFromVariantAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getMildInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doMildToTracedFromVariantTransitions();

        verify(vmitte).apply(PERSON_3b);
        Mockito.verifyNoMoreInteractions(vmitte);
    }

    @Test
    public void doLocationBasedSevereToRecoveredFromVariantTransitions() {
        when(sq.getSevereInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doSevereToRecoveredFromVariantTransitions();

        verify(vsitrte).apply(PERSON_5d);
        Mockito.verifyNoMoreInteractions(vsitrte);
    }

    @Test
    public void doLocationBasedSevereToHospitalisedFromVariantTransitions() {
        when(sq.getSevereInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doSevereToHospitalisedFromVariantTransitions();

        verify(vsithte).apply(PERSON_5d);
        Mockito.verifyNoMoreInteractions(vsithte);
    }

    @Test
    public void doLocationBasedSevereToDeadFromVariantTransitions() {
        when(sq.getSevereInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doSevereToDeadFromVariantTransitions();

        verify(vsitdte).apply(PERSON_5d);
        Mockito.verifyNoMoreInteractions(vsitdte);
    }

    @Test
    public void doLocationBasedSevereToTracedFromVariantTransitions() {
        when(sq.getSevereInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doSevereToTracedFromVariantTransitions();

        verify(vsittte).apply(PERSON_5d);
        Mockito.verifyNoMoreInteractions(vsittte);
    }

    @Test
    public void doLocationBasedHospitalisedToRecoveredFromVariantTransitions() {
        when(sq.getHospitalisedFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getHospitalisedFromVariantAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getHospitalisedFromVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doHospitalisedToRecoveredFromVariantTransitions();

        verify(vhtrte).apply(PERSON_3ab);
        Mockito.verifyNoMoreInteractions(vhtrte);
    }

    @Test
    public void doLocationBasedHospitalisedToDeadFromVariantTransitions() {
        when(sq.getHospitalisedFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getHospitalisedFromVariantAdultLocations()).thenReturn(intSetOf(LOCATION_1));
        when(sq.getHospitalisedFromVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doHospitalisedToDeadFromVariantTransitions();

        verify(vhtdte).apply(PERSON_3ab);
        Mockito.verifyNoMoreInteractions(vhtdte);
    }

    @Test
    public void doLocationBasedTracedToRecoveredFromVariantTransitions() {
        when(sq.getTracedYoungFromVariantLocations()).thenReturn(intSetOf());
        when(sq.getTracedAdultFromVariantLocations()).thenReturn(intSetOf());
        when(sq.getTracedElderlyFromVariantLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doTracedToRecoveredFromVariantTransitions();

        verify(vttrte).apply(PERSON_5e);
        Mockito.verifyNoMoreInteractions(vttrte);
    }

    @Test
    public void doLocationBasedTracedToHospitalisedFromVariantTransitions() {
        when(sq.getTracedYoungFromVariantLocations()).thenReturn(intSetOf());
        when(sq.getTracedAdultFromVariantLocations()).thenReturn(intSetOf());
        when(sq.getTracedElderlyFromVariantLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doTracedToHospitalisedFromVariantTransitions();

        verify(vtthte).apply(PERSON_5e);
        Mockito.verifyNoMoreInteractions(vtthte);
    }

    @Test
    public void doLocationBasedTracedToDeadFromVariantTransitions() {
        when(sq.getTracedYoungFromVariantLocations()).thenReturn(intSetOf());
        when(sq.getTracedAdultFromVariantLocations()).thenReturn(intSetOf());
        when(sq.getTracedElderlyFromVariantLocations()).thenReturn(intSetOf(LOCATION_2));

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doTracedToDeadFromVariantTransitions();

        verify(vttdte).apply(PERSON_5e);
        Mockito.verifyNoMoreInteractions(vttdte);
    }

    @Test
    public void doLocationBasedRecovereToSusceptibleFromVariantTransitions() {
        when(sq.getRecoveredFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getRecoveredFromVariantAdultLocations()).thenReturn(intSetOf(LOCATION_3));
        when(sq.getRecoveredFromVariantElderlyLocations()).thenReturn(intSetOf());

        when(sq.getCopyOfLocalPopulation(LOCATION_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(LOCATION_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(LOCATION_3)).thenReturn(pop3);

        when(smf.getStateQuery()).thenReturn(sq);
        when(smf.getStateModifier()).thenReturn(sm);

        TransitionExecutor instance = new TransitionExecutor( smf, rnd, params, sate, eate, eare, ete, mitste, mitre, mitte, sitrte, sithte, sitdte, sittte, htrte, htdte, ttrte, tthte, ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);

        instance.doRecoveredToSusceptibleFromVariantTransitions();

        verify(vrtste).apply(PERSON_8b);
        Mockito.verifyNoMoreInteractions(vrtste);
    }

}
