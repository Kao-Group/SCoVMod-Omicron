/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.communityForRCalc;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import scovmod.model.output.IResult;
import scovmod.model.output.modules.OutputModuleAdapter;
import scovmod.model.output.modules.community.CommunitySummaryResult;
import scovmod.model.output.modules.community.CommunityValueType;
import scovmod.model.output.modules.community.DailyEvent;
import scovmod.model.output.modules.community.DailyResult;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommunitySummary extends OutputModuleAdapter {

    // This contains the data which will be reported in the IResult
    private final Map<DailyResult, Integer> dailyCommunityResult = new HashMap<>();
    private final Map<DailyEvent, Integer> dailyCommunityEvents = new HashMap<>();
    private final Map<DailyResult, Integer> dailyCommunityMIResult = new HashMap<>();

    private Map<DailyResult, Integer> addZeroesToResults(Map<DailyResult, Integer> dailyCommunityResult) {
        Map<DailyResult, Integer> dailyCommunityResultWithZeroes = dailyCommunityResult;
        Set<LocalDate> dates = new HashSet<>();
        //Get all possible dates
        for (Map.Entry<DailyResult, Integer> kvp : dailyCommunityResult.entrySet()) {
            DailyResult dailyResult = kvp.getKey();
            LocalDate date = dailyResult.getDate();
            dates.add(date);
        }
        for (LocalDate date : dates) {
            for (int ca = 5; ca <= 50; ca++) {
                DailyResult ar = new DailyResult(date, CommunityValueType.NEW_MILD_INFECTIOUS, ca);
                if (!dailyCommunityResult.containsKey(ar)) {
                    dailyCommunityResult.put(ar, 0);
                }
            }
        }
        return dailyCommunityResultWithZeroes;
    }

    @Override
    public IResult buildResult() {
        Map<DailyResult, Integer> dailyCommunityResultWithZeroes = this.addZeroesToResults(dailyCommunityResult);
        return new CommunitySummaryResult(dailyCommunityResultWithZeroes);
    }

    @Override
    public void currentTotalExposed(LocalDate date, int value, int community) {
    }

    @Override
    public void currentExposedYoung(LocalDate date, int value, int community) {
    }

    @Override
    public void currentExposedAdult(LocalDate date, int value, int community) {
    }

    @Override
    public void currentExposedElderly(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTotalInfectious(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTotalSusceptible(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SUSCEPTIBLE, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousYoung(LocalDate date, int value, int community) {
    }

    @Override
    public void currentMildInfectiousAdult(LocalDate date, int value, int community) {
    }

    @Override
    public void currentMildInfectiousElderly(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTotalMildInfectious(LocalDate date, int value, int community) {
    }

    @Override
    public void currentSevereInfectiousYoung(LocalDate date, int value, int community) {
    }

    @Override
    public void currentSevereInfectiousAdult(LocalDate date, int value, int community) {
    }

    @Override
    public void currentSevereInfectiousElderly(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTotalSevereInfectious(LocalDate date, int value, int community) {
    }

    @Override
    public void currentRecoveredYoung(LocalDate date, int value, int community) {
    }

    @Override
    public void currentRecoveredAdult(LocalDate date, int value, int community) {
    }

    @Override
    public void currentRecoveredElderly(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTotalRecovered(LocalDate date, int value, int community) {
    }

    @Override
    public void currentDeadYoung(LocalDate date, int value, int community) {
    }

    @Override
    public void currentDeadAdult(LocalDate date, int value, int community) {
    }

    @Override
    public void currentDeadElderly(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTotalDead(LocalDate date, int value, int community) {
    }

    @Override
    public void currentHospitalisedYoung(LocalDate date, int value, int community) {
    }

    @Override
    public void currentHospitalisedAdult(LocalDate date, int value, int community) {
    }

    @Override
    public void currentHospitalisedElderly(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTotalHospitalised(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTracedYoung(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTracedAdult(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTracedElderly(LocalDate date, int value, int community) {
    }

    @Override
    public void currentTraced(LocalDate date, int value, int community) {
    }

    @Override
    public void newMildInfectiousOA(LocalDate date, int locationID, int personID ) {
        DailyEvent de = new DailyEvent(personID, CommunityValueType.NEW_MILD_INFECTIOUS, locationID);
        incrementDailyEvent(de);
    }

    private void incrementDailyEvent(DailyEvent de) {
        if (dailyCommunityEvents.containsKey(de)) {
            //System.out.println("This shouldn't happen - only one event per person"); // Note there is a chance this might happen with R to S jumps now
        } else {
            dailyCommunityEvents.put(de, 1);
        }
    }

    @Override
    public void aggregateMildInfectiousEvents(LocalDate date, Int2DoubleMap caseMultiplierMap) {
        for (Map.Entry<DailyEvent, Integer> kvp : dailyCommunityEvents.entrySet()) {
            DailyEvent dailyEvent = kvp.getKey();
            int ca = dailyEvent.getCommunityID();
            DailyResult ar = new DailyResult(date, CommunityValueType.NEW_MILD_INFECTIOUS, ca);
            updateForDailyResult(ar, 1);
            incrementDailyMIResult(ar, 1);
        }
        dailyCommunityEvents.clear(); //Clear to properly count for next week
//        for (Map.Entry<DailyResult, Integer> kvp : dailyCommunityMIResult.entrySet()) {
//            DailyResult dailyResult = kvp.getKey();
//            int value = kvp.getValue();
//            int ca = dailyResult.getCommunityId();
//            if (dailyResult.getType() == CommunityValueType.NEW_MILD_INFECTIOUS &&
//                    dailyResult.getDate() == date) {
//                double caseMultiplier = 0;
//                if (caseMultiplierMap.containsKey(ca)) caseMultiplier = caseMultiplierMap.get(ca);
//                DailyResult ar_case = new DailyResult(date, CommunityValueType.CASES, ca);
//                int numberCases = (int) (value*caseMultiplier);
////                System.out.println("Number multiplier: "+caseMultiplier+" for ca: "+ca);
////                System.out.println("Number MI: "+value+" for ca: "+ca);
////                System.out.println("Number cases: "+numberCases+" for ca: "+ca);
//                if(numberCases>0) updateForDailyResult(ar_case, numberCases);
//            }
//        }
    }

    @Override
    public void newLockdown(LocalDate date, int community) {
    }

    @Override
    public void lockdownEndDuration(LocalDate date, int durationTimeSteps, int community) {
    }

    //private void updateForDailyResult(DailyResult ar, int value) { //Want to combine two half day timesteps
//        dailyCommunityResult.put(ar,  value);
//    }
    private void updateForDailyResult(DailyResult ar, int value) {
        if (dailyCommunityResult.containsKey(ar)) {
            int currentCount = dailyCommunityResult.get(ar);
            dailyCommunityResult.put(ar, currentCount + value);
        } else {
            dailyCommunityResult.put(ar, value);
        }
    }
    private void incrementDailyMIResult(DailyResult ar, int value) {
        if (dailyCommunityMIResult.containsKey(ar)) {
            int currentCount = dailyCommunityMIResult.get(ar);
            dailyCommunityMIResult.put(ar, currentCount + value);
        } else {
            dailyCommunityMIResult.put(ar, value);
        }
    }
}
