/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package lib;

import static lib.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.Statement;


public class Query {

    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    
    public static void makeQuery(String q) {
        query = q.toLowerCase();
        
        try {
            stmt = conn.createStatement();
            
            // determin query execution
            if (query.startsWith("select")) {
                result = stmt.executeQuery(query);
            } 
            
            if (query.startsWith("delete") || query.startsWith("insert") || query.startsWith("update")) {
                stmt.executeUpdate(query);
            }
        } catch(Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static ResultSet getResult() {
        return result;
    }
}
