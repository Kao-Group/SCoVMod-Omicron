/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input.wastewater;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.junit.Test;
import scovmod.model.BadInputDataException;
import scovmod.model.input.SpecifiedLockdownDetailsReader;
import scovmod.model.lockdown.SpecifiedLockdownDetails;
import scovmod.model.util.TestUtils;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DZToWWSiteMappingReaderTest {

	private Path rootPath = Paths.get("src", "test", "resources", "inputData", "dzToWWSiteMapping");

	@Test
	public void loadData() throws URISyntaxException {
		Path testData = rootPath.resolve("dz_ssa_summary_2.csv");
		DZToWWSiteMappingReader instance = new DZToWWSiteMappingReader(testData);

		Int2ObjectMap<Object2DoubleMap> expected = new Int2ObjectOpenHashMap();
		Object2DoubleMap Map1 = new Object2DoubleOpenHashMap();
		Map1.put("Allers",0.6138059701492538);
		Map1.put("Philipshill",0.0727611940298507);
		expected.put(12895, Map1);
		Object2DoubleMap Map2 = new Object2DoubleOpenHashMap();
		Map2.put("Allers",0.0344062153163152);
		expected.put(12752, Map2);
		assertEquals(expected, instance.getSitesToDZdMap());
	}

	@Test(expected = BadInputDataException.class)
	public void badHeader() {
		new SpecifiedLockdownDetailsReader(rootPath.resolve("dz_ssa_summary_badHeader.csv"),null);
	}

}
