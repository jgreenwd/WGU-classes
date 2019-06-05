/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

public class Address {
    private int     addressId;
    private String  address1;
    private String  address2;
    private String  postalCode;
    private String  phone;
    private City    city;

    public  int     getAddressId()              { return addressId; }
    public  void    setAddressId(int ID)        { addressId = ID; }
    public  String  getAddress1()               { return address1; }
    public  void    setAddress1(String addr)    { address1 = addr; }
    public  String  getAddress2()               { return address2; }
    public  void    setAddress2(String addr2)   { address2 = addr2; }
    public  String  getPostalCode()             { return postalCode; }
    public  void    setPostalCode(String zip)   { postalCode = zip; }
    public  String  getPhone()                  { return phone; }
    public  void    setPhone(String num)        { phone = num; }
    public  City    getCityObj()                { return city; }
    public  void    setCityObj(City city)       { this.city = city; }
    
    
    public Address(City city) {
        this.city = city;
    }
    
    public Address(String postalCode, City city) {
        this(city);
        this.postalCode = postalCode;
        
    }
    
    public Address(String address1, String postalCode, City city) {
        this(postalCode, city);
        this.address1 = address1;
    }
    
    public Address(String address1, String address2, String postalCode, City city) {
        this(address1, postalCode, city);
        this.address2 = address2;
    }
    
    public Address(String address1, String address2, String postalCode, String phone, City city) {
        this(address1, address2, postalCode, city);
        this.phone = phone;
    }
    
    public Address(int ID, String address1, String address2, String postalCode, String phone, City city) {
        this(address1, address2, postalCode, phone, city);
        this.addressId = ID;
    }
}
