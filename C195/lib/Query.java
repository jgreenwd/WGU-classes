/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package lib;

import static lib.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.User;


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
     * Query DB for customer info & render results to table
     * =============================================================== */
    public static void getAllCustomers() {
        makeQuery(
            "SELECT country.countryId, country, city.cityId, city, "
                + "address.addressId, address, address2, postalCode, phone, "
                + "customer.customerId, customerName FROM country "
                + "JOIN city ON country.countryId = city.countryId "
                + "JOIN address ON city.cityId = address.cityId "
                + "JOIN customer ON address.addressId=customer.addressId "
                + "ORDER BY customer.customerId;"
        );
    }
    
    public static User getUser(String name) throws SQLException {
        makeQuery( "SELECT userId, userName, password, active FROM user WHERE userName=\""+ name +"\";");
        getResult().first();
        
        int userId = getResult().getInt("userID");
        String user = getResult().getString("userName");
        String pwd = getResult().getString("password");
        int active = getResult().getInt("active");
        
        return new User(userId, user, pwd, active);
    }
}
