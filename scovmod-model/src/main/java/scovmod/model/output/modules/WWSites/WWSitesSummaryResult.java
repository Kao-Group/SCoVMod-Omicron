/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.WWSites;


import scovmod.model.output.IResult;

import java.util.Arrays;
import java.util.Map;

public class WWSitesSummaryResult implements IResult{

    private final Map<DailyResult,Double> simulatedWWSitesResult;

    public WWSitesSummaryResult(Map<DailyResult,Double> simulatedWWSitesResult) {
        this.simulatedWWSitesResult = simulatedWWSitesResult;
    }

    public Map<DailyResult,Double>getSimulatedWWSitesResult() {
        return this.simulatedWWSitesResult;
    }

    @Override
    public String toString() {
        return "WWSitesSummaryResult{" +
                "simulatedWWSitesResult=" + Arrays.toString(simulatedWWSitesResult.entrySet().toArray()) +
                '}';
    }
}
