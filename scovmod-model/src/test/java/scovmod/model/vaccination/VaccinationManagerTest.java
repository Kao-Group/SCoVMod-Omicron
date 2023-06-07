package scovmod.model.vaccination;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.JsonVaccinationsReader;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;
import scovmod.model.util.TestUtils;
import scovmod.model.util.math.AliasMethod;
import scovmod.model.util.math.AliasMethodFactory;
import scovmod.model.util.math.Random;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static scovmod.model.util.TestUtils.intSetOf;

public class VaccinationManagerTest {

    @Mock
    StartLocationsAndAgeClasses sl;
    @Mock
    StateQuery sq;
    @Mock
    StateModifier sm;
    @Mock
    JsonVaccinationsReader pv;
    @Mock
    Random rand;
    @Mock
    TimeManager tm;
    @Mock
    ConfigParameters config;
    @Mock
    Parameters params;
    @Mock
    AliasMethodFactory amf;
    @Mock
    AliasMethod am,am2;

    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    //Historical
    private final int PERSON_4 = 4;
    private final int PERSON_5 = 5;
    //Not vaccinated
    private final int PERSON_6 = 6;

    private final long TIMESTEP_1 = 1000l;
    private final long TIMESTEP_HISTORICAL = 500l;
    private final long TIMESTEP_2 = 2000l;

