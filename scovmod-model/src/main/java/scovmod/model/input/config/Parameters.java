package scovmod.model.input.config;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.Objects;

public class Parameters {

    private double sToE_Mild_YoungRate_Day;
    private double sToE_Mild_AdultRate_Day;
    private double sToE_Mild_ElderlyRate_Day;
    private double sToE_Severe_YoungRate_Day;
    private double sToE_Severe_AdultRate_Day;
    private double sToE_Severe_ElderlyRate_Day;
    private double sToE_Mild_YoungRate_Night;
    private double sToE_Mild_AdultRate_Night;
    private double sToE_Mild_ElderlyRate_Night;
    private double sToE_Severe_YoungRate_Night;
    private double sToE_Severe_AdultRate_Night;
    private double sToE_Severe_ElderlyRate_Night;
    private double firstBetaFactor;
    //private double secondBetaFactor;
    private double betaFactorForLocalLockdown;
    private double eToMI_YoungRate;
    private double eToMI_AdultRate;
    private double eToMI_ElderlyRate;
    private double eToR_YoungRate;
    private double eToR_AdultRate;
    private double eToR_ElderlyRate;
    private double eToT_youngRate;
    private double eToT_adultRate;
    private double eToT_elderlyRate;
    private double miToR_YoungRate;
    private double miToR_AdultRate;
    private double miToR_ElderlyRate;
    private double miToSI_YoungRate;
    private double miToSI_AdultRate;
    private double miToSI_ElderlyRate;
    private double miToT_youngRate;
    private double miToT_adultRate;
    private double miToT_elderlyRate;
    private double siToR_YoungRate;
    private double siToR_AdultRate;
    private double siToR_ElderlyRate;
    private double siToH_YoungRate;
    private double siToH_AdultRate;
    private double siToH_ElderlyRate;
    private double siToT_youngRate;
    private double siToT_adultRate;
    private double siToT_elderlyRate;
    private double siToD_YoungRate;
    private double siToD_AdultRate;
    private double siToD_ElderlyRate;
    private double hToD_YoungRate;
    private double hToD_AdultRate;
    private double hToD_ElderlyRate;
    private double hToR_YoungRate;
    private double hToR_AdultRate;
    private double hToR_ElderlyRate;
    private double tToR_youngRate;
    private double tToR_adultRate;
    private double tToR_elderlyRate;
    private double tToH_youngRate;
    private double tToH_adultRate;
    private double tToH_elderlyRate;
    private double tToD_youngRate;
    private double tToD_adultRate;
    private double tToD_elderlyRate;
    private double rToS_youngRate;
    private double rToS_adultRate;
    private double rToS_elderlyRate;
    private double transModifier;
    private double averageTransModIndexPerCouncilArea;
    private double numSeeds;
    private double numRecoveredSeeds;
    private double partialVersusFullProtectionProp;
    private double partialVersusFullProtectionProp_variant;

    private static final String newLine = System.getProperty("line.separator");
    private static final String commaNewLine = "," + newLine;

