/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.junit.Test;
import scovmod.model.BadInputDataException;
import scovmod.model.lockdown.SpecifiedLockdownDetails;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class SpecifiedLockdownDetailsReaderTest {

	private final int TIMESTEP_1 = 1000;
	private final int TIMESTEP_2 = 2000;
	private final double SPECIFIED_BETA_MULT = 0.5;

	private Path rootPath = Paths.get("src", "test", "resources", "inputData", "councilAreaWithSpecifiedLockdown");

	@Test
	public void loadData() throws URISyntaxException {
		Path testData = rootPath.resolve("specifiedLockdown.csv");
		SpecifiedLockdownDetailsReader instance = new SpecifiedLockdownDetailsReader(testData,null);

		Object2DoubleMap expected = new Object2DoubleOpenHashMap();
		SpecifiedLockdownDetails sd1 = new SpecifiedLockdownDetails(15,TIMESTEP_1);
		SpecifiedLockdownDetails sd2 = new SpecifiedLockdownDetails(16,TIMESTEP_1);
		SpecifiedLockdownDetails sd3 = new SpecifiedLockdownDetails(17,TIMESTEP_1);
		SpecifiedLockdownDetails sd4 = new SpecifiedLockdownDetails(19,TIMESTEP_1);
		SpecifiedLockdownDetails sd5 = new SpecifiedLockdownDetails(20,TIMESTEP_2);
		expected.put(sd1,SPECIFIED_BETA_MULT);
		expected.put(sd2,SPECIFIED_BETA_MULT);
		expected.put(sd3,SPECIFIED_BETA_MULT);
		expected.put(sd4,SPECIFIED_BETA_MULT);
		expected.put(sd5,SPECIFIED_BETA_MULT);

		assertEquals(expected, instance.getAreasWithSpecifiedLockdownMap());
	}

	@Test(expected = BadInputDataException.class)
	public void badHeader() {
		new SpecifiedLockdownDetailsReader(rootPath.resolve("specifiedLockdown_badHeader.csv"),null);
	}

	@Test(expected = BadInputDataException.class)
	public void exceptionIfRepeatedLocationId() {
		Path repeatedRowFilePath = rootPath.resolve("specifiedLockdown_repeatedRow.csv");
		new SpecifiedLockdownDetailsReader(repeatedRowFilePath,null);
	}

}
