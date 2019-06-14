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


/* *****************************************************************************
    Use a local copy of the DB indexes to minimize expensive calls to external resources.
   ***************************************************************************** */
public class LocalDB {
    private static final ObservableList<Customer> CUSTOMER_LIST = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> APPOINTMENT_LIST = FXCollections.observableArrayList();
    private static final List<City> CITIES = new ArrayList<>();
    
    
    
    /* =========================================================================
     * General Utility Methods
     *
     * init() - populate local copy of Customers & Appointments, set timezone
     * getLocalCustomer() - return CUSTOMER_LIST for rendering
     * getLocalAppointment() - return APPOINTMENT_LIST for rendering
     * convertDateTime(String) - convert input String to LocalDateTime
     * ========================================================================= */
    public static final void init() throws SQLException {
        CUSTOMER_LIST.clear();
        APPOINTMENT_LIST.clear();
        
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
        
        // Query DB for Appointment details
        Query.getAllAppointments();

        // build ObservableList of Appointment objects
        if (Query.getResult().next() == false) {
            System.out.println("Empty ResultSet");
        } else {
            do {                       
                Appointment appt = new AppointmentBuilder()
                    .setId(Query.getResult().getInt("appointmentId"))
                    .setCustomerObj(  get(Query.getResult().getInt("customerId"))  )
                    .setTitle(Query.getResult().getString("title"))
                    .setType(Query.getResult().getString("type"))
                    .setUrl(Query.getResult().getString("url"))
                    .setContact(Query.getResult().getString("contact"))
                    .setStart(Query.getResult().getTimestamp("start").toLocalDateTime())
                    .setEnd(Query.getResult().getTimestamp("end").toLocalDateTime())
                    .setDescription(Query.getResult().getString("description"))
                    .setLocation(Query.getResult().getString("location"))                    
                .createAppointment();
                APPOINTMENT_LIST.add( appt );
                
            } while(Query.getResult().next());
        }
    }
    
    public static final ObservableList<Customer> getListCustomers() {
        return CUSTOMER_LIST;
    }
        
    public static final ObservableList<Appointment> getListAppointments() {
        return APPOINTMENT_LIST;
    }
    
    
    
    /* =========================================================================
     * Appointment Query Methods
     *
     * getId(appointment) - get appointmentId if appointment in LocalDB
     * add(appointment) - add appointment entry to remote DB & LocalDB
     * set(index, appointment) - modify appointment entry
     * remove(appointment) - delete appointment entry
     * ========================================================================= */
    public static final Appointment getId(Appointment appt) {
        
        return APPOINTMENT_LIST
                .stream()
                .filter(a -> a.getCustomerObj() == appt.getCustomerObj())
                .filter(a -> a.getContact().equals(appt.getContact()))
                .filter(a -> a.getDate().equals(appt.getDate()))
                .filter(a -> a.getStart() == appt.getStart())
                .filter(a -> a.getEnd() == appt.getEnd())
                .findAny()
                .get();
    }
    
    public static final void add(Appointment appt) throws SQLException{
        Query.insertAppointment(appt, C195.user);
        
        init();
    }
    
    public static final void set(int index, Appointment appt) throws SQLException {
        Query.updateAppointment(appt, C195.user);
        
        init();
    }
    
    public static final void remove(Appointment appt) throws SQLException {
        Query.deleteAppointment(appt);
        
        init();
    }
    
    
    
    /* =========================================================================
     * Customer Query Methods
     *
     * getId(name) - return customerId of name
     * get(customerId) - return customer object w/ customerId
     * get(name) - return customer object w/ name
     * add(customer) - add customer entry to remote DB & LocalDB
     * set(index, customer) - modify customer entry
     * remove(customer) - delete customer entry
     * ========================================================================= */
    
    public static final int getId(String name) {
        return CUSTOMER_LIST
                .stream()
                .filter(c -> c.getCustomerName().equals(name))
                .mapToInt(c -> c.getCustomerId())
                .sum() | 0;
    }
    
    public static final Customer get(int customerId) {
        return CUSTOMER_LIST
                .stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findAny()
                .get();
    }
    
    public static final Customer get(String name) {
        return CUSTOMER_LIST
                .stream()
                .filter(c -> c.getCustomerName().equals(name))
                .findAny()
                .get();
    }
    
    public static final void add(Customer customer) throws SQLException {
        Address address = customer.getAddressObj();
        
        // addressId is a FOREIGN KEY for Customer
        // if the address already exists, get addressId
        if (contains(address)) {
            address.setAddressId(getAddressId(address));
        } else {
        // if address does NOT exist, insert address, then get addressId
            Query.insertAddress(address, C195.user);
            address.setAddressId(Query.getAddressId(address));
        }
        
        Query.insertCustomer(customer, C195.user);
        
        init();
    }
        
    public static final void set(int index, Customer customer) throws SQLException {
        Address oldAddr = CUSTOMER_LIST.get(index).getAddressObj();
        Address newAddr = customer.getAddressObj();
        
        // If address has changed...
        if (!oldAddr.equals(newAddr)) {
            if (contains(newAddr)) {
                newAddr.setAddressId(getAddressId(newAddr));
            } else {
                Query.insertAddress(newAddr, C195.user);
                int ID = Query.getAddressId(newAddr);
                newAddr.setAddressId(ID);
            }
        }

        Query.updateCustomer(customer, C195.user);
        
        if (Query.isSingleton(oldAddr)) {
                Query.deleteAddress(oldAddr.getAddressId());
        }
        
        init();
    }
    
    public static final void remove(Customer customer) throws SQLException {
        for(Appointment appt: APPOINTMENT_LIST) {
            if(appt.getCustomerId() == customer.getCustomerId()){
                Query.deleteAppointment(appt);
            }
        }
        
        Address address = customer.getAddressObj();
        
        Query.deleteCustomer(customer);
        
        // If address no long used in DB, remove
        if (Query.isSingleton(address)){
            Query.deleteAddress(address.getAddressId());
        }

        init();
    }
       
    
    
    /* =========================================================================
     * Address/City Utility Methods
     *
     * contains(address) - validate address exists in LocalDB
     * contains(city) - validate city exists in LocalDB
     * getAddressId(address) - return addressId stored in LocalDB
     * getCityId(city) - return cityId stored in LocalDB
     * ========================================================================= */
    // to validate addresses passed with no addressId
    public static final boolean contains(Address address) {
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
        
    // to validate cities passed with no cityId
    public static final boolean contains(City city) {
        String ciName = city.getCityName();
        String coName = city.getCountryName();
        
        return CITIES.stream().anyMatch((item) -> (
                item.getCityName().equals(ciName) && 
                item.getCountryName().equals(coName)));
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
