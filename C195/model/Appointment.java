/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

import java.time.ZonedDateTime;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    private final   SimpleIntegerProperty       appointmentId = new SimpleIntegerProperty();
    private final   SimpleStringProperty        title = new SimpleStringProperty();
    private final   SimpleStringProperty        description = new SimpleStringProperty();
    private final   SimpleStringProperty        location = new SimpleStringProperty();
    private final   SimpleStringProperty        contact = new SimpleStringProperty();
    private final   SimpleStringProperty        type = new SimpleStringProperty();
    private final   SimpleStringProperty        url = new SimpleStringProperty();
    private final   Customer                    customer;
    private final   User                        user;
    private         ZonedDateTime               start;
    private         ZonedDateTime               end;
    
    public int      getAppointmentId()          { return appointmentId.get(); }
    public void     setAppointmentId(int ID)    { appointmentId.set(ID); }
    public String   getTitle()                  { return title.get(); }
    public void     setTitle(String title)      { this.title.set(title); }
    public String   getDescription()            { return description.get(); }
    public void     setDescription(String desc) { description.set(desc); }
    public String   getLocation()               { return location.get(); }
    public void     setLocation(String loc)     { location.set(loc); }
    public String   getContact()                { return contact.get(); }
    public void     setContact(String con)      { contact.set(con); }
    public String   getType()                   { return type.get(); }
    public void     setType(String type)        { this.type.set(type); }
    public String   getUrl()                    { return url.get(); }
    public void     setUrl(String url)          { this.url.set(url); }
    
    
    public ZonedDateTime getStart()             { return start; }
    public void     setStart(ZonedDateTime start){ this.start = start; }
    
    public ZonedDateTime getEnd()               { return end; }
    public void     setEnd(ZonedDateTime end)   { this.end = end; }
    
    public Appointment(int ID, Customer cust, User user, ZonedDateTime start, ZonedDateTime end) {
        this.appointmentId.set(ID);
        this.customer = cust;
        this.user = user;
        this.start = start;
        this.end = end;
    }
}
