package scovmod.model.transition.susceptible;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateModifier;
import scovmod.model.util.math.Random;
import scovmod.model.vaccination.EfficacyAfterImmunityLossCalculator;
import scovmod.model.vaccination.EfficacyProtection;
import scovmod.model.vaccination.VaccinationEfficacyCalculator;
import scovmod.model.vaccination.VaccinationManager;

import static scovmod.model.state.infection.InfectionState.*;

public class SusceptiblePersonTransitionExecutor {
    private final StateModifier sm;
    private final Random rnd;
    private double timeStepInDays;
    private final StatisticsCollector stats;
    private final Int2ObjectMap<AgeClass> peopleAgeClasses;
    private final VaccinationEfficacyCalculator vec;
    private final EfficacyAfterImmunityLossCalculator eailc;
    private final VaccinationManager vm;

    public SusceptiblePersonTransitionExecutor(StateModifier sm,
                                               Random rnd,
                                               double timeStepInDays,
                                               StatisticsCollector stats,
                                               StartLocationsAndAgeClasses slaac,
                                               VaccinationEfficacyCalculator vec,
                                               EfficacyAfterImmunityLossCalculator eailc,
                                               VaccinationManager vm) {
        this.sm = sm;
        this.rnd = rnd;
        this.timeStepInDays = timeStepInDays;
        this.stats = stats;
        peopleAgeClasses = slaac.getPeopleAgeClasses();
        this.vec = vec;
        this.eailc = eailc;
        this.vm = vm;
    }

    public void apply(int personID, InfectionPressure communityPressure) {
        // Check if person is partial efficacy after vaccination OR partial efficacy after immunity loss
        double standardStrainEfficacy = 1.0;
        double variantStrainEfficacy = 1.0;
        if(vm.getVaccinatedPersonEfficacyMap().containsKey(personID)) {
            EfficacyProtection vaccinationEfficacy = vm.getVaccinationEfficacy(personID);
            switch (vaccinationEfficacy) {
                case FULL:
                    standardStrainEfficacy = 0.0;
                    variantStrainEfficacy = 0.0;
                    break;
                case PARTIAL:
                    standardStrainEfficacy = vec.getCurrentStandardStrainEfficacy(personID);
                    variantStrainEfficacy = vec.getCurrentVariantStrainEfficacy(personID);
                    break;
                case PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY:
                case PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY:
                    standardStrainEfficacy = eailc.getCurrentStandardStrainEfficacy_afterImmunityLoss(personID);
                    variantStrainEfficacy = eailc.getCurrentVariantStrainEfficacy_afterImmunityLoss(personID);
                    break;
                default: //If it is none of the above then there is no protection.
                    standardStrainEfficacy = 1.0;
                    variantStrainEfficacy = 1.0;
            }
        }

        TransmissionEventType outcome = communityPressure.evaluate(rnd, timeStepInDays,standardStrainEfficacy, variantStrainEfficacy);
        AgeClass ageClass = peopleAgeClasses.get(personID);
        if (outcome == TransmissionEventType.FROM_PERSON) {
            //Get what age group the person is in
            switch (ageClass) {
                case YOUNG:
                    sm.updateInfectionState(personID, EXPOSED_YOUNG);
                    break;
                case ADULT:
                    sm.updateInfectionState(personID, EXPOSED_ADULT);
                    break;
                case ELDERLY:
                    sm.updateInfectionState(personID, EXPOSED_ELDERLY);
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for exposed event");
            }
        } else if (outcome == TransmissionEventType.FROM_VARIANT) {
            switch (ageClass) {
                case YOUNG:
                    sm.updateInfectionState(personID, EXPOSED_YOUNG_VARIANT);
                    break;
                case ADULT:
                    sm.updateInfectionState(personID, EXPOSED_ADULT_VARIANT);
                    break;
                case ELDERLY:
                    sm.updateInfectionState(personID, EXPOSED_ELDERLY_VARIANT);
                    break;
                default:
                    throw new UnsupportedOperationException("Age class not known- for exposed to variant event");
            }
        } else {
            //No transmission event
        }
    }
}
