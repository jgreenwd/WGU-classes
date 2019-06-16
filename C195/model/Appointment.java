/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    
    /*  I placed operating hours in the appointment class to make the business
        hours test more readable, ie. appointment.isDuringBusinessHours()
        operating hours: 8amGMT - 10pmGMT
        assume open 7 days per week                                             */
    private final int                           openHour = 8; // GMT
    private final int                           openMin  = 0;
    private final int                           closeHour = 22;
    private final int                           closeMin  = 0;
    
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
    
    public boolean  isDuringBusinessHours()     {
        ZonedDateTime open = ZonedDateTime.of(start.getYear(), start.getMonthValue(), 
                start.getDayOfMonth(), openHour, openMin, 0, 0, ZoneId.of("UTC"));
        
        ZonedDateTime close = ZonedDateTime.of(end.getYear(), end.getMonthValue(), 
                end.getDayOfMonth(), closeHour, closeMin, 0, 0, ZoneId.of("UTC"));
        
        ZonedDateTime begin = this.start.atZone(ZoneId.systemDefault());
        ZonedDateTime finish = this.end.atZone(ZoneId.systemDefault());
        
        return (begin.equals(open) || begin.isAfter(open)) &&
               (finish.equals(close) || finish.isBefore(close));
    }
    
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
