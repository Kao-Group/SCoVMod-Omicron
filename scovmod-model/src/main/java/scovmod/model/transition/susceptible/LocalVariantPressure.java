/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.susceptible;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.config.Parameters;
import scovmod.model.state.InfectiousProportion;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.transition.beta.BetaRatesMgr;

public class LocalVariantPressure {

    private StateQuery sq;
    private Parameters params;
    private InfectiousProportion ip;
    private BetaRatesMgr br;

    public LocalVariantPressure(StateQuery sq, Parameters params, InfectiousProportion ip, BetaRatesMgr br) {
        this.sq = sq;
        this.params = params;
        this.ip = ip;
        this.br = br;
    }

    public void addLocalVariantPressure(Int2ObjectMap<InfectionPressure> pressureMap) {
        for (int infLocId : sq.getMildInfectiousFromVariantYoungLocations()) {
            double localVariantPressure = br.getBeta(InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT,infLocId) * ip.inCommunity(infLocId, InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentVariantPressure(localVariantPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(localVariantPressure, 0));
        }
        for (int infLocId : sq.getMildInfectiousFromVariantAdultLocations()) {
            double localVariantPressure = br.getBeta(InfectionState.MILD_INFECTIOUS_ADULT_VARIANT,infLocId) * ip.inCommunity(infLocId, InfectionState.MILD_INFECTIOUS_ADULT_VARIANT);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentVariantPressure(localVariantPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(localVariantPressure, 0));
        }
        for (int infLocId : sq.getMildInfectiousFromVariantElderlyLocations()) {
            double localVariantPressure = br.getBeta(InfectionState.MILD_INFECTIOUS_ELDERLY_VARIANT,infLocId) * ip.inCommunity(infLocId, InfectionState.MILD_INFECTIOUS_ELDERLY_VARIANT);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentVariantPressure(localVariantPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(localVariantPressure, 0));
        }
        for (int infLocId : sq.getSevereInfectiousFromVariantYoungLocations()) {
            double localVariantPressure = br.getBeta(InfectionState.SEVERE_INFECTIOUS_YOUNG_VARIANT,infLocId) * ip.inCommunity(infLocId, InfectionState.SEVERE_INFECTIOUS_YOUNG_VARIANT);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentVariantPressure(localVariantPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(localVariantPressure, 0));
        }
        for (int infLocId : sq.getSevereInfectiousFromVariantAdultLocations()) {
            double localVariantPressure = br.getBeta(InfectionState.SEVERE_INFECTIOUS_ADULT_VARIANT,infLocId) * ip.inCommunity(infLocId, InfectionState.SEVERE_INFECTIOUS_ADULT_VARIANT);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentVariantPressure(localVariantPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(localVariantPressure, 0));
        }
        for (int infLocId : sq.getSevereInfectiousFromVariantElderlyLocations()) {
            double localVariantPressure = br.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT,infLocId) * ip.inCommunity(infLocId, InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT);
            if (pressureMap.containsKey(infLocId))
                pressureMap.put(infLocId, pressureMap.get(infLocId).augmentVariantPressure(localVariantPressure));
            else
                pressureMap.put(infLocId, new InfectionPressure(localVariantPressure, 0));
        }
    }

}
