package scovmod.model.transition.infected.variant;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.lockdown.RegionalInfectionsTracker;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.transition.infected.ExposedToMildInfectiousTransitionExecutor;
import scovmod.model.util.math.Random;

import static org.mockito.Mockito.*;

public class ExposedToMildInfectiousVariantTransitionExecutorTest {

    private static int PERSON_1 = 100;
    private static int LOCATION_1 = 1;
    private static int TIMESTEP = 1;
    private static double E_TO_MI_RATE = 0.3;

    @Mock
    Random rnd;
    @Mock
    StateModifier sm;
    @Mock
    CovidVariantParameters params;
    @Mock
    StartLocationsAndAgeClasses slaac;
    @Mock
    StatisticsCollector stats;
    @Mock
    StateQuery sq;
    @Mock
    RegionalInfectionsTracker rit;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void youngPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.geteToMI_YoungRate_covidVariant()).thenReturn(E_TO_MI_RATE);
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);

        ExposedToMildInfectiousVariantTransitionExecutor instance = new ExposedToMildInfectiousVariantTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, stats, sq, rit);

        when(rnd.nextPoissonReturnsOneOrMore(E_TO_MI_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT);
        verify(stats).newMildInfectiousVariant(LOCATION_1,PERSON_1);
        verifyNoMoreInteractions(sm);
        verifyNoMoreInteractions(stats);
        verifyNoMoreInteractions(rit);
    }

    @Test
    public void adultPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ADULT);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.geteToMI_AdultRate_covidVariant()).thenReturn(E_TO_MI_RATE);
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);

        ExposedToMildInfectiousVariantTransitionExecutor instance = new ExposedToMildInfectiousVariantTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, stats, sq, rit);

        when(rnd.nextPoissonReturnsOneOrMore(E_TO_MI_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.MILD_INFECTIOUS_ADULT_VARIANT);
        verify(stats).newMildInfectiousVariant(LOCATION_1,PERSON_1);
        verifyNoMoreInteractions(sm);
        verifyNoMoreInteractions(stats);
        verifyNoMoreInteractions(rit);
    }

    @Test
    public void elderlyPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.geteToMI_ElderlyRate_covidVariant()).thenReturn(E_TO_MI_RATE);
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);

        ExposedToMildInfectiousVariantTransitionExecutor instance = new ExposedToMildInfectiousVariantTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, stats, sq, rit);

        when(rnd.nextPoissonReturnsOneOrMore(E_TO_MI_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.MILD_INFECTIOUS_ELDERLY_VARIANT);
        verify(stats).newMildInfectiousVariant(LOCATION_1,PERSON_1);
        verifyNoMoreInteractions(sm);
        verifyNoMoreInteractions(stats);
        verifyNoMoreInteractions(rit);
    }

}
