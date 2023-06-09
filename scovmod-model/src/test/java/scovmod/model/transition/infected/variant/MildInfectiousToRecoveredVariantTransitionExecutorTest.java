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
import scovmod.model.transition.infected.MildInfectiousToRecoveredTransitionExecutor;
import scovmod.model.util.math.Random;

import static org.mockito.Mockito.*;

public class MildInfectiousToRecoveredVariantTransitionExecutorTest {

    private static int PERSON_1 = 100;
    private static int TIMESTEP = 1;
    private static double MI_TO_R_RATE = 0.3;

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
        when(params.getMiToR_YoungRate_covidVariant()).thenReturn(MI_TO_R_RATE);

        MildInfectiousToRecoveredVariantTransitionExecutor instance = new MildInfectiousToRecoveredVariantTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(MI_TO_R_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.RECOVERED_YOUNG_VARIANT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void adultPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ADULT);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getMiToR_AdultRate_covidVariant()).thenReturn(MI_TO_R_RATE);

        MildInfectiousToRecoveredVariantTransitionExecutor instance = new MildInfectiousToRecoveredVariantTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(MI_TO_R_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.RECOVERED_ADULT_VARIANT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void elderlyPerson_CAN_Transition(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(params.getMiToR_ElderlyRate_covidVariant()).thenReturn(MI_TO_R_RATE);

        MildInfectiousToRecoveredVariantTransitionExecutor instance = new MildInfectiousToRecoveredVariantTransitionExecutor(sm, rnd, TIMESTEP, params,slaac);

        when(rnd.nextPoissonReturnsOneOrMore(MI_TO_R_RATE * TIMESTEP))
                .thenReturn(true, false);

        instance.apply(PERSON_1);

        verify(sm).updateInfectionState(PERSON_1, InfectionState.RECOVERED_ELDERLY_VARIANT);
        verifyNoMoreInteractions(sm);
    }

}
