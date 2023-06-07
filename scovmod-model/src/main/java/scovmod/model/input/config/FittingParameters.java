package scovmod.model.input.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

public class FittingParameters implements Serializable {
    private static final long serialVersionUID = 1;

    private final double sToE_MI_Factor;
    private final double sToE_SI_day;
    private final double sToE_SI_night;
    private final double eToMI;
    private final double miToR;
    private final double miToSI;
    private final double numSeeds;
    private final double numRecoveredSeeds;
    //private final double firstBetaMultiplier;
    //private final double secondBetaMultiplier;
    private final double transModifier;

    private static final Logger log = LoggerFactory.getLogger(FittingParameters.class.getClass());

    public FittingParameters(double sToE_mi_factor, double sToE_SI_day, double sToE_SI_night, double eToMI, double miToR, double miToSI, double numSeeds, double numRecoveredSeeds,/*double firstBetaMultiplier, double secondBetaMultiplier,*/ double transModifier) {
        this.sToE_MI_Factor = sToE_mi_factor;
        this.sToE_SI_day = sToE_SI_day;
        this.sToE_SI_night = sToE_SI_night;
        this.eToMI = eToMI;
        this.miToR = miToR;
        this.miToSI = miToSI;
        this.numSeeds = numSeeds;
        this.numRecoveredSeeds = numRecoveredSeeds;
       // this.firstBetaMultiplier = firstBetaMultiplier;
       // this.secondBetaMultiplier = secondBetaMultiplier;
        this.transModifier = transModifier;
    }


    public Parameters makeFullParameterSet(Parameters params) {
        return makeFullParameterSet(
                params.geteToR_YoungRate(),
                params.geteToR_AdultRate(),
                params.geteToR_ElderlyRate(),
                params.geteToT_youngRate(),
                params.geteToT_adultRate(),
                params.geteToT_elderlyRate(),
                params.getMiToT_youngRate(),
                params.getMiToT_adultRate(),
                params.getMiToT_elderlyRate(),
                params.getSiToR_YoungRate(),
                params.getSiToR_AdultRate(),
                params.getSiToR_ElderlyRate(),
                params.getSiToH_YoungRate(),
                params.getSiToH_AdultRate(),
                params.getSiToH_ElderlyRate(),
                params.getSiToT_YoungRate(),
                params.getSiToT_AdultRate(),
                params.getSiToT_ElderlyRate(),
                params.getSiToD_YoungRate(),
                params.getSiToD_AdultRate(),
                params.getSiToD_ElderlyRate(),
                params.gethToD_YoungRate(),
                params.gethToD_AdultRate(),
                params.gethToD_ElderlyRate(),
                params.gethToR_YoungRate(),
                params.gethToR_AdultRate(),
                params.gethToR_ElderlyRate(),
                params.gettToR_YoungRate(),
                params.gettToR_AdultRate(),
                params.gettToR_ElderlyRate(),
                params.gettToH_YoungRate(),
                params.gettToH_AdultRate(),
                params.gettToH_ElderlyRate(),
                params.gettToD_YoungRate(),
                params.gettToD_AdultRate(),
                params.gettToD_ElderlyRate(),
                params.getrToS_youngRate(),
                params.getrToS_adultRate(),
                params.getrToS_elderlyRate(),
                params.getBetaFactorForLocalLockdown(),
                params.getAverageTransIndexPerCouncilArea(),
                params.getFirstBetaFactor(),
                params.getPartialVersusFullProtectionProp(),
                params.getPartialVersusFullProtectionProp_variant());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FittingParameters that = (FittingParameters) o;
        return Double.compare(that.sToE_MI_Factor, sToE_MI_Factor) == 0 && Double.compare(that.sToE_SI_day, sToE_SI_day) == 0 && Double.compare(that.sToE_SI_night, sToE_SI_night) == 0 && Double.compare(that.eToMI, eToMI) == 0 && Double.compare(that.miToR, miToR) == 0 && Double.compare(that.miToSI, miToSI) == 0 && Double.compare(that.numSeeds, numSeeds) == 0 && Double.compare(that.numRecoveredSeeds, numRecoveredSeeds) == 0 && Double.compare(that.transModifier, transModifier) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sToE_MI_Factor, sToE_SI_day, sToE_SI_night, eToMI, miToR, miToSI, numSeeds, numRecoveredSeeds, transModifier);
    }

    public Parameters makeFullParameterSet(
            double eToR_youngRate,
            double eToR_adultRate,
            double eToR_elderlyRate,
            double eToT_youngRate,
            double eToT_adultRate,
            double eToT_elderlyRate,
            double miToT_YoungRate,
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
            double betaLockdownForLocalMultiplier,
            double averageAccessIndexPerCouncilArea,
            double firstBetaMultiplier,
            double partialVersusFullProtectionProp,
            double partialVersusFullProtectionProp_variant
    ) {

        return new Parameters(
                sToE_MI_Factor,
                sToE_MI_Factor,
                sToE_MI_Factor,
                sToE_SI_day,
                sToE_SI_day,
                sToE_SI_day,
                sToE_MI_Factor,
                sToE_MI_Factor,
                sToE_MI_Factor,
                sToE_SI_night,
                sToE_SI_night,
                sToE_SI_night,
                firstBetaMultiplier,
               // secondBetaMultiplier,
                betaLockdownForLocalMultiplier,
                eToMI,
                eToMI,
                eToMI,
                eToR_youngRate,
                eToR_adultRate,
                eToR_elderlyRate,
                eToT_youngRate,
                eToT_adultRate,
                eToT_elderlyRate,
                miToR,
                miToR,
                miToR,
                miToSI,
                miToSI,
                miToSI,
                miToT_YoungRate,
                miToT_adultRate,
                miToT_elderlyRate,
                siToR_youngRate,
                siToR_adultRate,
                siToR_elderlyRate,
                siToH_youngRate,
                siToH_adultRate,
                siToH_elderlyRate,
                siToT_youngRate,
                siToT_adultRate,
                siToT_elderlyRate,
                siToD_youngRate,
                siToD_adultRate,
                siToD_elderlyRate,
                hToD_youngRate,
                hToD_adultRate,
                hToD_elderlyRate,
                hToR_youngRate,
                hToR_adultRate,
                hToR_elderlyRate,
                tToR_youngRate,
                tToR_adultRate,
                tToR_elderlyRate,
                tToH_youngRate,
                tToH_adultRate,
                tToH_elderlyRate,
                tToD_youngRate,
                tToD_adultRate,
                tToD_elderlyRate,
                rToS_youngRate,
                rToS_adultRate,
                rToS_elderlyRate,
                transModifier,
                averageAccessIndexPerCouncilArea,
                numSeeds,
                numRecoveredSeeds,
                partialVersusFullProtectionProp,
                partialVersusFullProtectionProp_variant);
    }

    public Logger getLog() {
        return log;
    }

    public double getsToE_MI_Factor() {
        return sToE_MI_Factor;
    }

    public double getsToE_SI_day() {
        return sToE_SI_day;
    }

    public double getsToE_SI_night() {
        return sToE_SI_night;
    }

    public double geteToMI() {
        return eToMI;
    }

    public double getMiToR() {
        return miToR;
    }

    public double getMiToSI() {
        return miToSI;
    }

    public double getNumSeeds() { return numSeeds; }

    public double getNumRecoveredSeeds() { return numRecoveredSeeds; }

    // public double getFirstBetaMultiplier() { return firstBetaMultiplier; }

   // public double getSecondBetaMultiplier() { return secondBetaMultiplier; }

    public double getTransModifier() { return transModifier; }
}
