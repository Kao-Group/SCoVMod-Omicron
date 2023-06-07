package scovmod.model.seeding.recovered;

import scovmod.model.input.RecoveredSeedingGroupAttributes;
import scovmod.model.input.SpatialSeedingGroupAttributes;
import scovmod.model.input.config.Parameters;
import scovmod.model.util.math.Random;

import java.util.Map;


public class RecoveredSpatialGroupMultinomial {
    private Random rand;
    private RecoveredSeedingGroupAttributes seedingGroupWeightsReader;
    private int numberToSeed;
    private Map<Integer,Double> seedingGroupWeightsMap;


    public RecoveredSpatialGroupMultinomial(
            Parameters params,
            Random rand,
            RecoveredSeedingGroupAttributes seedingGroupWeightsReader) {

        this.rand = rand;
        this.seedingGroupWeightsReader = seedingGroupWeightsReader;
        this.numberToSeed = (int) params.getNumRecoveredSeeds();
        seedingGroupWeightsMap = seedingGroupWeightsReader.getRecoveredGroupWeightsMap();

    }

    public Map<Integer,Integer> getNumberRecoveredSeedsPerGroupMap() {
        return rand.nextMultinomialTrials(seedingGroupWeightsMap,numberToSeed);
    }
}
