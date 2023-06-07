/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.vaccination;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.time.TimeManager;

public class EfficacyAfterImmunityLossCalculator {

    private final TimeManager tm;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;
    private ConfigParameters modelConfig;
    private VaccinationManager vm;
    private Int2IntMap peopleWithLossOfImmunity;
    private int endOfPhase2_young;
    private int endOfPhase3_young;
    private int endOfPhase2_young_variant;
    private int endOfPhase3_young_variant;
    private int endOfPhase2_adult;
    private int endOfPhase3_adult;
    private int endOfPhase2_adult_variant;
    private int endOfPhase3_adult_variant;
    private int endOfPhase2_elderly;
    private int endOfPhase3_elderly;
    private int endOfPhase2_elderly_variant;
    private int endOfPhase3_elderly_variant;


    public EfficacyAfterImmunityLossCalculator(
            ConfigParameters modelConfig,
            TimeManager tm,
            StartLocationsAndAgeClasses slaac,
            VaccinationManager vm){
        this.tm = tm;
        this.peopleAgeClasses = slaac.getPeopleAgeClasses();
        this.modelConfig = modelConfig;
        this.peopleWithLossOfImmunity = vm.getPeopleWithLossOfImmunityEvents();
        this.vm = vm;

        endOfPhase2_young = modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young()
                + modelConfig.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young();
        endOfPhase3_young = endOfPhase2_young + modelConfig.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young();

        endOfPhase2_young_variant = modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young()
                + modelConfig.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young();
        endOfPhase3_young_variant = endOfPhase2_young_variant + modelConfig.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young();

        endOfPhase2_adult = modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult()
                + modelConfig.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult();
        endOfPhase3_adult = endOfPhase2_adult + modelConfig.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult();

        endOfPhase2_adult_variant = modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult()
                + modelConfig.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult();
        endOfPhase3_adult_variant = endOfPhase2_adult_variant + modelConfig.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult();

        endOfPhase2_elderly = modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly()
                + modelConfig.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly();
        endOfPhase3_elderly = endOfPhase2_elderly + modelConfig.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly();

        endOfPhase2_elderly_variant = modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly()
                + modelConfig.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly();
        endOfPhase3_elderly_variant = endOfPhase2_elderly_variant + modelConfig.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly();

    }

    private double getYoungEfficacy_standard_afterImmunityLoss(int personID){
        int timeStepWithImmunityLoss= peopleWithLossOfImmunity.get(personID);
        int timeStepsSinceImmunityLoss =  (int) tm.getTimeStep() - timeStepWithImmunityLoss;
        if(timeStepsSinceImmunityLoss<modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young()){
            return modelConfig.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young();
        } else if (timeStepsSinceImmunityLoss>=modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young()
                && timeStepsSinceImmunityLoss<endOfPhase2_young){
            return modelConfig.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_young();
        } else if(timeStepsSinceImmunityLoss>=endOfPhase2_young && timeStepsSinceImmunityLoss<endOfPhase3_young){
            return modelConfig.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young();
        } else {
            return 1.0;
        }
    }

    private double getYoungEfficacy_variant_afterImmunityLoss(int personID){
        int timeStepWithImmunityLoss= peopleWithLossOfImmunity.get(personID);
        int timeStepsSinceImmunityLoss =  (int) tm.getTimeStep() - timeStepWithImmunityLoss;
        if(timeStepsSinceImmunityLoss<modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young()){
            return modelConfig.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young();
        } else if (timeStepsSinceImmunityLoss>=modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young()
                && timeStepsSinceImmunityLoss<endOfPhase2_young_variant){
            return modelConfig.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_young();
        } else if(timeStepsSinceImmunityLoss>=endOfPhase2_young_variant && timeStepsSinceImmunityLoss<endOfPhase3_young_variant){
            return modelConfig.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young();
        } else {
            return 1.0;
        }
    }

    private double getAdultEfficacy_standard_afterImmunityLoss(int personID){
        int timeStepWithImmunityLoss= peopleWithLossOfImmunity.get(personID);
        int timeStepsSinceImmunityLoss =  (int) tm.getTimeStep() - timeStepWithImmunityLoss;
        if(timeStepsSinceImmunityLoss<modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult()){
            return modelConfig.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult();
        } else if (timeStepsSinceImmunityLoss>=modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult()
                && timeStepsSinceImmunityLoss<endOfPhase2_adult){
            return modelConfig.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult();
        } else if(timeStepsSinceImmunityLoss>=endOfPhase2_adult && timeStepsSinceImmunityLoss<endOfPhase3_adult){
            return modelConfig.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult();
        } else {
            return 1.0;
        }
    }

