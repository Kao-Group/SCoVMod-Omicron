package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.List;

public class WithinGroupSampler {

    private final LocationShuffler ls;
    private final WithinLocationSampler wls;

    public WithinGroupSampler(
            LocationShuffler ls,
            WithinLocationSampler wls) {
        this.ls = ls;
        this.wls = wls;
    }

    public IntSet samplePeopleFromGroup(
            int desiredNumGroupSeeds,
            int iz,
            Int2ObjectMap<IntSet> peopleByLocation) {
        IntSet hbPeopleSampled = new IntOpenHashSet();

        while (hbPeopleSampled.size() < desiredNumGroupSeeds) { //This allows for constant reshuffles until quota is reached - happens for Recovered seeds
            for (int oa : ls.shuffleLocationsInGroup(iz)) { // The list of OAs is shuffled each time
                List<Integer> sampledPeople = wls.samplePeopleAtLocation(oa, iz, peopleByLocation);
                hbPeopleSampled.addAll(sampledPeople);
                if (hbPeopleSampled.size() >= desiredNumGroupSeeds) {
                    break;
                }
            }
        }
        return hbPeopleSampled;
    }


}