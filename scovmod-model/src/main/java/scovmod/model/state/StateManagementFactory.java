package scovmod.model.state;

import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.output.HealthBoardLookup;
import scovmod.model.state.cache.traced.TracedAdultLocations;
import scovmod.model.state.cache.traced.TracedElderlyLocations;
import scovmod.model.state.cache.traced.TracedYoungLocations;
import scovmod.model.state.cache.variant.dead.DeadFromVariantAdultLocations;
import scovmod.model.state.cache.variant.dead.DeadFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.dead.DeadFromVariantYoungLocations;
import scovmod.model.state.cache.variant.exposed.ExposedToVariantAdultLocations;
import scovmod.model.state.cache.variant.exposed.ExposedToVariantElderlyLocations;
import scovmod.model.state.cache.variant.exposed.ExposedToVariantYoungLocations;
import scovmod.model.state.cache.variant.hospitalised.HospitalisedFromVariantAdultLocations;
import scovmod.model.state.cache.variant.hospitalised.HospitalisedFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.hospitalised.HospitalisedFromVariantYoungLocations;
import scovmod.model.state.cache.variant.mildInfectious.MildInfectiousFromVariantAdultLocations;
import scovmod.model.state.cache.variant.mildInfectious.MildInfectiousFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.mildInfectious.MildInfectiousFromVariantYoungLocations;
import scovmod.model.state.cache.variant.recovered.RecoveredFromVariantAdultLocations;
import scovmod.model.state.cache.variant.recovered.RecoveredFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.recovered.RecoveredFromVariantYoungLocations;
import scovmod.model.state.cache.variant.severeInfectious.SevereInfectiousFromVariantAdultLocations;
import scovmod.model.state.cache.variant.severeInfectious.SevereInfectiousFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.severeInfectious.SevereInfectiousFromVariantYoungLocations;
import scovmod.model.state.cache.variant.traced.TracedFromVariantAdultLocations;
import scovmod.model.state.cache.variant.traced.TracedFromVariantElderlyLocations;
import scovmod.model.state.cache.variant.traced.TracedFromVariantYoungLocations;
import scovmod.model.state.infection.InfectionTransitions;
import scovmod.model.state.cache.CacheManager;
import scovmod.model.state.cache.exposed.*;
import scovmod.model.state.cache.dead.*;
import scovmod.model.state.cache.hospitalised.*;
import scovmod.model.state.cache.recovered.*;
import scovmod.model.state.cache.mildInfectious.*;
import scovmod.model.state.cache.severeInfectious.*;
import scovmod.model.state.coordination.InfectionStateCoordinator;
import scovmod.model.state.coordination.MovementCoordinator;
import scovmod.model.state.population.LocalPopulationBank;
import scovmod.model.state.population.LocalPopulationIndex;
import scovmod.model.util.math.Random;

public class StateManagementFactory {

    private StateModifier sm = null;
    private StateQuery sq = null;

