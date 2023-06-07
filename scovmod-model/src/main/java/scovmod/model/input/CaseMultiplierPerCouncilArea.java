/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
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

public class CaseMultiplierPerCouncilArea {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Int2DoubleMap multiplierValuePerCA = new Int2DoubleOpenHashMap();

    private final String[] expectedHeader = new String[]{"LA","multiplier"};

    // Note Transmission Index is the square root of access index * health index
    public CaseMultiplierPerCouncilArea(Path path) {
        final long startTimeLoadCaseMult = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(",");
                int ca = Integer.parseInt(toks[0]);
                String multValueString = toks[1];
                double multValue = Double.parseDouble(multValueString);

                if (multiplierValuePerCA.containsKey(ca)) {
                    throw new BadInputDataException("This council area appears more than once in case multiplier");
                } else {
                    multiplierValuePerCA.put(ca, multValue);
                }
            }
        } catch (IOException e) {
            throw new ModelException("Exception reading case multiplier data", e);
        }
        final long endTimeLoadCaseMult = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading case multiplier per council area: " + (double) (endTimeLoadCaseMult - startTimeLoadCaseMult) / 1000);
        }
    }
    public Int2DoubleMap getCaseMultiplierPerCA() {
        return multiplierValuePerCA;
    }
}
