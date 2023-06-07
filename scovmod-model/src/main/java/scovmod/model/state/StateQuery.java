package scovmod.model.state;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.AreaLevels;
import scovmod.model.input.seeding.AgeClass;
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
import scovmod.model.state.infection.InfectionState;
import scovmod.model.state.cache.exposed.*;
import scovmod.model.state.cache.dead.*;
import scovmod.model.state.cache.hospitalised.*;
import scovmod.model.state.cache.recovered.*;
import scovmod.model.state.cache.mildInfectious.*;
import scovmod.model.state.cache.severeInfectious.*;
import scovmod.model.state.population.LocalPopulation;
import scovmod.model.state.population.LocalPopulationIndex;
import scovmod.model.util.math.Random;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.List;

public class StateQuery {

    private LocalPopulationIndex lpi;
    private Random rnd;
    private HealthBoardLookup hbl;
    private ExposedYoungLocations eyl;
    private ExposedAdultLocations eal;
    private ExposedElderlyLocations eel;

    private DeadYoungLocations dyl;
    private DeadAdultLocations dal;
    private DeadElderlyLocations del;

    private HospitalisedYoungLocations hyl;
    private HospitalisedAdultLocations hal;
    private HospitalisedElderlyLocations hel;

    private RecoveredYoungLocations ryl;
    private RecoveredAdultLocations ral;
    private RecoveredElderlyLocations rel;

    private MildInfectiousYoungLocations miyl;
    private MildInfectiousAdultLocations mial;
    private MildInfectiousElderlyLocations miel;

    private SevereInfectiousYoungLocations siyl;
    private SevereInfectiousAdultLocations sial;
    private SevereInfectiousElderlyLocations siel;

    private TracedYoungLocations tyl;
    private TracedAdultLocations tal;
    private TracedElderlyLocations tel;

    private ExposedToVariantYoungLocations eylv;
    private ExposedToVariantAdultLocations ealv;
    private ExposedToVariantElderlyLocations eelv;

    private DeadFromVariantYoungLocations dylv;
    private DeadFromVariantAdultLocations dalv;
    private DeadFromVariantElderlyLocations delv;

    private HospitalisedFromVariantYoungLocations hylv;
    private HospitalisedFromVariantAdultLocations halv;
    private HospitalisedFromVariantElderlyLocations helv;

    private RecoveredFromVariantYoungLocations rylv;
    private RecoveredFromVariantAdultLocations ralv;
    private RecoveredFromVariantElderlyLocations relv;

    private MildInfectiousFromVariantYoungLocations miylv;
    private MildInfectiousFromVariantAdultLocations mialv;
    private MildInfectiousFromVariantElderlyLocations mielv;

    private SevereInfectiousFromVariantYoungLocations siylv;
    private SevereInfectiousFromVariantAdultLocations sialv;
    private SevereInfectiousFromVariantElderlyLocations sielv;

    private TracedFromVariantYoungLocations tylv;
    private TracedFromVariantAdultLocations talv;
    private TracedFromVariantElderlyLocations telv;

    private Int2ObjectMap<AgeClass> peopleAgeClasses;