    public static Parameters fromJSON(String jsonContent) {

        ReadContext ctx = JsonPath.parse(jsonContent);
        double sToE_Mild_YoungRate_Day = ctx.read("$.parameters.rates.sToE_Mild_YoungRate_Day");
        double sToE_Mild_AdultRate_Day = ctx.read("$.parameters.rates.sToE_Mild_AdultRate_Day");
        double sToE_Mild_ElderlyRate_Day = ctx.read("$.parameters.rates.sToE_Mild_ElderlyRate_Day");
        double sToE_Severe_YoungRate_Day = ctx.read("$.parameters.rates.sToE_Severe_YoungRate_Day");
        double sToE_Severe_AdultRate_Day = ctx.read("$.parameters.rates.sToE_Severe_AdultRate_Day");
        double sToE_Severe_ElderlyRate_Day = ctx.read("$.parameters.rates.sToE_Severe_ElderlyRate_Day");
        double sToE_Mild_YoungRate_Night = ctx.read("$.parameters.rates.sToE_Mild_YoungRate_Night");
        double sToE_Mild_AdultRate_Night = ctx.read("$.parameters.rates.sToE_Mild_AdultRate_Night");
        double sToE_Mild_ElderlyRate_Night = ctx.read("$.parameters.rates.sToE_Mild_ElderlyRate_Night");
        double sToE_Severe_YoungRate_Night = ctx.read("$.parameters.rates.sToE_Severe_YoungRate_Night");
        double sToE_Severe_AdultRate_Night = ctx.read("$.parameters.rates.sToE_Severe_AdultRate_Night");
        double sToE_Severe_ElderlyRate_Night = ctx.read("$.parameters.rates.sToE_Severe_ElderlyRate_Night");
        double firstBetaFactor = ctx.read("$.parameters.rates.firstBetaFactor");
      //  double secondBetaFactor = ctx.read("$.parameters.rates.secondBetaFactor");
        double betaFactorForLocalLockdown = ctx.read("$.parameters.rates.betaFactorForLocalLockdown");
        double eToMI_YoungRate = ctx.read("$.parameters.rates.eToMI_YoungRate");
        double eToMI_AdultRate = ctx.read("$.parameters.rates.eToMI_AdultRate");
        double eToMI_ElderlyRate = ctx.read("$.parameters.rates.eToMI_ElderlyRate");
        double eToR_YoungRate = ctx.read("$.parameters.rates.eToR_YoungRate");
        double eToR_AdultRate = ctx.read("$.parameters.rates.eToR_AdultRate");
        double eToR_ElderlyRate = ctx.read("$.parameters.rates.eToR_ElderlyRate");
        double eToT_youngRate = ctx.read("$.parameters.rates.eToT_YoungRate");
        double eToT_adultRate = ctx.read("$.parameters.rates.eToT_YoungRate");
        double eToT_elderlyRate = ctx.read("$.parameters.rates.eToT_YoungRate");
        double miToR_YoungRate = ctx.read("$.parameters.rates.miToR_YoungRate");
        double miToR_AdultRate = ctx.read("$.parameters.rates.miToR_AdultRate");
        double miToR_ElderlyRate = ctx.read("$.parameters.rates.miToR_ElderlyRate");
        double miToSI_YoungRate = ctx.read("$.parameters.rates.miToSI_YoungRate");
        double miToSI_AdultRate = ctx.read("$.parameters.rates.miToSI_AdultRate");
        double miToSI_ElderlyRate = ctx.read("$.parameters.rates.miToSI_ElderlyRate");
        double miToT_YoungRate = ctx.read("$.parameters.rates.miToT_YoungRate");
        double miToT_AdultRate = ctx.read("$.parameters.rates.miToT_AdultRate");
        double miToT_ElderlyRate = ctx.read("$.parameters.rates.miToT_ElderlyRate");
        double siToR_YoungRate = ctx.read("$.parameters.rates.siToR_YoungRate");
        double siToR_AdultRate = ctx.read("$.parameters.rates.siToR_AdultRate");
        double siToR_ElderlyRate = ctx.read("$.parameters.rates.siToR_ElderlyRate");
        double siToH_YoungRate = ctx.read("$.parameters.rates.siToH_YoungRate");
        double siToH_AdultRate = ctx.read("$.parameters.rates.siToH_AdultRate");
        double siToH_ElderlyRate = ctx.read("$.parameters.rates.siToH_ElderlyRate");
        double siToD_YoungRate = ctx.read("$.parameters.rates.siToD_YoungRate");
        double siToD_AdultRate = ctx.read("$.parameters.rates.siToD_AdultRate");
        double siToD_ElderlyRate = ctx.read("$.parameters.rates.siToD_ElderlyRate");
        double siToT_YoungRate = ctx.read("$.parameters.rates.siToT_YoungRate");
        double siToT_AdultRate = ctx.read("$.parameters.rates.siToT_AdultRate");
        double siToT_ElderlyRate = ctx.read("$.parameters.rates.siToT_ElderlyRate");
        double hToD_YoungRate = ctx.read("$.parameters.rates.hToD_YoungRate");
        double hToD_AdultRate = ctx.read("$.parameters.rates.hToD_AdultRate");
        double hToD_ElderlyRate = ctx.read("$.parameters.rates.hToD_ElderlyRate");
        double hToR_YoungRate = ctx.read("$.parameters.rates.hToR_YoungRate");
        double hToR_AdultRate = ctx.read("$.parameters.rates.hToR_AdultRate");
        double hToR_ElderlyRate = ctx.read("$.parameters.rates.hToR_ElderlyRate");
        double tToR_YoungRate = ctx.read("$.parameters.rates.tToR_YoungRate");
        double tToR_AdultRate = ctx.read("$.parameters.rates.tToR_AdultRate");
        double tToR_ElderlyRate = ctx.read("$.parameters.rates.tToR_ElderlyRate");
        double tToH_YoungRate = ctx.read("$.parameters.rates.tToH_YoungRate");
        double tToH_AdultRate = ctx.read("$.parameters.rates.tToH_AdultRate");
        double tToH_ElderlyRate = ctx.read("$.parameters.rates.tToH_ElderlyRate");
        double tToD_YoungRate = ctx.read("$.parameters.rates.tToD_YoungRate");
        double tToD_AdultRate = ctx.read("$.parameters.rates.tToD_AdultRate");
        double tToD_ElderlyRate = ctx.read("$.parameters.rates.tToD_ElderlyRate");
        double rToS_youngRate = ctx.read("$.parameters.rates.rToS_YoungRate");
        double rToS_adultRate = ctx.read("$.parameters.rates.rToS_AdultRate");
        double rToS_elderlyRate = ctx.read("$.parameters.rates.rToS_ElderlyRate");
        double transIndexModifier = ctx.read("$.parameters.transmissionMod.transmissionModIndex.transModifier");
        double averageTransModIndexPerCouncilArea = ctx.read("$.parameters.transmissionMod.transmissionModIndex.averageTransmissionModIndexPerCouncilArea");

        double numSeeds = ctx.read("$.parameters.numSeeds");
        double numRecoveredSeeds = ctx.read("$.parameters.numRecoveredSeeds");
        double partialVersusFullProtectionProp = ctx.read("$.parameters.vaccination.protectionlevel.partialVersusFullProtectionProp");
        double partialVersusFullProtectionProp_variant = ctx.read("$.parameters.vaccination.protectionlevel.partialVersusFullProtectionProp_variant");

        return new Parameters(
                sToE_Mild_YoungRate_Day,// This is actually the multiplier that helps convert Severe to Mild TODO:Rename
                sToE_Mild_AdultRate_Day,// This is actually the multiplier that helps convert Severe to Mild TODO:Rename
                sToE_Mild_ElderlyRate_Day,// This is actually the multiplier that helps convert Severe to Mild TODO:Rename
                sToE_Severe_YoungRate_Day,
                sToE_Severe_AdultRate_Day,
                sToE_Severe_ElderlyRate_Day,
                sToE_Mild_YoungRate_Night,// This is actually the multiplier that helps convert Severe to Mild TODO:Rename
                sToE_Mild_AdultRate_Night,// This is actually the multiplier that helps convert Severe to Mild TODO:Rename
                sToE_Mild_ElderlyRate_Night,// This is actually the multiplier that helps convert Severe to Mild TODO:Rename
                sToE_Severe_YoungRate_Night,
                sToE_Severe_AdultRate_Night,
                sToE_Severe_ElderlyRate_Night,
                firstBetaFactor,
               // secondBetaFactor,
                betaFactorForLocalLockdown,
                eToMI_YoungRate,
                eToMI_AdultRate,
                eToMI_ElderlyRate,
                eToR_YoungRate,
                eToR_AdultRate,
                eToR_ElderlyRate,
                eToT_youngRate,
                eToT_adultRate,
                eToT_elderlyRate,
                miToR_YoungRate,
                miToR_AdultRate,
                miToR_ElderlyRate,
                miToSI_YoungRate,
                miToSI_AdultRate,
                miToSI_ElderlyRate,
                miToT_YoungRate,
                miToT_AdultRate,
                miToT_ElderlyRate,
                siToR_YoungRate,
                siToR_AdultRate,
                siToR_ElderlyRate,
                siToH_YoungRate,
                siToH_AdultRate,
                siToH_ElderlyRate,
                siToT_YoungRate,
                siToT_AdultRate,
                siToT_ElderlyRate,
                siToD_YoungRate,
                siToD_AdultRate,
                siToD_ElderlyRate,
                hToD_YoungRate,
                hToD_AdultRate,
                hToD_ElderlyRate,
                hToR_YoungRate,
                hToR_AdultRate,
                hToR_ElderlyRate,
                tToR_YoungRate,
                tToR_AdultRate,
                tToR_ElderlyRate,
                tToH_YoungRate,
                tToH_AdultRate,
                tToH_ElderlyRate,
                tToD_YoungRate,
                tToD_AdultRate,
                tToD_ElderlyRate,
                rToS_youngRate,
                rToS_adultRate,
                rToS_elderlyRate,
                transIndexModifier,
                averageTransModIndexPerCouncilArea,
                numSeeds,
                numRecoveredSeeds,
                partialVersusFullProtectionProp,
                partialVersusFullProtectionProp_variant);
    }

