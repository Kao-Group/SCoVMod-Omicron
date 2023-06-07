package scovmod.model.seeding.recovered;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.SpatialSeedingGroupAttributes;
import scovmod.model.input.config.Parameters;
import scovmod.model.seeding.WithinLocationSampler;
import scovmod.model.util.math.Random;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.listOf;

public class WithinLocationRecoveredSamplerTest {

    private static final Double GROUP_1_LOAD_FACTOR = 0.9;
    private static final Double GROUP_2_LOAD_FACTOR = 0.3;
    private static final int LOCATION_1 = 100;
    private static final int LOCATION_2 = 200;
    private static final int PERSON_1 = 1;
    private static final int PERSON_2 = 2;
    private static final int PERSON_3 = 3;
    private static final int PERSON_4 = 4;
    private static final int PERSON_5 = 5;
    private static final int SEED_GROUP_1 = 15;
    private static final int SEED_GROUP_2 = 16;

    private Int2ObjectMap<IntSet> peopleByLocation;

    @Mock
    Random rand;

    private WithinLocationRecoveredSampler instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Parameters params = mock(Parameters.class);
        peopleByLocation = new Int2ObjectOpenHashMap<>();
        peopleByLocation.put(LOCATION_1,intSetOf(PERSON_1,PERSON_2));
        peopleByLocation.put(LOCATION_2,intSetOf(PERSON_3,PERSON_4, PERSON_5));

        instance = new WithinLocationRecoveredSampler(rand);
    }


    @Test
    public void sampleIZInGroup2(){
        int expectedNumOA2 = 2;

        when(rand.sampleWithoutReplacement(
                expectedNumOA2,
                intSetOf(PERSON_3, PERSON_4, PERSON_5))
        ).thenReturn(listOf(PERSON_3,PERSON_4));

        assertEquals(
                listOf(PERSON_3,PERSON_4),
                instance.samplePeopleAtLocation(LOCATION_2, peopleByLocation,expectedNumOA2));

    }
}