    public StateQuery(
            LocalPopulationIndex lpi,
            HealthBoardLookup hbl,
            ExposedYoungLocations eyl,
            ExposedAdultLocations eal,
            ExposedElderlyLocations eel,
            DeadYoungLocations dyl,
            DeadAdultLocations dal,
            DeadElderlyLocations del,
            HospitalisedYoungLocations hyl,
            HospitalisedAdultLocations hal,
            HospitalisedElderlyLocations hel,
            RecoveredYoungLocations ryl,
            RecoveredAdultLocations ral,
            RecoveredElderlyLocations rel,
            MildInfectiousYoungLocations miyl,
            MildInfectiousAdultLocations mial,
            MildInfectiousElderlyLocations miel,
            SevereInfectiousYoungLocations siyl,
            SevereInfectiousAdultLocations sial,
            SevereInfectiousElderlyLocations siel,
            TracedYoungLocations tyl,
            TracedAdultLocations tal,
            TracedElderlyLocations tel,
            ExposedToVariantYoungLocations eylv,
            ExposedToVariantAdultLocations ealv,
            ExposedToVariantElderlyLocations eelv,
            DeadFromVariantYoungLocations dylv,
            DeadFromVariantAdultLocations dalv,
            DeadFromVariantElderlyLocations delv,
            HospitalisedFromVariantYoungLocations hylv,
            HospitalisedFromVariantAdultLocations halv,
            HospitalisedFromVariantElderlyLocations helv,
            RecoveredFromVariantYoungLocations rylv,
            RecoveredFromVariantAdultLocations ralv,
            RecoveredFromVariantElderlyLocations relv,
            MildInfectiousFromVariantYoungLocations miylv,
            MildInfectiousFromVariantAdultLocations mialv,
            MildInfectiousFromVariantElderlyLocations mielv,
            SevereInfectiousFromVariantYoungLocations siylv,
            SevereInfectiousFromVariantAdultLocations sialv,
            SevereInfectiousFromVariantElderlyLocations sielv,
            TracedFromVariantYoungLocations tylv,
            TracedFromVariantAdultLocations talv,
            TracedFromVariantElderlyLocations telv,
            Random rnd,
            StartLocationsAndAgeClasses sl) {
        this.lpi = lpi;
        this.hbl = hbl;
        this.tyl = tyl;
        this.tal = tal;
        this.tel = tel;
        this.rnd = rnd;
        this.eyl = eyl;
        this.eal = eal;
        this.eel = eel;
        this.dyl = dyl;
        this.dal = dal;
        this.del = del;
        this.hyl = hyl;
        this.hal = hal;
        this.hel = hel;
        this.ryl = ryl;
        this.ral = ral;
        this.rel = rel;
        this.miyl = miyl;
        this.mial = mial;
        this.miel = miel;
        this.siyl = siyl;
        this.sial = sial;
        this.siel = siel;
        this.eylv = eylv;
        this.ealv = ealv;
        this.eelv = eelv;
        this.dylv = dylv;
        this.dalv = dalv;
        this.delv = delv;
        this.hylv = hylv;
        this.halv = halv;
        this.helv = helv;
        this.rylv = rylv;
        this.ralv = ralv;
        this.relv = relv;
        this.miylv = miylv;
        this.mialv = mialv;
        this.mielv = mielv;
        this.siylv = siylv;
        this.sialv = sialv;
        this.sielv = sielv;
        this.tylv = tylv;
        this.talv = talv;
        this.telv = telv;
        peopleAgeClasses = sl.getPeopleAgeClasses();
    }

    public IntSet getAllActiveLocationIds() {
        return lpi.getAllLocationIds();
    }

    public int getRandomPersonAtLocation(int locationId, InfectionState state) {
        return rnd.sampleOne(lpi.getLocalPopulation(locationId).getAllInState(state));
    }

    public IntSet getAllSusceptiblePersonsInArea(AreaLevels levelToSeed, int locationToSeed) {
        IntSet susPeopleAtLocations = new IntOpenHashSet();
        for (int location : getAllLocationsInArea(levelToSeed,locationToSeed)) {
            LocalPopulation localPop = lpi.getLocalPopulation(location);
            susPeopleAtLocations.addAll(localPop.getAllInState(InfectionState.SUSCEPTIBLE));
        }
        return susPeopleAtLocations;
    }

    public IntSet getAllSusceptiblePersonsInAreaInAgeGroup(AreaLevels levelToSeed, int locationToSeed, AgeClass ageClass) {
        IntSet susPeopleAtLocations = new IntOpenHashSet();
        IntSet susPeopleAtLocationsInAgeGroup = new IntOpenHashSet();
        for (int location : getAllLocationsInArea(levelToSeed,locationToSeed)) {
            LocalPopulation localPop = lpi.getLocalPopulation(location);
            susPeopleAtLocations.addAll(localPop.getAllInState(InfectionState.SUSCEPTIBLE));
        }
        for(int susPerson:susPeopleAtLocations){
            if(peopleAgeClasses.get(susPerson)==ageClass) susPeopleAtLocationsInAgeGroup.add(susPerson);
        }
        return susPeopleAtLocationsInAgeGroup;
    }

    // NNB TODO Test this method
    public IntSet getAllLocationsInArea(AreaLevels levelToTrack,int areaID) {
        IntSet locations = new IntOpenHashSet();
        switch (levelToTrack) {
            case HB:
                Int2ObjectMap<List<Integer>> oasByHB = hbl.getOasByHB();
                List<Integer> oasInHB = oasByHB.get(areaID);
                locations.addAll(oasInHB);
                break;
            case CA:
                Int2ObjectMap<List<Integer>> oasByCA = hbl.getOasByLA();
                List<Integer> oasInCA = oasByCA.get(areaID);
                locations.addAll(oasInCA);
                break;
            case IZ:
                Int2ObjectMap<List<Integer>> oasByIZ = hbl.getOasByIZ();
                List<Integer> oasInIZ = oasByIZ.get(areaID);
                locations.addAll(oasInIZ);
                break;
            case DZ:
                Int2ObjectMap<List<Integer>> oasByDZ = hbl.getOasByDZ();
                List<Integer> oasInDZ = oasByDZ.get(areaID);
                locations.addAll(oasInDZ);
                break;
            case OA:
                locations.add(areaID);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported reseeding level type: " + levelToTrack);
        }
        return locations;
    }

