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
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.time.TimeManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class EfficacyAfterImmunityLossCalculatorTest {

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
        Int2IntMap peopleWithImmunityLoss = new Int2IntOpenHashMap();
        peopleWithImmunityLoss.put(PERSON_1, 30);
        peopleWithImmunityLoss.put(PERSON_2, 20);
        peopleWithImmunityLoss.put(PERSON_3, 10);
        peopleWithImmunityLoss.put(PERSON_5, 10);
        when(vm.getPeopleWithLossOfImmunityEvents()).thenReturn(peopleWithImmunityLoss);

        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_3)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_5)).thenReturn(EfficacyProtection.FULL);

        when(config.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young()).thenReturn(200);
        when(config.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult()).thenReturn(100);
        when(config.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly()).thenReturn(50);

        when(config.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young()).thenReturn(200);
        when(config.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult()).thenReturn(100);
        when(config.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly()).thenReturn(50);

        when(config.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young()).thenReturn(200);
        when(config.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult()).thenReturn(100);
        when(config.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly()).thenReturn(50);

        when(config.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young()).thenReturn(0.1);
        when(config.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult()).thenReturn(0.2);
        when(config.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly()).thenReturn(0.3);
        when(config.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_young()).thenReturn(0.6);
        when(config.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult()).thenReturn(0.7);
        when(config.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly()).thenReturn(0.8);
        when(config.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young()).thenReturn(0.0);
        when(config.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult()).thenReturn(0.1);
        when(config.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly()).thenReturn(0.2);

        when(config.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young()).thenReturn(100);
        when(config.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult()).thenReturn(50);
        when(config.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly()).thenReturn(25);

        when(config.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young()).thenReturn(100);
        when(config.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult()).thenReturn(50);
        when(config.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly()).thenReturn(25);

        when(config.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young()).thenReturn(100);
        when(config.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult()).thenReturn(50);
        when(config.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly()).thenReturn(25);

        when(config.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young()).thenReturn(0.11);
        when(config.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult()).thenReturn(0.22);
        when(config.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly()).thenReturn(0.33);
        when(config.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_young()).thenReturn(0.66);
        when(config.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult()).thenReturn(0.77);
        when(config.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly()).thenReturn(0.88);
        when(config.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young()).thenReturn(0.0);
        when(config.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult()).thenReturn(0.11);
        when(config.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly()).thenReturn(0.22);
    }

    @Test
    public void elderlyPerson_efficacy_fullyProtected(){
        when(tm.getTimeStep()).thenReturn(250l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.0,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_5),TOL);
    }

    @Test
    public void youngPerson_efficacy_firstPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(150l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.1,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_1),TOL);
    }

    @Test
    public void youngPerson_efficacy_secondPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(250l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.6,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_1),TOL);
    }

    @Test
    public void youngPerson_efficacy_thirdPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(450l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.0,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_1),TOL);
    }

    @Test
    public void adultPerson_efficacy_firstPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(80l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.2,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_2),TOL);
    }

    @Test
    public void adultPerson_efficacy_secondPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(180l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.7,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_2),TOL);
    }

    @Test
    public void adultPerson_efficacy_thirdPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(280l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.1,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_2),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_firstPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(40l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.3,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_secondPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(90l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.8,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_thirdPhase_standardStrain(){
        when(tm.getTimeStep()).thenReturn(140l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.2,instance.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_fullyProtected_variant(){
        when(tm.getTimeStep()).thenReturn(250l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.0,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_5),TOL);
    }

    @Test
    public void youngPerson_efficacy_firstPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(50l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.11,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_1),TOL);
    }

    @Test
    public void youngPerson_efficacy_secondPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(150l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.66,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_1),TOL);
    }

    @Test
    public void youngPerson_efficacy_thirdPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(250l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.0,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_1),TOL);
    }

    @Test
    public void adultPerson_efficacy_firstPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(40l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.22,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_2),TOL);
    }

    @Test
    public void adultPerson_efficacy_secondPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(80l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.77,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_2),TOL);
    }

    @Test
    public void adultPerson_efficacy_thirdPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(140l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.11,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_2),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_firstPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(20l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.33,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_secondPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(45l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.88,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_3),TOL);
    }

    @Test
    public void elderlyPerson_efficacy_thirdPhase_variantStrain(){
        when(tm.getTimeStep()).thenReturn(70l);
        EfficacyAfterImmunityLossCalculator instance = new EfficacyAfterImmunityLossCalculator(config,tm,slaac,vm);
        assertEquals(0.22,instance.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_3),TOL);
    }

}
