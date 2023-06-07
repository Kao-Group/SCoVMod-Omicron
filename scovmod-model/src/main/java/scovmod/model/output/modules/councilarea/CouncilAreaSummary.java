/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.councilarea;

import it.unimi.dsi.fastutil.ints.*;
import scovmod.model.output.IResult;
import scovmod.model.output.modules.OutputModuleAdapter;

import java.time.LocalDate;
import java.util.*;

public class CouncilAreaSummary extends OutputModuleAdapter {

    // This contains the data which will be reported in the IResult
    //private final Map<DailyResult, Integer> dailyCouncilAreaResult = new TreeMap<>();
    private final Map<DailyResult, Integer> dailyCouncilAreaResult = new HashMap<>();
    private final Map<DailyResult, Integer> dailyCouncilAreaMIResult = new HashMap<>();
    private final Map<DailyEvent, Integer> dailyCouncilAreaEvents = new HashMap<>();
    private final Map<DailyResult, Integer> nonCumulativeCouncilAreaResult = new HashMap<>();
    private Int2ObjectMap<IntOpenHashSet> dzsPerCouncilAreaWithDead = new Int2ObjectOpenHashMap<>();
    private Int2ObjectMap<IntOpenHashSet> dzsPerCouncilAreaWithMildInfected = new Int2ObjectOpenHashMap<>();

    @Override
    public IResult buildResult() {
//        //Convert cumulative to just incidence per week
//        //System.out.println("dailyCouncilAreaResult: "+dailyCouncilAreaResult);
//        Map<Integer,Integer> previousValuePerAreaDead = new Int2IntOpenHashMap();
//        for (Map.Entry<DailyResult, Integer> kvp : dailyCouncilAreaResult.entrySet()) {
//            DailyResult dailyResult = kvp.getKey();
//            if(dailyResult.getType()==CouncilAreaValueType.DEAD) {
//                int newValue = kvp.getValue();
//                int area = dailyResult.getCouncilAreaID();
//                CouncilAreaValueType type = dailyResult.getType();
//                int previousValue = 0;
//                if (previousValuePerAreaDead.containsKey(area)) previousValue = previousValuePerAreaDead.get(area);
//                int diff = newValue - previousValue;
//                nonCumulativeCouncilAreaResult.put(dailyResult, diff);
//                previousValuePerAreaDead.put(area, newValue);
//            }
//        }
//        Map<Integer,Integer> previousValuePerAreaDZ = new Int2IntOpenHashMap();
//
//        for (Map.Entry<DailyResult, Integer> kvp : dailyCouncilAreaResult.entrySet()) {
//            DailyResult dailyResult = kvp.getKey();
//            if(dailyResult.getType()==CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_DEAD) {
//                int newValue = kvp.getValue();
//                int area = dailyResult.getCouncilAreaID();
//                CouncilAreaValueType type = dailyResult.getType();
//                int previousValue = 0;
//                if (previousValuePerAreaDZ.containsKey(area)) previousValue = previousValuePerAreaDZ.get(area);
//                int diff = newValue - previousValue;
//                nonCumulativeCouncilAreaResult.put(dailyResult, diff);
//                previousValuePerAreaDZ.put(area, newValue);
//            }
//        }
        //System.out.println("nonCumulativeCouncilAreaResult: "+nonCumulativeCouncilAreaResult);
       // return new CouncilAreaSummaryResult(nonCumulativeCouncilAreaResult);
        // Note: Use only next line for cumulative - to add zeroes when there are no entries - to aid plots
        Map<DailyResult, Integer> dailyCouncilAreaResultWithZeroes = this.addZeroesToResults(dailyCouncilAreaResult);
        return new CouncilAreaSummaryResult(dailyCouncilAreaResultWithZeroes);
    }

    private Map<DailyResult, Integer> addZeroesToResults(Map<DailyResult, Integer> dailyCouncilAreaResult) {
        Map<DailyResult, Integer> dailyCouncilAreaResultWithZeroes = dailyCouncilAreaResult;
        Set<LocalDate> dates = new HashSet<>();
        //Get all possible dates
        for (Map.Entry<DailyResult, Integer> kvp : dailyCouncilAreaResult.entrySet()) {
            DailyResult dailyResult = kvp.getKey();
            LocalDate date = dailyResult.getDate();
            dates.add(date);
        }
        for (LocalDate date : dates) {
//            for (int ca = 5; ca <= 50; ca++) {
//                DailyResult ar = new DailyResult(date, CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_DEAD, ca);
//                if (!dailyCouncilAreaResult.containsKey(ar)) {
//                    dailyCouncilAreaResult.put(ar, 0);
//                }
//            }
        }
        return dailyCouncilAreaResultWithZeroes;
    }

