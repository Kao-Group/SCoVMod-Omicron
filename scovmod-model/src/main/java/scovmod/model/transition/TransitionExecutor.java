package scovmod.model.transition;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import scovmod.model.input.config.Parameters;
import scovmod.model.state.StateManagementFactory;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.transition.infected.*;
import scovmod.model.transition.infected.variant.*;
import scovmod.model.transition.susceptible.InfectionPressure;
import scovmod.model.transition.susceptible.SusceptiblePersonTransitionExecutor;
import scovmod.model.util.math.Random;

import static scovmod.model.state.infection.InfectionState.*;

public class TransitionExecutor {

    private StateQuery sq;
    private Random rnd;
    private StateModifier sm;

    private SusceptiblePersonTransitionExecutor sate;
    private ExposedToMildInfectiousTransitionExecutor eate;
    private ExposedToRecoveredTransitionExecutor eare;
    private ExposedToTracedTransitionExecutor ete;
    private MildInfectiousToSevereTransitionExecutor mitste;
    private MildInfectiousToRecoveredTransitionExecutor mitre;
    private MildInfectiousToTracedTransitionExecutor mitte;
    private SevereInfectiousToRecoveredTransitionExecutor sitrte;
    private SevereInfectiousToHospitalisedTransitionExecutor sithte;
    private SevereInfectiousToDeadTransitionExecutor sitdte;
    private SevereInfectiousToTracedTransitionExecutor sittte;
    private HospitalisedToRecoveredTransitionExecutor htrte;
    private HospitalisedToDeadTransitionExecutor htdte;
    private TracedToRecoveredTransitionExecutor ttrte;
    private TracedToHospitalisedTransitionExecutor tthte;
    private TracedToDeadTransitionExecutor ttdte;
    private RecoveredToSusceptibleTransitionExecutor rtste;
    // Variant transition executors
    private ExposedToMildInfectiousVariantTransitionExecutor veate;
    private ExposedToRecoveredVariantTransitionExecutor veare;
    private ExposedToTracedVariantTransitionExecutor vete;
    private MildInfectiousToSevereVariantTransitionExecutor vmitste;
    private MildInfectiousToRecoveredVariantTransitionExecutor vmitre;
    private MildInfectiousToTracedVariantTransitionExecutor vmitte;
    private SevereInfectiousFromVariantToRecoveredTransitionExecutor vsitrte;
    private SevereInfectiousFromVariantToHospitalisedTransitionExecutor vsithte;
    private SevereInfectiousFromVariantToDeadTransitionExecutor vsitdte;
    private SevereInfectiousFromVariantToTracedTransitionExecutor vsittte;
    private HospitalisedToRecoveredVariantTransitionExecutor vhtrte;
    private HospitalisedToDeadVariantTransitionExecutor vhtdte;
    private TracedToRecoveredFromVariantTransitionExecutor vttrte;
    private TracedToHospitalisedFromVariantTransitionExecutor vtthte;
    private TracedToDeadFromVariantTransitionExecutor vttdte;
    private RecoveredFromVariantToSusceptibleTransitionExecutor vrtste;

