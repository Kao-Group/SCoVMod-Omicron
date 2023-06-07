package scovmod.model.lockdown;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.time.TimeManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static scovmod.model.input.config.AreaLevels.DZ;
import static scovmod.model.util.TestUtils.intSetOf;

public class RegionalInfectionsTrackerTest {

    private RegionalInfectionsTracker instance;

    private final int OA_1 = 100;
    private final int OA_2 = 200;
    private final int OA_3 = 300;
    private final int OA_4 = 400;
    private final int DZ_1 = 10;
    private final int DZ_2 = 20;
    private final int PERSON_1 = 1;
    private final int PERSON_2 = 2;
    private final int PERSON_3 = 3;
    private final int PERSON_4 = 4;
    private final int PERSON_5 = 5;
    private final int PERSON_6 = 6;

    @Mock
    StateQuery sq;
    @Mock
    ConfigParameters config;
    @Mock
    HealthBoardLookup hbl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        LocalPopulation localPop1 = mock(LocalPopulation.class);
//        when(sq.getCopyOfLocalPopulation(OA_1)).thenReturn(localPop1);
//        LocalPopulation localPop2 = mock(LocalPopulation.class);
//        when(sq.getCopyOfLocalPopulation(OA_2)).thenReturn(localPop2);
//        LocalPopulation localPop3 = mock(LocalPopulation.class);
//        when(sq.getCopyOfLocalPopulation(OA_3)).thenReturn(localPop3);
//        LocalPopulation localPop4 = mock(LocalPopulation.class);
//        when(sq.getCopyOfLocalPopulation(OA_4)).thenReturn(localPop4);
//        when(localPop1.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY)).thenReturn(intSetOf(PERSON_1));
//        when(localPop1.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT)).thenReturn(intSetOf(PERSON_2));
//        when(localPop1.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG)).thenReturn(intSetOf());
//        when(localPop2.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY)).thenReturn(intSetOf(PERSON_3));
//        when(localPop2.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG)).thenReturn(intSetOf(PERSON_4));
//        when(localPop2.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT)).thenReturn(intSetOf());
//        when(localPop3.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY)).thenReturn(intSetOf(PERSON_5));
//        when(localPop3.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG)).thenReturn(intSetOf(PERSON_6));
//        when(localPop3.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT)).thenReturn(intSetOf());
//        when(localPop4.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY)).thenReturn(intSetOf());
//        when(localPop4.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG)).thenReturn(intSetOf());
//        when(localPop4.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT)).thenReturn(intSetOf());
        when(sq.getAllLocationsInArea(DZ,DZ_1)).thenReturn(intSetOf(OA_1,OA_4));
        when(sq.getAllLocationsInArea(DZ,DZ_2)).thenReturn(intSetOf(OA_2,OA_3));
        when(config.getLocalLockdownsAreaLevel()).thenReturn(AreaLevels.DZ);
        when(hbl.getAllDZs()).thenReturn(intSetOf(DZ_1,DZ_2));
    }

    @Test
    public void checkMapInitialisedProperly() {
        instance = new RegionalInfectionsTracker(sq,hbl, config);
        assertEquals(0,instance.getCurrentInfectionLevelPerArea(DZ_1));
        assertEquals(0,instance.getCurrentInfectionLevelPerArea(DZ_2));
    }

    @Test
    public void checkAreaUpdatedProperly() {
        instance = new RegionalInfectionsTracker(sq,hbl, config);
        instance.newMildInfectious(OA_1);
        instance.newMildInfectious(OA_4);
        instance.newMildInfectious(OA_2);
        instance.newMildInfectious(OA_3);
        instance.newMildInfectious(OA_3);
        instance.newMildInfectious(OA_2);
        instance.updateCurrentInfectionLevelPerArea();
        assertEquals(2,instance.getCurrentInfectionLevelPerArea(DZ_1));
        assertEquals(4,instance.getCurrentInfectionLevelPerArea(DZ_2));
    }

    @Test
    public void checkAreaResetProperly() {
        instance = new RegionalInfectionsTracker(sq,hbl, config);
        instance.newMildInfectious(OA_1);
        instance.newMildInfectious(OA_4);
        instance.newMildInfectious(OA_2);
        instance.newMildInfectious(OA_3);
        instance.newMildInfectious(OA_3);
        instance.newMildInfectious(OA_2);
        instance.resetMildInfectiousEvents();
        assertEquals(0,instance.getCurrentInfectionLevelPerArea(DZ_1));
        assertEquals(0,instance.getCurrentInfectionLevelPerArea(DZ_2));
    }
    
}
