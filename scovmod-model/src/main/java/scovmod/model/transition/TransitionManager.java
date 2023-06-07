/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition;

import scovmod.model.input.config.Parameters;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.state.InfectiousProportion;
import scovmod.model.state.StateQuery;
import scovmod.model.transition.beta.BetaRatesMgr;
import scovmod.model.transition.susceptible.FromSusceptible;
import scovmod.model.transition.susceptible.LocalPersonPressure;
import scovmod.model.transition.susceptible.LocalVariantPressure;
import scovmod.model.transition.susceptible.VisitorPressure;

import java.util.Set;

public class TransitionManager {

    // See IncubationRates class for explanation of how the rates work

    public static TransitionManager build(
            StateQuery sq,
            Parameters params,
            TransitionExecutor te,
            InfectiousProportion ip,
            BetaRatesMgr br) {
        return new TransitionManager(
                new FromSusceptible(
                        new LocalPersonPressure(sq, params, ip, br),
                        new LocalVariantPressure(sq, params, ip, br),
                        new VisitorPressure(sq, br),
                        te
                ),
                te);
    }

    private final FromSusceptible fromSus;
    private final TransitionExecutor te;

    public TransitionManager(FromSusceptible fromSus, TransitionExecutor te) {
        this.fromSus = fromSus;
        this.te = te;
    }

    public void doTransitions(Set<LocationIncomingPersons> incomingPersons) {
        fromSus.doTransitions(incomingPersons);
        te.doExposedToMildTransitions();
        te.doExposedToRecoveredTransitions();
        te.doExposedToTracedTransitions();
        te.doMildToSevereTransitions();
        te.doMildToRecoveredTransitions();
        te.doMildToTracedTransitions();
        te.doSevereToDeadTransitions();
        te.doSevereToHospitalisedTransitions();
        te.doSevereToRecoveredTransitions();
        te.doSevereToTracedTransitions();
        te.doHospitalisedToDeadTransitions();
        te.doHospitalisedToRecoveredTransitions();
        te.doTracedToDeadTransitions();
        te.doTracedToHospitalisedTransitions();
        te.doTracedToRecoveredTransitions();
        te.doRecoveredToSusceptibleTransitions();
        te.doExposedToMildFromVariantTransitions();
        te.doExposedToRecoveredFromVariantTransitions();
        te.doExposedToTracedFromVariantTransitions();
        te.doMildToSevereFromVariantTransitions();
        te.doMildToRecoveredFromVariantTransitions();
        te.doMildToTracedFromVariantTransitions();
        te.doSevereToDeadFromVariantTransitions();
        te.doSevereToHospitalisedFromVariantTransitions();
        te.doSevereToRecoveredFromVariantTransitions();
        te.doSevereToTracedFromVariantTransitions();
        te.doHospitalisedToDeadFromVariantTransitions();
        te.doHospitalisedToRecoveredFromVariantTransitions();
        te.doTracedToDeadFromVariantTransitions();
        te.doTracedToHospitalisedFromVariantTransitions();
        te.doTracedToRecoveredFromVariantTransitions();
        te.doRecoveredToSusceptibleFromVariantTransitions();
    }
}
