package scovmod.model.lockdown;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.StateQuery;
import scovmod.model.state.population.LocalPopulation;

import java.util.List;

public class PopulationSizePerAreaLookup {
    private final HealthBoardLookup hbl;
    private final StateQuery sq;
    private final Int2IntMap populationPerCA = new Int2IntOpenHashMap();

    public PopulationSizePerAreaLookup(HealthBoardLookup hbl, StateQuery sq) {
        this.hbl = hbl;
        this.sq = sq;
    }
    public void initialisePopSizePerAreas(){
        Int2ObjectMap<List<Integer>> oasByLA = hbl.getOasByLA();
        for(int ca: hbl.getAllCAs()){
            int popSizeOfCA = 0;
            for(int oa: oasByLA.get(ca)) {
                popSizeOfCA += sq.getCopyOfLocalPopulation(oa).getSize();
            }
            populationPerCA.put(ca,popSizeOfCA);
        }
//        for(int ca: hbl.getAllDZs()){
//
//        }
//        for(int hb: hbl.getAllHBs()){
//
//        }
    }
    public Int2IntMap getPopByCouncilAreaLookup(){
        return populationPerCA;
    }
}
