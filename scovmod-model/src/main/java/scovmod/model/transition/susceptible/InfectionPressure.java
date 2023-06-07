/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.susceptible;

import scovmod.model.util.math.Random;

public class InfectionPressure {

    private double variantPressure;
    private double personPressure;

    public InfectionPressure(
            double variantPressure,
            double personPressure) {
        this.variantPressure = variantPressure;
        this.personPressure = personPressure;
    }

    double getPersonPressure() {
        return personPressure;
    }

    public boolean isNonZeroPressure(){
        return this.personPressure >0 || this.variantPressure >0;
    }

    double getVariantPressure() {
        return variantPressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfectionPressure that = (InfectionPressure) o;

        if (Double.compare(that.variantPressure, variantPressure) != 0) return false;
        return Double.compare(that.personPressure, personPressure) == 0;
    }

    @Override
    public String toString() {
        return "InfectionPressure{" +
                "wildlifePressure=" + variantPressure +
                ", personPressure=" + personPressure +
                '}';
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(variantPressure);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(personPressure);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public TransmissionEventType evaluate(Random rnd, double timeStepInDays, double protectionFactor_standard, double protectionFactor_variant) {

        double probNoTransmission =
                Math.exp(-(variantPressure * timeStepInDays*protectionFactor_variant)) *
                Math.exp(-(personPressure * timeStepInDays*protectionFactor_standard));
        boolean transmissionOccurs = rnd.nextBoolean(1 - probNoTransmission);

        if(transmissionOccurs){
            double conditionalProbabilityVariant = variantPressure/(variantPressure+personPressure);
            boolean viaVariant = rnd.nextBoolean(conditionalProbabilityVariant);

            if(viaVariant){
                return TransmissionEventType.FROM_VARIANT;
            } else {
                return TransmissionEventType.FROM_PERSON;
            }
        } else {
            return TransmissionEventType.NULL;
        }
    }

    public InfectionPressure setVariantPressure(double variantPressure) {
        assert(getVariantPressure() == 0);
        return new InfectionPressure(variantPressure, personPressure );
    }

    public InfectionPressure augmentPeoplePressure(double additionalPeoplePressure) {
        return new InfectionPressure(variantPressure, personPressure + additionalPeoplePressure);
    }

    public InfectionPressure augmentVariantPressure(double additionalVariantPressure) {
        return new InfectionPressure(variantPressure + additionalVariantPressure, personPressure);
    }
}
