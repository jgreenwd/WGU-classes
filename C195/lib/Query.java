/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package lib;

import java.sql.PreparedStatement;
import static lib.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;

public class Query {

    private static ResultSet result;
    
    /* ===============================================================
     * General Utility Methods
     *
     * login(usr, pwd) - return boolean login credential validity
     * getUser(name) - return User object for current User
     * getAllCustomers() - general Query to populate local DB
     * getResult() - return result for all Queries
     * =============================================================== */
    public static boolean login(String usr, String pwd) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
            "SELECT CASE WHEN EXISTS(SELECT * FROM user WHERE userName=? AND password=?) "
            + "THEN TRUE "
            + "ELSE FALSE "
            +"END AS valid;");) {
            st.setString(1, usr);
            st.setString(2, pwd);

            result = st.executeQuery();
        
            return result.next() ? result.getBoolean("valid") : false;
        }
    }
    
    public static User getUser(String name) throws SQLException {
        try(PreparedStatement st = conn.prepareStatement(
                "SELECT userId, userName, password, active "
                + "FROM user WHERE userName=?");) {
            st.setString(1, name);
            result = st.executeQuery();
            result.first();
        
            int userId = result.getInt("userID");
            String user = result.getString("userName");
            String pwd = result.getString("password");
            int active = result.getInt("active");
        
            return new User(userId, user, pwd, active);
        }
    }
    
    public static void getAllCustomers() throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement(
                "SELECT customer.customerId, customerName, active, "
                + "address.addressId, address, address2, postalCode, phone, "
                + "country.countryId, country, city.cityId, city "
                + "FROM country "
                + "JOIN city ON country.countryId = city.countryId "
                + "JOIN address ON city.cityId = address.cityId "
                + "JOIN customer ON address.addressId=customer.addressId "
                + "ORDER BY customer.customerId;");
            result = st.executeQuery();
        } catch(SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    public static void getAllAppointments() throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement(
                "SELECT appointmentId, customer.customerId, contact, "
                + "title, type, url, start, end, description, location "
                + "FROM customer JOIN appointment "
                + "ON customer.customerId = appointment.customerId;");
            
            result = st.executeQuery();
        } catch(SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    public static ResultSet getResult() {
        return result;
    }
    
    
    /* ===============================================================
     * Appointment Query Methods
     *
     * getAppointmentId(appt) - return appointmentId
     * insertAppointment(appointment, user) - add new appointment to remote DB
     * updateAppointment(appointment, user) - modify existing appointment entry in DB
     * deleteAppointment(appointment, user) - delete appointment entry from DB
     * =============================================================== */
    public static int getAppointmentId(Appointment appt) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT appointmentId FROM appointment WHERE customerId=? "
                + "AND title=? AND type=? AND start=? AND end=? AND "
                + "location=? AND contact=?"); ) {
            st.setInt(1, appt.getCustomerId());
            st.setString(2, appt.getTitle());
            st.setString(3, appt.getType());
            st.setString(4, appt.getDate()+" "+appt.getStartTime());
            st.setString(5, appt.getDate()+" "+appt.getEndTime());
            st.setString(6, appt.getLocation());
            st.setString(7, appt.getContact());
            result = st.executeQuery();
            
            return result.next() ? result.getInt("appointmentId") : 0;
        }
    }
    
    public static void insertAppointment(Appointment appt, User user) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "INSERT INTO appointment(customerId, title, description, location, "
                + "contact, url, start, end, createDate, createdBy, lastUpdate,"
                + "lastUpdateBy, type, userId) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?);"); ){
            Customer cust = appt.getCustomerObj();
            st.setInt(1, cust.getCustomerId());
            st.setString(2, appt.getTitle());
            st.setString(3, "");                 // description not available in app
            st.setString(4, cust.getCityName()+", "+cust.getCountryName());
            st.setString(5, user.getUsername()); // supply User as contact
            st.setString(6, appt.getUrl());
            st.setString(7, appt.getDate()+ " "+appt.getStartTime()+":00");
            st.setString(8, appt.getDate()+ " "+appt.getEndTime()+":00");
            st.setString(9, user.getUsername());
            st.setString(10, user.getUsername());
            st.setString(11, appt.getType());
            st.setInt(12, user.getUserID());
            st.executeUpdate();
        }
    }
    
    public static void updateAppointment(Appointment appt, User user) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "UPDATE appointment SET customerId=?, title=?, type=?, url=?, "
                + "start=?, end=?, lastUpdate=NOW(), lastUpdateBy=? "
                + "WHERE appointmentId=?");) {
            st.setInt(1, appt.getCustomerId());
            st.setString(2, appt.getTitle());
            st.setString(3, appt.getType());
            st.setString(4, appt.getUrl());
            st.setString(5, appt.getDate()+ " "+appt.getStartTime()+":00");
            st.setString(6, appt.getDate()+ " "+appt.getEndTime()+":00");
            st.setString(7, user.getUsername());
            st.setInt(8, appt.getAppointmentId());
            st.executeUpdate();
        }
    }
    
    public static void deleteAppointment(Appointment appt) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM appointment WHERE appointmentId=?");) {
            st.setInt(1, appt.getAppointmentId());
            st.executeUpdate();
        }
    }
    /* ===============================================================
     * Customer Query Methods
     *
     * getCustomerId(name) - return customerId
     * insertCustomer(customer, user) - add new customer to remote DB
     * updateCustomer(customer, user) - modify existing customer entry in DB
     * deleteCustomer(customer, user) - delete customer entry from DB
     * =============================================================== */
    public static int getCustomerId(String name) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT customerId FROM customer WHERE customerName=?");) {
            st.setString(1, name);
            result = st.executeQuery();
            
            return result.next() ? result.getInt("customerId") : 0;
        }
    }
    
    public static void insertCustomer(Customer customer, User user) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "INSERT INTO customer(customerName, addressId, active, "
                + "createDate, createdBy, lastUpdate, lastUpdateBy) "
                + "VALUES(?, ?, ?, NOW(), ?, NOW(), ?)");) {
            st.setString(1, customer.getCustomerName());
            st.setInt(   2, customer.getAddressObj().getAddressId());
            st.setInt(   3, customer.getActive());
            st.setString(4, user.getUsername());
            st.setString(5, user.getUsername());
            st.executeUpdate();
        }
    }
    
    public static void updateCustomer(Customer customer, User user) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "UPDATE customer SET customerName=?, addressId=?, "
                + "active=?, lastUpdate=NOW(), lastUpdateBy=? WHERE customerId=?");) {
            st.setString(1, customer.getCustomerName());
            st.setInt(   2, customer.getAddressObj().getAddressId());
            st.setInt(   3, customer.getActive());
            st.setString(4, user.getUsername());
            st.setInt(   5, customer.getCustomerId());
            st.executeUpdate();
        }
    }
    
    public static void deleteCustomer(Customer customer) throws SQLException{
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM customer WHERE customerId=?");) {
            st.setInt(1, customer.getCustomerId());
            st.executeUpdate();
        }
    }
    
    
    /* ===============================================================
     * Address Query Methods
     *
     * getAddressId(address) - return addressId
     * insertAddress(address, user) - add new address to remote DB
     * updateAddress(address, user) - modify existing address entry in DB
     * deleteAddress(address, user) - delete address entry from DB
     * =============================================================== */
    public static int getAddressId(Address address) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT addressId FROM address WHERE address=? AND "
                + "address2=? AND postalCode=? AND phone=? AND cityId=?");) {
            st.setString(1, address.getAddress1());
            st.setString(2, address.getAddress2());
            st.setString(3, address.getPostalCode());
            st.setString(4, address.getPhone());
            st.setInt(5, address.getCityObj().getCityId());
            result = st.executeQuery();
            
            return result.next() ? result.getInt("addressId") : 0;
        }
    }
    
    public static void insertAddress(Address addr, User user) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "INSERT INTO address(address, cityId, address2, postalCode, phone, "
                + "createDate, createdBy, lastUpdate, lastUpdateBy) "
                + "VALUES(?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)");) {
            st.setString(1, addr.getAddress1());
            st.setInt(   2, addr.getCityObj().getCityId());
            st.setString(3, addr.getAddress2());
            st.setString(4, addr.getPostalCode());
            st.setString(5, addr.getPhone());
            st.setString(6, user.getUsername());
            st.setString(7, user.getUsername());
            st.executeUpdate();
        }
    }
    
    public static void updateAddress(Address address, User user) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "UPDATE address SET address=?, address2=?, cityId=?, "
                + "postalCode=?, phone=?, lastUpdate=NOW(), lastUpdateBy=? "
                + "WHERE addressId=?");) {
            st.setString(1, address.getAddress1());
            st.setString(2, address.getAddress2());
            st.setInt(   3, address.getCityObj().getCityId());
            st.setString(4, address.getPostalCode());
            st.setString(5, address.getPhone());
            st.setString(6, user.getUsername());
            st.setInt(   7, address.getAddressId());
            st.executeUpdate();
        }
    }
    
    public static void deleteAddress(int addressId) throws SQLException{
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM address WHERE addressId=?");) {
            st.setInt(1, addressId);
            st.executeUpdate();
        }
    }
    
    public static boolean isSingleton(Address address) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT COUNT(addressId) FROM customer WHERE addressId=?;");) {
            st.setInt(1, address.getAddressId());
            result = st.executeQuery();
            result.first();
            
            return result.getInt("COUNT(addressId)") == 0;
        }
    }
    
}
