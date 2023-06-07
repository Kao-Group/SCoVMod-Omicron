package scovmod.model.transition.infected;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.transition.TracingRates;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class ExposedToTracedTransitionExecutor {

    private Random rnd;
    private double timeStep;
    private final Parameters params;
    private StateModifier sm;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;
    private TracingRates tr;
    private StateQuery sq;

    public ExposedToTracedTransitionExecutor(
            StateModifier sm,
            Random rnd,
            double timeStep,
            Parameters params,
            StartLocationsAndAgeClasses slaac,
            TracingRates tr,
            StateQuery sq) {
        this.rnd = rnd;
        this.sm = sm;
        this.timeStep = timeStep;
        this.params = params;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
        this.tr = tr;
        this.sq = sq;
    }

    public void apply(int personID) {
        AgeClass ageClass = peopleAgeClasses.getOrDefault(personID,AgeClass.YOUNG);
        int location = sq.getPersonLocation(personID);
            //Get what age group the person is in
            switch(ageClass){
                case YOUNG:
                    if (rnd.nextPoissonReturnsOneOrMore(tr.getTracingRate(params.geteToT_youngRate(),location) * timeStep)) {
                        sm.updateInfectionState(personID, TRACED_YOUNG);
                    }
                    break;
                case ADULT:
                    if (rnd.nextPoissonReturnsOneOrMore(tr.getTracingRate(params.geteToT_adultRate(),location) * timeStep)) {
                        sm.updateInfectionState(personID, TRACED_ADULT);
                    }
                    break;
                case ELDERLY:
                    if (rnd.nextPoissonReturnsOneOrMore(tr.getTracingRate(params.geteToT_elderlyRate(),location) * timeStep)) {
                        sm.updateInfectionState(personID, TRACED_ELDERLY);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for traced event");
            }
    }
}