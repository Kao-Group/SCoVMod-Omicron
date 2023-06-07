package scovmod.model.transition.infected.variant;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.SUSCEPTIBLE;

public class RecoveredFromVariantToSusceptibleTransitionExecutor {

    private Random rnd;
    private double timeStep;
    private final CovidVariantParameters params;
    private StateModifier sm;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;

    public RecoveredFromVariantToSusceptibleTransitionExecutor(
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
                    if (rnd.nextPoissonReturnsOneOrMore(params.getrToS_youngRate_covidVariant() * timeStep)) {
                        sm.updateInfectionState(personID, SUSCEPTIBLE);
                    }
                    break;
                case ADULT:
                    if (rnd.nextPoissonReturnsOneOrMore(params.getrToS_adultRate_covidVariant() * timeStep)) {
                        sm.updateInfectionState(personID, SUSCEPTIBLE);
                    }
                    break;
                case ELDERLY:
                    if (rnd.nextPoissonReturnsOneOrMore(params.getrToS_elderlyRate_covidVariant()* timeStep)) {
                        sm.updateInfectionState(personID, SUSCEPTIBLE);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for recovered to susceptible event");
            }
    }
}