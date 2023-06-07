package scovmod.model.lockdown;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.time.TimeManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LockdownStatusManagerTest {

    private LockdownStatusManager instance;

    private final long TIMESTEP_1 = 1100;
    private final long TIMESTEP_3 = 1102;
    private final int AREA_1 = 100;

    private final boolean NOT_IN_LOCKDOWN = false;
    private final boolean IN_LOCKDOWN = true;

    @Mock
    TimeManager tm;
    @Mock
    StatisticsCollector stat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        instance = new LockdownStatusManager(tm, stat);
    }

    @Test
    public void defaultNotInLockdown() {
        assertEquals(NOT_IN_LOCKDOWN, instance.isInLockdown(AREA_1));
    }

    @Test
    public void defaultNoLockdownHistory() {
        assertEquals(0, instance.getCurrentLockdownHistory(AREA_1).size());
    }

    @Test
    public void enterLockdown() {
        when(tm.getTimeStep()).thenReturn(TIMESTEP_1);

        instance.enterLockdown(AREA_1);

        assertEquals(IN_LOCKDOWN, instance.isInLockdown(AREA_1));

        List<LockdownEvent> expected = new ArrayList<>();
        expected.add(new LockdownStart(TIMESTEP_1));
        assertEquals(expected, instance.getCurrentLockdownHistory(AREA_1));

       // verify(stat).newLockdown(AREA_1);
    }

    @Test
    public void exitingLockdown() {
        when(tm.getTimeStep()).thenReturn(TIMESTEP_1);
        instance.enterLockdown(AREA_1);

        when(tm.getTimeStep()).thenReturn(TIMESTEP_3);
        instance.endLockdown(AREA_1);

        assertEquals(NOT_IN_LOCKDOWN, instance.isInLockdown(AREA_1));
        //verify(stat).lockdownEndDuration(TIMESTEP_1, TIMESTEP_3, AREA_1, true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void exitingLockdownBeforeItStarted() {
        when(tm.getTimeStep()).thenReturn(TIMESTEP_3);
        instance.endLockdown(AREA_1);
    }

    @Test
    public void cantSetInLockdownTwice() {
        instance.enterLockdown(AREA_1);
        try {
            instance.enterLockdown(AREA_1);
            fail("Expected exception");
        } catch (UnsupportedOperationException e) {
        }
    }
}
