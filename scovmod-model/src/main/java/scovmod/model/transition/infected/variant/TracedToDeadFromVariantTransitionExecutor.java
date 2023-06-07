package scovmod.model.transition.infected.variant;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class TracedToDeadFromVariantTransitionExecutor {

    private Random rnd;
    private double timeStep;
    private final CovidVariantParameters params;
    private StateModifier sm;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;
   // private final DeathRates dr;

    public TracedToDeadFromVariantTransitionExecutor(
            StateModifier sm,
            Random rnd,
            double timeStep,
            CovidVariantParameters params,
            StartLocationsAndAgeClasses slaac
            /*DeathRates dr*/) {
        this.rnd = rnd;
        this.sm = sm;
        this.timeStep = timeStep;
        this.params = params;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
        //this.dr = dr;
    }

    public void apply(int personID) {
        AgeClass ageClass = peopleAgeClasses.get(personID);
        //double deathRateModPerHB = dr.getDeathRateModifierBasedOnHealthIndex(personID);
            //Get what age group the person is in
            switch(ageClass){
                case YOUNG:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gettToD_YoungRate_covidVariant() /** deathRateModPerHB*/ * timeStep)) {
                        sm.updateInfectionState(personID, DEAD_YOUNG_VARIANT);
                    }
                    break;
                case ADULT:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gettToD_AdultRate_covidVariant() /** deathRateModPerHB*/ * timeStep)) {
                        sm.updateInfectionState(personID, DEAD_ADULT_VARIANT);
                    }
                    break;
                case ELDERLY:
                    if (rnd.nextPoissonReturnsOneOrMore(params.gettToD_ElderlyRate_covidVariant() /** deathRateModPerHB*/ * timeStep)) {
                        sm.updateInfectionState(personID, DEAD_ELDERLY_VARIANT);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for dead event");
            }
    }
}