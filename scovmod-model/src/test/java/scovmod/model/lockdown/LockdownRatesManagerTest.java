package scovmod.model.lockdown;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.TransmissionModIndexPerCouncilArea;
import scovmod.model.input.SpecifiedLockdownDetailsReader;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.time.TimeManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class LockdownRatesManagerTest {

    private static final double TOL = 1e-10;
    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;
    private final int TIMESTEP_1 = 1000;
    private final int TIMESTEP_2 = 2000;
    private static final double FITTED_ACCESS_MODIFIER = 0.1;
    private static final double AVERAGE_ACCESS_INDEX = 17;
    private static final int LA_AYRSHIRE = 15;
    private static final int LA_GLASGOW = 49;
    private static final double BETA_LOCAL_LOCKDOWN = 0.5;
    private static final double FIRST_BETA_FACTOR = 0.2;
    private static final double SPECIFIED_BETA_FACTOR = 0.5;

    @Mock
    Parameters params;
    @Mock
    ConfigParameters config;
    @Mock
    TimeManager tm;
    @Mock
    LockdownTriggerManager ltm;
    @Mock
    TransmissionModIndexPerCouncilArea aipca;
    @Mock
    HealthBoardLookup hbl;
    @Mock
    SpecifiedLockdownDetailsReader sldr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //when(tm.getTimeStep()).thenReturn(CURRENT_EPOCH_TIMESTEP);
        when(params.getFirstBetaFactor()).thenReturn(FIRST_BETA_FACTOR);
        //when(params.getSecondBetaFactor()).thenReturn(SECOND_BETA_FACTOR);
        when(params.getBetaFactorForLocalLockdown()).thenReturn(BETA_LOCAL_LOCKDOWN);

        Int2DoubleMap deprivationIndex = new Int2DoubleOpenHashMap();
        deprivationIndex.put(15,28.28449377);
        deprivationIndex.put(16,15.72185475);
        deprivationIndex.put(17,16.80101126);
        deprivationIndex.put(19,17.70649213);
        deprivationIndex.put(20,10.84380362);
        deprivationIndex.put(22,14.53387911);
        deprivationIndex.put(24,16.29550089);
        deprivationIndex.put(25,10.59653685);
        deprivationIndex.put(26,10.86340133);
        deprivationIndex.put(28,13.72908302);
        deprivationIndex.put(29,18.76111476);
        deprivationIndex.put(30,18.28639984);
        deprivationIndex.put(31,31.76163256);
        deprivationIndex.put(32,26.56907092);
        when(aipca.getAccessIndexPerCA()).thenReturn(deprivationIndex);
        when(params.getAverageTransIndexPerCouncilArea()).thenReturn(AVERAGE_ACCESS_INDEX);
        when(hbl.getCouncilAreaFromOA(LOCATION_1)).thenReturn(LA_AYRSHIRE);
        when(hbl.getCouncilAreaFromOA(LOCATION_2)).thenReturn(LA_GLASGOW);
        when(params.getTransIndexModifier()).thenReturn(FITTED_ACCESS_MODIFIER);
    }

    @Test
    public void isInLocalLockdown() {
        when(tm.getTimeStep()).thenReturn(5l);
        when(config.getLocalLockdownStartTimeStep()).thenReturn(4);
        when(config.isLocalLockdownsActivated()).thenReturn(true);
        when(ltm.isLocationInAreaUnderLockdown(LOCATION_1)).thenReturn(true);
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm, params, sldr, hbl, aipca);
        assertTrue(instance.isInLocalLockdown(LOCATION_1));
    }

    @Test
    public void isNotInLocalLockdown() {
        when(tm.getTimeStep()).thenReturn(3l);
        when(config.getLocalLockdownStartTimeStep()).thenReturn(4);
        when(config.isLocalLockdownsActivated()).thenReturn(true);
        when(ltm.isLocationInAreaUnderLockdown(LOCATION_1)).thenReturn(true);
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm, params, sldr, hbl, aipca);
        assertFalse(instance.isInLocalLockdown(LOCATION_1));
    }

    @Test
    public void getLocalLockdownMultiplier_NotInLockdown_InLocalLockdown() {
        when(tm.getTimeStep()).thenReturn(5l);
        when(config.getLocalLockdownStartTimeStep()).thenReturn(4);
        when(config.isLocalLockdownsActivated()).thenReturn(true);
        when(ltm.isLocationInAreaUnderLockdown(LOCATION_1)).thenReturn(true);
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm, params, sldr, hbl, aipca);
        assertEquals(BETA_LOCAL_LOCKDOWN,instance.getLocalLockdownMultiplier(LOCATION_1),TOL);
    }

    @Test
    public void getLocalLockdownMultiplier_NotInLockdown() {
        when(tm.getTimeStep()).thenReturn(3l);
        when(config.getLocalLockdownStartTimeStep()).thenReturn(4);
        when(config.isLocalLockdownsActivated()).thenReturn(true);
        when(ltm.isLocationInAreaUnderLockdown(LOCATION_1)).thenReturn(true);
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm, params, sldr, hbl, aipca);
        assertEquals(1.0,instance.getLocalLockdownMultiplier(LOCATION_1),TOL);
    }

    @Test
    public void isDay() {
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm , params, sldr, hbl, aipca);
        when(tm.getTimeStep()).thenReturn(4l);
        assertTrue( instance.isDay());
    }

    @Test
    public void isNight() {
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm , params, sldr, hbl, aipca);
        when(tm.getTimeStep()).thenReturn(5l);
        assertFalse( instance.isDay());
    }

    @Test
    public void isDuringFirstBetaChange() {
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm , params, sldr, hbl, aipca);
        when(tm.getTimeStep()).thenReturn(5l);
        when(config.getFirstBetaFactorTimeStep()).thenReturn(4l);
        when(config.getSecondBetaFactorTimeStep()).thenReturn(8l);
        assertTrue(instance.isDuringFirstBetaChange());
    }

    @Test
    public void checkBetaRatesAfterSecondBetaChange_atNight() {
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm , params, sldr, hbl, aipca);
        when(tm.getTimeStep()).thenReturn(5l);
        when(config.getFirstBetaFactorTimeStep()).thenReturn(2l);
        when(config.getSecondBetaFactorTimeStep()).thenReturn(4l);
        assertTrue(instance.isDuringSecondBetaChange());
    }

    @Test
    public void getSpecifiedBetaFactor_InSpecifiedLockdown() {
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm , params, sldr, hbl, aipca);
        when(tm.getTimeStep()).thenReturn(6l);
        when(config.getSecondBetaFactorTimeStep()).thenReturn(4l);
        Object2DoubleMap<SpecifiedLockdownDetails> specifiedLockdownMap = new Object2DoubleOpenHashMap();
        SpecifiedLockdownDetails details1 = new SpecifiedLockdownDetails(LA_GLASGOW,TIMESTEP_1);
        SpecifiedLockdownDetails details2 = new SpecifiedLockdownDetails(LA_AYRSHIRE,TIMESTEP_2);
        specifiedLockdownMap.put(details1,SPECIFIED_BETA_FACTOR);
        specifiedLockdownMap.put(details2,SPECIFIED_BETA_FACTOR);
        when(sldr.getAreasWithSpecifiedLockdownMap()).thenReturn(specifiedLockdownMap);
        when(sldr.getSpecifiedBetaMultiplier(LA_GLASGOW)).thenReturn(0.5);
        when(sldr.getSpecifiedBetaMultiplier(LA_AYRSHIRE)).thenReturn(0.5);
        assertEquals(SPECIFIED_BETA_FACTOR,instance.getSpecifiedBetaFactor(LOCATION_1),TOL);
    }

    @Test
    public void getSpecifiedBetaFactor_NotInSpecifiedLockdown() {
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm , params, sldr, hbl, aipca);
        when(tm.getTimeStep()).thenReturn(6l);
        when(config.getSecondBetaFactorTimeStep()).thenReturn(4l);
        Object2DoubleMap<SpecifiedLockdownDetails> specifiedLockdownMap = new Object2DoubleOpenHashMap();
        when(sldr.getAreasWithSpecifiedLockdownMap()).thenReturn(specifiedLockdownMap);
        when(sldr.getSpecifiedBetaMultiplier(LA_GLASGOW)).thenReturn(1.0);
        when(sldr.getSpecifiedBetaMultiplier(LA_AYRSHIRE)).thenReturn(1.0);
        assertEquals(1.0,instance.getSpecifiedBetaFactor(LOCATION_1),TOL);
    }

    @Test
    public void getAccessModifierBasedOnAccessIndex() {
        LockdownRatesManager instance = new LockdownRatesManager(tm, config, ltm , params, sldr, hbl, aipca);
        when(tm.getTimeStep()).thenReturn(6l);
        when(config.getSecondBetaFactorTimeStep()).thenReturn(4l);
        Object2DoubleMap<SpecifiedLockdownDetails> specifiedLockdownMap = new Object2DoubleOpenHashMap();
        SpecifiedLockdownDetails details1 = new SpecifiedLockdownDetails(LA_GLASGOW,TIMESTEP_1);
        specifiedLockdownMap.put(details1,SPECIFIED_BETA_FACTOR);
        //Note Second lockdown is a factor of the first
        double transIndexMod = 1+(FITTED_ACCESS_MODIFIER*(28.28449377-AVERAGE_ACCESS_INDEX));
        assertEquals(transIndexMod, instance.getTansmissionModifierBasedOnTransmissionIndex(LOCATION_1), TOL);
    }

}
