/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package lib;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DBConnection {
    private static final String DB_NAME = "U05sep";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + DB_NAME;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    static Connection conn = null;
    
    /* ===============================================================
     * As per the text, use DataSource instead of DriverManager
     *
     * usr = username
     * pwd = password
     * =============================================================== */
    public static void makeConnection(String usr, String pwd) throws ClassNotFoundException, SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        
        dataSource.setURL(DB_URL);
        
        conn = dataSource.getConnection(usr, pwd);
        System.out.println("Opening Connection: " + DB_NAME);
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException {
        conn.close();
        System.out.println("Closing Connection: " + DB_NAME);
    } 
}
