/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.BadInputDataException;
import scovmod.model.ModelException;
import scovmod.model.seeding.reseeding.AreaToReseed;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

public class RecoveredSeeds { //TODO Rename - now used for seeding recovered.

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Int2ObjectMap<Set> areaToReseedMap = new Int2ObjectOpenHashMap();

    private final String[] expectedHeader = new String[]{"Timestep","Area","NumberSeeds"};

    public RecoveredSeeds(Path path) {
        final long startTimeLoadInfectedSeeds = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(",");
                int area = Integer.parseInt(toks[1]);
                int numberSeeds = Integer.parseInt(toks[2]);
                int timestep = Integer.parseInt(toks[0]);
                AreaToReseed areaToSeed = new AreaToReseed(area,numberSeeds);
                if (areaToReseedMap.containsKey(timestep)) {
                    Set set = areaToReseedMap.get(timestep);
                    set.add(areaToSeed);
                } else {
                    Set areasToReseed = new ObjectOpenHashSet();
                    areasToReseed.add(areaToSeed);
                    areaToReseedMap.put(timestep,areasToReseed);
                }
            }
        } catch (IOException e) {
            throw new ModelException("Exception reading areas to reseed data", e);
        }
        final long endTimeLoadInfectedSeeds = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading start locations: " + (double) (endTimeLoadInfectedSeeds - startTimeLoadInfectedSeeds) / 1000);
        }
    }

    public Int2ObjectMap<Set> getAreaToReseedMap() {
        return areaToReseedMap;
    }
}
