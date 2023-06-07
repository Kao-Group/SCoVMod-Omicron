package scovmod.model.lockdown;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.StateQuery;
import scovmod.model.time.TimeManager;
import scovmod.test.FakeLocalPopulation;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static scovmod.model.state.infection.InfectionState.*;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.listOf;

public class PopulationSizePerAreaLookupTest {

    private PopulationSizePerAreaLookup instance;

    private final int CA_1 = 100;
    private final int CA_2 = 200;
    private final int PERSON_1 = 1;
    private final int OA_1 = 100;
    private final int PERSON_2 = 2;
    private final int OA_2 = 200;
    private final int PERSON_3 = 3;
    private final int OA_3 = 300;

    @Mock
    StateQuery sq;
    @Mock
    HealthBoardLookup hbl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPopulationofCA() {
        instance = new PopulationSizePerAreaLookup(hbl,sq);
        when(hbl.getAllCAs()).thenReturn(intSetOf(CA_1,CA_2));
        FakeLocalPopulation pop1 = new FakeLocalPopulation();
        pop1.testSet(PERSON_1, EXPOSED_ADULT);
        FakeLocalPopulation pop2 = new FakeLocalPopulation();
        pop2.testSet(PERSON_2, MILD_INFECTIOUS_ELDERLY);
        FakeLocalPopulation pop3 = new FakeLocalPopulation();
        pop3.testSet(PERSON_3, EXPOSED_YOUNG);
        when(sq.getCopyOfLocalPopulation(OA_1)).thenReturn(pop1);
        when(sq.getCopyOfLocalPopulation(OA_2)).thenReturn(pop2);
        when(sq.getCopyOfLocalPopulation(OA_3)).thenReturn(pop3);
        Int2ObjectMap<List<Integer>> oasByLA = new Int2ObjectOpenHashMap();
        oasByLA.put(CA_1, listOf(OA_1,OA_2));
        oasByLA.put(CA_2, listOf(OA_3));
        when(hbl.getOasByLA()).thenReturn(oasByLA);
        instance.initialisePopSizePerAreas();
        assertEquals(2,instance.getPopByCouncilAreaLookup().get(CA_1));
        assertEquals(1,instance.getPopByCouncilAreaLookup().get(CA_2));
    }
}
