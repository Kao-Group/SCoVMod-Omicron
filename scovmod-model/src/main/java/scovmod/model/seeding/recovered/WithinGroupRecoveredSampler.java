package scovmod.model.seeding.recovered;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.seeding.LocationShuffler;
import scovmod.model.seeding.WithinLocationSampler;

public class WithinGroupRecoveredSampler {

    //private final LocationShuffler ls;
    private final WithinLocationRecoveredSampler wlrs;

    public WithinGroupRecoveredSampler(
     //       LocationShuffler ls,
            WithinLocationRecoveredSampler wlrs) {
      //  this.ls = ls;
        this.wlrs = wlrs;
    }

    public IntSet samplePeopleFromGroup(
            int desiredNumGroupSeeds,
            int oa,
            Int2ObjectMap<IntSet> peopleByLocation) {
        IntSet hbPeopleSampled = new IntOpenHashSet();

            hbPeopleSampled.addAll(wlrs.samplePeopleAtLocation(oa, peopleByLocation,desiredNumGroupSeeds));

        return hbPeopleSampled;
    }
}