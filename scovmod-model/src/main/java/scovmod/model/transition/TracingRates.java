package scovmod.model.transition;

import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.lockdown.LockdownTriggerManager;
import scovmod.model.state.StateQuery;
import scovmod.model.time.TimeManager;


public class TracingRates {

    private final ConfigParameters config;
    private final LockdownTriggerManager ltm;
    private TimeManager tm;

    public TracingRates(ConfigParameters config, LockdownTriggerManager ltm, TimeManager tm) {
        this.config = config;
        this.ltm = ltm;
        this.tm = tm;
    }

    public boolean isInLocalLockdown(int location){
        if ( tm.getTimeStep() >=config.getLocalLockdownStartTimeStep()
        && config.isLocalLockdownsActivated() //TODO Probably don't need these first two as a location will not be in lcockdown if they aren't true anyway
        && ltm.isLocationInAreaUnderLockdown(location)) return true;
        else return false;
    }

    public double getTracingRate(double currentRate,int location) {
        if (isInLocalLockdown(location)) { //This supersedes the others? TODO - find out correct possible hierarchy/timing
            return currentRate*config.getLocalLockdownContactTracingRateMultiplier();
        } else {
            return currentRate;
        }
    }
}
