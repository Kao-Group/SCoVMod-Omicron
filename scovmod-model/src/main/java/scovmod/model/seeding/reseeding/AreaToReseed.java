package scovmod.model.seeding.reseeding;

import java.util.Objects;

public class AreaToReseed {
    private int location;
    private int numberSeeds;

    public AreaToReseed(int location, int numberSeeds) {
        this.location = location;
        this.numberSeeds = numberSeeds;
    }

    @Override
    public String toString() {
        return "AreaToReseed{" +
                "location=" + location +
                ", numberSeeds=" + numberSeeds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaToReseed that = (AreaToReseed) o;
        return location == that.location &&
                numberSeeds == that.numberSeeds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, numberSeeds);
    }

    public int getLocation() {
        return location;
    }

    public int getNumberSeeds() {
        return numberSeeds;
    }
}
