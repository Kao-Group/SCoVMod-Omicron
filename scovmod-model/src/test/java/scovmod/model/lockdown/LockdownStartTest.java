/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.lockdown;

import org.junit.Test;

import static org.junit.Assert.*;

public class LockdownStartTest {
    
    	private final int TIMESTEP_1 = 100;
    	private final int TIMESTEP_2 = 200;
    	private final int TIMESTEP_3 = 300;
    
    @Test
	public void valueObject() {
		LockdownStart instance1a = new LockdownStart(TIMESTEP_1);
		LockdownStart instance1b = new LockdownStart(TIMESTEP_1);
		LockdownStart instance2 = new LockdownStart(TIMESTEP_2);
		LockdownStart instance3 = new LockdownStart(TIMESTEP_3);

		assertTrue(instance1a.equals(instance1b));
		assertFalse(instance1a.equals(instance2));
		assertFalse(instance1a.equals(instance3));

		assertEquals(instance1a.hashCode(), instance1b.hashCode());
		assertFalse(instance1a.hashCode() == instance2.hashCode());
		assertFalse(instance1a.hashCode() == instance3.hashCode());
	}
    
}