    @Override
    public void currentTotalDeadCA(LocalDate date, int value, int ca) {
        if(ca!=0) { //&& ca!=27) { //Avoid counting shetland or 0
            DailyResult ar = new DailyResult(date, CouncilAreaValueType.DEAD, ca);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void newMildInfectiousCA(LocalDate date, int ca, int personID) {
        if(ca!=0) { //&& ca!=27) { //Avoid counting shetland or 0
            DailyEvent de = new DailyEvent(personID, CouncilAreaValueType.NEW_MILD_INFECTIOUS, ca);
            incrementDailyEvent(de);
        }
    }

    @Override
    public void newLockdown(LocalDate date, int ca) {
        if(ca!=0) { // && ca!=27) { //Avoid counting shetland or 0
            DailyResult ar = new DailyResult(date, CouncilAreaValueType.LOCAL_LOCKDOWNS, ca);
            incrementDailyResult(ar,1);
        }
    }

    @Override
    public void aggregateMildInfectiousEvents(LocalDate date, Int2DoubleMap caseMultiplierMap) {
        for (Map.Entry<DailyEvent, Integer> kvp : dailyCouncilAreaEvents.entrySet()) {
            DailyEvent dailyEvent = kvp.getKey();
            int ca = dailyEvent.getCouncilAreaID();
            DailyResult ar = new DailyResult(date, CouncilAreaValueType.NEW_MILD_INFECTIOUS, ca);
            incrementDailyResult(ar, 1);
            incrementDailyMIResult(ar, 1);
        }
        dailyCouncilAreaEvents.clear(); //Clear to properly count for next week
        for (Map.Entry<DailyResult, Integer> kvp : dailyCouncilAreaMIResult.entrySet()) {
            DailyResult dailyResult = kvp.getKey();
            int value = kvp.getValue();
            int ca = dailyResult.getCouncilAreaID();
            if (dailyResult.getType() == CouncilAreaValueType.NEW_MILD_INFECTIOUS &&
                    dailyResult.getDate() == date) {
                double caseMultiplier = 0;
                if (caseMultiplierMap.containsKey(ca)) caseMultiplier = caseMultiplierMap.get(ca);
                DailyResult ar_case = new DailyResult(date, CouncilAreaValueType.CASES, ca);
                int numberCases = (int) (value*caseMultiplier);
//                System.out.println("Number multiplier: "+caseMultiplier+" for ca: "+ca);
//                System.out.println("Number MI: "+value+" for ca: "+ca);
//                System.out.println("Number cases: "+numberCases+" for ca: "+ca);
                if(numberCases>0) incrementDailyResult(ar_case, numberCases);
            }
        }
    }

    @Override
    public void currentTotalExposed(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentExposedElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.EXPOSED_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalInfectious(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.INFECTIOUS, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalMildInfectious(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.MILD_INFECTIOUS, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalSevereInfectious(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.SEVERE_INFECTIOUS, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalRecovered(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.RECOVERED, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentDeadElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalDead(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.DEAD, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedYoung(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_YOUNG, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedAdult(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_ADULT, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedElderly(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED_ELDERLY, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalHospitalised(LocalDate date, int value, int community) {
//        DailyResult ar = new DailyResult(date, HealthBoardValueType.HOSPITALISED, community);
//        incrementDailyResult(ar, value);
    }

    @Override
    public void currentNumberDZAreasWithDeadPerCouncilArea(LocalDate date, int dzAreaWithDead, int councilArea) {
//        if(dzsPerCouncilAreaWithDead.containsKey(councilArea)){
//            //System.out.println("Council area "+councilArea+" already has dead");
//            IntOpenHashSet dzsWithDead = dzsPerCouncilAreaWithDead.get(councilArea);
//            if(!dzsWithDead.contains(dzAreaWithDead)) { //Doesn't contain it already then increment
//                ///System.out.println("This is a new DZ");
//                dzsWithDead.add(dzAreaWithDead);
//                dzsPerCouncilAreaWithDead.put(councilArea, dzsWithDead);
//                DailyResult ar = new DailyResult(date, CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_DEAD, councilArea);
//                //incrementDailyResult(ar, izsWithDead.size());
//                // new DZ area with dead  so increment for this council area
//                //System.out.println("Increasing number of DZ's in CA "+councilArea+" to "+dzsWithDead.size());
//                dailyCouncilAreaResult.put(ar, dzsWithDead.size());
//            } else { // Still want to report even if no change
//               // System.out.println("Keeping number of DZ's in CA "+councilArea+" to "+dzsWithDead.size());
//                DailyResult ar = new DailyResult(date, CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_DEAD, councilArea);
//                dailyCouncilAreaResult.put(ar, dzsWithDead.size());
//            }
//        } else { // No entries for this healthboard yet so create new empty set and add - then increment stat
//            //System.out.println("Council area "+councilArea+" not registered yet");
//            IntOpenHashSet dzsWithDead = new IntOpenHashSet();
//            dzsWithDead.add(dzAreaWithDead);
//            dzsPerCouncilAreaWithDead.put(councilArea, dzsWithDead);
//            DailyResult ar = new DailyResult(date, CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_DEAD, councilArea);
//            //System.out.println("Increasing number of DZs to 1");
//            incrementDailyResult(ar, 1); //new DZ area with dead  so increment for this council; area
//        }
    }

    public void currentNumberDZAreasWithInfectedPerCouncilArea(LocalDate date, int dzAreaWithInfected, int councilArea) {
        if(dzsPerCouncilAreaWithMildInfected.containsKey(councilArea)){
            //System.out.println("Council area "+councilArea+" already has infected");
            IntOpenHashSet dzsWithInfected = dzsPerCouncilAreaWithMildInfected.get(councilArea);
            if(!dzsWithInfected.contains(dzAreaWithInfected)) { //Doesn't contain it already then increment
                ///System.out.println("This is a new DZ");
                dzsWithInfected.add(dzAreaWithInfected);
                dzsPerCouncilAreaWithMildInfected.put(councilArea, dzsWithInfected);
                DailyResult ar = new DailyResult(date, CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_MILD_INFECTIOUS, councilArea);
                //System.out.println("Increasing number of DZ's in CA "+councilArea+" to "+dzsWithInfected.size());
                dailyCouncilAreaResult.put(ar, dzsWithInfected.size());
            } else { // Still want to report even if no change
                // System.out.println("Keeping number of DZ's in CA "+councilArea+" to "+dzsWithInfected.size());
                DailyResult ar = new DailyResult(date, CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_MILD_INFECTIOUS, councilArea);
                dailyCouncilAreaResult.put(ar, dzsWithInfected.size());
            }
        } else { // No entries for this council area yet so create new empty set and add - then increment stat
            //System.out.println("Council area "+councilArea+" not registered yet");
            IntOpenHashSet dzsWithInfected = new IntOpenHashSet();
            dzsWithInfected.add(dzAreaWithInfected);
            dzsPerCouncilAreaWithMildInfected.put(councilArea, dzsWithInfected);
            DailyResult ar = new DailyResult(date, CouncilAreaValueType.NUMBER_DZ_AREAS_WITH_MILD_INFECTIOUS, councilArea);
            //System.out.println("Increasing number of DZs to 1");
            incrementDailyResult(ar, 1); //new DZ area with infected  so increment for this council area
        }
    }

    private void incrementDailyResult(DailyResult ar, int value) {
        if (dailyCouncilAreaResult.containsKey(ar)) {
            int currentCount = dailyCouncilAreaResult.get(ar);
            dailyCouncilAreaResult.put(ar, currentCount + value);
        } else {
            dailyCouncilAreaResult.put(ar, value);
        }
    }

    private void incrementDailyMIResult(DailyResult ar, int value) {
        if (dailyCouncilAreaMIResult.containsKey(ar)) {
            int currentCount = dailyCouncilAreaMIResult.get(ar);
            dailyCouncilAreaMIResult.put(ar, currentCount + value);
        } else {
            dailyCouncilAreaMIResult.put(ar, value);
        }
    }

    private void incrementDailyEvent(DailyEvent de) {
        if (dailyCouncilAreaEvents.containsKey(de)) {
//            int currentCount = dailyCouncilAreaEvents.get(de);
//            dailyCouncilAreaEvents.put(de, currentCount + 1);
           // System.out.println("This shouldn't happen - only one event per person"); // Note there is a chance this might happen with R to S jumps now
        } else {
            dailyCouncilAreaEvents.put(de, 1);
        }
    }

}
