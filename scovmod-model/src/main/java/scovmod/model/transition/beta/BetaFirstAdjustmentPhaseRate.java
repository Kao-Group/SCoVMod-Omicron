package scovmod.model.transition.beta;

import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.lockdown.LockdownRatesManager;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.transition.beta.BetaRate;

import static scovmod.model.state.infection.InfectionState.*;

public class BetaFirstAdjustmentPhaseRate implements BetaRate {

    private final LockdownRatesManager lrm;
    private final Parameters params;

    private final double betaYoungSevere_night;
    private final double betaAdultSevere_night;
    private final double betaElderlySevere_night;
    private final double betaYoungSevere_day;
    private final double betaAdultSevere_day;
    private final double betaElderlySevere_day;

    private final double betaYoungMild_night_firstBetaChange;
    private final double betaAdultMild_night_firstBetaChange;
    private final double betaElderlyMild_night_firstBetaChange;
    private final double betaYoungSevere_night_firstBetaChange;
    private final double betaAdultSevere_night_firstBetaChange;
    private final double betaElderlySevere_night_firstBetaChange;
    private final double betaYoungMild_day_firstBetaChange;
    private final double betaAdultMild_day_firstBetaChange;
    private final double betaElderlyMild_day_firstBetaChange;
    private final double betaYoungSevere_day_firstBetaChange;
    private final double betaAdultSevere_day_firstBetaChange;
    private final double betaElderlySevere_day_firstBetaChange;
    //variant
    private final double betaYoungMild_night_variant;
    private final double betaAdultMild_night_variant;
    private final double betaElderlyMild_night_variant;
    private final double betaYoungSevere_night_variant;
    private final double betaAdultSevere_night_variant;
    private final double betaElderlySevere_night_variant;
    private final double betaYoungMild_day_variant;
    private final double betaAdultMild_day_variant;
    private final double betaElderlyMild_day_variant;
    private final double betaYoungSevere_day_variant;
    private final double betaAdultSevere_day_variant;
    private final double betaElderlySevere_day_variant;


