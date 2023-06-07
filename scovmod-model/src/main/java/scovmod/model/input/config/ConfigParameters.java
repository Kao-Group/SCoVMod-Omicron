package scovmod.model.input.config;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
//import net.minidev.json.JSONArray;

import static scovmod.model.input.config.AreaLevels.*;

public class ConfigParameters {

    private long firstTimeStep = -1;
    private long lastTimeStep = -1;
    private long firstBetaFactorTimeStep = -1;
    private long secondBetaFactorTimeStep = -1;
    private double tauLeapStep = 0;
    private String outputFrequencyString;
    private OutputFrequency outputFrequency;
    private int chunkSize;
    private String reseedingAreaLevel;
    private AreaLevels seedingLevel;
    private boolean localLockdownsActivated;
    private String localLockdownsAreaLevelString;
    private AreaLevels localLockdownsAreaLevel;

    private double mildInfectiousUpperLevel;
    private double mildInfectiousLowerLevel;
    //private double betaReductionFactorDuringLocalLockdown;
    private int localLockdownStartTimeStep;
    private double localLockdownContactTracingRateMultiplier;

    private int vaccineStartTimestep;

    private double standardStrainFirstEfficacyChange_young_Step1;
    private double standardStrainFirstEfficacyChange_adult_Step1;
    private double standardStrainFirstEfficacyChange_elderly_Step1;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step1;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1;

    private double standardStrainFirstEfficacyChange_young_Step2;
    private double standardStrainFirstEfficacyChange_adult_Step2;
    private double standardStrainFirstEfficacyChange_elderly_Step2;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step2;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2;

    private double standardStrainFirstEfficacyChange_young_Step3;
    private double standardStrainFirstEfficacyChange_adult_Step3;
    private double standardStrainFirstEfficacyChange_elderly_Step3;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step3;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3;

    private double standardStrainSecondEfficacyChange_young_Step1;
    private double standardStrainSecondEfficacyChange_adult_Step1;
    private double standardStrainSecondEfficacyChange_elderly_Step1;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step1;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1;

    private double standardStrainSecondEfficacyChange_young_Step2;
    private double standardStrainSecondEfficacyChange_adult_Step2;
    private double standardStrainSecondEfficacyChange_elderly_Step2;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step2;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2;

    private double standardStrainSecondEfficacyChange_young_Step3;
    private double standardStrainSecondEfficacyChange_adult_Step3;
    private double standardStrainSecondEfficacyChange_elderly_Step3;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step3;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3;

    private double standardStrainThirdEfficacyChange_young_Step1;
    private double standardStrainThirdEfficacyChange_adult_Step1;
    private double standardStrainThirdEfficacyChange_elderly_Step1;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step1;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1;

    private double standardStrainThirdEfficacyChange_young_Step2;
    private double standardStrainThirdEfficacyChange_adult_Step2;
    private double standardStrainThirdEfficacyChange_elderly_Step2;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step2;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2;

    private double standardStrainThirdEfficacyChange_young_Step3;
    private double standardStrainThirdEfficacyChange_adult_Step3;
    private double standardStrainThirdEfficacyChange_elderly_Step3;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step3;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3;


    private double variantStrainFirstEfficacyChange_young_Step1;
    private double variantStrainFirstEfficacyChange_adult_Step1;
    private double variantStrainFirstEfficacyChange_elderly_Step1;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step1;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1;
    private double variantStrainFirstEfficacyChange_young_Step2;
    private double variantStrainFirstEfficacyChange_adult_Step2;
    private double variantStrainFirstEfficacyChange_elderly_Step2;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step2;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2;
    private double variantStrainFirstEfficacyChange_young_Step3;
    private double variantStrainFirstEfficacyChange_adult_Step3;
    private double variantStrainFirstEfficacyChange_elderly_Step3;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step3;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3;

    private double variantStrainSecondEfficacyChange_young_Step1;
    private double variantStrainSecondEfficacyChange_adult_Step1;
    private double variantStrainSecondEfficacyChange_elderly_Step1;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step1;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1;
    private double variantStrainSecondEfficacyChange_young_Step2;
    private double variantStrainSecondEfficacyChange_adult_Step2;
    private double variantStrainSecondEfficacyChange_elderly_Step2;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step2;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2;
    private double variantStrainSecondEfficacyChange_young_Step3;
    private double variantStrainSecondEfficacyChange_adult_Step3;
    private double variantStrainSecondEfficacyChange_elderly_Step3;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step3;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3;

    private double variantStrainThirdEfficacyChange_young_Step1;
    private double variantStrainThirdEfficacyChange_adult_Step1;
    private double variantStrainThirdEfficacyChange_elderly_Step1;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step1;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1;
    private double variantStrainThirdEfficacyChange_young_Step2;
    private double variantStrainThirdEfficacyChange_adult_Step2;
    private double variantStrainThirdEfficacyChange_elderly_Step2;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step2;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2;
    private double variantStrainThirdEfficacyChange_young_Step3;
    private double variantStrainThirdEfficacyChange_adult_Step3;
    private double variantStrainThirdEfficacyChange_elderly_Step3;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step3;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3;

    //TODO Split these efficacy phases into their own class (like CovidVariantParameters)

    private double standardStrainFirstEfficacyChange_afterImmunityLoss_young;
    private double standardStrainFirstEfficacyChange_afterImmunityLoss_adult;
    private double standardStrainFirstEfficacyChange_afterImmunityLoss_elderly;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    private int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;

    private double standardStrainSecondEfficacyChange_afterImmunityLoss_young;
    private double standardStrainSecondEfficacyChange_afterImmunityLoss_adult;
    private double standardStrainSecondEfficacyChange_afterImmunityLoss_elderly;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    private int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;

    private double standardStrainThirdEfficacyChange_afterImmunityLoss_young;
    private double standardStrainThirdEfficacyChange_afterImmunityLoss_adult;
    private double standardStrainThirdEfficacyChange_afterImmunityLoss_elderly;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    private int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;


    private double variantStrainFirstEfficacyChange_afterImmunityLoss_young;
    private double variantStrainFirstEfficacyChange_afterImmunityLoss_adult;
    private double variantStrainFirstEfficacyChange_afterImmunityLoss_elderly;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    private int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;

    private double variantStrainSecondEfficacyChange_afterImmunityLoss_young;
    private double variantStrainSecondEfficacyChange_afterImmunityLoss_adult;
    private double variantStrainSecondEfficacyChange_afterImmunityLoss_elderly;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    private int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;

    private double variantStrainThirdEfficacyChange_afterImmunityLoss_young;
    private double variantStrainThirdEfficacyChange_afterImmunityLoss_adult;
    private double variantStrainThirdEfficacyChange_afterImmunityLoss_elderly;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    private int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;

    private double mildInfectiousMultiplier;
    private double severeInfectiousMultiplier;
    private double hospitalisedMultiplier;


