/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.councilareaAllStates;


import scovmod.model.output.IResult;

import java.util.Arrays;
import java.util.Map;

public class CouncilAreaAllStatesSummaryResult implements IResult{

    private final Map<DailyResult,Integer> simulatedDailyCouncilAreaAllStatesResult;

    public CouncilAreaAllStatesSummaryResult(Map<DailyResult,Integer> simulatedDailyCouncilAreaAllStatesResult) {
        this.simulatedDailyCouncilAreaAllStatesResult = simulatedDailyCouncilAreaAllStatesResult;
    }

    public Map<DailyResult,Integer>getSimulatedDailyResultPerCouncilArea() {
        return this.simulatedDailyCouncilAreaAllStatesResult;
    }

    @Override
    public String toString() {
        return "CouncilAreaAllStatesSummaryResult{" +
                "simulatedDailyCouncilAreaAllStatesResult=" + Arrays.toString(simulatedDailyCouncilAreaAllStatesResult.entrySet().toArray()) +
                '}';
    }
}
