/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.WWSites;

import java.util.Comparator;
import java.util.Objects;

public class DailyEvent implements Comparable<DailyEvent>{

    private final int personID;
    private final WWSitesValueType type;
    private final String SiteID;
    public DailyEvent(int personID, WWSitesValueType type, String SiteID){
        this.personID = personID;
        this.type = type;
        this.SiteID = SiteID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyEvent that = (DailyEvent) o;
        return personID == that.personID &&
                SiteID == that.SiteID &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, type, SiteID);
    }

    public int getPersonID() { return personID; }

    public WWSitesValueType getType() {
        return type;
    }

    public String getSiteID() {
        return SiteID;
    }

    @Override
    public int compareTo(DailyEvent dailyResult) {
        return Comparator.comparing(DailyEvent::getType)
                .thenComparing(DailyEvent::getSiteID)
                .thenComparing(DailyEvent::getPersonID)
                .compare(this,dailyResult);
    }


}
