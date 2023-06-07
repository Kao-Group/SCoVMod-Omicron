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

public class TransmissionModIndexPerCouncilAreaTest {

	private Path rootPath = Paths.get("src", "test", "resources", "inputData", "accessIndexPerCouncilArea");

	@Test
	public void loadData() throws URISyntaxException {
		Path testData = rootPath.resolve("accessIndex.csv");
		TransmissionModIndexPerCouncilArea instance = new TransmissionModIndexPerCouncilArea(testData);

		Map<Integer, Double> expectedIndex = new HashMap<>();
		expectedIndex.put(15,28.28449377);
		expectedIndex.put(16,15.72185475);
		expectedIndex.put(17,16.80101126);
		expectedIndex.put(19,17.70649213);
		expectedIndex.put(20,10.84380362);
		expectedIndex.put(22,14.53387911);
		expectedIndex.put(24,16.29550089);
		expectedIndex.put(25,10.59653685);
		expectedIndex.put(26,10.86340133);
		expectedIndex.put(28,13.72908302);
		expectedIndex.put(29,18.76111476);
		expectedIndex.put(30,18.28639984);
		expectedIndex.put(31,31.76163256);
		expectedIndex.put(32,26.56907092);

		assertEquals(expectedIndex, instance.getAccessIndexPerCA());
	}

	@Test(expected = BadInputDataException.class)
	public void badHeader() {
		new TransmissionModIndexPerCouncilArea(rootPath.resolve("accessIndex_badHeader.csv"));
	}

	@Test(expected = BadInputDataException.class)
	public void exceptionIfRepeatedLocationId() {
		Path repeatedRowFilePath = rootPath.resolve("accessIndex_repeatedRow.csv");
		new TransmissionModIndexPerCouncilArea(repeatedRowFilePath);
	}

}
