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
    With the understanding that this project is an exercise in making SQL queries
    to a remote DataBase, I have tried to use a local copy of the DB indexes to 
    minimize expensive calls to external resources. List refreshes (use of init()) 
    go against this, but ensure accurate information is displayed.
   ***************************************************************************** */
public class LocalDB {
    private static final ObservableList<Customer> CUSTOMER_LIST = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> APPOINTMENT_LIST = FXCollections.observableArrayList();
    private static final List<City> CITIES = new ArrayList<>();
    
    
    /* =========================================================================
     * General Utility Methods
     *
     * init() - populate local copy of Customers & Appointments
     * getLocalCustomer() - return CUSTOMER_LIST for rendering
     * getLocalAppointment() - return APPOINTMENT_LIST for rendering
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
    
    public static final ObservableList<Customer> getAllCustomers() {
        return CUSTOMER_LIST;
    }
        
    public static final ObservableList<Appointment> getAllAppointments() {
        return APPOINTMENT_LIST;
    }
    
    
    
    /* =========================================================================
     * Appointment Query Methods
     *
     * isAvailable(appt, user) - is there a time conflict for this user
     * add(appointment) - add appointment entry to remote DB & LocalDB
     * set(index, appointment) - modify appointment entry
     * remove(appointment) - delete appointment entry
     * ========================================================================= */
    public static final boolean isAvailable(Appointment appt, User user) {
        boolean available = true;
        
        // Select all the elements assigned to this user
        ArrayList<Appointment> list = new ArrayList<>();
        APPOINTMENT_LIST
                .stream()
                .filter(a -> a.getContact().equals(user.getUsername()))
                .forEachOrdered(a -> list.add(a));
        
        for(Appointment ap : list) {
            // if appt does not start & end before ap starts ... 
            if (appt.getStart().isBefore(ap.getStart()) && !appt.getEnd().equals(ap.getStart())) {
                if (!appt.getEnd().isBefore(ap.getStart())) {
                    available = false;
                }
            }
            // if appt does not start & end after ap ends ...
            if (appt.getEnd().isAfter(ap.getEnd()) && !appt.getStart().equals(ap.getEnd())) {
                if (!appt.getStart().isAfter(ap.getEnd())) {
                    available = false;
                }
            }
        }
        
        return available;
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
     * get(customerId) - return customer object w/ customerId
     * get(name) - return customer object w/ name
     * add(customer) - add customer entry to remote DB & LocalDB
     * set(index, customer) - modify customer entry
     * remove(customer) - delete customer entry
     * ========================================================================= */
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
