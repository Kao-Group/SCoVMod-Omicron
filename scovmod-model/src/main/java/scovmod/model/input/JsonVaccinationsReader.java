/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scovmod.model.BadInputDataException;
import scovmod.model.ModelException;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.TimeStepMovements;
import scovmod.model.vaccination.AreaToVaccinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JsonVaccinationsReader {

    private Int2ObjectMap<Set> peopleToVaccinateMap = new Int2ObjectOpenHashMap();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JsonVaccinationsReader(Path path) {
        final long startTimePeopleToVaccinate= System.currentTimeMillis();
        String json = "";
        try {
            json = FileUtils.readFileToString(path.toFile(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
            JsonObject stepData = parser.parse(json).getAsJsonObject();

            try {
                JsonArray steps = stepData.getAsJsonArray("steps");
                for (int j = 0; j < steps.size(); j++) {
                    JsonObject timeStep = steps.get(j).getAsJsonObject();
                    IntSet persons = new IntOpenHashSet(); //persons per step
                    JsonArray personIDs = timeStep.getAsJsonArray("personIDs");
                    for (int k = 0; k < personIDs.size(); k++) {
                        persons.add(personIDs.get(k).getAsInt());
                    }
                    String step = timeStep.get("step").getAsString();
                    int stepInt = Integer.parseInt(step);
                    peopleToVaccinateMap.put(stepInt,persons);
                }
            } catch (Exception e) {
                throw new RuntimeException("Bad json structure.  Nested exception: " + e.getMessage(), e);
            }

        final long endTimeLoadPeopleToVaccinate = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Total execution time for loading people to vaccinate " + (double) (endTimeLoadPeopleToVaccinate - startTimePeopleToVaccinate) / 1000);
        }
    }

    public Int2ObjectMap<Set> getPeopleToVaccinateMap() {
        return peopleToVaccinateMap;
    }
}
