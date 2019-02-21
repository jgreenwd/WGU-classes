    /**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package controller;

import model.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** TODO:
 *  - modify parts
 *  - search parts
 *  - delete parts
 *  - populate products table
 *  - add products
 *  - modify products
 *  - search products
 *  - delete products
 *  - toggle add/modify parts Label
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
    
    private String inhouse = "InHouse", outsourced = "Outsourced";
    
    public void searchPartsButton(ActionEvent event) throws IOException {
        Inventory.allParts.forEach((item) -> {
            System.out.println("Part name: " + item.getName());
        });
    }
    
    public void addPartsButton(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    
    public void modifyPartsButton(ActionEvent event) throws IOException {
        // identify selected part
        Part part = partsTable.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartScreen.fxml"));
        Parent modifyPartParent = loader.load();
        Scene modifyPartScene = new Scene(modifyPartParent);
        
        // identify part type and send to loadPart()
        ModifyPartScreenController controller = loader.getController();
        if (part.getClass().getSimpleName().equals(inhouse)) { controller.loadPart((InHouse)part); }
        if (part.getClass().getSimpleName().equals(outsourced)) { controller.loadPart((Outsourced)part); }
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(modifyPartScene);
        window.show();
    }
    
    public void deletePartsButton(ActionEvent event) throws IOException {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(part);
        partsTable.setItems( updatePartsDisplay() );
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
        Parent addProductsParent = FXMLLoader.load(getClass().getResource("/view/AddProductScreen.fxml"));
        Scene addProductsScene = new Scene(addProductsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addProductsScene);
        window.show();
    }
    
    public void modifyProductsButton(ActionEvent event) throws IOException {
        Parent modifyProductsParent = FXMLLoader.load(getClass().getResource("/view/ModifyProductScreen.fxml"));
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
    
    public ObservableList<Part> updatePartsDisplay() {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        Inventory.allParts.forEach((item) -> { parts.add(item); });
        
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

}
