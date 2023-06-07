package scovmod.model.transition.infected.variant;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.lockdown.RegionalInfectionsTracker;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class ExposedToMildInfectiousVariantTransitionExecutor {

    private Random rnd;
    private double timeStep;
    private final CovidVariantParameters params;
    private StateModifier sm;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;
    private final StatisticsCollector stats;
    private final StateQuery sq;
    private final RegionalInfectionsTracker rit;

    public ExposedToMildInfectiousVariantTransitionExecutor(
            StateModifier sm,
            Random rnd,
            double timeStep,
            CovidVariantParameters params,
            StartLocationsAndAgeClasses slaac,
            StatisticsCollector stats,
            StateQuery sq,
            RegionalInfectionsTracker rit) {
        this.rnd = rnd;
        this.sm = sm;
        this.timeStep = timeStep;
        this.params = params;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
        this.stats = stats;
        this.sq = sq;
        this.rit = rit;
    }

    public void apply(int personID) {
        AgeClass ageClass = peopleAgeClasses.get(personID);
            //Get what age group the person is in
            switch(ageClass){
                case YOUNG:
                    if (rnd.nextPoissonReturnsOneOrMore(params.geteToMI_YoungRate_covidVariant() * timeStep)) {
                        sm.updateInfectionState(personID, MILD_INFECTIOUS_YOUNG_VARIANT);
                        stats.newMildInfectiousVariant(sq.getPersonLocation(personID),personID);
                        //rit.newMildInfectious(sq.getPersonLocation(personID));
                    }
                    break;
                case ADULT:
                    if (rnd.nextPoissonReturnsOneOrMore(params.geteToMI_AdultRate_covidVariant() * timeStep)) {
                        sm.updateInfectionState(personID, MILD_INFECTIOUS_ADULT_VARIANT);
                        stats.newMildInfectiousVariant(sq.getPersonLocation(personID),personID);
                        //rit.newMildInfectious(sq.getPersonLocation(personID));
                    }
                    break;
                case ELDERLY:
                    if (rnd.nextPoissonReturnsOneOrMore(params.geteToMI_ElderlyRate_covidVariant() * timeStep)) {
                        sm.updateInfectionState(personID, MILD_INFECTIOUS_ELDERLY_VARIANT);
                        stats.newMildInfectiousVariant(sq.getPersonLocation(personID),personID);
                        //rit.newMildInfectious(sq.getPersonLocation(personID));
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for mild infectious variant event");
            }
    }
}