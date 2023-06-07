package scovmod.model.transition.susceptible;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Test;
import scovmod.model.input.config.Parameters;
import scovmod.model.state.InfectiousProportion;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.transition.beta.BetaRatesMgr;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scovmod.model.util.TestUtils.intSetOf;

public class LocalVariantPressureTest {

    private static final double MI_RATE = 0.2;
    private static final double SI_RATE = 0.5;

    private final int LOCATION_1 = 100;
    private final int LOCATION_2 = 200;

    private final double IP_MI_LOCATION_1 = 0.3;
    private final double IP_MI_LOCATION_2 = 0.4;

    private final double IP_SI_LOCATION_1 = 0.4;
    private final double IP_SI_LOCATION_2 = 0.5;

    @Test
    public void mutatePressureMap() {
        StateQuery sq = mock(StateQuery.class);
        InfectiousProportion ip = mock(InfectiousProportion.class);
        BetaRatesMgr br = mock(BetaRatesMgr.class);
        Parameters params = mock(Parameters.class);
        when(br.getBeta(InfectionState.MILD_INFECTIOUS_ADULT_VARIANT,LOCATION_1)).thenReturn(MI_RATE);
        when(br.getBeta(InfectionState.MILD_INFECTIOUS_ADULT_VARIANT,LOCATION_2)).thenReturn(MI_RATE);
        when(br.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT,LOCATION_1)).thenReturn(SI_RATE);
        when(br.getBeta(InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT,LOCATION_2)).thenReturn(SI_RATE);
        when(ip.inCommunity(LOCATION_1, InfectionState.MILD_INFECTIOUS_ADULT_VARIANT)).thenReturn(IP_MI_LOCATION_1);
        when(ip.inCommunity(LOCATION_2,InfectionState.MILD_INFECTIOUS_ADULT_VARIANT)).thenReturn(IP_MI_LOCATION_2);
        when(ip.inCommunity(LOCATION_1, InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT)).thenReturn(IP_SI_LOCATION_1);
        when(ip.inCommunity(LOCATION_2,InfectionState.SEVERE_INFECTIOUS_ELDERLY_VARIANT)).thenReturn(IP_SI_LOCATION_2);

        LocalVariantPressure instance = new LocalVariantPressure(sq, params, ip, br);

        when(sq.getMildInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getMildInfectiousFromVariantAdultLocations()).thenReturn(intSetOf(LOCATION_1, LOCATION_2));
        when(sq.getMildInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantYoungLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantAdultLocations()).thenReturn(intSetOf());
        when(sq.getSevereInfectiousFromVariantElderlyLocations()).thenReturn(intSetOf(LOCATION_1, LOCATION_2));

        Int2ObjectMap pressureMap = new Int2ObjectOpenHashMap();
        instance.addLocalVariantPressure(pressureMap);

        Int2ObjectMap expectedMap = new Int2ObjectOpenHashMap();

        expectedMap.put(LOCATION_1,new InfectionPressure(MI_RATE*IP_MI_LOCATION_1+SI_RATE*IP_SI_LOCATION_1,0));
        expectedMap.put(LOCATION_2, new InfectionPressure(MI_RATE*IP_MI_LOCATION_2+SI_RATE*IP_SI_LOCATION_2, 0));
        assertEquals(pressureMap,expectedMap);
        InfectionPressure loc1PersonPressure = (InfectionPressure) expectedMap.get(LOCATION_1);
        assertEquals(MI_RATE*IP_MI_LOCATION_1+SI_RATE*IP_SI_LOCATION_1,loc1PersonPressure.getVariantPressure());
        InfectionPressure loc2PersonPressure = (InfectionPressure) expectedMap.get(LOCATION_2);
        assertEquals(MI_RATE*IP_MI_LOCATION_2+SI_RATE*IP_SI_LOCATION_2,loc2PersonPressure.getVariantPressure());
    }
}
