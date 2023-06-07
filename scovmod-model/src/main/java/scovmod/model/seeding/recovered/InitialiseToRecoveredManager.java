package scovmod.model.seeding.recovered;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.state.infection.InfectionState;

public class InitialiseToRecoveredManager {

    private StateModifier sm;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;
    private NationalRecoveredSamplerFactory nrsf;

    public InitialiseToRecoveredManager(
            NationalRecoveredSamplerFactory nrsf,
            StateModifier sm,
            StartLocationsAndAgeClasses sl) {
        this.sm = sm;
        peopleAgeClasses = sl.getPeopleAgeClasses();
        this.nrsf = nrsf;

    }

    public void initialiseRecoveredPeople(Int2ObjectMap<IntSet> peopleByLocation) {
        NationalRecoveredSampler nrs = nrsf.build(peopleByLocation);
        IntSet sampledPeople = nrs.getSampledPeople();
        for (int personID : sampledPeople) {
            AgeClass ageClass = peopleAgeClasses.get(personID);
            //Get what age group the person is in
            switch (ageClass) {
                case YOUNG:
                    sm.updateInfectionState(personID, InfectionState.RECOVERED_YOUNG);
                    break;
                case ADULT:
                    sm.updateInfectionState(personID, InfectionState.RECOVERED_ADULT);
                    break;
                case ELDERLY:
                    sm.updateInfectionState(personID, InfectionState.RECOVERED_ELDERLY);
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for seeding recovered event");
            }
        }
    }
}
