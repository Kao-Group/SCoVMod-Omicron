package scovmod.model.state.cache.variant.recovered;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.state.cache.Cache;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;

import static scovmod.model.state.infection.InfectionState.RECOVERED_YOUNG;
import static scovmod.model.state.infection.InfectionState.RECOVERED_YOUNG_VARIANT;

public class RecoveredFromVariantYoungLocations implements Cache {

    IntSet locations = new IntOpenHashSet();

    public void wholeLocationUpdate(int locationId, LocalPopulation population) {
        if (!population.getAllInState(RECOVERED_YOUNG_VARIANT).isEmpty()) {
            locations.add(locationId);
        } else {
            locations.remove(locationId);
        }
    }

    public void notifyNonEdgeCaseStateChange(
            int personId,
            InfectionState oldState,
            InfectionState newState,
            int locationId) {
        // Ignored since not relevant
    }

    public IntSet getRecoveredFromVariantYoungLocations() {
        return new IntOpenHashSet(locations);
    }

    public void notifyNonEdgeCaseMovement(
            int personId,
            boolean isOnMovement,
            int locationId,
            InfectionState state) {
        // Ignored since not relevant
    }

}
