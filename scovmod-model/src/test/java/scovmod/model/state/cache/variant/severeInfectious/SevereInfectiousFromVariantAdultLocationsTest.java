package scovmod.model.state.cache.variant.severeInfectious;

import org.junit.Test;
import scovmod.model.state.cache.severeInfectious.SevereInfectiousAdultLocations;
import scovmod.test.FakeLocalPopulation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static scovmod.model.state.infection.InfectionState.*;

public class SevereInfectiousFromVariantAdultLocationsTest {

	private final int LOCATION_1 = 100;
	private final int LOCATION_2 = 200;

	private final int PERSON_1 = 100;
	private final int PERSON_2 = 200;
	private final int PERSON_3 = 300;
	private final int PERSON_4 = 140;
	private final int PERSON_5 = 500;

	private final Set<Integer> NONE = setOf();

	@Test
	public void updatingByWholeLocationStatus(){
		SevereInfectiousFromVariantAdultLocations instance = new SevereInfectiousFromVariantAdultLocations();

		FakeLocalPopulation location1pop = new FakeLocalPopulation();
		location1pop.testSet(PERSON_1, SUSCEPTIBLE);
		location1pop.testSet(PERSON_2, EXPOSED_YOUNG);
		location1pop.testSet(PERSON_3, EXPOSED_ELDERLY);
		instance.wholeLocationUpdate(LOCATION_1, location1pop);

		assertEquals(NONE, instance.getSevereInfectiousFromVariantAdultLocations());

		FakeLocalPopulation location2pop = new FakeLocalPopulation();
		location2pop.testSet(PERSON_5, SEVERE_INFECTIOUS_ADULT_VARIANT);
		instance.wholeLocationUpdate(LOCATION_2, location2pop);

		assertEquals(setOf(LOCATION_2), instance.getSevereInfectiousFromVariantAdultLocations());

		FakeLocalPopulation location1updatedPop = new FakeLocalPopulation();
		location1updatedPop.testSet(PERSON_1, SUSCEPTIBLE);
		location1updatedPop.testSet(PERSON_3,SEVERE_INFECTIOUS_ADULT_VARIANT);
		instance.wholeLocationUpdate(LOCATION_1, location1updatedPop);

		assertEquals(setOf(LOCATION_2, LOCATION_1), instance.getSevereInfectiousFromVariantAdultLocations());
	}

	@Test
	public void edgeCaseUpdatesDoNothing(){
		SevereInfectiousFromVariantAdultLocations instance = new SevereInfectiousFromVariantAdultLocations();

		FakeLocalPopulation location1pop = new FakeLocalPopulation();
		location1pop.testSet(PERSON_1, SEVERE_INFECTIOUS_ADULT_VARIANT);
		location1pop.testSet(PERSON_2, EXPOSED_YOUNG);
		location1pop.testSet(PERSON_3, EXPOSED_ADULT);
		instance.wholeLocationUpdate(LOCATION_1, location1pop);

		FakeLocalPopulation LOCATION2pop = new FakeLocalPopulation();
		LOCATION2pop.testSet(PERSON_4, EXPOSED_YOUNG);
		instance.wholeLocationUpdate(LOCATION_2, LOCATION2pop);

		instance.notifyNonEdgeCaseMovement(PERSON_2, false, LOCATION_1, EXPOSED_YOUNG);
		instance.notifyNonEdgeCaseMovement(PERSON_2, true, LOCATION_2, EXPOSED_YOUNG);

		assertEquals(setOf(LOCATION_1), instance.getSevereInfectiousFromVariantAdultLocations());
	}

	private Set<Integer> setOf(Integer... ints){
		Set<Integer> s = new HashSet<>(Arrays.asList(ints));
		return s;
	}
}
