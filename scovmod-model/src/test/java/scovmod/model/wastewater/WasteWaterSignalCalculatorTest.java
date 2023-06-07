package scovmod.model.wastewater;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.wastewater.DZToWWSiteMappingReader;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;
import scovmod.test.FakeLocalPopulation;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static scovmod.model.state.infection.InfectionState.MILD_INFECTIOUS_ELDERLY;
import static scovmod.model.state.infection.InfectionState.SEVERE_INFECTIOUS_ELDERLY;


public class WasteWaterSignalCalculatorTest {
    @Mock
    StateQuery sq;

    @Mock
    DZToWWSiteMappingReader DtoWMap;

    @Mock
    HealthBoardLookup hbl;

    @Mock
    ConfigParameters config;

    @Mock
    StatisticsCollector stats;

    @Mock
    TimeConversions tcv;

    @Mock
    TimeManager tm;

    private WasteWaterSignalCalculator instance;

    private static final double TOL = Double.MIN_VALUE;

    private final int DZ_1 = 1;
    private final int DZ_2 = 2;
    private final int DZ_3 = 3;
    private final int DZ_4 = 4;
    private final int DZ_5 = 5;

    private final String SITE_1 = "Site 1";
    private final String SITE_2 = "Site 2";
    private final String SITE_3 = "Site 3";

    private final int OA_1 = 10;
    private final int OA_2 = 20;
    private final int OA_3 = 30;
    private final int OA_4 = 40;
    private final int OA_5 = 50;
    private final int OA_6 = 60;
    private final int OA_7 = 70;
    private final int OA_8 = 80;
    private final int OA_9 = 90;
    private final int OA_10 = 91;

    private final int PERSON_1 = 101;
    private final int PERSON_2 = 102;
    private final int PERSON_3 = 103;
    private final int PERSON_4 = 104;
    private final int PERSON_5 = 105;
    private final int PERSON_6 = 106;
    private final int PERSON_7 = 107;
    private final int PERSON_8 = 108;
    private final int PERSON_9 = 109;
    private final int PERSON_10 = 110;
    private final int PERSON_11 = 111;
    private final int PERSON_12 = 112;
    private final int PERSON_13 = 113;
    private final int PERSON_14 = 114;
    private final int PERSON_15 = 115;
    private final int PERSON_16 = 116;
    private final int PERSON_17 = 117;
    private final int PERSON_18 = 118;
    private final int PERSON_19 = 119;
    private final int PERSON_20 = 120;

    private LocalDate CURRENT_DATE = LocalDate.of(2003, Month.JANUARY, 1);

