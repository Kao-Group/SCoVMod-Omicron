/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.StatesForWW;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import scovmod.model.output.IResult;
import scovmod.model.output.modules.OutputModuleAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StatesForWWSummary extends OutputModuleAdapter {
    
    private final Map<DailyResult, Integer> dailyDZResult = new HashMap<>();
    private final Map<DailyResult, Integer> dailyDZMIResult = new HashMap<>();
    private final Map<DailyEvent, Integer> dailyDZEvents = new HashMap<>();

    @Override
    public IResult buildResult() {
        return new StatesForWWSummaryResult(dailyDZResult);
    }

    @Override
    public void newMildInfectiousDZ(LocalDate date, int dz, int personID) {
        if(dz!=0) { 
            DailyEvent de = new DailyEvent(personID, StatesForWWValueType.NEW_MILD_INFECTIOUS, dz);
            incrementDailyEvent(de);
        }
    }

    @Override
    public void aggregateMildInfectiousEvents(LocalDate date, Int2DoubleMap caseMultiplierMap) {
        for (Map.Entry<DailyEvent, Integer> kvp : dailyDZEvents.entrySet()) {
            DailyEvent dailyEvent = kvp.getKey();
            int dz = dailyEvent.getDzID();
            DailyResult ar = new DailyResult(date, StatesForWWValueType.NEW_MILD_INFECTIOUS, dz);
            incrementDailyResult(ar, 1);
            incrementDailyMIResult(ar, 1);
        }
        dailyDZEvents.clear(); //Clear to properly count for next week
        for (Map.Entry<DailyResult, Integer> kvp : dailyDZMIResult.entrySet()) {
            DailyResult dailyResult = kvp.getKey();
            int value = kvp.getValue();
            int dz = dailyResult.getDzID();
            if (dailyResult.getType() == StatesForWWValueType.NEW_MILD_INFECTIOUS &&
                    dailyResult.getDate() == date) {
                double caseMultiplier = 0;
                if (caseMultiplierMap.containsKey(dz)) caseMultiplier = caseMultiplierMap.get(dz);
                DailyResult ar_case = new DailyResult(date, StatesForWWValueType.CASES, dz);
                int numberCases = (int) (value*caseMultiplier);
//                System.out.println("Number multiplier: "+caseMultiplier+" for dz: "+dz);
//                System.out.println("Number MI: "+value+" for dz: "+dz);
//                System.out.println("Number cases: "+numberCases+" for dz: "+dz);
                if(numberCases>0) incrementDailyResult(ar_case, numberCases);
            }
        }
    }


    @Override
    public void currentTotalMildInfectious(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, StatesForWWValueType.MILD_INFECTIOUS, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalSevereInfectious(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, StatesForWWValueType.SEVERE_INFECTIOUS, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalHospitalised(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, StatesForWWValueType.HOSPITALISED, dz);
            incrementDailyResult(ar, value);
        }
    }

    // Handle variants

    private void incrementDailyResult(DailyResult ar, int value) {
        if (dailyDZResult.containsKey(ar)) {
            int currentCount = dailyDZResult.get(ar);
            dailyDZResult.put(ar, currentCount + value);
        } else {
            dailyDZResult.put(ar, value);
        }
    }

    private void incrementDailyMIResult(DailyResult ar, int value) {
        if (dailyDZMIResult.containsKey(ar)) {
            int currentCount = dailyDZMIResult.get(ar);
            dailyDZMIResult.put(ar, currentCount + value);
        } else {
            dailyDZMIResult.put(ar, value);
        }
    }

    private void incrementDailyEvent(DailyEvent de) {
        if (dailyDZEvents.containsKey(de)) {
//            int currentCount = dailyDZEvents.get(de);
//            dailyDZEvents.put(de, currentCount + 1);
            //System.out.println("This shouldn't happen - only one event per person"); // Note there is a chance this might happen with R to S jumps now
        } else {
            dailyDZEvents.put(de, 1);
        }
    }

}
