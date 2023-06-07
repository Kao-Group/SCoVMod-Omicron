package scovmod.model.input.config;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.Objects;

public class CovidVariantParameters {

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

    private Parameters params; //To be able to feed in standard params - making equivalent variant version multipliers


    private static final String newLine = System.getProperty("line.separator");
    private static final String commaNewLine = "," + newLine;

    public static CovidVariantParameters fromJSON(String jsonContent, Parameters params) {

        ReadContext ctx = JsonPath.parse(jsonContent);
        double sToE_Mild_YoungRate_Day = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Mild_YoungRate_Day");
        double sToE_Mild_AdultRate_Day = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Mild_AdultRate_Day");
        double sToE_Mild_ElderlyRate_Day = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Mild_ElderlyRate_Day");
        double sToE_Severe_YoungRate_Day = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Severe_YoungRate_Day");
        double sToE_Severe_AdultRate_Day = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Severe_AdultRate_Day");
        double sToE_Severe_ElderlyRate_Day = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Severe_ElderlyRate_Day");
        double sToE_Mild_YoungRate_Night = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Mild_YoungRate_Night");
        double sToE_Mild_AdultRate_Night = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Mild_AdultRate_Night");
        double sToE_Mild_ElderlyRate_Night = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Mild_ElderlyRate_Night");
        double sToE_Severe_YoungRate_Night = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Severe_YoungRate_Night");
        double sToE_Severe_AdultRate_Night = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Severe_AdultRate_Night");
        double sToE_Severe_ElderlyRate_Night = ctx.read("$.configParameters.feature.covidVariant.rates.sToE_Severe_ElderlyRate_Night");
        double eToMI_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToMI_YoungRate");
        double eToMI_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToMI_AdultRate");
        double eToMI_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToMI_ElderlyRate");
        double eToR_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToR_YoungRate");
        double eToR_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToR_AdultRate");
        double eToR_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToR_ElderlyRate");
        double eToT_youngRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToT_YoungRate");
        double eToT_adultRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToT_YoungRate");
        double eToT_elderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.eToT_YoungRate");
        double miToR_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToR_YoungRate");
        double miToR_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToR_AdultRate");
        double miToR_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToR_ElderlyRate");
        double miToSI_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToSI_YoungRate");
        double miToSI_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToSI_AdultRate");
        double miToSI_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToSI_ElderlyRate");
        double miToT_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToT_YoungRate");
        double miToT_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToT_AdultRate");
        double miToT_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.miToT_ElderlyRate");
        double siToR_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToR_YoungRate");
        double siToR_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToR_AdultRate");
        double siToR_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToR_ElderlyRate");
        double siToH_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToH_YoungRate");
        double siToH_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToH_AdultRate");
        double siToH_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToH_ElderlyRate");
        double siToD_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToD_YoungRate");
        double siToD_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToD_AdultRate");
        double siToD_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToD_ElderlyRate");
        double siToT_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToT_YoungRate");
        double siToT_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToT_AdultRate");
        double siToT_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.siToT_ElderlyRate");
        double hToD_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.hToD_YoungRate");
        double hToD_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.hToD_AdultRate");
        double hToD_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.hToD_ElderlyRate");
        double hToR_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.hToR_YoungRate");
        double hToR_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.hToR_AdultRate");
        double hToR_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.hToR_ElderlyRate");
        double tToR_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToR_YoungRate");
        double tToR_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToR_AdultRate");
        double tToR_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToR_ElderlyRate");
        double tToH_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToH_YoungRate");
        double tToH_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToH_AdultRate");
        double tToH_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToH_ElderlyRate");
        double tToD_YoungRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToD_YoungRate");
        double tToD_AdultRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToD_AdultRate");
        double tToD_ElderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.tToD_ElderlyRate");
        double rToS_youngRate = ctx.read("$.configParameters.feature.covidVariant.rates.rToS_YoungRate");
        double rToS_adultRate = ctx.read("$.configParameters.feature.covidVariant.rates.rToS_AdultRate");
        double rToS_elderlyRate = ctx.read("$.configParameters.feature.covidVariant.rates.rToS_ElderlyRate");

        return new CovidVariantParameters(
                params,
                // NNB TODO Need to change this to take in fitted parameters and convert accordingly?
                // NNB TODO For example use fitted sToE_MI_Factor. Or are they precomputed and put in config file?
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
                rToS_elderlyRate);
    }

