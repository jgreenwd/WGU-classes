/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package lib;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;


public class LocalDB {
    private static final ObservableList<Customer> CUSTOMER_LIST = FXCollections.observableArrayList();
    private static final List<Address> ADDRESSES = new ArrayList<>();
    private static final List<City> CITIES = new ArrayList<>();
    
    public static final void loadCustomers() throws SQLException {
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
                
                Address address = customer.getAddressObj();
                ADDRESSES.add(address);
                
            } while(Query.getResult().next());
        }
    }
    
    public static final ObservableList<Customer> getLocalDB() {
        return CUSTOMER_LIST;
    }
    
    public static final void add(Customer customer) {
        CUSTOMER_LIST.add(customer);
    }
    
    public static final void add(Address address) {
        ADDRESSES.add(address);
    }
    
    public static final void set(int index, Address address) {
        ADDRESSES.set(index, address);
    }
    
    public static final void set(int index, Customer customer) {
        CUSTOMER_LIST.set(index, customer);
    }
    
    public static final void remove(Customer customer) {
        CUSTOMER_LIST.remove(customer);
    }
    
    public static final void remove(Address address) {
        ADDRESSES.remove(address);
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
    public static final boolean contains(Address address) {
        String addr1 = address.getAddress1();
        String addr2 = address.getAddress2();
        String zip   = address.getPostalCode();
        String phone = address.getPhone();
        
        return ADDRESSES.stream().anyMatch((item) -> (
                item.getAddress1().equals(addr1) && 
                item.getAddress2().equals(addr2) &&
                item.getPostalCode().equals(zip) &&
                item.getPhone().equals(phone)));
    }
    
    public static final int getAddressId(Address address) {
        String addr1 = address.getAddress1();
        String addr2 = address.getAddress2();
        String zip   = address.getPostalCode();
        String phone = address.getPhone();
        
        for(Address item : ADDRESSES) {
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
    
    public static final boolean redundant(Address address) {
        return CUSTOMER_LIST.stream().anyMatch((customer) -> (
                customer.getAddressObj().getAddressId() == address.getAddressId())
        );
    }
}
