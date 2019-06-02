/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

public class CustomerDelegate {
    private         Customer                        customer = new Customer();
    private         Address                         address = new Address();
    private         City                            city = new City();
    private         Country                         country = new Country();
    
    // Customer
    public int      getCustomerId()                 { return customer.getCustomerId(); }
    public void     setCustomerId(int ID)           { customer.setCustomerId(ID); }
    public String   getCustomerName()               { return customer.getCustomerName(); }
    public void     setCustomerName(String name)    { customer.setCustomerName(name); }
    public int      getActive()                     { return customer.getActive(); }
    public void     setActive(int value)            { customer.setActive(value); }
    
    // Address
    public int      getAddressId()                  { return address.getAddressId(); }
    public void     setAddressID(int ID)            { address.setAddressID(ID); }
    public String   getAddress()                    { return address.getAddress(); }
    public void     setAddress(String addr)         { address.setAddress(addr); }
    public String   getAddress2()                   { return address.getAddress2(); }
    public void     setAddress2(String addr2)       { address.setAddress2(addr2); }
    public String   getPostalCode()                 { return address.getPostalCode(); }
    public void     setPostalCode(String zip)       { address.setPostalCode(zip); }
    public String   getPhone()                      { return address.getPhone(); }
    public void     setPhone(String num)            { address.setPhone(num); }

    // City
    public int      getCityId()                     { return city.getCityId(); }
    public void     setCityID(int ID)               { city.setCityID(ID); }
    public String   getCityName()                   { return city.getCityName(); }
    public void     setCityName(String city)        { this.city.setCityName(city); }
    
    // Country
    public int      getCountryId()                  { return country.getCountryId(); }
    public void     setCountryId(int ID)            { country.setCountryId(ID); }
    public String   getCountry()                    { return country.getCountry(); }
    public void     setCountry(String country)      { this.country.setCountry(country); }
    
    public CustomerDelegate(Customer customer, Address address, City city, Country country) {
        this.customer = customer;
        this.address = address;
        this.city = city;
        this.country = country;
    }
}
