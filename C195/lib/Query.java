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
import java.sql.Statement;
import model.*;


public class Query {

    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    
    public static void makeQuery(String q) {
        query = q.toLowerCase();
        
        try {
            stmt = conn.createStatement();
            
            // determine query execution
            if (query.startsWith("select")) {
                result = stmt.executeQuery(query);
            } 
            
            if (query.startsWith("delete") || query.startsWith("insert") || query.startsWith("update")) {
                stmt.executeUpdate(query);
            }
        } catch(SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static ResultSet getResult() {
        return result;
    }
    
    /* ===============================================================
     * Create static version of repeatedly used queries
     *
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
    
    // Query DB for customer info & render results to table
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
                System.out.println("Error (Query:GetAllCustomers): " + ex.getMessage());
        }
    }
    
    // 
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
    
    /* *******************************************************
        Customer
    
       ******************************************************* */
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
    
    
    /* *******************************************************
        Address
    
       ******************************************************* */
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
    
    public static int getAddressId(String addr, String addr2, String zip, String phone) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT addressId FROM address WHERE address=? AND "
                + "address2=? AND postalCode=? AND phone=?");) {
            st.setString(1, addr);
            st.setString(2, addr2);
            st.setString(3, zip);
            st.setString(4, phone);
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
    
    
    /* *******************************************************
        City
    
       ******************************************************* */
//    public static int getCityId(String name) throws SQLException {
//        try (PreparedStatement st =conn.prepareStatement(
//            "SELECT cityId FROM city WHERE city=?");) {
//            st.setString(1, name);
//            result = st.executeQuery();
//            
//            return result.next() ? result.getInt("cityId") : 0;
//        }
//    }
//    
    
    /* *******************************************************
        Country
    
       ******************************************************* */
//    public static int getCountryId(String name) throws SQLException {
//        try (PreparedStatement st = conn.prepareStatement(
//                "SELECT countryId FROM country WHERE country=?");) {
//            st.setString(1, name);
//            result = st.executeQuery();
//        
//            return result.next() ? result.getInt("countryId") : 0;
//        }
//    }
}
