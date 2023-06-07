/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.seeding.reseeding;

import org.junit.Test;

import static org.junit.Assert.*;

public class AreaToReseedTest {

	private final int AREA_1 = 100;
	private final int AREA_2 = 100;
    private final int NUMPERSON_1 = 100;
	private final int NUMPERSON_2 = 200;

	@Test
	public void getAndSet(){
		AreaToReseed instance = new AreaToReseed(AREA_1,NUMPERSON_1);
		assertEquals(AREA_1, instance.getLocation());
		assertEquals(NUMPERSON_1, instance.getNumberSeeds());
	}

	@Test
	public void valueObject(){
		AreaToReseed instance1a = new AreaToReseed(AREA_1,NUMPERSON_1);
		AreaToReseed instance1b = new AreaToReseed(AREA_1,NUMPERSON_1);
		AreaToReseed instance2 = new AreaToReseed(AREA_2,NUMPERSON_2);

		assertTrue(instance1a.equals(instance1b));
		assertFalse(instance1a.equals(instance2));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());

	}
}