    private double getAdultEfficacy_variant_afterImmunityLoss(int personID){
        int timeStepWithImmunityLoss= peopleWithLossOfImmunity.get(personID);
        int timeStepsSinceImmunityLoss =  (int) tm.getTimeStep() - timeStepWithImmunityLoss;
        if(timeStepsSinceImmunityLoss<modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult()){
            return modelConfig.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult();
        } else if (timeStepsSinceImmunityLoss>=modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult()
                && timeStepsSinceImmunityLoss<endOfPhase2_adult_variant){
            return modelConfig.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult();
        } else if(timeStepsSinceImmunityLoss>=endOfPhase2_adult_variant && timeStepsSinceImmunityLoss<endOfPhase3_adult_variant){
            return modelConfig.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult();
        } else {
            return 1.0;
        }
    }

    private double getElderlyEfficacy_standard_afterImmunityLoss(int personID){
        int timeStepWithImmunityLoss= peopleWithLossOfImmunity.get(personID);
        int timeStepsSinceImmunityLoss =  (int) tm.getTimeStep() - timeStepWithImmunityLoss;
        if(timeStepsSinceImmunityLoss<modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly()){
            return modelConfig.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly();
        } else if (timeStepsSinceImmunityLoss>=modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly()
                && timeStepsSinceImmunityLoss<endOfPhase2_elderly){
            return modelConfig.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly();
        } else if(timeStepsSinceImmunityLoss>=endOfPhase2_elderly && timeStepsSinceImmunityLoss<endOfPhase3_elderly){
            return modelConfig.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly();
        } else {
            return 1.0;
        }
    }

    private double getElderlyEfficacy_variant_afterImmunityLoss(int personID){
        int timeStepWithImmunityLoss= peopleWithLossOfImmunity.get(personID);
        int timeStepsSinceImmunityLoss =  (int) tm.getTimeStep() - timeStepWithImmunityLoss;
        if(timeStepsSinceImmunityLoss<modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly()){
            return modelConfig.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly();
        } else if (timeStepsSinceImmunityLoss>=modelConfig.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly()
                && timeStepsSinceImmunityLoss<endOfPhase2_elderly_variant){
            return modelConfig.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly();
        } else if(timeStepsSinceImmunityLoss>=endOfPhase2_elderly_variant && timeStepsSinceImmunityLoss<endOfPhase3_elderly_variant){
            return modelConfig.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly();
        } else {
            return 1.0;
        }
    }

    public double getCurrentStandardStrainEfficacy_afterImmunityLoss(int personID){
      //  if(vaccinatedPeople.containsKey(personID)){ // Removed because partial after immunity can be from natural too
            if(vm.getVaccinationEfficacy(personID) == EfficacyProtection.FULL) {return 0.0;} //Maybe throw exception here as fully protected are recovered so not taking part in transmission event
            else { // In this case it can only mean Partial for now
                AgeClass ageClass = peopleAgeClasses.get(personID);
                //Get what age group the person is in
                switch (ageClass) {
                    case YOUNG:
                        return getYoungEfficacy_standard_afterImmunityLoss(personID);
                    case ADULT:
                        return getAdultEfficacy_standard_afterImmunityLoss(personID);
                    case ELDERLY:
                        return getElderlyEfficacy_standard_afterImmunityLoss(personID);
                    default:
                        throw new UnsupportedOperationException("Age class not known- for standard strain efficacy after immunity loss calculation");
                }
            }

//        } else {
//            return 1.0; // No protection
//        }
    }

    public double getCurrentVariantStrainEfficacy_afterImmunityLoss(int personID){
  //      if(vaccinatedPeople.containsKey(personID)){ // Removed because partial after immunity can be from natural too
            if(vm.getVaccinationEfficacy(personID) == EfficacyProtection.FULL) {return 0.0;} //Maybe throw exception here as fully protected are recovered so not taking part in transmission event
            else { // In this case it can only mean Partial for now
                AgeClass ageClass = peopleAgeClasses.get(personID);
                //Get what age group the person is in
                switch (ageClass) {
                    case YOUNG:
                        return getYoungEfficacy_variant_afterImmunityLoss(personID);
                    case ADULT:
                        return getAdultEfficacy_variant_afterImmunityLoss(personID);
                    case ELDERLY:
                        return getElderlyEfficacy_variant_afterImmunityLoss(personID);
                    default:
                        throw new UnsupportedOperationException("Age class not known- for variant strain efficacy after immunity loss calculation");
                }
            }
//        } else {
//            return 1.0; // No protection
//        }
    }

}