    public StateManagementFactory(Random rnd, HealthBoardLookup hbl, StartLocationsAndAgeClasses sl) {
        LocalPopulationBank lpb = new LocalPopulationBank();
        LocalPopulationIndex lpi = new LocalPopulationIndex(lpb);

        ExposedYoungLocations eyl = new ExposedYoungLocations();
        ExposedAdultLocations eal = new ExposedAdultLocations();
        ExposedElderlyLocations eel = new ExposedElderlyLocations();

        DeadYoungLocations dyl = new DeadYoungLocations();
        DeadAdultLocations dal = new DeadAdultLocations();
        DeadElderlyLocations del = new DeadElderlyLocations();

        HospitalisedYoungLocations hyl = new HospitalisedYoungLocations();
        HospitalisedAdultLocations hal = new HospitalisedAdultLocations();
        HospitalisedElderlyLocations hel = new HospitalisedElderlyLocations();

        RecoveredYoungLocations ryl = new RecoveredYoungLocations();
        RecoveredAdultLocations ral = new RecoveredAdultLocations();
        RecoveredElderlyLocations rel = new RecoveredElderlyLocations();

        MildInfectiousYoungLocations miyl = new MildInfectiousYoungLocations();
        MildInfectiousAdultLocations mial = new MildInfectiousAdultLocations();
        MildInfectiousElderlyLocations miel = new MildInfectiousElderlyLocations();

        SevereInfectiousYoungLocations siyl = new SevereInfectiousYoungLocations();
        SevereInfectiousAdultLocations sial = new SevereInfectiousAdultLocations();
        SevereInfectiousElderlyLocations siel = new SevereInfectiousElderlyLocations();

        TracedYoungLocations tyl = new TracedYoungLocations();
        TracedAdultLocations tal = new TracedAdultLocations();
        TracedElderlyLocations tel = new TracedElderlyLocations();

        ExposedToVariantYoungLocations eylv= new ExposedToVariantYoungLocations();
        ExposedToVariantAdultLocations ealv = new ExposedToVariantAdultLocations();
        ExposedToVariantElderlyLocations eelv = new ExposedToVariantElderlyLocations();

        DeadFromVariantYoungLocations dylv = new DeadFromVariantYoungLocations();
        DeadFromVariantAdultLocations dalv = new DeadFromVariantAdultLocations();
        DeadFromVariantElderlyLocations delv = new DeadFromVariantElderlyLocations();

        HospitalisedFromVariantYoungLocations hylv = new HospitalisedFromVariantYoungLocations();
        HospitalisedFromVariantAdultLocations halv = new HospitalisedFromVariantAdultLocations();
        HospitalisedFromVariantElderlyLocations helv = new HospitalisedFromVariantElderlyLocations();

        RecoveredFromVariantYoungLocations rylv = new RecoveredFromVariantYoungLocations();
        RecoveredFromVariantAdultLocations ralv = new RecoveredFromVariantAdultLocations();
        RecoveredFromVariantElderlyLocations relv = new RecoveredFromVariantElderlyLocations();

        MildInfectiousFromVariantYoungLocations miylv = new MildInfectiousFromVariantYoungLocations();
        MildInfectiousFromVariantAdultLocations mialv = new MildInfectiousFromVariantAdultLocations();
        MildInfectiousFromVariantElderlyLocations mielv = new MildInfectiousFromVariantElderlyLocations();

        SevereInfectiousFromVariantYoungLocations siylv = new SevereInfectiousFromVariantYoungLocations();
        SevereInfectiousFromVariantAdultLocations sialv = new SevereInfectiousFromVariantAdultLocations();
        SevereInfectiousFromVariantElderlyLocations sielv = new SevereInfectiousFromVariantElderlyLocations();

        TracedFromVariantYoungLocations tylv = new TracedFromVariantYoungLocations();
        TracedFromVariantAdultLocations talv = new TracedFromVariantAdultLocations();
        TracedFromVariantElderlyLocations telv = new TracedFromVariantElderlyLocations();

        CacheManager cm = new CacheManager(lpi, eyl, eal, eel, miyl, mial, miel, siyl, sial, siel, hyl, hal, hel, ryl, ral, rel, dyl, dal,del,tyl,tal,tel,eylv,ealv,eelv,dylv, dalv,delv,hylv,halv,helv,rylv,ralv,relv,miylv,mialv,mielv,siylv,sialv,sielv,tylv,talv,telv);
        MovementCoordinator mc = new MovementCoordinator(lpi, cm);
        InfectionTransitions it = new InfectionTransitions();
        InfectionStateCoordinator isc = new InfectionStateCoordinator(lpi, it, cm);

        sm = new StateModifier(mc, isc);
        sq = new StateQuery(lpi, hbl,eyl, eal, eel, dyl, dal,del, hyl, hal, hel, ryl, ral, rel, miyl, mial, miel, siyl, sial, siel, tyl,tal,tel,eylv,ealv,eelv,dylv, dalv,delv,hylv,halv,helv,rylv,ralv,relv,miylv,mialv,mielv,siylv,sialv,sielv,tylv,talv,telv,rnd,sl);
    }

    public StateModifier getStateModifier() {
        return sm;
    }

    public StateQuery getStateQuery() {
        return sq;
    }
}
