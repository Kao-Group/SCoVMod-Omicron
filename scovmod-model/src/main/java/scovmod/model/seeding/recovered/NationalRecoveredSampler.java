package scovmod.model.seeding.recovered;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.seeding.SpatialGroupMultinomial;
import scovmod.model.seeding.WithinGroupSampler;

import java.util.Map;

public class NationalRecoveredSampler {
    private RecoveredSpatialGroupMultinomial rsgm;
    private WithinGroupRecoveredSampler wgrs;

    Int2ObjectMap<IntSet> peopleByLocation;

    public NationalRecoveredSampler(
            RecoveredSpatialGroupMultinomial rsgm,
            WithinGroupRecoveredSampler wgrs,
            Int2ObjectMap<IntSet> peopleByLocation
            ) {
        this.rsgm = rsgm;
        this.wgrs = wgrs;
        this.peopleByLocation = peopleByLocation;
    }

    public IntSet getSampledPeople() {
        IntSet sampledPeople = new IntOpenHashSet();
        Map<Integer,Integer> seedsPerGroup = rsgm.getNumberRecoveredSeedsPerGroupMap();

        for (Map.Entry<Integer, Integer> entry : seedsPerGroup.entrySet()) {
            int groupId = entry.getKey();
            int numPeopleForGroup = entry.getValue(); //Number of people to sample for this particular group
           // System.out.println("Number of recovered required for this group is: "+groupId+" is "+numPeopleForGroup);
            sampledPeople.addAll(
                    wgrs.samplePeopleFromGroup(
                            numPeopleForGroup,
                            groupId,
                            peopleByLocation
                    )
            );
        }
        return sampledPeople;
    }
}
