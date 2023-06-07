package scovmod.model.wastewater;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.wastewater.DZToWWSiteMappingReader;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.output.StatisticsCollector;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;

import java.time.LocalDate;
import java.time.Month;

public class WasteWaterSignalCalculator {
    private final StateQuery sq;
    private final DZToWWSiteMappingReader DtoWMap;
    private final HealthBoardLookup hbl;

    private final ConfigParameters config;

    private final StatisticsCollector stats;
    private final TimeConversions tcv;

    private final TimeManager tm;

    private Object2DoubleMap<String> totalSignalperSiteMap;


    public WasteWaterSignalCalculator(StateQuery sq, DZToWWSiteMappingReader DtoWMap, HealthBoardLookup hbl, ConfigParameters config, StatisticsCollector stats, TimeConversions tcv, TimeManager tm) {

        this.sq = sq;
        this.DtoWMap = DtoWMap;
        this.hbl = hbl;
        this.config = config;
        this.stats = stats;
        this.tcv = tcv;
        this.tm = tm;
        this.totalSignalperSiteMap = new Object2DoubleOpenHashMap();//Make sure this is reset after every time step
    }

    public void aggregateTotalRNASignalPerSite() {


        Int2ObjectMap<Object2DoubleMap> DWMap = DtoWMap.getSitesToDZdMap();

        for (int DZ : DWMap.keySet()) {
            int totalMildInfectious = 0;
            int totalSevereInfectious = 0;
            int totalHospitalised = 0;
            for(int oa : hbl.getOasByDZ().get(DZ)){
                LocalPopulation localPop = sq.getCopyOfLocalPopulation(oa);
                totalMildInfectious += localPop.getAllInState(InfectionState.MILD_INFECTIOUS_YOUNG).size();
                totalMildInfectious += localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ADULT).size();
                totalMildInfectious += localPop.getAllInState(InfectionState.MILD_INFECTIOUS_ELDERLY).size();

                totalSevereInfectious += localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_YOUNG).size();
                totalSevereInfectious += localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ADULT).size();
                totalSevereInfectious += localPop.getAllInState(InfectionState.SEVERE_INFECTIOUS_ELDERLY).size();

                totalHospitalised += localPop.getAllInState(InfectionState.HOSPITALISED_YOUNG).size();
                totalHospitalised += localPop.getAllInState(InfectionState.HOSPITALISED_ADULT).size();
                totalHospitalised += localPop.getAllInState(InfectionState.HOSPITALISED_ELDERLY).size();
            }

            Object2DoubleMap<String> WWAreaPropMap = DWMap.get(DZ);

            double totalRNASignalDZ = totalMildInfectious*config.getMildInfectiousMultiplier() + totalSevereInfectious*config.getSevereInfectiousMultiplier() + totalHospitalised*config.getHospitalisedMultiplier();
            System.out.println("DZ="+DZ+","+"RNASigna="+totalRNASignalDZ);
            for(String WWArea : WWAreaPropMap.keySet()){
                double prop = WWAreaPropMap.get(WWArea);
                //multiply prop by dz population
                double signal = prop*totalRNASignalDZ;
                if (totalSignalperSiteMap.containsKey(WWArea)) {
                    double currentTotal = totalSignalperSiteMap.get(WWArea);
                    totalSignalperSiteMap.put(WWArea,signal+currentTotal);
                } else {
                    totalSignalperSiteMap.put(WWArea,signal);
                }
            }
        }

        //Send totalSignalperSiteMap to output
        for(String WWArea : totalSignalperSiteMap.keySet()){
            stats.currentTotalRNASignal(tcv.toTimeStepStartDate(tm.getTimeStep()),totalSignalperSiteMap.get(WWArea),WWArea);
        }

    }

    public void resetTotalSignalperSiteMap(){
        totalSignalperSiteMap.clear();
    }

    public Object2DoubleMap<String> getTotalSignalperSiteMap() {
        return totalSignalperSiteMap;
    }
}
