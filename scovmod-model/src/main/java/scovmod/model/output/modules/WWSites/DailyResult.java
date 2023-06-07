/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.WWSites;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class DailyResult implements Comparable<DailyResult>{

    private final LocalDate date;
    private final WWSitesValueType type;
    private final String siteID;
    public DailyResult(LocalDate date, WWSitesValueType type, String siteID){
        this.date = date;
        this.type = type;
        this.siteID = siteID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyResult that = (DailyResult) o;
        return siteID == that.siteID &&
                Objects.equals(date, that.date) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type, siteID);
    }

    @Override
    public String toString() {
        return "DailyResult{" +
                "date=" + date +
                ", type=" + type +
                ", dzID=" + siteID +
                '}';
    }

    public LocalDate getDate() {
        return date;
    }

    public WWSitesValueType getType() {
        return type;
    }

    public String getSiteID() {
        return siteID;
    }

    @Override
    public int compareTo(DailyResult dailyResult) {
        return Comparator.comparing(DailyResult::getType)
                .thenComparing(DailyResult::getSiteID)
                .thenComparing(DailyResult::getDate)
                .compare(this,dailyResult);
    }


}
