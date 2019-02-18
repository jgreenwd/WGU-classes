/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package c482;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** TODO:
 *  - add parts
 *  - modify parts
 *  - search parts
 *  - delete parts
 *  - populate products table
 *  - add products
 *  - modify products
 *  - search products
 *  - delete products
 */
public class MainScreenController implements Initializable {
    
    /* ---------- Parts Management ---------- */
    @FXML private Button searchParts;
    @FXML private Button addParts;
    @FXML private Button modifyParts;
    @FXML private Button deleteParts;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, String> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partInvColumn;
    @FXML private TableColumn<Part, String> partPriceColumn;
    
    
    public void searchPartsButton(ActionEvent event) throws IOException {
        System.out.println("Parts: Search button pressed");
    }
    
    public void addPartsButton(ActionEvent event) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("PartScreen.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addPartsScene);
        window.show();
    }
    
    public void modifyPartsButton(ActionEvent event) throws IOException {
        Parent modifyPartsParent = FXMLLoader.load(getClass().getResource("PartScreen.fxml"));
        Scene modifyPartsScene = new Scene(modifyPartsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(modifyPartsScene);
        window.show();
    }
    
    public void deletePartsButton(ActionEvent event) throws IOException {
        System.out.println("Parts: Delete button pressed");
        Inventory.allParts.forEach((item) -> {
            System.out.println(item.getName());
        }); 

    }
    
    
    /* ---------- Products Management ---------- */
    @FXML private Button searchProducts;
    @FXML private Button addProducts;
    @FXML private Button modifyProducts;
    @FXML private Button deleteProducts;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, String> productInvColumn;
    @FXML private TableColumn<Product, String> productPriceColumn;
    
    
    public void searchProductsButton(ActionEvent event) throws IOException {
        System.out.println("Products: Search button pressed");
    }
    
    public void addProductsButton(ActionEvent event) throws IOException {
        Parent addProductsParent = FXMLLoader.load(getClass().getResource("ProductScreen.fxml"));
        Scene addProductsScene = new Scene(addProductsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addProductsScene);
        window.show();
    }
    
    public void modifyProductsButton(ActionEvent event) throws IOException {
        Parent modifyProductsParent = FXMLLoader.load(getClass().getResource("ProductScreen.fxml"));
        Scene modifyProductsScene = new Scene(modifyProductsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(modifyProductsScene);
        window.show();
    }
    
    public void deleteProductsButton(ActionEvent event) throws IOException {
        System.out.println("Products: Delete button pressed");
        
    }
    
    
    /* ---------- Exit ---------- */
    @FXML private Button exitButton;
    
    public void exitButton() {
        System.exit(0);
    }

    
    /* ---------- test data ---------- */
    // add products
    public ObservableList<Part> updatePartsDisplay() {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InHouse(001,"Widget",1.99, 0,0,999));
        parts.add(new InHouse(002,"Wodget",2.95, 0,0,999));
        parts.add(new InHouse(003,"Wudget",3.90, 0,0,999));
        parts.add(new Outsourced(004,"Thingy",2.99, 0,0,999));
        parts.add(new Outsourced(005,"Thingamajig", 1.96, 0,0,999));
        parts.add(new Outsourced(006,"Thingamabob", 1.92, 0,0,999));
        
        return parts;
    }
    

    @Override public void initialize(URL url, ResourceBundle rb) {
        /* ---------- init parts table display ---------- */
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTable.setItems( updatePartsDisplay() );
        
        /* ---------- init products table display ---------- */
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

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
