/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

import javafx.beans.property.SimpleStringProperty;

public class Address {
    private int                         addressId;
    private final SimpleStringProperty  address1 = new SimpleStringProperty();
    private final SimpleStringProperty  address2 = new SimpleStringProperty();
    private final SimpleStringProperty  postalCode = new SimpleStringProperty();
    private final SimpleStringProperty  phone = new SimpleStringProperty();
    private City                  city;

    public  int     getAddressId()              { return addressId; }
    public  void    setAddressId(int ID)        { addressId = ID; }
    public  String  getAddress1()               { return address1.get(); }
    public  void    setAddress1(String addr)    { address1.set(addr); }
    public  String  getAddress2()               { return address2.get(); }
    public  void    setAddress2(String addr2)   { address2.set(addr2); }
    public  String  getPostalCode()             { return postalCode.get(); }
    public  void    setPostalCode(String zip)   { postalCode.set(zip); }
    public  String  getPhone()                  { return phone.get(); }
    public  void    setPhone(String num)        { phone.set(num); }
    public  City    getCityObj()                { return city; }
    public  void    setCityObj(City city)       { this.city = city; }
    
    public boolean equals(Address addr) {
        return this.address1 == addr.address1 &&
               this.address2 == addr.address2 &&
               this.postalCode == addr.postalCode &&
               this.phone == addr.phone &&
               this.city == addr.city;
    }
    
    
    public Address(City city) {
        this.city = city;
    }
    
    public Address(String postalCode, City city) {
        this(city);
        this.postalCode.set(postalCode);
        
    }
    
    public Address(String address1, String postalCode, City city) {
        this(postalCode, city);
        this.address1.set(address1);
    }
    
    public Address(String address1, String address2, String postalCode, City city) {
        this(address1, postalCode, city);
        this.address2.set(address2);
    }
    
    public Address(String address1, String address2, String postalCode, String phone, City city) {
        this(address1, address2, postalCode, city);
        this.phone.set(phone);
    }
    
    public Address(int ID, String address1, String address2, String postalCode, String phone, City city) {
        this(address1, address2, postalCode, phone, city);
        this.addressId = ID;
    }
}
