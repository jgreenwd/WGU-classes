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
    private final   SimpleIntegerProperty           customerId = new SimpleIntegerProperty();
    private final   SimpleStringProperty            customerName = new SimpleStringProperty();
    private final   SimpleIntegerProperty           active = new SimpleIntegerProperty();
    private         Address                         address;
    
    public int      getCustomerId()                 { return customerId.get(); }
    public void     setCustomerId(int ID)           { customerId.set(ID); }
    public String   getCustomerName()               { return customerName.get(); }
    public void     setCustomerName(String name)    { customerName.set(name); }
    public int      getActive()                     { return active.get(); }
    public void     setActive(int value)            { active.set(value); }
    public Address  getAddress()                    { return address; }
    public void     setAddress(Address address)     { this. address = address; }
    
    public Customer(Address address) {
        this.address = address;
    }
}
