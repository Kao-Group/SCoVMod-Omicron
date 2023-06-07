/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input.wastewater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.BadInputDataException;
import scovmod.model.ModelException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;

public class DZToWWSiteMappingReader {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Int2ObjectMap<Object2DoubleMap> sitesToDZdMap = new Int2ObjectOpenHashMap();

    private final String[] expectedHeader = new String[]{"SampledSewerArea","DataZone2011","Pop2011","dz_pop","prop_dz","WWRNA_simplename"};

    public DZToWWSiteMappingReader(Path path) {
        final long startTimeLoadDZMappingFile = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(",");
                String sampleSewerArea = toks[5];
                int dz = Integer.parseInt(toks[1].substring(3,9));
                //System.out.println("DZ: "+dz);
//                int pop2011 = Integer.parseInt(toks[2]);
//                int dz_pop = Integer.parseInt(toks[3]);
                double prop_dz = Double.parseDouble(toks[4]);
                if (sitesToDZdMap.containsKey(dz)) {
                    Object2DoubleMap WWMap = sitesToDZdMap.get(dz);
                    WWMap.put(sampleSewerArea,prop_dz);
                } else {
                    Object2DoubleMap WWMap = new Object2DoubleOpenHashMap();
                    WWMap.put(sampleSewerArea,prop_dz);
                    sitesToDZdMap.put(dz,WWMap);
                }
            }
        } catch (IOException e) {
            throw new ModelException("Exception reading specified lockdown data", e);
        }
        final long endTimeLoadDZMappingFile = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading dz ww area mapping " + (double) (endTimeLoadDZMappingFile - startTimeLoadDZMappingFile) / 1000);
        }
    }

    public Int2ObjectMap<Object2DoubleMap> getSitesToDZdMap() {
        return sitesToDZdMap;
    }
//    public Object2DoubleMap<SpecifiedLockdownDetails> getAreasWithSpecifiedLockdownMap() {
//        return specifiedLockdownTimeMap;
//    }
//
//    public double getSpecifiedBetaMultiplier(int ca) {
//        long timeStep = timeMgr.getTimeStep();
//        if (!specifiedLockdownTimeMap.containsKey((new SpecifiedLockdownDetails(ca,(int)timeStep)))) {
//            return 1.0;
//        } else {
//            return specifiedLockdownTimeMap.get(new SpecifiedLockdownDetails(ca,(int)timeStep));
//        }
//    }
}
