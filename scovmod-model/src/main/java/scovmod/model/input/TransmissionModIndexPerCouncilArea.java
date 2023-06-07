/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import it.unimi.dsi.fastutil.ints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.BadInputDataException;
import scovmod.model.ModelException;
import scovmod.model.input.seeding.AgeClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class TransmissionModIndexPerCouncilArea {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Int2DoubleMap indexValuePerCA = new Int2DoubleOpenHashMap();

    private final String[] expectedHeader = new String[]{"LA","transMod"};

    // Note Transmission Index is the square root of access index * health index
    public TransmissionModIndexPerCouncilArea(Path path) {
        final long startTimeLoadTransIndex = System.currentTimeMillis();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String[] header = br.readLine().replaceAll("\\s+", "").split(",");
            if (!Arrays.equals(header, expectedHeader)) {
                throw new BadInputDataException("Bad file header");
            }

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] toks = line.replaceAll("\\s+", "").split(",");
                int ca = Integer.parseInt(toks[0]);
                String indexValueString = toks[1];
                double indexValue = Double.parseDouble(indexValueString);

                if (indexValuePerCA.containsKey(ca)) {
                    throw new BadInputDataException("This council area appears more than once in access index");
                } else {
                    indexValuePerCA.put(ca, indexValue);
                }
            }
        } catch (IOException e) {
            throw new ModelException("Exception reading trans mod index data", e);
        }
        final long endTimeLoadTransIndex = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading trans mod index per council area: " + (double) (endTimeLoadTransIndex - startTimeLoadTransIndex) / 1000);
        }
    }
    public Int2DoubleMap getAccessIndexPerCA() {
        return indexValuePerCA;
    }
}
