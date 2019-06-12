/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public class AppointmentBuilder {
    private int      apptId;
    private Customer customer;
    private String   title;
    private String   desc;
    private String   loc;
    private String   contact;
    private String   type;
    private String   url;
    private LocalDateTime start;
    private LocalDateTime end;
    
    
//    private int     userId;
//    private String  userName;
    
    public AppointmentBuilder setId(int ID)                { this.apptId = ID; return this; }
    public AppointmentBuilder setCustomerObj(Customer c)   { this.customer = c; return this; }
    public AppointmentBuilder setTitle(String title)       { this.title = title; return this; }
    public AppointmentBuilder setDescription(String desc)  { this.desc = desc; return this; }
    public AppointmentBuilder setLocation(String loc)      { this.loc = loc; return this; }
    public AppointmentBuilder setContact(String cont)      { this.contact = cont; return this; }
    public AppointmentBuilder setType(String type)         { this.type = type; return this; }
    public AppointmentBuilder setUrl(String url)           { this.url = url; return this; }
    public AppointmentBuilder setStart(LocalDateTime st)   { this.start = st; return this; }
    public AppointmentBuilder setEnd(LocalDateTime end)    { this.end = end; return this; }

    
    public AppointmentBuilder with( Consumer<AppointmentBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }
    
    public Appointment createAppointment() {
        return new Appointment(apptId, customer, start, end, title, type, loc, contact, url, desc);
    }
   
}
