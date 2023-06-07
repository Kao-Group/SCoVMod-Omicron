package scovmod.model.transition;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.lockdown.LockdownTriggerManager;
import scovmod.model.time.TimeManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TracingRatesTest {

    private static final double TOL = 1e-10;
    private final int LOCATION_1 = 100;

    @Mock
    ConfigParameters config;
    @Mock
    TimeManager tm;
    @Mock
    LockdownTriggerManager ltm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkTracingRatesInLocalLockdown() {
        TracingRates instance = new TracingRates(config, ltm, tm);
        when(tm.getTimeStep()).thenReturn(5l);
        when(config.getLocalLockdownStartTimeStep()).thenReturn(4);
        when(config.isLocalLockdownsActivated()).thenReturn(true);
        when(ltm.isLocationInAreaUnderLockdown(LOCATION_1)).thenReturn(true);
        when(config.getLocalLockdownContactTracingRateMultiplier()).thenReturn(2.0);
        assertEquals(0.4, instance.getTracingRate(0.2,LOCATION_1), TOL);
    }

    @Test
    public void checkTracingRatesNotInLockdown() {
        TracingRates instance = new TracingRates(config,ltm, tm);
        when(tm.getTimeStep()).thenReturn(5l);
        when(config.isLocalLockdownsActivated()).thenReturn(false);
        when(ltm.isLocationInAreaUnderLockdown(LOCATION_1)).thenReturn(false);
        when(config.getFirstBetaFactorTimeStep()).thenReturn(10l);
        assertEquals(0.2, instance.getTracingRate(0.2,LOCATION_1), TOL);
    }
}
