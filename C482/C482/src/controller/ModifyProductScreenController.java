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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModifyProductScreenController implements Initializable {

    @FXML private TextField productNameField;
    @FXML private TextField idField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private Label exceptionMessage;
    
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
    Product product = new Product();
    
    
    public void loadProduct(Product product) {
        idField.setText(String.valueOf(product.getProductID()));
        productNameField.setText(product.getName());
        invField.setText(String.valueOf(product.getInStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));
        addedPartsTable.setItems( FXCollections.observableArrayList(product.getAllAssociatedParts()) );
    }
    
    /* ---------- Search Query Segment ---------- */
    @FXML private TextField partSearchQuery;
    
    public void searchPartsButton(ActionEvent event) throws IOException {
        boolean found = false;
        exceptionMessage.setVisible(false);
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
                exceptionMessage.setText("Please enter a valid Part ID");
                exceptionMessage.setVisible(true);
            }
        }
        if (!found) {
            partSearchQuery.setPromptText("Part ID not found");
            availablePartsTable.getSelectionModel().clearSelection();
        }
        partSearchQuery.clear();
    }
    
    public void addButtonPressed() {
        productPartsList.add(availablePartsTable.getSelectionModel().getSelectedItem());
    }
    
    public void deleteButtonPressed() {
        productPartsList.remove(addedPartsTable.getSelectionModel().getSelectedItem());
    }
    
    public void saveButtonPressed(ActionEvent event) throws IOException {
        //TODO: validate input before executing
        productPartsList.forEach((item) -> { product.addAssociatedPart(item); });
        product.setProductID(Integer.parseInt(idField.getText()));
        product.setName(productNameField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        product.setInStock(Integer.parseInt(invField.getText()));
        product.setMin(Integer.parseInt(minField.getText()));
        product.setMax(Integer.parseInt(maxField.getText()));
        
        Inventory.updateProduct(Integer.parseInt(idField.getText()) -1, product);
        returnToMainScreen(event);
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
    }    
    
}
