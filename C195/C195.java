/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package c195;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class C195 extends Application {
    /* ===============================================================
     * (4025.01.08) - A: Internationalize Login form
     *
     * Uncomment the first line to change Locale from Default ("en"/"US")
     * to Spanish ("es"/"MX").
     * =============================================================== */
    @Override public void start(Stage primaryStage) throws IOException {
//        Locale.setDefault(new Locale("es", "MX"));
        ResourceBundle rb = ResourceBundle.getBundle("lib/Login");
        
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

    public static void main(String[] args) {
        launch(args);
    }
    
}
