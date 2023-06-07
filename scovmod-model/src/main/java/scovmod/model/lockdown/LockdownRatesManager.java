package scovmod.model.lockdown;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.TransmissionModIndexPerCouncilArea;
import scovmod.model.input.SpecifiedLockdownDetailsReader;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.time.TimeManager;

public class LockdownRatesManager {


    private final TimeManager tm;
    private final LockdownTriggerManager ltm;
    private final Parameters params;
    private final SpecifiedLockdownDetailsReader cawsl;
    private final HealthBoardLookup hbl;
    private ConfigParameters config;
    private final Int2DoubleMap transIndexPerLA;

    public LockdownRatesManager(
            TimeManager tm,
            ConfigParameters config,
            LockdownTriggerManager ltm,
            Parameters params,
            SpecifiedLockdownDetailsReader cawsl,
            HealthBoardLookup hbl,
            TransmissionModIndexPerCouncilArea aipca) {
        this.tm = tm;
        this.ltm = ltm;
        this.params = params;
        this.cawsl = cawsl;
        this.hbl = hbl;
        this.config = config;
        transIndexPerLA = aipca.getAccessIndexPerCA();
    }

    public boolean isInLocalLockdown(int location){
        if ( tm.getTimeStep() >=config.getLocalLockdownStartTimeStep()
                && config.isLocalLockdownsActivated() //TODO Probably don't need these first two as a location will not be in lcockdown if they aren't true anyway
                && ltm.isLocationInAreaUnderLockdown(location)) return true;
        else return false;
    }

    public double getLocalLockdownMultiplier(int location){
        if (isInLocalLockdown(location)) {
            return params.getBetaFactorForLocalLockdown();
        } else return 1;
    }

    public boolean isDay(){
        if ( tm.getTimeStep() % 2 == 0 ) return true;
        else return false;
    }

    public boolean isDuringFirstBetaChange(){
        if ( tm.getTimeStep() >=config.getFirstBetaFactorTimeStep() ) return true;
        else return false;
    }

    public boolean isDuringSecondBetaChange(){
        if ( tm.getTimeStep() >=config.getSecondBetaFactorTimeStep() ) return true;
        else return false;
    }

    public double getSpecifiedBetaFactor(int location){
        int ca = hbl.getCouncilAreaFromOA(location);
        return cawsl.getSpecifiedBetaMultiplier(ca);
    }

    //TODO Probably should have its own calculator component
    public double getTansmissionModifierBasedOnTransmissionIndex(int location) {
        int councilArea = hbl.getCouncilAreaFromOA(location);
        double Tr_ca = transIndexPerLA.get(councilArea);
        if(Tr_ca==0) return 1;
        double trans_modifier = params.getTransIndexModifier();
        double Tr_av = params.getAverageTransIndexPerCouncilArea(); //NB This is currently CA average
        return (1+(trans_modifier*(Tr_ca-Tr_av)));
    }
}
