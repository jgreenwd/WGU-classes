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
    private         SimpleIntegerProperty           customerId = new SimpleIntegerProperty();
    private         SimpleStringProperty            customerName = new SimpleStringProperty();
    private         SimpleIntegerProperty           active = new SimpleIntegerProperty();
    
    public int      getCustomerId()                 { return customerId.get(); }
    public void     setCustomerId(int ID)           { customerId.set(ID); }
    public String   getCustomerName()               { return customerName.get(); }
    public void     setCustomerName(String name)    { customerName.set(name); }
    public int      getActive()                     { return active.get(); }
    public void     setActive(int value)            { active.set(value); }
}
