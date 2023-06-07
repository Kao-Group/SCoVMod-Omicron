package scovmod.model.lockdown;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.StateQuery;
import scovmod.model.time.TimeManager;

import static org.mockito.Mockito.*;
import static scovmod.model.util.TestUtils.intSetOf;

public class LockdownTriggerManagerTest {

    private LockdownTriggerManager instance;

    private final long TIMESTEP_1 = 1100;
    private final long TIMESTEP_3 = 1102;
    private final int IZ_1 = 100;
    private final int IZ_2 = 200;
    private final int HB_1 = 300;
    private final int HB_2 = 400;

    private static final double TOL = 1e-10;


    @Mock
    StateQuery sq;
    @Mock
    ConfigParameters config;
    @Mock
    HealthBoardLookup hbl;
    @Mock
    RegionalInfectionsTracker rit;
    @Mock
    LockdownStatusManager lst;
    @Mock
    TimeManager tm;
    @Mock
    PopulationSizePerAreaLookup pspa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void noLockdownAsLockdownsNotActivated() {
        instance = new LockdownTriggerManager(sq,hbl, config,rit, lst,tm, pspa);
        when(config.isLocalLockdownsActivated()).thenReturn(false);
        instance.updateAreaLockdownStatuses();
        verifyZeroInteractions(lst);
    }

    @Test
    public void noLockdownAsLockdownsNotStartedYet() {
        instance = new LockdownTriggerManager(sq,hbl, config,rit, lst,tm, pspa);
        when(config.isLocalLockdownsActivated()).thenReturn(true);
        when(config.getLocalLockdownStartTimeStep()).thenReturn((int)TIMESTEP_3);
        when(tm.getTimeStep()).thenReturn(TIMESTEP_1);
        instance.updateAreaLockdownStatuses();
        verifyZeroInteractions(lst);
    }

    @Test
    public void enterLockdownBecauseThresholdofSIReached() {
        when(config.getLocalLockdownsAreaLevel()).thenReturn(AreaLevels.IZ);
        when(config.getMildInfectiousUpperLevel()).thenReturn(15.0,TOL);
        when(config.getMildInfectiousLowerLevel()).thenReturn(10.0, TOL);

        instance = new LockdownTriggerManager(sq,hbl, config,rit, lst,tm, pspa);

        Int2IntMap populationPerCA = new Int2IntOpenHashMap();
        populationPerCA.put(IZ_1,10); //TODO Should really be for CA - properly implement for all area sizes.
        populationPerCA.put(IZ_2,5);
        when(pspa.getPopByCouncilAreaLookup()).thenReturn(populationPerCA);

        when(config.isLocalLockdownsActivated()).thenReturn(true);
        when(config.getLocalLockdownStartTimeStep()).thenReturn((int)TIMESTEP_3);
        when(tm.getTimeStep()).thenReturn(TIMESTEP_3);
        when(hbl.getAllIZs()).thenReturn(intSetOf(IZ_1,IZ_2));
        when(lst.isInLockdown(IZ_1)).thenReturn(false);
        when(lst.isInLockdown(IZ_2)).thenReturn(false);
        when(rit.getCurrentInfectionLevelPerArea(IZ_1)).thenReturn(200);
        when(rit.getCurrentInfectionLevelPerArea(IZ_2)).thenReturn(100);

        instance.updateAreaLockdownStatuses();

        verify(lst).enterLockdown(IZ_1);
    }

    @Test
    public void exitingLockdownBecauseSIGoesLowerThanThreshold() {
        when(config.getLocalLockdownsAreaLevel()).thenReturn(AreaLevels.HB);
        when(config.getMildInfectiousUpperLevel()).thenReturn(15.0,TOL);
        when(config.getMildInfectiousLowerLevel()).thenReturn(12.0,TOL);

        instance = new LockdownTriggerManager(sq,hbl, config,rit, lst,tm, pspa);

        Int2IntMap populationPerCA = new Int2IntOpenHashMap();
        populationPerCA.put(HB_1,10); //TODO Should really be for CA - properly implement for all area sizes.
        populationPerCA.put(HB_2,50);
        when(pspa.getPopByCouncilAreaLookup()).thenReturn(populationPerCA);

        when(config.isLocalLockdownsActivated()).thenReturn(true);
        when(config.getLocalLockdownStartTimeStep()).thenReturn((int)TIMESTEP_3);
        when(tm.getTimeStep()).thenReturn(TIMESTEP_3);
        when(hbl.getAllHBs()).thenReturn(intSetOf(HB_1,HB_2));
        when(lst.isInLockdown(HB_1)).thenReturn(true);
        when(lst.isInLockdown(HB_2)).thenReturn(true);
        when(rit.getCurrentInfectionLevelPerArea(HB_1)).thenReturn(200);
        when(rit.getCurrentInfectionLevelPerArea(HB_2)).thenReturn(100);

        instance.updateAreaLockdownStatuses();

        verify(lst).endLockdown(HB_2);
    }

}