    public TransitionExecutor(StateManagementFactory smf,
                              Random rnd,
                              Parameters params,
                              SusceptiblePersonTransitionExecutor sate,
                              ExposedToMildInfectiousTransitionExecutor eate,
                              ExposedToRecoveredTransitionExecutor eare,
                              ExposedToTracedTransitionExecutor ete,
                              MildInfectiousToSevereTransitionExecutor mitste,
                              MildInfectiousToRecoveredTransitionExecutor mitre,
                              MildInfectiousToTracedTransitionExecutor mitte,
                              SevereInfectiousToRecoveredTransitionExecutor sitrte,
                              SevereInfectiousToHospitalisedTransitionExecutor sithte,
                              SevereInfectiousToDeadTransitionExecutor sitdte,
                              SevereInfectiousToTracedTransitionExecutor sittte,
                              HospitalisedToRecoveredTransitionExecutor htrte,
                              HospitalisedToDeadTransitionExecutor htdte,
                              TracedToRecoveredTransitionExecutor ttrte,
                              TracedToHospitalisedTransitionExecutor tthte,
                              TracedToDeadTransitionExecutor ttdte,
                              RecoveredToSusceptibleTransitionExecutor rtste,
                              ExposedToMildInfectiousVariantTransitionExecutor veate,
                              ExposedToRecoveredVariantTransitionExecutor veare,
                              ExposedToTracedVariantTransitionExecutor vete,
                              MildInfectiousToSevereVariantTransitionExecutor vmitste,
                              MildInfectiousToRecoveredVariantTransitionExecutor vmitre,
                              MildInfectiousToTracedVariantTransitionExecutor vmitte,
                              SevereInfectiousFromVariantToRecoveredTransitionExecutor vsitrte,
                              SevereInfectiousFromVariantToHospitalisedTransitionExecutor vsithte,
                              SevereInfectiousFromVariantToDeadTransitionExecutor vsitdte,
                              SevereInfectiousFromVariantToTracedTransitionExecutor vsittte,
                              HospitalisedToRecoveredVariantTransitionExecutor vhtrte,
                              HospitalisedToDeadVariantTransitionExecutor vhtdte,
                              TracedToRecoveredFromVariantTransitionExecutor vttrte,
                              TracedToHospitalisedFromVariantTransitionExecutor vtthte,
                              TracedToDeadFromVariantTransitionExecutor vttdte,
                              RecoveredFromVariantToSusceptibleTransitionExecutor vrtste) {
		this.sate = sate;
        this.sq = smf.getStateQuery();
        this.sm = smf.getStateModifier();
        this.rnd = rnd;
        this.eare = eare;
        this.ete = ete;
        this.mitste = mitste;
        this.mitre = mitre;
        this.mitte = mitte;
        this.sitrte = sitrte;
        this.sithte = sithte;
        this.sitdte = sitdte;
        this.sittte = sittte;
        this.htrte = htrte;
        this.htdte = htdte;
        this.eate = eate;
        this.ttrte = ttrte;
        this.tthte = tthte;
        this.ttdte = ttdte;
        this.rtste = rtste;
        this.veate = veate;
        this.veare = veare;
        this.vete = vete;
        this.vmitste = vmitste;
        this.vmitre = vmitre;
        this.vmitte = vmitte;
        this.vsitrte = vsitrte;
        this.vsithte = vsithte;
        this.vsitdte = vsitdte;
        this.vsittte = vsittte;
        this.vhtrte = vhtrte;
        this.vhtdte = vhtdte;
        this.vttrte = vttrte;
        this.vtthte = vtthte;
        this.vttdte = vttdte;
        this.vrtste = vrtste;
    }

    public void doSusceptibleTransitions(Int2ObjectMap<InfectionPressure> locationPressures) {
        for (int locationId : locationPressures.keySet()) {
            IntSet localSusceptibles = sq.getCopyOfLocalPopulation(locationId).getAllInState(SUSCEPTIBLE);
            if (localSusceptibles.isEmpty()) {
                continue;
            }
            InfectionPressure communityPressure = locationPressures.get(locationId);
            if (communityPressure.isNonZeroPressure()) {
                for (int personId : localSusceptibles) {
                    sate.apply(personId, communityPressure);
                }
            }
        }
    }

