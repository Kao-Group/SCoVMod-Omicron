/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.lockdown;

import it.unimi.dsi.fastutil.ints.*;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;

import static scovmod.model.input.config.AreaLevels.*;

public class RegionalInfectionsTracker {

    private final Int2IntMap infectionCachePerArea = new Int2IntOpenHashMap();
    private StateQuery sq;
    private AreaLevels lockdownLevel;
    private ConfigParameters config;
    private HealthBoardLookup hbl;
    private Int2IntMap mildInfectionEventPerLocation = new Int2IntOpenHashMap();

    public static RegionalInfectionsTracker build(
            StateQuery sq,
            HealthBoardLookup hbl,
            ConfigParameters config) {
        return new RegionalInfectionsTracker(sq,hbl, config);
    }

    protected RegionalInfectionsTracker(
            StateQuery sq,
            HealthBoardLookup hbl, ConfigParameters config) {
        this.sq = sq;
        this.config = config;
        this.lockdownLevel = this.config.getLocalLockdownsAreaLevel();
        this.hbl = hbl;
        //Need to initialise maps based on area operating at.
        switch (lockdownLevel) {
            case HB:
                for(int hb:hbl.getAllHBs()) {
                    infectionCachePerArea.put(hb,0);
                }
                break;
            case CA:
                for(int ca:hbl.getAllCAs()) {
                    infectionCachePerArea.put(ca,0);
                }
                break;
            case IZ:
                for(int iz:hbl.getAllIZs()) {
                    infectionCachePerArea.put(iz,0);
                }
                break;
            case DZ:
                for(int dz:hbl.getAllDZs()) {
                    infectionCachePerArea.put(dz,0);
                }
                break;
            case OA:
                for(int oa:sq.getAllActiveLocationIds()) {
                    infectionCachePerArea.put(oa,0);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported reseeding level type: " + lockdownLevel);
        }
    }

    public void updateCurrentInfectionLevelPerArea(){
        //Get all locations in area
        switch (lockdownLevel) {
            case HB:
                for(int hb:hbl.getAllHBs()) {
                    infectionCachePerArea.put(hb,getTotalMIEventsForArea(sq.getAllLocationsInArea(HB,hb)));
                }
                break;
            case CA:
                for(int ca:hbl.getAllCAs()) {
                    infectionCachePerArea.put(ca,getTotalMIEventsForArea(sq.getAllLocationsInArea(CA,ca)));
                }
                break;
            case IZ:
                for(int iz:hbl.getAllIZs()) {
                    infectionCachePerArea.put(iz,getTotalMIEventsForArea(sq.getAllLocationsInArea(IZ,iz)));
                }
                break;
            case DZ:
                for(int dz:hbl.getAllDZs()) {
                    infectionCachePerArea.put(dz,getTotalMIEventsForArea(sq.getAllLocationsInArea(DZ,dz)));
                }
                break;
            case OA:
                for(int oa:sq.getAllActiveLocationIds()) {
                    infectionCachePerArea.put(oa,getTotalMIEventsForArea(sq.getAllLocationsInArea(OA,oa)));
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported reseeding level type: " + lockdownLevel);
        }
    }

    public int getCurrentInfectionLevelPerArea(int areaID){
        return infectionCachePerArea.get(areaID);
    }

//    private int getTotalSIForArea(IntSet alllocations) {
//        int totalSI = 0;
//        for (int location : alllocations) {
//            LocalPopulation localPop = sq.getCopyOfLocalPopulation(location);
//            int noSI = localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_YOUNG).size() +
//                    localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ADULT).size() +
//                    localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ELDERLY).size();
//            totalSI += noSI;
//        }
//        return totalSI;
//    }

//    private int getTotalMIForArea(IntSet alllocations) {
//        int totalSI = 0;
//        for (int location : alllocations) {
//            LocalPopulation localPop = sq.getCopyOfLocalPopulation(location);
//            int noSI = localPop.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG).size() +
//                    localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT).size() +
//                    localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY).size();
//            totalSI += noSI;
//        }
//        return totalSI;
//    }

    private int getTotalMIEventsForArea(IntSet alllocations) {
        int totalMIEvents = 0;
        for (int location : alllocations) {
            if(mildInfectionEventPerLocation.containsKey(location)) totalMIEvents+=mildInfectionEventPerLocation.get(location);
        }
        return totalMIEvents;
    }

    public void newMildInfectious(int location) {
        if (mildInfectionEventPerLocation.containsKey(location)) {
            int currentCount = mildInfectionEventPerLocation.get(location);
            mildInfectionEventPerLocation.put(location, currentCount + 1);
        } else {
            mildInfectionEventPerLocation.put(location, 1);
        }
    }

    public void resetMildInfectiousEvents(){
        infectionCachePerArea.clear();
        mildInfectionEventPerLocation.clear();
    }
}
