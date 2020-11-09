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
import model.*;


/* FUTURE TODO:
    Add ability to insert City/Country values through application, instead of database
    Explore use of Optionals to eliminate null checks.
    Refactor reports to be more functional
    Explore use of a TreeMap with Observable types for LocalDB instead of ObservableList?
*/

public class C195 extends Application {
    // global variables for stage reuse
    public static Stage primaryStage = new Stage();   
    public static Stage secondaryStage = new Stage();
    public static ResourceBundle rb;
    public static User user;
    
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
        launch(args);
    }

    public static Stage getPrimaryStage() { return primaryStage; }
    public static Stage getSecondaryStage() { return secondaryStage; }
    public static ResourceBundle getResourceBundle() { return rb; }
}
