/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import org.junit.Test;
import scovmod.model.BadInputDataException;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RecoveredSeedingGroupAttributesTest {

	private Path rootPath = Paths.get("src", "test", "resources", "inputData", "recoveredSeedsWeights");

	@Test
	public void loadData() throws URISyntaxException {
		Path testData = rootPath.resolve("recoveredSpatialSeedingGroupAttributes.csv");
		RecoveredSeedingGroupAttributes instance = new RecoveredSeedingGroupAttributes(testData);

		Map<Integer, Double> expectedIndex = new HashMap<>();
		expectedIndex.put(1,0.001);
		expectedIndex.put(2,0.002);

		assertEquals(expectedIndex, instance.getRecoveredGroupWeightsMap());
	}

	@Test(expected = BadInputDataException.class)
	public void badHeader() {
		new RecoveredSeedingGroupAttributes(rootPath.resolve("recoveredSpatialSeedingGroupAttributes_badHeader.csv"));
	}

	@Test(expected = BadInputDataException.class)
	public void exceptionIfRepeatedLocationId() {
		Path repeatedRowFilePath = rootPath.resolve("recoveredSpatialSeedingGroupAttributes_repeatedRow.csv");
		new RecoveredSeedingGroupAttributes(repeatedRowFilePath);
	}

}
