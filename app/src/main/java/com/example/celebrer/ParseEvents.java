package com.example.celebrer;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Shrabanti on 02-12-2015.
 */
@ParseClassName("ParseEvents")
public class ParseEvents extends ParseObject {
    public String getEventId() {
        return getString("objectId");
    }

    public String getEventName() {
        return getString("eventName");
    }

    public String getEventLocation() {
        return getString("eventLocation");
    }

    public int getPeopleGoing() {
        return getInt("peopleGoing");
    }

    public String getEventCreator(){return getString("eventCreator");}

    public String getTicketPrice(){return getString("ticketPrice");}

    public String getEventCategory(){return getString("eventCategory");}

    public String getEventType(){return getString("eventType");}

    public String getEventHost(){return getString("eventHost");}

    public void setEventName(String name){put("eventName", name);}

    public void setEventLocation(String location){put("eventLocation", location);}

    public void setPeopleGoing(int peopleGoing){put("peopleGoing", peopleGoing);}

    public void setTicketPrice(String price){put("ticketPrice", price);}

    public void setEventCategory(String category){put("eventCategory", category);}

    public void setEventType(String type){put("eventType", type);}

    public void setEventHost(String host){put("eventHost", host);}

    public void setEventCreator(String evCreator){put("eventCreator", evCreator);}

}
