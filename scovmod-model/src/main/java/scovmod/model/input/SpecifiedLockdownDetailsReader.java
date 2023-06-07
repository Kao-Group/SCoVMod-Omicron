/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.BadInputDataException;
import scovmod.model.ModelException;
import scovmod.model.lockdown.SpecifiedLockdownDetails;
import scovmod.model.time.TimeManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

public class SpecifiedLockdownDetailsReader {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Object2DoubleMap<SpecifiedLockdownDetails> specifiedLockdownTimeMap = new Object2DoubleOpenHashMap();
    private final TimeManager timeMgr;

    private final String[] expectedHeader = new String[]{"LA","startTimeStep","betaMultiplier"};

    public SpecifiedLockdownDetailsReader(Path path, TimeManager timeMgr) {
        this.timeMgr = timeMgr;
        final long startTimeLoadAccessIndex = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(",");
                int ca = Integer.parseInt(toks[0]);
                int startTimeStep = Integer.parseInt(toks[1]);
                double betaMultiplier = Double.parseDouble(toks[2]);
                SpecifiedLockdownDetails lockdownDetails = new SpecifiedLockdownDetails(ca,startTimeStep);
                if (specifiedLockdownTimeMap.containsKey(lockdownDetails)) {
                    throw new BadInputDataException("This details per timestep appears more than once in specified lockdown file");
                } else {
                    specifiedLockdownTimeMap.put(lockdownDetails,betaMultiplier);
                }
            }
        } catch (IOException e) {
            throw new ModelException("Exception reading specified lockdown data", e);
        }
        final long endTimeLoadAccessIndex = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading specified lockdown details per council area: " + (double) (endTimeLoadAccessIndex - startTimeLoadAccessIndex) / 1000);
        }
    }
    public Object2DoubleMap<SpecifiedLockdownDetails> getAreasWithSpecifiedLockdownMap() {
        return specifiedLockdownTimeMap;
    }

    public double getSpecifiedBetaMultiplier(int ca) {
        long timeStep = timeMgr.getTimeStep();
        if (!specifiedLockdownTimeMap.containsKey((new SpecifiedLockdownDetails(ca,(int)timeStep)))) {
            return 1.0;
        } else {
            return specifiedLockdownTimeMap.get(new SpecifiedLockdownDetails(ca,(int)timeStep));
        }
    }
}
