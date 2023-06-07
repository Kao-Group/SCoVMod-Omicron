package scovmod.model.input.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import scovmod.model.time.TimeConversions;
import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;
import static scovmod.model.input.config.OutputFrequency.DAILY;
import static scovmod.model.input.config.OutputFrequency.WEEKLY;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ConfigParametersTest {

    private static final double TOL = 1e-10;

    @Mock
    TimeConversions tcv;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private String getTestConfig(String fileName){
        Path jsonPath = Paths.get("src", "test", "resources", "inputData", "parameters", fileName);

        String json = "";
        try {
            json = FileUtils.readFileToString(new File(jsonPath.toString()), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return json;
    }

    @Test
    public void testJSONParsing() {
        String json = getTestConfig("scovmod-config.json");

        ConfigParameters params = ConfigParameters.fromJSON(json);

        assertEquals(0, params.getFirstTimeStep());
        assertEquals(56, params.getLastTimeStep());
        assertEquals(0.5, params.getTauLeapStep(),TOL);
        assertEquals(20, params.getChunkSize());
        assertEquals(WEEKLY, params.getOutputFrequency());

        assertEquals(123, params.getFirstBetaFactorTimeStep());
        assertEquals(321, params.getSecondBetaFactorTimeStep());
        assertEquals(AreaLevels.IZ, params.getReseedAreaLevel());

        assertEquals(AreaLevels.HB, params.getLocalLockdownsAreaLevel());
        assertEquals(false, params.isLocalLockdownsActivated());
        assertEquals(50.0, params.getMildInfectiousUpperLevel(),TOL);
        assertEquals(10.0, params.getMildInfectiousLowerLevel(),TOL);
        assertEquals(36720, params.getLocalLockdownStartTimeStep());
        assertEquals(1.0, params.getLocalLockdownContactTracingRateMultiplier(),TOL);

        assertEquals(37400, params.getVaccineStartTimestep());
        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_young_Step1(), TOL);
        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_elderly_Step1(), TOL);
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step1());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1());
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_young_Step1(), TOL);
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_elderly_Step1(), TOL);
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step1());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1());
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_young_Step1(), TOL);
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_elderly_Step1(), TOL);
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step1());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1());
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_young_Step1(), TOL);
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_elderly_Step1(), TOL);
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step1());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1());
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_young_Step1(), TOL);
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_elderly_Step1(), TOL);
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step1());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1());
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_young_Step1(), TOL);
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_elderly_Step1(), TOL);
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step1());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1());

        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_young_Step2(), TOL);
        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_elderly_Step2(), TOL);
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step2());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2());
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_young_Step2(), TOL);
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_elderly_Step2(), TOL);
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step2());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2());
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_young_Step2(), TOL);
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_elderly_Step2(), TOL);
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step2());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2());
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_young_Step2(), TOL);
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_elderly_Step2(), TOL);
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step2());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2());
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_young_Step2(), TOL);
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_elderly_Step2(), TOL);
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step2());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2());
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_young_Step2(), TOL);
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_elderly_Step2(), TOL);
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step2());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2());

        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_young_Step3(), TOL);
        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.0, params.getStandardStrainFirstEfficacyChange_elderly_Step3(), TOL);
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step3());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3());
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_young_Step3(), TOL);
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.0, params.getStandardStrainSecondEfficacyChange_elderly_Step3(), TOL);
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step3());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3());
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_young_Step3(), TOL);
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.0, params.getStandardStrainThirdEfficacyChange_elderly_Step3(), TOL);
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step3());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3());
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_young_Step3(), TOL);
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.4, params.getVariantStrainFirstEfficacyChange_elderly_Step3(), TOL);
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step3());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3());
        assertEquals(360, params.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3());
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_young_Step3(), TOL);
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.4, params.getVariantStrainSecondEfficacyChange_elderly_Step3(), TOL);
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step3());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3());
        assertEquals(360, params.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3());
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_young_Step3(), TOL);
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.4, params.getVariantStrainThirdEfficacyChange_elderly_Step3(), TOL);
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step3());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3());
        assertEquals(360, params.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3());

        assertEquals(0.1, params.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.3, params.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.1, params.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(380, params.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young());
        assertEquals(380, params.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult());
        assertEquals(380, params.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly());
        assertEquals(0.2, params.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.2, params.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.2, params.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(380, params.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young());
        assertEquals(380, params.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult());
        assertEquals(380, params.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly());
        assertEquals(0.3, params.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.3, params.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.3, params.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(380, params.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young());
        assertEquals(380, params.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult());
        assertEquals(380, params.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly());
        assertEquals(0.5, params.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.5, params.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.5, params.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(365, params.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young());
        assertEquals(365, params.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult());
        assertEquals(365, params.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly());
        assertEquals(0.55, params.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.55, params.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.55, params.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(370, params.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young());
        assertEquals(370, params.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult());
        assertEquals(370, params.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly());
        assertEquals(0.45, params.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.45, params.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.45, params.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(375, params.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young());
        assertEquals(375, params.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult());
        assertEquals(375, params.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly());
        assertEquals(0.1,params.getMildInfectiousMultiplier(),TOL);
        assertEquals(0.2,params.getSevereInfectiousMultiplier(),TOL);
        assertEquals(0.3,params.getHospitalisedMultiplier(),TOL);
    }

    @Test
    public void anotherTestWithDifferentConfigValues(){
        String json = getTestConfig("scovmod-config-1.json");

        ConfigParameters params = ConfigParameters.fromJSON(json);

        assertEquals(0, params.getFirstTimeStep());
        assertEquals(28, params.getLastTimeStep());
        assertEquals(0.6, params.getTauLeapStep(),TOL);
        assertEquals(20, params.getChunkSize());
        assertEquals(DAILY, params.getOutputFrequency());
        assertEquals(123, params.getFirstBetaFactorTimeStep());
        assertEquals(321, params.getSecondBetaFactorTimeStep());

        assertEquals(AreaLevels.IZ, params.getReseedAreaLevel());

        assertEquals(AreaLevels.IZ, params.getLocalLockdownsAreaLevel());
        assertEquals(true, params.isLocalLockdownsActivated());
        assertEquals(20.0, params.getMildInfectiousUpperLevel(),TOL);
        assertEquals(5.0, params.getMildInfectiousLowerLevel(),TOL);
        assertEquals(36725, params.getLocalLockdownStartTimeStep());
        assertEquals(2.0, params.getLocalLockdownContactTracingRateMultiplier(),TOL);

        assertEquals(37200, params.getVaccineStartTimestep());

        assertEquals(0.21, params.getStandardStrainFirstEfficacyChange_young_Step1(), TOL);
        assertEquals(0.22, params.getStandardStrainFirstEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.23, params.getStandardStrainFirstEfficacyChange_elderly_Step1(), TOL);
        assertEquals(721, params.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step1());
        assertEquals(722, params.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step1());
        assertEquals(723, params.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step1());
        assertEquals(0.21, params.getStandardStrainSecondEfficacyChange_young_Step1(), TOL);
        assertEquals(0.22, params.getStandardStrainSecondEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.23, params.getStandardStrainSecondEfficacyChange_elderly_Step1(), TOL);
        assertEquals(721, params.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step1());
        assertEquals(722, params.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step1());
        assertEquals(723, params.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step1());
        assertEquals(0.21, params.getStandardStrainThirdEfficacyChange_young_Step1(), TOL);
        assertEquals(0.22, params.getStandardStrainThirdEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.23, params.getStandardStrainThirdEfficacyChange_elderly_Step1(), TOL);
        assertEquals(721, params.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step1());
        assertEquals(722, params.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step1());
        assertEquals(723, params.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step1());
        assertEquals(0.81, params.getVariantStrainFirstEfficacyChange_young_Step1(), TOL);
        assertEquals(0.82, params.getVariantStrainFirstEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.83, params.getVariantStrainFirstEfficacyChange_elderly_Step1(), TOL);
        assertEquals(721, params.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step1());
        assertEquals(722, params.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step1());
        assertEquals(723, params.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step1());
        assertEquals(0.81, params.getVariantStrainSecondEfficacyChange_young_Step1(), TOL);
        assertEquals(0.82, params.getVariantStrainSecondEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.83, params.getVariantStrainSecondEfficacyChange_elderly_Step1(), TOL);
        assertEquals(721, params.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step1());
        assertEquals(722, params.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step1());
        assertEquals(723, params.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step1());
        assertEquals(0.81, params.getVariantStrainThirdEfficacyChange_young_Step1(), TOL);
        assertEquals(0.82, params.getVariantStrainThirdEfficacyChange_adult_Step1(), TOL);
        assertEquals(0.83, params.getVariantStrainThirdEfficacyChange_elderly_Step1(), TOL);
        assertEquals(721, params.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step1());
        assertEquals(722, params.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step1());
        assertEquals(723, params.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step1());

        assertEquals(0.31, params.getStandardStrainFirstEfficacyChange_young_Step2(), TOL);
        assertEquals(0.32, params.getStandardStrainFirstEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.33, params.getStandardStrainFirstEfficacyChange_elderly_Step2(), TOL);
        assertEquals(731, params.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step2());
        assertEquals(732, params.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step2());
        assertEquals(733, params.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step2());
        assertEquals(0.31, params.getStandardStrainSecondEfficacyChange_young_Step2(), TOL);
        assertEquals(0.32, params.getStandardStrainSecondEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.33, params.getStandardStrainSecondEfficacyChange_elderly_Step2(), TOL);
        assertEquals(731, params.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step2());
        assertEquals(732, params.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step2());
        assertEquals(733, params.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step2());
        assertEquals(0.31, params.getStandardStrainThirdEfficacyChange_young_Step2(), TOL);
        assertEquals(0.32, params.getStandardStrainThirdEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.33, params.getStandardStrainThirdEfficacyChange_elderly_Step2(), TOL);
        assertEquals(731, params.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step2());
        assertEquals(732, params.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step2());
        assertEquals(733, params.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step2());
        assertEquals(0.91, params.getVariantStrainFirstEfficacyChange_young_Step2(), TOL);
        assertEquals(0.92, params.getVariantStrainFirstEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.93, params.getVariantStrainFirstEfficacyChange_elderly_Step2(), TOL);
        assertEquals(731, params.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step2());
        assertEquals(732, params.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step2());
        assertEquals(733, params.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step2());
        assertEquals(0.91, params.getVariantStrainSecondEfficacyChange_young_Step2(), TOL);
        assertEquals(0.92, params.getVariantStrainSecondEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.93, params.getVariantStrainSecondEfficacyChange_elderly_Step2(), TOL);
        assertEquals(731, params.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step2());
        assertEquals(732, params.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step2());
        assertEquals(733, params.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step2());
        assertEquals(0.91, params.getVariantStrainThirdEfficacyChange_young_Step2(), TOL);
        assertEquals(0.92, params.getVariantStrainThirdEfficacyChange_adult_Step2(), TOL);
        assertEquals(0.93, params.getVariantStrainThirdEfficacyChange_elderly_Step2(), TOL);
        assertEquals(731, params.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step2());
        assertEquals(732, params.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step2());
        assertEquals(733, params.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step2());

        assertEquals(0.41, params.getStandardStrainFirstEfficacyChange_young_Step3(), TOL);
        assertEquals(0.42, params.getStandardStrainFirstEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.43, params.getStandardStrainFirstEfficacyChange_elderly_Step3(), TOL);
        assertEquals(741, params.getTimestepsInFirstEfficacyPhaseStandardStrain_young_Step3());
        assertEquals(742, params.getTimestepsInFirstEfficacyPhaseStandardStrain_adult_Step3());
        assertEquals(743, params.getTimestepsInFirstEfficacyPhaseStandardStrain_elderly_Step3());
        assertEquals(0.41, params.getStandardStrainSecondEfficacyChange_young_Step3(), TOL);
        assertEquals(0.42, params.getStandardStrainSecondEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.43, params.getStandardStrainSecondEfficacyChange_elderly_Step3(), TOL);
        assertEquals(741, params.getTimestepsInSecondEfficacyPhaseStandardStrain_young_Step3());
        assertEquals(742, params.getTimestepsInSecondEfficacyPhaseStandardStrain_adult_Step3());
        assertEquals(743, params.getTimestepsInSecondEfficacyPhaseStandardStrain_elderly_Step3());
        assertEquals(0.41, params.getStandardStrainThirdEfficacyChange_young_Step3(), TOL);
        assertEquals(0.42, params.getStandardStrainThirdEfficacyChange_adult_Step3(), TOL);
        assertEquals(0.43, params.getStandardStrainThirdEfficacyChange_elderly_Step3(), TOL);
        assertEquals(741, params.getTimestepsInThirdEfficacyPhaseStandardStrain_young_Step3());
        assertEquals(742, params.getTimestepsInThirdEfficacyPhaseStandardStrain_adult_Step3());
        assertEquals(743, params.getTimestepsInThirdEfficacyPhaseStandardStrain_elderly_Step3());
        assertEquals(1.1, params.getVariantStrainFirstEfficacyChange_young_Step3(), TOL);
        assertEquals(1.2, params.getVariantStrainFirstEfficacyChange_adult_Step3(), TOL);
        assertEquals(1.3, params.getVariantStrainFirstEfficacyChange_elderly_Step3(), TOL);
        assertEquals(741, params.getTimestepsInFirstEfficacyPhaseVariantStrain_young_Step3());
        assertEquals(742, params.getTimestepsInFirstEfficacyPhaseVariantStrain_adult_Step3());
        assertEquals(743, params.getTimestepsInFirstEfficacyPhaseVariantStrain_elderly_Step3());
        assertEquals(1.1, params.getVariantStrainSecondEfficacyChange_young_Step3(), TOL);
        assertEquals(1.2, params.getVariantStrainSecondEfficacyChange_adult_Step3(), TOL);
        assertEquals(1.3, params.getVariantStrainSecondEfficacyChange_elderly_Step3(), TOL);
        assertEquals(741, params.getTimestepsInSecondEfficacyPhaseVariantStrain_young_Step3());
        assertEquals(742, params.getTimestepsInSecondEfficacyPhaseVariantStrain_adult_Step3());
        assertEquals(743, params.getTimestepsInSecondEfficacyPhaseVariantStrain_elderly_Step3());
        assertEquals(1.1, params.getVariantStrainThirdEfficacyChange_young_Step3(), TOL);
        assertEquals(1.2, params.getVariantStrainThirdEfficacyChange_adult_Step3(), TOL);
        assertEquals(1.3, params.getVariantStrainThirdEfficacyChange_elderly_Step3(), TOL);
        assertEquals(741, params.getTimestepsInThirdEfficacyPhaseVariantStrain_young_Step3());
        assertEquals(742, params.getTimestepsInThirdEfficacyPhaseVariantStrain_adult_Step3());
        assertEquals(743, params.getTimestepsInThirdEfficacyPhaseVariantStrain_elderly_Step3());



        assertEquals(0.1, params.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.3, params.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.1, params.getStandardStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(380, params.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_young());
        assertEquals(380, params.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_adult());
        assertEquals(380, params.getNumberOfTimestepsInFirstEfficacyPhaseStandardStrain_afterImmunityLoss_elderly());
        assertEquals(0.2, params.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.2, params.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.2, params.getStandardStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(380, params.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_young());
        assertEquals(380, params.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_adult());
        assertEquals(380, params.getNumberOfTimestepsInSecondEfficacyPhaseStandardStrain_afterImmunityLoss_elderly());
        assertEquals(0.3, params.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.3, params.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.3, params.getStandardStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(380, params.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_young());
        assertEquals(380, params.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_adult());
        assertEquals(380, params.getNumberOfTimestepsInThirdEfficacyPhaseStandardStrain_afterImmunityLoss_elderly());
        assertEquals(0.5, params.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.5, params.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.5, params.getVariantStrainEfficacyFirstEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(365, params.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_young());
        assertEquals(365, params.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_adult());
        assertEquals(365, params.getNumberOfTimestepsInFirstEfficacyPhaseVariantStrain_afterImmunityLoss_elderly());
        assertEquals(0.55, params.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.55, params.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.55, params.getVariantStrainEfficacySecondEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(370, params.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_young());
        assertEquals(370, params.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_adult());
        assertEquals(370, params.getNumberOfTimestepsInSecondEfficacyPhaseVariantStrain_afterImmunityLoss_elderly());
        assertEquals(0.45, params.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_young(), TOL);
        assertEquals(0.45, params.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_adult(), TOL);
        assertEquals(0.45, params.getVariantStrainEfficacyThirdEfficacyChange_afterImmunityLoss_elderly(), TOL);
        assertEquals(375, params.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_young());
        assertEquals(375, params.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_adult());
        assertEquals(375, params.getNumberOfTimestepsInThirdEfficacyPhaseVariantStrain_afterImmunityLoss_elderly());
        assertEquals(0.1,params.getMildInfectiousMultiplier(),TOL);
        assertEquals(0.2,params.getSevereInfectiousMultiplier(),TOL);
        assertEquals(0.3,params.getHospitalisedMultiplier(),TOL);
    }

//    @Test
//    public void testTimeStepNotInitialisedProperly() {
//        String json = getTestConfig("scovmod-config.json");
//
//        ConfigParameters params = ConfigParameters.fromJSON(json);
//
//        try {
//            params.getFirstTimeStep();
//        } catch (ModelException e) {
//        }
//
//        try {
//            params.getLastTimeStep();
//        } catch (ModelException e) {
//        }
//    }

}
