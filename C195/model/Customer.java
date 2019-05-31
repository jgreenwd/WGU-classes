/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleIntegerProperty      customerId = new SimpleIntegerProperty();
    private final SimpleStringProperty       customerName = new SimpleStringProperty();
    private final SimpleStringProperty       address = new SimpleStringProperty();
    private final SimpleStringProperty       postalCode = new SimpleStringProperty();
    private final SimpleStringProperty       phone = new SimpleStringProperty();
    private final SimpleStringProperty       city = new SimpleStringProperty();
    private final SimpleStringProperty       country = new SimpleStringProperty();
    
    public Customer(int ID, String Name, String phone, String addr, String addr2, String zip, String city, String country) {
        this.customerId.set(ID);
        this.customerName.set(Name);
        this.phone.set(phone);
        this.address.set((addr + " " + addr2).trim());
        this.postalCode.set(zip);
        this.city.set(city);
        this.country.set(country);
    }
    
    public void setCustomerId(int id)        { customerId.set(id); }
    public void setCustomerName(String name) { customerName.set(name); }
    public void setAddress(String addr)      { address.set(addr); }
    public void setPostalCode(String zip)    { postalCode.set(zip); }
    public void setPhone(String tele)        { phone.set(tele); }
    public void setCity(String city)         { this.city.set(city); }
    public void setCountry(String country)   { this.country.set(country); }
    
    public int    getCustomerId()            { return customerId.get(); }
    public String getCustomerName()          { return customerName.get(); }
    public String getAddress()               { return address.get(); }
    public String getPostalCode()            { return postalCode.get(); }
    public String getPhone()                 { return phone.get(); }
    public String getCity()                  { return city.get(); }
    public String getCountry()               { return country.get(); }
    
}
