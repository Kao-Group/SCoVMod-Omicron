package scovmod.model.transition;

import scovmod.model.input.config.ConfigParameters;
import scovmod.model.movements.LocationIncomingPersons;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import scovmod.model.transition.susceptible.FromSusceptible;

public class TransitionManagerTest {

    @Mock
    Set<LocationIncomingPersons> incomingPersons;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void delegatesToSubComponents_WithVaccination() {
        FromSusceptible fromSus = mock(FromSusceptible.class);
        TransitionExecutor te = mock(TransitionExecutor.class);

        TransitionManager instance = new TransitionManager(fromSus, te);

        instance.doTransitions(incomingPersons);

        verify(fromSus).doTransitions(incomingPersons);
        verify(te).doExposedToMildTransitions();
        verify(te).doExposedToRecoveredTransitions();
        verify(te).doExposedToTracedTransitions();
        verify(te).doMildToSevereTransitions();
        verify(te).doMildToRecoveredTransitions();
        verify(te).doMildToTracedTransitions();
        verify(te).doSevereToDeadTransitions();
        verify(te).doSevereToHospitalisedTransitions();
        verify(te).doSevereToRecoveredTransitions();
        verify(te).doSevereToTracedTransitions();
        verify(te).doHospitalisedToDeadTransitions();
        verify(te).doHospitalisedToRecoveredTransitions();
        verify(te).doTracedToDeadTransitions();
        verify(te).doTracedToHospitalisedTransitions();
        verify(te).doTracedToRecoveredTransitions();
    }

    @Test
    public void delegatesToSubComponents_NoVaccination() {
        FromSusceptible fromSus = mock(FromSusceptible.class);
        TransitionExecutor te = mock(TransitionExecutor.class);
        ConfigParameters config = mock(ConfigParameters.class);

        TransitionManager instance = new TransitionManager(fromSus, te);

        instance.doTransitions(incomingPersons);

        verify(fromSus).doTransitions(incomingPersons);
//        verify(te).doSusceptibleToRecoveredTransitions();
        verify(te).doExposedToMildTransitions();
        verify(te).doExposedToRecoveredTransitions();
        verify(te).doExposedToTracedTransitions();
        verify(te).doMildToSevereTransitions();
        verify(te).doMildToRecoveredTransitions();
        verify(te).doMildToTracedTransitions();
        verify(te).doSevereToDeadTransitions();
        verify(te).doSevereToHospitalisedTransitions();
        verify(te).doSevereToRecoveredTransitions();
        verify(te).doSevereToTracedTransitions();
        verify(te).doHospitalisedToDeadTransitions();
        verify(te).doHospitalisedToRecoveredTransitions();
        verify(te).doTracedToDeadTransitions();
        verify(te).doTracedToHospitalisedTransitions();
        verify(te).doTracedToRecoveredTransitions();
        verify(te).doRecoveredToSusceptibleTransitions();
        verify(te).doExposedToMildFromVariantTransitions();
        verify(te).doExposedToRecoveredFromVariantTransitions();
        verify(te).doExposedToTracedFromVariantTransitions();
        verify(te).doMildToSevereFromVariantTransitions();
        verify(te).doMildToRecoveredFromVariantTransitions();
        verify(te).doMildToTracedFromVariantTransitions();
        verify(te).doSevereToDeadFromVariantTransitions();
        verify(te).doSevereToHospitalisedFromVariantTransitions();
        verify(te).doSevereToRecoveredFromVariantTransitions();
        verify(te).doSevereToTracedFromVariantTransitions();
        verify(te).doHospitalisedToDeadFromVariantTransitions();
        verify(te).doHospitalisedToRecoveredFromVariantTransitions();
        verify(te).doTracedToDeadFromVariantTransitions();
        verify(te).doTracedToHospitalisedFromVariantTransitions();
        verify(te).doTracedToRecoveredFromVariantTransitions();
        verify(te).doRecoveredToSusceptibleFromVariantTransitions();
    }
}
