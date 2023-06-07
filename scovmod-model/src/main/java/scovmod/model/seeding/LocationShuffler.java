package scovmod.model.seeding;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.util.math.Random;

import java.util.List;

public class LocationShuffler {
    private Int2ObjectMap<List<Integer>> oasByIZ;
    private Random rand;

    public LocationShuffler(
            HealthBoardLookup hbl,
            Random rand) {
        this.oasByIZ = hbl.getOasByIZ();
        this.rand = rand;
    }

    public List<Integer> shuffleLocationsInGroup(int iz) {
        return rand.shuffleList(oasByIZ.get(iz));
    }
}
