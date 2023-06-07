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
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.transition.TracingRates;
import scovmod.model.transition.infected.SevereInfectiousToTracedTransitionExecutor;
import scovmod.model.util.math.Random;

import static org.mockito.Mockito.*;

public class SevereInfectiousFromVariantToTracedTransitionExecutorTest {

    private static final int LOCATION_1 = 1000;
    private static int PERSON_1 = 100;
    private static int TIMESTEP = 1;
    private static double SI_TO_T_RATE = 0.3;

    @Mock
    Random rnd;
    @Mock
    StateModifier sm;
    @Mock
    CovidVariantParameters params;
    @Mock
    StartLocationsAndAgeClasses slaac;
    @Mock
    TracingRates tr;
    @Mock
    StateQuery sq;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void youngPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getSiToT_YoungRate_covidVariant()).thenReturn(SI_TO_T_RATE);
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);
        when(tr.getTracingRate(SI_TO_T_RATE,LOCATION_1)).thenReturn(SI_TO_T_RATE);

        SevereInfectiousFromVariantToTracedTransitionExecutor instance = new SevereInfectiousFromVariantToTracedTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, tr, sq);

        when(rnd.nextPoissonReturnsOneOrMore(SI_TO_T_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.TRACED_YOUNG_VARIANT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void adultPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ADULT);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getSiToT_AdultRate_covidVariant()).thenReturn(SI_TO_T_RATE);
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);
        when(tr.getTracingRate(SI_TO_T_RATE,LOCATION_1)).thenReturn(SI_TO_T_RATE);

        SevereInfectiousFromVariantToTracedTransitionExecutor instance = new SevereInfectiousFromVariantToTracedTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, tr, sq);

        when(rnd.nextPoissonReturnsOneOrMore(SI_TO_T_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.TRACED_ADULT_VARIANT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void elderlyPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getSiToT_ElderlyRate_covidVariant()).thenReturn(SI_TO_T_RATE);
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);
        when(tr.getTracingRate(SI_TO_T_RATE,LOCATION_1)).thenReturn(SI_TO_T_RATE);

        SevereInfectiousFromVariantToTracedTransitionExecutor instance = new SevereInfectiousFromVariantToTracedTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, tr, sq);

        when(rnd.nextPoissonReturnsOneOrMore(SI_TO_T_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.TRACED_ELDERLY_VARIANT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void elderlyPerson_CAN_Transition_inLocalLockdown(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getSiToT_ElderlyRate_covidVariant()).thenReturn(SI_TO_T_RATE);
        when(sq.getPersonLocation(PERSON_1)).thenReturn(LOCATION_1);
        when(tr.getTracingRate(SI_TO_T_RATE,LOCATION_1)).thenReturn(SI_TO_T_RATE*2);

        SevereInfectiousFromVariantToTracedTransitionExecutor instance = new SevereInfectiousFromVariantToTracedTransitionExecutor(sm, rnd, TIMESTEP, params,slaac, tr, sq);

        when(rnd.nextPoissonReturnsOneOrMore(SI_TO_T_RATE *2* TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.TRACED_ELDERLY_VARIANT);
        verifyNoMoreInteractions(sm);
    }

}
