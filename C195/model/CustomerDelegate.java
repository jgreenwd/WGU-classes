/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerDelegate {
    private         Customer                        customer;
    
    // Customer
    private final   SimpleIntegerProperty           customerId = new SimpleIntegerProperty();
    private final   SimpleStringProperty            customerName = new SimpleStringProperty();
    private final   SimpleIntegerProperty           active = new SimpleIntegerProperty();

    public int      getCustomerId()                 { return customerId.get(); }
    public void     setCustomerId(int ID)           { customerId.set(ID); }
    public String   getCustomerName()               { return customerName.get(); }
    public void     setCustomerName(String name)    { customerName.set(name); }
    public int      getActive()                     { return active.get(); }
    public void     setActive(int value)            { active.set(value); }

    // Address
    private final SimpleIntegerProperty           addressId = new SimpleIntegerProperty();
    private final SimpleStringProperty            address = new SimpleStringProperty();
    private final SimpleStringProperty            address2 = new SimpleStringProperty();
    private final SimpleStringProperty            postalCode = new SimpleStringProperty();
    private final SimpleStringProperty            phone = new SimpleStringProperty();

    public int      getAddressId()                  { return addressId.get(); }
    public void     setAddressId(int ID)            { addressId.set(ID); }
    public String   getAddress()                    { return address.get(); }
    public void     setAddress(String addr)         { address.set(addr); }
    public String   getAddress2()                   { return address2.get(); }
    public void     setAddress2(String addr2)       { address2.set(addr2); }
    public String   getPostalCode()                 { return postalCode.get(); }
    public void     setPostalCode(String zip)       { postalCode.set(zip); }
    public String   getPhone()                      { return phone.get(); }
    public void     setPhone(String num)            { phone.set(num); }
    
    // City
    private final SimpleIntegerProperty           cityId = new SimpleIntegerProperty();
    private final SimpleStringProperty            cityName = new SimpleStringProperty();

    public int      getCityId()                     { return cityId.get(); }
    public void     setCityId(int ID)               { cityId.set(ID); }
    public String   getCityName()                   { return cityName.get(); }
    public void     setCityName(String city)        { cityName.set(city); }

    // Country
    private final SimpleIntegerProperty           countryId = new SimpleIntegerProperty();
    private final SimpleStringProperty            countryName = new SimpleStringProperty();
    
    public int      getCountryId()                  { return countryId.get(); }
    public void     setCountryId(int ID)            { countryId.set(ID); }
    public String   getCountry()                    { return countryName.get(); }
    public void     setCountry(String country)      { countryName.set(country); }
    
    public CustomerDelegate(Customer customer) {
        this.customer = customer;
        
        // initialize observable values
        customerId.set(customer.getCustomerId());
        customerName.set(customer.getCustomerName());
        active.set(customer.getActive());
        
        addressId.set(customer.getAddress().getAddressId());
        address.set(customer.getAddress().getAddress());
        address2.set(customer.getAddress().getAddress2());
        postalCode.set(customer.getAddress().getPostalCode());
        phone.set(customer.getAddress().getPhone());
        
        cityId.set(customer.getAddress().getCity().getCityId());
        cityName.set(customer.getAddress().getCity().getCityName());
        
        countryId.set(customer.getAddress().getCity().getCountry().getCountryId());
        countryName.set(customer.getAddress().getCity().getCountry().getCountry());
    }
}
