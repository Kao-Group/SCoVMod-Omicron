/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.dzAllStates;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import scovmod.model.output.IResult;
import scovmod.model.output.modules.OutputModuleAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DZAllStatesSummary extends OutputModuleAdapter {
    
    private final Map<DailyResult, Integer> dailyDZResult = new HashMap<>();
    private final Map<DailyResult, Integer> dailyDZMIResult = new HashMap<>();
    private final Map<DailyEvent, Integer> dailyDZEvents = new HashMap<>();

    @Override
    public IResult buildResult() {
        return new DZAllStatesSummaryResult(dailyDZResult);
    }

    @Override
    public void currentTotalDead(LocalDate date, int value, int dz) {
        if(dz!=0) {
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.DEAD, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void newMildInfectiousDZ(LocalDate date, int dz, int personID) {
        if(dz!=0) { 
            DailyEvent de = new DailyEvent(personID, DZAllStatesValueType.NEW_MILD_INFECTIOUS, dz);
            incrementDailyEvent(de);
        }
    }

    @Override
    public void newLockdown(LocalDate date, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.LOCAL_LOCKDOWNS, dz);
            incrementDailyResult(ar,1);
        }
    }

    @Override
    public void aggregateMildInfectiousEvents(LocalDate date, Int2DoubleMap caseMultiplierMap) {
        for (Map.Entry<DailyEvent, Integer> kvp : dailyDZEvents.entrySet()) {
            DailyEvent dailyEvent = kvp.getKey();
            int dz = dailyEvent.getDzID();
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.NEW_MILD_INFECTIOUS, dz);
            incrementDailyResult(ar, 1);
            incrementDailyMIResult(ar, 1);
        }
        dailyDZEvents.clear(); //Clear to properly count for next week
        for (Map.Entry<DailyResult, Integer> kvp : dailyDZMIResult.entrySet()) {
            DailyResult dailyResult = kvp.getKey();
            int value = kvp.getValue();
            int dz = dailyResult.getDzID();
            if (dailyResult.getType() == DZAllStatesValueType.NEW_MILD_INFECTIOUS &&
                    dailyResult.getDate() == date) {
                double caseMultiplier = 0;
                if (caseMultiplierMap.containsKey(dz)) caseMultiplier = caseMultiplierMap.get(dz);
                DailyResult ar_case = new DailyResult(date, DZAllStatesValueType.CASES, dz);
                int numberCases = (int) (value*caseMultiplier);
//                System.out.println("Number multiplier: "+caseMultiplier+" for dz: "+dz);
//                System.out.println("Number MI: "+value+" for dz: "+dz);
//                System.out.println("Number cases: "+numberCases+" for dz: "+dz);
                if(numberCases>0) incrementDailyResult(ar_case, numberCases);
            }
        }
    }

    @Override
    public void currentTotalSusceptible(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SUSCEPTIBLE, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentSusceptibleYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.SUSCEPTIBLE_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSusceptibleAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.SUSCEPTIBLE_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentSusceptibleElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.SUSCEPTIBLE_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalVaccinatedSusceptible(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.VACCINATED_SUSCEPTIBLE, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalVaccinatedNonSusceptible(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.VACCINATED_NOT_SUSCEPTIBLE, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedSusceptibleYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.VACCINATED_SUSCEPTIBLE_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedSusceptibleAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.VACCINATED_SUSCEPTIBLE_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedSusceptibleElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.VACCINATED_SUSCEPTIBLE_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedNonSusceptibleYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.VACCINATED_NOT_SUSCEPTIBLE_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedNonSusceptibleAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.VACCINATED_NOT_SUSCEPTIBLE_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentVaccinatedNonSusceptibleElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.VACCINATED_NOT_SUSCEPTIBLE_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalFullyProtected(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.FULL_EFFICACY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialProtected(LocalDate date, int value, int community) {
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_EFFICACY, community);
            incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossVacProtected(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossNatProtected(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalFullyProtectedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.FULL_EFFICACY_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalFullyProtectedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.FULL_EFFICACY_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalFullyProtectedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.FULL_EFFICACY_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialProtectedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_EFFICACY_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialProtectedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_EFFICACY_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialProtectedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_EFFICACY_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossVacProtectedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossVacProtectedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossVacProtectedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossNatProtectedYoung(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY_YOUNG, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossNatProtectedAdult(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY_ADULT, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalPartialImmLossNatProtectedElderly(LocalDate date, int value, int community) {
        DailyResult ar = new DailyResult(date, DZAllStatesValueType.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY_ELDERLY, community);
        incrementDailyResult(ar, value);
    }

    @Override
    public void currentTotalExposed(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.EXPOSED, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentExposedYoung(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.EXPOSED_YOUNG, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentExposedAdult(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.EXPOSED_ADULT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentExposedElderly(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.EXPOSED_ELDERLY, dz);
            incrementDailyResult(ar, value);
        }
    }


    @Override
    public void currentTotalInfectious(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.INFECTIOUS, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentMildInfectiousYoung(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.MILD_INFECTIOUS_YOUNG, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentMildInfectiousAdult(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.MILD_INFECTIOUS_ADULT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentMildInfectiousElderly(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.MILD_INFECTIOUS_ELDERLY, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalMildInfectious(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.MILD_INFECTIOUS, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentSevereInfectiousYoung(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SEVERE_INFECTIOUS_YOUNG, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentSevereInfectiousAdult(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SEVERE_INFECTIOUS_ADULT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentSevereInfectiousElderly(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SEVERE_INFECTIOUS_ELDERLY, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalSevereInfectious(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SEVERE_INFECTIOUS, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentRecoveredYoung(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.RECOVERED_YOUNG, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentRecoveredAdult(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.RECOVERED_ADULT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentRecoveredElderly(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.RECOVERED_ELDERLY, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalRecovered(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.RECOVERED, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentDeadYoung(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.DEAD_YOUNG, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentDeadAdult(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.DEAD_ADULT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentDeadElderly(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.DEAD_ELDERLY, dz);
            incrementDailyResult(ar, value);
        }
    }
    

    @Override
    public void currentHospitalisedYoung(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.HOSPITALISED_YOUNG, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentHospitalisedAdult(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.HOSPITALISED_ADULT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentHospitalisedElderly(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.HOSPITALISED_ELDERLY, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalHospitalised(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.HOSPITALISED, dz);
            incrementDailyResult(ar, value);
        }
    }

    // Handle variants

    @Override
    public void currentTotalExposedVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.EXPOSED_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentExposedYoungVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.EXPOSED_YOUNG_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentExposedAdultVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.EXPOSED_ADULT_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentExposedElderlyVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.EXPOSED_ELDERLY_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalInfectiousVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.INFECTIOUS_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentMildInfectiousYoungVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.MILD_INFECTIOUS_YOUNG_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentMildInfectiousAdultVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.MILD_INFECTIOUS_ADULT_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentMildInfectiousElderlyVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.MILD_INFECTIOUS_ELDERLY_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalMildInfectiousVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.MILD_INFECTIOUS_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentSevereInfectiousYoungVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SEVERE_INFECTIOUS_YOUNG_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentSevereInfectiousAdultVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SEVERE_INFECTIOUS_ADULT_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentSevereInfectiousElderlyVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SEVERE_INFECTIOUS_ELDERLY_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalSevereInfectiousVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.SEVERE_INFECTIOUS_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentRecoveredYoungVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.RECOVERED_YOUNG_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentRecoveredAdultVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.RECOVERED_ADULT_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentRecoveredElderlyVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.RECOVERED_ELDERLY_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalRecoveredVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.RECOVERED_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentDeadYoungVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.DEAD_YOUNG_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentDeadAdultVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.DEAD_ADULT_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentDeadElderlyVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.DEAD_ELDERLY_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalDeadVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.DEAD_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentHospitalisedYoungVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.HOSPITALISED_YOUNG_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentHospitalisedAdultVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.HOSPITALISED_ADULT_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentHospitalisedElderlyVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.HOSPITALISED_ELDERLY_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

    @Override
    public void currentTotalHospitalisedVariant(LocalDate date, int value, int dz) {
        if(dz!=0) { 
            DailyResult ar = new DailyResult(date, DZAllStatesValueType.HOSPITALISED_VARIANT, dz);
            incrementDailyResult(ar, value);
        }
    }

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
