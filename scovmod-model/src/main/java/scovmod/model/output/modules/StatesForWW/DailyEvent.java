/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.StatesForWW;

import java.util.Comparator;
import java.util.Objects;

public class DailyEvent implements Comparable<DailyEvent>{

    private final int personID;
    private final StatesForWWValueType type;
    private final int dzID;
    public DailyEvent(int personID, StatesForWWValueType type, int dzID){
        this.personID = personID;
        this.type = type;
        this.dzID = dzID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyEvent that = (DailyEvent) o;
        return personID == that.personID &&
                dzID == that.dzID &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, type, dzID);
    }

    public int getPersonID() { return personID; }

    public StatesForWWValueType getType() {
        return type;
    }

    public int getDzID() {
        return dzID;
    }

    @Override
    public int compareTo(DailyEvent dailyResult) {
        return Comparator.comparing(DailyEvent::getType)
                .thenComparing(DailyEvent::getDzID)
                .thenComparing(DailyEvent::getPersonID)
                .compare(this,dailyResult);
    }


}
