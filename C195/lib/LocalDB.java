/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package lib;

import c195.C195;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;


/* *******************************************************
    Use a local copy of the DB to avoid expensive calls
    to external resources.
******************************************************* */
public class LocalDB {
    private static final ObservableList<Customer> CUSTOMER_LIST = FXCollections.observableArrayList();
    private static final List<City> CITIES = new ArrayList<>();
    
    public static final void init() throws SQLException {
        // Query DB for Customer details
        Query.getAllCustomers();

        // build ObservableList of Customer objects
        if (Query.getResult().next() == false) {
            System.out.println("Empty ResultSet");
        } else {
            do {
                Customer customer = new CustomerBuilder()
                    .setCustomerId(Query.getResult().getInt("customerId"))
                    .setCustomerName(Query.getResult().getString("customerName"))
                    .setActive(Query.getResult().getInt("active"))
                    .setAddressId(Query.getResult().getInt("addressId"))
                    .setAddress1(Query.getResult().getString("address"))
                    .setAddress2(Query.getResult().getString("address2"))
                    .setPostalCode(Query.getResult().getString("postalCode"))
                    .setPhone(Query.getResult().getString("phone"))
                    .setCityId(Query.getResult().getInt("cityId"))
                    .setCityName(Query.getResult().getString("city"))
                    .setCountryName(Query.getResult().getString("country"))
                .createCustomer();
                CUSTOMER_LIST.add( customer );
                
                City city = customer.getAddressObj().getCityObj();
                CITIES.add(city);
                
            } while(Query.getResult().next());
        }
    }
    
    public static final ObservableList<Customer> getLocalDB() {
        return CUSTOMER_LIST;
    }
    
    public static final void add(Customer customer) throws SQLException {
        Address address = customer.getAddressObj();
        if (exists(address)) {
            address.setAddressId(getAddressId(address));
        } else {
            Query.insertAddress(address, C195.user);
            address.setAddressId(Query.getAddressId(address));
        }
        
        Query.insertCustomer(customer, C195.user);
        customer.setCustomerId(Query.getCustomerId(customer.getCustomerName()));
        CUSTOMER_LIST.add(customer);
    }
        
    public static final void set(int index, Customer customer) {
        CUSTOMER_LIST.set(index, customer);
    }
    
    public static final void remove(Customer customer) throws SQLException {
        Address address = customer.getAddressObj();
        
        Query.deleteCustomer(customer);
        
        // If address no long used in DB, remove
        if (Query.isSingleton(address)){
            Query.deleteAddress(address.getAddressId());
        }
        
        CUSTOMER_LIST.remove(customer);
    }
       
    // Required to validate cities passed with no cityId
    public static final boolean contains(City city) {
        String ciName = city.getCityName();
        String coName = city.getCountryName();
        
        return CITIES.stream().anyMatch((item) -> (
                item.getCityName().equals(ciName) && 
                item.getCountryName().equals(coName)));
    }
    
    // Required to validate cities passed with no cityId
    public static final boolean exists(Address address) {
        String addr1 = address.getAddress1();
        String addr2 = address.getAddress2();
        String zip   = address.getPostalCode();
        String phone = address.getPhone();
        
        return CUSTOMER_LIST.stream().anyMatch((cust) -> (
                cust.getAddress1().equals(addr1) && 
                cust.getAddress2().equals(addr2) &&
                cust.getPostalCode().equals(zip) &&
                cust.getPhone().equals(phone)));
    }
    
    public static final int getAddressId(Address address) {
        String addr1 = address.getAddress1();
        String addr2 = address.getAddress2();
        String zip   = address.getPostalCode();
        String phone = address.getPhone();
        
        for(Customer item : CUSTOMER_LIST) {
            if (item.getAddress1().equals(addr1) && 
                item.getAddress2().equals(addr2) &&
                item.getPostalCode().equals(zip) &&
                item.getPhone().equals(phone)){
                return item.getAddressId();
            }
        }
        
        return 0;
    }
    
    public static final int getCityId(City city) {
        String ciName = city.getCityName();
        String coName = city.getCountryName();
        
        for(City item : CITIES) {
            if (item.getCityName().equals(ciName) && item.getCountryName().equals(coName)) {
                return item.getCityId();
            }
        }
        
        return 0;
    }
}
