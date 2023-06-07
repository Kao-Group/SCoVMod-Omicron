/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.junit.Test;
import scovmod.model.BadInputDataException;
import scovmod.model.seeding.reseeding.AreaToReseed;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class VariantSeedsPerCATest {

	private Path rootPath = Paths.get("src", "test", "resources", "inputData", "variantSeeds");

	private final int TIMESTEP_1 = 1;
	private final int TIMESTEP_2 = 2;
	private final int TIMESTEP_3 = 3;
	private final int TIMESTEP_4 = 4;

	@Test
	public void loadData() throws URISyntaxException {
		Path testData = rootPath.resolve("variantSeedsPerCA.csv");
		VariantSeedsPerCA instance = new VariantSeedsPerCA(testData);

		Int2ObjectMap<Set> expectedAreaToReseedMap = new Int2ObjectOpenHashMap();
		AreaToReseed areaToReseed1 = new AreaToReseed(10, 20);
		Set<AreaToReseed> set1 = new ObjectOpenHashSet();
		set1.add(areaToReseed1);
		AreaToReseed areaToReseed2 = new AreaToReseed(20, 30);
		Set<AreaToReseed> set2 = new ObjectOpenHashSet();
		set2.add(areaToReseed2);
		AreaToReseed areaToReseed3 = new AreaToReseed(30, 40);
		Set<AreaToReseed> set3 = new ObjectOpenHashSet();
		set3.add(areaToReseed3);
		AreaToReseed areaToReseed4 = new AreaToReseed(40, 50);
		Set<AreaToReseed> set4 = new ObjectOpenHashSet();
		set4.add(areaToReseed4);
		expectedAreaToReseedMap.put(TIMESTEP_1, set1);
		expectedAreaToReseedMap.put(TIMESTEP_2, set2);
		expectedAreaToReseedMap.put(TIMESTEP_3, set3);
		expectedAreaToReseedMap.put(TIMESTEP_4, set4);

		assertEquals(expectedAreaToReseedMap, instance.getVariantSeedsPerCAMap());
	}

	@Test(expected = BadInputDataException.class)
	public void badHeader() {
		new VariantSeedsPerCA(rootPath.resolve("variantSeedsPerCA_badHeader.csv"));
	}

}
