/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private int                                 customerId;
    private int                                 active;
    private final   SimpleStringProperty        customerName = new SimpleStringProperty();
    
    public  int     getCustomerId()             { return customerId; }
    public  void    setCustomerId(int ID)       { customerId = ID; }
    public  String  getCustomerName()           { return customerName.get(); }
    public  void    setCustomerName(String name){ customerName.set(name); }
    public  int     getActive()                 { return active; }
    public  void    setActive(int value)        { active = value; }
    
    private Address address;
    public  int     getAddressId()              { return address.getAddressId(); }
    public  String  getAddress1()               { return address.getAddress1(); }
    public  String  getAddress2()               { return address.getAddress2(); }
    public  String  getPostalCode()             { return address.getPostalCode(); }
    public  String  getPhone()                  { return address.getPhone(); }
    public  int     getCityId()                 { return address.getCityObj().getCityId(); }
    public  String  getCityName()               { return address.getCityObj().getCityName(); }
    public  String  getCountryName()            { return address.getCityObj().getCountryName(); }
    public  Address getAddressObj()             { return address; }
    public  void    setAddressObj(Address addr) { this.address = addr; }
    
    
    public Customer() {}
    
    public Customer(Address address) {
        this.address = address;
    }
    
    public Customer(String name, Address address) {
        this(address);
        this.customerName.set(name);
    }
    
    public Customer(int active, String name, Address address) {
        this(name, address);
        this.active = active;
    }
    
    public Customer(int ID, int active, String name, Address address) {
        this(active, name, address);
        this.customerId = ID;
    }
}
