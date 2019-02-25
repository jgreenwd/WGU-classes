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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class AddProductScreenController implements Initializable {
    
    @FXML private TextField productNameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    
    ArrayList<Part> parts = new ArrayList<>();
    
    
    public void saveButtonPressed(ActionEvent event) throws IOException {
        Product product = new Product();
        
        parts.forEach((item) -> product.addAssociatedPart(item));
        product.setName(productNameField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        product.setInStock(Integer.parseInt(invField.getText()));
        product.setMin(Integer.parseInt(minField.getText()));
        product.setMax(Integer.parseInt(maxField.getText()));
        
        Inventory.addProduct(product);
        returnToMainScreen(event);
    }

    /* ---------- Cancel page & return to Main Screen ---------- */
    /* redundant, but added for consistency between controllers  */
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
//        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("PartID"));
//        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
//        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//        
//        partsTable.setItems( updatePartsDisplay() );
    }    
    
}
