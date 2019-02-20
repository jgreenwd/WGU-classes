/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

import model.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class C482 extends Application {
    
    @Override public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("WGU Software I - Inventory Mgmt System - Jeremy Greenwood #000917613");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadDB() {
        /* ---------- test data ---------- */
        // add products
        Inventory inventory = new Inventory();
        
        InHouse part1 = new InHouse(001,"Widget",1.99, 0,0,999);
        part1.setMachineID(1);
        InHouse part2 = new InHouse(002,"Wodget",2.95, 0,0,999);
        part2.setMachineID(2);
        InHouse part3 = new InHouse(003,"Wudget",3.90, 0,0,999);
        part3.setMachineID(1);
        Outsourced part4 = new Outsourced(004,"Thingy",2.99, 0,0,999);
        part4.setCompanyName("ACME");
        Outsourced part5 = new Outsourced(005,"Thingamajig", 1.96, 0,0,999);
        part5.setCompanyName("Ajax");
        Outsourced part6 = new Outsourced(006,"Thingamabob", 1.92, 0,0,999);
        part6.setCompanyName("ACME");
        
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
    
    /* ---------- synonyms for widget: ----------
        001,"Widget",1.99, 0,0,999
        002,"Wodget",2.95, 0,0,999
        003,"Wudget",3.90, 0,0,999
    
        004,"Thingy",2.99, 0,0,999
        005,"Thingamajig", 1.96, 0,0,999
        006,"Thingamabob", 1.92, 0,0,999
    
        007,"Doohickey",0.94, 0,0,999
        008,"Doodad", 1.91, 0,0,999
        009,"Diddlydilly", 2.97, 0,0,999
    
        010,"Whatsit",0.10, 0,0,999
        011,"Whatchamacallit", 1.95, 0,0,999
        012,"Whatsis", 1.98, 0,0,999
    
        013,"Gadget", 1.90, 0,0,999
        014,"Gizmo", 1.93, 0,0,999
        015,"Gubbins", 1.97, 0,0,999
        

        012, "stuff", 1.94, 0,0,999
        
        017, "appliance", 1.89, 0,0,999
        018, "contraption", 1.88, 0,0,999
        020, "device", 1.86, 0,0,999
    */
}
