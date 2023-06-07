/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output;

import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.CaseMultiplierPerCouncilArea;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.state.StateQuery;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;

import java.time.LocalDate;

import static scovmod.model.output.OutputModuleType.OA;

public class StatisticsCollector {

    private final TimeManager tm;
    private final TimeConversions tcv;
    private StateQuery sq;
    private IOutputModule output;
    private HealthBoardLookup hbl;
    private ConfigParameters config;
    private CaseMultiplierPerCouncilArea cmpca;

    public StatisticsCollector(
            IOutputModule output,
            TimeManager tm,
            TimeConversions tcv,
            StateQuery sq,
            HealthBoardLookup hbl,
            ConfigParameters config,
            CaseMultiplierPerCouncilArea cmpca) {
        this.tm = tm;
        this.tcv = tcv;
        this.sq = sq;
        this.output = output;
        this.hbl = hbl;
        this.config = config;
        this.cmpca = cmpca;
    }


    public void currentExposed(int totalExposed, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalExposed(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
                break;
            case CA:
                output.currentTotalExposed(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalExposed(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }


    public void currentInfectious(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, location);
                break;
            case CA:
                output.currentTotalInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSusceptible(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentTotalSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSusceptibleYoung(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSusceptibleAdult(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSusceptibleElderly(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentVaccinatedSusceptible(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalVaccinatedSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentTotalVaccinatedSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalVaccinatedSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentVaccinatedSusceptibleYoung(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentVaccinatedSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentVaccinatedSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentVaccinatedSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentVaccinatedSusceptibleAdult(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentVaccinatedSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentVaccinatedSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentVaccinatedSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentVaccinatedSusceptibleElderly(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentVaccinatedSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentVaccinatedSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentVaccinatedSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalVaccinatedNonSusceptible(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalVaccinatedNonSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentTotalVaccinatedNonSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalVaccinatedNonSusceptible(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentVaccinatedNonSusceptibleYoung(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentVaccinatedNonSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentVaccinatedNonSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentVaccinatedNonSusceptibleYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentVaccinatedNonSusceptibleAdult(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentVaccinatedNonSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentVaccinatedNonSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentVaccinatedNonSusceptibleAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentVaccinatedNonSusceptibleElderly(int totalSusceptible, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentVaccinatedNonSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, location);
                break;
            case CA:
                output.currentVaccinatedNonSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentVaccinatedNonSusceptibleElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentTotalFullyProtected(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalFullyProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalFullyProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalFullyProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialProtected(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialImmLossVacProtected(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialImmLossVacProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialImmLossVacProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialImmLossVacProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialImmLossNatProtected(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialImmLossNatProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialImmLossNatProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialImmLossNatProtected(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalFullyProtectedYoung(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalFullyProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalFullyProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalFullyProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalFullyProtectedAdult(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalFullyProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalFullyProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalFullyProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalFullyProtectedElderly(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalFullyProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalFullyProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalFullyProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentTotalPartialProtectedYoung(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialProtectedAdult(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialProtectedElderly(int total, int location, OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialImmLossVacProtectedYoung(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialImmLossVacProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialImmLossVacProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialImmLossVacProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialImmLossVacProtectedAdult(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialImmLossVacProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialImmLossVacProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialImmLossVacProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialImmLossVacProtectedElderly(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialImmLossVacProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialImmLossVacProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialImmLossVacProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialImmLossNatProtectedYoung(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialImmLossNatProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialImmLossNatProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialImmLossNatProtectedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialImmLossNatProtectedAdult(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialImmLossNatProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialImmLossNatProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialImmLossNatProtectedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }
    public void currentTotalPartialImmLossNatProtectedElderly(int total, int location,OutputModuleType outputModuleType){
        switch (outputModuleType) {
            case OA:
                output.currentTotalPartialImmLossNatProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, location);
                break;
            case CA:
                output.currentTotalPartialImmLossNatProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalPartialImmLossNatProtectedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), total, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

//    public void currentSusceptibleCA(int totalSusceptible, int location) {
//        output.currentTotalSusceptibleCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentSusceptibleYoungCA(int totalSusceptible, int location) {
//        output.currentSusceptibleYoungCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentSusceptibleAdultCA(int totalSusceptible, int location) {
//        output.currentSusceptibleAdultCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentSusceptibleElderlyCA(int totalSusceptible, int location) {
//        output.currentSusceptibleElderlyCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentVaccinatedSusceptibleCA(int totalSusceptible, int location) {
//        output.currentTotalVaccinatedSusceptibleCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentVaccinatedSusceptibleYoungCA(int totalSusceptible, int location) {
//        output.currentVaccinatedSusceptibleYoungCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentVaccinatedSusceptibleAdultCA(int totalSusceptible, int location) {
//        output.currentVaccinatedSusceptibleAdultCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentVaccinatedSusceptibleElderlyCA(int totalSusceptible, int location) {
//        output.currentVaccinatedSusceptibleElderlyCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalVaccinatedNonSusceptibleCA(int totalSusceptible, int location) {
//        output.currentTotalVaccinatedNonSusceptibleCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentVaccinatedNonSusceptibleYoungCA(int totalSusceptible, int location) {
//        output.currentVaccinatedNonSusceptibleYoungCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentVaccinatedNonSusceptibleAdultCA(int totalSusceptible, int location) {
//        output.currentVaccinatedNonSusceptibleAdultCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentVaccinatedNonSusceptibleElderlyCA(int totalSusceptible, int location) {
//        output.currentVaccinatedNonSusceptibleElderlyCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getCouncilAreaFromOA(location));
//    }
//
//
//    public void currentTotalFullyProtectedCA(int total, int location){
//        output.currentTotalFullyProtectedCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialProtectedCA(int total, int location){
//        output.currentTotalPartialProtectedCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossVacProtectedCA(int total, int location){
//        output.currentTotalPartialImmLossVacProtectedCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossNatProtectedCA(int total, int location){
//        output.currentTotalPartialImmLossNatProtectedCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalFullyProtectedYoungCA(int total, int location){
//        output.currentTotalFullyProtectedYoungCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalFullyProtectedAdultCA(int total, int location){
//        output.currentTotalFullyProtectedAdultCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalFullyProtectedElderlyCA(int total, int location){
//        output.currentTotalFullyProtectedElderlyCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialProtectedYoungCA(int total, int location){
//        output.currentTotalPartialProtectedYoungCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialProtectedAdultCA(int total, int location){
//        output.currentTotalPartialProtectedAdultCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialProtectedElderlyCA(int total, int location){
//        output.currentTotalPartialProtectedElderlyCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossVacProtectedYoungCA(int total, int location){
//        output.currentTotalPartialImmLossVacProtectedYoungCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossVacProtectedAdultCA(int total, int location){
//        output.currentTotalPartialImmLossVacProtectedAdultCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossVacProtectedElderlyCA(int total, int location){
//        output.currentTotalPartialImmLossVacProtectedElderlyCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossNatProtectedYoungCA(int total, int location){
//        output.currentTotalPartialImmLossNatProtectedYoungCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossNatProtectedAdultCA(int total, int location){
//        output.currentTotalPartialImmLossNatProtectedAdultCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossNatProtectedElderlyCA(int total, int location){
//        output.currentTotalPartialImmLossNatProtectedElderlyCA(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getCouncilAreaFromOA(location));
//    }
//
//    public void currentSusceptibleDZ(int totalSusceptible, int location) {
//        output.currentTotalSusceptibleDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentSusceptibleYoungDZ(int totalSusceptible, int location) {
//        output.currentSusceptibleYoungDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentSusceptibleAdultDZ(int totalSusceptible, int location) {
//        output.currentSusceptibleAdultDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentSusceptibleElderlyDZ(int totalSusceptible, int location) {
//        output.currentSusceptibleElderlyDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentVaccinatedSusceptibleDZ(int totalSusceptible, int location) {
//        output.currentTotalVaccinatedSusceptibleDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentVaccinatedSusceptibleYoungDZ(int totalSusceptible, int location) {
//        output.currentVaccinatedSusceptibleYoungDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentVaccinatedSusceptibleAdultDZ(int totalSusceptible, int location) {
//        output.currentVaccinatedSusceptibleAdultDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentVaccinatedSusceptibleElderlyDZ(int totalSusceptible, int location) {
//        output.currentVaccinatedSusceptibleElderlyDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalVaccinatedNonSusceptibleDZ(int totalSusceptible, int location) {
//        output.currentTotalVaccinatedNonSusceptibleDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentVaccinatedNonSusceptibleYoungDZ(int totalSusceptible, int location) {
//        output.currentVaccinatedNonSusceptibleYoungDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentVaccinatedNonSusceptibleAdultDZ(int totalSusceptible, int location) {
//        output.currentVaccinatedNonSusceptibleAdultDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//    public void currentVaccinatedNonSusceptibleElderlyDZ(int totalSusceptible, int location) {
//        output.currentVaccinatedNonSusceptibleElderlyDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), totalSusceptible,hbl.getDZAreaFromOA(location));
//    }
//
//
//    public void currentTotalFullyProtectedDZ(int total, int location){
//        output.currentTotalFullyProtectedDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialProtectedDZ(int total, int location){
//        output.currentTotalPartialProtectedDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossVacProtectedDZ(int total, int location){
//        output.currentTotalPartialImmLossVacProtectedDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossNatProtectedDZ(int total, int location){
//        output.currentTotalPartialImmLossNatProtectedDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalFullyProtectedYoungDZ(int total, int location){
//        output.currentTotalFullyProtectedYoungDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalFullyProtectedAdultDZ(int total, int location){
//        output.currentTotalFullyProtectedAdultDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalFullyProtectedElderlyDZ(int total, int location){
//        output.currentTotalFullyProtectedElderlyDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialProtectedYoungDZ(int total, int location){
//        output.currentTotalPartialProtectedYoungDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialProtectedAdultDZ(int total, int location){
//        output.currentTotalPartialProtectedAdultDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialProtectedElderlyDZ(int total, int location){
//        output.currentTotalPartialProtectedElderlyDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossVacProtectedYoungDZ(int total, int location){
//        output.currentTotalPartialImmLossVacProtectedYoungDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossVacProtectedAdultDZ(int total, int location){
//        output.currentTotalPartialImmLossVacProtectedAdultDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossVacProtectedElderlyDZ(int total, int location){
//        output.currentTotalPartialImmLossVacProtectedElderlyDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossNatProtectedYoungDZ(int total, int location){
//        output.currentTotalPartialImmLossNatProtectedYoungDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossNatProtectedAdultDZ(int total, int location){
//        output.currentTotalPartialImmLossNatProtectedAdultDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }
//    public void currentTotalPartialImmLossNatProtectedElderlyDZ(int total, int location){
//        output.currentTotalPartialImmLossNatProtectedElderlyDZ(tcv.toTimeStepStartDate(tm.getTimeStep()), total,hbl.getDZAreaFromOA(location));
//    }

    public void currentMildInfectious(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalMildInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentTotalMildInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalMildInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSevereInfectious(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalSevereInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentTotalSevereInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalSevereInfectious(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }

    }

    public void currentRecovered(int totalRecovered, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalRecovered(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
                break;
            case CA:
                output.currentTotalRecovered(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalRecovered(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDead(int totalDead, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalDead(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
                break;
            case CA:
                output.currentTotalDead(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalDead(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDeadCA(int totalDead, int location) {
        output.currentTotalDeadCA(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
    }

    public void currentHospitalised(int totalHospitalised, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalHospitalised(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
                break;
            case CA:
                output.currentTotalHospitalised(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalHospitalised(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentExposedYoung(int totalExposed, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentExposedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
                break;
            case CA:
                output.currentExposedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentExposedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentExposedAdult(int totalExposed, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentExposedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
                break;
            case CA:
                output.currentExposedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentExposedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentExposedElderly(int totalExposed, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentExposedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
                break;
            case CA:
                output.currentExposedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentExposedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentMildInfectiousYoung(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentMildInfectiousYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentMildInfectiousYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentMildInfectiousYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentMildInfectiousAdult(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentMildInfectiousAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentMildInfectiousAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentMildInfectiousAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentMildInfectiousElderly(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentMildInfectiousElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentMildInfectiousElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentMildInfectiousElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSevereInfectiousYoung(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSevereInfectiousYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentSevereInfectiousYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSevereInfectiousYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSevereInfectiousAdult(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSevereInfectiousAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentSevereInfectiousAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSevereInfectiousAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSevereInfectiousElderly(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSevereInfectiousElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentSevereInfectiousElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSevereInfectiousElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentRecoveredYoung(int totalRecovered, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentRecoveredYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
                break;
            case CA:
                output.currentRecoveredYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentRecoveredYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentRecoveredAdult(int totalRecovered, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentRecoveredAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
                break;
            case CA:
                output.currentRecoveredAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentRecoveredAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentRecoveredElderly(int totalRecovered, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentRecoveredElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
                break;
            case CA:
                output.currentRecoveredElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentRecoveredElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDeadYoung(int totalDead, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentDeadYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
                break;
            case CA:
                output.currentDeadYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentDeadYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDeadAdult(int totalDead, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentDeadAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
                break;
            case CA:
                output.currentDeadAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentDeadAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDeadElderly(int totalDead, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentDeadElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
                break;
            case CA:
                output.currentDeadElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentDeadElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }


    public void currentHospitalisedYoung(int totalHospitalised, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentHospitalisedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
                break;
            case CA:
                output.currentHospitalisedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentHospitalisedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentHospitalisedAdult(int totalHospitalised, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentHospitalisedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
                break;
            case CA:
                output.currentHospitalisedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentHospitalisedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentHospitalisedElderly(int totalHospitalised, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentHospitalisedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
                break;
            case CA:
                output.currentHospitalisedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentHospitalisedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }




    public void currentTracedYoung(int noT_y, int location) {
        output.currentTracedYoung(tcv.toTimeStepStartDate(tm.getTimeStep()), noT_y,location);
    }

    public void currentTracedAdult(int noT_a, int location) {
        output.currentTracedAdult(tcv.toTimeStepStartDate(tm.getTimeStep()), noT_a,location);
    }

    public void currentTracedElderly(int noT_e, int location) {
        output.currentTracedElderly(tcv.toTimeStepStartDate(tm.getTimeStep()), noT_e,location);
    }

    public void currentTraced(int traced, int location) {
        output.currentTraced(tcv.toTimeStepStartDate(tm.getTimeStep()), traced,location);
    }

    public void currentNumberDZAreasWithDeadPerCouncilArea(int dz, int oa) {
        int councilArea = hbl.getCouncilAreaFromOA(oa);
        output.currentNumberDZAreasWithDeadPerCouncilArea(tcv.toTimeStepStartDate(tm.getTimeStep()), dz,councilArea);
    }

    public IResult buildResult() {
        return output.buildResult();
    }

    public void newLockdown(int locationID) {
            output.newLockdown(tcv.toTimeStepStartDate(tm.getTimeStep()),locationID);
    }
    public void lockdownEndDuration(long startTimeStep, long currentTimeStep, int locationID) {
        if (startTimeStep >= currentTimeStep)
            throw new RuntimeException("Lockdown start date must be before end date: start: " + startTimeStep + ", end: " + currentTimeStep);
        int durationInTimeSteps = (int) (currentTimeStep - startTimeStep);
        AreaLevels lockdownLevel = config.getLocalLockdownsAreaLevel();
        IntSet allLocationsInArea = sq.getAllLocationsInArea(lockdownLevel, locationID);
        for (int location : allLocationsInArea) {
            output.lockdownEndDuration(tcv.toTimeStepStartDate(tm.getTimeStep()), durationInTimeSteps, location);
        }
    }

    public void newMildInfectious(int oa, int personID) {
        output.newMildInfectiousCA(tcv.toTimeStepStartDate(tm.getTimeStep()),hbl.getCouncilAreaFromOA(oa),personID);
        output.newMildInfectiousDZ(tcv.toTimeStepStartDate(tm.getTimeStep()),hbl.getDZAreaFromOA(oa),personID);
        output.newMildInfectiousOA(tcv.toTimeStepStartDate(tm.getTimeStep()),oa,personID);

    }

    public void newMildInfectiousVariant(int oa, int personID) {
        output.newMildInfectiousVariantCA(tcv.toTimeStepStartDate(tm.getTimeStep()),hbl.getCouncilAreaFromOA(oa),personID);
        output.newMildInfectiousVariantDZ(tcv.toTimeStepStartDate(tm.getTimeStep()),hbl.getDZAreaFromOA(oa),personID);
        output.newMildInfectiousVariantOA(tcv.toTimeStepStartDate(tm.getTimeStep()),oa,personID);

    }

    public void aggregateMildInfectiousEvents(){
        output.aggregateMildInfectiousEvents(tcv.toTimeStepStartDate(tm.getTimeStep()),cmpca.getCaseMultiplierPerCA());
    }

    public void aggregateMildInfectiousEvents_variant(){
        output.aggregateMildInfectiousVariantEvents(tcv.toTimeStepStartDate(tm.getTimeStep()),cmpca.getCaseMultiplierPerCA());
    }

    //New variant stats
    public void currentExposedVariant(int totalExposed, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalExposedVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
                break;
            case CA:
                output.currentTotalExposedVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalExposedVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentInfectiousVariant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentTotalInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentMildInfectiousVariant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalMildInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentTotalMildInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalMildInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSevereInfectiousVariant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalSevereInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentTotalSevereInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalSevereInfectiousVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentRecoveredVariant(int totalRecovered, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalRecoveredVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
                break;
            case CA:
                output.currentTotalRecoveredVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalRecoveredVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDeadVariant(int totalDead, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalRecoveredVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
                break;
            case CA:
                output.currentTotalRecoveredVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalRecoveredVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentHospitalisedVariant(int totalHospitalised, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentTotalHospitalisedVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
                break;
            case CA:
                output.currentTotalHospitalisedVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentTotalHospitalisedVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentExposedYoungVariant(int totalExposed, int location, OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentExposedYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
                break;
            case CA:
                output.currentExposedYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentExposedYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentMildInfectiousYoungVariant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentMildInfectiousYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentMildInfectiousYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentMildInfectiousYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSevereInfectiousYoungVariant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSevereInfectiousYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentSevereInfectiousYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSevereInfectiousYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentRecoveredYoungVariant(int totalRecovered, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentRecoveredYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
                break;
            case CA:
                output.currentRecoveredYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentRecoveredYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDeadYoungVariant(int totalDead, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentDeadYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
                break;
            case CA:
                output.currentDeadYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentDeadYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentHospitalisedYoungVariant(int totalHospitalised, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentHospitalisedYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
                break;
            case CA:
                output.currentHospitalisedYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentHospitalisedYoungVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentExposedAdultVariant(int totalExposed, int location, OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentExposedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
                break;
            case CA:
                output.currentExposedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentExposedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentMildInfectiousAdultVariant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentExposedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentExposedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentExposedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSevereInfectiousAdultVariant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSevereInfectiousAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentSevereInfectiousAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSevereInfectiousAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentRecoveredAdultVariant(int totalRecovered, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentRecoveredAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
                break;
            case CA:
                output.currentRecoveredAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentRecoveredAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDeadAdultVariant(int totalDead, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentDeadAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
                break;
            case CA:
                output.currentDeadAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentDeadAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentHospitalisedAdultVariant(int totalHospitalised, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentHospitalisedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
                break;
            case CA:
                output.currentHospitalisedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentHospitalisedAdultVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentExposedElderlyVariant(int totalExposed, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentExposedElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed,location);
                break;
            case CA:
                output.currentExposedElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentExposedElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalExposed, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentMildInfectiousElderlyVaraiant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentMildInfectiousElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentMildInfectiousElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentMildInfectiousElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentSevereInfectiousElderlyVariant(int totalInfectious, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentSevereInfectiousElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious,location);
                break;
            case CA:
                output.currentSevereInfectiousElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentSevereInfectiousElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalInfectious, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentRecoveredElderlyVariant(int totalRecovered, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentRecoveredElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered,location);
                break;
            case CA:
                output.currentRecoveredElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentRecoveredElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalRecovered, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentDeadElderlyVariant(int totalDead, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentDeadElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead,location);
                break;
            case CA:
                output.currentDeadElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentDeadElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalDead, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentHospitalisedElderlyVariant(int totalHospitalised, int location,OutputModuleType outputModuleType) {
        switch (outputModuleType) {
            case OA:
                output.currentHospitalisedElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised,location);
                break;
            case CA:
                output.currentHospitalisedElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getCouncilAreaFromOA(location));
                break;
            case DZ:
                output.currentHospitalisedElderlyVariant(tcv.toTimeStepStartDate(tm.getTimeStep()), totalHospitalised, hbl.getDZAreaFromOA(location));
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }
    }

    public void currentTotalRNASignal(LocalDate date, double value, String site){
        output.currentTotalRNASignal(date,value, site);
    }

}
