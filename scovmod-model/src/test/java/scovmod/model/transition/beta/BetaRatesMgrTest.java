package scovmod.model.transition.beta;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.lockdown.LockdownRatesManager;
import scovmod.model.state.infection.InfectionState;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BetaRatesMgrTest {

    private static final double TOL = 1e-10;
    private final int LOCATION_1 = 100;

    @Mock
    LockdownRatesManager lrm;
    @Mock
    BetaMatrixPhaseRate bmr;
    @Mock
    BetaFittedPhasedRate bfr;
    @Mock
    BetaFirstAdjustmentPhaseRate bfar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkBetaRatesNotInLockdown_duringDay() {
        BetaRatesMgr instance = new BetaRatesMgr(bfr,bfar,bmr,lrm);
        when(lrm.isDuringFirstBetaChange()).thenReturn(false);
        when(lrm.isDuringSecondBetaChange()).thenReturn(false);
        when(bfr.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1)).thenReturn(0.55);
        when(bfr.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1)).thenReturn(1.0);
        assertEquals(0.55, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1), TOL);
        assertEquals(1.0, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1), TOL);
    }

    @Test
    public void checkBetaRatesFirstBetaChange() {
        BetaRatesMgr instance = new BetaRatesMgr(bfr,bfar,bmr,lrm);
        when(lrm.isDuringFirstBetaChange()).thenReturn(true);
        when(lrm.isDuringSecondBetaChange()).thenReturn(false);
        when(bfar.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1)).thenReturn(0.11);
        when(bfar.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1)).thenReturn(0.2);
        assertEquals(0.11, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1), TOL);
        assertEquals(0.2, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1), TOL);
    }

    @Test
    public void checkBetaRatesAfterSecondBetaChange() {
        BetaRatesMgr instance = new BetaRatesMgr(bfr,bfar,bmr,lrm);
        when(lrm.isDuringFirstBetaChange()).thenReturn(false);
        when(lrm.isDuringSecondBetaChange()).thenReturn(true);
        when(bmr.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1)).thenReturn(0.66);
        when(bmr.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1)).thenReturn(0.22);
        assertEquals(0.66, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1), TOL);
        assertEquals(0.22, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1), TOL);
    }

}
