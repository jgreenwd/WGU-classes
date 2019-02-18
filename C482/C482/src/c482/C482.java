/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package c482;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class C482 extends Application {
    
    @Override public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void loadDB() {
        /* ---------- test data ---------- */
        // add products
        Inventory inventory = new Inventory();
        
        Part part1 = new InHouse(001,"Widget",1.99, 0,0,999);
        Part part2 = new InHouse(002,"Wodget",2.95, 0,0,999);
        Part part3 = new InHouse(003,"Wudget",3.90, 0,0,999);
        Part part4 = new Outsourced(004,"Thingy",2.99, 0,0,999);
        Part part5 = new Outsourced(005,"Thingamajig", 1.96, 0,0,999);
        Part part6 = new Outsourced(006,"Thingamabob", 1.92, 0,0,999);
        
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);
        inventory.addPart(part4);
        inventory.addPart(part5);
        inventory.addPart(part6);
        
    }
    
    public static void main(String[] args) {
        loadDB();
        
        launch(args);
    }
}
