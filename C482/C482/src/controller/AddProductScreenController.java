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
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class AddProductScreenController implements Initializable {
    
    @FXML private TextField productNameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    
    @FXML private TableView<Part> availablePartsTable;
    @FXML private TableColumn<Part, String> availablePartIdColumn;
    @FXML private TableColumn<Part, String> availablePartNameColumn;
    @FXML private TableColumn<Part, String> availablePartInvColumn;
    @FXML private TableColumn<Part, String> availablePartPriceColumn;
    
    @FXML private TableView<Part> addedPartsTable;
    @FXML private TableColumn<Part, String> addedPartIdColumn;
    @FXML private TableColumn<Part, String> addedPartNameColumn;
    @FXML private TableColumn<Part, String> addedPartInvColumn;
    @FXML private TableColumn<Part, String> addedPartPriceColumn;
    
    @FXML private ObservableList<Part> productPartsList = FXCollections.observableArrayList();
    private Product product;
    private int max, inv, min;
    private double price;

    
    /* ---------- Search Query Segment ---------- */
    @FXML private TextField partSearchQuery;
    
    public void searchPartsButton(ActionEvent event) throws IOException {
        boolean found = false;
        int temp;
        for(Part part: Inventory.getAllParts()) {
            try {
                temp = Integer.parseInt(partSearchQuery.getText());
                if (part.getPartID() == temp) {
                    availablePartsTable.getSelectionModel().select(part);
                    partSearchQuery.setPromptText("Enter Part ID");
                    found = true;
                    break;
                }
            } catch (NumberFormatException e) {
                partSearchQuery.setPromptText("Invalid Part ID");
            }
        }
        if (!found) {
            partSearchQuery.setPromptText("Part ID not found");
            availablePartsTable.getSelectionModel().clearSelection();
        }
        partSearchQuery.clear();
    }
    
    /* ---------- Add/Delete Parts Segment ---------- */
    public void addButtonPressed() {
        productPartsList.add(availablePartsTable.getSelectionModel().getSelectedItem());
    }
    
    public void deleteButtonPressed() {
        productPartsList.remove(addedPartsTable.getSelectionModel().getSelectedItem());
    }
    
    
    /* ---------- Save Product & return to Main Screen ---------- */
    public void saveButtonPressed(ActionEvent event) throws IOException {
        double total = 0;
        for(Part part: productPartsList) {
            total += part.getPrice();
        }

        boolean isValidProduct = (max >= inv && inv >= min && price >= total && 
                productPartsList.size() > 0 && !productNameField.getText().isEmpty() &&
                !priceField.getText().isEmpty() && inv >= 0);
        
        if (isValidProduct) {
            try {
                product = new Product( new ArrayList<>(productPartsList), 
                        productNameField.getText(), price, inv, min, max);
                
                Inventory.addProduct(product);
                returnToMainScreen(event);
            } catch (NullPointerException e) {
//                exceptionMessage.setText("Invalid Product Entry");
            }
        } else {
//            exceptionMessage.setText("Invalid Product Entry");
        }
    }

    /* ---------- Cancel page & return to Main Screen ---------- */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        returnToMainScreen(event);
    }
    
    
    /* ---------- Return to Main Screen ---------- */
    public void returnToMainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    

    @Override public void initialize(URL url, ResourceBundle rb) {
        availablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        availablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        availablePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        availablePartsTable.setItems( Inventory.getAllParts() );
        
        addedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("PartID"));
        addedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addedPartsTable.setItems( productPartsList );
        
        /* ---------- TextField Listeners ---------- *
         * === copied to <xyz>PartScreenController  ===
         * ===      same issues here as there       ===
         * Waaaaay too much going on here
         * Replace with a listener on the GridPane?
         * Implement an external class for input validation instead?
         */
        invField.textProperty().addListener((obs, prev, next) -> {
            try {
                if (!invField.getText().matches("[0-9]*")) {
                    invField.setText(prev);
                } else {
                    inv = Integer.parseInt(next);
                }
            } catch (NumberFormatException e) {
//                exceptionMessage.setText("Invalid Inventory Field Input");
            }
        });
        
        minField.textProperty().addListener((obs, prev, next) -> {
            try {
                if (!minField.getText().matches("[0-9]*")) {
                    minField.setText(prev);
                } else {
                    min = Integer.parseInt(next);
                }
            } catch (NumberFormatException e) {
//                exceptionMessage.setText("Invalid Min Field Input");
            }
        });
        
        maxField.textProperty().addListener((obs, prev, next) -> {
            try {
                if (!maxField.getText().matches("[0-9]*")) {
                    maxField.setText(prev);
                } else {
                    max = Integer.parseInt(next);
                }
            } catch (NumberFormatException e) {
//                exceptionMessage.setText("Invalid Max Field Input");
            }
        });
        
        // NOT perfect regex, but it will do for now
        priceField.textProperty().addListener((obs, prev, next) -> {
            try {
                if (!priceField.getText().matches("[.0-9]*")) {
                    priceField.setText(prev);
                } else {
                    price = Double.parseDouble(next);
                }
            } catch (NumberFormatException e) {
//                exceptionMessage.setText("Invalid Price Field Input");
            }
        });
    }    
}
