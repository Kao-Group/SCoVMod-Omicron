/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.untested;

import scovmod.model.Model;
import scovmod.model.input.*;
import scovmod.model.input.wastewater.DZToWWSiteMappingReader;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.CovidVariantParameters;
import scovmod.model.input.config.InputData;
import scovmod.model.input.config.Parameters;
import scovmod.model.lockdown.*;
import scovmod.model.movements.MovementEventManager;
import scovmod.model.movements.Mover;
import scovmod.model.movements.Resolver;
import scovmod.model.movements.MovementStepper;
import scovmod.model.movements.MovementHandler;
import scovmod.model.output.*;
import scovmod.model.seeding.*;
import scovmod.model.seeding.recovered.*;
import scovmod.model.seeding.reseeding.ReSeedManager;
import scovmod.model.seeding.variant.VariantSeedManager;
import scovmod.model.setup.InitialLocations;
import scovmod.model.setup.InitialSeeder;
import scovmod.model.setup.Initialiser;
import scovmod.model.state.InfectiousProportion;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.time.TaskTimeManager;
import scovmod.model.time.TimeConversions;
import scovmod.model.time.TimeManager;
import scovmod.model.transition.*;
import scovmod.model.transition.beta.BetaRatesMgr;
import scovmod.model.transition.infected.*;
import scovmod.model.transition.infected.variant.*;
import scovmod.model.transition.susceptible.SusceptiblePersonTransitionExecutor;
import scovmod.model.util.math.AliasMethodFactory;
import scovmod.model.util.math.Random;
import scovmod.model.vaccination.EfficacyAfterImmunityLossCalculator;
import scovmod.model.vaccination.VaccinationEfficacyCalculator;
import scovmod.model.vaccination.VaccinationManager;
import scovmod.model.wastewater.WasteWaterSignalCalculator;

public class Factory {

