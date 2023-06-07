package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.SpatialSeedingGroupAttributes;
import scovmod.model.input.config.Parameters;
import scovmod.model.util.math.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithinLocationSampler {
    private final SpatialSeedingGroupAttributes seedingGroupWeightsReader;
    private final Random rand;
    private Map<Integer, Double> seedingGroupLoadFactorMap;
    private Parameters params;


    public WithinLocationSampler(
            SpatialSeedingGroupAttributes seedingGroupWeightsReader,
            Random rand,
            Parameters params) {
        this.seedingGroupWeightsReader = seedingGroupWeightsReader;
        this.rand = rand;
        //seedingGroupLoadFactorMap = seedingGroupWeightsReader.getGroupLoadFactor();
        this.params = params;
    }

    private  int getNumPeopleToSeed(int locationID, int groupId, Int2ObjectMap<IntSet> peopleByLocation){
        return 1;
    }

    public List<Integer> samplePeopleAtLocation(
            int locID,
            int groupId,
            Int2ObjectMap<IntSet> peopleByLocation) {

        IntSet locationPeople = peopleByLocation.get(locID);
        //System.out.println("locationPeople: "+locationPeople);
        int numPeopleToSeed = getNumPeopleToSeed(locID,groupId,peopleByLocation);
        List<Integer> personIDs = new ArrayList<>();
        if(numPeopleToSeed>0 && locationPeople!=null)
            personIDs = rand.sampleWithoutReplacement(numPeopleToSeed, locationPeople);

        return personIDs;
    }
}
