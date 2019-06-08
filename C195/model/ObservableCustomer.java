/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ObservableCustomer extends Customer {
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
    public    SimpleStringProperty        countryName = new SimpleStringProperty();
    
    @Override
    public  int     getCustomerId()             { return customerId.get(); }
    @Override
    public  String  getCustomerName()           { return customerName.get(); }
    @Override
    public  int     getActive()                 { return active.get(); }
    public  int     getAddressId()              { return addressId.get(); }
    public  String  getAddress1()               { return address1.get(); }
    public  String  getAddress2()               { return address2.get(); }
    public  String  getPostalCode()             { return postalCode.get(); }
    public  String  getPhone()                  { return phone.get(); }
    public  int     getCityId()                 { return cityId.get(); }
    public  String  getCityName()               { return cityName.get(); }
    public  String  getCountryName()            { return countryName.get(); }
    
    public ObservableCustomer(Customer cust) {
        this.customerId.set(cust.getCustomerId());
        this.customerName.set(cust.getCustomerName());
        this.active.set(cust.getActive());
        
        this.addressId.set(cust.getAddressObj().getAddressId());
        this.address1.set(cust.getAddressObj().getAddress1());
        this.address2.set(cust.getAddressObj().getAddress2());
        this.postalCode.set(cust.getAddressObj().getPostalCode());
        this.phone.set(cust.getAddressObj().getPhone());
        
        this.cityId.set(cust.getAddressObj().getCityObj().getCityId());
        this.cityName.set(cust.getAddressObj().getCityObj().getCityName());
        this.countryName.set(cust.getAddressObj().getCityObj().getCountryName());
    }
}
