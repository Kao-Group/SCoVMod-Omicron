package scovmod.model.transition.beta;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.lockdown.LockdownRatesManager;
import scovmod.model.state.infection.InfectionState;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BetaFirstAdjustmentPhaseRateTest {

    private static final double TOL = 1e-10;
    private final int LOCATION_1 = 100;
    private static final double FITTED_TRANS_MODIFIER = 0.1;
    private static final double AVERAGE_TRANS_INDEX = 17;
    private static final double BETA_LOCAL_LOCKDOWN = 0.5;
    private static final double FIRST_BETA_FACTOR = 0.2;
    private static final double SPECIFIED_BETA_FACTOR = 1.0;

    @Mock
    Parameters params;
    @Mock
    CovidVariantParameters covidParams;
    @Mock
    LockdownRatesManager lrm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(params.getFirstBetaFactor()).thenReturn(FIRST_BETA_FACTOR);
        when(lrm.getSpecifiedBetaFactor(LOCATION_1)).thenReturn(SPECIFIED_BETA_FACTOR);
        when(params.getBetaFactorForLocalLockdown()).thenReturn(BETA_LOCAL_LOCKDOWN);
        when(params.getsToE_Mild_YoungRate_Night()).thenReturn(0.555);
        when(params.getsToE_Mild_AdultRate_Night()).thenReturn(0.555);
        when(params.getsToE_Mild_ElderlyRate_Night()).thenReturn(0.555);
        when(params.getsToE_Severe_YoungRate_Night()).thenReturn(1.2);
        when(params.getsToE_Severe_AdultRate_Night()).thenReturn(1.2);
        when(params.getsToE_Severe_ElderlyRate_Night()).thenReturn(1.2);
        when(params.getsToE_Mild_YoungRate_Day()).thenReturn(0.55);
        when(params.getsToE_Mild_AdultRate_Day()).thenReturn(0.55);
        when(params.getsToE_Mild_ElderlyRate_Day()).thenReturn(0.55);
        when(params.getsToE_Severe_YoungRate_Day()).thenReturn(1.0);
        when(params.getsToE_Severe_AdultRate_Day()).thenReturn(1.0);
        when(params.getsToE_Severe_ElderlyRate_Day()).thenReturn(1.0);
        when(covidParams.getsToE_Mild_YoungRate_Night_covidVariant()).thenReturn(0.555);
        when(covidParams.getsToE_Mild_AdultRate_Night_covidVariant()).thenReturn(0.555);
        when(covidParams.getsToE_Mild_ElderlyRate_Night_covidVariant()).thenReturn(0.555);
        when(covidParams.getsToE_Severe_YoungRate_Night_covidVariant()).thenReturn(1.2);
        when(covidParams.getsToE_Severe_AdultRate_Night_covidVariant()).thenReturn(1.2);
        when(covidParams.getsToE_Severe_ElderlyRate_Night_covidVariant()).thenReturn(1.2);
        when(covidParams.getsToE_Mild_YoungRate_Day_covidVariant()).thenReturn(0.55);
        when(covidParams.getsToE_Mild_AdultRate_Day_covidVariant()).thenReturn(0.55);
        when(covidParams.getsToE_Mild_ElderlyRate_Day_covidVariant()).thenReturn(0.55);
        when(covidParams.getsToE_Severe_YoungRate_Day_covidVariant()).thenReturn(1.0);
        when(covidParams.getsToE_Severe_AdultRate_Day_covidVariant()).thenReturn(1.0);
        when(covidParams.getsToE_Severe_ElderlyRate_Day_covidVariant()).thenReturn(1.0);
        when(params.getAverageTransIndexPerCouncilArea()).thenReturn(AVERAGE_TRANS_INDEX);
        when(params.getTransIndexModifier()).thenReturn(FITTED_TRANS_MODIFIER);
    }

    @Test
    public void checkBetaRatesInLocalLockdown_atNight() {
        BetaFirstAdjustmentPhaseRate instance = new BetaFirstAdjustmentPhaseRate(lrm, params,covidParams);
        when(lrm.isInLocalLockdown(LOCATION_1)).thenReturn(true);
        when(lrm.isDay()).thenReturn(false);
        when(lrm.isDuringFirstBetaChange()).thenReturn(true);
        when(lrm.isDuringSecondBetaChange()).thenReturn(false);
        double transMod = 1-(FITTED_TRANS_MODIFIER *(28.28449377- AVERAGE_TRANS_INDEX));
        when(lrm.getTansmissionModifierBasedOnTransmissionIndex(LOCATION_1)).thenReturn(transMod);
        when(lrm.getLocalLockdownMultiplier(LOCATION_1)).thenReturn(BETA_LOCAL_LOCKDOWN);
        assertEquals(0.1332* transMod *BETA_LOCAL_LOCKDOWN, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1), TOL);
        assertEquals(0.24* transMod *BETA_LOCAL_LOCKDOWN, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1), TOL);
        assertEquals(0.1332* transMod *BETA_LOCAL_LOCKDOWN, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT,LOCATION_1), TOL);
        assertEquals(0.24* transMod *BETA_LOCAL_LOCKDOWN, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT,LOCATION_1), TOL);
    }

    @Test
    public void checkBetaRatesInLocalLockdown_duringDay() {
        BetaFirstAdjustmentPhaseRate instance = new BetaFirstAdjustmentPhaseRate(lrm, params,covidParams);
        when(lrm.isInLocalLockdown(LOCATION_1)).thenReturn(true);
        when(lrm.isDay()).thenReturn(true);
        when(lrm.isDuringFirstBetaChange()).thenReturn(true);
        when(lrm.isDuringSecondBetaChange()).thenReturn(false);
        double transMod = 1-(FITTED_TRANS_MODIFIER *(28.28449377- AVERAGE_TRANS_INDEX));
        when(lrm.getTansmissionModifierBasedOnTransmissionIndex(LOCATION_1)).thenReturn(transMod);
        when(lrm.getLocalLockdownMultiplier(LOCATION_1)).thenReturn(BETA_LOCAL_LOCKDOWN);
        assertEquals(0.11*transMod*BETA_LOCAL_LOCKDOWN, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1), TOL);
        assertEquals(0.2*transMod*BETA_LOCAL_LOCKDOWN, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1), TOL);
        assertEquals(0.11*transMod*BETA_LOCAL_LOCKDOWN, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT,LOCATION_1), TOL);
        assertEquals(0.2*transMod*BETA_LOCAL_LOCKDOWN, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT,LOCATION_1), TOL);
    }

    @Test
    public void checkBetaRatesFirstBetaChangeNoLocalLockdown_atNight() {
        BetaFirstAdjustmentPhaseRate instance = new BetaFirstAdjustmentPhaseRate(lrm, params,covidParams);
        when(lrm.isInLocalLockdown(LOCATION_1)).thenReturn(false);
        when(lrm.isDay()).thenReturn(false);
        when(lrm.isDuringFirstBetaChange()).thenReturn(true);
        when(lrm.isDuringSecondBetaChange()).thenReturn(false);
        double transMod = 1-(FITTED_TRANS_MODIFIER *(28.28449377- AVERAGE_TRANS_INDEX)); //2.128449377
        when(lrm.getTansmissionModifierBasedOnTransmissionIndex(LOCATION_1)).thenReturn(transMod);
        when(lrm.getLocalLockdownMultiplier(LOCATION_1)).thenReturn(1.0);
        assertEquals(0.1332*transMod, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1), TOL);
        assertEquals(0.24*transMod, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1), TOL);
        assertEquals(0.1332*transMod, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT,LOCATION_1), TOL);
        assertEquals(0.24*transMod, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT,LOCATION_1), TOL);
    }

    @Test
    public void checkBetaRatesFirstBetaChange_duringDay() {
        BetaFirstAdjustmentPhaseRate instance = new BetaFirstAdjustmentPhaseRate(lrm, params,covidParams);
        when(lrm.isInLocalLockdown(LOCATION_1)).thenReturn(false);
        when(lrm.isDay()).thenReturn(true);
        when(lrm.isDuringFirstBetaChange()).thenReturn(true);
        when(lrm.isDuringSecondBetaChange()).thenReturn(false);
        double transMod = 1-(FITTED_TRANS_MODIFIER *(28.28449377- AVERAGE_TRANS_INDEX));//2.128449377
        when(lrm.getTansmissionModifierBasedOnTransmissionIndex(LOCATION_1)).thenReturn(transMod);
        when(lrm.getLocalLockdownMultiplier(LOCATION_1)).thenReturn(1.0);
        assertEquals(0.11*transMod, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG,LOCATION_1), TOL);
        assertEquals(0.2*transMod, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY,LOCATION_1), TOL);
        assertEquals(0.11*transMod, instance.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT,LOCATION_1), TOL);
        assertEquals(0.2*transMod, instance.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT,LOCATION_1), TOL);
    }

}
