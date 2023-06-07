package scovmod.model.state;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.cache.dead.DeadAdultLocations;
import scovmod.model.state.cache.dead.DeadElderlyLocations;
import scovmod.model.state.cache.dead.DeadYoungLocations;
import scovmod.model.state.cache.exposed.ExposedAdultLocations;
import scovmod.model.state.cache.exposed.ExposedElderlyLocations;
import scovmod.model.state.cache.exposed.ExposedYoungLocations;
import scovmod.model.state.cache.hospitalised.HospitalisedAdultLocations;
import scovmod.model.state.cache.hospitalised.HospitalisedElderlyLocations;
import scovmod.model.state.cache.hospitalised.HospitalisedYoungLocations;
import scovmod.model.state.cache.mildInfectious.MildInfectiousAdultLocations;
import scovmod.model.state.cache.mildInfectious.MildInfectiousElderlyLocations;
import scovmod.model.state.cache.mildInfectious.MildInfectiousYoungLocations;
import scovmod.model.state.cache.recovered.RecoveredAdultLocations;
import scovmod.model.state.cache.recovered.RecoveredElderlyLocations;
import scovmod.model.state.cache.recovered.RecoveredYoungLocations;
import scovmod.model.state.cache.severeInfectious.SevereInfectiousAdultLocations;
import scovmod.model.state.cache.severeInfectious.SevereInfectiousElderlyLocations;
import scovmod.model.state.cache.severeInfectious.SevereInfectiousYoungLocations;
import scovmod.model.state.cache.traced.TracedAdultLocations;
import scovmod.model.state.cache.traced.TracedElderlyLocations;
import scovmod.model.state.cache.traced.TracedYoungLocations;
import scovmod.model.state.cache.variant.dead.DeadFromVariantAdultLocations;
import scovmod.model.state.cache.variant.dead.DeadFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.dead.DeadFromVariantYoungLocations;
import scovmod.model.state.cache.variant.exposed.ExposedToVariantAdultLocations;
import scovmod.model.state.cache.variant.exposed.ExposedToVariantElderlyLocations;
import scovmod.model.state.cache.variant.exposed.ExposedToVariantYoungLocations;
import scovmod.model.state.cache.variant.hospitalised.HospitalisedFromVariantAdultLocations;
import scovmod.model.state.cache.variant.hospitalised.HospitalisedFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.hospitalised.HospitalisedFromVariantYoungLocations;
import scovmod.model.state.cache.variant.mildInfectious.MildInfectiousFromVariantAdultLocations;
import scovmod.model.state.cache.variant.mildInfectious.MildInfectiousFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.mildInfectious.MildInfectiousFromVariantYoungLocations;
import scovmod.model.state.cache.variant.recovered.RecoveredFromVariantAdultLocations;
import scovmod.model.state.cache.variant.recovered.RecoveredFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.recovered.RecoveredFromVariantYoungLocations;
import scovmod.model.state.cache.variant.severeInfectious.SevereInfectiousFromVariantAdultLocations;
import scovmod.model.state.cache.variant.severeInfectious.SevereInfectiousFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.severeInfectious.SevereInfectiousFromVariantYoungLocations;
import scovmod.model.state.cache.variant.traced.TracedFromVariantAdultLocations;
import scovmod.model.state.cache.variant.traced.TracedFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.traced.TracedFromVariantYoungLocations;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.state.population.LocalPopulationIndex;
import scovmod.model.util.math.Random;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scovmod.model.state.infection.InfectionState.MILD_INFECTIOUS_ADULT;
import static scovmod.model.state.infection.InfectionState.SUSCEPTIBLE;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.listOf;

public class StateQueryTest {

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;

    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final AreaLevels AREA_LEVEL_IZ = AreaLevels.IZ;
    private final int IZ_AREA = 30;
    @Mock
    private LocalPopulationIndex lpi;
    @Mock
    private ExposedYoungLocations eyl;
    @Mock
    private ExposedAdultLocations eal;
    @Mock
    private ExposedElderlyLocations eel;
    @Mock
    private DeadYoungLocations dyl;
    @Mock
    private DeadAdultLocations dal;
    @Mock
    private DeadElderlyLocations del;
    @Mock
    private HospitalisedYoungLocations hyl;
    @Mock
    private HospitalisedAdultLocations hal;
    @Mock
    private HospitalisedElderlyLocations hel;
    @Mock
    private RecoveredYoungLocations ryl;
    @Mock
    private RecoveredAdultLocations ral;
    @Mock
    private RecoveredElderlyLocations rel;
    @Mock
    private MildInfectiousYoungLocations miyl;
    @Mock
    private MildInfectiousAdultLocations mial;
    @Mock
    private MildInfectiousElderlyLocations miel;
    @Mock
    private SevereInfectiousYoungLocations siyl;
    @Mock
    private SevereInfectiousAdultLocations sial;
    @Mock
    private SevereInfectiousElderlyLocations siel;
    @Mock
    private TracedYoungLocations tyl;
    @Mock
    private TracedAdultLocations tal;
    @Mock
    private TracedElderlyLocations tel;

