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
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;

public class VaccinationEfficacyCalculator {

    private final TimeManager tm;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;
    private ConfigParameters modelConfig;
    private VaccinationManager vm;
    private Int2IntMap vaccinatedPeople;

    private int endOfPhase1_young_Step2;
    private int endOfPhase1_young_Step3;
    private int endOfPhase1_young_variant_Step2;
    private int endOfPhase1_young_variant_Step3;
    private int endOfPhase1_adult_Step2;
    private int endOfPhase1_adult_Step3;
    private int endOfPhase1_adult_variant_Step2;
    private int endOfPhase1_adult_variant_Step3;
    private int endOfPhase1_elderly_Step2;
    private int endOfPhase1_elderly_Step3;
    private int endOfPhase1_elderly_variant_Step2;
    private int endOfPhase1_elderly_variant_Step3;


    private int endOfPhase2_young_Step2;
    private int endOfPhase3_young_Step2;
    private int endOfPhase2_young_variant_Step2;
    private int endOfPhase3_young_variant_Step2;
    private int endOfPhase2_adult_Step2;
    private int endOfPhase3_adult_Step2;
    private int endOfPhase2_adult_variant_Step2;
    private int endOfPhase3_adult_variant_Step2;
    private int endOfPhase2_elderly_Step2;
    private int endOfPhase3_elderly_Step2;
    private int endOfPhase2_elderly_variant_Step2;
    private int endOfPhase3_elderly_variant_Step2;
    private int endOfPhase2_young_Step3;
    private int endOfPhase3_young_Step3;
    private int endOfPhase2_young_variant_Step3;
    private int endOfPhase3_young_variant_Step3;
    private int endOfPhase2_adult_Step3;
    private int endOfPhase3_adult_Step3;
    private int endOfPhase2_adult_variant_Step3;
    private int endOfPhase3_adult_variant_Step3;
    private int endOfPhase2_elderly_Step3;
    private int endOfPhase3_elderly_Step3;
    private int endOfPhase2_elderly_variant_Step3;
    private int endOfPhase3_elderly_variant_Step3;


    public VaccinationEfficacyCalculator(
            ConfigParameters modelConfig,
            TimeManager tm,
            StartLocationsAndAgeClasses slaac,
            VaccinationManager vm){
        this.tm = tm;
        this.peopleAgeClasses = slaac.getPeopleAgeClasses();
        this.modelConfig = modelConfig;
        this.vaccinatedPeople = vm.getVaccinatedPeople();
        this.vm = vm;

        endOfPhase1_young_Step2 = modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step1()
                + modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step2();
        endOfPhase1_young_Step3 = endOfPhase1_young_Step2 + modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step3();

        endOfPhase1_young_variant_Step2 = modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step1()
                + modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step2();
        endOfPhase1_young_variant_Step3 = endOfPhase1_young_variant_Step2 + modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step3();

        endOfPhase1_adult_Step2 = modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1()
                + modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2();
        endOfPhase1_adult_Step3 = endOfPhase1_adult_Step2 + modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3();

        endOfPhase1_adult_variant_Step2 = modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1()
                + modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2();
        endOfPhase1_adult_variant_Step3 = endOfPhase1_adult_variant_Step2 + modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3();

        endOfPhase1_elderly_Step2 = modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1()
                + modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2();
        endOfPhase1_elderly_Step3 = endOfPhase1_elderly_Step2 + modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3();

        endOfPhase1_elderly_variant_Step2 = modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1()
                + modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2();
        endOfPhase1_elderly_variant_Step3 = endOfPhase1_elderly_variant_Step2 + modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3();

        //////////////////////////////////////////////////////////////////////

        endOfPhase2_young_Step2 = modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step1()
                + modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step2();
        endOfPhase2_young_Step3 = endOfPhase2_young_Step2 + modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step3();

        endOfPhase2_young_variant_Step2 = modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step1()
                + modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step2();
        endOfPhase2_young_variant_Step3 = endOfPhase2_young_variant_Step2 + modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step3();

        endOfPhase2_adult_Step2 = modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1()
                + modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2();
        endOfPhase2_adult_Step3 = endOfPhase2_adult_Step2 + modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3();

        endOfPhase2_adult_variant_Step2 = modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1()
                + modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2();
        endOfPhase2_adult_variant_Step3 = endOfPhase2_adult_variant_Step2 + modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3();

        endOfPhase2_elderly_Step2 = modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1()
                + modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2();
        endOfPhase2_elderly_Step3 = endOfPhase2_elderly_Step2 + modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3();

        endOfPhase2_elderly_variant_Step2 = modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1()
                + modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2();
        endOfPhase2_elderly_variant_Step3 = endOfPhase2_elderly_variant_Step2 + modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3();

        endOfPhase3_young_Step2 = modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step1()
                + modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step2();
        endOfPhase3_young_Step3 = endOfPhase3_young_Step2 + modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step3();

        endOfPhase3_young_variant_Step2 = modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step1()
                + modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step2();
        endOfPhase3_young_variant_Step3 = endOfPhase3_young_variant_Step2 + modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step3();

        endOfPhase3_adult_Step2 = modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1()
                + modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2();
        endOfPhase3_adult_Step3 = endOfPhase3_adult_Step2 + modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3();

        endOfPhase3_adult_variant_Step2 = modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1()
                + modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2();
        endOfPhase3_adult_variant_Step3 = endOfPhase3_adult_variant_Step2 + modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3();

        endOfPhase3_elderly_Step2 = modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1()
                + modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2();
        endOfPhase3_elderly_Step3 = endOfPhase3_elderly_Step2 + modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3();

        endOfPhase3_elderly_variant_Step2 = modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1()
                + modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2();
        endOfPhase3_elderly_variant_Step3 = endOfPhase3_elderly_variant_Step2 + modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3();

    }

