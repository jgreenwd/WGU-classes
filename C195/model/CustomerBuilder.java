/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package model;

import java.util.function.Consumer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerBuilder {
    
    public    SimpleIntegerProperty       customerId = new SimpleIntegerProperty();
    public    SimpleStringProperty        customerName = new SimpleStringProperty();
    public    SimpleIntegerProperty       active = new SimpleIntegerProperty();
        
    public    SimpleIntegerProperty       addressId = new SimpleIntegerProperty();
    public    SimpleStringProperty        address1 = new SimpleStringProperty();
    public    SimpleStringProperty        address2 = new SimpleStringProperty();
    public    SimpleStringProperty        postalCode = new SimpleStringProperty();
    public    SimpleStringProperty        phone = new SimpleStringProperty();
    
    public    SimpleIntegerProperty       cityId = new SimpleIntegerProperty();
    public    SimpleStringProperty        cityName = new SimpleStringProperty();
    
    public    SimpleIntegerProperty       countryId = new SimpleIntegerProperty();
    public    SimpleStringProperty        countryName = new SimpleStringProperty();
    
    public  CustomerBuilder setCustomerId(int ID)       { customerId.set(ID); return this;}
    public  CustomerBuilder setCustomerName(String name){ customerName.set(name); return this; }
    public  CustomerBuilder setActive(int value)        { active.set(value); return this;}
    public  CustomerBuilder setAddressId(int ID)        { addressId.set(ID); return this; }
    public  CustomerBuilder setAddress1(String addr1)   { address1.set(addr1); return this; }
    public  CustomerBuilder setAddress2(String addr2)   { address2.set(addr2); return this; }
    public  CustomerBuilder setPostalCode(String zip)   { postalCode.set(zip); return this; }
    public  CustomerBuilder setPhone(String num)        { phone.set(num); return this; }
    public  CustomerBuilder setCityId(int ID)           { cityId.set(ID); return this; }
    public  CustomerBuilder setCityName(String city)    { cityName.set(city); return this; }
    public  CustomerBuilder setCountryId(int ID)        { countryId.set(ID); return this; }
    public  CustomerBuilder setCountryName(String ctry) { countryName.set(ctry); return this; }
    
    public CustomerBuilder with( Consumer<CustomerBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }
    
    public Customer createCustomer() {
        return new Customer(customerId.get(), active.get(), customerName.get(), 
            new Address(addressId.get(), address1.get(), address2.get(), postalCode.get(), phone.get(), 
                new City(cityId.get(), cityName.get(), 
                    new Country(countryId.get(), countryName.get())
                )
            )
        );
    }
}
