package scovmod.model.movements;

import scovmod.model.lockdown.LockdownTriggerManager;
import scovmod.model.state.StateQuery;

import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.state.infection.InfectionState;

import java.util.HashSet;
import java.util.Set;

public class Resolver {

    private final StateQuery sq;
    private static int count = 0;
    private LockdownTriggerManager ltm;

    public Resolver(StateQuery sq,LockdownTriggerManager ltm) {
        this.sq = sq;
        this.ltm = ltm;
    }

    public Set<ResolvedMovement> apply(LocationIncomingPersons incomingLocMovs,
                                       IntSet allExposedLocations,
                                       IntSet allMildInfectiousLocations,
                                       IntSet allSevereInfectiousLocations,
                                       IntSet allExposedVariantLocations,
                                       IntSet allMildInfectiousVariantLocations,
                                       IntSet allSevereInfectiousVariantLocations) {
        int destinationID = incomingLocMovs.getLocationId();
        boolean destinationInfected = false;
        if(allExposedLocations.contains(destinationID)
                || allMildInfectiousLocations.contains(destinationID)
                || allSevereInfectiousLocations.contains(destinationID)
                || allExposedVariantLocations.contains(destinationID)
                || allMildInfectiousVariantLocations.contains(destinationID)
                || allSevereInfectiousVariantLocations.contains(destinationID)) destinationInfected = true;
        boolean destinationInLockdown = ltm.isLocationInAreaUnderLockdown(destinationID);

        IntSet incomingPersons = incomingLocMovs.getNewPersonIds();
        Set<ResolvedMovement> resolvedMovements = new HashSet<>();
        for (int personID : incomingPersons) {
            int sourceID = sq.getPersonLocation(personID);
            boolean sourceInLockdown = ltm.isLocationInAreaUnderLockdown(sourceID);
            if(sourceID==destinationID) continue; //Have missed move to work so ignore move to self?
            InfectionState state = sq.getPersonInfectionStatus(personID);
            if (isShowingSevereSymptoms(state)) {
                continue; //No matter what ignore moves for these people - in real life they obviously would not commute
            } else if(destinationInLockdown || sourceInLockdown) {
                continue;
            } else {
                    //This is an optimisation to speed up processing of movements - only move those who could spread covid
                    if(destinationInfected
                            && ((state != InfectionState.RECOVERED_YOUNG)
                            || (state != InfectionState.RECOVERED_ADULT)
                            || (state != InfectionState.RECOVERED_ELDERLY)
                            || (state != InfectionState.RECOVERED_YOUNG_VARIANT)
                            || (state != InfectionState.RECOVERED_ADULT_VARIANT)
                            || (state != InfectionState.RECOVERED_ELDERLY_VARIANT))) {
                        resolvedMovements.add(
                                new ResolvedMovement(
                                        personID,
                                        sourceID,
                                        destinationID,
                                        state
                                ));
                    } else if ((state == InfectionState.EXPOSED_YOUNG)
                            || (state == InfectionState.EXPOSED_ADULT)
                            || (state == InfectionState.EXPOSED_ELDERLY)
                            || (state == InfectionState.MILD_INFECTIOUS_YOUNG)
                            || (state == InfectionState.MILD_INFECTIOUS_ADULT)
                            || (state == InfectionState.MILD_INFECTIOUS_ELDERLY)
                            ||  (state == InfectionState.EXPOSED_YOUNG_VARIANT)
                        || (state == InfectionState.EXPOSED_ADULT_VARIANT)
                        || (state == InfectionState.EXPOSED_ELDERLY_VARIANT)
                        || (state == InfectionState.MILD_INFECTIOUS_YOUNG_VARIANT)
                        || (state == InfectionState.MILD_INFECTIOUS_ADULT_VARIANT)
                        || (state == InfectionState.MILD_INFECTIOUS_ELDERLY_VARIANT)){
                        resolvedMovements.add(
                                new ResolvedMovement(
                                        personID,
                                        sourceID,
                                        destinationID,
                                        state
                                ));
                    } else {
                        continue;
                    }
                }
        }
        return resolvedMovements;
    }

    private boolean isShowingSevereSymptoms(InfectionState state) {
        if(state==InfectionState.SEVERE_INFECTIOUS_YOUNG
                || state==InfectionState.SEVERE_INFECTIOUS_ADULT
                || state==InfectionState.SEVERE_INFECTIOUS_ELDERLY
                || state==InfectionState.HOSPITALISED_YOUNG
                || state==InfectionState.HOSPITALISED_ADULT
                || state==InfectionState.HOSPITALISED_ELDERLY
                || state==InfectionState.DEAD_YOUNG
                || state==InfectionState.DEAD_ADULT
                || state==InfectionState.DEAD_ELDERLY
                || state==InfectionState.SEVERE_INFECTIOUS_YOUNG_VARIANT
                || state==InfectionState.SEVERE_INFECTIOUS_ADULT_VARIANT
                || state==InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT
                || state==InfectionState.HOSPITALISED_YOUNG_VARIANT
                || state==InfectionState.HOSPITALISED_ADULT_VARIANT
                || state==InfectionState.HOSPITALISED_ELDERLY_VARIANT
                || state==InfectionState.DEAD_YOUNG_VARIANT
                || state==InfectionState.DEAD_ADULT_VARIANT
                || state==InfectionState.DEAD_ELDERLY_VARIANT){ return true;}
        else {return false;}
    }
}
