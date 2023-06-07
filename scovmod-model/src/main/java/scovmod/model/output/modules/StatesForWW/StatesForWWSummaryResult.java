/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.StatesForWW;


import scovmod.model.output.IResult;

import java.util.Arrays;
import java.util.Map;

public class StatesForWWSummaryResult implements IResult{

    private final Map<DailyResult,Integer> simulatedDailyDZAllStatesResult;

    public StatesForWWSummaryResult(Map<DailyResult,Integer> simulatedDailyDZAllStatesResult) {
        this.simulatedDailyDZAllStatesResult = simulatedDailyDZAllStatesResult;
    }

    public Map<DailyResult,Integer>getSimulatedDailyResultPerDZ() {
        return this.simulatedDailyDZAllStatesResult;
    }

    @Override
    public String toString() {
        return "DZAllStatesSummaryResult{" +
                "simulatedDailyDZAllStatesResult=" + Arrays.toString(simulatedDailyDZAllStatesResult.entrySet().toArray()) +
                '}';
    }
}
