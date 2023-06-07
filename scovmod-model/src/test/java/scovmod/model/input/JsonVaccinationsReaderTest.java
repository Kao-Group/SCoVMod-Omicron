package scovmod.model.input;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import scovmod.model.movements.LocationIncomingPersons;
import scovmod.model.movements.TimeStepMovements;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.Assert.*;
import static scovmod.model.util.TestUtils.intSetOf;
import static scovmod.model.util.TestUtils.setOf;

public class JsonVaccinationsReaderTest {

    static final Path basePath = Paths.get("src", "test", "resources", "inputData", "peopleToVaccinate");

    JsonVaccinationsReader instance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       // instance = new JsonVaccinationsReader(basePath);
    }

    @Test
    public void loadTimeSteps() {
        Path testData = basePath.resolve("peopleToVaccinate.json");
        JsonVaccinationsReader instance = new JsonVaccinationsReader(testData);

        Int2ObjectMap<Set> expected1 = new Int2ObjectOpenHashMap();
        expected1.put(1, intSetOf(10001, 20001, 30001));
        expected1.put(2, intSetOf(10002, 20002, 30002));
        expected1.put(3, intSetOf(10003, 20003, 30003,10001));

       assertEquals(expected1,instance.getPeopleToVaccinateMap());

    }
    @Test
    public void stepWithNoVaccinations() {
        Path testData = basePath.resolve("peopleToVaccinate_noneInTimeStep.json");
        JsonVaccinationsReader instance = new JsonVaccinationsReader(testData);

        Int2ObjectMap<Set> expected1 = new Int2ObjectOpenHashMap();
        expected1.put(1, intSetOf(10001, 20001, 30001));
        expected1.put(2, intSetOf(10002, 20002, 30002));
        expected1.put(3, intSetOf());

        assertEquals(expected1,instance.getPeopleToVaccinateMap());
    }

    @Test
    public void wrongJsonStructureFile() {
        try {
            Path testData = basePath.resolve("peopleToVaccinate_wrongStructure.json");
            JsonVaccinationsReader instance = new JsonVaccinationsReader(testData);
            fail("Expected exception");
        } catch (RuntimeException e) {
        }
    }
}