    private double getYoungEfficacy_standard(int personID){
        int timeStepVaccinated = vaccinatedPeople.get(personID);
        int timeStepsSinceVaccination =  (int) tm.getTimeStep() - timeStepVaccinated;
        int currentVaccinationDose = vm.getCurrentVaccinationDose(personID);
        if(currentVaccinationDose == 0){
            return 1.0;
        }
        else if(currentVaccinationDose == 1) {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainFirstEfficacyChange_young_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step1()
                    && timeStepsSinceVaccination < endOfPhase1_young_Step2) {
                return modelConfig.getStandardStrainFirstEfficacyChange_young_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase1_young_Step2 && timeStepsSinceVaccination < endOfPhase1_young_Step3) {
                return modelConfig.getStandardStrainFirstEfficacyChange_young_Step3();
            } else {
                return 1.0;
            }
        }
        else if(currentVaccinationDose == 2){
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainSecondEfficacyChange_young_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_young_Step2) {
                return modelConfig.getStandardStrainSecondEfficacyChange_young_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_young_Step2 && timeStepsSinceVaccination < endOfPhase2_young_Step3) {
                return modelConfig.getStandardStrainSecondEfficacyChange_young_Step3();
            } else {
                return 1.0;
            }
        }
        else {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainThirdEfficacyChange_young_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_young_Step2) {
                return modelConfig.getStandardStrainThirdEfficacyChange_young_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_young_Step2 && timeStepsSinceVaccination < endOfPhase2_young_Step3) {
                return modelConfig.getStandardStrainThirdEfficacyChange_young_Step3();
            } else {
                return 1.0;
            }
        }
    }

    private double getYoungEfficacy_variant(int personID){

        int timeStepVaccinated = vaccinatedPeople.get(personID);
        int timeStepsSinceVaccination =  (int) tm.getTimeStep() - timeStepVaccinated;
        int currentVaccinationDose = vm.getCurrentVaccinationDose(personID);
        if(currentVaccinationDose == 0){
            return 1.0;
        }
        else if(currentVaccinationDose == 1) {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainFirstEfficacyChange_young_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step1()
                    && timeStepsSinceVaccination < endOfPhase1_young_variant_Step2) {
                return modelConfig.getVariantStrainFirstEfficacyChange_young_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase1_young_variant_Step2 && timeStepsSinceVaccination < endOfPhase1_young_variant_Step3) {
                return modelConfig.getVariantStrainFirstEfficacyChange_young_Step3();
            } else {
                return 1.0;
            }
        }
        else if(currentVaccinationDose == 2){
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainSecondEfficacyChange_young_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_young_variant_Step2) {
                return modelConfig.getVariantStrainSecondEfficacyChange_young_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_young_variant_Step2 && timeStepsSinceVaccination < endOfPhase2_young_variant_Step3) {
                return modelConfig.getVariantStrainSecondEfficacyChange_young_Step3();
            } else {
                return 1.0;
            }
        }
        else {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainThirdEfficacyChange_young_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_young_variant_Step2) {
                return modelConfig.getVariantStrainThirdEfficacyChange_young_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_young_variant_Step2 && timeStepsSinceVaccination < endOfPhase2_young_variant_Step3) {
                return modelConfig.getVariantStrainThirdEfficacyChange_young_Step3();
            } else {
                return 1.0;
            }
        }
    }

    private double getAdultEfficacy_standard(int personID){
        int timeStepVaccinated = vaccinatedPeople.get(personID);
        int timeStepsSinceVaccination =  (int) tm.getTimeStep() - timeStepVaccinated;
        int currentVaccinationDose = vm.getCurrentVaccinationDose(personID);
        if(currentVaccinationDose == 0){
            return 1.0;
        }
        else if(currentVaccinationDose == 1) {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainFirstEfficacyChange_adult_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1()
                    && timeStepsSinceVaccination < endOfPhase1_adult_Step2) {
                return modelConfig.getStandardStrainFirstEfficacyChange_adult_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase1_adult_Step2 && timeStepsSinceVaccination < endOfPhase1_adult_Step3) {
                return modelConfig.getStandardStrainFirstEfficacyChange_adult_Step3();
            } else {
                return 1.0;
            }
        }
        else if(currentVaccinationDose == 2){
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainSecondEfficacyChange_adult_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_adult_Step2) {
                return modelConfig.getStandardStrainSecondEfficacyChange_adult_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_adult_Step2 && timeStepsSinceVaccination < endOfPhase2_adult_Step3) {
                return modelConfig.getStandardStrainSecondEfficacyChange_adult_Step3();
            } else {
                return 1.0;
            }
        }
        else {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainThirdEfficacyChange_adult_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_adult_Step2) {
                return modelConfig.getStandardStrainThirdEfficacyChange_adult_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_adult_Step2 && timeStepsSinceVaccination < endOfPhase2_adult_Step3) {
                return modelConfig.getStandardStrainThirdEfficacyChange_adult_Step3();
            } else {
                return 1.0;
            }
        }
    }

    private double getAdultEfficacy_variant(int personID){
        int timeStepVaccinated = vaccinatedPeople.get(personID);
        int timeStepsSinceVaccination =  (int) tm.getTimeStep() - timeStepVaccinated;
        int currentVaccinationDose = vm.getCurrentVaccinationDose(personID);
        if(currentVaccinationDose == 0){
            return 1.0;
        }
        else if(currentVaccinationDose == 1) {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainFirstEfficacyChange_adult_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1()
                    && timeStepsSinceVaccination < endOfPhase1_adult_variant_Step2) {
                return modelConfig.getVariantStrainFirstEfficacyChange_adult_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase1_adult_variant_Step2 && timeStepsSinceVaccination < endOfPhase1_adult_variant_Step3) {
                return modelConfig.getVariantStrainFirstEfficacyChange_adult_Step3();
            } else {
                return 1.0;
            }
        }
        else if(currentVaccinationDose == 2){
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainSecondEfficacyChange_adult_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_adult_variant_Step2) {
                return modelConfig.getVariantStrainSecondEfficacyChange_adult_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_adult_variant_Step2 && timeStepsSinceVaccination < endOfPhase2_adult_variant_Step3) {
                return modelConfig.getVariantStrainSecondEfficacyChange_adult_Step3();
            } else {
                return 1.0;
            }
        }
        else {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainThirdEfficacyChange_adult_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_adult_variant_Step2) {
                return modelConfig.getVariantStrainThirdEfficacyChange_adult_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_adult_variant_Step2 && timeStepsSinceVaccination < endOfPhase2_adult_variant_Step3) {
                return modelConfig.getVariantStrainThirdEfficacyChange_adult_Step3();
            } else {
                return 1.0;
            }
        }
    }

    private double getElderlyEfficacy_standard(int personID){
        int timeStepVaccinated = vaccinatedPeople.get(personID);
        int timeStepsSinceVaccination =  (int) tm.getTimeStep() - timeStepVaccinated;
        int currentVaccinationDose = vm.getCurrentVaccinationDose(personID);
        if(currentVaccinationDose == 0){
            return 1.0;
        }
        else if(currentVaccinationDose == 1) {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainFirstEfficacyChange_elderly_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1()
                    && timeStepsSinceVaccination < endOfPhase1_elderly_Step2) {
                return modelConfig.getStandardStrainFirstEfficacyChange_elderly_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase1_elderly_Step2 && timeStepsSinceVaccination < endOfPhase1_elderly_Step3) {
                return modelConfig.getStandardStrainFirstEfficacyChange_elderly_Step3();
            } else {
                return 1.0;
            }
        }
        else if(currentVaccinationDose == 2){
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainSecondEfficacyChange_elderly_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_elderly_Step2) {
                return modelConfig.getStandardStrainSecondEfficacyChange_elderly_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_elderly_Step2 && timeStepsSinceVaccination < endOfPhase2_elderly_Step3) {
                return modelConfig.getStandardStrainSecondEfficacyChange_elderly_Step3();
            } else {
                return 1.0;
            }
        }
        else {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getStandardStrainThirdEfficacyChange_elderly_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_elderly_Step2) {
                return modelConfig.getStandardStrainThirdEfficacyChange_elderly_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_elderly_Step2 && timeStepsSinceVaccination < endOfPhase2_elderly_Step3) {
                return modelConfig.getStandardStrainThirdEfficacyChange_elderly_Step3();
            } else {
                return 1.0;
            }
        }
    }

    private double getElderlyEfficacy_variant(int personID){
        int timeStepVaccinated = vaccinatedPeople.get(personID);
        int timeStepsSinceVaccination =  (int) tm.getTimeStep() - timeStepVaccinated;
        int currentVaccinationDose = vm.getCurrentVaccinationDose(personID);
        if(currentVaccinationDose == 0){
            return 1.0;
        }
        else if(currentVaccinationDose == 1) {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainFirstEfficacyChange_elderly_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1()
                    && timeStepsSinceVaccination < endOfPhase1_elderly_variant_Step2) {
                return modelConfig.getVariantStrainFirstEfficacyChange_elderly_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase1_elderly_variant_Step2 && timeStepsSinceVaccination < endOfPhase1_elderly_variant_Step3) {
                return modelConfig.getVariantStrainFirstEfficacyChange_elderly_Step3();
            } else {
                return 1.0;
            }
        }
        else if(currentVaccinationDose == 2){
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainSecondEfficacyChange_elderly_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_elderly_variant_Step2) {
                return modelConfig.getVariantStrainSecondEfficacyChange_elderly_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_elderly_variant_Step2 && timeStepsSinceVaccination < endOfPhase2_elderly_variant_Step3) {
                return modelConfig.getVariantStrainSecondEfficacyChange_elderly_Step3();
            } else {
                return 1.0;
            }
        }
        else {
            if (timeStepsSinceVaccination < modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1() && timeStepsSinceVaccination > 0) {
                return modelConfig.getVariantStrainThirdEfficacyChange_elderly_Step1();
            } else if (timeStepsSinceVaccination >= modelConfig.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1()
                    && timeStepsSinceVaccination < endOfPhase2_elderly_variant_Step2) {
                return modelConfig.getVariantStrainThirdEfficacyChange_elderly_Step2();
            } else if (timeStepsSinceVaccination >= endOfPhase2_elderly_variant_Step2 && timeStepsSinceVaccination < endOfPhase2_elderly_variant_Step3) {
                return modelConfig.getVariantStrainThirdEfficacyChange_elderly_Step3();
            } else {
                return 1.0;
            }
        }
    }

    public double getCurrentStandardStrainEfficacy(int personID){
        if(vaccinatedPeople.containsKey(personID)){
            if(vm.getVaccinationEfficacy(personID) == EfficacyProtection.FULL) {return 0.0;} //Maybe throw exception here as fully protected are recovered so not taking part in transmission event
            else { // In this case it can only mean Partial for now
                AgeClass ageClass = peopleAgeClasses.get(personID);
                //Get what age group the person is in
                switch (ageClass) {
                    case YOUNG:
                        return getYoungEfficacy_standard(personID);
                    case ADULT:
                        return getAdultEfficacy_standard(personID);
                    case ELDERLY:
                        return getElderlyEfficacy_standard(personID);
                    default:
                        throw new UnsupportedOperationException("Age class not known- for standard strain efficacy calculation");
                }
            }

        } else {
            return 1.0; // No protection
        }
    }

    public double getCurrentVariantStrainEfficacy(int personID){
        if(vaccinatedPeople.containsKey(personID)){
            if(vm.getVariantVaccinationEfficacy(personID) == VariantEfficacyProtection.FULL) {return 0.0;} //Maybe throw exception here as fully protected are recovered so not taking part in transmission event
            else { // In this case it can only mean Partial for now
                AgeClass ageClass = peopleAgeClasses.get(personID);
                //Get what age group the person is in
                switch (ageClass) {
                    case YOUNG:
                        return getYoungEfficacy_variant(personID);
                    case ADULT:
                        return getAdultEfficacy_variant(personID);
                    case ELDERLY:
                        return getElderlyEfficacy_variant(personID);
                    default:
                        throw new UnsupportedOperationException("Age class not known- for variant strain efficacy calculation");
                }
            }
        } else {
            return 1.0; // No protection
        }
    }

}
