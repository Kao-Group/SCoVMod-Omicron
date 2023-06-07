package scovmod.model.transition.susceptible;

import static org.mockito.Mockito.*;
import static scovmod.model.util.TestUtils.intSetOf;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateModifier;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.util.math.Random;
//import scovmod.model.vaccination.EfficacyAfterImmunityLossCalculator;
import scovmod.model.vaccination.EfficacyAfterImmunityLossCalculator;
import scovmod.model.vaccination.EfficacyProtection;
import scovmod.model.vaccination.VaccinationEfficacyCalculator;
import scovmod.model.vaccination.VaccinationManager;

public class SusceptiblePersonTransitionExecutorTest {

    private int timeStep = 1;
    private final int PERSON_1 = 11;
    private final int PERSON_2 = 22;
    private static final double STANDARD_EFFICACY_PROTECTION = 1.0;
    private static final double VARIANT_EFFICACY_PROTECTION = 1.0;
    private static final double STANDARD_EFFICACY_PROTECTION_AFTER_IMMUNITY_LOSS = 0.5;
    private static final double VARIANT_EFFICACY_PROTECTION_AFTER_IMMUNITY_LOSS = 0.5;

    @Mock
    StateModifier sm;
    @Mock
    Random rnd;
    @Mock
    StatisticsCollector stats;
    @Mock
    InfectionPressure infPressure;
    @Mock
    StartLocationsAndAgeClasses slaac;
    @Mock
    VaccinationEfficacyCalculator vec;
    @Mock
    EfficacyAfterImmunityLossCalculator eailc;
    @Mock
    VaccinationManager vm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Int2ObjectOpenHashMap<EfficacyProtection> vaccinatedPersonEfficacy  = new Int2ObjectOpenHashMap();
        vaccinatedPersonEfficacy.put(PERSON_1, EfficacyProtection.FULL);
        vaccinatedPersonEfficacy.put(PERSON_2, EfficacyProtection.FULL);
        when(vm.getVaccinatedPersonEfficacyMap()).thenReturn(vaccinatedPersonEfficacy);
    }

    @Test
    public void doSusceptibleTransitionYoung(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(vec.getCurrentStandardStrainEfficacy(PERSON_1)).thenReturn(STANDARD_EFFICACY_PROTECTION);
        when(vec.getCurrentVariantStrainEfficacy(PERSON_1)).thenReturn(VARIANT_EFFICACY_PROTECTION);
        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.PARTIAL);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac,vec,eailc,vm);

        when(infPressure.evaluate(rnd, 1,STANDARD_EFFICACY_PROTECTION,VARIANT_EFFICACY_PROTECTION)).thenReturn(
                TransmissionEventType.FROM_PERSON,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_YOUNG);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void doSusceptibleTransitionVaccinatedYoung(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(vec.getCurrentStandardStrainEfficacy(PERSON_1)).thenReturn(STANDARD_EFFICACY_PROTECTION);
        when(vec.getCurrentVariantStrainEfficacy(PERSON_1)).thenReturn(VARIANT_EFFICACY_PROTECTION);
        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.PARTIAL);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac,vec,eailc,vm);

        when(infPressure.evaluate(rnd, 1,STANDARD_EFFICACY_PROTECTION,VARIANT_EFFICACY_PROTECTION)).thenReturn(
                TransmissionEventType.FROM_PERSON,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_YOUNG);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void doSusceptibleTransitionVaccinatedYoung_PartialFromLossOfImmunity(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_2, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(eailc.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_1)).thenReturn(STANDARD_EFFICACY_PROTECTION_AFTER_IMMUNITY_LOSS);
        when(eailc.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_1)).thenReturn(VARIANT_EFFICACY_PROTECTION_AFTER_IMMUNITY_LOSS);
        when(eailc.getCurrentStandardStrainEfficacy_afterImmunityLoss(PERSON_2)).thenReturn(STANDARD_EFFICACY_PROTECTION_AFTER_IMMUNITY_LOSS);
        when(eailc.getCurrentVariantStrainEfficacy_afterImmunityLoss(PERSON_2)).thenReturn(VARIANT_EFFICACY_PROTECTION_AFTER_IMMUNITY_LOSS);
        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac,vec,eailc,vm);

        when(infPressure.evaluate(rnd, 1,STANDARD_EFFICACY_PROTECTION_AFTER_IMMUNITY_LOSS,VARIANT_EFFICACY_PROTECTION_AFTER_IMMUNITY_LOSS)).thenReturn(
                TransmissionEventType.FROM_PERSON,
                TransmissionEventType.FROM_PERSON
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_YOUNG);
        verify(sm).updateInfectionState(PERSON_2, InfectionState.EXPOSED_YOUNG);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void doSusceptibleTransitionVaccinatedYoung_FullProtection(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.FULL);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.FULL);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac,vec,eailc,vm);

        when(infPressure.evaluate(rnd, 1,0.0,0.0)).thenReturn(
                TransmissionEventType.NULL,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void doSusceptibleTransitionAdult(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ADULT);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(vec.getCurrentStandardStrainEfficacy(PERSON_1)).thenReturn(STANDARD_EFFICACY_PROTECTION);
        when(vec.getCurrentVariantStrainEfficacy(PERSON_1)).thenReturn(VARIANT_EFFICACY_PROTECTION);
        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.PARTIAL);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac,vec,eailc,vm);

        when(infPressure.evaluate(rnd, 1,STANDARD_EFFICACY_PROTECTION,VARIANT_EFFICACY_PROTECTION)).thenReturn(
                TransmissionEventType.FROM_PERSON,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_ADULT);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void doSusceptibleTransitionElderly(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(vec.getCurrentStandardStrainEfficacy(PERSON_1)).thenReturn(STANDARD_EFFICACY_PROTECTION);
        when(vec.getCurrentVariantStrainEfficacy(PERSON_1)).thenReturn(VARIANT_EFFICACY_PROTECTION);
        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.PARTIAL);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac,vec,eailc,vm);

        when(infPressure.evaluate(rnd, 1,STANDARD_EFFICACY_PROTECTION,VARIANT_EFFICACY_PROTECTION)).thenReturn(
                TransmissionEventType.FROM_PERSON,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_ELDERLY);
        verifyNoMoreInteractions(sm);
    }

    @Test
    public void doSusceptibleTransitionToVariantElderly(){
        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.ELDERLY);
        when(slaac.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);
        when(vec.getCurrentStandardStrainEfficacy(PERSON_1)).thenReturn(STANDARD_EFFICACY_PROTECTION);
        when(vec.getCurrentVariantStrainEfficacy(PERSON_1)).thenReturn(VARIANT_EFFICACY_PROTECTION);
        when(vm.getVaccinationEfficacy(PERSON_1)).thenReturn(EfficacyProtection.PARTIAL);
        when(vm.getVaccinationEfficacy(PERSON_2)).thenReturn(EfficacyProtection.PARTIAL);
        SusceptiblePersonTransitionExecutor instance =
                new SusceptiblePersonTransitionExecutor(sm, rnd, timeStep,  stats,slaac,vec,eailc,vm);

        when(infPressure.evaluate(rnd, 1,STANDARD_EFFICACY_PROTECTION,VARIANT_EFFICACY_PROTECTION)).thenReturn(
                TransmissionEventType.FROM_VARIANT,
                TransmissionEventType.NULL
        );

        instance.apply(PERSON_1,infPressure);
        instance.apply(PERSON_2,infPressure);
        verify(sm).updateInfectionState(PERSON_1, InfectionState.EXPOSED_ELDERLY_VARIANT);
        verifyNoMoreInteractions(sm);
    }
}
