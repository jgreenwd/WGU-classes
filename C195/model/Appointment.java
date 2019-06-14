/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    private int                                 appointmentId;
    private final   SimpleStringProperty        title = new SimpleStringProperty();
    private final   SimpleStringProperty        description = new SimpleStringProperty();
    private final   SimpleStringProperty        location = new SimpleStringProperty();
    private final   SimpleStringProperty        contact = new SimpleStringProperty();
    private final   SimpleStringProperty        type = new SimpleStringProperty();
    private final   SimpleStringProperty        url = new SimpleStringProperty();
    private final   SimpleStringProperty        startTime = new SimpleStringProperty();
    private final   SimpleStringProperty        endTime = new SimpleStringProperty();
    private final   SimpleStringProperty        date = new SimpleStringProperty();
    private         LocalDateTime               start;
    private         LocalDateTime               end;
    
    public int      getAppointmentId()          { return appointmentId; }
    public void     setAppointmentId(int ID)    { appointmentId = ID; }
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
    public LocalDateTime getStart()             { return start; }
    public void     setStart(LocalDateTime strt){ this.start = strt; }
    public LocalDateTime getEnd()               { return end; }
    public void     setEnd(LocalDateTime end)   { this.end = end; }
    public String   getStartTime()              { return this.startTime.get(); }
    public String   getEndTime()                { return this.endTime.get(); }
    public String   getDate()                   { return this.date.get(); }
    
    private         Customer                    customer;
    public int      getCustomerId()             { return this.customer.getCustomerId(); }
    public String   getCustomerName()           { return this.customer.getCustomerName(); }
    public Customer getCustomerObj()            { return this.customer; }
    public void     setCustomerObj(Customer c)  { this.customer = c; }
    
    
    public Appointment() {
        this.customer = new Customer();
    }
    
    public Appointment(int apptId, Customer cust, LocalDateTime start, LocalDateTime end, 
            String title, String type, String location, String contact, String url, String desc) {
        this.appointmentId = apptId;
        this.customer = cust;
        this.start = start;
        this.end = end;
        this.title.set(title);
        this.type.set(type);
        this.location.set(location);
        this.contact.set(contact);
        this.url.set(url);
        this.description.set(desc);
        this.description.set(desc);
        
        this.startTime.set(start.toLocalTime().toString());
        this.endTime.set(end.toLocalTime().toString());
        this.date.set(start.toLocalDate().toString());
    }
}
