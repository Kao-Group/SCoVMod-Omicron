package scovmod.model.seeding.reseeding;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.RecoveredSeeds;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;
import scovmod.model.util.math.Random;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.listOf;

public class ReSeedManagerTest {

    @Mock
    StartLocationsAndAgeClasses sl;
    @Mock
    StateQuery sq;
    @Mock
    StateModifier sm;
    @Mock
    RecoveredSeeds str;
    @Mock
    Random rand;
    @Mock
    TimeManager tm;
    @Mock
    ConfigParameters config;

    @Mock
    Int2ObjectOpenHashMap<IntSet> peopleByLocationMap;

    private final int LOCATION_1 = 100;

    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;

    private final long TIMESTEP_1 = 1000l;
    private final int NUM_SEEDS = 2;


    private ReSeedManager instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Int2ObjectMap<Set> areasToReSeedMap = new Int2ObjectOpenHashMap();
        Set<AreaToReseed>  areasToReseed = new ObjectOpenHashSet();
        AreaToReseed areaToReseed1 = new AreaToReseed(LOCATION_1,NUM_SEEDS);
        areasToReseed.add(areaToReseed1);
        areasToReSeedMap.put((int)TIMESTEP_1, areasToReseed );
        when(str.getAreaToReseedMap()).thenReturn(areasToReSeedMap);

        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_2, AgeClass.ADULT);
        when(sl.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);

        instance = new ReSeedManager(rand,str,tm,config,sq,sm,sl);
    }
    //TODO: Do more edge cases
    @Test
    public void ReseedPeopleInfections() {
        when(tm.getTimeStep()).thenReturn(TIMESTEP_1);
        when(config.getReseedAreaLevel()).thenReturn(AreaLevels.IZ);
        when(sq.getAllSusceptiblePersonsInArea(AreaLevels.IZ,LOCATION_1)).thenReturn(intSetOf(PERSON_1,PERSON_2));
        when(rand.sampleWithoutReplacement(NUM_SEEDS,intSetOf(PERSON_1,PERSON_2))).thenReturn(listOf(PERSON_1,PERSON_2));

        instance.reseedPeopleInfections();

        verify(sm).updateInfectionState(PERSON_1,InfectionState.EXPOSED_YOUNG);
        verify(sm).updateInfectionState(PERSON_2,InfectionState.EXPOSED_ADULT);
        //verifyNoMoreInteractions();
    }

}
