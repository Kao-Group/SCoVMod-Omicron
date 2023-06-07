/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.lockdown;

class LockdownEnd implements LockdownEvent {
	
	public final long timeStep;

	public LockdownEnd(long timeStep) {
		this.timeStep = timeStep;
	}

	@Override
	public String toString() {
		return "BreakdownEnd{" + "date=" + timeStep + '}';
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 59 * hash + (int) (this.timeStep ^ (this.timeStep >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LockdownEnd other = (LockdownEnd) obj;
		if (this.timeStep != other.timeStep) {
			return false;
		}
		return true;
	}	
}