    //Temporary
    public IntSet combine(IntSet... sets)
    {
        IntSet collection = new IntOpenHashSet();
        for (IntSet e: sets)
            collection.addAll(e);
        return collection;
    }

    public IntSet getAllInfectiousLocations(){
        return combine(getMildInfectiousYoungLocations(),getMildInfectiousAdultLocations(),getMildInfectiousElderlyLocations(),
                getSevereInfectiousYoungLocations(),getSevereInfectiousAdultLocations(),getSevereInfectiousElderlyLocations());
    }

    public IntSet getAllHospitalisedLocations(){
        return combine(getHospitalisedYoungLocations(),getHospitalisedAdultLocations(),getHospitalisedElderlyLocations());
    }

    public IntSet getAllExposedLocations(){
        return combine(getExposedYoungLocations(),getExposedAdultLocations(),getExposedElderlyLocations());
    }

    public IntSet getAllExposedVariantLocations(){
        return combine(getExposedToVariantYoungLocations(),getExposedToVariantAdultLocations(),getExposedToVariantElderlyLocations());
    }

    public IntSet getAllMildInfectiousLocations(){
        return combine(getMildInfectiousYoungLocations(),getMildInfectiousAdultLocations(),getMildInfectiousElderlyLocations());
    }

    public IntSet getAllMildInfectiousFromVariantLocations(){
        return combine(getMildInfectiousFromVariantYoungLocations(),getMildInfectiousFromVariantAdultLocations(),getMildInfectiousFromVariantElderlyLocations());
    }

    public IntSet getAllSevereInfectiousLocations(){
        return combine(getSevereInfectiousYoungLocations(),getSevereInfectiousAdultLocations(),getSevereInfectiousElderlyLocations());
    }

    public IntSet getAllSevereInfectiousFromVariantLocations(){
        return combine(getSevereInfectiousFromVariantYoungLocations(),getSevereInfectiousFromVariantAdultLocations(),getSevereInfectiousFromVariantElderlyLocations());
    }

    public IntSet getAllTracedLocations(){
        return combine(getTracedYoungLocations(),getTracedAdultLocations(),getTracedElderlyLocations());
    }

    public IntSet getExposedYoungLocations() {
        return eyl.getExposedYoungLocations();
    }

    public IntSet getExposedAdultLocations() {
        return eal.getExposedAdultLocations();
    }

    public IntSet getExposedElderlyLocations() {
        return eel.getExposedElderlyLocations();
    }

    public IntSet getDeadYoungLocations() {
        return dyl.getDeadYoungLocations();
    }

    public IntSet getDeadAdultLocations() {
        return dal.getDeadAdultLocations();
    }

    public IntSet getDeadElderlyLocations() {
        return del.getDeadElderlyLocations();
    }

    public IntSet getHospitalisedYoungLocations() {
        return hyl.getHospitalisedYoungLocations();
    }

    public IntSet getHospitalisedAdultLocations() {
        return hal.getHospitaliseAdultLocations();
    }

    public IntSet getHospitalisedElderlyLocations() {
        return hel.getHospitalisedElderlyLocations();
    }

    public IntSet getRecoveredYoungLocations() {
        return ryl.getRecoveredYoungLocations();
    }

    public IntSet getRecoveredAdultLocations() {
        return ral.getRecoveredAdultLocations();
    }

    public IntSet getRecoveredElderlyLocations() {
        return rel.getRecoveredElderlyLocations();
    }

    public IntSet getMildInfectiousYoungLocations() { return miyl.getMildInfectiousYoungLocations(); }

    public IntSet getMildInfectiousAdultLocations() {
        return mial.getMildInfectiousAdultLocations();
    }

    public IntSet getMildInfectiousElderlyLocations() {
        return miel.getMildInfectiousElderlyLocations();
    }

    public IntSet getSevereInfectiousYoungLocations() {
        return siyl.getSevereInfectiousYoungLocations();
    }

    public IntSet getSevereInfectiousAdultLocations() {
        return sial.getSevereInfectiousAdultLocations();
    }

    public IntSet getSevereInfectiousElderlyLocations() {
        return siel.getSevereInfectiousElderlyLocations();
    }

