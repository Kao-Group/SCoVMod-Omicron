package scovmod.model.seeding.recovered;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.seeding.NationalSampler;
import scovmod.model.seeding.SpatialGroupMultinomial;
import scovmod.model.seeding.WithinGroupSampler;

public class NationalRecoveredSamplerFactory {

    private RecoveredSpatialGroupMultinomial rsgs;
    private WithinGroupRecoveredSampler wgrs;

    public NationalRecoveredSamplerFactory(
            RecoveredSpatialGroupMultinomial rsgs,
            WithinGroupRecoveredSampler wgrs
    ){
        this.rsgs = rsgs;
        this.wgrs = wgrs;
    }

    public NationalRecoveredSampler build(Int2ObjectMap<IntSet> peopleByLocation) {
        return new NationalRecoveredSampler(
                rsgs,
                wgrs,
                peopleByLocation
        );
    }
}
