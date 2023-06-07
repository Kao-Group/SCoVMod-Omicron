package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.RecoveredSeeds;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.seeding.reseeding.AreaToReseed;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.math.Random;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;

public class SeedManagerTest {

    @Mock
    StartLocationsAndAgeClasses sl;

    @Mock
    NationalSamplerFactory asf;

    @Mock
    NationalSampler as;

    @Mock
    Random rand;

    @Mock
    StatisticsCollector stats;

    @Mock
    RecoveredSeeds str;

    @Mock
    ConfigParameters config;

    @Mock
    WithinGroupSampler wgs;

    @Mock
    Int2ObjectOpenHashMap<IntSet> peopleByLocationMap;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int LOCATION_3 = 300;
    private final int LOCATION_4 = 400;
    private final int IZ_1 = 1000;
    private final int IZ_2 = 2000;
    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    private final int PERSON_4 = 4;
    private final int PERSON_5 = 5;
    private final int PERSON_6 = 6;
    private final int PERSON_7 = 7;
    private final int PERSON_8 = 8;
    private final int PERSON_9 = 9;
    private final int PERSON_10 = 10;
    
    private final long FIRST_TIMESTEP = 36982;

    private SeedManager instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Int2IntMap startLocationMap = new Int2IntOpenHashMap();
        startLocationMap.put(PERSON_1, LOCATION_1);
        startLocationMap.put(PERSON_2, LOCATION_1);
        startLocationMap.put(PERSON_3, LOCATION_2);
        startLocationMap.put(PERSON_4, LOCATION_2);
        startLocationMap.put(PERSON_5, LOCATION_3);
        startLocationMap.put(PERSON_6, LOCATION_3);
        startLocationMap.put(PERSON_7, LOCATION_4);
        startLocationMap.put(PERSON_8, LOCATION_4);
        startLocationMap.put(PERSON_9, LOCATION_1);
        startLocationMap.put(PERSON_10, LOCATION_4);

        when(sl.getLocationsByPeopleId()).thenReturn(startLocationMap);
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_2, AgeClass.ADULT);
        peopleAgeClasses.put(PERSON_3, AgeClass.ELDERLY);
        peopleAgeClasses.put(PERSON_4, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_5, AgeClass.ADULT);
        peopleAgeClasses.put(PERSON_6, AgeClass.ELDERLY);
        peopleAgeClasses.put(PERSON_7, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_8, AgeClass.ELDERLY);
        peopleAgeClasses.put(PERSON_9, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_10, AgeClass.ELDERLY);
        when(sl.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);

        Int2ObjectMap<Set> areaToReseedMap = new Int2ObjectOpenHashMap();
        AreaToReseed areaToReseed1 = new AreaToReseed(IZ_1, 1);
        AreaToReseed areaToReseed2 = new AreaToReseed(IZ_2, 1);
        Set<AreaToReseed> set1 = new ObjectOpenHashSet();
        set1.add(areaToReseed1);
        set1.add(areaToReseed2);
        areaToReseedMap.put((int)FIRST_TIMESTEP, set1);
        when(str.getAreaToReseedMap()).thenReturn(areaToReseedMap);
        instance = new SeedManager(sl, asf, rand, str, config, wgs);
    }

    @Test
    public void seedAnimalInfections() {

        Int2ObjectMap<IntSet> peopleByLocation = peopleByLocationMap;

        when(asf.build(peopleByLocationMap)).thenReturn(as);
        when(as.getSampledPeople()).thenReturn(intSetOf(PERSON_1,PERSON_2,PERSON_3));

      //  when(wgs.samplePeopleFromGroup(1,IZ_1,peopleByLocationMap)).thenReturn(intSetOf(PERSON_9));
      //  when(wgs.samplePeopleFromGroup(1,IZ_2,peopleByLocationMap)).thenReturn(intSetOf(PERSON_10));

        when(config.getFirstTimeStep()).thenReturn(FIRST_TIMESTEP);
        Int2ObjectMap<InfectionState> resultPersonStates = new Int2ObjectOpenHashMap<>();

        instance.seedPeopleInfections(resultPersonStates, peopleByLocation);

        assertEquals(InfectionState.EXPOSED_YOUNG, resultPersonStates.get(PERSON_1));
        assertEquals(InfectionState.EXPOSED_ADULT, resultPersonStates.get(PERSON_2));
        assertEquals(InfectionState.EXPOSED_ELDERLY, resultPersonStates.get(PERSON_3));
        assertEquals(InfectionState.SUSCEPTIBLE, resultPersonStates.get(PERSON_4));
        assertEquals(InfectionState.SUSCEPTIBLE, resultPersonStates.get(PERSON_5));
        assertEquals(InfectionState.SUSCEPTIBLE, resultPersonStates.get(PERSON_6));
        assertEquals(InfectionState.SUSCEPTIBLE, resultPersonStates.get(PERSON_7));
        assertEquals(InfectionState.SUSCEPTIBLE, resultPersonStates.get(PERSON_8));
        assertEquals(InfectionState.SUSCEPTIBLE, resultPersonStates.get(PERSON_9));
        assertEquals(InfectionState.SUSCEPTIBLE, resultPersonStates.get(PERSON_10));
        assertEquals(10, resultPersonStates.size());
    }

}
