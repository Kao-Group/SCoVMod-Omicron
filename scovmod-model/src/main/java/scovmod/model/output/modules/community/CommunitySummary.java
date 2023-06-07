/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.community;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import scovmod.model.output.IResult;
import scovmod.model.output.modules.OutputModuleAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CommunitySummary extends OutputModuleAdapter {

    // This contains the data which will be reported in the IResult
    private final Map<DailyResult, Integer> dailyCommunityResult = new HashMap<>();
    private final Map<DailyEvent, Integer> dailyCommunityEvents = new HashMap<>();
    private final Map<DailyResult, Integer> dailyCommunityMIResult = new HashMap<>();

    @Override
    public IResult buildResult() {
        return new CommunitySummaryResult(dailyCommunityResult);
    }

    @Override
    public void currentTotalExposed(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalInfectious(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.INFECTIOUS, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalSusceptible(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SUSCEPTIBLE, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSusceptibleYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SUSCEPTIBLE_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSusceptibleAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SUSCEPTIBLE_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSusceptibleElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SUSCEPTIBLE_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalVaccinatedSusceptible(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.VACCINATED_SUSCEPTIBLE, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalVaccinatedNonSusceptible(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.VACCINATED_NOT_SUSCEPTIBLE, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedSusceptibleYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.VACCINATED_SUSCEPTIBLE_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedSusceptibleAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.VACCINATED_SUSCEPTIBLE_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedSusceptibleElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.VACCINATED_SUSCEPTIBLE_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedNonSusceptibleYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.VACCINATED_NOT_SUSCEPTIBLE_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedNonSusceptibleAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.VACCINATED_NOT_SUSCEPTIBLE_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedNonSusceptibleElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.VACCINATED_NOT_SUSCEPTIBLE_ELDERLY, community);
        updateForDailyResult(ar, value);
    }


    @Override
    public void currentTotalFullyProtected(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.FULL_EFFICACY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialProtected(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_EFFICACY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossVacProtected(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossNatProtected(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalFullyProtectedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.FULL_EFFICACY_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalFullyProtectedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.FULL_EFFICACY_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalFullyProtectedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.FULL_EFFICACY_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialProtectedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_EFFICACY_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialProtectedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_EFFICACY_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialProtectedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_EFFICACY_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossVacProtectedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossVacProtectedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossVacProtectedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossNatProtectedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossNatProtectedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossNatProtectedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalMildInfectious(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalSevereInfectious(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalRecovered(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalDead(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalHospitalised(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTracedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.TRACED_YOUNG, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTracedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.TRACED_ADULT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTracedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.TRACED_ELDERLY, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTraced(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.TRACED, community);
        updateForDailyResult(ar, value);
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
        for (Map.Entry<DailyResult, Integer> kvp : dailyCommunityMIResult.entrySet()) {
            DailyResult dailyResult = kvp.getKey();
            int value = kvp.getValue();
            int ca = dailyResult.getCommunityId();
            if (dailyResult.getType() == CommunityValueType.NEW_MILD_INFECTIOUS &&
                    dailyResult.getDate() == date) {
                double caseMultiplier = 0;
                if (caseMultiplierMap.containsKey(ca)) caseMultiplier = caseMultiplierMap.get(ca);
                DailyResult ar_case = new DailyResult(date, CommunityValueType.CASES, ca);
                int numberCases = (int) (value*caseMultiplier);
//                System.out.println("Number multiplier: "+caseMultiplier+" for ca: "+ca);
//                System.out.println("Number MI: "+value+" for ca: "+ca);
//                System.out.println("Number cases: "+numberCases+" for ca: "+ca);
                if(numberCases>0) updateForDailyResult(ar_case, numberCases);
            }
        }
    }

    @Override
    public void newLockdown(LocalDate date, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.NEW_LOCKDOWN, community);
        updateForDailyResult(ar, 1);
    }

    @Override
    public void lockdownEndDuration(LocalDate date, int durationTimeSteps, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.LOCKDOWN_DURATION, community);
        updateForDailyResult(ar, durationTimeSteps);
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

    // Handle variants

    @Override
    public void currentTotalExposedVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedYoungVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_YOUNG_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedAdultVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_ADULT_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentExposedElderlyVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.EXPOSED_ELDERLY_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalInfectiousVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.INFECTIOUS_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousYoungVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_YOUNG_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousAdultVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_ADULT_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentMildInfectiousElderlyVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_ELDERLY_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalMildInfectiousVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.MILD_INFECTIOUS_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousYoungVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_YOUNG_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousAdultVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_ADULT_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentSevereInfectiousElderlyVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_ELDERLY_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalSevereInfectiousVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.SEVERE_INFECTIOUS_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredYoungVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_YOUNG_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredAdultVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_ADULT_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentRecoveredElderlyVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_ELDERLY_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalRecoveredVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.RECOVERED_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadYoungVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_YOUNG_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadAdultVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_ADULT_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentDeadElderlyVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_ELDERLY_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalDeadVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.DEAD_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedYoungVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_YOUNG_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedAdultVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_ADULT_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentHospitalisedElderlyVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_ELDERLY_VARIANT, community);
        updateForDailyResult(ar, value);
    }

    @Override
    public void currentTotalHospitalisedVariant(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, CommunityValueType.HOSPITALISED_VARIANT, community);
        updateForDailyResult(ar, value);
    }

}
