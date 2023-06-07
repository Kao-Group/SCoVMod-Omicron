/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.susceptible;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.transition.TransitionExecutor;

import java.util.Set;

public class FromSusceptible {
    private final LocalPersonPressure lap;
    private final LocalVariantPressure lvp;
    private final VisitorPressure vp;
    private final TransitionExecutor te;

    public FromSusceptible(LocalPersonPressure lap, LocalVariantPressure lvp, VisitorPressure vp, TransitionExecutor te) {
        this.lap = lap;
        this.vp = vp;
        this.te = te;
        this.lvp = lvp;
    }

    public void doTransitions(Set<LocationIncomingPersons> setOfIncomingPeople) {
        Int2ObjectMap<InfectionPressure> locationPressures = new Int2ObjectOpenHashMap<>();
        vp.addVisitorPresssure(setOfIncomingPeople, locationPressures);
        lap.addLocalPressure(locationPressures);
        lvp.addLocalVariantPressure(locationPressures);
        te.doSusceptibleTransitions(locationPressures);
    }
}
