/*
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** TODO:
 *  - exception handling
 *      - product must have at least 1 part
 *      - product price > combined parts price
 *      - product entry validation
 *  - confirmation box
 *      - all Delete & Cancel buttons
 */
public class MainScreenController implements Initializable {
    
    /* ---------- Search Query Segment ---------- */
    @FXML private TextField partSearchQuery;
    @FXML private TextField productSearchQuery;
    
    public void searchPartsButton() {
        boolean found = false;
        try {
            int temp = Integer.parseInt(partSearchQuery.getText());
            for(Part part: Inventory.getAllParts()) {
                if (part.getPartID() == temp) {
                    partsTable.getSelectionModel().select(part);
                    partsTable.requestFocus();
                    partSearchQuery.setPromptText("Enter Part ID");
                    found = true;
                    break;
                }
            }
            if (!found) {
                partSearchQuery.setPromptText("Part ID not found");
                partsTable.getSelectionModel().clearSelection();
            }
        } catch (NumberFormatException e) {
            partSearchQuery.setPromptText("Invalid Part ID");            
        }

        partSearchQuery.clear();
    }
    
    public void searchProductsButton() {
        boolean found = false;
        try {
            int temp = Integer.parseInt(productSearchQuery.getText());
            for(Product product: Inventory.getAllProducts()) {
                if (product.getProductID() == temp) {
                    productTable.getSelectionModel().select(product);
                    productTable.requestFocus();
                    productSearchQuery.setPromptText("Enter Product ID");
                    found = true;
                    break;
                }
            }
            if (!found) { 
            productSearchQuery.setPromptText("Product ID not found");
            productTable.getSelectionModel().clearSelection();
        }
        } catch (NumberFormatException e) {
            productSearchQuery.setPromptText("Invalid Product ID");
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
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(addPartScene);
        stage.setResizable(false);
        stage.show();
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

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(modifyPartScene);
            stage.setResizable(false);
            stage.show();
        } catch (NullPointerException e) {            
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(null);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part Selected");
            alert.setContentText("Please select a Part from the list");
            
            alert.showAndWait();
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
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(addProductsScene);
        stage.setResizable(false);
        stage.show();
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
        
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(modifyProductsScene);
            stage.setResizable(false);
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(null);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Product Selected");
            alert.setContentText("Please select a Product from the list");
            
            alert.showAndWait();
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
        
        partsTable.setItems( Inventory.getAllParts() );

        /* ---------- init products table display ---------- */
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems( Inventory.getAllProducts());
    }
}