    @Mock
    private ExposedToVariantYoungLocations eylv;
    @Mock
    private ExposedToVariantAdultLocations ealv;
    @Mock
    private ExposedToVariantElderlyLocations eelv;
    @Mock
    private DeadFromVariantYoungLocations dylv;
    @Mock
    private DeadFromVariantAdultLocations dalv;
    @Mock
    private DeadFromVariantElderlyLocations delv;
    @Mock
    private HospitalisedFromVariantYoungLocations hylv;
    @Mock
    private HospitalisedFromVariantAdultLocations halv;
    @Mock
    private HospitalisedFromVariantElderlyLocations helv;
    @Mock
    private RecoveredFromVariantYoungLocations rylv;
    @Mock
    private RecoveredFromVariantAdultLocations ralv;
    @Mock
    private RecoveredFromVariantElderlyLocations relv;
    @Mock
    private MildInfectiousFromVariantYoungLocations miylv;
    @Mock
    private MildInfectiousFromVariantAdultLocations mialv;
    @Mock
    private MildInfectiousFromVariantElderlyLocations mielv;
    @Mock
    private SevereInfectiousFromVariantYoungLocations siylv;
    @Mock
    private SevereInfectiousFromVariantAdultLocations sialv;
    @Mock
    private SevereInfectiousFromVariantElderlyLocations sielv;
    @Mock
    private TracedFromVariantYoungLocations tylv;
    @Mock
    private TracedFromVariantAdultLocations talv;
    @Mock
    private TracedFromVariantElderlyLocations telv;


    @Mock
    private Random rnd;
    @Mock
    StartLocationsAndAgeClasses sl;
    @Mock
    private HealthBoardLookup hbl;
    private StateQuery instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Int2ObjectMap<AgeClass> peopleAgeClasses = new Int2ObjectOpenHashMap();
        peopleAgeClasses.put(PERSON_1, AgeClass.YOUNG);
        peopleAgeClasses.put(PERSON_2, AgeClass.ADULT);
        when(sl.getPeopleAgeClasses()).thenReturn(peopleAgeClasses);

