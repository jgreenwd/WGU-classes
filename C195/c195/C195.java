/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package c195;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lib.DBConnection;
import model.User;

public class C195 extends Application {
    // global variables for stage reuse
    public static Stage primaryStage = new Stage();    
    public static ResourceBundle rb;
    public static User user;
    public enum ProcessState { ADD, EDIT, DELETE };
    
    /* ===============================================================
     * (4025.01.08) - A: Internationalize Login form
     *
     * Uncomment the first line to change Locale from Default ("en"/"US")
     * to Spanish ("es"/"MX").
     * =============================================================== */
    @Override public void start(Stage stage) throws IOException {
//        Locale.setDefault(new Locale("es", "MX"));
        primaryStage = stage;
        rb = ResourceBundle.getBundle("lib/Login");
        
        // display login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        loader.setResources(rb);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle(rb.getString("Title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /* **************************************************************
            Hard coding the login details is BAD PRACTICE, but necessary to
            avoid Querying the DB on failed login attempts and meet rubric 
            conditions: "You must use “test” as the username and password to
            log-in."
         * ************************************************************** */
        DBConnection.makeConnection("U05sep", "53688595689");
        launch(args);
        
    }

    public static Stage getPrimaryStage() { return primaryStage; }
    public static ResourceBundle getResourceBundle() { return rb; }
}