    public CovidVariantParameters(
            Parameters params,
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
            double rToS_elderlyRate) {
        sToE_Mild_YoungRate_Day = sToE_Mild_youngRate_Day*params.getsToE_Mild_YoungRate_Day();
        sToE_Mild_AdultRate_Day = sToE_Mild_adultRate_Day*params.getsToE_Mild_AdultRate_Day();;
        sToE_Mild_ElderlyRate_Day = sToE_Mild_elderlyRate_Day*params.getsToE_Mild_ElderlyRate_Day();
        sToE_Severe_YoungRate_Day = sToE_Severe_youngRate_Day*params.getsToE_Severe_YoungRate_Day();
        sToE_Severe_AdultRate_Day = sToE_Severe_adultRate_Day*params.getsToE_Severe_AdultRate_Day();
        sToE_Severe_ElderlyRate_Day = sToE_Severe_elderlyRate_Day*params.getsToE_Severe_ElderlyRate_Day();
        sToE_Mild_YoungRate_Night = sToE_Mild_youngRate_Night*params.getsToE_Mild_YoungRate_Night();
        sToE_Mild_AdultRate_Night = sToE_Mild_adultRate_Night*params.getsToE_Mild_AdultRate_Night();
        sToE_Mild_ElderlyRate_Night = sToE_Mild_elderlyRate_Night*params.getsToE_Mild_ElderlyRate_Night();
        sToE_Severe_YoungRate_Night = sToE_Severe_youngRate_Night*params.getsToE_Severe_YoungRate_Night();
        sToE_Severe_AdultRate_Night = sToE_Severe_adultRate_Night*params.getsToE_Severe_AdultRate_Night();
        sToE_Severe_ElderlyRate_Night = sToE_Severe_elderlyRate_Night*params.getsToE_Severe_ElderlyRate_Night();
        eToMI_YoungRate = eToMI_youngRate*params.geteToMI_YoungRate();
        eToMI_AdultRate = eToMI_adultRate*params.geteToMI_AdultRate();
        eToMI_ElderlyRate = eToMI_elderlyRate*params.geteToMI_ElderlyRate();
        eToR_YoungRate = eToR_youngRate;
        eToR_AdultRate = eToR_adultRate;
        eToR_ElderlyRate = eToR_elderlyRate;
        this.eToT_youngRate = eToT_youngRate;
        this.eToT_adultRate = eToT_adultRate;
        this.eToT_elderlyRate = eToT_elderlyRate;
        miToR_YoungRate = miToR_youngRate*params.getMiToR_YoungRate();
        miToR_AdultRate = miToR_adultRate*params.getMiToR_AdultRate();
        miToR_ElderlyRate = miToR_elderlyRate*params.getMiToR_ElderlyRate();
        miToSI_YoungRate = miToSI_youngRate*params.getMiToSI_YoungRate();
        miToSI_AdultRate = miToSI_adultRate*params.getMiToSI_AdultRate();
        miToSI_ElderlyRate = miToSI_elderlyRate*params.getMiToSI_ElderlyRate();
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CovidVariantParameters that = (CovidVariantParameters) o;
        return Double.compare(that.sToE_Mild_YoungRate_Day, sToE_Mild_YoungRate_Day) == 0 && Double.compare(that.sToE_Mild_AdultRate_Day, sToE_Mild_AdultRate_Day) == 0 && Double.compare(that.sToE_Mild_ElderlyRate_Day, sToE_Mild_ElderlyRate_Day) == 0 && Double.compare(that.sToE_Severe_YoungRate_Day, sToE_Severe_YoungRate_Day) == 0 && Double.compare(that.sToE_Severe_AdultRate_Day, sToE_Severe_AdultRate_Day) == 0 && Double.compare(that.sToE_Severe_ElderlyRate_Day, sToE_Severe_ElderlyRate_Day) == 0 && Double.compare(that.sToE_Mild_YoungRate_Night, sToE_Mild_YoungRate_Night) == 0 && Double.compare(that.sToE_Mild_AdultRate_Night, sToE_Mild_AdultRate_Night) == 0 && Double.compare(that.sToE_Mild_ElderlyRate_Night, sToE_Mild_ElderlyRate_Night) == 0 && Double.compare(that.sToE_Severe_YoungRate_Night, sToE_Severe_YoungRate_Night) == 0 && Double.compare(that.sToE_Severe_AdultRate_Night, sToE_Severe_AdultRate_Night) == 0 && Double.compare(that.sToE_Severe_ElderlyRate_Night, sToE_Severe_ElderlyRate_Night) == 0 && Double.compare(that.eToMI_YoungRate, eToMI_YoungRate) == 0 && Double.compare(that.eToMI_AdultRate, eToMI_AdultRate) == 0 && Double.compare(that.eToMI_ElderlyRate, eToMI_ElderlyRate) == 0 && Double.compare(that.eToR_YoungRate, eToR_YoungRate) == 0 && Double.compare(that.eToR_AdultRate, eToR_AdultRate) == 0 && Double.compare(that.eToR_ElderlyRate, eToR_ElderlyRate) == 0 && Double.compare(that.eToT_youngRate, eToT_youngRate) == 0 && Double.compare(that.eToT_adultRate, eToT_adultRate) == 0 && Double.compare(that.eToT_elderlyRate, eToT_elderlyRate) == 0 && Double.compare(that.miToR_YoungRate, miToR_YoungRate) == 0 && Double.compare(that.miToR_AdultRate, miToR_AdultRate) == 0 && Double.compare(that.miToR_ElderlyRate, miToR_ElderlyRate) == 0 && Double.compare(that.miToSI_YoungRate, miToSI_YoungRate) == 0 && Double.compare(that.miToSI_AdultRate, miToSI_AdultRate) == 0 && Double.compare(that.miToSI_ElderlyRate, miToSI_ElderlyRate) == 0 && Double.compare(that.miToT_youngRate, miToT_youngRate) == 0 && Double.compare(that.miToT_adultRate, miToT_adultRate) == 0 && Double.compare(that.miToT_elderlyRate, miToT_elderlyRate) == 0 && Double.compare(that.siToR_YoungRate, siToR_YoungRate) == 0 && Double.compare(that.siToR_AdultRate, siToR_AdultRate) == 0 && Double.compare(that.siToR_ElderlyRate, siToR_ElderlyRate) == 0 && Double.compare(that.siToH_YoungRate, siToH_YoungRate) == 0 && Double.compare(that.siToH_AdultRate, siToH_AdultRate) == 0 && Double.compare(that.siToH_ElderlyRate, siToH_ElderlyRate) == 0 && Double.compare(that.siToT_youngRate, siToT_youngRate) == 0 && Double.compare(that.siToT_adultRate, siToT_adultRate) == 0 && Double.compare(that.siToT_elderlyRate, siToT_elderlyRate) == 0 && Double.compare(that.siToD_YoungRate, siToD_YoungRate) == 0 && Double.compare(that.siToD_AdultRate, siToD_AdultRate) == 0 && Double.compare(that.siToD_ElderlyRate, siToD_ElderlyRate) == 0 && Double.compare(that.hToD_YoungRate, hToD_YoungRate) == 0 && Double.compare(that.hToD_AdultRate, hToD_AdultRate) == 0 && Double.compare(that.hToD_ElderlyRate, hToD_ElderlyRate) == 0 && Double.compare(that.hToR_YoungRate, hToR_YoungRate) == 0 && Double.compare(that.hToR_AdultRate, hToR_AdultRate) == 0 && Double.compare(that.hToR_ElderlyRate, hToR_ElderlyRate) == 0 && Double.compare(that.tToR_youngRate, tToR_youngRate) == 0 && Double.compare(that.tToR_adultRate, tToR_adultRate) == 0 && Double.compare(that.tToR_elderlyRate, tToR_elderlyRate) == 0 && Double.compare(that.tToH_youngRate, tToH_youngRate) == 0 && Double.compare(that.tToH_adultRate, tToH_adultRate) == 0 && Double.compare(that.tToH_elderlyRate, tToH_elderlyRate) == 0 && Double.compare(that.tToD_youngRate, tToD_youngRate) == 0 && Double.compare(that.tToD_adultRate, tToD_adultRate) == 0 && Double.compare(that.tToD_elderlyRate, tToD_elderlyRate) == 0 && Double.compare(that.rToS_youngRate, rToS_youngRate) == 0 && Double.compare(that.rToS_adultRate, rToS_adultRate) == 0 && Double.compare(that.rToS_elderlyRate, rToS_elderlyRate) == 0 && Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sToE_Mild_YoungRate_Day, sToE_Mild_AdultRate_Day, sToE_Mild_ElderlyRate_Day, sToE_Severe_YoungRate_Day, sToE_Severe_AdultRate_Day, sToE_Severe_ElderlyRate_Day, sToE_Mild_YoungRate_Night, sToE_Mild_AdultRate_Night, sToE_Mild_ElderlyRate_Night, sToE_Severe_YoungRate_Night, sToE_Severe_AdultRate_Night, sToE_Severe_ElderlyRate_Night, eToMI_YoungRate, eToMI_AdultRate, eToMI_ElderlyRate, eToR_YoungRate, eToR_AdultRate, eToR_ElderlyRate, eToT_youngRate, eToT_adultRate, eToT_elderlyRate, miToR_YoungRate, miToR_AdultRate, miToR_ElderlyRate, miToSI_YoungRate, miToSI_AdultRate, miToSI_ElderlyRate, miToT_youngRate, miToT_adultRate, miToT_elderlyRate, siToR_YoungRate, siToR_AdultRate, siToR_ElderlyRate, siToH_YoungRate, siToH_AdultRate, siToH_ElderlyRate, siToT_youngRate, siToT_adultRate, siToT_elderlyRate, siToD_YoungRate, siToD_AdultRate, siToD_ElderlyRate, hToD_YoungRate, hToD_AdultRate, hToD_ElderlyRate, hToR_YoungRate, hToR_AdultRate, hToR_ElderlyRate, tToR_youngRate, tToR_adultRate, tToR_elderlyRate, tToH_youngRate, tToH_adultRate, tToH_elderlyRate, tToD_youngRate, tToD_adultRate, tToD_elderlyRate, rToS_youngRate, rToS_adultRate, rToS_elderlyRate, params);
    }

    public double getsToE_Mild_YoungRate_Day_covidVariant() {
        return sToE_Mild_YoungRate_Day;
    }

    public double getsToE_Mild_AdultRate_Day_covidVariant() {
        return sToE_Mild_AdultRate_Day;
    }

    public double getsToE_Mild_ElderlyRate_Day_covidVariant() {
        return sToE_Mild_ElderlyRate_Day;
    }

    public double getsToE_Severe_YoungRate_Day_covidVariant() {
        return sToE_Severe_YoungRate_Day;
    }

    public double getsToE_Severe_AdultRate_Day_covidVariant() {
        return sToE_Severe_AdultRate_Day;
    }

    public double getsToE_Severe_ElderlyRate_Day_covidVariant() {
        return sToE_Severe_ElderlyRate_Day;
    }

    public double getsToE_Mild_YoungRate_Night_covidVariant() {
        return sToE_Mild_YoungRate_Night;
    }

    public double getsToE_Mild_AdultRate_Night_covidVariant() {
        return sToE_Mild_AdultRate_Night;
    }

    public double getsToE_Mild_ElderlyRate_Night_covidVariant() {
        return sToE_Mild_ElderlyRate_Night;
    }

    public double getsToE_Severe_YoungRate_Night_covidVariant() {
        return sToE_Severe_YoungRate_Night;
    }

    public double getsToE_Severe_AdultRate_Night_covidVariant() {
        return sToE_Severe_AdultRate_Night;
    }

    public double getsToE_Severe_ElderlyRate_Night_covidVariant() {
        return sToE_Severe_ElderlyRate_Night;
    }

    public double geteToMI_YoungRate_covidVariant() {
        return eToMI_YoungRate;
    }

    public double geteToMI_AdultRate_covidVariant() {
        return eToMI_AdultRate;
    }

    public double geteToMI_ElderlyRate_covidVariant() {
        return eToMI_ElderlyRate;
    }

    public double geteToR_YoungRate_covidVariant() {
        return eToR_YoungRate;
    }

    public double geteToR_AdultRate_covidVariant() {
        return eToR_AdultRate;
    }

    public double geteToR_ElderlyRate_covidVariant() {
        return eToR_ElderlyRate;
    }

    public double getMiToR_YoungRate_covidVariant() {
        return miToR_YoungRate;
    }

    public double getMiToR_AdultRate_covidVariant() {
        return miToR_AdultRate;
    }

    public double getMiToR_ElderlyRate_covidVariant() {
        return miToR_ElderlyRate;
    }

    public double getMiToSI_YoungRate_covidVariant() {
        return miToSI_YoungRate;
    }

    public double getMiToSI_AdultRate_covidVariant() {
        return miToSI_AdultRate;
    }

    public double getMiToSI_ElderlyRate_covidVariant() {
        return miToSI_ElderlyRate;
    }

    public double getSiToR_YoungRate_covidVariant() {
        return siToR_YoungRate;
    }

    public double getSiToR_AdultRate_covidVariant() {
        return siToR_AdultRate;
    }

    public double getSiToR_ElderlyRate_covidVariant() {
        return siToR_ElderlyRate;
    }

    public double getSiToH_YoungRate_covidVariant() {
        return siToH_YoungRate;
    }

    public double getSiToH_AdultRate_covidVariant() {
        return siToH_AdultRate;
    }

    public double getSiToH_ElderlyRate_covidVariant() {
        return siToH_ElderlyRate;
    }

    public double getSiToD_YoungRate_covidVariant() {
        return siToD_YoungRate;
    }

    public double getSiToD_AdultRate_covidVariant() {
        return siToD_AdultRate;
    }

    public double getSiToD_ElderlyRate_covidVariant() {
        return siToD_ElderlyRate;
    }

    public double gethToD_YoungRate_covidVariant() {
        return hToD_YoungRate;
    }

    public double gethToD_AdultRate_covidVariant() {
        return hToD_AdultRate;
    }

    public double gethToD_ElderlyRate_covidVariant() {
        return hToD_ElderlyRate;
    }

    public double gethToR_YoungRate_covidVariant() {
        return hToR_YoungRate;
    }

    public double gethToR_AdultRate_covidVariant() {
        return hToR_AdultRate;
    }

    public double gethToR_ElderlyRate_covidVariant() {
        return hToR_ElderlyRate;
    }

    public double geteToT_youngRate_covidVariant() { return eToT_youngRate; }

    public double geteToT_adultRate_covidVariant() { return eToT_adultRate; }

    public double geteToT_elderlyRate_covidVariant() { return eToT_elderlyRate; }

    public double getMiToT_YoungRate_covidVariant() { return miToT_youngRate; }

    public double getMiToT_AdultRate_covidVariant() { return miToT_adultRate; }

    public double getMiToT_ElderlyRate_covidVariant() { return miToT_elderlyRate; }

    public double getSiToT_YoungRate_covidVariant() { return siToT_youngRate; }

    public double getSiToT_AdultRate_covidVariant() { return siToT_adultRate; }

    public double getSiToT_ElderlyRate_covidVariant() { return siToT_elderlyRate; }

    public double gettToR_YoungRate_covidVariant() { return tToR_youngRate; }

    public double gettToR_AdultRate_covidVariant() { return tToR_adultRate; }

    public double gettToR_ElderlyRate_covidVariant() { return tToR_elderlyRate; }

    public double gettToH_YoungRate_covidVariant() { return tToH_youngRate; }

    public double gettToH_AdultRate_covidVariant() { return tToH_adultRate; }

    public double gettToH_ElderlyRate_covidVariant() { return tToH_elderlyRate; }

    public double gettToD_YoungRate_covidVariant() { return tToD_youngRate; }

    public double gettToD_AdultRate_covidVariant() { return tToD_adultRate; }

    public double gettToD_ElderlyRate_covidVariant() { return tToD_elderlyRate; }

    public double getrToS_youngRate_covidVariant() {
        return rToS_youngRate;
    }

    public double getrToS_adultRate_covidVariant() {
        return rToS_adultRate;
    }

    public double getrToS_elderlyRate_covidVariant() {
        return rToS_elderlyRate;
    }
}