    public static ConfigParameters fromJSON(String jsonContent) {

        ReadContext ctx = JsonPath.parse(jsonContent);

        int startTimeStep = ctx.read("$.configParameters.time.start-timestep");
        int lastTimeStepInclusive = ctx.read("$.configParameters.time.end-timestep-inclusive");
        int firstBetaFactorTimeStep = ctx.read("$.configParameters.time.first-beta-factor-timestep");
        int secondBetaFactorTimeStep = ctx.read("$.configParameters.time.second-beta-factor-timestep");
        double tauLeapStep = ctx.read("$.configParameters.time.tau-leap-step");
        int chunkSize = ctx.read("$.configParameters.time.chunkSize");
        String outputFrequencyString = ctx.read("$.configParameters.time.outputFrequency");
        String reseedingAreaLevel = ctx.read("$.configParameters.feature.reseeding.reseeding-area-level");

        boolean localLockdownsActivated = Boolean.parseBoolean(ctx.read("$.configParameters.feature.localLockdown.local-lockdowns-activated"));
        String localLockdownsAreaLevelString = ctx.read("$.configParameters.feature.localLockdown.local-lockdown-area-level");
        double mildInfectiousUpperLevel = ctx.read("$.configParameters.feature.localLockdown.mild-infectious-upper-threshold");
        double mildInfectiousLowerLevel = ctx.read("$.configParameters.feature.localLockdown.mild-infectious-lower-threshold");
        int localLockdownStartTimeStep = ctx.read("$.configParameters.feature.localLockdown.local-lockdowns-start-timestep");
        double localLockdownContactTracingRateMultiplier = ctx.read("$.configParameters.feature.localLockdown.contact-tracing-rate-multiplier");

        int vaccineStartTimestep = ctx.read("$.configParameters.feature.vaccination.time.startingTimeStep");

        String standardStrainFirstEfficacyChange_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.standard.standardStrainEfficacyFirstEfficacyChange_young");
        String standardStrainFirstEfficacyChange_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.standard.standardStrainEfficacyFirstEfficacyChange_adult");
        String standardStrainFirstEfficacyChange_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.standard.standardStrainEfficacyFirstEfficacyChange_elderly");
        String TimestepsInFirstEfficacyPhaseStandardStrain_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.standard.numberOfTimestepsInFirstEfficacyPhaseStandardStrain_young");
        String TimestepsInFirstEfficacyPhaseStandardStrain_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.standard.numberOfTimestepsInFirstEfficacyPhaseStandardStrain_adult");
        String TimestepsInFirstEfficacyPhaseStandardStrain_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.standard.numberOfTimestepsInFirstEfficacyPhaseStandardStrain_elderly");

        String[] toks = standardStrainFirstEfficacyChange_young.replaceAll("\\s+", "").split(",");
        double standardStrainFirstEfficacyChange_young_Step1 = Double.parseDouble(toks[0]);
        double standardStrainFirstEfficacyChange_young_Step2 = Double.parseDouble(toks[1]);
        double standardStrainFirstEfficacyChange_young_Step3 = Double.parseDouble(toks[2]);
        String[] toks1 = standardStrainFirstEfficacyChange_adult.replaceAll("\\s+", "").split(",");
        double standardStrainFirstEfficacyChange_adult_Step1 = Double.parseDouble(toks1[0]);
        double standardStrainFirstEfficacyChange_adult_Step2 = Double.parseDouble(toks1[1]);
        double standardStrainFirstEfficacyChange_adult_Step3 = Double.parseDouble(toks1[2]);
        String[] toks2 = standardStrainFirstEfficacyChange_elderly.replaceAll("\\s+", "").split(",");
        double standardStrainFirstEfficacyChange_elderly_Step1 = Double.parseDouble(toks2[0]);
        double standardStrainFirstEfficacyChange_elderly_Step2 = Double.parseDouble(toks2[1]);
        double standardStrainFirstEfficacyChange_elderly_Step3 = Double.parseDouble(toks2[2]);

        String[] toks3 = TimestepsInFirstEfficacyPhaseStandardStrain_young.replaceAll("\\s+", "").split(",");
        int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step1 = Integer.parseInt(toks3[0]);
        int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step2 = Integer.parseInt(toks3[1]);
        int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step3 = Integer.parseInt(toks3[2]);
        String[] toks4 = TimestepsInFirstEfficacyPhaseStandardStrain_adult.replaceAll("\\s+", "").split(",");
        int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1 = Integer.parseInt(toks4[0]);
        int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2 = Integer.parseInt(toks4[1]);
        int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3 = Integer.parseInt(toks4[2]);
        String[] toks5 = TimestepsInFirstEfficacyPhaseStandardStrain_elderly.replaceAll("\\s+", "").split(",");
        int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1 = Integer.parseInt(toks5[0]);
        int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2 = Integer.parseInt(toks5[1]);
        int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3 = Integer.parseInt(toks5[2]);

        String standardStrainSecondEfficacyChange_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.standard.standardStrainEfficacySecondEfficacyChange_young");
        String standardStrainSecondEfficacyChange_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.standard.standardStrainEfficacySecondEfficacyChange_adult");
        String standardStrainSecondEfficacyChange_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.standard.standardStrainEfficacySecondEfficacyChange_elderly");
        String TimestepsInSecondEfficacyPhaseStandardStrain_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.standard.numberOfTimestepsInSecondEfficacyPhaseStandardStrain_young");
        String TimestepsInSecondEfficacyPhaseStandardStrain_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.standard.numberOfTimestepsInSecondEfficacyPhaseStandardStrain_adult");
        String TimestepsInSecondEfficacyPhaseStandardStrain_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.standard.numberOfTimestepsInSecondEfficacyPhaseStandardStrain_elderly");

        String[] toks6 = standardStrainSecondEfficacyChange_young.replaceAll("\\s+", "").split(",");
        double standardStrainSecondEfficacyChange_young_Step1 = Double.parseDouble(toks6[0]);
        double standardStrainSecondEfficacyChange_young_Step2 = Double.parseDouble(toks6[1]);
        double standardStrainSecondEfficacyChange_young_Step3 = Double.parseDouble(toks6[2]);
        String[] toks7 = standardStrainSecondEfficacyChange_adult.replaceAll("\\s+", "").split(",");
        double standardStrainSecondEfficacyChange_adult_Step1 = Double.parseDouble(toks7[0]);
        double standardStrainSecondEfficacyChange_adult_Step2 = Double.parseDouble(toks7[1]);
        double standardStrainSecondEfficacyChange_adult_Step3 = Double.parseDouble(toks7[2]);
        String[] toks8 = standardStrainSecondEfficacyChange_elderly.replaceAll("\\s+", "").split(",");
        double standardStrainSecondEfficacyChange_elderly_Step1 = Double.parseDouble(toks8[0]);
        double standardStrainSecondEfficacyChange_elderly_Step2 = Double.parseDouble(toks8[1]);
        double standardStrainSecondEfficacyChange_elderly_Step3 = Double.parseDouble(toks8[2]);

        String[] toks9 = TimestepsInSecondEfficacyPhaseStandardStrain_young.replaceAll("\\s+", "").split(",");
        int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step1 = Integer.parseInt(toks9[0]);
        int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step2 = Integer.parseInt(toks9[1]);
        int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step3 = Integer.parseInt(toks9[2]);
        String[] toks10 = TimestepsInSecondEfficacyPhaseStandardStrain_adult.replaceAll("\\s+", "").split(",");
        int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1 = Integer.parseInt(toks10[0]);
        int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2 = Integer.parseInt(toks10[1]);
        int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3 = Integer.parseInt(toks10[2]);
        String[] toks11 = TimestepsInSecondEfficacyPhaseStandardStrain_elderly.replaceAll("\\s+", "").split(",");
        int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1 = Integer.parseInt(toks11[0]);
        int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2 = Integer.parseInt(toks11[1]);
        int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3 = Integer.parseInt(toks11[2]);

        String standardStrainThirdEfficacyChange_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.standard.standardStrainEfficacyThirdEfficacyChange_young");
        String standardStrainThirdEfficacyChange_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.standard.standardStrainEfficacyThirdEfficacyChange_adult");
        String standardStrainThirdEfficacyChange_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.standard.standardStrainEfficacyThirdEfficacyChange_elderly");
        String TimestepsInThirdEfficacyPhaseStandardStrain_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.standard.numberOfTimestepsInThirdEfficacyPhaseStandardStrain_young");
        String TimestepsInThirdEfficacyPhaseStandardStrain_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.standard.numberOfTimestepsInThirdEfficacyPhaseStandardStrain_adult");
        String TimestepsInThirdEfficacyPhaseStandardStrain_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.standard.numberOfTimestepsInThirdEfficacyPhaseStandardStrain_elderly");

        String[] toks12 = standardStrainThirdEfficacyChange_young.replaceAll("\\s+", "").split(",");
        double standardStrainThirdEfficacyChange_young_Step1 = Double.parseDouble(toks12[0]);
        double standardStrainThirdEfficacyChange_young_Step2 = Double.parseDouble(toks12[1]);
        double standardStrainThirdEfficacyChange_young_Step3 = Double.parseDouble(toks12[2]);
        String[] toks13 = standardStrainThirdEfficacyChange_adult.replaceAll("\\s+", "").split(",");
        double standardStrainThirdEfficacyChange_adult_Step1 = Double.parseDouble(toks13[0]);
        double standardStrainThirdEfficacyChange_adult_Step2 = Double.parseDouble(toks13[1]);
        double standardStrainThirdEfficacyChange_adult_Step3 = Double.parseDouble(toks13[2]);
        String[] toks14 = standardStrainThirdEfficacyChange_elderly.replaceAll("\\s+", "").split(",");
        double standardStrainThirdEfficacyChange_elderly_Step1 = Double.parseDouble(toks14[0]);
        double standardStrainThirdEfficacyChange_elderly_Step2 = Double.parseDouble(toks14[1]);
        double standardStrainThirdEfficacyChange_elderly_Step3 = Double.parseDouble(toks14[2]);

        String[] toks15 = TimestepsInThirdEfficacyPhaseStandardStrain_young.replaceAll("\\s+", "").split(",");
        int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step1 = Integer.parseInt(toks15[0]);
        int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step2 = Integer.parseInt(toks15[1]);
        int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step3 = Integer.parseInt(toks15[2]);
        String[] toks16 = TimestepsInThirdEfficacyPhaseStandardStrain_adult.replaceAll("\\s+", "").split(",");
        int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1 = Integer.parseInt(toks16[0]);
        int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2 = Integer.parseInt(toks16[1]);
        int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3 = Integer.parseInt(toks16[2]);
        String[] toks17 = TimestepsInThirdEfficacyPhaseStandardStrain_elderly.replaceAll("\\s+", "").split(",");
        int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1 = Integer.parseInt(toks17[0]);
        int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2 = Integer.parseInt(toks17[1]);
        int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3 = Integer.parseInt(toks17[2]);

        String variantStrainFirstEfficacyChange_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.variant.variantStrainEfficacyFirstEfficacyChange_young");
        String variantStrainFirstEfficacyChange_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.variant.variantStrainEfficacyFirstEfficacyChange_adult");
        String variantStrainFirstEfficacyChange_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.variant.variantStrainEfficacyFirstEfficacyChange_elderly");
        String TimestepsInFirstEfficacyPhaseVariantStrain_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.variant.numberOfTimestepsInFirstEfficacyPhaseVariantStrain_young");
        String TimestepsInFirstEfficacyPhaseVariantStrain_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.variant.numberOfTimestepsInFirstEfficacyPhaseVariantStrain_adult");
        String TimestepsInFirstEfficacyPhaseVariantStrain_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.variant.numberOfTimestepsInFirstEfficacyPhaseVariantStrain_elderly");

        String[] toks18 = variantStrainFirstEfficacyChange_young.replaceAll("\\s+", "").split(",");
        double variantStrainFirstEfficacyChange_young_Step1 = Double.parseDouble(toks18[0]);
        double variantStrainFirstEfficacyChange_young_Step2 = Double.parseDouble(toks18[1]);
        double variantStrainFirstEfficacyChange_young_Step3 = Double.parseDouble(toks18[2]);
        String[] toks19 = variantStrainFirstEfficacyChange_adult.replaceAll("\\s+", "").split(",");
        double variantStrainFirstEfficacyChange_adult_Step1 = Double.parseDouble(toks19[0]);
        double variantStrainFirstEfficacyChange_adult_Step2 = Double.parseDouble(toks19[1]);
        double variantStrainFirstEfficacyChange_adult_Step3 = Double.parseDouble(toks19[2]);
        String[] toks20 = variantStrainFirstEfficacyChange_elderly.replaceAll("\\s+", "").split(",");
        double variantStrainFirstEfficacyChange_elderly_Step1 = Double.parseDouble(toks20[0]);
        double variantStrainFirstEfficacyChange_elderly_Step2 = Double.parseDouble(toks20[1]);
        double variantStrainFirstEfficacyChange_elderly_Step3 = Double.parseDouble(toks20[2]);

        String[] toks21 = TimestepsInFirstEfficacyPhaseVariantStrain_young.replaceAll("\\s+", "").split(",");
        int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step1 = Integer.parseInt(toks21[0]);
        int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step2 = Integer.parseInt(toks21[1]);
        int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step3 = Integer.parseInt(toks21[2]);
        String[] toks22 = TimestepsInFirstEfficacyPhaseVariantStrain_adult.replaceAll("\\s+", "").split(",");
        int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1 = Integer.parseInt(toks22[0]);
        int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2 = Integer.parseInt(toks22[1]);
        int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3 = Integer.parseInt(toks22[2]);
        String[] toks23 = TimestepsInFirstEfficacyPhaseVariantStrain_elderly.replaceAll("\\s+", "").split(",");
        int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1 = Integer.parseInt(toks23[0]);
        int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2 = Integer.parseInt(toks23[1]);
        int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3 = Integer.parseInt(toks23[2]);

        String variantStrainSecondEfficacyChange_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.variant.variantStrainEfficacySecondEfficacyChange_young");
        String variantStrainSecondEfficacyChange_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.variant.variantStrainEfficacySecondEfficacyChange_adult");
        String variantStrainSecondEfficacyChange_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.variant.variantStrainEfficacySecondEfficacyChange_elderly");
        String TimestepsInSecondEfficacyPhaseVariantStrain_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.variant.numberOfTimestepsInSecondEfficacyPhaseVariantStrain_young");
        String TimestepsInSecondEfficacyPhaseVariantStrain_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.variant.numberOfTimestepsInSecondEfficacyPhaseVariantStrain_adult");
        String TimestepsInSecondEfficacyPhaseVariantStrain_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.variant.numberOfTimestepsInSecondEfficacyPhaseVariantStrain_elderly");

        String[] toks24 = variantStrainSecondEfficacyChange_young.replaceAll("\\s+", "").split(",");
        double variantStrainSecondEfficacyChange_young_Step1 = Double.parseDouble(toks24[0]);
        double variantStrainSecondEfficacyChange_young_Step2 = Double.parseDouble(toks24[1]);
        double variantStrainSecondEfficacyChange_young_Step3 = Double.parseDouble(toks24[2]);
        String[] toks25 = variantStrainSecondEfficacyChange_adult.replaceAll("\\s+", "").split(",");
        double variantStrainSecondEfficacyChange_adult_Step1 = Double.parseDouble(toks25[0]);
        double variantStrainSecondEfficacyChange_adult_Step2 = Double.parseDouble(toks25[1]);
        double variantStrainSecondEfficacyChange_adult_Step3 = Double.parseDouble(toks25[2]);
        String[] toks26 = variantStrainSecondEfficacyChange_elderly.replaceAll("\\s+", "").split(",");
        double variantStrainSecondEfficacyChange_elderly_Step1 = Double.parseDouble(toks26[0]);
        double variantStrainSecondEfficacyChange_elderly_Step2 = Double.parseDouble(toks26[1]);
        double variantStrainSecondEfficacyChange_elderly_Step3 = Double.parseDouble(toks26[2]);

        String[] toks27 = TimestepsInSecondEfficacyPhaseVariantStrain_young.replaceAll("\\s+", "").split(",");
        int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step1 = Integer.parseInt(toks27[0]);
        int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step2 = Integer.parseInt(toks27[1]);
        int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step3 = Integer.parseInt(toks27[2]);
        String[] toks28 = TimestepsInSecondEfficacyPhaseVariantStrain_adult.replaceAll("\\s+", "").split(",");
        int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1 = Integer.parseInt(toks28[0]);
        int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2 = Integer.parseInt(toks28[1]);
        int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3 = Integer.parseInt(toks28[2]);
        String[] toks29 = TimestepsInSecondEfficacyPhaseVariantStrain_elderly.replaceAll("\\s+", "").split(",");
        int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1 = Integer.parseInt(toks29[0]);
        int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2 = Integer.parseInt(toks29[1]);
        int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3 = Integer.parseInt(toks29[2]);

        String variantStrainThirdEfficacyChange_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.variant.variantStrainEfficacyThirdEfficacyChange_young");
        String variantStrainThirdEfficacyChange_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.variant.variantStrainEfficacyThirdEfficacyChange_adult");
        String variantStrainThirdEfficacyChange_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.variant.variantStrainEfficacyThirdEfficacyChange_elderly");
        String TimestepsInThirdEfficacyPhaseVariantStrain_young = ctx.read("$.configParameters.feature.vaccination.efficacy.young.variant.numberOfTimestepsInThirdEfficacyPhaseVariantStrain_young");
        String TimestepsInThirdEfficacyPhaseVariantStrain_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.adult.variant.numberOfTimestepsInThirdEfficacyPhaseVariantStrain_adult");
        String TimestepsInThirdEfficacyPhaseVariantStrain_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.elderly.variant.numberOfTimestepsInThirdEfficacyPhaseVariantStrain_elderly");

        String[] toks30 = variantStrainThirdEfficacyChange_young.replaceAll("\\s+", "").split(",");
        double variantStrainThirdEfficacyChange_young_Step1 = Double.parseDouble(toks30[0]);
        double variantStrainThirdEfficacyChange_young_Step2 = Double.parseDouble(toks30[1]);
        double variantStrainThirdEfficacyChange_young_Step3 = Double.parseDouble(toks30[2]);
        String[] toks31 = variantStrainThirdEfficacyChange_adult.replaceAll("\\s+", "").split(",");
        double variantStrainThirdEfficacyChange_adult_Step1 = Double.parseDouble(toks31[0]);
        double variantStrainThirdEfficacyChange_adult_Step2 = Double.parseDouble(toks31[1]);
        double variantStrainThirdEfficacyChange_adult_Step3 = Double.parseDouble(toks31[2]);
        String[] toks32 = variantStrainThirdEfficacyChange_elderly.replaceAll("\\s+", "").split(",");
        double variantStrainThirdEfficacyChange_elderly_Step1 = Double.parseDouble(toks32[0]);
        double variantStrainThirdEfficacyChange_elderly_Step2 = Double.parseDouble(toks32[1]);
        double variantStrainThirdEfficacyChange_elderly_Step3 = Double.parseDouble(toks32[2]);

        String[] toks33 = TimestepsInThirdEfficacyPhaseVariantStrain_young.replaceAll("\\s+", "").split(",");
        int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step1 = Integer.parseInt(toks33[0]);
        int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step2 = Integer.parseInt(toks33[1]);
        int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step3 = Integer.parseInt(toks33[2]);
        String[] toks34 = TimestepsInThirdEfficacyPhaseVariantStrain_adult.replaceAll("\\s+", "").split(",");
        int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1 = Integer.parseInt(toks34[0]);
        int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2 = Integer.parseInt(toks34[1]);
        int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3 = Integer.parseInt(toks34[2]);
        String[] toks35 = TimestepsInThirdEfficacyPhaseVariantStrain_elderly.replaceAll("\\s+", "").split(",");
        int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1 = Integer.parseInt(toks35[0]);
        int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2 = Integer.parseInt(toks35[1]);
        int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3 = Integer.parseInt(toks35[2]);


        double standardStrainFirstEfficacyChange_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.standard.standardStrainEfficacyFirstEfficacyChange_young");
        double standardStrainFirstEfficacyChange_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.standard.standardStrainEfficacyFirstEfficacyChange_adult");
        double standardStrainFirstEfficacyChange_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.standard.standardStrainEfficacyFirstEfficacyChange_elderly");
        int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.standard.numberOfTimestepsInFirstEfficacyPhaseStandardStrain_young");
        int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.standard.numberOfTimestepsInFirstEfficacyPhaseStandardStrain_adult");
        int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.standard.numberOfTimestepsInFirstEfficacyPhaseStandardStrain_elderly");

        double standardStrainSecondEfficacyChange_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.standard.standardStrainEfficacySecondEfficacyChange_young");
        double standardStrainSecondEfficacyChange_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.standard.standardStrainEfficacySecondEfficacyChange_adult");
        double standardStrainSecondEfficacyChange_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.standard.standardStrainEfficacySecondEfficacyChange_elderly");
        int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.standard.numberOfTimestepsInSecondEfficacyPhaseStandardStrain_young");
        int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.standard.numberOfTimestepsInSecondEfficacyPhaseStandardStrain_adult");
        int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.standard.numberOfTimestepsInSecondEfficacyPhaseStandardStrain_elderly");

        double standardStrainThirdEfficacyChange_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.standard.standardStrainEfficacyThirdEfficacyChange_young");
        double standardStrainThirdEfficacyChange_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.standard.standardStrainEfficacyThirdEfficacyChange_adult");
        double standardStrainThirdEfficacyChange_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.standard.standardStrainEfficacyThirdEfficacyChange_elderly");
        int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.standard.numberOfTimestepsInThirdEfficacyPhaseStandardStrain_young");
        int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.standard.numberOfTimestepsInThirdEfficacyPhaseStandardStrain_adult");
        int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.standard.numberOfTimestepsInThirdEfficacyPhaseStandardStrain_elderly");

        double variantStrainFirstEfficacyChange_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.variant.variantStrainEfficacyFirstEfficacyChange_young");
        double variantStrainFirstEfficacyChange_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.variant.variantStrainEfficacyFirstEfficacyChange_adult");
        double variantStrainFirstEfficacyChange_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.variant.variantStrainEfficacyFirstEfficacyChange_elderly");
        int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.variant.numberOfTimestepsInFirstEfficacyPhaseVariantStrain_young");
        int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.variant.numberOfTimestepsInFirstEfficacyPhaseVariantStrain_adult");
        int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.variant.numberOfTimestepsInFirstEfficacyPhaseVariantStrain_elderly");

        double variantStrainSecondEfficacyChange_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.variant.variantStrainEfficacySecondEfficacyChange_young");
        double variantStrainSecondEfficacyChange_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.variant.variantStrainEfficacySecondEfficacyChange_adult");
        double variantStrainSecondEfficacyChange_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.variant.variantStrainEfficacySecondEfficacyChange_elderly");
        int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.variant.numberOfTimestepsInSecondEfficacyPhaseVariantStrain_young");
        int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.variant.numberOfTimestepsInSecondEfficacyPhaseVariantStrain_adult");
        int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.variant.numberOfTimestepsInSecondEfficacyPhaseVariantStrain_elderly");

        double variantStrainThirdEfficacyChange_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.variant.variantStrainEfficacyThirdEfficacyChange_young");
        double variantStrainThirdEfficacyChange_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.variant.variantStrainEfficacyThirdEfficacyChange_adult");
        double variantStrainThirdEfficacyChange_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.variant.variantStrainEfficacyThirdEfficacyChange_elderly");
        int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.young.variant.numberOfTimestepsInThirdEfficacyPhaseVariantStrain_young");
        int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.adult.variant.numberOfTimestepsInThirdEfficacyPhaseVariantStrain_adult");
        int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly = ctx.read("$.configParameters.feature.vaccination.efficacy.afterImmunityLoss.elderly.variant.numberOfTimestepsInThirdEfficacyPhaseVariantStrain_elderly");

        double mildInfectiousMultiplier = ctx.read("$.configParameters.feature.wastewater.mild-infectious-multiplier");
        double severeInfectiousMultiplier= ctx.read("$.configParameters.feature.wastewater.severe-infectious-multiplier");
        double hospitalisedMultiplier = ctx.read("$.configParameters.feature.wastewater.hospitalised-multiplier");

        return new ConfigParameters(
                startTimeStep,
                lastTimeStepInclusive,
                firstBetaFactorTimeStep,
                secondBetaFactorTimeStep,
                tauLeapStep,
                chunkSize,
                outputFrequencyString,
                reseedingAreaLevel,
                localLockdownsActivated,
                localLockdownsAreaLevelString,
                mildInfectiousUpperLevel,
                mildInfectiousLowerLevel,
                localLockdownStartTimeStep,
                localLockdownContactTracingRateMultiplier,
                vaccineStartTimestep,
                standardStrainFirstEfficacyChange_young_Step1,
                standardStrainFirstEfficacyChange_adult_Step1,
                standardStrainFirstEfficacyChange_elderly_Step1,
                TimestepsInFirstEfficacyPhaseStandardStrain_young_Step1,
                TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1,
                TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1,
                standardStrainFirstEfficacyChange_young_Step2,
                standardStrainFirstEfficacyChange_adult_Step2,
                standardStrainFirstEfficacyChange_elderly_Step2,
                TimestepsInFirstEfficacyPhaseStandardStrain_young_Step2,
                TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2,
                TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2,
                standardStrainFirstEfficacyChange_young_Step3,
                standardStrainFirstEfficacyChange_adult_Step3,
                standardStrainFirstEfficacyChange_elderly_Step3,
                TimestepsInFirstEfficacyPhaseStandardStrain_young_Step3,
                TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3,
                TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3,
                standardStrainSecondEfficacyChange_young_Step1,
                standardStrainSecondEfficacyChange_adult_Step1,
                standardStrainSecondEfficacyChange_elderly_Step1,
                TimestepsInSecondEfficacyPhaseStandardStrain_young_Step1,
                TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1,
                TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1,
                standardStrainSecondEfficacyChange_young_Step2,
                standardStrainSecondEfficacyChange_adult_Step2,
                standardStrainSecondEfficacyChange_elderly_Step2,
                TimestepsInSecondEfficacyPhaseStandardStrain_young_Step2,
                TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2,
                TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2,
                standardStrainSecondEfficacyChange_young_Step3,
                standardStrainSecondEfficacyChange_adult_Step3,
                standardStrainSecondEfficacyChange_elderly_Step3,
                TimestepsInSecondEfficacyPhaseStandardStrain_young_Step3,
                TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3,
                TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3,
                standardStrainThirdEfficacyChange_young_Step1,
                standardStrainThirdEfficacyChange_adult_Step1,
                standardStrainThirdEfficacyChange_elderly_Step1,
                TimestepsInThirdEfficacyPhaseStandardStrain_young_Step1,
                TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1,
                TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1,
                standardStrainThirdEfficacyChange_young_Step2,
                standardStrainThirdEfficacyChange_adult_Step2,
                standardStrainThirdEfficacyChange_elderly_Step2,
                TimestepsInThirdEfficacyPhaseStandardStrain_young_Step2,
                TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2,
                TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2,
                standardStrainThirdEfficacyChange_young_Step3,
                standardStrainThirdEfficacyChange_adult_Step3,
                standardStrainThirdEfficacyChange_elderly_Step3,
                TimestepsInThirdEfficacyPhaseStandardStrain_young_Step3,
                TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3,
                TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3,
                variantStrainFirstEfficacyChange_young_Step1,
                variantStrainFirstEfficacyChange_adult_Step1,
                variantStrainFirstEfficacyChange_elderly_Step1,
                TimestepsInFirstEfficacyPhaseVariantStrain_young_Step1,
                TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1,
                TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1,
                variantStrainFirstEfficacyChange_young_Step2,
                variantStrainFirstEfficacyChange_adult_Step2,
                variantStrainFirstEfficacyChange_elderly_Step2,
                TimestepsInFirstEfficacyPhaseVariantStrain_young_Step2,
                TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2,
                TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2,
                variantStrainFirstEfficacyChange_young_Step3,
                variantStrainFirstEfficacyChange_adult_Step3,
                variantStrainFirstEfficacyChange_elderly_Step3,
                TimestepsInFirstEfficacyPhaseVariantStrain_young_Step3,
                TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3,
                TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3,
                variantStrainSecondEfficacyChange_young_Step1,
                variantStrainSecondEfficacyChange_adult_Step1,
                variantStrainSecondEfficacyChange_elderly_Step1,
                TimestepsInSecondEfficacyPhaseVariantStrain_young_Step1,
                TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1,
                TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1,
                variantStrainSecondEfficacyChange_young_Step2,
                variantStrainSecondEfficacyChange_adult_Step2,
                variantStrainSecondEfficacyChange_elderly_Step2,
                TimestepsInSecondEfficacyPhaseVariantStrain_young_Step2,
                TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2,
                TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2,
                variantStrainSecondEfficacyChange_young_Step3,
                variantStrainSecondEfficacyChange_adult_Step3,
                variantStrainSecondEfficacyChange_elderly_Step3,
                TimestepsInSecondEfficacyPhaseVariantStrain_young_Step3,
                TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3,
                TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3,
                variantStrainThirdEfficacyChange_young_Step1,
                variantStrainThirdEfficacyChange_adult_Step1,
                variantStrainThirdEfficacyChange_elderly_Step1,
                TimestepsInThirdEfficacyPhaseVariantStrain_young_Step1,
                TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1,
                TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1,
                variantStrainThirdEfficacyChange_young_Step2,
                variantStrainThirdEfficacyChange_adult_Step2,
                variantStrainThirdEfficacyChange_elderly_Step2,
                TimestepsInThirdEfficacyPhaseVariantStrain_young_Step2,
                TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2,
                TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2,
                variantStrainThirdEfficacyChange_young_Step3,
                variantStrainThirdEfficacyChange_adult_Step3,
                variantStrainThirdEfficacyChange_elderly_Step3,
                TimestepsInThirdEfficacyPhaseVariantStrain_young_Step3,
                TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3,
                TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3,
                standardStrainFirstEfficacyChange_afterImmunityLoss_young,
                standardStrainFirstEfficacyChange_afterImmunityLoss_adult,
                standardStrainFirstEfficacyChange_afterImmunityLoss_elderly,
                TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young,
                TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult,
                TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly,
                standardStrainSecondEfficacyChange_afterImmunityLoss_young,
                standardStrainSecondEfficacyChange_afterImmunityLoss_adult,
                standardStrainSecondEfficacyChange_afterImmunityLoss_elderly,
                TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young,
                TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult,
                TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly,
                standardStrainThirdEfficacyChange_afterImmunityLoss_young,
                standardStrainThirdEfficacyChange_afterImmunityLoss_adult,
                standardStrainThirdEfficacyChange_afterImmunityLoss_elderly,
                TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young,
                TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult,
                TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly,
                variantStrainFirstEfficacyChange_afterImmunityLoss_young,
                variantStrainFirstEfficacyChange_afterImmunityLoss_adult,
                variantStrainFirstEfficacyChange_afterImmunityLoss_elderly,
                TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young,
                TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult,
                TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly,
                variantStrainSecondEfficacyChange_afterImmunityLoss_young,
                variantStrainSecondEfficacyChange_afterImmunityLoss_adult,
                variantStrainSecondEfficacyChange_afterImmunityLoss_elderly,
                TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young,
                TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult,
                TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly,
                variantStrainThirdEfficacyChange_afterImmunityLoss_young,
                variantStrainThirdEfficacyChange_afterImmunityLoss_adult,
                variantStrainThirdEfficacyChange_afterImmunityLoss_elderly,
                TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young,
                TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult,
                TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly,
                mildInfectiousMultiplier,
                severeInfectiousMultiplier,
                hospitalisedMultiplier);
    }

