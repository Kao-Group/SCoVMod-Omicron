/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.lockdown;

import java.util.Objects;

class LockdownStart implements LockdownEvent {
	
	public final long timeStep;

	public LockdownStart(long timeStep) {
		this.timeStep = timeStep;
	}

	@Override
	public String toString() {
		return "LockdownStart{" +
				"timeStep=" + timeStep +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LockdownStart that = (LockdownStart) o;
		return timeStep == that.timeStep;
	}

	@Override
	public int hashCode() {
		return Objects.hash(timeStep);
	}
}
