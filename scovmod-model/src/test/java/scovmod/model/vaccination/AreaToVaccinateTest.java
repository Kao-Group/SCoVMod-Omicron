/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.vaccination;

import org.junit.Test;
import scovmod.model.input.seeding.AgeClass;
import scovmod.model.seeding.reseeding.AreaToReseed;

import static org.junit.Assert.*;

public class AreaToVaccinateTest {

	private final int AREA_1 = 100;
	private final int AREA_2 = 100;
    private final int NUMPERSON_1 = 100;
	private final int NUMPERSON_2 = 200;
	private final AgeClass YOUNG = AgeClass.YOUNG;
	private final AgeClass ELDERLY = AgeClass.ELDERLY;

	@Test
	public void getAndSet(){
		AreaToVaccinate instance = new AreaToVaccinate(AREA_1,NUMPERSON_1,YOUNG);
		assertEquals(AREA_1, instance.getLocation());
		assertEquals(NUMPERSON_1, instance.getNumberVaccinations());
		assertEquals(YOUNG, instance.getAgeClass());
	}

	@Test
	public void valueObject(){
		AreaToVaccinate instance1a = new AreaToVaccinate(AREA_1,NUMPERSON_1,YOUNG);
		AreaToVaccinate instance1b = new AreaToVaccinate(AREA_1,NUMPERSON_1,YOUNG);
		AreaToVaccinate instance2 = new AreaToVaccinate(AREA_2,NUMPERSON_2,ELDERLY);
		AreaToVaccinate instance3 = new AreaToVaccinate(AREA_2,NUMPERSON_2,YOUNG);

		assertTrue(instance1a.equals(instance1b));
		assertFalse(instance1a.equals(instance2));
		assertFalse(instance2.equals(instance3));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());

	}
}
