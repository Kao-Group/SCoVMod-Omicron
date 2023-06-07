package scovmod.model.input.config;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FittingParametersPostLockdownTest {

    private static final double TOL = Double.MIN_VALUE;

    @Test
    public void makeFullParameterSetFromParameters() {
        FittingParameters instance = new FittingParameters(
                0.1,
                0.2,
                0.3,
                0.4,
                0.5,
                0.6,
                100.0,
               300.0,
               // 0.3,
              //  0.4,
                0.033);

        Parameters fullParams = mock(Parameters.class);
        when(fullParams.geteToR_YoungRate()).thenReturn(0.147);
        when(fullParams.geteToR_AdultRate()).thenReturn(0.147);
        when(fullParams.geteToR_ElderlyRate()).thenReturn(0.147);
        when(fullParams.geteToT_youngRate()).thenReturn(0.14);
        when(fullParams.geteToT_adultRate()).thenReturn(0.14);
        when(fullParams.geteToT_elderlyRate()).thenReturn(0.14);
        when(fullParams.getMiToT_youngRate()).thenReturn(0.14);
        when(fullParams.getMiToT_adultRate()).thenReturn(0.14);
        when(fullParams.getMiToT_elderlyRate()).thenReturn(0.14);
        when(fullParams.getSiToR_YoungRate()).thenReturn(0.147);
        when(fullParams.getSiToR_AdultRate()).thenReturn(0.147);
        when(fullParams.getSiToR_ElderlyRate()).thenReturn(0.147);
        when(fullParams.getSiToH_YoungRate()).thenReturn(0.147);
        when(fullParams.getSiToH_AdultRate()).thenReturn(0.147);
        when(fullParams.getSiToH_ElderlyRate()).thenReturn(0.147);
        when(fullParams.getSiToT_YoungRate()).thenReturn(0.14);
        when(fullParams.getSiToT_AdultRate()).thenReturn(0.14);
        when(fullParams.getSiToT_ElderlyRate()).thenReturn(0.14);
        when(fullParams.getSiToD_YoungRate()).thenReturn(0.147);
        when(fullParams.getSiToD_AdultRate()).thenReturn(0.147);
        when(fullParams.getSiToD_ElderlyRate()).thenReturn(0.147);
        when(fullParams.gethToD_YoungRate()).thenReturn(0.147);
        when(fullParams.gethToD_AdultRate()).thenReturn(0.147);
        when(fullParams.gethToD_ElderlyRate()).thenReturn(0.147);
        when(fullParams.gethToR_YoungRate()).thenReturn(0.147);
        when(fullParams.gethToR_AdultRate()).thenReturn(0.147);
        when(fullParams.gethToR_ElderlyRate()).thenReturn(0.147);
        when(fullParams.gettToR_YoungRate()).thenReturn(0.14);
        when(fullParams.gettToR_AdultRate()).thenReturn(0.14);
        when(fullParams.gettToR_ElderlyRate()).thenReturn(0.14);
        when(fullParams.gettToH_YoungRate()).thenReturn(0.14);
        when(fullParams.gettToH_AdultRate()).thenReturn(0.14);
        when(fullParams.gettToH_ElderlyRate()).thenReturn(0.14);
        when(fullParams.gettToD_YoungRate()).thenReturn(0.14);
        when(fullParams.gettToD_AdultRate()).thenReturn(0.14);
        when(fullParams.gettToD_ElderlyRate()).thenReturn(0.14);
        when(fullParams.getrToS_youngRate()).thenReturn(0.15);
        when(fullParams.getrToS_adultRate()).thenReturn(0.15);
        when(fullParams.getrToS_elderlyRate()).thenReturn(0.15);
       // when(fullParams.getBetaFactorForLockdown()).thenReturn(0.3);
        //when(fullParams.getAccessModifier()).thenReturn(0.4);
        when(fullParams.getBetaFactorForLocalLockdown()).thenReturn(0.4);
        when(fullParams.getAverageTransIndexPerCouncilArea()).thenReturn(0.09);
        when(fullParams.getFirstBetaFactor()).thenReturn(0.3);
        when(fullParams.getPartialVersusFullProtectionProp()).thenReturn(0.2);
        when(fullParams.getPartialVersusFullProtectionProp_variant()).thenReturn(0.1);

        Parameters expected = new Parameters(
                0.1,
                0.1,
                0.1,
                0.2,
                0.2,
                0.2,
                0.1,
                0.1,
                0.1,
                0.3,
                0.3,
                0.3,
                0.3,
               // 0.4,
                0.4,
                0.4,
                0.4,
                0.4,
                0.147,
                0.147,
                0.147,
                0.14,
                0.14,
                0.14,
                0.5,
                0.5,
                0.5,
                0.6,
                0.6,
                0.6,
                0.14,
                0.14,
                0.14,
                0.147,
                0.147,
                0.147,
                0.147,
                0.147,
                0.147,
                0.14,
                0.14,
                0.14,
                0.147,
                0.147,
                0.147,
                0.147,
                0.147,
                0.147,
                0.147,
                0.147,
                0.147,
                0.14,
                0.14,
                0.14,
                0.14,
                0.14,
                0.14,
                0.14,
                0.14,
                0.14,
                0.15,
                0.15,
                0.15,
                0.033,
                0.09,
                100.0,
                300.0,
                0.2,
                0.1);


        Parameters result = instance.makeFullParameterSet(fullParams);

        assertEquals(expected, result);

        assertEquals(0.1, result.getsToE_Mild_YoungRate_Day(), TOL);
        assertEquals(0.1, result.getsToE_Mild_AdultRate_Day(), TOL);
        assertEquals(0.1, result.getsToE_Mild_ElderlyRate_Day(), TOL);
        assertEquals(0.2, result.getsToE_Severe_YoungRate_Day() , TOL);
        assertEquals(0.2, result.getsToE_Severe_AdultRate_Day(), TOL);
        assertEquals(0.2, result.getsToE_Severe_ElderlyRate_Day() , TOL);
        assertEquals(0.1, result.getsToE_Mild_YoungRate_Night(), TOL);
        assertEquals(0.1, result.getsToE_Mild_AdultRate_Night(), TOL);
        assertEquals(0.1, result.getsToE_Mild_ElderlyRate_Night(), TOL);
        assertEquals(0.3, result.getsToE_Severe_YoungRate_Night(), TOL);
        assertEquals(0.3, result.getsToE_Severe_AdultRate_Night() , TOL);
        assertEquals(0.3, result.getsToE_Severe_ElderlyRate_Night(), TOL);
        assertEquals(0.3, result.getFirstBetaFactor(), TOL);
        assertEquals(0.4, result.geteToMI_YoungRate(), TOL);
        assertEquals(0.4, result.geteToMI_AdultRate(), TOL);
        assertEquals(0.4, result.geteToMI_ElderlyRate() , TOL);
        assertEquals(0.147, result.geteToR_YoungRate() , TOL);
        assertEquals(0.147, result.geteToR_AdultRate(), TOL);
        assertEquals(0.147, result.geteToR_ElderlyRate() , TOL);
        assertEquals(0.5, result.getMiToR_YoungRate() , TOL);
        assertEquals(0.5, result.getMiToR_AdultRate(), TOL);
        assertEquals(0.5, result.getMiToR_ElderlyRate() , TOL);
        assertEquals(0.6, result.getMiToSI_YoungRate() , TOL);
        assertEquals(0.6, result.getMiToSI_AdultRate(), TOL);
        assertEquals(0.6, result.getMiToSI_ElderlyRate() , TOL);
        assertEquals(0.147, result.getSiToR_YoungRate(), TOL);
        assertEquals(0.147, result.getSiToR_AdultRate(), TOL);
        assertEquals(0.147, result.getSiToR_ElderlyRate() , TOL);
        assertEquals(0.147, result.getSiToH_YoungRate(), TOL);
        assertEquals(0.147, result.getSiToH_AdultRate() , TOL);
        assertEquals(0.147, result.getSiToH_ElderlyRate(), TOL);
        assertEquals(0.147, result.getSiToD_YoungRate() , TOL);
        assertEquals(0.147, result.getSiToD_AdultRate(), TOL);
        assertEquals(0.147, result.getSiToD_ElderlyRate(), TOL);
        assertEquals(0.147, result.gethToD_YoungRate(), TOL);
        assertEquals(0.147, result.gethToD_AdultRate() , TOL);
        assertEquals(0.147, result.gethToD_ElderlyRate(), TOL);
        assertEquals(0.147, result.gethToR_YoungRate(), TOL);
        assertEquals(0.147, result.gethToR_AdultRate(), TOL);
        assertEquals(0.147, result.gethToR_ElderlyRate() , TOL);
        assertEquals(0.15, result.getrToS_youngRate(), TOL);
        assertEquals(0.15, result.getrToS_adultRate(), TOL);
        assertEquals(0.15, result.getrToS_elderlyRate() , TOL);
        assertEquals(100.0, result.getNumSeeds() , TOL);
        assertEquals(300.0, result.getNumRecoveredSeeds() , TOL);
        assertEquals(0.3, result.getFirstBetaFactor() , TOL);
        assertEquals(0.2, result.getPartialVersusFullProtectionProp() , TOL);
        assertEquals(0.1, result.getPartialVersusFullProtectionProp_variant() , TOL);
    }

    @Test
    public void valueObject() {
        FittingParameters instance1a = new FittingParameters(0.1, 0.2, /*0.3, 0.4,*/ 0.7, 0.1,0.8, 0.7, 100,  300,0.033);
        FittingParameters instance1b = new FittingParameters(0.1, 0.2, /*0.3, 0.4,*/ 0.7, 0.1,0.8, 0.7, 100,  300,0.033);
        FittingParameters instance2 = new FittingParameters(0.2, 0.2, /*0.3, 0.4,*/ 0.7, 0.1,0.8, 0.7, 100,  300,0.033);
        FittingParameters instance3 = new FittingParameters(0.1, 0.3, /*0.3, 0.4,*/ 0.7, 0.1,0.8, 0.7, 100,  300,0.033);
     //   FittingParameters instance4 = new FittingParameters(0.1, 0.2, /*0.4, 0.4, */0.7, 0.1,0.8, 0.7, 100,  0.033);
        FittingParameters instance5 = new FittingParameters(0.1, 0.2, /*0.3, 0.4,*/ 0.7, 99,0.8, 0.7, 1001,  300,0.033);

        assertTrue(instance1a.equals(instance1b));
        assertFalse(instance1a.equals(instance2));
        assertFalse(instance1a.equals(instance3));
        //assertFalse(instance1a.equals(instance4));
        assertFalse(instance1a.equals(instance5));

        assertEquals(instance1a.hashCode(), instance1b.hashCode());
        assertFalse(instance1a.hashCode() == instance2.hashCode());
        assertFalse(instance1a.hashCode() == instance3.hashCode());
        //assertFalse(instance1a.hashCode() == instance4.hashCode());
        assertFalse(instance1a.hashCode() == instance5.hashCode());
    }
}