    public void doExposedToMildTransitions() {
        for (int locId : sq.getExposedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_YOUNG)) {
                eate.apply(personId);
            }
        }
        for (int locId : sq.getExposedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ADULT)) {
                eate.apply(personId);
            }
        }
        for (int locId : sq.getExposedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ELDERLY)) {
                eate.apply(personId);
            }
        }
    }

    public void doExposedToRecoveredTransitions() {
        for (int locId : sq.getExposedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_YOUNG)) {
                eare.apply(personId);
            }
        }
        for (int locId : sq.getExposedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ADULT)) {
                eare.apply(personId);
            }
        }
        for (int locId : sq.getExposedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ELDERLY)) {
                eare.apply(personId);
            }
        }
    }

    public void doExposedToTracedTransitions() {
        for (int locId : sq.getExposedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_YOUNG)) {
                ete.apply(personId);
            }
        }
        for (int locId : sq.getExposedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ADULT)) {
                ete.apply(personId);
            }
        }
        for (int locId : sq.getExposedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ELDERLY)) {
                ete.apply(personId);
            }
        }
    }

    public void doMildToRecoveredTransitions() {
        for (int locId : sq.getMildInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_YOUNG)) {
                mitre.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ADULT)) {
                mitre.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ELDERLY)) {
                mitre.apply(personId);
            }
        }
    }

    public void doMildToSevereTransitions() {
        for (int locId : sq.getMildInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_YOUNG)) {
                mitste.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ADULT)) {
                mitste.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ELDERLY)) {
                mitste.apply(personId);
            }
        }
    }

    public void doMildToTracedTransitions() {
        for (int locId : sq.getMildInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_YOUNG)) {
                mitte.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ADULT)) {
                mitte.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ELDERLY)) {
                mitte.apply(personId);
            }
        }
    }

    public void doSevereToRecoveredTransitions() {
        for (int locId : sq.getSevereInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG)) {
                sitrte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT)) {
                sitrte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY)) {
                sitrte.apply(personId);
            }
        }
    }

    public void doSevereToHospitalisedTransitions() {
        for (int locId : sq.getSevereInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG)) {
                sithte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT)) {
                sithte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY)) {
                sithte.apply(personId);
            }
        }
    }

    public void doSevereToDeadTransitions() {
        for (int locId : sq.getSevereInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG)) {
                sitdte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT)) {
                sitdte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY)) {
                sitdte.apply(personId);
            }
        }
    }

    public void doSevereToTracedTransitions() {
        for (int locId : sq.getSevereInfectiousYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG)) {
                sittte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT)) {
                sittte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY)) {
                sittte.apply(personId);
            }
        }
    }

    public void doHospitalisedToRecoveredTransitions() {
        for (int locId : sq.getHospitalisedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_YOUNG)) {
                htrte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ADULT)) {
                htrte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ELDERLY)) {
                htrte.apply(personId);
            }
        }
    }

    public void doHospitalisedToDeadTransitions() {
        for (int locId : sq.getHospitalisedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_YOUNG)) {
                htdte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ADULT)) {
                htdte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ELDERLY)) {
                htdte.apply(personId);
            }
        }
    }

    public void doTracedToRecoveredTransitions() {
        for (int locId : sq.getTracedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_YOUNG)) {
                ttrte.apply(personId);
            }
        }
        for (int locId : sq.getTracedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ADULT)) {
                ttrte.apply(personId);
            }
        }
        for (int locId : sq.getTracedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ELDERLY)) {
                ttrte.apply(personId);
            }
        }
    }

    public void doTracedToHospitalisedTransitions() {
        for (int locId : sq.getTracedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_YOUNG)) {
                tthte.apply(personId);
            }
        }
        for (int locId : sq.getTracedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ADULT)) {
                tthte.apply(personId);
            }
        }
        for (int locId : sq.getTracedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ELDERLY)) {
                tthte.apply(personId);
            }
        }
    }

    public void doTracedToDeadTransitions() {
        for (int locId : sq.getTracedYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_YOUNG)) {
                ttdte.apply(personId);
            }
        }
        for (int locId : sq.getTracedAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ADULT)) {
                ttdte.apply(personId);
            }
        }
        for (int locId : sq.getTracedElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ELDERLY)) {
                ttdte.apply(personId);
            }
        }
    }

    public void doRecoveredToSusceptibleTransitions() {
        for (int locId : sq.getRecoveredYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(RECOVERED_YOUNG)) {
                rtste.apply(personId);
            }
        }
        for (int locId : sq.getRecoveredAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(RECOVERED_ADULT)) {
                rtste.apply(personId);
            }
        }
        for (int locId : sq.getRecoveredElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(RECOVERED_ELDERLY)) {
                rtste.apply(personId);
            }
        }
    }

    // Variant transitions start here

    public void doExposedToMildFromVariantTransitions() {
        for (int locId : sq.getExposedToVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_YOUNG_VARIANT)) {
                veate.apply(personId);
            }
        }
        for (int locId : sq.getExposedToVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ADULT_VARIANT)) {
                veate.apply(personId);
            }
        }
        for (int locId : sq.getExposedToVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ELDERLY_VARIANT)) {
                veate.apply(personId);
            }
        }
    }

    public void doExposedToRecoveredFromVariantTransitions() {
        for (int locId : sq.getExposedToVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_YOUNG_VARIANT)) {
                veare.apply(personId);
            }
        }
        for (int locId : sq.getExposedToVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ADULT_VARIANT)) {
                veare.apply(personId);
            }
        }
        for (int locId : sq.getExposedToVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ELDERLY)) {
                veare.apply(personId);
            }
        }
    }

    public void doExposedToTracedFromVariantTransitions() {
        for (int locId : sq.getExposedToVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_YOUNG_VARIANT)) {
                vete.apply(personId);
            }
        }
        for (int locId : sq.getExposedToVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ADULT_VARIANT)) {
                vete.apply(personId);
            }
        }
        for (int locId : sq.getExposedToVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(EXPOSED_ELDERLY_VARIANT)) {
                vete.apply(personId);
            }
        }
    }

    public void doMildToRecoveredFromVariantTransitions() {
        for (int locId : sq.getMildInfectiousFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_YOUNG_VARIANT)) {
                vmitre.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ADULT_VARIANT)) {
                vmitre.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ELDERLY_VARIANT)) {
                vmitre.apply(personId);
            }
        }
    }

    public void doMildToSevereFromVariantTransitions() {
        for (int locId : sq.getMildInfectiousFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_YOUNG_VARIANT)) {
               vmitste.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ADULT_VARIANT)) {
                vmitste.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ELDERLY_VARIANT)) {
                vmitste.apply(personId);
            }
        }
    }

    public void doMildToTracedFromVariantTransitions() {
        for (int locId : sq.getMildInfectiousFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_YOUNG_VARIANT)) {
                vmitte.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ADULT_VARIANT)) {
                vmitte.apply(personId);
            }
        }
        for (int locId : sq.getMildInfectiousFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(MILD_INFECTIOUS_ELDERLY_VARIANT)) {
                vmitte.apply(personId);
            }
        }
    }

    public void doSevereToRecoveredFromVariantTransitions() {
        for (int locId : sq.getSevereInfectiousFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG_VARIANT)) {
                vsitrte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT_VARIANT)) {
                vsitrte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY_VARIANT)) {
                vsitrte.apply(personId);
            }
        }
    }

    public void doSevereToHospitalisedFromVariantTransitions() {
        for (int locId : sq.getSevereInfectiousFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG_VARIANT)) {
                vsithte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT_VARIANT)) {
                vsithte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY_VARIANT)) {
                vsithte.apply(personId);
            }
        }
    }

    public void doSevereToDeadFromVariantTransitions() {
        for (int locId : sq.getSevereInfectiousFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG_VARIANT)) {
                vsitdte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT_VARIANT)) {
                vsitdte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY_VARIANT)) {
                vsitdte.apply(personId);
            }
        }
    }

    public void doSevereToTracedFromVariantTransitions() {
        for (int locId : sq.getSevereInfectiousFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_YOUNG_VARIANT)) {
                vsittte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ADULT_VARIANT)) {
                vsittte.apply(personId);
            }
        }
        for (int locId : sq.getSevereInfectiousFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(SEVERE_INFECTIOUS_ELDERLY_VARIANT)) {
                vsittte.apply(personId);
            }
        }
    }

    public void doHospitalisedToRecoveredFromVariantTransitions() {
        for (int locId : sq.getHospitalisedFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_YOUNG_VARIANT)) {
                vhtrte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ADULT_VARIANT)) {
                vhtrte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ELDERLY_VARIANT)) {
                vhtrte.apply(personId);
            }
        }
    }

    public void doHospitalisedToDeadFromVariantTransitions() {
        for (int locId : sq.getHospitalisedFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_YOUNG_VARIANT)) {
                vhtdte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ADULT_VARIANT)) {
                vhtdte.apply(personId);
            }
        }
        for (int locId : sq.getHospitalisedFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(HOSPITALISED_ELDERLY_VARIANT)) {
                vhtdte.apply(personId);
            }
        }
    }

    public void doTracedToRecoveredFromVariantTransitions() {
        for (int locId : sq.getTracedYoungFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_YOUNG_VARIANT)) {
                vttrte.apply(personId);
            }
        }
        for (int locId : sq.getTracedAdultFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ADULT_VARIANT)) {
                vttrte.apply(personId);
            }
        }
        for (int locId : sq.getTracedElderlyFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ELDERLY_VARIANT)) {
                vttrte.apply(personId);
            }
        }
    }

    public void doTracedToHospitalisedFromVariantTransitions() {
        for (int locId : sq.getTracedYoungFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_YOUNG_VARIANT)) {
                vtthte.apply(personId);
            }
        }
        for (int locId : sq.getTracedAdultFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ADULT_VARIANT)) {
                vtthte.apply(personId);
            }
        }
        for (int locId : sq.getTracedElderlyFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ELDERLY_VARIANT)) {
                vtthte.apply(personId);
            }
        }
    }

    public void doTracedToDeadFromVariantTransitions() {
        for (int locId : sq.getTracedYoungFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_YOUNG_VARIANT)) {
                vttdte.apply(personId);
            }
        }
        for (int locId : sq.getTracedAdultFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ADULT_VARIANT)) {
                vttdte.apply(personId);
            }
        }
        for (int locId : sq.getTracedElderlyFromVariantLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(TRACED_ELDERLY_VARIANT)) {
                vttdte.apply(personId);
            }
        }
    }

    public void doRecoveredToSusceptibleFromVariantTransitions() {
        for (int locId : sq.getRecoveredFromVariantYoungLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(RECOVERED_YOUNG_VARIANT)) {
                vrtste.apply(personId);
            }
        }
        for (int locId : sq.getRecoveredFromVariantAdultLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(RECOVERED_ADULT_VARIANT)) {
                vrtste.apply(personId);
            }
        }
        for (int locId : sq.getRecoveredFromVariantElderlyLocations()) {
            LocalPopulation localPop = sq.getCopyOfLocalPopulation(locId);
            for (int personId : localPop.getAllInState(RECOVERED_ELDERLY_VARIANT)) {
                vrtste.apply(personId);
            }
        }
    }
}
