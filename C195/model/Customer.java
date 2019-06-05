/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

public class Customer {
    private int     customerId;
    private String  customerName;
    private int     active;
    private Address address;
    
    public  int     getCustomerId()             { return customerId; }
    public  void    setCustomerId(int ID)       { customerId = ID; }
    public  String  getCustomerName()           { return customerName; }
    public  void    setCustomerName(String name){ customerName = name; }
    public  int     getActive()                 { return active; }
    public  void    setActive(int value)        { active = value; }
    public  Address getAddressObj()             { return address; }
    public  void    setAddressObj(Address addr) { this.address = addr; }
    
    public Customer(Address address) {
        this.address = address;
    }
    
    public Customer(String name, Address address) {
        this(address);
        this.customerName = name;
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