    private VaccinationManager instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);


        Int2ObjectMap<Set> peopleToVaccinateMap = new Int2ObjectOpenHashMap();
        peopleToVaccinateMap.put((int) TIMESTEP_HISTORICAL, intSetOf(PERSON_4, PERSON_5));
        peopleToVaccinateMap.put((int) TIMESTEP_1, intSetOf(PERSON_1, PERSON_2, PERSON_3));
        peopleToVaccinateMap.put((int) TIMESTEP_2, intSetOf(PERSON_1));
        when(pv.getPeopleToVaccinateMap()).thenReturn(peopleToVaccinateMap);

        Int2IntMap vaccinationDosePerPerson = new Int2IntOpenHashMap();

        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_2, AgeClass.ELDERLY);
        peopleAgeClasses.put(PERSON_3, AgeClass.ADULT);
        peopleAgeClasses.put(PERSON_4, AgeClass.ELDERLY);
        peopleAgeClasses.put(PERSON_5, AgeClass.ADULT);
        when(sl.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);

        Int2ObjectOpenHashMap<EfficacyProtection> vaccinatedPersonEfficacy  = new Int2ObjectOpenHashMap();
        vaccinatedPersonEfficacy.put(PERSON_1, EfficacyProtection.FULL);
        vaccinatedPersonEfficacy.put(PERSON_2, EfficacyProtection.FULL);
        vaccinatedPersonEfficacy.put(PERSON_3, EfficacyProtection.PARTIAL);
        vaccinatedPersonEfficacy.put(PERSON_4,  EfficacyProtection.PARTIAL);
        vaccinatedPersonEfficacy.put(PERSON_5,  EfficacyProtection.FULL);

        when(sq.getPersonInfectionStatus(PERSON_1)).thenReturn(InfectionState.RECOVERED_YOUNG);
        when(sq.getPersonInfectionStatus(PERSON_2)).thenReturn(InfectionState.SUSCEPTIBLE);
        when(sq.getPersonInfectionStatus(PERSON_3)).thenReturn(InfectionState.SEVERE_INFECTIOUS_ADULT);
        when(sq.getPersonInfectionStatus(PERSON_4)).thenReturn(InfectionState.SUSCEPTIBLE);
        when(sq.getPersonInfectionStatus(PERSON_5)).thenReturn(InfectionState.SUSCEPTIBLE);

        when(amf.build()).thenReturn(am,am2);
        when(am.next()).thenReturn(0,1,0);
        when(am2.next()).thenReturn(0,1,0);
        when(params.getPartialVersusFullProtectionProp()).thenReturn(0.4);
        when(params.getPartialVersusFullProtectionProp_variant()).thenReturn(0.4);

        instance = new VaccinationManager(rand,pv,tm,config,sq,sm,sl,params,amf);
    }
    //TODO: Do more edge cases

    @Test
    public void setupEfficacySamplingByAliasMethodInConstructor() {
        verify(am).initialise(TestUtils.listOf(0.6, 0.4));
    }

    @Test
    public void vaccinatePeoplePerTimeStep() {
        when(tm.getTimeStep()).thenReturn(TIMESTEP_1);
        Int2IntMap expectedMap = new Int2IntOpenHashMap();
        expectedMap.put(PERSON_1,(int)TIMESTEP_1);
        expectedMap.put(PERSON_2,(int)TIMESTEP_1);
        expectedMap.put(PERSON_3,(int)TIMESTEP_1);

        assertEquals(0,instance.getCurrentVaccinationDose(PERSON_1));
        assertEquals(0,instance.getCurrentVaccinationDose(PERSON_2));
        assertEquals(0,instance.getCurrentVaccinationDose(PERSON_3));
        instance.vaccinatePeoplePerTimestep();
        assertEquals(expectedMap,instance.getVaccinatedPeople());
        assertEquals(EfficacyProtection.FULL,instance.getVaccinationEfficacy(PERSON_2));
        assertEquals(EfficacyProtection.PARTIAL,instance.getVaccinationEfficacy(PERSON_3));
        assertEquals(InfectionState.SEVERE_INFECTIOUS_ADULT,sq.getPersonInfectionStatus(PERSON_3));
        verify(sm).updateInfectionState(PERSON_2,InfectionState.RECOVERED_ELDERLY);

        assertEquals(1,instance.getCurrentVaccinationDose(PERSON_1));
        assertEquals(1,instance.getCurrentVaccinationDose(PERSON_2));
        assertEquals(1,instance.getCurrentVaccinationDose(PERSON_3));

        Int2IntMap expectedMap2 = new Int2IntOpenHashMap();
        expectedMap2.put(PERSON_1,(int)TIMESTEP_2);
        expectedMap2.put(PERSON_2,(int)TIMESTEP_1);
        expectedMap2.put(PERSON_3,(int)TIMESTEP_1);
        when(tm.getTimeStep()).thenReturn(TIMESTEP_2);
        instance.vaccinatePeoplePerTimestep();
        assertEquals(2,instance.getCurrentVaccinationDose(PERSON_1));
        assertEquals(1,instance.getCurrentVaccinationDose(PERSON_2));
        assertEquals(1,instance.getCurrentVaccinationDose(PERSON_3));
        assertEquals(expectedMap2,instance.getVaccinatedPeople());
    }

    @Test
    public void peopleWithImmunityLoss() {
        when(tm.getTimeStep()).thenReturn(TIMESTEP_1);
        Int2IntMap expectedMap = new Int2IntOpenHashMap();
        expectedMap.put(PERSON_1,(int)TIMESTEP_1);
        expectedMap.put(PERSON_2,(int)TIMESTEP_1);
        expectedMap.put(PERSON_3,(int)TIMESTEP_1);
        instance.recordLossOfImmunityEvent(PERSON_1);
        instance.recordLossOfImmunityEvent(PERSON_2);
        instance.recordLossOfImmunityEvent(PERSON_3);
        assertEquals(expectedMap,instance.getPeopleWithLossOfImmunityEvents());
    }

    @Test
    public void initialiseHistoricallyVaccinatedPeople() {
        when(config.getVaccineStartTimestep()).thenReturn(1);
        when(config.getFirstTimeStep()).thenReturn(600l);
        when(tm.getTimeStep()).thenReturn(TIMESTEP_HISTORICAL);
        Int2IntMap expectedMap = new Int2IntOpenHashMap();
        expectedMap.put(PERSON_4,(int)TIMESTEP_HISTORICAL);
        expectedMap.put(PERSON_5,(int)TIMESTEP_HISTORICAL);

        instance.initialiseHistoricVaccinations();

        assertEquals(expectedMap,instance.getVaccinatedPeople());
        assertEquals(EfficacyProtection.FULL,instance.getVaccinationEfficacy(PERSON_4));
        assertEquals(EfficacyProtection.PARTIAL,instance.getVaccinationEfficacy(PERSON_5));
        verify(sm).updateInfectionState(PERSON_4,InfectionState.RECOVERED_ELDERLY);
    }

    @Test
    public void VaccinatedPeople_HistoricalAndCurrent() {
        when(config.getVaccineStartTimestep()).thenReturn(1);
        when(config.getFirstTimeStep()).thenReturn(TIMESTEP_2);
        when(tm.getTimeStep()).thenReturn(TIMESTEP_HISTORICAL);
        when(sq.getPersonInfectionStatus(PERSON_1)).thenReturn(InfectionState.RECOVERED_YOUNG);
        when(sq.getPersonInfectionStatus(PERSON_2)).thenReturn(InfectionState.SUSCEPTIBLE);
        when(sq.getPersonInfectionStatus(PERSON_3)).thenReturn(InfectionState.SEVERE_INFECTIOUS_ADULT);
        when(sq.getPersonInfectionStatus(PERSON_4)).thenReturn(InfectionState.RECOVERED_ELDERLY);
        when(sq.getPersonInfectionStatus(PERSON_5)).thenReturn(InfectionState.SUSCEPTIBLE);
        Int2IntMap expectedMap = new Int2IntOpenHashMap();
        expectedMap.put(PERSON_1,(int)TIMESTEP_1);
        expectedMap.put(PERSON_2,(int)TIMESTEP_1);
        expectedMap.put(PERSON_3,(int)TIMESTEP_1);
        expectedMap.put(PERSON_4,(int)TIMESTEP_HISTORICAL);
        expectedMap.put(PERSON_5,(int)TIMESTEP_HISTORICAL);

        instance.initialiseHistoricVaccinations();
        instance.vaccinatePeoplePerTimestep();

        assertEquals(expectedMap,instance.getVaccinatedPeople());
    }

//    @Test
//    public void makeProtectionPartialAfterVaccinationImmunityLoss() {
//        when(tm.getTimeStep()).thenReturn(TIMESTEP_1);
//        instance.vaccinatePeoplePerTimestep();
//        assertEquals(EfficacyProtection.FULL,instance.getVaccinationEfficacy(PERSON_2));
//        instance.makeProtectionPartialAfterImmunityLoss(PERSON_2);
//        assertEquals(EfficacyProtection.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY,instance.getVaccinationEfficacy(PERSON_2));
//    }
//
//
//    @Test
//    public void makeProtectionPartialAfterNaturalImmunityLoss() {
//        when(tm.getTimeStep()).thenReturn(TIMESTEP_1);
//        instance.makeProtectionPartialAfterImmunityLoss(PERSON_6);
//        assertEquals(EfficacyProtection.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY,instance.getVaccinationEfficacy(PERSON_6));
//    }

}