    public Parameters(
            double sToE_Mild_youngRate_Day,
            double sToE_Mild_adultRate_Day,
            double sToE_Mild_elderlyRate_Day,
            double sToE_Severe_youngRate_Day,
            double sToE_Severe_adultRate_Day,
            double sToE_Severe_elderlyRate_Day,
            double sToE_Mild_youngRate_Night,
            double sToE_Mild_adultRate_Night,
            double sToE_Mild_elderlyRate_Night,
            double sToE_Severe_youngRate_Night,
            double sToE_Severe_adultRate_Night,
            double sToE_Severe_elderlyRate_Night,
            double firstBetaFactor,
           // double secondBetaFactor,
            double betaFactorForLocalLockdown,
            double eToMI_youngRate,
            double eToMI_adultRate,
            double eToMI_elderlyRate,
            double eToR_youngRate,
            double eToR_adultRate,
            double eToR_elderlyRate,
            double eToT_youngRate,
            double eToT_adultRate,
            double eToT_elderlyRate,
            double miToR_youngRate,
            double miToR_adultRate,
            double miToR_elderlyRate,
            double miToSI_youngRate,
            double miToSI_adultRate,
            double miToSI_elderlyRate,
            double miToT_youngRate,
            double miToT_adultRate,
            double miToT_elderlyRate,
            double siToR_youngRate,
            double siToR_adultRate,
            double siToR_elderlyRate,
            double siToH_youngRate,
            double siToH_adultRate,
            double siToH_elderlyRate,
            double siToT_youngRate,
            double siToT_adultRate,
            double siToT_elderlyRate,
            double siToD_youngRate,
            double siToD_adultRate,
            double siToD_elderlyRate,
            double hToD_youngRate,
            double hToD_adultRate,
            double hToD_elderlyRate,
            double hToR_youngRate,
            double hToR_adultRate,
            double hToR_elderlyRate,
            double tToR_youngRate,
            double tToR_adultRate,
            double tToR_elderlyRate,
            double tToH_youngRate,
            double tToH_adultRate,
            double tToH_elderlyRate,
            double tToD_youngRate,
            double tToD_adultRate,
            double tToD_elderlyRate,
            double rToS_youngRate,
            double rToS_adultRate,
            double rToS_elderlyRate,
            double transIndexModifier,
            double averageTransModIndexPerCouncilArea,
            double numSeeds,
            double numRecoveredSeeds,
            double partialVersusFullProtectionProp,
            double partialVersusFullProtectionProp_variant) {
        sToE_Mild_YoungRate_Day = sToE_Mild_youngRate_Day;
        sToE_Mild_AdultRate_Day = sToE_Mild_adultRate_Day;
        sToE_Mild_ElderlyRate_Day = sToE_Mild_elderlyRate_Day;
        sToE_Severe_YoungRate_Day = sToE_Severe_youngRate_Day;
        sToE_Severe_AdultRate_Day = sToE_Severe_adultRate_Day;
        sToE_Severe_ElderlyRate_Day = sToE_Severe_elderlyRate_Day;
        sToE_Mild_YoungRate_Night = sToE_Mild_youngRate_Night;
        sToE_Mild_AdultRate_Night = sToE_Mild_adultRate_Night;
        sToE_Mild_ElderlyRate_Night = sToE_Mild_elderlyRate_Night;
        sToE_Severe_YoungRate_Night = sToE_Severe_youngRate_Night;
        sToE_Severe_AdultRate_Night = sToE_Severe_adultRate_Night;
        sToE_Severe_ElderlyRate_Night = sToE_Severe_elderlyRate_Night;
        this.firstBetaFactor = firstBetaFactor;
        //this.secondBetaFactor = secondBetaFactor;
        this.betaFactorForLocalLockdown = betaFactorForLocalLockdown;
        eToMI_YoungRate = eToMI_youngRate;
        eToMI_AdultRate = eToMI_adultRate;
        eToMI_ElderlyRate = eToMI_elderlyRate;
        eToR_YoungRate = eToR_youngRate;
        eToR_AdultRate = eToR_adultRate;
        eToR_ElderlyRate = eToR_elderlyRate;
        this.eToT_youngRate = eToT_youngRate;
        this.eToT_adultRate = eToT_adultRate;
        this.eToT_elderlyRate = eToT_elderlyRate;
        miToR_YoungRate = miToR_youngRate;
        miToR_AdultRate = miToR_adultRate;
        miToR_ElderlyRate = miToR_elderlyRate;
        miToSI_YoungRate = miToSI_youngRate;
        miToSI_AdultRate = miToSI_adultRate;
        miToSI_ElderlyRate = miToSI_elderlyRate;
        this.miToT_youngRate = miToT_youngRate;
        this.miToT_adultRate = miToT_adultRate;
        this.miToT_elderlyRate = miToT_elderlyRate;
        siToR_YoungRate = siToR_youngRate;
        siToR_AdultRate = siToR_adultRate;
        siToR_ElderlyRate = siToR_elderlyRate;
        siToH_YoungRate = siToH_youngRate;
        siToH_AdultRate = siToH_adultRate;
        siToH_ElderlyRate = siToH_elderlyRate;
        this.siToT_youngRate = siToT_youngRate;
        this.siToT_adultRate = siToT_adultRate;
        this.siToT_elderlyRate = siToT_elderlyRate;
        siToD_YoungRate = siToD_youngRate;
        siToD_AdultRate = siToD_adultRate;
        siToD_ElderlyRate = siToD_elderlyRate;
        hToD_YoungRate = hToD_youngRate;
        hToD_AdultRate = hToD_adultRate;
        hToD_ElderlyRate = hToD_elderlyRate;
        hToR_YoungRate = hToR_youngRate;
        hToR_AdultRate = hToR_adultRate;
        hToR_ElderlyRate = hToR_elderlyRate;
        this.tToR_youngRate = tToR_youngRate;
        this.tToR_adultRate = tToR_adultRate;
        this.tToR_elderlyRate = tToR_elderlyRate;
        this.tToH_youngRate = tToH_youngRate;
        this.tToH_adultRate = tToH_adultRate;
        this.tToH_elderlyRate = tToH_elderlyRate;
        this.tToD_youngRate = tToD_youngRate;
        this.tToD_adultRate = tToD_adultRate;
        this.tToD_elderlyRate = tToD_elderlyRate;
        this.rToS_youngRate = rToS_youngRate;
        this.rToS_adultRate = rToS_adultRate;
        this.rToS_elderlyRate = rToS_elderlyRate;
        this.transModifier = transIndexModifier;
        this.averageTransModIndexPerCouncilArea = averageTransModIndexPerCouncilArea;
        this.numSeeds = numSeeds;
        this.numRecoveredSeeds = numRecoveredSeeds;
        this.partialVersusFullProtectionProp = partialVersusFullProtectionProp;
        this.partialVersusFullProtectionProp_variant = partialVersusFullProtectionProp_variant;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameters that = (Parameters) o;
        return Double.compare(that.sToE_Mild_YoungRate_Day, sToE_Mild_YoungRate_Day) == 0 && Double.compare(that.sToE_Mild_AdultRate_Day, sToE_Mild_AdultRate_Day) == 0 && Double.compare(that.sToE_Mild_ElderlyRate_Day, sToE_Mild_ElderlyRate_Day) == 0 && Double.compare(that.sToE_Severe_YoungRate_Day, sToE_Severe_YoungRate_Day) == 0 && Double.compare(that.sToE_Severe_AdultRate_Day, sToE_Severe_AdultRate_Day) == 0 && Double.compare(that.sToE_Severe_ElderlyRate_Day, sToE_Severe_ElderlyRate_Day) == 0 && Double.compare(that.sToE_Mild_YoungRate_Night, sToE_Mild_YoungRate_Night) == 0 && Double.compare(that.sToE_Mild_AdultRate_Night, sToE_Mild_AdultRate_Night) == 0 && Double.compare(that.sToE_Mild_ElderlyRate_Night, sToE_Mild_ElderlyRate_Night) == 0 && Double.compare(that.sToE_Severe_YoungRate_Night, sToE_Severe_YoungRate_Night) == 0 && Double.compare(that.sToE_Severe_AdultRate_Night, sToE_Severe_AdultRate_Night) == 0 && Double.compare(that.sToE_Severe_ElderlyRate_Night, sToE_Severe_ElderlyRate_Night) == 0 && Double.compare(that.firstBetaFactor, firstBetaFactor) == 0 && Double.compare(that.betaFactorForLocalLockdown, betaFactorForLocalLockdown) == 0 && Double.compare(that.eToMI_YoungRate, eToMI_YoungRate) == 0 && Double.compare(that.eToMI_AdultRate, eToMI_AdultRate) == 0 && Double.compare(that.eToMI_ElderlyRate, eToMI_ElderlyRate) == 0 && Double.compare(that.eToR_YoungRate, eToR_YoungRate) == 0 && Double.compare(that.eToR_AdultRate, eToR_AdultRate) == 0 && Double.compare(that.eToR_ElderlyRate, eToR_ElderlyRate) == 0 && Double.compare(that.eToT_youngRate, eToT_youngRate) == 0 && Double.compare(that.eToT_adultRate, eToT_adultRate) == 0 && Double.compare(that.eToT_elderlyRate, eToT_elderlyRate) == 0 && Double.compare(that.miToR_YoungRate, miToR_YoungRate) == 0 && Double.compare(that.miToR_AdultRate, miToR_AdultRate) == 0 && Double.compare(that.miToR_ElderlyRate, miToR_ElderlyRate) == 0 && Double.compare(that.miToSI_YoungRate, miToSI_YoungRate) == 0 && Double.compare(that.miToSI_AdultRate, miToSI_AdultRate) == 0 && Double.compare(that.miToSI_ElderlyRate, miToSI_ElderlyRate) == 0 && Double.compare(that.miToT_youngRate, miToT_youngRate) == 0 && Double.compare(that.miToT_adultRate, miToT_adultRate) == 0 && Double.compare(that.miToT_elderlyRate, miToT_elderlyRate) == 0 && Double.compare(that.siToR_YoungRate, siToR_YoungRate) == 0 && Double.compare(that.siToR_AdultRate, siToR_AdultRate) == 0 && Double.compare(that.siToR_ElderlyRate, siToR_ElderlyRate) == 0 && Double.compare(that.siToH_YoungRate, siToH_YoungRate) == 0 && Double.compare(that.siToH_AdultRate, siToH_AdultRate) == 0 && Double.compare(that.siToH_ElderlyRate, siToH_ElderlyRate) == 0 && Double.compare(that.siToT_youngRate, siToT_youngRate) == 0 && Double.compare(that.siToT_adultRate, siToT_adultRate) == 0 && Double.compare(that.siToT_elderlyRate, siToT_elderlyRate) == 0 && Double.compare(that.siToD_YoungRate, siToD_YoungRate) == 0 && Double.compare(that.siToD_AdultRate, siToD_AdultRate) == 0 && Double.compare(that.siToD_ElderlyRate, siToD_ElderlyRate) == 0 && Double.compare(that.hToD_YoungRate, hToD_YoungRate) == 0 && Double.compare(that.hToD_AdultRate, hToD_AdultRate) == 0 && Double.compare(that.hToD_ElderlyRate, hToD_ElderlyRate) == 0 && Double.compare(that.hToR_YoungRate, hToR_YoungRate) == 0 && Double.compare(that.hToR_AdultRate, hToR_AdultRate) == 0 && Double.compare(that.hToR_ElderlyRate, hToR_ElderlyRate) == 0 && Double.compare(that.tToR_youngRate, tToR_youngRate) == 0 && Double.compare(that.tToR_adultRate, tToR_adultRate) == 0 && Double.compare(that.tToR_elderlyRate, tToR_elderlyRate) == 0 && Double.compare(that.tToH_youngRate, tToH_youngRate) == 0 && Double.compare(that.tToH_adultRate, tToH_adultRate) == 0 && Double.compare(that.tToH_elderlyRate, tToH_elderlyRate) == 0 && Double.compare(that.tToD_youngRate, tToD_youngRate) == 0 && Double.compare(that.tToD_adultRate, tToD_adultRate) == 0 && Double.compare(that.tToD_elderlyRate, tToD_elderlyRate) == 0 && Double.compare(that.rToS_youngRate, rToS_youngRate) == 0 && Double.compare(that.rToS_adultRate, rToS_adultRate) == 0 && Double.compare(that.rToS_elderlyRate, rToS_elderlyRate) == 0 && Double.compare(that.transModifier, transModifier) == 0 && Double.compare(that.averageTransModIndexPerCouncilArea, averageTransModIndexPerCouncilArea) == 0 && Double.compare(that.numSeeds, numSeeds) == 0 && Double.compare(that.numRecoveredSeeds, numRecoveredSeeds) == 0 && Double.compare(that.partialVersusFullProtectionProp, partialVersusFullProtectionProp) == 0 && Double.compare(that.partialVersusFullProtectionProp_variant, partialVersusFullProtectionProp_variant) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sToE_Mild_YoungRate_Day, sToE_Mild_AdultRate_Day, sToE_Mild_ElderlyRate_Day, sToE_Severe_YoungRate_Day, sToE_Severe_AdultRate_Day, sToE_Severe_ElderlyRate_Day, sToE_Mild_YoungRate_Night, sToE_Mild_AdultRate_Night, sToE_Mild_ElderlyRate_Night, sToE_Severe_YoungRate_Night, sToE_Severe_AdultRate_Night, sToE_Severe_ElderlyRate_Night, firstBetaFactor, betaFactorForLocalLockdown, eToMI_YoungRate, eToMI_AdultRate, eToMI_ElderlyRate, eToR_YoungRate, eToR_AdultRate, eToR_ElderlyRate, eToT_youngRate, eToT_adultRate, eToT_elderlyRate, miToR_YoungRate, miToR_AdultRate, miToR_ElderlyRate, miToSI_YoungRate, miToSI_AdultRate, miToSI_ElderlyRate, miToT_youngRate, miToT_adultRate, miToT_elderlyRate, siToR_YoungRate, siToR_AdultRate, siToR_ElderlyRate, siToH_YoungRate, siToH_AdultRate, siToH_ElderlyRate, siToT_youngRate, siToT_adultRate, siToT_elderlyRate, siToD_YoungRate, siToD_AdultRate, siToD_ElderlyRate, hToD_YoungRate, hToD_AdultRate, hToD_ElderlyRate, hToR_YoungRate, hToR_AdultRate, hToR_ElderlyRate, tToR_youngRate, tToR_adultRate, tToR_elderlyRate, tToH_youngRate, tToH_adultRate, tToH_elderlyRate, tToD_youngRate, tToD_adultRate, tToD_elderlyRate, rToS_youngRate, rToS_adultRate, rToS_elderlyRate, transModifier, averageTransModIndexPerCouncilArea, numSeeds, numRecoveredSeeds, partialVersusFullProtectionProp, partialVersusFullProtectionProp_variant);
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "sToE_Mild_YoungRate_Day=" + sToE_Mild_YoungRate_Day +
                ", sToE_Mild_AdultRate_Day=" + sToE_Mild_AdultRate_Day +
                ", sToE_Mild_ElderlyRate_Day=" + sToE_Mild_ElderlyRate_Day +
                ", sToE_Severe_YoungRate_Day=" + sToE_Severe_YoungRate_Day +
                ", sToE_Severe_AdultRate_Day=" + sToE_Severe_AdultRate_Day +
                ", sToE_Severe_ElderlyRate_Day=" + sToE_Severe_ElderlyRate_Day +
                ", sToE_Mild_YoungRate_Night=" + sToE_Mild_YoungRate_Night +
                ", sToE_Mild_AdultRate_Night=" + sToE_Mild_AdultRate_Night +
                ", sToE_Mild_ElderlyRate_Night=" + sToE_Mild_ElderlyRate_Night +
                ", sToE_Severe_YoungRate_Night=" + sToE_Severe_YoungRate_Night +
                ", sToE_Severe_AdultRate_Night=" + sToE_Severe_AdultRate_Night +
                ", sToE_Severe_ElderlyRate_Night=" + sToE_Severe_ElderlyRate_Night +
                ", firstBetaFactor=" + firstBetaFactor +
                ", betaFactorForLocalLockdown=" + betaFactorForLocalLockdown +
                ", eToMI_YoungRate=" + eToMI_YoungRate +
                ", eToMI_AdultRate=" + eToMI_AdultRate +
                ", eToMI_ElderlyRate=" + eToMI_ElderlyRate +
                ", eToR_YoungRate=" + eToR_YoungRate +
                ", eToR_AdultRate=" + eToR_AdultRate +
                ", eToR_ElderlyRate=" + eToR_ElderlyRate +
                ", eToT_youngRate=" + eToT_youngRate +
                ", eToT_adultRate=" + eToT_adultRate +
                ", eToT_elderlyRate=" + eToT_elderlyRate +
                ", miToR_YoungRate=" + miToR_YoungRate +
                ", miToR_AdultRate=" + miToR_AdultRate +
                ", miToR_ElderlyRate=" + miToR_ElderlyRate +
                ", miToSI_YoungRate=" + miToSI_YoungRate +
                ", miToSI_AdultRate=" + miToSI_AdultRate +
                ", miToSI_ElderlyRate=" + miToSI_ElderlyRate +
                ", miToT_youngRate=" + miToT_youngRate +
                ", miToT_adultRate=" + miToT_adultRate +
                ", miToT_elderlyRate=" + miToT_elderlyRate +
                ", siToR_YoungRate=" + siToR_YoungRate +
                ", siToR_AdultRate=" + siToR_AdultRate +
                ", siToR_ElderlyRate=" + siToR_ElderlyRate +
                ", siToH_YoungRate=" + siToH_YoungRate +
                ", siToH_AdultRate=" + siToH_AdultRate +
                ", siToH_ElderlyRate=" + siToH_ElderlyRate +
                ", siToT_youngRate=" + siToT_youngRate +
                ", siToT_adultRate=" + siToT_adultRate +
                ", siToT_elderlyRate=" + siToT_elderlyRate +
                ", siToD_YoungRate=" + siToD_YoungRate +
                ", siToD_AdultRate=" + siToD_AdultRate +
                ", siToD_ElderlyRate=" + siToD_ElderlyRate +
                ", hToD_YoungRate=" + hToD_YoungRate +
                ", hToD_AdultRate=" + hToD_AdultRate +
                ", hToD_ElderlyRate=" + hToD_ElderlyRate +
                ", hToR_YoungRate=" + hToR_YoungRate +
                ", hToR_AdultRate=" + hToR_AdultRate +
                ", hToR_ElderlyRate=" + hToR_ElderlyRate +
                ", tToR_youngRate=" + tToR_youngRate +
                ", tToR_adultRate=" + tToR_adultRate +
                ", tToR_elderlyRate=" + tToR_elderlyRate +
                ", tToH_youngRate=" + tToH_youngRate +
                ", tToH_adultRate=" + tToH_adultRate +
                ", tToH_elderlyRate=" + tToH_elderlyRate +
                ", tToD_youngRate=" + tToD_youngRate +
                ", tToD_adultRate=" + tToD_adultRate +
                ", tToD_elderlyRate=" + tToD_elderlyRate +
                ", rToS_youngRate=" + rToS_youngRate +
                ", rToS_adultRate=" + rToS_adultRate +
                ", rToS_elderlyRate=" + rToS_elderlyRate +
                ", transModifier=" + transModifier +
                ", averageTransModIndexPerCouncilArea=" + averageTransModIndexPerCouncilArea +
                ", numSeeds=" + numSeeds +
                ", numRecoveredSeeds=" + numRecoveredSeeds +
                ", partialVersusFullProtectionProp=" + partialVersusFullProtectionProp +
                ", partialVersusFullProtectionProp_variant=" + partialVersusFullProtectionProp_variant +
                '}';
    }

    public double getsToE_Mild_YoungRate_Day() {
        return sToE_Mild_YoungRate_Day;
    }

    public double getsToE_Mild_AdultRate_Day() {
        return sToE_Mild_AdultRate_Day;
    }

    public double getsToE_Mild_ElderlyRate_Day() {
        return sToE_Mild_ElderlyRate_Day;
    }

    public double getsToE_Severe_YoungRate_Day() {
        return sToE_Severe_YoungRate_Day;
    }

    public double getsToE_Severe_AdultRate_Day() {
        return sToE_Severe_AdultRate_Day;
    }

    public double getsToE_Severe_ElderlyRate_Day() {
        return sToE_Severe_ElderlyRate_Day;
    }

    public double getsToE_Mild_YoungRate_Night() {
        return sToE_Mild_YoungRate_Night;
    }

    public double getsToE_Mild_AdultRate_Night() {
        return sToE_Mild_AdultRate_Night;
    }

    public double getsToE_Mild_ElderlyRate_Night() {
        return sToE_Mild_ElderlyRate_Night;
    }

    public double getsToE_Severe_YoungRate_Night() {
        return sToE_Severe_YoungRate_Night;
    }

    public double getsToE_Severe_AdultRate_Night() {
        return sToE_Severe_AdultRate_Night;
    }

    public double getsToE_Severe_ElderlyRate_Night() {
        return sToE_Severe_ElderlyRate_Night;
    }

    public double geteToMI_YoungRate() {
        return eToMI_YoungRate;
    }

    public double geteToMI_AdultRate() {
        return eToMI_AdultRate;
    }

    public double geteToMI_ElderlyRate() {
        return eToMI_ElderlyRate;
    }

    public double geteToR_YoungRate() {
        return eToR_YoungRate;
    }

    public double geteToR_AdultRate() {
        return eToR_AdultRate;
    }

    public double geteToR_ElderlyRate() {
        return eToR_ElderlyRate;
    }

    public double getMiToR_YoungRate() {
        return miToR_YoungRate;
    }

    public double getMiToR_AdultRate() {
        return miToR_AdultRate;
    }

    public double getMiToR_ElderlyRate() {
        return miToR_ElderlyRate;
    }

    public double getMiToSI_YoungRate() {
        return miToSI_YoungRate;
    }

    public double getMiToSI_AdultRate() {
        return miToSI_AdultRate;
    }

    public double getMiToSI_ElderlyRate() {
        return miToSI_ElderlyRate;
    }

    public double getSiToR_YoungRate() {
        return siToR_YoungRate;
    }

    public double getSiToR_AdultRate() {
        return siToR_AdultRate;
    }

    public double getSiToR_ElderlyRate() {
        return siToR_ElderlyRate;
    }

    public double getSiToH_YoungRate() {
        return siToH_YoungRate;
    }

    public double getSiToH_AdultRate() {
        return siToH_AdultRate;
    }

    public double getSiToH_ElderlyRate() {
        return siToH_ElderlyRate;
    }

    public double getSiToD_YoungRate() {
        return siToD_YoungRate;
    }

    public double getSiToD_AdultRate() {
        return siToD_AdultRate;
    }

    public double getSiToD_ElderlyRate() {
        return siToD_ElderlyRate;
    }

    public double gethToD_YoungRate() {
        return hToD_YoungRate;
    }

    public double gethToD_AdultRate() {
        return hToD_AdultRate;
    }

    public double gethToD_ElderlyRate() {
        return hToD_ElderlyRate;
    }

    public double gethToR_YoungRate() {
        return hToR_YoungRate;
    }

    public double gethToR_AdultRate() {
        return hToR_AdultRate;
    }

    public double gethToR_ElderlyRate() {
        return hToR_ElderlyRate;
    }

    public double getFirstBetaFactor() { return firstBetaFactor; }

   // public double getSecondBetaFactor() { return secondBetaFactor; }

    public double getBetaFactorForLocalLockdown() { return betaFactorForLocalLockdown; }

    public double geteToT_youngRate() { return eToT_youngRate; }

    public double geteToT_adultRate() { return eToT_adultRate; }

    public double geteToT_elderlyRate() { return eToT_elderlyRate; }

    public double getMiToT_YoungRate() { return miToT_youngRate; }

    public double getMiToT_AdultRate() { return miToT_adultRate; }

    public double getMiToT_ElderlyRate() { return miToT_elderlyRate; }

    public double getMiToT_youngRate() { return miToT_youngRate; }

    public double getMiToT_adultRate() { return miToT_adultRate; }

    public double getMiToT_elderlyRate() { return miToT_elderlyRate; }

    public double getSiToT_YoungRate() { return siToT_youngRate; }

    public double getSiToT_AdultRate() { return siToT_adultRate; }

    public double getSiToT_ElderlyRate() { return siToT_elderlyRate; }

    public double getSiToT_youngRate() { return siToT_youngRate; }

    public double getSiToT_adultRate() { return siToT_adultRate; }

    public double getSiToT_elderlyRate() { return siToT_elderlyRate; }

    public double gettToR_YoungRate() { return tToR_youngRate; }

    public double gettToR_AdultRate() { return tToR_adultRate; }

    public double gettToR_ElderlyRate() { return tToR_elderlyRate; }

    public double gettToH_YoungRate() { return tToH_youngRate; }

    public double gettToH_AdultRate() { return tToH_adultRate; }

    public double gettToH_ElderlyRate() { return tToH_elderlyRate; }

    public double gettToD_YoungRate() { return tToD_youngRate; }

    public double gettToD_AdultRate() { return tToD_adultRate; }

    public double gettToD_ElderlyRate() { return tToD_elderlyRate; }

    public double gettToR_youngRate() { return tToR_youngRate; }

    public double gettToR_adultRate() { return tToR_adultRate; }

    public double gettToR_elderlyRate() { return tToR_elderlyRate; }

    public double gettToH_youngRate() { return tToH_youngRate; }

    public double gettToH_adultRate() { return tToH_adultRate; }

    public double gettToH_elderlyRate() { return tToH_elderlyRate; }

    public double gettToD_youngRate() { return tToD_youngRate; }

    public double gettToD_adultRate() { return tToD_adultRate; }

    public double gettToD_elderlyRate() { return tToD_elderlyRate; }

    public double getrToS_youngRate() {
        return rToS_youngRate;
    }

    public double getrToS_adultRate() {
        return rToS_adultRate;
    }

    public double getrToS_elderlyRate() {
        return rToS_elderlyRate;
    }

    public double getTransIndexModifier() { return transModifier; }

    public double getAverageTransIndexPerCouncilArea() { return averageTransModIndexPerCouncilArea; }

    public double getNumSeeds() { return numSeeds; }

    public double getNumRecoveredSeeds() { return numRecoveredSeeds;}

    public double getPartialVersusFullProtectionProp() { return partialVersusFullProtectionProp; }

    public double getPartialVersusFullProtectionProp_variant() {return partialVersusFullProtectionProp_variant;}

}


