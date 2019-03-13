/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

import model.*;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class C482 extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    @Override public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("WGU Software I - Inventory Mgmt System - Jeremy Greenwood #000917613");
        
        initRootLayout();
        LoadMainScreen();
    }
    
    public void initRootLayout() {
        // set the stage & scene
        try {
            rootLayout = (BorderPane) FXMLLoader.load(getClass().getClassLoader()
                    .getResource("view/RootLayout.fxml"));
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadMainScreen() {
        // load the application
        try {
            AnchorPane mainScreen = FXMLLoader.load(getClass().getClassLoader()
                    .getResource("view/MainScreen.fxml"));
            rootLayout.setCenter(mainScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    

    /* ---------- Development Test Data ---------- */
    public static void loadDB() {
        InHouse part1 = new InHouse(001,"Widget",1.99, 0,0,999,1);
        InHouse part2 = new InHouse(002,"Wedget",2.95, 0,0,999,2);
        Outsourced part3 = new Outsourced(003,"Wodget",2.99, 0,0,999,"ACME");
        Outsourced part4 = new Outsourced(004,"Wudget", 1.96, 0,0,999,"Ajax");
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        
        // add products
        ArrayList<Part> partset1 = new ArrayList<>();
        partset1.add(part1);
        partset1.add(part2);
        partset1.add(part3);
        partset1.add(part4);
        
        ArrayList<Part> partset2 = new ArrayList<>();
        partset2.add(part2);
        partset2.add(part4);
        
        Product product1 = new Product(partset1, 001, "Doohickey", 14.95, 0,0,999);
        Product product2 = new Product(partset2, 002, "Doodad", 7.81, 0,0,999);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
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
