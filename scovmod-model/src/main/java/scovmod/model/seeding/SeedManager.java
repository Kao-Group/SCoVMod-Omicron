package scovmod.model.seeding;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.RecoveredSeeds;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.seeding.reseeding.AreaToReseed;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.math.Random;

import java.util.Set;

import static scovmod.model.state.infection.InfectionState.*;

public class SeedManager {

    private StartLocationsAndAgeClasses startLocations;
    private StatisticsCollector stats;
    private NationalSamplerFactory nsf;
    private Random rand;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;
    private Int2ObjectMap<Set> areaToReseedMap;
    private ConfigParameters config;
    private WithinGroupSampler wgs;

    public SeedManager(
            StartLocationsAndAgeClasses sl,
            NationalSamplerFactory nsf,
            Random rand,
            RecoveredSeeds str,
            ConfigParameters config,
            WithinGroupSampler wgs) {
        startLocations = sl;
        this.nsf = nsf;
        this.rand = rand;
        peopleAgeClasses = sl.getPeopleAgeClasses();
        areaToReseedMap = str.getAreaToReseedMap();
        this.config = config;
        this.wgs = wgs;
    }

    public void seedPeopleInfections(
            Int2ObjectMap<InfectionState> personStates,
            Int2ObjectMap<IntSet> peopleByLocation
    ) {
        // Make all people susceptible to start
        IntSet personKeys = startLocations.getLocationsByPeopleId().keySet();
        for (int personID : personKeys) {
            personStates.put(personID, InfectionState.SUSCEPTIBLE);
        }
        NationalSampler ns = nsf.build(peopleByLocation);
        IntSet sampledSeeds = ns.getSampledPeople();
        for (int personID : sampledSeeds) {
            AgeClass ageClass = peopleAgeClasses.get(personID);
            //Get what age group the person is in
            switch (ageClass) {
                case YOUNG:
                    personStates.put(personID, EXPOSED_YOUNG);
                    break;
                case ADULT:
                    personStates.put(personID, EXPOSED_ADULT);
                    break;
                case ELDERLY:
                    personStates.put(personID, EXPOSED_ELDERLY);
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for seeding");
            }
        }
//        //NNB TODO This reseeding on the first time step is now used for seeding with recovereds
//        // It works because it assumes the seeding is at IZ level same as main seeding above
//        Set<AreaToReseed> areasToReseed = this.areaToReseedMap.get((int) config.getFirstTimeStep());
//        for (AreaToReseed areaToReseed : areasToReseed) {
//            IntSet newPeopleToSeed = wgs.samplePeopleFromGroup(areaToReseed.getNumberSeeds(), areaToReseed.getLocation(), peopleByLocation);
//            for (int personID : newPeopleToSeed) {
//                AgeClass ageClass = peopleAgeClasses.get(personID);
//                //Get what age group the person is in
//                switch (ageClass) {
//                    case YOUNG:
//                        personStates.put(personID, RECOVERED_YOUNG);
//                        break;
//                    case ADULT:
//                        personStates.put(personID, RECOVERED_ADULT);
//                        break;
//                    case ELDERLY:
//                        personStates.put(personID, RECOVERED_ELDERLY);
//                        break;
//                    default:
//                        throw new UnsupportedOperationException("Age class not known- for seed recovered event");
//                }
//            }
//        }
    }
}
