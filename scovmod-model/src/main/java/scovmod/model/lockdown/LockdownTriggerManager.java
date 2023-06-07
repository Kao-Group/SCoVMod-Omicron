/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.lockdown;

import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.StateQuery;
import scovmod.model.time.TimeManager;

public class LockdownTriggerManager {

    private StateQuery sq;
    private AreaLevels lockdownLevel;
    private ConfigParameters config;
    private HealthBoardLookup hbl;
    private RegionalInfectionsTracker rit;
    private LockdownStatusManager lst;
    private double upperThreshold;
    private double lowerThreshold;
    private TimeManager tm;
    private PopulationSizePerAreaLookup pspa;

    public static LockdownTriggerManager build(
            StateQuery sq,
            ConfigParameters config,
            HealthBoardLookup hbl,
            RegionalInfectionsTracker rit,
            LockdownStatusManager lst,
            TimeManager tm,
            PopulationSizePerAreaLookup pspa) {
        return new LockdownTriggerManager(sq,hbl, config, rit, lst,tm, pspa);
    }

    protected LockdownTriggerManager(
            StateQuery sq,
            HealthBoardLookup hbl,
            ConfigParameters config,
            RegionalInfectionsTracker rit,
            LockdownStatusManager lst,
            TimeManager tm, PopulationSizePerAreaLookup pspa) {
        this.sq = sq;
        this.config = config;
        this.rit = rit;
        this.lst = lst;
        this.hbl = hbl;
        this.tm = tm;
        this.pspa = pspa;
        this.lockdownLevel = this.config.getLocalLockdownsAreaLevel();
        upperThreshold = config.getMildInfectiousUpperLevel();
        lowerThreshold = config.getMildInfectiousLowerLevel();
    }

    public void updateAreaLockdownStatuses(){
        if(config.isLocalLockdownsActivated() && tm.getTimeStep()>=config.getLocalLockdownStartTimeStep()) {
            rit.updateCurrentInfectionLevelPerArea();
            switch (lockdownLevel) {
                case HB:
                    for (int hb : hbl.getAllHBs()) {
                        updateStatusForArea(hb);
                    }
                    break;
                case CA:
                    for (int ca : hbl.getAllCAs()) {
                        updateStatusForArea(ca);
                    }
                    break;
                case IZ:
                    for (int iz : hbl.getAllIZs()) {
                        updateStatusForArea(iz);
                    }
                    break;
                case DZ:
                    for (int dz : hbl.getAllDZs()) {
                        updateStatusForArea(dz);
                    }
                    break;
                case OA:
                    for (int oa : sq.getAllActiveLocationIds()) {
                        updateStatusForArea(oa);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported lockdown level type: " + lockdownLevel);
            }
        }
    }

    private void updateStatusForArea(int area){
        int popOfCA = pspa.getPopByCouncilAreaLookup().get(area);
        int currentInfectionLevel = rit.getCurrentInfectionLevelPerArea(area);
        double currentInfectionLevelProportion = (double)currentInfectionLevel/popOfCA;
        if(currentInfectionLevelProportion>=upperThreshold && !lst.isInLockdown(area)){
            lst.enterLockdown(area);
        }
        if(currentInfectionLevelProportion<=lowerThreshold && lst.isInLockdown(area)){
            lst.endLockdown(area);
        }
    }

    public boolean isLocationInAreaUnderLockdown(int oa){
        switch (lockdownLevel) {
            case HB:
                return lst.isInLockdown(hbl.getHealthBoardFromOA(oa));
            case CA:
                return lst.isInLockdown(hbl.getLALookupMap().get(oa));
            case IZ:
                return lst.isInLockdown(hbl.getIZLookupMap().get(oa));
            case DZ:
                return lst.isInLockdown(hbl.getDZLookupMap().get(oa));
            case OA:
                return lst.isInLockdown(oa);
            default:
                throw new UnsupportedOperationException("Unsupported lockdown level type: " + lockdownLevel);
        }
    }

}