    public static Model buildModel(
            InputData data, 
            ConfigParameters config, 
            Parameters params,
            CovidVariantParameters covidParam,
            IOutputModule out) {
        Random rnd = Random.buildRandom();

        String outputModuleName = out.getClass().getSimpleName();
        System.out.println("Output Module chosen is: "+outputModuleName);
        OutputModuleType outputModule;
        switch (outputModuleName) {
            case "CommunitySummary":
                outputModule = OutputModuleType.OA;
                break;
            case "CouncilAreaAllStatesSummary":
            case "CouncilAreaSummary":
                outputModule = OutputModuleType.CA;
                break;
            case "DZAllStatesSummary":
                outputModule = OutputModuleType.DZ;
                break;

            case "StatesForWWSummary":
                outputModule = OutputModuleType.WW;
                break;
            default:
                throw new UnsupportedOperationException("Output statistics module not known");
        }

        String modelId = randomName(rnd);
        //Path jsonConfigPath = Paths.get("scovmod.json");
        String jsonConfig = "";

        HealthBoardLookup hbl = new HealthBoardLookup(data.getAreaToHBLookupFile());
        StartLocationsAndAgeClasses asl = new StartLocationsAndAgeClasses(data.getPeopleStartLocationsFile());
        StateManagementFactory smf = new StateManagementFactory(rnd,hbl,asl);
        StateModifier sm = smf.getStateModifier();
        StateQuery sq = smf.getStateQuery();

        int timeStepInDays = MovementTimeStepReader.getStochasticIncrement(data.getMovementDir());
        TimeConversions tcv = new TimeConversions(timeStepInDays);

        double tauLeapTimeStep = config.getTauLeapStep();

        //config.initTime(tcv);

        long startTimeStep = config.getFirstTimeStep();
        TimeManager timeMgr = new TimeManager(startTimeStep);

        MovementStepper movementStepper = MovementStepper.build(config, data);
        CaseMultiplierPerCouncilArea cmpca = new CaseMultiplierPerCouncilArea(data.getCasesMultiplierFileName());
        StatisticsCollector stats = new StatisticsCollector(out, timeMgr,  tcv,  sq, hbl, config, cmpca);
        TaskTimeManager ttm = new TaskTimeManager( config, timeMgr);

        MovementHandler mh = new MovementHandler(sm, stats, sq);
        Mover mover = new Mover(mh,stats);
        LockdownStatusManager lsm = LockdownStatusManager.build(timeMgr,stats);
        RegionalInfectionsTracker rit = RegionalInfectionsTracker.build(sq,hbl,config);
        PopulationSizePerAreaLookup pspa = new PopulationSizePerAreaLookup(hbl, sq);
        LockdownTriggerManager ltm = LockdownTriggerManager.build(sq,config,hbl,rit,lsm,timeMgr,pspa);
        Resolver resolver = new Resolver(sq,ltm);
        MovementEventManager moverMgr = new MovementEventManager(resolver,mover);

        SpatialSeedingGroupAttributes ssga = new SpatialSeedingGroupAttributes(data.getSpatialSeedingGroupFileName());
        RecoveredSeedingGroupAttributes rsga = new RecoveredSeedingGroupAttributes(data.getRecoveredSeedingGroupFileName());
        SpecifiedLockdownDetailsReader cawsl = new SpecifiedLockdownDetailsReader(data.getSpecifiedLockdownsFileName(),timeMgr);

        SpatialGroupMultinomial sgs = new SpatialGroupMultinomial(params,rnd,ssga);
        LocationShuffler ls = new LocationShuffler(hbl, rnd);
        WithinLocationSampler as = new WithinLocationSampler(ssga, rnd, params);
        WithinGroupSampler asbg = new WithinGroupSampler(ls,as);
        NationalSamplerFactory asf = new NationalSamplerFactory(sgs,asbg);
        RecoveredSeeds str = new RecoveredSeeds(data.getAreaToReseedFile());
        ReSeedManager rm = new ReSeedManager(rnd,str,timeMgr,config,sq,sm,asl);
        SeedManager smr = new SeedManager(asl, asf, rnd, str, config, asbg);

       // InitialiseToRecovered itr = new InitialiseToRecovered(data.getAreaWithRecoveredFile());
        RecoveredSpatialGroupMultinomial rsgs = new RecoveredSpatialGroupMultinomial(params,rnd,rsga);
        WithinLocationRecoveredSampler wlrs = new WithinLocationRecoveredSampler(rnd);
        WithinGroupRecoveredSampler wgrs = new WithinGroupRecoveredSampler(wlrs);
        NationalRecoveredSamplerFactory nrsf = new NationalRecoveredSamplerFactory(rsgs,wgrs);
        InitialiseToRecoveredManager itrm = new InitialiseToRecoveredManager(nrsf,sm,asl);

        DZToWWSiteMappingReader dzww = new DZToWWSiteMappingReader(data.getDzToWWSiteMappingFileName());

        //InfectedSeeds ifs = new InfectedSeeds(data.getInfectedSeedsFile());
        InfectiousProportion ip = new InfectiousProportion(sq,timeMgr);
        JsonVaccinationsReader pv = new JsonVaccinationsReader(data.getPeopleToVaccinatePerTimestepFileName());
        AliasMethodFactory amf = new AliasMethodFactory();
        VaccinationManager vm = new VaccinationManager(rnd,pv,timeMgr,config,sq,sm,asl,params,amf);
        VaccinationEfficacyCalculator vec = new VaccinationEfficacyCalculator(config,timeMgr,asl,vm);
        EfficacyAfterImmunityLossCalculator eailc = new EfficacyAfterImmunityLossCalculator(config,timeMgr,asl,vm);
        SusceptiblePersonTransitionExecutor sate = new SusceptiblePersonTransitionExecutor(sm,rnd,tauLeapTimeStep,stats,asl,vec,eailc,vm);
       // SusceptiblePersonTransitionExecutor sate = new SusceptiblePersonTransitionExecutor(sm,rnd,tauLeapTimeStep,stats,asl,vec,vm);
        TransmissionModIndexPerCouncilArea tmpca = new TransmissionModIndexPerCouncilArea(data.getTransModIndexFile());
        LockdownRatesManager lrm = new LockdownRatesManager(timeMgr,config,ltm,params,cawsl,hbl,tmpca);
        BetaRatesMgr br = BetaRatesMgr.build(lrm, params,covidParam);
        //DeathRates dr = new DeathRates(params,diph,sq,hbl);
        TracingRates tr = new TracingRates(config,ltm,timeMgr);
        ExposedToMildInfectiousTransitionExecutor eate= new ExposedToMildInfectiousTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl, stats, sq, rit);
        ExposedToRecoveredTransitionExecutor eare = new ExposedToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        ExposedToTracedTransitionExecutor ete = new ExposedToTracedTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl,tr,sq);
        MildInfectiousToSevereTransitionExecutor mitste = new MildInfectiousToSevereTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        MildInfectiousToRecoveredTransitionExecutor mitre = new MildInfectiousToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        MildInfectiousToTracedTransitionExecutor mitte = new MildInfectiousToTracedTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl, tr, sq);
        SevereInfectiousToRecoveredTransitionExecutor sitrte = new SevereInfectiousToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        SevereInfectiousToHospitalisedTransitionExecutor sithte = new SevereInfectiousToHospitalisedTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        SevereInfectiousToDeadTransitionExecutor sitdte = new SevereInfectiousToDeadTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl/*, dr*/);
        SevereInfectiousToTracedTransitionExecutor sitte = new SevereInfectiousToTracedTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl, tr, sq);
        HospitalisedToRecoveredTransitionExecutor htrte = new HospitalisedToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        HospitalisedToDeadTransitionExecutor htdte = new HospitalisedToDeadTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl/*, dr*/);
        TracedToRecoveredTransitionExecutor ttrte = new TracedToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        TracedToHospitalisedTransitionExecutor tthte = new TracedToHospitalisedTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl);
        TracedToDeadTransitionExecutor ttdte = new TracedToDeadTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl/*, dr*/);
        RecoveredToSusceptibleTransitionExecutor rtste = new RecoveredToSusceptibleTransitionExecutor(sm,rnd,tauLeapTimeStep,params,asl,vm/*, dr*/);

        ExposedToMildInfectiousVariantTransitionExecutor veate = new ExposedToMildInfectiousVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl, stats, sq, rit);
        ExposedToRecoveredVariantTransitionExecutor veare = new ExposedToRecoveredVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl);
        ExposedToTracedVariantTransitionExecutor vete = new ExposedToTracedVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl,tr,sq);
        MildInfectiousToSevereVariantTransitionExecutor vmitste = new MildInfectiousToSevereVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl);
        MildInfectiousToRecoveredVariantTransitionExecutor vmitre = new MildInfectiousToRecoveredVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl);
        MildInfectiousToTracedVariantTransitionExecutor vmitte = new MildInfectiousToTracedVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl, tr, sq);
        SevereInfectiousFromVariantToRecoveredTransitionExecutor vsitrte = new SevereInfectiousFromVariantToRecoveredTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl);
        SevereInfectiousFromVariantToHospitalisedTransitionExecutor vsithte = new SevereInfectiousFromVariantToHospitalisedTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl);
        SevereInfectiousFromVariantToDeadTransitionExecutor vsitdte = new SevereInfectiousFromVariantToDeadTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl/*, dr*/);
        SevereInfectiousFromVariantToTracedTransitionExecutor vsittte = new SevereInfectiousFromVariantToTracedTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl, tr, sq);
        HospitalisedToRecoveredVariantTransitionExecutor vhtrte = new HospitalisedToRecoveredVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl);
        HospitalisedToDeadVariantTransitionExecutor vhtdte = new HospitalisedToDeadVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl/*, dr*/);
        TracedToRecoveredFromVariantTransitionExecutor vttrte = new TracedToRecoveredFromVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl);
        TracedToHospitalisedFromVariantTransitionExecutor vtthte = new TracedToHospitalisedFromVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl);
        TracedToDeadFromVariantTransitionExecutor vttdte = new TracedToDeadFromVariantTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl/*, dr*/);
        RecoveredFromVariantToSusceptibleTransitionExecutor vrtste = new RecoveredFromVariantToSusceptibleTransitionExecutor(sm,rnd,tauLeapTimeStep,covidParam,asl/*, dr*/);

        TransitionExecutor te = new TransitionExecutor(smf, rnd,params, sate,eate,eare,ete,mitste,
                mitre,mitte,sitrte,sithte,sitdte,sitte,htrte,htdte,ttrte,tthte,ttdte,rtste, veate, veare, vete, vmitste, vmitre, vmitte, vsitrte, vsithte, vsitdte, vsittte, vhtrte, vhtdte, vttrte, vtthte, vttdte, vrtste);
        TransitionManager tm = TransitionManager.build(sq, params, te, ip,br);
        InitialLocations il = new InitialLocations(asl);
        InitialSeeder is = new InitialSeeder(asl, smf);
        //SeedManager smr = new SeedManager(asl,ifs); //Old version
        VariantSeedsPerCA vspca = new VariantSeedsPerCA(data.getCAsToSeedWithVariantFile());
        VariantSeedManager vsm = new VariantSeedManager(rnd,vspca,timeMgr,config,sq,sm,asl);
        StandardStateReport ssr = new StandardStateReport(stats,sq,vm,hbl,asl);
        VaccinatedAndSusceptibleStateReport vasr = new VaccinatedAndSusceptibleStateReport(stats,sq,vm,asl);
        DZToWWSiteMappingReader dtwmr = new DZToWWSiteMappingReader(data.getDzToWWSiteMappingFileName());
        WasteWaterSignalCalculator wwsc = new WasteWaterSignalCalculator(sq,dtwmr,hbl,config,stats,tcv,timeMgr);
        ModelReportManager mrm = new ModelReportManager(ssr,vasr,stats,sq,ltm,rit, wwsc);

        Initialiser initialiser = new Initialiser(smf, il, rnd, timeMgr, tcv,  is, smr,itrm);

        return new Model(
                modelId,
                initialiser,
                config,
                movementStepper,
                timeMgr,
                sq,
                moverMgr,
                tm,
                stats,
                tcv,
                ttm,
                hbl,
                rm,
                vsm,
                ltm,
                rit,
                pspa,
                vm,
                asl,
                mrm,
                outputModule);
    }

    public static String randomName(Random random) {
        char[] word = new char[random.nextUniformInteger(3,6)];
        for(int j = 0; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextUniformInteger(0, 25));
        }
        return new String(word);
    }
}
