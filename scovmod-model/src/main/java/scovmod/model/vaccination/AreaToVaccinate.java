package scovmod.model.vaccination;

import scovmod.model.input.seeding.AgeClass;

import java.util.Objects;

public class AreaToVaccinate {
    private int location;
    private int numberVaccinations;
    private AgeClass ageClass;

    public AreaToVaccinate(int location, int numberVaccinations, AgeClass ageClass) {
        this.location = location;
        this.numberVaccinations = numberVaccinations;
        this.ageClass = ageClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaToVaccinate that = (AreaToVaccinate) o;
        return location == that.location && numberVaccinations == that.numberVaccinations && ageClass == that.ageClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, numberVaccinations, ageClass);
    }

    @Override
    public String toString() {
        return "AreaToVaccinate{" +
                "location=" + location +
                ", numberVaccinations=" + numberVaccinations +
                ", ageClass=" + ageClass +
                '}';
    }

    public int getNumberVaccinations() {
        return numberVaccinations;
    }

    public AgeClass getAgeClass() {
        return ageClass;
    }

    public int getLocation() {
        return location;
    }

}
