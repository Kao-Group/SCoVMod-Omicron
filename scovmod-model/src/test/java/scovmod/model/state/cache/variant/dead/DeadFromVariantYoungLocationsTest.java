package scovmod.model.state.cache.variant.dead;


import org.junit.Test;
import scovmod.model.state.cache.dead.DeadYoungLocations;
import scovmod.test.FakeLocalPopulation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static scovmod.model.state.infection.InfectionState.*;

public class DeadFromVariantYoungLocationsTest {

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
		DeadFromVariantYoungLocations instance = new DeadFromVariantYoungLocations();

		FakeLocalPopulation location1pop = new FakeLocalPopulation();
		location1pop.testSet(PERSON_1, SUSCEPTIBLE);
		location1pop.testSet(PERSON_2, EXPOSED_YOUNG);
		location1pop.testSet(PERSON_3, EXPOSED_ADULT);
		instance.wholeLocationUpdate(LOCATION_1, location1pop);

		assertEquals(NONE, instance.getDeadFromVariantYoungLocations());

		FakeLocalPopulation location2pop = new FakeLocalPopulation();
		location2pop.testSet(PERSON_5, DEAD_YOUNG_VARIANT);
		instance.wholeLocationUpdate(LOCATION_2, location2pop);

		assertEquals(setOf(LOCATION_2), instance.getDeadFromVariantYoungLocations());

		FakeLocalPopulation location1updatedPop = new FakeLocalPopulation();
		location1updatedPop.testSet(PERSON_1, SUSCEPTIBLE);
		location1updatedPop.testSet(PERSON_3, DEAD_YOUNG_VARIANT);
		instance.wholeLocationUpdate(LOCATION_1, location1updatedPop);

		assertEquals(setOf(LOCATION_2, LOCATION_1), instance.getDeadFromVariantYoungLocations());
	}

	@Test
	public void edgeCaseUpdatesDoNothing(){
		DeadFromVariantYoungLocations instance = new DeadFromVariantYoungLocations();

		FakeLocalPopulation location1pop = new FakeLocalPopulation();
		location1pop.testSet(PERSON_1, DEAD_YOUNG_VARIANT);
		location1pop.testSet(PERSON_2, EXPOSED_YOUNG);
		location1pop.testSet(PERSON_3, EXPOSED_ADULT);
		instance.wholeLocationUpdate(LOCATION_1, location1pop);

		FakeLocalPopulation LOCATION2pop = new FakeLocalPopulation();
		LOCATION2pop.testSet(PERSON_4, EXPOSED_YOUNG);
		instance.wholeLocationUpdate(LOCATION_2, LOCATION2pop);

		instance.notifyNonEdgeCaseMovement(PERSON_2, false, LOCATION_1, EXPOSED_YOUNG);
		instance.notifyNonEdgeCaseMovement(PERSON_2, true, LOCATION_2, EXPOSED_YOUNG);

		assertEquals(setOf(LOCATION_1), instance.getDeadFromVariantYoungLocations());
	}

	private Set<Integer> setOf(Integer... ints){
		Set<Integer> s = new HashSet<>(Arrays.asList(ints));
		return s;
	}
}