    public BetaFirstAdjustmentPhaseRate(
            LockdownRatesManager lrm,
            Parameters params,
            CovidVariantParameters covidParams) {
        this.lrm = lrm;
        this.params = params;
        this.betaYoungSevere_night = params.getsToE_Severe_YoungRate_Night();
        this.betaAdultSevere_night = params.getsToE_Severe_AdultRate_Night();
        this.betaElderlySevere_night = params.getsToE_Severe_ElderlyRate_Night();
        this.betaYoungSevere_day = params.getsToE_Severe_YoungRate_Day();
        this.betaAdultSevere_day = params.getsToE_Severe_AdultRate_Day();
        this.betaElderlySevere_day = params.getsToE_Severe_ElderlyRate_Day();
        this.betaYoungMild_night_firstBetaChange = params.getFirstBetaFactor()*params.getsToE_Mild_YoungRate_Night()*params.getsToE_Severe_YoungRate_Night(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaAdultMild_night_firstBetaChange = params.getFirstBetaFactor()*params.getsToE_Mild_AdultRate_Night()*params.getsToE_Severe_AdultRate_Night(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaElderlyMild_night_firstBetaChange = params.getFirstBetaFactor()*params.getsToE_Mild_ElderlyRate_Night()*params.getsToE_Severe_ElderlyRate_Night(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaYoungSevere_night_firstBetaChange = params.getFirstBetaFactor()*this.betaYoungSevere_night;
        this.betaAdultSevere_night_firstBetaChange = params.getFirstBetaFactor()*this.betaAdultSevere_night;
        this.betaElderlySevere_night_firstBetaChange = params.getFirstBetaFactor()*betaElderlySevere_night;
        this.betaYoungMild_day_firstBetaChange = params.getFirstBetaFactor()*params.getsToE_Mild_YoungRate_Day()*params.getsToE_Severe_YoungRate_Day(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaAdultMild_day_firstBetaChange = params.getFirstBetaFactor()*params.getsToE_Mild_AdultRate_Day()*params.getsToE_Severe_AdultRate_Day(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaElderlyMild_day_firstBetaChange = params.getFirstBetaFactor()* params.getsToE_Mild_ElderlyRate_Day()*params.getsToE_Severe_ElderlyRate_Day(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaYoungSevere_day_firstBetaChange = params.getFirstBetaFactor()*this.betaYoungSevere_day;
        this.betaAdultSevere_day_firstBetaChange= params.getFirstBetaFactor()*this.betaAdultSevere_day;
        this.betaElderlySevere_day_firstBetaChange = params.getFirstBetaFactor()*this.betaElderlySevere_day;
        this.betaYoungMild_night_variant = params.getFirstBetaFactor()*covidParams.getsToE_Mild_YoungRate_Night_covidVariant() * covidParams.getsToE_Severe_YoungRate_Night_covidVariant(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaAdultMild_night_variant = params.getFirstBetaFactor()*covidParams.getsToE_Mild_AdultRate_Night_covidVariant() * covidParams.getsToE_Severe_AdultRate_Night_covidVariant(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaElderlyMild_night_variant = params.getFirstBetaFactor()*covidParams.getsToE_Mild_ElderlyRate_Night_covidVariant() * covidParams.getsToE_Severe_ElderlyRate_Night_covidVariant(); //Note remember mild rate is fitted as a multiplier to severe night rate
        this.betaYoungSevere_night_variant = params.getFirstBetaFactor()*covidParams.getsToE_Severe_YoungRate_Night_covidVariant();
        this.betaAdultSevere_night_variant = params.getFirstBetaFactor()*covidParams.getsToE_Severe_AdultRate_Night_covidVariant();
        this.betaElderlySevere_night_variant = params.getFirstBetaFactor()*covidParams.getsToE_Severe_ElderlyRate_Night_covidVariant();
        this.betaYoungMild_day_variant = params.getFirstBetaFactor()*covidParams.getsToE_Mild_YoungRate_Day_covidVariant() * covidParams.getsToE_Severe_YoungRate_Day_covidVariant(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaAdultMild_day_variant = params.getFirstBetaFactor()*covidParams.getsToE_Mild_AdultRate_Day_covidVariant() * covidParams.getsToE_Severe_AdultRate_Day_covidVariant(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaElderlyMild_day_variant = params.getFirstBetaFactor()*covidParams.getsToE_Mild_ElderlyRate_Day_covidVariant() * covidParams.getsToE_Severe_ElderlyRate_Day_covidVariant(); //Note remember mild rate is fitted as a multiplier to severe day rate
        this.betaYoungSevere_day_variant = params.getFirstBetaFactor()*covidParams.getsToE_Severe_YoungRate_Day_covidVariant();
        this.betaAdultSevere_day_variant = params.getFirstBetaFactor()*covidParams.getsToE_Severe_AdultRate_Day_covidVariant();
        this.betaElderlySevere_day_variant = params.getFirstBetaFactor()*covidParams.getsToE_Severe_ElderlyRate_Day_covidVariant();
    }

    public double getBeta(InfectionState state,int location) {
        double transModRatePerCA = lrm.getTansmissionModifierBasedOnTransmissionIndex(location);
            if (state == MILD_INFECTIOUS_YOUNG) {
                if (lrm.isDay()) return betaYoungMild_day_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaYoungMild_night_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == SEVERE_INFECTIOUS_YOUNG) {
                if (lrm.isDay()) return betaYoungSevere_day_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaYoungSevere_night_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == MILD_INFECTIOUS_ADULT) {
                if (lrm.isDay()) return betaAdultMild_day_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaAdultMild_night_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == SEVERE_INFECTIOUS_ADULT) {
                if (lrm.isDay()) return betaAdultSevere_day_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaAdultSevere_night_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == MILD_INFECTIOUS_ELDERLY) {
                if (lrm.isDay()) return betaElderlyMild_day_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaElderlyMild_night_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == MILD_INFECTIOUS_YOUNG_VARIANT) {
                if (lrm.isDay()) return betaYoungMild_day_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaYoungMild_night_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == SEVERE_INFECTIOUS_YOUNG_VARIANT) {
                if (lrm.isDay()) return betaYoungSevere_day_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaYoungSevere_night_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == MILD_INFECTIOUS_ADULT_VARIANT) {
                if (lrm.isDay()) return betaAdultMild_day_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaAdultMild_night_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == SEVERE_INFECTIOUS_ADULT_VARIANT) {
                if (lrm.isDay()) return betaAdultSevere_day_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaAdultSevere_night_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == MILD_INFECTIOUS_ELDERLY_VARIANT) {
                if (lrm.isDay()) return betaElderlyMild_day_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaElderlyMild_night_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else if (state == SEVERE_INFECTIOUS_ELDERLY_VARIANT) {
                if (lrm.isDay()) return betaElderlySevere_day_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaElderlySevere_night_variant* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            } else {
                if (lrm.isDay()) return betaElderlySevere_day_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
                else return betaElderlySevere_night_firstBetaChange* transModRatePerCA *lrm.getLocalLockdownMultiplier(location);
            }
        }

}