    public IntSet getTracedYoungLocations() { return tyl.getTracedYoungLocations(); }

    public IntSet getTracedAdultLocations() { return tal.getTracedAdultLocations();}

    public IntSet getTracedElderlyLocations() { return tel.getTracedElderlyLocations(); }

    public IntSet getExposedToVariantYoungLocations() {
        return eylv.getExposedToVariantYoungLocations();
    }

    public IntSet getExposedToVariantAdultLocations() {
        return ealv.getExposedToVariantAdultLocations();
    }

    public IntSet getExposedToVariantElderlyLocations() {
        return eelv.getExposedToVariantElderlyLocations();
    }

    public IntSet getDeadFromVariantYoungLocations() {
        return dylv.getDeadFromVariantYoungLocations();
    }

    public IntSet getDeadFromVariantAdultLocations() {
        return dalv.getDeadFromVariantAdultLocations();
    }

    public IntSet getDeadFromVariantElderlyLocations() {
        return delv.getDeadFromVariantElderlyLocations();
    }

    public IntSet getHospitalisedFromVariantYoungLocations() {
        return hylv.getHospitalisedFromVariantYoungLocations();
    }

    public IntSet getHospitalisedFromVariantAdultLocations() {
        return halv.getHospitalisedFromVariantAdultLocations();
    }

    public IntSet getHospitalisedFromVariantElderlyLocations() { return helv.getHospitalisedFromVariantElderlyLocations(); }

    public IntSet getRecoveredFromVariantYoungLocations() {
        return rylv.getRecoveredFromVariantYoungLocations();
    }

    public IntSet getRecoveredFromVariantAdultLocations() {
        return ralv.getRecoveredFromVariantAdultLocations();
    }

    public IntSet getRecoveredFromVariantElderlyLocations() {
        return relv.getRecoveredFromVariantElderlyLocations();
    }

    public IntSet getMildInfectiousFromVariantYoungLocations() { return miylv.getMildInfectiousFromVariantYoungLocations(); }

    public IntSet getMildInfectiousFromVariantAdultLocations() {
        return mialv.getMildInfectiousFromVariantAdultLocations();
    }

    public IntSet getMildInfectiousFromVariantElderlyLocations() {
        return mielv.getMildInfectiousFromVariantElderlyLocations();
    }

    public IntSet getSevereInfectiousFromVariantYoungLocations() {
        return siylv.getSevereInfectiousFromVariantYoungLocations();
    }

    public IntSet getSevereInfectiousFromVariantAdultLocations() {
        return sialv.getSevereInfectiousFromVariantAdultLocations();
    }

    public IntSet getSevereInfectiousFromVariantElderlyLocations() {
        return sielv.getSevereInfectiousFromVariantElderlyLocations();
    }

    public IntSet getTracedYoungFromVariantLocations() { return tylv.getFromVariantTracedYoungLocations(); }

    public IntSet getTracedAdultFromVariantLocations() { return talv.getTracedFromVariantAdultLocations();}

    public IntSet getTracedElderlyFromVariantLocations() { return telv.getTracedFromVariantElderlyLocations(); }

    public LocalPopulation getCopyOfLocalPopulation(int locationId) {
        return lpi.getLocalPopulation(locationId);
    }

    public InfectionState getPersonInfectionStatus(int personId) {
        return lpi.getPersonInfectionStatus(personId);
    }

    public  boolean isShowingSevereSymptoms(int personId) {
        InfectionState state = lpi.getPersonInfectionStatus(personId);
        if(state==InfectionState.SEVERE_INFECTIOUS_YOUNG
                || state==InfectionState.SEVERE_INFECTIOUS_ADULT
                || state==InfectionState.SEVERE_INFECTIOUS_ELDERLY
                || state==InfectionState.HOSPITALISED_YOUNG
                || state==InfectionState.HOSPITALISED_ADULT
                || state==InfectionState.HOSPITALISED_ELDERLY
                || state==InfectionState.TRACED_YOUNG
                || state==InfectionState.TRACED_ADULT
                || state==InfectionState.TRACED_ELDERLY
                || state==InfectionState.DEAD_YOUNG
                || state==InfectionState.DEAD_ADULT
                || state==InfectionState.DEAD_ELDERLY){ return true;}
        else {return false;}
    }

    public int getPersonLocation(int personId) {
        return lpi.getPersonLocationId(personId);
    }

    public int getLocationSize(int locationId) {
        return getCopyOfLocalPopulation(locationId).getSize();
    }
}
