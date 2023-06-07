/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input.wastewater;

import java.util.Objects;

class DZtoWWDetails  {

	public final String sampleSewerArea;
	public final int dz;
	public final int pop2011;
	public final int dz_pop;
	public final double prop_dz;

	public DZtoWWDetails(String sampleSewerArea, int dz, int pop2011, int dz_pop, double prop_dz) {
		this.sampleSewerArea = sampleSewerArea;
		this.dz = dz;
		this.pop2011 = pop2011;
		this.dz_pop = dz_pop;
		this.prop_dz = prop_dz;
	}

	@Override
	public String toString() {
		return "DZtoWWDetails{" +
				"sampleSewerArea='" + sampleSewerArea + '\'' +
				", dz=" + dz +
				", pop2011=" + pop2011 +
				", dz_pop=" + dz_pop +
				", prop_dz=" + prop_dz +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DZtoWWDetails that = (DZtoWWDetails) o;
		return dz == that.dz && pop2011 == that.pop2011 && dz_pop == that.dz_pop && Double.compare(that.prop_dz, prop_dz) == 0 && Objects.equals(sampleSewerArea, that.sampleSewerArea);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sampleSewerArea, dz, pop2011, dz_pop, prop_dz);
	}

	public String getSampleSewerArea() {
		return sampleSewerArea;
	}

	public int getDz() {
		return dz;
	}

	public int getPop2011() {
		return pop2011;
	}

	public int getDz_pop() {
		return dz_pop;
	}

	public double getProp_dz() {
		return prop_dz;
	}
}
