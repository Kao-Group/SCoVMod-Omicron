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
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import scovmod.model.BadInputDataException;
//import scovmod.model.ModelException;
//import scovmod.model.seeding.recovered.AreaToHaveRecovered;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Arrays;
//import java.util.Set;
//
//public class InitialiseToRecovered {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private Int2ObjectMap<Set> areaToHaveRecoveredMap = new Int2ObjectOpenHashMap();
//
//    private final String[] expectedHeader = new String[]{"Timestep","Area","NumberRecovered"};
//
//    public InitialiseToRecovered(Path path) {
//        final long startTimeLoadRecovered = System.currentTimeMillis();
//        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
//            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
//            if (!Arrays.equals(header, expectedHeader)) {
//                throw new BadInputDataException("Bad file header");
//            }
//
//            for (String line = br.readLine(); line != null; line = br.readLine()) {
//                String[] toks = line.replaceAll("\\s+", "").split(",");
//                int area = Integer.parseInt(toks[1]);
//                int numberRecovered = Integer.parseInt(toks[2]);
//                int timestep = Integer.parseInt(toks[0]);
//                AreaToHaveRecovered areaToHaveRecovered = new AreaToHaveRecovered(area,numberRecovered);
//                if (areaToHaveRecoveredMap.containsKey(timestep)) {
//                    Set set = areaToHaveRecoveredMap.get(timestep);
//                    set.add(areaToHaveRecovered);
//                } else {
//                    Set areasToRecover = new ObjectOpenHashSet();
//                    areasToRecover.add(areaToHaveRecovered);
//                    areaToHaveRecoveredMap.put(timestep,areasToRecover);
//                }
//            }
//        } catch (IOException e) {
//            throw new ModelException("Exception reading areas to set recovered data", e);
//        }
//        final long endTimeLoadRecovered = System.currentTimeMillis();
//        if (log.isDebugEnabled()) {
//            log.debug("Total execution time for loading start locations: " + (double) (endTimeLoadRecovered - startTimeLoadRecovered) / 1000);
//        }
//    }
//
//    public Int2ObjectMap<Set> getAreaToSetRecoveredMap() {
//        return areaToHaveRecoveredMap;
//    }
//}
