package scovmod.model.vaccination;

import it.unimi.dsi.fastutil.ints.*;
import scovmod.model.input.JsonVaccinationsReader;
import scovmod.model.input.StartLocationsAndAgeClasses;
import scovmod.model.input.config.ConfigParameters;
import scovmod.model.input.config.Parameters;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.state.StateModifier;
import scovmod.model.state.StateQuery;
import scovmod.model.state.infection.InfectionState;
import scovmod.model.time.TimeManager;
import scovmod.model.util.math.AliasMethod;
import scovmod.model.util.math.AliasMethodFactory;
import scovmod.model.util.math.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VaccinationManager {

    private Random rand;
    private Int2ObjectMap<Set> peopleToVaccinateMap;
    private TimeManager tm;
    private ConfigParameters config;
    private Parameters params;
    private StateQuery sq;
    private StateModifier sm;
    private Int2ObjectMap<AgeClass> peopleAgeClasses;
    private Int2IntMap vaccinatedPeople = new Int2IntOpenHashMap();
    private Int2IntMap vaccinationDosePerPerson = new Int2IntOpenHashMap();
    private Int2IntMap peopleWithLossOfImmunity = new Int2IntOpenHashMap();
    private Int2ObjectOpenHashMap<EfficacyProtection> vaccinatedPersonEfficacy;
    private Int2ObjectOpenHashMap<VariantEfficacyProtection> variantVaccinatedPersonEfficacy;
    private List<EfficacyProtection> efficacyProtectionIndex;
    private List<VariantEfficacyProtection> variantEfficacyProtectionIndex;
    private AliasMethod am, am_variant;

    public VaccinationManager(
            Random rand,
            JsonVaccinationsReader pv,
            TimeManager tm,
            ConfigParameters config,
            StateQuery sq,
            StateModifier sm,
            StartLocationsAndAgeClasses sl,
            Parameters params,
            AliasMethodFactory amf) {
        this.rand = rand;
        peopleToVaccinateMap = pv.getPeopleToVaccinateMap();
        vaccinatedPersonEfficacy = new Int2ObjectOpenHashMap<>();
        variantVaccinatedPersonEfficacy = new Int2ObjectOpenHashMap<>();
        this.vaccinationDosePerPerson = new Int2IntOpenHashMap();
        this.tm = tm;
        this.config = config;
        this.params = params;
        this.sq = sq;
        this.sm = sm;
        peopleAgeClasses = sl.getPeopleAgeClasses();
        //Build alias table - standard efficacy
        List<Double> efficacyProbs = new ArrayList<>();
        efficacyProbs.add(1 - params.getPartialVersusFullProtectionProp());
        efficacyProbs.add(params.getPartialVersusFullProtectionProp());
        efficacyProtectionIndex = new ArrayList<>();
        efficacyProtectionIndex.add(EfficacyProtection.FULL);
        efficacyProtectionIndex.add(EfficacyProtection.PARTIAL);
        am = amf.build();
        am.initialise(efficacyProbs);
        //Build alias table - new variant
        List<Double> variantEfficacyProbs = new ArrayList<>();
        variantEfficacyProbs.add(1 - params.getPartialVersusFullProtectionProp_variant());
        variantEfficacyProbs.add(params.getPartialVersusFullProtectionProp_variant());
        variantEfficacyProtectionIndex = new ArrayList<>();
        variantEfficacyProtectionIndex.add(VariantEfficacyProtection.FULL);
        variantEfficacyProtectionIndex.add(VariantEfficacyProtection.PARTIAL);
        am_variant = amf.build();
        am_variant.initialise(variantEfficacyProbs);
    }

    private EfficacyProtection sampleEfficacyLevel() {
        return efficacyProtectionIndex.get(am.next());
    }
    private VariantEfficacyProtection sampleVariantEfficacyLevel() {return variantEfficacyProtectionIndex.get(am_variant.next());}
    public EfficacyProtection getVaccinationEfficacy(int personID) {return vaccinatedPersonEfficacy.get(personID);}
    public VariantEfficacyProtection getVariantVaccinationEfficacy(int personID) {return variantVaccinatedPersonEfficacy.get(personID);}

    public void makeProtectionPartialAfterImmunityLoss(int personID) {
        if (this.vaccinatedPeople.containsKey(personID)) {
            vaccinatedPersonEfficacy.put(personID, EfficacyProtection.PARTIAL_AFTER_LOSS_OF_VACCINATED_IMMUNITY);
        } else {
            vaccinatedPersonEfficacy.put(personID, EfficacyProtection.PARTIAL_AFTER_LOSS_OF_NATURAL_IMMUNITY);
        }
    }

    public void recordLossOfImmunityEvent(int personID){
        peopleWithLossOfImmunity.put(personID, (int) tm.getTimeStep());
    }

    public void vaccinatePeoplePerTimestep() {
        if (peopleToVaccinateMap.containsKey((int) tm.getTimeStep())) { //There are vaccinations for this timestep
            Set<Integer> peopleToSeed = this.peopleToVaccinateMap.get((int) tm.getTimeStep());
            for (int personID : peopleToSeed) {
                if(!vaccinatedPeople.containsKey(personID)){
                    vaccinationDosePerPerson.put(personID,1);//this is a persons first dose
                }
                else{
                    int doseNumber = vaccinationDosePerPerson.get(personID);
                    vaccinationDosePerPerson.put(personID,doseNumber+1);
                }
                vaccinatedPeople.put(personID, (int) tm.getTimeStep());
                EfficacyProtection efficacyProtection = sampleEfficacyLevel();
                vaccinatedPersonEfficacy.put(personID, efficacyProtection);
                VariantEfficacyProtection variantEfficacyProtection = sampleVariantEfficacyLevel();
                variantVaccinatedPersonEfficacy.put(personID, variantEfficacyProtection);
                if (efficacyProtection == EfficacyProtection.FULL) {
                    AgeClass ageClass = peopleAgeClasses.get(personID);
                    //Get what age group the person is in
                    switch (ageClass) {
                        case YOUNG:
                            if(sq.getPersonInfectionStatus(personID)==InfectionState.SUSCEPTIBLE) {
                                sm.updateInfectionState(personID, InfectionState.RECOVERED_YOUNG);
                            }
                            break;
                        case ADULT:
                            if(sq.getPersonInfectionStatus(personID)==InfectionState.SUSCEPTIBLE) {
                                sm.updateInfectionState(personID, InfectionState.RECOVERED_ADULT);
                            }
                            break;
                        case ELDERLY:
                            if(sq.getPersonInfectionStatus(personID)==InfectionState.SUSCEPTIBLE) {
                                sm.updateInfectionState(personID, InfectionState.RECOVERED_ELDERLY);
                            }
                            break;
                        default:
                            throw new UnsupportedOperationException("Age class not known- for vaccination event");
                    }
                }
            }
        }
    }

    public void initialiseHistoricVaccinations() {
        for (int timestep = config.getVaccineStartTimestep(); timestep < config.getFirstTimeStep(); timestep++) { // Cover period from vaccine start to start of model
            if (peopleToVaccinateMap.containsKey(timestep)) { //There are vaccinations for this timestep
                Set<Integer> peopleToSeed = this.peopleToVaccinateMap.get(timestep);
                for (int personID : peopleToSeed) {
                    vaccinatedPeople.put(personID, timestep);
                    EfficacyProtection efficacyProtection = sampleEfficacyLevel();
                    vaccinatedPersonEfficacy.put(personID, efficacyProtection);
                    VariantEfficacyProtection variantEfficacyProtection = sampleVariantEfficacyLevel();
                    variantVaccinatedPersonEfficacy.put(personID, variantEfficacyProtection);
                    if (efficacyProtection == EfficacyProtection.FULL) {
                        AgeClass ageClass = peopleAgeClasses.get(personID);
                        //Get what age group the person is in
                        switch (ageClass) {
                            case YOUNG:
                                if(sq.getPersonInfectionStatus(personID)==InfectionState.SUSCEPTIBLE) {
                                    sm.updateInfectionState(personID, InfectionState.RECOVERED_YOUNG);
                                }
                                break;
                            case ADULT:
                                if(sq.getPersonInfectionStatus(personID)==InfectionState.SUSCEPTIBLE) {
                                    sm.updateInfectionState(personID, InfectionState.RECOVERED_ADULT);
                                }
                                break;
                            case ELDERLY:
                                if(sq.getPersonInfectionStatus(personID)==InfectionState.SUSCEPTIBLE) {
                                    sm.updateInfectionState(personID, InfectionState.RECOVERED_ELDERLY);
                                }
                                break;
                            default:
                                throw new UnsupportedOperationException("Age class not known- for vaccination event");
                        }
                    }
                }
            }
        }
    }

    public Int2ObjectOpenHashMap<EfficacyProtection> getVaccinatedPersonEfficacyMap() {
        return vaccinatedPersonEfficacy;
    }

    public Int2IntMap getVaccinatedPeople() {
        return vaccinatedPeople;
    }
    public Int2IntMap getPeopleWithLossOfImmunityEvents() {
        return peopleWithLossOfImmunity;
    }

    public int getCurrentVaccinationDose(int personID){
        if(!vaccinationDosePerPerson.containsKey(personID)){
            return 0;
        }
        else {
            return vaccinationDosePerPerson.get(personID);
        }
    }
}