        instance = new StateQuery(lpi,hbl,eyl,eal,eel,dyl, dal,del,hyl,hal,hel,ryl,ral,rel,miyl,mial,miel,siyl,sial,siel,tyl,tal,tel,eylv,ealv,eelv,dylv, dalv,delv,hylv,halv,helv,rylv,ralv,relv,miylv,mialv,mielv,siylv,sialv,sielv,tylv,talv,telv,rnd,sl);
    }

    @Test
    public void getPersonLocation() {
        int expected = LOCATION_1;
        when(lpi.getPersonLocationId(PERSON_1)).thenReturn(LOCATION_1);
        assertSame(expected, instance.getPersonLocation(PERSON_1));
    }

    @Test
    public void getAllLocationIds() {
        IntSet expected = new IntOpenHashSet();
        when(lpi.getAllLocationIds()).thenReturn(expected);

        assertEquals(expected, instance.getAllActiveLocationIds());
    }

    @Test
    public void getRandomPersonAtLocation() {
        LocalPopulation partition = mock(LocalPopulation.class);
        when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(partition);

        IntSet potentials = new IntOpenHashSet();
        when(partition.getAllInState(SUSCEPTIBLE)).thenReturn(potentials);

        when(rnd.sampleOne(same(potentials))).thenReturn(PERSON_1);

        assertEquals(PERSON_1, instance.getRandomPersonAtLocation(LOCATION_1, SUSCEPTIBLE));
    }

    @Test
    public void getExposedYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(eyl.getExposedYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedYoungLocations());
    }

    @Test
    public void getExposedAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(eal.getExposedAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedAdultLocations());
    }

    @Test
    public void getExposedElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(eel.getExposedElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedElderlyLocations());
    }

    @Test
    public void getDeadYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(dyl.getDeadYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadYoungLocations());
    }

    @Test
    public void getDeadAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(dal.getDeadAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadAdultLocations());
    }

    @Test
    public void getDeadElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(del.getDeadElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadElderlyLocations());
    }

    @Test
    public void getHospitalisedYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(hyl.getHospitalisedYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedYoungLocations());
    }

    @Test
    public void getHospitalisedAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(hal.getHospitaliseAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedAdultLocations());
    }

    @Test
    public void getHospitalisedElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(hel.getHospitalisedElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedElderlyLocations());
    }

    @Test
    public void getRecoveredYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(ryl.getRecoveredYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredYoungLocations());
    }

    @Test
    public void getRecoveredAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(ral.getRecoveredAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredAdultLocations());
    }

    @Test
    public void getRecoveredElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(rel.getRecoveredElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredElderlyLocations());
    }

    @Test
    public void getMildInfectiousYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(miyl.getMildInfectiousYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousYoungLocations());
    }

    @Test
    public void getMildInfectiousAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(mial.getMildInfectiousAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousAdultLocations());
    }

    @Test
    public void getMildInfectiousElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(miel.getMildInfectiousElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousElderlyLocations());
    }

    @Test
    public void getSevereInfectiousYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(siyl.getSevereInfectiousYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousYoungLocations());
    }

    @Test
    public void getSevereInfectiousAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(sial.getSevereInfectiousAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousAdultLocations());
    }

    @Test
    public void getTracedElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(tel.getTracedElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getTracedElderlyLocations());
    }

    @Test
    public void getTracedYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(tyl.getTracedYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getTracedYoungLocations());
    }

    @Test
    public void getTracedAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(tal.getTracedAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getTracedAdultLocations());
    }

    @Test
    public void getSevereInfectiousElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(siel.getSevereInfectiousElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousElderlyLocations());
    }

    @Test
    public void getExposedToVariantYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(eylv.getExposedToVariantYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedToVariantYoungLocations());
    }

    @Test
    public void getExposedToVariantAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(ealv.getExposedToVariantAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedToVariantAdultLocations());
    }

    @Test
    public void getExposedToVariantElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(eelv.getExposedToVariantElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getExposedToVariantElderlyLocations());
    }

    @Test
    public void getDeadFromVariantYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(dylv.getDeadFromVariantYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadFromVariantYoungLocations());
    }

    @Test
    public void getDeadFromVariantAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(dalv.getDeadFromVariantAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadFromVariantAdultLocations());
    }

    @Test
    public void getDeadFromVariantElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(delv.getDeadFromVariantElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getDeadFromVariantElderlyLocations());
    }

    @Test
    public void getHospitalisedFromVariantYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(hylv.getHospitalisedFromVariantYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedFromVariantYoungLocations());
    }

    @Test
    public void getHospitalisedFromVariantAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(halv.getHospitalisedFromVariantAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedFromVariantAdultLocations());
    }

    @Test
    public void getHospitalisedFromVariantElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(helv.getHospitalisedFromVariantElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getHospitalisedFromVariantElderlyLocations());
    }

    @Test
    public void getRecoveredFromVariantYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(rylv.getRecoveredFromVariantYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredFromVariantYoungLocations());
    }

    @Test
    public void getRecoveredFromVariantAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(ralv.getRecoveredFromVariantAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredFromVariantAdultLocations());
    }

    @Test
    public void getRecoveredFromVariantElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(relv.getRecoveredFromVariantElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getRecoveredFromVariantElderlyLocations());
    }

    @Test
    public void getMildInfectiousFromVariantYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(miylv.getMildInfectiousFromVariantYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousFromVariantYoungLocations());
    }

    @Test
    public void getMildInfectiousFromVariantAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(mialv.getMildInfectiousFromVariantAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousFromVariantAdultLocations());
    }

    @Test
    public void getMildInfectiousFromVariantElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(mielv.getMildInfectiousFromVariantElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getMildInfectiousFromVariantElderlyLocations());
    }

    @Test
    public void getSevereInfectiousFromVariantYoungLocations() {
        IntSet expected = new IntOpenHashSet();
        when(siylv.getSevereInfectiousFromVariantYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousFromVariantYoungLocations());
    }

    @Test
    public void getSevereInfectiousFromVariantAdultLocations() {
        IntSet expected = new IntOpenHashSet();
        when(sialv.getSevereInfectiousFromVariantAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousFromVariantAdultLocations());
    }

    @Test
    public void getTracedElderlyFromVariantLocations() {
        IntSet expected = new IntOpenHashSet();
        when(telv.getTracedFromVariantElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getTracedElderlyFromVariantLocations());
    }

    @Test
    public void getTracedYoungFromVariantLocations() {
        IntSet expected = new IntOpenHashSet();
        when(tylv.getFromVariantTracedYoungLocations()).thenReturn(expected);
        assertSame(expected, instance.getTracedYoungFromVariantLocations());
    }

    @Test
    public void getTracedAdultFromVariantLocations() {
        IntSet expected = new IntOpenHashSet();
        when(talv.getTracedFromVariantAdultLocations()).thenReturn(expected);
        assertSame(expected, instance.getTracedAdultFromVariantLocations());
    }

    @Test
    public void getSevereInfectiousFromVariantElderlyLocations() {
        IntSet expected = new IntOpenHashSet();
        when(sielv.getSevereInfectiousFromVariantElderlyLocations()).thenReturn(expected);
        assertSame(expected, instance.getSevereInfectiousFromVariantElderlyLocations());
    }

    @Test
    public void getCopyOfLocalPopulation() {
        LocalPopulation localPop = mock(LocalPopulation.class);
        when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(localPop);

        assertSame(localPop, instance.getCopyOfLocalPopulation(LOCATION_1));
    }

    @Test
    public void getPersonInfectionStatus() {
        when(lpi.getPersonInfectionStatus(PERSON_1)).thenReturn(SUSCEPTIBLE);
        when(lpi.getPersonInfectionStatus(PERSON_2)).thenReturn(MILD_INFECTIOUS_ADULT);

        assertEquals(SUSCEPTIBLE, instance.getPersonInfectionStatus(PERSON_1));
        assertEquals(MILD_INFECTIOUS_ADULT, instance.getPersonInfectionStatus(PERSON_2));
    }

    @Test
    public void getSusceptiblePeopleInArea() {
        Int2ObjectMap<List<Integer>> oasByIZ = new Int2ObjectOpenHashMap<>();
        oasByIZ.put(IZ_AREA,listOf(LOCATION_1,LOCATION_2));
        when(hbl.getOasByIZ()).thenReturn(oasByIZ);
        LocalPopulation localPop1 = mock(LocalPopulation.class);
        when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(localPop1);
        LocalPopulation localPop2 = mock(LocalPopulation.class);
        when(lpi.getLocalPopulation(LOCATION_2)).thenReturn(localPop2);
        when(localPop1.getAllInState(InfectionState.SUSCEPTIBLE)).thenReturn(intSetOf(PERSON_1));
        when(localPop2.getAllInState(InfectionState.SUSCEPTIBLE)).thenReturn(intSetOf(PERSON_2));

        assertEquals(intSetOf(PERSON_1,PERSON_2), instance.getAllSusceptiblePersonsInArea(AREA_LEVEL_IZ,IZ_AREA));
    }

    @Test
    public void getSusceptiblePeopleInAreaInAgeGroup() {
        Int2ObjectMap<List<Integer>> oasByIZ = new Int2ObjectOpenHashMap<>();
        oasByIZ.put(IZ_AREA,listOf(LOCATION_1,LOCATION_2));
        when(hbl.getOasByIZ()).thenReturn(oasByIZ);
        LocalPopulation localPop1 = mock(LocalPopulation.class);
        when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(localPop1);
        LocalPopulation localPop2 = mock(LocalPopulation.class);
        when(lpi.getLocalPopulation(LOCATION_2)).thenReturn(localPop2);
        when(localPop1.getAllInState(InfectionState.SUSCEPTIBLE)).thenReturn(intSetOf(PERSON_1));
        when(localPop2.getAllInState(InfectionState.SUSCEPTIBLE)).thenReturn(intSetOf(PERSON_2));

        assertEquals(intSetOf(PERSON_1), instance.getAllSusceptiblePersonsInAreaInAgeGroup(AREA_LEVEL_IZ,IZ_AREA,AgeClass.YOUNG));
        assertEquals(intSetOf(PERSON_2), instance.getAllSusceptiblePersonsInAreaInAgeGroup(AREA_LEVEL_IZ,IZ_AREA,AgeClass.ADULT));
    }

    @Test
    public void getLocationSize() {
        LocalPopulation localPop = mock(LocalPopulation.class);
        when(localPop.getSize()).thenReturn(200);
        when(lpi.getLocalPopulation(LOCATION_1)).thenReturn(localPop);

        assertEquals(200, instance.getLocationSize(LOCATION_1));
    }
}
