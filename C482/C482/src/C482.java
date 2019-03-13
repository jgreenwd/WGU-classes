/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    

    /* ---------- Fake Development Data ---------- */
    public static void loadDB() {
        // add parts
        InHouse part1 = new InHouse(1,"Widget",1.99, 100,10,500,123123);
        InHouse part2 = new InHouse(2,"Wadget",2.95, 95,50,100,124124);
        InHouse part3 = new InHouse(3,"Wedget",0.57, 250,100,1000,120125);
        Outsourced part4 = new Outsourced(4,"Wodget", 2.99, 107,25,250,"ACME");
        Outsourced part5 = new Outsourced(5,"Wudget", 1.96, 130,25,250,"Ajax");
        Outsourced part6 = new Outsourced(6,"Wydget", 4.97, 20,50,10,"StuffCo");
        InHouse part7 = new InHouse(7,"Gadget",1.90, 100,10,500,120125);
        InHouse part8 = new InHouse(8,"Gizmo",1.93, 87,25,250,337345);
        InHouse part9 = new InHouse(9,"Gubbins",1.97, 43,15,100,212657);
        Outsourced part10 = new Outsourced(10,"Thingy", 2.99, 19,50,10,"ThePlace");
        Outsourced part11 = new Outsourced(11,"Thingamajig", 1.96, 12,50,10,"ACME");
        Outsourced part12 = new Outsourced(12,"Thingamabob", 1.92, 45,50,10,"Bob's Things");
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        Inventory.addPart(part9);
        Inventory.addPart(part10);
        Inventory.addPart(part11);
        Inventory.addPart(part12);
        
        // add products
        ArrayList<Part> partset1 = new ArrayList<>( Arrays.asList( new Part[]{part1, part2, part3} ) );
        ArrayList<Part> partset2 = new ArrayList<>( Arrays.asList( new Part[]{part2, part1, part4} ) );
        ArrayList<Part> partset3 = new ArrayList<>( Arrays.asList( new Part[]{part3, part12, part5} ) );
        ArrayList<Part> partset4 = new ArrayList<>( Arrays.asList( new Part[]{part4, part11, part6} ) );
        ArrayList<Part> partset5 = new ArrayList<>( Arrays.asList( new Part[]{part5, part10, part7} ) );
        ArrayList<Part> partset6 = new ArrayList<>( Arrays.asList( new Part[]{part6, part9, part8} ) );
        ArrayList<Part> partset7 = new ArrayList<>( Arrays.asList( new Part[]{part7, part8, part9} ) );
        
        Product product1 = new Product(partset1, 1, "Doohickey", 14.95, 10,2,20);
        Product product2 = new Product(partset2, 2, "Doodad", 19.85, 25,10,100);
        Product product3 = new Product(partset3, 3, "Whatsit", 11.15, 10,2,20);
        Product product4 = new Product(partset4, 4, "Whatchamacallit", 24.75, 25,10,100);
        Product product5 = new Product(partset5, 5, "Appliance", 17.25, 25,10,100);
        Product product6 = new Product(partset6, 6, "Contraption", 22.15, 25,10,100);
        Product product7 = new Product(partset7, 7, "Device", 14.50, 25,10,100);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        Inventory.addProduct(product5);
        Inventory.addProduct(product6);
        Inventory.addProduct(product7);
    }
    
    public static void main(String[] args) {
        loadDB();
        launch(args);
    }
}
