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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** TODO:
 *  - exception handling
 *      - inventory bounds checking on entry
 *      - product must have at least 1 part
 *      - product price > combined parts price
 *      - product entry validation
 *      - part entry validation
 *  - confirmation box
 *      - all Delete & Cancel buttons
 * 
 */
public class MainScreenController implements Initializable {
    /* ---------- Display Exceptions on this Label ---------- */
    @FXML private Label exceptionMessage;
    
    
    /* ---------- Search Query Segment ---------- */
    @FXML private TextField partSearchQuery;
    @FXML private TextField productSearchQuery;
    
    public void searchPartsButton() {
        boolean found = false;
        exceptionMessage.setVisible(false);
        int temp;
        for(Part part: Inventory.getAllParts()) {
            try {
                temp = Integer.parseInt(partSearchQuery.getText());
                if (part.getPartID() == temp) {
                    partsTable.getSelectionModel().select(part);
                    partSearchQuery.setPromptText("Enter Part ID");
                    found = true;
                    break;
                }
            } catch (NumberFormatException e) {
                exceptionMessage.setText("Please enter a valid Part ID");
                exceptionMessage.setVisible(true);
            }
        }
        if (!found) {
            partSearchQuery.setPromptText("Part ID not found");
            partsTable.getSelectionModel().clearSelection();
        }
        partSearchQuery.clear();
    }
    
    public void searchProductsButton() {
        boolean found = false;
        exceptionMessage.setVisible(false);
        int temp;
        for(Product product: Inventory.getAllProducts()) {
            try {
                temp = Integer.parseInt(productSearchQuery.getText());
                if (product.getProductID() == Integer.parseInt(productSearchQuery.getText())) {
                    productTable.getSelectionModel().select(product);
                    productSearchQuery.setPromptText("Enter Product ID");
                    found = true;
                    break;
                }
            } catch (NumberFormatException e) {
                exceptionMessage.setText("Please enter a valid Product ID");
                exceptionMessage.setVisible(true);
            }
        }
        if (!found) { 
            productSearchQuery.setPromptText("Product ID not found");
            productTable.getSelectionModel().clearSelection();
        }
        productSearchQuery.clear();
    }
    
    
    /* ---------- Parts Management Segment ---------- */
    @FXML private TableView<Part> partsTable = new TableView<>();
    @FXML private TableColumn<Part, String> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partInvColumn;
    @FXML private TableColumn<Part, String> partPriceColumn;

    public void addPartsButton(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    
    public void modifyPartsButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartScreen.fxml"));
            Parent modifyPartParent = loader.load();
            Scene modifyPartScene = new Scene(modifyPartParent);
            ModifyPartScreenController controller = loader.getController();
        
            // identify part type and send to loadPart((source-type) part)
            Part part = partsTable.getSelectionModel().getSelectedItem();
            if (part instanceof InHouse) { controller.loadPart((InHouse)part); }
            else { controller.loadPart((Outsourced)part); }

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyPartScene);
            window.show();
        } catch (NullPointerException e) {
            exceptionMessage.setText("Please select a Part from the list");
            exceptionMessage.setVisible(true);
        }
    }
    
    public void deletePartsButton(ActionEvent event) throws IOException {
        Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
    }
    
    
    /* ---------- Products Management Segment ---------- */
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, String> productInvColumn;
    @FXML private TableColumn<Product, String> productPriceColumn;
    
    public void addProductsButton(ActionEvent event) throws IOException {
        Parent addProductsParent = FXMLLoader.load(getClass().getResource("/view/AddProductScreen.fxml"));
        Scene addProductsScene = new Scene(addProductsParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductsScene);
        window.show();
    }
    
    public void modifyProductsButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductScreen.fxml"));
            Parent modifyProductsParent = loader.load();
            Scene modifyProductsScene = new Scene(modifyProductsParent);
            ModifyProductScreenController controller = loader.getController();
        
            // send product info from here
            Product product = productTable.getSelectionModel().getSelectedItem();
            controller.loadProduct(product);
        
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyProductsScene);
            window.show();
        } catch (NullPointerException e) {
            exceptionMessage.setText("Please select a Product from the list");
            exceptionMessage.setVisible(true);
        }
    }
    
    public void deleteProductsButton(ActionEvent event) throws IOException {
        Inventory.removeProduct(productTable.getSelectionModel().getSelectedItem());
    }
    
    
    /* ---------- Exit ---------- */
    public void exitButton() {
        System.exit(0);
    }
    
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        
        /* ---------- init parts table display ---------- */
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTable.setItems(Inventory.getAllParts());

        /* ---------- init products table display ---------- */
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems( Inventory.getAllProducts());
    }
}
