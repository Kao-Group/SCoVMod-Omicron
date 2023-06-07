package scovmod.model.lockdown;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpecifiedLockdownDetailsTest {

	private final int LOCATION_1 = 100;
	private final int LOCATION_2 = 200;

	private final int TIMESTEP_1 = 1000;
	private final int TIMESTEP_2 = 2000;

	@Test
	public void valueObject() {
		SpecifiedLockdownDetails instance1a = new SpecifiedLockdownDetails(LOCATION_1, TIMESTEP_1);
		SpecifiedLockdownDetails instance1b = new SpecifiedLockdownDetails(LOCATION_1, TIMESTEP_1);
		SpecifiedLockdownDetails instance2 = new SpecifiedLockdownDetails(LOCATION_2, TIMESTEP_2);
		SpecifiedLockdownDetails instance3 = new SpecifiedLockdownDetails(LOCATION_2, TIMESTEP_1);

		assertTrue(instance1a.equals(instance1b));
		assertFalse(instance1a.equals(instance2));
		assertFalse(instance1a.equals(instance3));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());
		assertFalse(instance1a.hashCode() == instance3.hashCode());
	}


}
