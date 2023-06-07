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
import scovmod.model.state.infection.InfectionState;
import scovmod.model.transition.infected.RecoveredToSusceptibleTransitionExecutor;
import scovmod.model.util.math.Random;

import static org.mockito.Mockito.*;

public class RecoveredFromVariantToSuscepibleTransitionExecutorTest {

    private static int PERSON_1 = 100;
    private static int TIMESTEP = 1;
    private static double R_TO_S_RATE_YOUNG = 0.3;
    private static double R_TO_S_RATE_ADULT = 0.4;
    private static double R_TO_S_RATE_ELDERLY= 0.5;

    @Mock
    Random rnd;
    @Mock
    StateModifier sm;
    @Mock
    CovidVariantParameters params;
    @Mock
    StartLocationsAndAgeClasses slaac;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void youngPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getrToS_youngRate_covidVariant()).thenReturn(R_TO_S_RATE_YOUNG);

        RecoveredFromVariantToSusceptibleTransitionExecutor instance = new RecoveredFromVariantToSusceptibleTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(R_TO_S_RATE_YOUNG * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.SUSCEPTIBLE);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void adultPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ADULT);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getrToS_adultRate_covidVariant()).thenReturn(R_TO_S_RATE_ADULT);

        RecoveredFromVariantToSusceptibleTransitionExecutor instance = new RecoveredFromVariantToSusceptibleTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(R_TO_S_RATE_ADULT * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.SUSCEPTIBLE);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void elderlyPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getrToS_elderlyRate_covidVariant()).thenReturn(R_TO_S_RATE_ELDERLY);

        RecoveredFromVariantToSusceptibleTransitionExecutor instance = new RecoveredFromVariantToSusceptibleTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(R_TO_S_RATE_ELDERLY * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.SUSCEPTIBLE);
        verifyNoMoreInteractions(sm);
    }

}
