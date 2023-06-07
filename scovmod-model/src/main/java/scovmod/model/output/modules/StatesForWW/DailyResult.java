/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.StatesForWW;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class DailyResult implements Comparable<DailyResult>{

    private final LocalDate date;
    private final StatesForWWValueType type;
    private final int dzID;
    public DailyResult(LocalDate date, StatesForWWValueType type, int dzID){
        this.date = date;
        this.type = type;
        this.dzID = dzID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyResult that = (DailyResult) o;
        return dzID == that.dzID &&
                Objects.equals(date, that.date) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type, dzID);
    }

    @Override
    public String toString() {
        return "DailyResult{" +
                "date=" + date +
                ", type=" + type +
                ", dzID=" + dzID +
                '}';
    }

    public LocalDate getDate() {
        return date;
    }

    public StatesForWWValueType getType() {
        return type;
    }

    public int getDzID() {
        return dzID;
    }

    @Override
    public int compareTo(DailyResult dailyResult) {
        return Comparator.comparing(DailyResult::getType)
                .thenComparing(DailyResult::getDzID)
                .thenComparing(DailyResult::getDate)
                .compare(this,dailyResult);
    }


}
