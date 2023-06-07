package scovmod.model.transition.infected.variant;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class HospitalisedToRecoveredVariantTransitionExecutor {

    private Random rnd;
    private double timeStep;
    private final CovidVariantParameters params;
    private StateModifier sm;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;

    public HospitalisedToRecoveredVariantTransitionExecutor(
            StateModifier sm,
            Random rnd,
            double timeStep,
            CovidVariantParameters params,
            StartLocationsAndAgeClasses slaac
    ) {
        this.rnd = rnd;
        this.sm = sm;
        this.timeStep = timeStep;
        this.params = params;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
    }

    public void apply(int personID) {
        AgeClass ageClass = peopleAgeClasses.getOrDefault(personID,AgeClass.YOUNG);
            //Get what age group the person is in
            switch(ageClass){
                case YOUNG:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gethToR_YoungRate_covidVariant() * timeStep)) {
                        sm.updateInfectionState(personID, RECOVERED_YOUNG_VARIANT);
                    }
                    break;
                case ADULT:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gethToR_AdultRate_covidVariant() * timeStep)) {
                        sm.updateInfectionState(personID, RECOVERED_ADULT_VARIANT);
                    }
                    break;
                case ELDERLY:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gethToR_ElderlyRate_covidVariant() * timeStep)) {
                        sm.updateInfectionState(personID, RECOVERED_ELDERLY_VARIANT);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for recovered event");
            }
    }
}