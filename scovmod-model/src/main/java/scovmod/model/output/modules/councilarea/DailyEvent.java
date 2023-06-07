/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.councilarea;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class DailyEvent implements Comparable<DailyEvent>{

    private final int personID;
    private final CouncilAreaValueType type;
    private final int councilAreaID;
    public DailyEvent(int personID, CouncilAreaValueType type, int councilAreaID){
        this.personID = personID;
        this.type = type;
        this.councilAreaID = councilAreaID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyEvent that = (DailyEvent) o;
        return personID == that.personID &&
                councilAreaID == that.councilAreaID &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, type, councilAreaID);
    }

    public int getPersonID() { return personID; }

    public CouncilAreaValueType getType() {
        return type;
    }

    public int getCouncilAreaID() {
        return councilAreaID;
    }
/*

 */
    @Override
    public int compareTo(DailyEvent dailyResult) {
        return Comparator.comparing(DailyEvent::getType)
                .thenComparing(DailyEvent::getCouncilAreaID)
                .thenComparing(DailyEvent::getPersonID)
                .compare(this,dailyResult);
//        if(this.date.getDayOfYear()==dailyResult.date.getDayOfYear()) return this.getCouncilAreaID() - dailyResult.getCouncilAreaID();
//        else return this.date.getDayOfYear() - dailyResult.date.getDayOfYear();
    }


}
