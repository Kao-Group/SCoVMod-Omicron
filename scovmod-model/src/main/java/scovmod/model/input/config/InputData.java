/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.input.config;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputData {

    private String rootDirectory;
    private String movementJSONDirectoryName;
    private String seedingDirectoryName;
    private String summaryStatDirectoryName;
    private String areaLookupFileName;
    private String peopleStartLocationsFileName;
    private String areasToReseedFileName;
    private String variantSeedsPerCAFileName;
    private String transModDirectoryName;
    private String transModFileName;
    private String spatialSeedingGroupWeightsFileName;
    private String recoveredSeedingGroupWeightsFileName;
    private String lockdownDirectoryName;
    private String specifiedLockdownsFileName;
    private String positiveCasesDirectoryName;
    private String casesMultiplierPerCAFileName;
    private String vaccinationDirectoryName;
    private String peopleToVaccinatePerTimestepFileName;
    private String wasteWaterDirectoryName;
    private String dzToWWSiteMappingFileName;


    public static InputData fromJSON(String jsonContent) {

        ReadContext ctx = JsonPath.parse(jsonContent);
        String rootDirectory = ctx.read("$.data.dir");
        String movementDirectory = ctx.read("$.data.movement.dir");
        String movementJSONDirectoryName = ctx.read("$.data.movement.dir");
        String seedingDirectoryName = ctx.read("$.data.seeding.dir");
        String areaLookupFileName = ctx.read("$.data.summarystat.area-hb-lookup");
        String summaryStatDirectoryName = ctx.read("$.data.summarystat.dir");
        String peopleStartLocationsFileName = ctx.read("$.data.seeding.people-start-locations");
        String spatialSeedingGroupWeightsFileName = ctx.read("$.data.seeding.spatial-seeding-weights-file");
        String recoveredSeedingGroupWeightsFileName = ctx.read("$.data.seeding.recovered-seeding-weights-file");
        String areasToReseedFileName = ctx.read("$.data.seeding.area-to-reseed");
        String variantSeedsPerCAFileName = ctx.read("$.data.seeding.area-to-seed-with-variant");
      //  String areasWithRecoveredFileName = ctx.read("$.data.seeding.area-with-recovered");
        String accessDirectoryName = ctx.read("$.data.transmissionMod.dir");
        String accessIndexFileName = ctx.read("$.data.transmissionMod.transModIndex");
        String lockdownDirectoryName = ctx.read("$.data.lockdown.dir");
        String specifiedLockdownsFileName = ctx.read("$.data.lockdown.specifiedLockdowns");
        String positiveCasesDirectoryName = ctx.read("$.data.cases.dir");
        String casesMultiplierPerCAFileName = ctx.read("$.data.cases.casesMultiplierPerCA");
        String vaccinationDirectoryName = ctx.read("$.data.vaccination.dir");
        String peopleToVaccinatePerTimestepFileName = ctx.read("$.data.vaccination.peopleToVaccinatePerTimestepFileName");
        String wastewaterDirectoryName = ctx.read("$.data.wastewater.dir");
        String dzToWWSiteMappingFileName = ctx.read("$.data.wastewater.dzToWWSiteMappingFileName");

        return new InputData(
                rootDirectory,
                movementDirectory,
                movementJSONDirectoryName,
                seedingDirectoryName,
                summaryStatDirectoryName,
                areaLookupFileName,
                peopleStartLocationsFileName,
                areasToReseedFileName,
                variantSeedsPerCAFileName,
                accessDirectoryName,
                accessIndexFileName,
                spatialSeedingGroupWeightsFileName,
                recoveredSeedingGroupWeightsFileName,
                lockdownDirectoryName,
                specifiedLockdownsFileName,
                positiveCasesDirectoryName,
                casesMultiplierPerCAFileName,
                vaccinationDirectoryName,
                peopleToVaccinatePerTimestepFileName,
                wastewaterDirectoryName,
                dzToWWSiteMappingFileName);
    }

    public InputData(
            String rootDirectory,
            String movementDirectory,
            String movementJSONDirectoryName,
            String seedingDirectoryName,
            String summaryStatDirectoryName,
            String areaLookupFileName,
            String peopleStartLocationsFileName,
            String areasToReseedFileName,
            String variantSeedsPerCAFileName,
            String transModDirectoryName,
            String transModFileName,
            String spatialSeedingGroupWeightsFileName,
            String recoveredSeedingGroupWeightsFileName,
            String lockdownDirectoryName,
            String specifiedLockdownsFileName,
            String positiveCasesDirectoryName,
            String casesMultiplierPerCAFileName,
            String vaccinationDirectoryName,
            String peopleToVaccinatePerTimestepFileName,
            String wastewaterDirectoryName,
            String dzToWWSiteMappingFileName) {
        this.rootDirectory = rootDirectory;
        this.movementJSONDirectoryName = movementJSONDirectoryName;
        this.seedingDirectoryName = seedingDirectoryName;
        this.summaryStatDirectoryName = summaryStatDirectoryName;
        this.areaLookupFileName = areaLookupFileName;
        this.peopleStartLocationsFileName = peopleStartLocationsFileName;
        this.areasToReseedFileName = areasToReseedFileName;
        this.variantSeedsPerCAFileName = variantSeedsPerCAFileName;
        this.transModDirectoryName = transModDirectoryName;
        this.transModFileName = transModFileName;
        this.spatialSeedingGroupWeightsFileName = spatialSeedingGroupWeightsFileName;
        this.recoveredSeedingGroupWeightsFileName = recoveredSeedingGroupWeightsFileName;
        this.lockdownDirectoryName = lockdownDirectoryName;
        this.specifiedLockdownsFileName = specifiedLockdownsFileName;
        this.positiveCasesDirectoryName = positiveCasesDirectoryName;
        this.casesMultiplierPerCAFileName = casesMultiplierPerCAFileName;
        this.vaccinationDirectoryName = vaccinationDirectoryName;
        this.peopleToVaccinatePerTimestepFileName = peopleToVaccinatePerTimestepFileName;
        this.wasteWaterDirectoryName = wastewaterDirectoryName;
        this.dzToWWSiteMappingFileName = dzToWWSiteMappingFileName;
    }

    public Path getRootDir() {
        return Paths.get(this.rootDirectory);
    }

    public Path getSeedingDir() {
        return this.getRootDir().resolve(Paths.get(this.seedingDirectoryName));
    }

    public Path getMovementDir() {
        return this.getRootDir().resolve(Paths.get(movementJSONDirectoryName));
    }

    public Path getSummaryStatDir() {
        return this.getRootDir().resolve(Paths.get(this.summaryStatDirectoryName));
    }

    public Path getTransModDir() {
        return this.getRootDir().resolve(Paths.get(this.transModDirectoryName));
    }

    public Path getAreaToHBLookupFile() { return this.getSummaryStatDir().resolve(Paths.get(this.areaLookupFileName)); }

    public Path getPeopleStartLocationsFile() { return this.getSeedingDir().resolve(Paths.get(this.peopleStartLocationsFileName)); }

    public Path getAreaToReseedFile() {
        return this.getSeedingDir().resolve(Paths.get(this.areasToReseedFileName));
    }

    public Path getCAsToSeedWithVariantFile() { return this.getSeedingDir().resolve(Paths.get(this.variantSeedsPerCAFileName)); }

    public Path getTransModIndexFile() {
        return this.getTransModDir().resolve(Paths.get(this.transModFileName));
    }

    public Path getSpatialSeedingGroupFileName() { return getSeedingDir().resolve(spatialSeedingGroupWeightsFileName); }

    public Path getRecoveredSeedingGroupFileName() { return getSeedingDir().resolve(recoveredSeedingGroupWeightsFileName); }

    public Path getLockdownDir() {
        return this.getRootDir().resolve(Paths.get(this.lockdownDirectoryName));
    }

    public Path getSpecifiedLockdownsFileName() { return this.getLockdownDir().resolve(Paths.get(this.specifiedLockdownsFileName)); }

    public Path getCasesDir() {
        return this.getRootDir().resolve(Paths.get(this.positiveCasesDirectoryName));
    }

    public Path getCasesMultiplierFileName() { return this.getCasesDir().resolve(Paths.get(this.casesMultiplierPerCAFileName)); }

    public Path getVaccinationDir() {
        return this.getRootDir().resolve(Paths.get(this.vaccinationDirectoryName));
    }

    public Path getPeopleToVaccinatePerTimestepFileName() { return this.getVaccinationDir().resolve(Paths.get(this.peopleToVaccinatePerTimestepFileName)); }

    public Path getWasteWaterDir() {
        return this.getRootDir().resolve(Paths.get(this.wasteWaterDirectoryName));
    }

    public Path getDzToWWSiteMappingFileName() { return this.getWasteWaterDir().resolve(Paths.get(this.dzToWWSiteMappingFileName)); }

}
