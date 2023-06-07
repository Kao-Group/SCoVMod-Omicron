package scovmod.model.seeding.variant;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.VariantSeedsPerCA;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.seeding.reseeding.AreaToReseed;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;
import scovmod.model.util.math.Random;

import java.util.List;
import java.util.Set;

public class VariantSeedManager {

    private Random rand;
    private Int2ObjectMap<Set> areaToReseedMap;
    private TimeManager tm;
    private ConfigParameters config;
    private StateQuery sq;
    private StateModifier sm;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;

    public VariantSeedManager(
            Random rand,
            VariantSeedsPerCA vspca,
            TimeManager tm,
            ConfigParameters config,
            StateQuery sq,
            StateModifier sm,
            StartLocationsAndAgeClasses sl) {
        this.rand = rand;
        areaToReseedMap = vspca.getVariantSeedsPerCAMap();
        this.tm = tm;
        this.config = config;
        this.sq = sq;
        this.sm = sm;
        peopleAgeClasses = sl.getPeopleAgeClasses();
    }

    public void SeedVariantInfections() {
        if (areaToReseedMap.containsKey((int) tm.getTimeStep())) { //There are seeds for this timestep
            Set<AreaToReseed> areasToReseed = this.areaToReseedMap.get((int) tm.getTimeStep());
            //AreaLevels reseedAreaLevel = config.getReseedAreaLevel();
            //if(reseedAreaLevel!=AreaLevels.IZ) throw new UnsupportedOperationException("Unsupported reseeding level type");
            for (AreaToReseed areaToReseed : areasToReseed) {
                List<Integer> peopleToSeed = rand.sampleWithoutReplacement(areaToReseed.getNumberSeeds(),
                        sq.getAllSusceptiblePersonsInArea(AreaLevels.CA, areaToReseed.getLocation()));
                for (int personID : peopleToSeed) {
                    AgeClass ageClass = peopleAgeClasses.get(personID);
                    //Get what age group the person is in
                    switch (ageClass) {
                        case YOUNG:
                            sm.updateInfectionState(personID, InfectionState.EXPOSED_YOUNG_VARIANT);
                            break;
                        case ADULT:
                            sm.updateInfectionState(personID, InfectionState.EXPOSED_ADULT_VARIANT);
                            break;
                        case ELDERLY:
                            sm.updateInfectionState(personID, InfectionState.EXPOSED_ELDERLY_VARIANT);
                            break;
                        default:
                            throw new UnsupportedOperationException("Age class not known- for seed variant event");
                    }
                }
            }
        }
    }
}
