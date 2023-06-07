/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.lockdown;

import java.util.Objects;

public class SpecifiedLockdownDetails {
	private final int location;
	private final int timeStep;
	public SpecifiedLockdownDetails(int location, int timeStep){
		this.location = location;
		this.timeStep = timeStep;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SpecifiedLockdownDetails that = (SpecifiedLockdownDetails) o;
		return location == that.location &&
				timeStep == that.timeStep;
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, timeStep);
	}

	public int getLocation() {
		return location;
	}

	public int getTimeStep() {
		return timeStep;
	}
}
