/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.lockdown;

import it.unimi.dsi.fastutil.ints.*;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LockdownStatusManager {

    private final TimeManager tm;
    private final Int2ObjectMap<List<LockdownEvent>> lockdownEventLogByArea = new Int2ObjectOpenHashMap<>();
    private final Int2BooleanMap lockdownStatus = new Int2BooleanOpenHashMap();
    private StatisticsCollector stats;
   // private TimeConversions tc;

    public static LockdownStatusManager build(
            TimeManager tm,
            StatisticsCollector stats) {
        return new LockdownStatusManager(tm, stats);
    }

    protected LockdownStatusManager(
            TimeManager tm,
            StatisticsCollector stats) {
        this.tm = tm;
        this.stats = stats;
    }

    public boolean isInLockdown(int locationId) {
        if (lockdownStatus.containsKey(locationId)) {
            return lockdownStatus.get(locationId);
        } else {
            return false;
        }
    }

    public List<LockdownEvent> getCurrentLockdownHistory(int locationId) {
        List<LockdownEvent> currentEvents = new ArrayList<>();
        if (lockdownEventLogByArea.containsKey(locationId)) {
            return lockdownEventLogByArea.get(locationId);
        } else {
            return currentEvents;
        }
    }

    public void enterLockdown(int locationID) {
         stats.newLockdown(locationID);

        if (isInLockdown(locationID)) {
            throw new UnsupportedOperationException("Cannot enter in lockdown status twice");
        } else {
           // System.out.println("Putting area: "+locationID+" into lockdown");
            lockdownStatus.put(locationID, true);
        }

        List<LockdownEvent> currentEvents = new ArrayList<>();
        currentEvents.add(new LockdownStart(tm.getTimeStep()));
        lockdownEventLogByArea.put(locationID, currentEvents);
    }


    private boolean isStarted(int locationID) {
        if (lockdownEventLogByArea.containsKey(locationID)) {
            List<LockdownEvent> currentEvents = lockdownEventLogByArea.get(locationID);
            //TODO This won't work if holding had lockdown in past.  Need to search backwards...
            for (LockdownEvent event : currentEvents) {
                if (event instanceof LockdownStart) {
                    return true;
                }
            }
        }
        return false;
    }

    public void endLockdown(int locationID) {
        if (isStarted(locationID)) {
            List<LockdownEvent> currentEvents = lockdownEventLogByArea.get(locationID);
            long currentTimeStep = tm.getTimeStep();
            currentEvents.add(new LockdownEnd(currentTimeStep));
            lockdownStatus.put(locationID, false);
           // System.out.println("Putting area: "+locationID+" out of lockdown");

            long startTimeStep = currentTimeStep;
            for (int i = currentEvents.size() - 1; i >= 0; i--) {
                LockdownEvent item = currentEvents.get(i);
                if (item instanceof LockdownStart) {
                    startTimeStep = ((LockdownStart) item).timeStep;
                    break;
                }
            }
            stats.lockdownEndDuration(startTimeStep, currentTimeStep, locationID);

        } else {
            throw new UnsupportedOperationException("Lockdown has not started yet");
        }
    }
}
