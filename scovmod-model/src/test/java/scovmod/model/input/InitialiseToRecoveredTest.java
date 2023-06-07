///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package scovmod.model.input;
//
//import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
//import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
//import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
//import org.junit.Test;
//import scovmod.model.BadInputDataException;
//import scovmod.model.seeding.recovered.AreaToHaveRecovered;
//import scovmod.model.seeding.reseeding.AreaToReseed;
//
//import java.net.URISyntaxException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Set;
//
//import static org.junit.Assert.assertEquals;
//
//public class InitialiseToRecoveredTest {
//
//	private Path rootPath = Paths.get("src", "test", "resources", "inputData", "recoveredSeeds");
//
//	private final int TIMESTEP_1 = 1;
//	private final int TIMESTEP_2 = 2;
//	private final int TIMESTEP_3 = 3;
//	private final int TIMESTEP_4 = 4;
//
//	@Test
//	public void loadData() throws URISyntaxException {
//		Path testData = rootPath.resolve("initialiseToRecovered.csv");
//		InitialiseToRecovered instance = new InitialiseToRecovered(testData);
//
//		Int2ObjectMap<Set> expectedAreaToRecoverMap = new Int2ObjectOpenHashMap();
//		AreaToHaveRecovered areaToRecover1 = new AreaToHaveRecovered(10, 20);
//		Set<AreaToHaveRecovered> set1 = new ObjectOpenHashSet();
//		set1.add(areaToRecover1);
//		AreaToHaveRecovered areaToRecover2 = new AreaToHaveRecovered(20, 30);
//		Set<AreaToHaveRecovered> set2 = new ObjectOpenHashSet();
//		set2.add(areaToRecover2);
//		AreaToHaveRecovered areaToRecover3 = new AreaToHaveRecovered(30, 40);
//		Set<AreaToHaveRecovered> set3 = new ObjectOpenHashSet();
//		set3.add(areaToRecover3);
//		AreaToHaveRecovered areaToRecover4 = new AreaToHaveRecovered(40, 50);
//		Set<AreaToHaveRecovered> set4 = new ObjectOpenHashSet();
//		set4.add(areaToRecover4);
//		expectedAreaToRecoverMap.put(TIMESTEP_1, set1);
//		expectedAreaToRecoverMap.put(TIMESTEP_2, set2);
//		expectedAreaToRecoverMap.put(TIMESTEP_3, set3);
//		expectedAreaToRecoverMap.put(TIMESTEP_4, set4);
//
//		assertEquals(expectedAreaToRecoverMap, instance.getAreaToSetRecoveredMap());
//	}
//
//	@Test(expected = BadInputDataException.class)
//	public void badHeader() {
//		new InitialiseToRecovered(rootPath.resolve("initialiseToRecovered_badHeader.csv"));
//	}
//
//
//}