    public ConfigParameters(
            int startTimeStep,
            int lastTimeStepInclusive,
            int firstBetaFactorTimeStep,
            int secondBetaFactorTimeStep,
            double tauLeapStep,
            int chunkSize,
            String outputFrequencyString,
            String reseedingAreaLevel,
            boolean localLockdownsActivated,
            String localLockdownsAreaLevelString,
            double mildInfectiousUpperLevel,
            double mildInfectiousLowerLevel,
            int localLockdownStartTimeStep,
            double localLockdownContactTracingRateMultiplier,
            int vaccineStartTimestep,
            double standardStrainFirstEfficacyChange_young_Step1,
            double standardStrainFirstEfficacyChange_adult_Step1,
            double standardStrainFirstEfficacyChange_elderly_Step1,
            int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step1,
            int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1,
            int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1,
            double standardStrainFirstEfficacyChange_young_Step2,
            double standardStrainFirstEfficacyChange_adult_Step2,
            double standardStrainFirstEfficacyChange_elderly_Step2,
            int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step2,
            int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2,
            int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2,
            double standardStrainFirstEfficacyChange_young_Step3,
            double standardStrainFirstEfficacyChange_adult_Step3,
            double standardStrainFirstEfficacyChange_elderly_Step3,
            int TimestepsInFirstEfficacyPhaseStandardStrain_young_Step3,
            int TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3,
            int TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3,
            double standardStrainSecondEfficacyChange_young_Step1,
            double standardStrainSecondEfficacyChange_adult_Step1,
            double standardStrainSecondEfficacyChange_elderly_Step1,
            int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step1,
            int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1,
            int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1,
            double standardStrainSecondEfficacyChange_young_Step2,
            double standardStrainSecondEfficacyChange_adult_Step2,
            double standardStrainSecondEfficacyChange_elderly_Step2,
            int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step2,
            int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2,
            int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2,
            double standardStrainSecondEfficacyChange_young_Step3,
            double standardStrainSecondEfficacyChange_adult_Step3,
            double standardStrainSecondEfficacyChange_elderly_Step3,
            int TimestepsInSecondEfficacyPhaseStandardStrain_young_Step3,
            int TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3,
            int TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3,
            double standardStrainThirdEfficacyChange_young_Step1,
            double standardStrainThirdEfficacyChange_adult_Step1,
            double standardStrainThirdEfficacyChange_elderly_Step1,
            int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step1,
            int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1,
            int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1,
            double standardStrainThirdEfficacyChange_young_Step2,
            double standardStrainThirdEfficacyChange_adult_Step2,
            double standardStrainThirdEfficacyChange_elderly_Step2,
            int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step2,
            int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2,
            int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2,
            double standardStrainThirdEfficacyChange_young_Step3,
            double standardStrainThirdEfficacyChange_adult_Step3,
            double standardStrainThirdEfficacyChange_elderly_Step3,
            int TimestepsInThirdEfficacyPhaseStandardStrain_young_Step3,
            int TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3,
            int TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3,
            double variantStrainFirstEfficacyChange_young_Step1,
            double variantStrainFirstEfficacyChange_adult_Step1,
            double variantStrainFirstEfficacyChange_elderly_Step1,
            int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step1,
            int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1,
            int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1,
            double variantStrainFirstEfficacyChange_young_Step2,
            double variantStrainFirstEfficacyChange_adult_Step2,
            double variantStrainFirstEfficacyChange_elderly_Step2,
            int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step2,
            int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2,
            int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2,
            double variantStrainFirstEfficacyChange_young_Step3,
            double variantStrainFirstEfficacyChange_adult_Step3,
            double variantStrainFirstEfficacyChange_elderly_Step3,
            int TimestepsInFirstEfficacyPhaseVariantStrain_young_Step3,
            int TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3,
            int TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3,
            double variantStrainSecondEfficacyChange_young_Step1,
            double variantStrainSecondEfficacyChange_adult_Step1,
            double variantStrainSecondEfficacyChange_elderly_Step1,
            int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step1,
            int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1,
            int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1,
            double variantStrainSecondEfficacyChange_young_Step2,
            double variantStrainSecondEfficacyChange_adult_Step2,
            double variantStrainSecondEfficacyChange_elderly_Step2,
            int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step2,
            int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2,
            int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2,
            double variantStrainSecondEfficacyChange_young_Step3,
            double variantStrainSecondEfficacyChange_adult_Step3,
            double variantStrainSecondEfficacyChange_elderly_Step3,
            int TimestepsInSecondEfficacyPhaseVariantStrain_young_Step3,
            int TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3,
            int TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3,
            double variantStrainThirdEfficacyChange_young_Step1,
            double variantStrainThirdEfficacyChange_adult_Step1,
            double variantStrainThirdEfficacyChange_elderly_Step1,
            int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step1,
            int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1,
            int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1,
            double variantStrainThirdEfficacyChange_young_Step2,
            double variantStrainThirdEfficacyChange_adult_Step2,
            double variantStrainThirdEfficacyChange_elderly_Step2,
            int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step2,
            int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2,
            int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2,
            double variantStrainThirdEfficacyChange_young_Step3,
            double variantStrainThirdEfficacyChange_adult_Step3,
            double variantStrainThirdEfficacyChange_elderly_Step3,
            int TimestepsInThirdEfficacyPhaseVariantStrain_young_Step3,
            int TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3,
            int TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3,
            double standardStrainFirstEfficacyChange_afterImmunityLoss_young,
            double standardStrainFirstEfficacyChange_afterImmunityLoss_adult,
            double standardStrainFirstEfficacyChange_afterImmunityLoss_elderly,
            int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young,
            int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult,
            int TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly,
            double standardStrainSecondEfficacyChange_afterImmunityLoss_young,
            double standardStrainSecondEfficacyChange_afterImmunityLoss_adult,
            double standardStrainSecondEfficacyChange_afterImmunityLoss_elderly,
            int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young,
            int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult,
            int TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly,
            double standardStrainThirdEfficacyChange_afterImmunityLoss_young,
            double standardStrainThirdEfficacyChange_afterImmunityLoss_adult,
            double standardStrainThirdEfficacyChange_afterImmunityLoss_elderly,
            int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young,
            int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult,
            int TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly,
            double variantStrainFirstEfficacyChange_afterImmunityLoss_young,
            double variantStrainFirstEfficacyChange_afterImmunityLoss_adult,
            double variantStrainFirstEfficacyChange_afterImmunityLoss_elderly,
            int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young,
            int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult,
            int TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly,
            double variantStrainSecondEfficacyChange_afterImmunityLoss_young,
            double variantStrainSecondEfficacyChange_afterImmunityLoss_adult,
            double variantStrainSecondEfficacyChange_afterImmunityLoss_elderly,
            int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young,
            int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult,
            int TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly,
            double variantStrainThirdEfficacyChange_afterImmunityLoss_young,
            double variantStrainThirdEfficacyChange_afterImmunityLoss_adult,
            double variantStrainThirdEfficacyChange_afterImmunityLoss_elderly,
            int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young,
            int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult,
            int TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly,
            double mildInfectiousMultiplier,
            double severeInfectiousMultiplier,
            double hospitalisedMultiplier) {
        this.firstTimeStep = startTimeStep;
        this.lastTimeStep = lastTimeStepInclusive;
        this.firstBetaFactorTimeStep = firstBetaFactorTimeStep;
        this.secondBetaFactorTimeStep = secondBetaFactorTimeStep;
        this.chunkSize = chunkSize;
        this.outputFrequencyString = outputFrequencyString;
        this.tauLeapStep = tauLeapStep;
        this.reseedingAreaLevel = reseedingAreaLevel;
        this.localLockdownsActivated = localLockdownsActivated;
        this.localLockdownsAreaLevelString = localLockdownsAreaLevelString;
        this.mildInfectiousUpperLevel = mildInfectiousUpperLevel;
        this.mildInfectiousLowerLevel = mildInfectiousLowerLevel;
        this.localLockdownStartTimeStep = localLockdownStartTimeStep;
        this.localLockdownContactTracingRateMultiplier = localLockdownContactTracingRateMultiplier;
        this.vaccineStartTimestep = vaccineStartTimestep;
        this.standardStrainFirstEfficacyChange_young_Step1 = standardStrainFirstEfficacyChange_young_Step1 ;
        this.standardStrainFirstEfficacyChange_adult_Step1  = standardStrainFirstEfficacyChange_adult_Step1 ;
        this.standardStrainFirstEfficacyChange_elderly_Step1  = standardStrainFirstEfficacyChange_elderly_Step1 ;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_young_Step1 = TimestepsInFirstEfficacyPhaseStandardStrain_young_Step1;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1 = TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1 = TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1;
        this.standardStrainFirstEfficacyChange_young_Step2 = standardStrainFirstEfficacyChange_young_Step2 ;
        this.standardStrainFirstEfficacyChange_adult_Step2  = standardStrainFirstEfficacyChange_adult_Step2 ;
        this.standardStrainFirstEfficacyChange_elderly_Step2  = standardStrainFirstEfficacyChange_elderly_Step2 ;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_young_Step2 = TimestepsInFirstEfficacyPhaseStandardStrain_young_Step2;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2 = TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2 = TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2;
        this.standardStrainFirstEfficacyChange_young_Step3 = standardStrainFirstEfficacyChange_young_Step3 ;
        this.standardStrainFirstEfficacyChange_adult_Step3  = standardStrainFirstEfficacyChange_adult_Step3 ;
        this.standardStrainFirstEfficacyChange_elderly_Step3  = standardStrainFirstEfficacyChange_elderly_Step3 ;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_young_Step3 = TimestepsInFirstEfficacyPhaseStandardStrain_young_Step3;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3 = TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3 = TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3;
        this.standardStrainSecondEfficacyChange_young_Step1 = standardStrainSecondEfficacyChange_young_Step1;
        this.standardStrainSecondEfficacyChange_adult_Step1 = standardStrainSecondEfficacyChange_adult_Step1;
        this.standardStrainSecondEfficacyChange_elderly_Step1 = standardStrainSecondEfficacyChange_elderly_Step1;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_young_Step1 = TimestepsInSecondEfficacyPhaseStandardStrain_young_Step1;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1 = TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1 = TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1;
        this.standardStrainSecondEfficacyChange_young_Step2 = standardStrainSecondEfficacyChange_young_Step2;
        this.standardStrainSecondEfficacyChange_adult_Step2 = standardStrainSecondEfficacyChange_adult_Step2;
        this.standardStrainSecondEfficacyChange_elderly_Step2 = standardStrainSecondEfficacyChange_elderly_Step2;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_young_Step2 = TimestepsInSecondEfficacyPhaseStandardStrain_young_Step2;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2 = TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2 = TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2;
        this.standardStrainSecondEfficacyChange_young_Step3 = standardStrainSecondEfficacyChange_young_Step3;
        this.standardStrainSecondEfficacyChange_adult_Step3 = standardStrainSecondEfficacyChange_adult_Step3;
        this.standardStrainSecondEfficacyChange_elderly_Step3 = standardStrainSecondEfficacyChange_elderly_Step3;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_young_Step3 = TimestepsInSecondEfficacyPhaseStandardStrain_young_Step3;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3 = TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3 = TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3;
        this.standardStrainThirdEfficacyChange_young_Step1 = standardStrainThirdEfficacyChange_young_Step1;
        this.standardStrainThirdEfficacyChange_adult_Step1 = standardStrainThirdEfficacyChange_adult_Step1;
        this.standardStrainThirdEfficacyChange_elderly_Step1 = standardStrainThirdEfficacyChange_elderly_Step1;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_young_Step1 = TimestepsInThirdEfficacyPhaseStandardStrain_young_Step1;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1 = TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1 = TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1;
        this.standardStrainThirdEfficacyChange_young_Step2 = standardStrainThirdEfficacyChange_young_Step2;
        this.standardStrainThirdEfficacyChange_adult_Step2 = standardStrainThirdEfficacyChange_adult_Step2;
        this.standardStrainThirdEfficacyChange_elderly_Step2 = standardStrainThirdEfficacyChange_elderly_Step2;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_young_Step2 = TimestepsInThirdEfficacyPhaseStandardStrain_young_Step2;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2 = TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2 = TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2;
        this.standardStrainThirdEfficacyChange_young_Step3 = standardStrainThirdEfficacyChange_young_Step3;
        this.standardStrainThirdEfficacyChange_adult_Step3 = standardStrainThirdEfficacyChange_adult_Step3;
        this.standardStrainThirdEfficacyChange_elderly_Step3 = standardStrainThirdEfficacyChange_elderly_Step3;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_young_Step3 = TimestepsInThirdEfficacyPhaseStandardStrain_young_Step3;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3 = TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3 = TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3;
        this.variantStrainFirstEfficacyChange_young_Step1 = variantStrainFirstEfficacyChange_young_Step1;
        this.variantStrainFirstEfficacyChange_adult_Step1 = variantStrainFirstEfficacyChange_adult_Step1;
        this.variantStrainFirstEfficacyChange_elderly_Step1 = variantStrainFirstEfficacyChange_elderly_Step1;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_young_Step1 = TimestepsInFirstEfficacyPhaseVariantStrain_young_Step1;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1 = TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1 = TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1;
        this.variantStrainFirstEfficacyChange_young_Step2 = variantStrainFirstEfficacyChange_young_Step2;
        this.variantStrainFirstEfficacyChange_adult_Step2 = variantStrainFirstEfficacyChange_adult_Step2;
        this.variantStrainFirstEfficacyChange_elderly_Step2 = variantStrainFirstEfficacyChange_elderly_Step2;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_young_Step2 = TimestepsInFirstEfficacyPhaseVariantStrain_young_Step2;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2 = TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2 = TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2;
        this.variantStrainFirstEfficacyChange_young_Step3 = variantStrainFirstEfficacyChange_young_Step3;
        this.variantStrainFirstEfficacyChange_adult_Step3 = variantStrainFirstEfficacyChange_adult_Step3;
        this.variantStrainFirstEfficacyChange_elderly_Step3 = variantStrainFirstEfficacyChange_elderly_Step3;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_young_Step3 = TimestepsInFirstEfficacyPhaseVariantStrain_young_Step3;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3 = TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3 = TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3;
        this.variantStrainSecondEfficacyChange_young_Step1 = variantStrainSecondEfficacyChange_young_Step1;
        this.variantStrainSecondEfficacyChange_adult_Step1 = variantStrainSecondEfficacyChange_adult_Step1;
        this.variantStrainSecondEfficacyChange_elderly_Step1 = variantStrainSecondEfficacyChange_elderly_Step1;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_young_Step1 = TimestepsInSecondEfficacyPhaseVariantStrain_young_Step1;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1 = TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1 = TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1;
        this.variantStrainSecondEfficacyChange_young_Step2 = variantStrainSecondEfficacyChange_young_Step2;
        this.variantStrainSecondEfficacyChange_adult_Step2 = variantStrainSecondEfficacyChange_adult_Step2;
        this.variantStrainSecondEfficacyChange_elderly_Step2 = variantStrainSecondEfficacyChange_elderly_Step2;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_young_Step2 = TimestepsInSecondEfficacyPhaseVariantStrain_young_Step2;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2 = TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2 = TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2;
        this.variantStrainSecondEfficacyChange_young_Step3 = variantStrainSecondEfficacyChange_young_Step3;
        this.variantStrainSecondEfficacyChange_adult_Step3 = variantStrainSecondEfficacyChange_adult_Step3;
        this.variantStrainSecondEfficacyChange_elderly_Step3 = variantStrainSecondEfficacyChange_elderly_Step3;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_young_Step3 = TimestepsInSecondEfficacyPhaseVariantStrain_young_Step3;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3 = TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3 = TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3;
        this.variantStrainThirdEfficacyChange_young_Step1 = variantStrainThirdEfficacyChange_young_Step1;
        this.variantStrainThirdEfficacyChange_adult_Step1 = variantStrainThirdEfficacyChange_adult_Step1;
        this.variantStrainThirdEfficacyChange_elderly_Step1 = variantStrainThirdEfficacyChange_elderly_Step1;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_young_Step1 = TimestepsInThirdEfficacyPhaseVariantStrain_young_Step1;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1 = TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1 = TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1;
        this.variantStrainThirdEfficacyChange_young_Step2 = variantStrainThirdEfficacyChange_young_Step2;
        this.variantStrainThirdEfficacyChange_adult_Step2 = variantStrainThirdEfficacyChange_adult_Step2;
        this.variantStrainThirdEfficacyChange_elderly_Step2 = variantStrainThirdEfficacyChange_elderly_Step2;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_young_Step2 = TimestepsInThirdEfficacyPhaseVariantStrain_young_Step2;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2 = TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2 = TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2;
        this.variantStrainThirdEfficacyChange_young_Step3 = variantStrainThirdEfficacyChange_young_Step3;
        this.variantStrainThirdEfficacyChange_adult_Step3 = variantStrainThirdEfficacyChange_adult_Step3;
        this.variantStrainThirdEfficacyChange_elderly_Step3 = variantStrainThirdEfficacyChange_elderly_Step3;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_young_Step3 = TimestepsInThirdEfficacyPhaseVariantStrain_young_Step3;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3 = TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3 = TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3;
        this.standardStrainFirstEfficacyChange_afterImmunityLoss_young = standardStrainFirstEfficacyChange_afterImmunityLoss_young;
        this.standardStrainFirstEfficacyChange_afterImmunityLoss_adult = standardStrainFirstEfficacyChange_afterImmunityLoss_adult;
        this.standardStrainFirstEfficacyChange_afterImmunityLoss_elderly = standardStrainFirstEfficacyChange_afterImmunityLoss_elderly;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young = TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult = TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
        this.TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly = TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
        this.standardStrainSecondEfficacyChange_afterImmunityLoss_young = standardStrainSecondEfficacyChange_afterImmunityLoss_young;
        this.standardStrainSecondEfficacyChange_afterImmunityLoss_adult = standardStrainSecondEfficacyChange_afterImmunityLoss_adult;
        this.standardStrainSecondEfficacyChange_afterImmunityLoss_elderly = standardStrainSecondEfficacyChange_afterImmunityLoss_elderly;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young = TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult = TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
        this.TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly = TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
        this.standardStrainThirdEfficacyChange_afterImmunityLoss_young = standardStrainThirdEfficacyChange_afterImmunityLoss_young;
        this.standardStrainThirdEfficacyChange_afterImmunityLoss_adult = standardStrainThirdEfficacyChange_afterImmunityLoss_adult;
        this.standardStrainThirdEfficacyChange_afterImmunityLoss_elderly = standardStrainThirdEfficacyChange_afterImmunityLoss_elderly;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young = TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult = TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
        this.TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly = TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
        this.variantStrainFirstEfficacyChange_afterImmunityLoss_young = variantStrainFirstEfficacyChange_afterImmunityLoss_young;
        this.variantStrainFirstEfficacyChange_afterImmunityLoss_adult = variantStrainFirstEfficacyChange_afterImmunityLoss_adult;
        this.variantStrainFirstEfficacyChange_afterImmunityLoss_elderly = variantStrainFirstEfficacyChange_afterImmunityLoss_elderly;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young = TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult = TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
        this.TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly = TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
        this.variantStrainSecondEfficacyChange_afterImmunityLoss_young = variantStrainSecondEfficacyChange_afterImmunityLoss_young;
        this.variantStrainSecondEfficacyChange_afterImmunityLoss_adult = variantStrainSecondEfficacyChange_afterImmunityLoss_adult;
        this.variantStrainSecondEfficacyChange_afterImmunityLoss_elderly = variantStrainSecondEfficacyChange_afterImmunityLoss_elderly;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young = TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult = TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
        this.TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly = TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
        this.variantStrainThirdEfficacyChange_afterImmunityLoss_young = variantStrainThirdEfficacyChange_afterImmunityLoss_young;
        this.variantStrainThirdEfficacyChange_afterImmunityLoss_adult = variantStrainThirdEfficacyChange_afterImmunityLoss_adult;
        this.variantStrainThirdEfficacyChange_afterImmunityLoss_elderly = variantStrainThirdEfficacyChange_afterImmunityLoss_elderly;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young = TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult = TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
        this.TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly = TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
        this.mildInfectiousMultiplier = mildInfectiousMultiplier;
        this.severeInfectiousMultiplier = severeInfectiousMultiplier;
        this.hospitalisedMultiplier = hospitalisedMultiplier;


        switch (reseedingAreaLevel) {
//            case "HB":
//                this.seedingLevel = HB;
//                break;
//            case "CA":
//                this.seedingLevel = CA;
//                break;
            case "IZ":
                this.seedingLevel = IZ;
                break;
//            case "DZ":
//                this.seedingLevel = DZ;
//                break;
//            case "OA":
//                this.seedingLevel = OA;
//                break;
            default:
                throw new UnsupportedOperationException("Unsupported reseeding level type: " + reseedingAreaLevel);
        }
        switch (localLockdownsAreaLevelString) {
            case "HB":
                this.localLockdownsAreaLevel = HB;
                break;
            case "CA":
                this.localLockdownsAreaLevel = CA;
                break;
            case "IZ":
                this.localLockdownsAreaLevel = IZ;
                break;
            case "DZ":
                this.localLockdownsAreaLevel = DZ;
                break;
            case "OA":
                this.localLockdownsAreaLevel = OA;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported reseeding level type: " + reseedingAreaLevel);
        }
        switch (outputFrequencyString) {
            case "daily":
                this.outputFrequency = OutputFrequency.DAILY;
                break;
            case "weekly":
                this.outputFrequency = OutputFrequency.WEEKLY;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported output frquency type: " + outputFrequencyString);
        }

    }