    private final long CURRENT_TIMESTEP = 1000;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new WasteWaterSignalCalculator(sq,DtoWMap,hbl, config,stats,tcv,tm);
    }

    @Test
    public void aggregateTotalRNASignalPerSiteTest() {

        //Int2ObjectMap<Object2DoubleMap> DWMap = DtoWMap.getSitesToDZdMap();
        //Step 1: Need to create mocked sitesToDZMap 2 sites split into 3 dz each
        Int2ObjectMap<Object2DoubleMap> DWMap = new Int2ObjectOpenHashMap<>();
        Object2DoubleMap propMap1 = new Object2DoubleOpenHashMap();
        propMap1.put(SITE_1,1);
        DWMap.put(DZ_1,propMap1);

        Object2DoubleMap propMap2 = new Object2DoubleOpenHashMap();
        propMap2.put(SITE_2,0.5);
        propMap2.put(SITE_3,0.5);
        DWMap.put(DZ_2,propMap2);

        Object2DoubleMap propMap3 = new Object2DoubleOpenHashMap();
        propMap3.put(SITE_1,0.2);
        propMap3.put(SITE_2,0.3);
        propMap3.put(SITE_3,0.5);
        DWMap.put(DZ_3,propMap3);

        Object2DoubleMap propMap4 = new Object2DoubleOpenHashMap();
        propMap4.put(SITE_1,1);
        DWMap.put(DZ_4,propMap4);

        Object2DoubleMap propMap5 = new Object2DoubleOpenHashMap();
        propMap5.put(SITE_3,1);
        DWMap.put(DZ_5,propMap5);

        when(DtoWMap.getSitesToDZdMap()).thenReturn(DWMap);

        //Step 2: Mock hbl.getOasByDZ()  two OAs for each of the DZs , .get(DZ)
        Int2ObjectMap<List<Integer>> OAbyDZ = new Int2ObjectOpenHashMap<>();
        List<Integer> OAList1 = new ArrayList<Integer>(){{
            add(OA_1);
            add(OA_2);
        }};
        OAbyDZ.put(DZ_1,OAList1);

        List<Integer> OAList2 = new ArrayList<Integer>(){{
            add(OA_3);
            add(OA_4);
        }};
        OAbyDZ.put(DZ_2,OAList2);

        List<Integer> OAList3 = new ArrayList<Integer>(){{
            add(OA_5);
            add(OA_6);
        }};
        OAbyDZ.put(DZ_3,OAList3);

        List<Integer> OAList4 = new ArrayList<Integer>(){{
            add(OA_7);
            add(OA_8);
        }};
        OAbyDZ.put(DZ_4,OAList4);

        List<Integer> OAList5 = new ArrayList<Integer>(){{
            add(OA_9);
            add(OA_10);
        }};
        OAbyDZ.put(DZ_5,OAList5);
        when(hbl.getOasByDZ()).thenReturn(OAbyDZ);
        //Step 3: Mock the populations of six different OAs three people per OA all in varied states
        FakeLocalPopulation OA1Pop = new FakeLocalPopulation();
        OA1Pop.testSet(PERSON_1, InfectionState.EXPOSED_ADULT);
        OA1Pop.testSet(PERSON_2, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_1)).thenReturn(OA1Pop);

        FakeLocalPopulation OA2Pop = new FakeLocalPopulation();
        OA2Pop.testSet(PERSON_3, InfectionState.EXPOSED_ADULT);
        OA2Pop.testSet(PERSON_4, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_2)).thenReturn(OA2Pop);

        FakeLocalPopulation OA3Pop = new FakeLocalPopulation();
        OA3Pop.testSet(PERSON_5, InfectionState.EXPOSED_ADULT);
        OA3Pop.testSet(PERSON_6, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_3)).thenReturn(OA3Pop);

        FakeLocalPopulation OA4Pop = new FakeLocalPopulation();
        OA4Pop.testSet(PERSON_7, InfectionState.EXPOSED_ADULT);
        OA4Pop.testSet(PERSON_8, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_4)).thenReturn(OA4Pop);

        FakeLocalPopulation OA5Pop = new FakeLocalPopulation();
        OA5Pop.testSet(PERSON_9, InfectionState.EXPOSED_ADULT);
        OA5Pop.testSet(PERSON_10, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_5)).thenReturn(OA5Pop);

        FakeLocalPopulation OA6Pop = new FakeLocalPopulation();
        OA6Pop.testSet(PERSON_11, InfectionState.EXPOSED_ADULT);
        OA6Pop.testSet(PERSON_12, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_6)).thenReturn(OA6Pop);

        FakeLocalPopulation OA7Pop = new FakeLocalPopulation();
        OA7Pop.testSet(PERSON_13, InfectionState.EXPOSED_ADULT);
        OA7Pop.testSet(PERSON_14, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_7)).thenReturn(OA7Pop);

        FakeLocalPopulation OA8Pop = new FakeLocalPopulation();
        OA8Pop.testSet(PERSON_15, InfectionState.EXPOSED_ADULT);
        OA8Pop.testSet(PERSON_16, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_8)).thenReturn(OA8Pop);

        FakeLocalPopulation OA9Pop = new FakeLocalPopulation();
        OA9Pop.testSet(PERSON_17, InfectionState.EXPOSED_ADULT);
        OA9Pop.testSet(PERSON_18, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_9)).thenReturn(OA9Pop);

        FakeLocalPopulation OA10Pop = new FakeLocalPopulation();
        OA10Pop.testSet(PERSON_19, InfectionState.EXPOSED_ADULT);
        OA10Pop.testSet(PERSON_20, MILD_INFECTIOUS_ELDERLY);
        when(sq.getCopyOfLocalPopulation(OA_10)).thenReturn(OA10Pop);

        //Mock proportions for all sites and DZs and put into WWAreaPropMap
        //double totalRNASignalDZ = totalMildInfectious*config.getMildInfectiousMultiplier() + totalSevereInfectious*config.getSevereInfectiousMultiplier() + totalHospitalised*config.getHospitalisedMultiplier();
        //Step 4: Mock config to have get for multipliers per state
        when(config.getMildInfectiousMultiplier()).thenReturn(0.3);
        when(config.getSevereInfectiousMultiplier()).thenReturn(0.6);
        when(config.getHospitalisedMultiplier()).thenReturn(0.9);

        when(tm.getTimeStep()).thenReturn(CURRENT_TIMESTEP);
        when(tcv.toTimeStepStartDate(CURRENT_TIMESTEP)).thenReturn(CURRENT_DATE);
        //Step 5: CHeck that stats is called three different times with different values for each
        instance.aggregateTotalRNASignalPerSite();

        double testValue1 = instance.getTotalSignalperSiteMap().get(SITE_1);
        assertEquals(1.32,testValue1,0.001);

        double testValue2 = instance.getTotalSignalperSiteMap().get(SITE_2);
        assertEquals(0.48,testValue2,TOL);

        double testValue3 = instance.getTotalSignalperSiteMap().get(SITE_3);
        assertEquals(1.2,testValue3,TOL);
        //verify(stats).currentTotalRNASignal(CURRENT_DATE,1.4,"Site 2");
        //verify(stats).currentTotalRNASignal(CURRENT_DATE,1.6,"Site 3");
    }
}

