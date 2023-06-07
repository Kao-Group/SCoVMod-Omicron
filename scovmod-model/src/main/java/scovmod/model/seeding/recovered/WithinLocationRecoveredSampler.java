package scovmod.model.seeding.recovered;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.RecoveredSeedingGroupAttributes;
import scovmod.model.input.SpatialSeedingGroupAttributes;
import scovmod.model.input.config.Parameters;
import scovmod.model.util.math.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithinLocationRecoveredSampler {
    private final Random rand;


    public WithinLocationRecoveredSampler(
            Random rand) {
        this.rand = rand;
    }


    public List<Integer> samplePeopleAtLocation(
            int locID,
            Int2ObjectMap<IntSet> peopleByLocation,
            int numPeopleToSeed) {

        IntSet locationPeople = peopleByLocation.get(locID);
       //System.out.println("Number of people to seed in group: "+locID+" is "+numPeopleToSeed);
        List<Integer> personIDs = new ArrayList<>();
        if(numPeopleToSeed>0 && locationPeople!=null) {
            if(numPeopleToSeed>locationPeople.size()) {numPeopleToSeed=locationPeople.size();}
            personIDs = rand.sampleWithoutReplacement(numPeopleToSeed, locationPeople);
        }

        //System.out.println("Number of people seeded in group: "+locID+" is "+personIDs.size());

        return personIDs;
    }
}
