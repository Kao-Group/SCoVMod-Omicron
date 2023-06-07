package scovmod.model.vaccination;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;
import scovmod.model.transition.infected.MildInfectiousToRecoveredTransitionExecutor;
import scovmod.model.util.math.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class VaccinationEfficacyCalculatorTest {

    private static final double TOL = Double.MIN_VALUE;

    private static int PERSON_1 = 100;
    private static int PERSON_2 = 200;
    private static int PERSON_3 = 300;
    private static int PERSON_4 = 400;
    private static int PERSON_5 = 500;

    @Mock
    VaccinationManager vm;
    @Mock
    TimeManager tm;
    @Mock
    ConfigParameters config;
    @Mock
    StartLocationsAndAgeClasses slaac;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_2, AgeClass.ADULT);
        peopleAgeClasses.put(PERSON_3, AgeClass.ELDERLY);
        peopleAgeClasses.put(PERSON_5, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        Int2IntMap vaccinatedPeople = new Int2IntOpenHashMap();
        vaccinatedPeople.put(PERSON_1, 3);
        vaccinatedPeople.put(PERSON_2, 2);
        vaccinatedPeople.put(PERSON_3, 1);
        vaccinatedPeople.put(PERSON_5, 1);
        when(vm.getVaccinatedPeople()).thenReturn(vaccinatedPeople);

        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_3)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_5)).thenReturn(EfficacyProtection.FULL);

        when(vm.getVariantVaccinationEfficacy(PERSON_1)).thenReturn(VariantEfficacyProtection.PARTIAL);
        when(vm.getVariantVaccinationEfficacy(PERSON_2)).thenReturn(VariantEfficacyProtection.PARTIAL);
        when(vm.getVariantVaccinationEfficacy(PERSON_3)).thenReturn(VariantEfficacyProtection.PARTIAL);
        when(vm.getVariantVaccinationEfficacy(PERSON_5)).thenReturn(VariantEfficacyProtection.FULL);

        when(vm.getCurrentVaccinationDose(PERSON_1)).thenReturn(1);
        when(vm.getCurrentVaccinationDose(PERSON_2)).thenReturn(2);
        when(vm.getCurrentVaccinationDose(PERSON_3)).thenReturn(3);

        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step1()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1()).thenReturn(30);

        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step1()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1()).thenReturn(30);

        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step1()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1()).thenReturn(30);

        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step1()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1()).thenReturn(30);

        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step1()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1()).thenReturn(30);

        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step1()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1()).thenReturn(30);

        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step2()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2()).thenReturn(30);

        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step2()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2()).thenReturn(30);

        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step2()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2()).thenReturn(30);

        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step2()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2()).thenReturn(30);

        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step2()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2()).thenReturn(30);

        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step2()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2()).thenReturn(30);

        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step3()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3()).thenReturn(30);

        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step3()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3()).thenReturn(30);

        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step3()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3()).thenReturn(30);

        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step3()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3()).thenReturn(30);
        when(config.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3()).thenReturn(30);

        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step3()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3()).thenReturn(30);
        when(config.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3()).thenReturn(30);

        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step3()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3()).thenReturn(30);
        when(config.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3()).thenReturn(30);


        when(config.getStandardStrainFirstEfficacyChange_young_Step1()).thenReturn(0.1);
        when(config.getStandardStrainFirstEfficacyChange_adult_Step1()).thenReturn(0.2);
        when(config.getStandardStrainFirstEfficacyChange_elderly_Step1()).thenReturn(0.3);
        when(config.getStandardStrainSecondEfficacyChange_young_Step1()).thenReturn(0.6);
        when(config.getStandardStrainSecondEfficacyChange_adult_Step1()).thenReturn(0.7);
        when(config.getStandardStrainSecondEfficacyChange_elderly_Step1()).thenReturn(0.8);
        when(config.getStandardStrainThirdEfficacyChange_young_Step1()).thenReturn(0.0);
        when(config.getStandardStrainThirdEfficacyChange_adult_Step1()).thenReturn(0.1);
        when(config.getStandardStrainThirdEfficacyChange_elderly_Step1()).thenReturn(0.2);

        when(config.getVariantStrainFirstEfficacyChange_young_Step1()).thenReturn(0.101);
        when(config.getVariantStrainFirstEfficacyChange_adult_Step1()).thenReturn(0.201);
        when(config.getVariantStrainFirstEfficacyChange_elderly_Step1()).thenReturn(0.301);
        when(config.getVariantStrainSecondEfficacyChange_young_Step1()).thenReturn(0.601);
        when(config.getVariantStrainSecondEfficacyChange_adult_Step1()).thenReturn(0.701);
        when(config.getVariantStrainSecondEfficacyChange_elderly_Step1()).thenReturn(0.801);
        when(config.getVariantStrainThirdEfficacyChange_young_Step1()).thenReturn(0.01);
        when(config.getVariantStrainThirdEfficacyChange_adult_Step1()).thenReturn(0.101);
        when(config.getVariantStrainThirdEfficacyChange_elderly_Step1()).thenReturn(0.201);

        when(config.getStandardStrainFirstEfficacyChange_young_Step2()).thenReturn(0.11);
        when(config.getStandardStrainFirstEfficacyChange_adult_Step2()).thenReturn(0.21);
        when(config.getStandardStrainFirstEfficacyChange_elderly_Step2()).thenReturn(0.31);
        when(config.getStandardStrainSecondEfficacyChange_young_Step2()).thenReturn(0.61);
        when(config.getStandardStrainSecondEfficacyChange_adult_Step2()).thenReturn(0.71);
        when(config.getStandardStrainSecondEfficacyChange_elderly_Step2()).thenReturn(0.81);
        when(config.getStandardStrainThirdEfficacyChange_young_Step2()).thenReturn(0.01);
        when(config.getStandardStrainThirdEfficacyChange_adult_Step2()).thenReturn(0.11);
        when(config.getStandardStrainThirdEfficacyChange_elderly_Step2()).thenReturn(0.21);

        when(config.getVariantStrainFirstEfficacyChange_young_Step2()).thenReturn(0.111);
        when(config.getVariantStrainFirstEfficacyChange_adult_Step2()).thenReturn(0.211);
        when(config.getVariantStrainFirstEfficacyChange_elderly_Step2()).thenReturn(0.311);
        when(config.getVariantStrainSecondEfficacyChange_young_Step2()).thenReturn(0.611);
        when(config.getVariantStrainSecondEfficacyChange_adult_Step2()).thenReturn(0.711);
        when(config.getVariantStrainSecondEfficacyChange_elderly_Step2()).thenReturn(0.811);
        when(config.getVariantStrainThirdEfficacyChange_young_Step2()).thenReturn(0.011);
        when(config.getVariantStrainThirdEfficacyChange_adult_Step2()).thenReturn(0.111);
        when(config.getVariantStrainThirdEfficacyChange_elderly_Step2()).thenReturn(0.211);

        when(config.getStandardStrainFirstEfficacyChange_young_Step3()).thenReturn(0.12);
        when(config.getStandardStrainFirstEfficacyChange_adult_Step3()).thenReturn(0.22);
        when(config.getStandardStrainFirstEfficacyChange_elderly_Step3()).thenReturn(0.32);
        when(config.getStandardStrainSecondEfficacyChange_young_Step3()).thenReturn(0.62);
        when(config.getStandardStrainSecondEfficacyChange_adult_Step3()).thenReturn(0.72);
        when(config.getStandardStrainSecondEfficacyChange_elderly_Step3()).thenReturn(0.82);
        when(config.getStandardStrainThirdEfficacyChange_young_Step3()).thenReturn(0.02);
        when(config.getStandardStrainThirdEfficacyChange_adult_Step3()).thenReturn(0.12);
        when(config.getStandardStrainThirdEfficacyChange_elderly_Step3()).thenReturn(0.22);

        when(config.getVariantStrainFirstEfficacyChange_young_Step3()).thenReturn(0.121);
        when(config.getVariantStrainFirstEfficacyChange_adult_Step3()).thenReturn(0.221);
        when(config.getVariantStrainFirstEfficacyChange_elderly_Step3()).thenReturn(0.321);
        when(config.getVariantStrainSecondEfficacyChange_young_Step3()).thenReturn(0.621);
        when(config.getVariantStrainSecondEfficacyChange_adult_Step3()).thenReturn(0.721);
        when(config.getVariantStrainSecondEfficacyChange_elderly_Step3()).thenReturn(0.821);
        when(config.getVariantStrainThirdEfficacyChange_young_Step3()).thenReturn(0.021);
        when(config.getVariantStrainThirdEfficacyChange_adult_Step3()).thenReturn(0.121);
        when(config.getVariantStrainThirdEfficacyChange_elderly_Step3()).thenReturn(0.221);
    }

    @Test
    public void youngPerson_efficacy_firstStep_standardStrain_notVaccinatedYet(){
        when(tm.getTimeStep()).thenReturn(250l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(1.0,instance.getCurrentStandardStrainEfficacy(PERSON_4),TOL);

    }

    @Test
    public void elderlyPerson_efficacy_fullyProtected(){
        when(tm.getTimeStep()).thenReturn(250l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.0,instance.getCurrentStandardStrainEfficacy(PERSON_5),TOL);
    }

    @Test
    public void youngPerson_efficacy_firstStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(20l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.1,instance.getCurrentStandardStrainEfficacy(PERSON_1),TOL);
    }

    @Test
    public void youngPerson_efficacy_secondStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(40l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.11,instance.getCurrentStandardStrainEfficacy(PERSON_1),TOL);
    }

    @Test
    public void youngPerson_efficacy_thirdStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(70l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.12,instance.getCurrentStandardStrainEfficacy(PERSON_1),TOL);
    }

    @Test
    public void adultPerson_efficacy_firstStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(20l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.7,instance.getCurrentStandardStrainEfficacy(PERSON_2),TOL);
    }

    @Test
    public void adultPerson_efficacy_secondStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(40l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.71,instance.getCurrentStandardStrainEfficacy(PERSON_2),TOL);
    }

    @Test
    public void adultPerson_efficacy_thirdStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(80l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.72,instance.getCurrentStandardStrainEfficacy(PERSON_2),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_firstStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(20l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.2,instance.getCurrentStandardStrainEfficacy(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_secondStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(40l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.21,instance.getCurrentStandardStrainEfficacy(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_thirdStep_standardStrain(){
        when(tm.getTimeStep()).thenReturn(89l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.22,instance.getCurrentStandardStrainEfficacy(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_fullyProtected_variant(){
        when(tm.getTimeStep()).thenReturn(250l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.0,instance.getCurrentVariantStrainEfficacy(PERSON_5),TOL);
    }

    @Test
    public void youngPerson_efficacy_firstStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(20l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.101,instance.getCurrentVariantStrainEfficacy(PERSON_1),TOL);
    }

    @Test
    public void youngPerson_efficacy_secondStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(40l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.111,instance.getCurrentVariantStrainEfficacy(PERSON_1),TOL);
    }

    @Test
    public void youngPerson_efficacy_thirdStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(80l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.121,instance.getCurrentVariantStrainEfficacy(PERSON_1),TOL);
    }

    @Test
    public void adultPerson_efficacy_firstStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(20l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.701,instance.getCurrentVariantStrainEfficacy(PERSON_2),TOL);
    }

    @Test
    public void adultPerson_efficacy_secondStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(40l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.711,instance.getCurrentVariantStrainEfficacy(PERSON_2),TOL);
    }

    @Test
    public void adultPerson_efficacy_thirdStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(80l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.721,instance.getCurrentVariantStrainEfficacy(PERSON_2),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_firstStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(20l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.201,instance.getCurrentVariantStrainEfficacy(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_secondStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(40l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.211,instance.getCurrentVariantStrainEfficacy(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_thirdStep_variantStrain(){
        when(tm.getTimeStep()).thenReturn(70l);
        VaccinationEfficacyCalculator instance = new VaccinationEfficacyCalculator(config,tm,slaac,vm);
        assertEquals(0.221,instance.getCurrentVariantStrainEfficacy(PERSON_3),TOL);
    }

}
