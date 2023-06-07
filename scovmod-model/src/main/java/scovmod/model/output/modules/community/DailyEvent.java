/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.output.modules.community;

import java.util.Objects;

public class DailyEvent{

    private final int personID;
    private final CommunityValueType type;
    private final int locationID;
    public DailyEvent(int personID, CommunityValueType type, int locationID){
        this.personID = personID;
        this.type = type;
        this.locationID = locationID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyEvent that = (DailyEvent) o;
        return personID == that.personID &&
                locationID == that.locationID &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, type, locationID);
    }

    public int getPersonID() { return personID; }

    public CommunityValueType getType() {
        return type;
    }

    public int getCommunityID() {
        return locationID;
    }

}
