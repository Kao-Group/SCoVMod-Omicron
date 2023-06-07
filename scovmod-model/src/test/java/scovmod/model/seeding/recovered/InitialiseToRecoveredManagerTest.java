package scovmod.model.seeding.recovered;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.state.infection.InfectionState;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.listOf;

public class InitialiseToRecoveredManagerTest {

    @Mock
    StartLocationsAndAgeClasses sl;
    @Mock
    StateModifier sm;
    @Mock
    NationalRecoveredSamplerFactory nrsf;
    @Mock
    NationalRecoveredSampler nrs;

    private final int NUM_SEEDS = 2;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    private final int PERSON_4 = 4;

   private Int2ObjectMap<IntSet> peopleByLocation;

    private InitialiseToRecoveredManager instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_2, AgeClass.ADULT);
        when(sl.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        peopleByLocation = new Int2ObjectOpenHashMap<>();
        peopleByLocation.put(LOCATION_1,intSetOf(PERSON_1,PERSON_2));
        peopleByLocation.put(LOCATION_2,intSetOf(PERSON_3,PERSON_4));
        when(nrsf.build(peopleByLocation)).thenReturn(nrs);
        instance = new InitialiseToRecoveredManager(nrsf,sm,sl);
    }
    //TODO: Do more edge cases
    @Test
    public void InitialiseRecovered() {
        when(nrs.getSampledPeople()).thenReturn(intSetOf(PERSON_1,PERSON_2));
        instance.initialiseRecoveredPeople(peopleByLocation);
        verify(sm).updateInfectionState(PERSON_1,InfectionState.RECOVERED_YOUNG);
        verify(sm).updateInfectionState(PERSON_2,InfectionState.RECOVERED_ADULT);
    }
}