    public String getReseedingAreaLevel() {
        return reseedingAreaLevel;
    }

    public AreaLevels getSeedingLevel() {
        return seedingLevel;
    }

    public String getLocalLockdownsAreaLevelString() {
        return localLockdownsAreaLevelString;
    }

    public double getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young() {
        return standardStrainFirstEfficacyChange_afterImmunityLoss_young;
    }

    public double getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult() {
        return standardStrainFirstEfficacyChange_afterImmunityLoss_adult;
    }

    public double getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly() {
        return standardStrainFirstEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    }

    public int getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    }

    public int getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
    }

    public double getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_young() {
        return standardStrainSecondEfficacyChange_afterImmunityLoss_young;
    }

    public double getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult() {
        return standardStrainSecondEfficacyChange_afterImmunityLoss_adult;
    }

    public double getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly() {
        return standardStrainSecondEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    }

    public int getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    }

    public int getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
    }

    public double getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young() {
        return standardStrainThirdEfficacyChange_afterImmunityLoss_young;
    }

    public double getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult() {
        return standardStrainThirdEfficacyChange_afterImmunityLoss_adult;
    }

    public double getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly() {
        return standardStrainThirdEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    }

    public int getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    }

    public int getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
    }

    public double getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young() {
        return variantStrainFirstEfficacyChange_afterImmunityLoss_young;
    }

    public double getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult() {
        return variantStrainFirstEfficacyChange_afterImmunityLoss_adult;
    }

    public double getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly() {
        return variantStrainFirstEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    }

    public int getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    }

    public int getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
    }

    public double getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_young() {
        return variantStrainSecondEfficacyChange_afterImmunityLoss_young;
    }

    public double getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult() {
        return variantStrainSecondEfficacyChange_afterImmunityLoss_adult;
    }

    public double getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly() {
        return variantStrainSecondEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    }

    public int getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    }

    public int getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
    }

    public double getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young() {
        return variantStrainThirdEfficacyChange_afterImmunityLoss_young;
    }

    public double getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult() {
        return variantStrainThirdEfficacyChange_afterImmunityLoss_adult;
    }

    public double getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly() {
        return variantStrainThirdEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    }

    public int getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    }

    public int getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
    }

    public long getFirstTimeStep() {
        return firstTimeStep;
    }

    public long getLastTimeStep() {
        return lastTimeStep;
    }

    public long getFirstBetaFactorTimeStep() {
        return firstBetaFactorTimeStep;
    }

    public long getSecondBetaFactorTimeStep() {
        return secondBetaFactorTimeStep;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public double getTauLeapStep() {
        return tauLeapStep;
    }

    public AreaLevels getReseedAreaLevel() {
        return seedingLevel;
    }

    public boolean isLocalLockdownsActivated() {
        return localLockdownsActivated;
    }

    public AreaLevels getLocalLockdownsAreaLevel() {
        return localLockdownsAreaLevel;
    }

    public double getMildInfectiousUpperLevel() {
        return mildInfectiousUpperLevel;
    }

    public double getMildInfectiousLowerLevel() {
        return mildInfectiousLowerLevel;
    }

    public int getLocalLockdownStartTimeStep() {
        return localLockdownStartTimeStep;
    }

    public double getLocalLockdownContactTracingRateMultiplier() {
        return localLockdownContactTracingRateMultiplier;
    }

    public OutputFrequency getOutputFrequency() {
        return outputFrequency;
    }


    public int getVaccineStartTimestep() {
        return vaccineStartTimestep;
    }

    public double getStandardStrainFirstEfficacyChange_young_Step1() {
        return standardStrainFirstEfficacyChange_young_Step1;
    }

    public double getStandardStrainFirstEfficacyChange_adult_Step1() {
        return standardStrainFirstEfficacyChange_adult_Step1;
    }

    public double getStandardStrainFirstEfficacyChange_elderly_Step1() {
        return standardStrainFirstEfficacyChange_elderly_Step1;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step1() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_young_Step1;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1;
    }

    public double getStandardStrainFirstEfficacyChange_young_Step2() {
        return standardStrainFirstEfficacyChange_young_Step2;
    }

    public double getStandardStrainFirstEfficacyChange_adult_Step2() {
        return standardStrainFirstEfficacyChange_adult_Step2;
    }

    public double getStandardStrainFirstEfficacyChange_elderly_Step2() {
        return standardStrainFirstEfficacyChange_elderly_Step2;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step2() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_young_Step2;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2;
    }

    public double getStandardStrainFirstEfficacyChange_young_Step3() {
        return standardStrainFirstEfficacyChange_young_Step3;
    }

    public double getStandardStrainFirstEfficacyChange_adult_Step3() {
        return standardStrainFirstEfficacyChange_adult_Step3;
    }

    public double getStandardStrainFirstEfficacyChange_elderly_Step3() {
        return standardStrainFirstEfficacyChange_elderly_Step3;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step3() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_young_Step3;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3;
    }

    public double getStandardStrainSecondEfficacyChange_young_Step1() {
        return standardStrainSecondEfficacyChange_young_Step1;
    }

    public double getStandardStrainSecondEfficacyChange_adult_Step1() {
        return standardStrainSecondEfficacyChange_adult_Step1;
    }

    public double getStandardStrainSecondEfficacyChange_elderly_Step1() {
        return standardStrainSecondEfficacyChange_elderly_Step1;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step1() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_young_Step1;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1;
    }

    public double getStandardStrainSecondEfficacyChange_young_Step2() {
        return standardStrainSecondEfficacyChange_young_Step2;
    }

    public double getStandardStrainSecondEfficacyChange_adult_Step2() {
        return standardStrainSecondEfficacyChange_adult_Step2;
    }

    public double getStandardStrainSecondEfficacyChange_elderly_Step2() {
        return standardStrainSecondEfficacyChange_elderly_Step2;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step2() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_young_Step2;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2;
    }

    public double getStandardStrainSecondEfficacyChange_young_Step3() {
        return standardStrainSecondEfficacyChange_young_Step3;
    }

    public double getStandardStrainSecondEfficacyChange_adult_Step3() {
        return standardStrainSecondEfficacyChange_adult_Step3;
    }

    public double getStandardStrainSecondEfficacyChange_elderly_Step3() {
        return standardStrainSecondEfficacyChange_elderly_Step3;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step3() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_young_Step3;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3;
    }

    public double getStandardStrainThirdEfficacyChange_young_Step1() {
        return standardStrainThirdEfficacyChange_young_Step1;
    }

    public double getStandardStrainThirdEfficacyChange_adult_Step1() {
        return standardStrainThirdEfficacyChange_adult_Step1;
    }

    public double getStandardStrainThirdEfficacyChange_elderly_Step1() {
        return standardStrainThirdEfficacyChange_elderly_Step1;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step1() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_young_Step1;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1;
    }

    public double getStandardStrainThirdEfficacyChange_young_Step2() {
        return standardStrainThirdEfficacyChange_young_Step2;
    }

    public double getStandardStrainThirdEfficacyChange_adult_Step2() {
        return standardStrainThirdEfficacyChange_adult_Step2;
    }

    public double getStandardStrainThirdEfficacyChange_elderly_Step2() {
        return standardStrainThirdEfficacyChange_elderly_Step2;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step2() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_young_Step2;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2;
    }

    public double getStandardStrainThirdEfficacyChange_young_Step3() {
        return standardStrainThirdEfficacyChange_young_Step3;
    }

    public double getStandardStrainThirdEfficacyChange_adult_Step3() {
        return standardStrainThirdEfficacyChange_adult_Step3;
    }

    public double getStandardStrainThirdEfficacyChange_elderly_Step3() {
        return standardStrainThirdEfficacyChange_elderly_Step3;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step3() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_young_Step3;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3;
    }

    public double getVariantStrainFirstEfficacyChange_young_Step1() {
        return variantStrainFirstEfficacyChange_young_Step1;
    }

    public double getVariantStrainFirstEfficacyChange_adult_Step1() {
        return variantStrainFirstEfficacyChange_adult_Step1;
    }

    public double getVariantStrainFirstEfficacyChange_elderly_Step1() {
        return variantStrainFirstEfficacyChange_elderly_Step1;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step1() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_young_Step1;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1;
    }

    public double getVariantStrainFirstEfficacyChange_young_Step2() {
        return variantStrainFirstEfficacyChange_young_Step2;
    }

    public double getVariantStrainFirstEfficacyChange_adult_Step2() {
        return variantStrainFirstEfficacyChange_adult_Step2;
    }

    public double getVariantStrainFirstEfficacyChange_elderly_Step2() {
        return variantStrainFirstEfficacyChange_elderly_Step2;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step2() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_young_Step2;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2;
    }

    public double getVariantStrainFirstEfficacyChange_young_Step3() {
        return variantStrainFirstEfficacyChange_young_Step3;
    }

    public double getVariantStrainFirstEfficacyChange_adult_Step3() {
        return variantStrainFirstEfficacyChange_adult_Step3;
    }

    public double getVariantStrainFirstEfficacyChange_elderly_Step3() {
        return variantStrainFirstEfficacyChange_elderly_Step3;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step3() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_young_Step3;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3;
    }

    public double getVariantStrainSecondEfficacyChange_young_Step1() {
        return variantStrainSecondEfficacyChange_young_Step1;
    }

    public double getVariantStrainSecondEfficacyChange_adult_Step1() {
        return variantStrainSecondEfficacyChange_adult_Step1;
    }

    public double getVariantStrainSecondEfficacyChange_elderly_Step1() {
        return variantStrainSecondEfficacyChange_elderly_Step1;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step1() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_young_Step1;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1;
    }

    public double getVariantStrainSecondEfficacyChange_young_Step2() {
        return variantStrainSecondEfficacyChange_young_Step2;
    }

    public double getVariantStrainSecondEfficacyChange_adult_Step2() {
        return variantStrainSecondEfficacyChange_adult_Step2;
    }

    public double getVariantStrainSecondEfficacyChange_elderly_Step2() {
        return variantStrainSecondEfficacyChange_elderly_Step2;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step2() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_young_Step2;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2;
    }

    public double getVariantStrainSecondEfficacyChange_young_Step3() {
        return variantStrainSecondEfficacyChange_young_Step3;
    }

    public double getVariantStrainSecondEfficacyChange_adult_Step3() {
        return variantStrainSecondEfficacyChange_adult_Step3;
    }

    public double getVariantStrainSecondEfficacyChange_elderly_Step3() {
        return variantStrainSecondEfficacyChange_elderly_Step3;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step3() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_young_Step3;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3;
    }

    public double getVariantStrainThirdEfficacyChange_young_Step1() {
        return variantStrainThirdEfficacyChange_young_Step1;
    }

    public double getVariantStrainThirdEfficacyChange_adult_Step1() {
        return variantStrainThirdEfficacyChange_adult_Step1;
    }

    public double getVariantStrainThirdEfficacyChange_elderly_Step1() {
        return variantStrainThirdEfficacyChange_elderly_Step1;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step1() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_young_Step1;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1;
    }

    public double getVariantStrainThirdEfficacyChange_young_Step2() {
        return variantStrainThirdEfficacyChange_young_Step2;
    }

    public double getVariantStrainThirdEfficacyChange_adult_Step2() {
        return variantStrainThirdEfficacyChange_adult_Step2;
    }

    public double getVariantStrainThirdEfficacyChange_elderly_Step2() {
        return variantStrainThirdEfficacyChange_elderly_Step2;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step2() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_young_Step2;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2;
    }

    public double getVariantStrainThirdEfficacyChange_young_Step3() {
        return variantStrainThirdEfficacyChange_young_Step3;
    }

    public double getVariantStrainThirdEfficacyChange_adult_Step3() {
        return variantStrainThirdEfficacyChange_adult_Step3;
    }

    public double getVariantStrainThirdEfficacyChange_elderly_Step3() {
        return variantStrainThirdEfficacyChange_elderly_Step3;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step3() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_young_Step3;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3;
    }

    public double getStandardStrainFirstEfficacyChange_afterImmunityLoss_young() {
        return standardStrainFirstEfficacyChange_afterImmunityLoss_young;
    }

    public double getStandardStrainFirstEfficacyChange_afterImmunityLoss_adult() {
        return standardStrainFirstEfficacyChange_afterImmunityLoss_adult;
    }

    public double getStandardStrainFirstEfficacyChange_afterImmunityLoss_elderly() {
        return standardStrainFirstEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    }

    public int getTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly() {
        return TimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
    }

    public double getStandardStrainSecondEfficacyChange_afterImmunityLoss_young() {
        return standardStrainSecondEfficacyChange_afterImmunityLoss_young;
    }

    public double getStandardStrainSecondEfficacyChange_afterImmunityLoss_adult() {
        return standardStrainSecondEfficacyChange_afterImmunityLoss_adult;
    }

    public double getStandardStrainSecondEfficacyChange_afterImmunityLoss_elderly() {
        return standardStrainSecondEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    }

    public int getTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly() {
        return TimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
    }

    public double getStandardStrainThirdEfficacyChange_afterImmunityLoss_young() {
        return standardStrainThirdEfficacyChange_afterImmunityLoss_young;
    }

    public double getStandardStrainThirdEfficacyChange_afterImmunityLoss_adult() {
        return standardStrainThirdEfficacyChange_afterImmunityLoss_adult;
    }

    public double getStandardStrainThirdEfficacyChange_afterImmunityLoss_elderly() {
        return standardStrainThirdEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult;
    }

    public int getTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly() {
        return TimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly;
    }

    public double getVariantStrainFirstEfficacyChange_afterImmunityLoss_young() {
        return variantStrainFirstEfficacyChange_afterImmunityLoss_young;
    }

    public double getVariantStrainFirstEfficacyChange_afterImmunityLoss_adult() {
        return variantStrainFirstEfficacyChange_afterImmunityLoss_adult;
    }

    public double getVariantStrainFirstEfficacyChange_afterImmunityLoss_elderly() {
        return variantStrainFirstEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    }

    public int getTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly() {
        return TimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
    }

    public double getVariantStrainSecondEfficacyChange_afterImmunityLoss_young() {
        return variantStrainSecondEfficacyChange_afterImmunityLoss_young;
    }

    public double getVariantStrainSecondEfficacyChange_afterImmunityLoss_adult() {
        return variantStrainSecondEfficacyChange_afterImmunityLoss_adult;
    }

    public double getVariantStrainSecondEfficacyChange_afterImmunityLoss_elderly() {
        return variantStrainSecondEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    }

    public int getTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly() {
        return TimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
    }

    public double getVariantStrainThirdEfficacyChange_afterImmunityLoss_young() {
        return variantStrainThirdEfficacyChange_afterImmunityLoss_young;
    }

    public double getVariantStrainThirdEfficacyChange_afterImmunityLoss_adult() {
        return variantStrainThirdEfficacyChange_afterImmunityLoss_adult;
    }

    public double getVariantStrainThirdEfficacyChange_afterImmunityLoss_elderly() {
        return variantStrainThirdEfficacyChange_afterImmunityLoss_elderly;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult;
    }

    public int getTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly() {
        return TimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly;
    }

    public double getMildInfectiousMultiplier() {
        return mildInfectiousMultiplier;
    }

    public double getSevereInfectiousMultiplier() {
        return severeInfectiousMultiplier;
    }

    public double getHospitalisedMultiplier() {
        return hospitalisedMultiplier;
    }
}
