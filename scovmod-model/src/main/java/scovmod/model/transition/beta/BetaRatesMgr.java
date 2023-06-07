package scovmod.model.transition.beta;

import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.lockdown.LockdownRatesManager;
import scovmod.model.state.infection.InfectionState;

public class BetaRatesMgr {

    private final LockdownRatesManager lrm;

    public static BetaRatesMgr build(
            LockdownRatesManager lrm,
            Parameters params,
            CovidVariantParameters covidParams) {
        return new BetaRatesMgr(
                new BetaFittedPhasedRate(lrm,params,covidParams),
                new BetaFirstAdjustmentPhaseRate(lrm,params,covidParams),
                new BetaMatrixPhaseRate(lrm,params,covidParams),
                lrm);

    }

    private final BetaFittedPhasedRate bfr;
    private final BetaFirstAdjustmentPhaseRate bfar;
    private final BetaMatrixPhaseRate bmr;

    public BetaRatesMgr(
            BetaFittedPhasedRate bfr,
            BetaFirstAdjustmentPhaseRate bfar,
            BetaMatrixPhaseRate bmr,
            LockdownRatesManager lrm) {
        this.bfr = bfr;
        this.bfar = bfar;
        this.bmr = bmr;
        this.lrm = lrm;
    }

    public double getBeta(InfectionState state,int location) {
        if (lrm.isDuringSecondBetaChange()) { // involves matrix etc.
            return bmr.getBeta(state,location);
        }
        if (!lrm.isDuringFirstBetaChange()) {
            return bfr.getBeta(state,location); //Fitted period
        } else  {
            return bfar.getBeta(state,location); //Period between fitted and matrix
        }
    }
}
