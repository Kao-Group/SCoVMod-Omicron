package scovmod.model.input.config;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scovmod.model.input.JsonMovementReader;
import scovmod.model.state.StateModifier;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CovidVariantParametersTest {

    private static final double TOL = 1e-10;
    CovidVariantParameters params;
    Path basePath = Paths.get("src", "test", "resources", "inputData", "parameters", "scovmod-config.json");

    @Mock
    private Parameters standardParams;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        System.out.println(basePath.toString());

        when(standardParams.getsToE_Mild_YoungRate_Day()).thenReturn(2.0);
        when(standardParams.getsToE_Mild_AdultRate_Day()).thenReturn(2.0);
        when(standardParams.getsToE_Mild_ElderlyRate_Day()).thenReturn(2.0);
        when(standardParams.getsToE_Severe_YoungRate_Day()).thenReturn(3.0);
        when(standardParams.getsToE_Severe_AdultRate_Day()).thenReturn(3.0);
        when(standardParams.getsToE_Severe_ElderlyRate_Day()).thenReturn(3.0);
        when(standardParams.getsToE_Mild_YoungRate_Night()).thenReturn(4.0);
        when(standardParams.getsToE_Mild_AdultRate_Night()).thenReturn(4.0);
        when(standardParams.getsToE_Mild_ElderlyRate_Night()).thenReturn(4.0);
        when(standardParams.getsToE_Severe_YoungRate_Night()).thenReturn(1.0);
        when(standardParams.getsToE_Severe_AdultRate_Night()).thenReturn(1.0);
        when(standardParams.getsToE_Severe_ElderlyRate_Night()).thenReturn(1.0);
        when(standardParams.geteToMI_YoungRate()).thenReturn(2.0);
        when(standardParams.geteToMI_AdultRate()).thenReturn(2.0);
        when(standardParams.geteToMI_ElderlyRate()).thenReturn(2.0);
        when(standardParams.getMiToR_YoungRate()).thenReturn(3.0);
        when(standardParams.getMiToR_AdultRate()).thenReturn(3.0);
        when(standardParams.getMiToR_ElderlyRate()).thenReturn(3.0);
        when(standardParams.getMiToSI_YoungRate()).thenReturn(4.0);
        when(standardParams.getMiToSI_AdultRate()).thenReturn(4.0);
        when(standardParams.getMiToSI_ElderlyRate()).thenReturn(4.0);

        String json = "";
        try {
            json = FileUtils.readFileToString(new File(basePath.toString()), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        params = CovidVariantParameters.fromJSON(json,standardParams);
    }

    @Test
    public void testJSONParsing() {

        assertEquals(0.00419749473795*2.0, params.getsToE_Mild_YoungRate_Day_covidVariant(), TOL);
        assertEquals(0.0419749473795*2.0, params.getsToE_Mild_AdultRate_Day_covidVariant(), TOL);
        assertEquals(0.0419749473795*2.0, params.getsToE_Mild_ElderlyRate_Day_covidVariant(), TOL);
        assertEquals(0.0419749473795*3.0, params.getsToE_Severe_YoungRate_Day_covidVariant() , TOL);
        assertEquals(0.00419749473795*3.0, params.getsToE_Severe_AdultRate_Day_covidVariant(), TOL);
        assertEquals(0.00419749473795*3.0, params.getsToE_Severe_ElderlyRate_Day_covidVariant() , TOL);
        assertEquals(0.00419749473795*4.0, params.getsToE_Mild_YoungRate_Night_covidVariant(), TOL);
        assertEquals(0.00419749473795*4.0, params.getsToE_Mild_AdultRate_Night_covidVariant(), TOL);
        assertEquals(0.00419749473795*4.0, params.getsToE_Mild_ElderlyRate_Night_covidVariant(), TOL);
        assertEquals(0.0419749473795, params.getsToE_Severe_YoungRate_Night_covidVariant(), TOL);
        assertEquals(0.00419749473795, params.getsToE_Severe_AdultRate_Night_covidVariant() , TOL);
        assertEquals(0.0419749473795, params.getsToE_Severe_ElderlyRate_Night_covidVariant(), TOL);

        assertEquals(0.00859118850425*2.0, params.geteToMI_YoungRate_covidVariant(), TOL);
        assertEquals(0.00859118850425*2.0, params.geteToMI_AdultRate_covidVariant(), TOL);
        assertEquals(0.00859118850425*2.0, params.geteToMI_ElderlyRate_covidVariant() , TOL);
        assertEquals(0.00859118850425, params.geteToR_YoungRate_covidVariant() , TOL);
        assertEquals(0.00859118850425, params.geteToR_AdultRate_covidVariant(), TOL);
        assertEquals(0.00859118850425, params.geteToR_ElderlyRate_covidVariant() , TOL);
        assertEquals(0.0419749473795, params.geteToT_youngRate_covidVariant() , TOL);
        assertEquals(0.0419749473795, params.geteToT_adultRate_covidVariant(), TOL);
        assertEquals(0.0419749473795, params.geteToT_elderlyRate_covidVariant() , TOL);
        assertEquals(0.00859118850425*3.0, params.getMiToR_YoungRate_covidVariant() , TOL);
        assertEquals(0.00859118850425*3.0, params.getMiToR_AdultRate_covidVariant(), TOL);
        assertEquals(0.00859118850425*3.0, params.getMiToR_ElderlyRate_covidVariant() , TOL);
        assertEquals(0.0859118850425*4.0, params.getMiToSI_YoungRate_covidVariant() , TOL);
        assertEquals(0.0859118850425*4.0, params.getMiToSI_AdultRate_covidVariant(), TOL);
        assertEquals(0.0859118850425*4.0, params.getMiToSI_ElderlyRate_covidVariant() , TOL);
        assertEquals(0.0859118850425, params.getMiToT_YoungRate_covidVariant() , TOL);
        assertEquals(0.0859118850425, params.getMiToT_AdultRate_covidVariant(), TOL);
        assertEquals(0.0859118850425, params.getMiToT_ElderlyRate_covidVariant() , TOL);
        assertEquals(0.00859118850425, params.getSiToR_YoungRate_covidVariant(), TOL);
        assertEquals(0.00859118850425, params.getSiToR_AdultRate_covidVariant(), TOL);
        assertEquals(0.00859118850425, params.getSiToR_ElderlyRate_covidVariant() , TOL);
        assertEquals(0.0859118850425, params.getSiToH_YoungRate_covidVariant(), TOL);
        assertEquals(0.0859118850425, params.getSiToH_AdultRate_covidVariant() , TOL);
        assertEquals(0.0859118850425, params.getSiToH_ElderlyRate_covidVariant(), TOL);
        assertEquals(0.0, params.getSiToT_YoungRate_covidVariant(), TOL);
        assertEquals(0.0, params.getSiToT_AdultRate_covidVariant() , TOL);
        assertEquals(0.0, params.getSiToT_ElderlyRate_covidVariant(), TOL);
        assertEquals(0.000859118850425, params.getSiToD_YoungRate_covidVariant() , TOL);
        assertEquals(0.00859118850425, params.getSiToD_AdultRate_covidVariant(), TOL);
        assertEquals(0.0859118850425, params.getSiToD_ElderlyRate_covidVariant(), TOL);
        assertEquals(0.000859118850425, params.gethToD_YoungRate_covidVariant(), TOL);
        assertEquals(0.00859118850425, params.gethToD_AdultRate_covidVariant() , TOL);
        assertEquals(0.0859118850425, params.gethToD_ElderlyRate_covidVariant(), TOL);
        assertEquals(0.000859118850425, params.gethToR_YoungRate_covidVariant(), TOL);
        assertEquals(0.000859118850425, params.gethToR_AdultRate_covidVariant(), TOL);
        assertEquals(0.0859118850425, params.gethToR_ElderlyRate_covidVariant() , TOL);
        assertEquals(0.0, params.gettToR_YoungRate_covidVariant(), TOL);
        assertEquals(0.0, params.gettToR_AdultRate_covidVariant(), TOL);
        assertEquals(0.0, params.gettToR_ElderlyRate_covidVariant() , TOL);
        assertEquals(0.0, params.gettToH_YoungRate_covidVariant(), TOL);
        assertEquals(0.0, params.gettToH_AdultRate_covidVariant() , TOL);
        assertEquals(0.0, params.gettToH_ElderlyRate_covidVariant(), TOL);
        assertEquals(0.0, params.gettToD_YoungRate_covidVariant() , TOL);
        assertEquals(0.0, params.gettToD_AdultRate_covidVariant(), TOL);
        assertEquals(0.0, params.gettToD_ElderlyRate_covidVariant(), TOL);
        assertEquals(0.0, params.getrToS_youngRate_covidVariant(), TOL);
        assertEquals(0.0, params.getrToS_adultRate_covidVariant(), TOL);
        assertEquals(0.0, params.getrToS_elderlyRate_covidVariant(), TOL);
    }

}
