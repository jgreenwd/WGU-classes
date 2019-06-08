/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

import java.util.function.Consumer;

public class CustomerBuilder {
    
    public  int     customerId;
    public  String  customerName;
    public  int     active;
        
    public  int     addressId;
    public  String  address1;
    public  String  address2;
    public  String  postalCode;
    public  String  phone;
    
    public  int     cityId;
    public  String  cityName;
    public  String  countryName;
    
    public  CustomerBuilder setCustomerId(int ID)       { customerId = ID; return this;}
    public  CustomerBuilder setCustomerName(String name){ customerName = name; return this; }
    public  CustomerBuilder setActive(int value)        { active = value; return this;}
    public  CustomerBuilder setAddressId(int ID)        { addressId = ID; return this; }
    public  CustomerBuilder setAddress1(String addr1)   { address1 = addr1; return this; }
    public  CustomerBuilder setAddress2(String addr2)   { address2 = addr2; return this; }
    public  CustomerBuilder setPostalCode(String zip)   { postalCode = zip; return this; }
    public  CustomerBuilder setPhone(String num)        { phone = num; return this; }
    public  CustomerBuilder setCityId(int ID)           { cityId = ID; return this; }
    public  CustomerBuilder setCityName(String name)    { cityName = name; return this; }
    public  CustomerBuilder setCountryName(String name) { countryName = name; return this; }
    
    public CustomerBuilder with( Consumer<CustomerBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }
    
    public Customer createCustomer() {
        return new Customer(customerId, active, customerName, 
            new Address(addressId, address1, address2, postalCode, phone,
                new City(cityId, cityName, countryName)
            )
        );
    }
}
