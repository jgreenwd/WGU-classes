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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartScreenController implements Initializable {

    private final ToggleGroup partSource = new ToggleGroup();
    @FXML private RadioButton inHouseRadio; 
    @FXML private RadioButton outsourcedRadio;
    @FXML private TextField partNameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private Label sourceTitleLabel;
    @FXML private TextField sourceNameField;
    
    
    public void saveButtonPressed(ActionEvent event) throws IOException {
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());
        int machID = Integer.parseInt(sourceNameField.getText());
        double price = Double.parseDouble((priceField.getText()).replace("$", ""));
        String partName = partNameField.getText();
        String companyName = sourceNameField.getText();
        
        // save as InHouse part
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            InHouse partToAdd = new InHouse();
            partToAdd.setName(partName);
            partToAdd.setInStock(Integer.parseInt(invField.getText()));
            partToAdd.setMin(min);
            partToAdd.setMax(max);
            partToAdd.setPrice(price);
            partToAdd.setMachineID(machID);
            
            Inventory.addPart(partToAdd);
        }
        
        // save Outsourced part
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            Outsourced partToAdd = new Outsourced();
            partToAdd.setName(partName);
            partToAdd.setInStock(Integer.parseInt(invField.getText()));
            partToAdd.setMin(min);
            partToAdd.setMax(max);
            partToAdd.setPrice(price);
            partToAdd.setCompanyName(sourceNameField.getText());
            
            Inventory.addPart(partToAdd);
        }
        
        // return to Main Screen after save
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
    
    
    /* ---------- Initialize Part Screen ---------- */
    @Override public void initialize(URL url, ResourceBundle rb) {
        
        /* ---------- RadioButtons ----------
         * 1. create ToggleGroup for RadioButtons
         * 2. add listener to each RadioButton
         * 3. set InHouse as default RadioButton value
         * -------------------------------- */
        this.inHouseRadio.setToggleGroup(partSource);
        this.outsourcedRadio.setToggleGroup(partSource);
        
        inHouseRadio.selectedProperty().addListener((obs, then, now) -> {
            if (now) {
                sourceTitleLabel.setText("Machine ID");
                sourceNameField.setPromptText("Machine ID");
            }
        });
        outsourcedRadio.selectedProperty().addListener((obs, then, now) -> {
            if (now) {
                sourceTitleLabel.setText("Company Name");
                sourceNameField.setPromptText("Company Name");
            }
        });
        
        partSource.selectToggle(inHouseRadio);
        
        
        /* ---------- TextField Listeners ---------- */
        invField.textProperty().addListener((obs, prev, next) -> {
            invField.setText(InputControl.IntCtrl(next));
        });
        
        minField.textProperty().addListener((obs, prev, next) -> {
            minField.setText(InputControl.IntCtrl(next));
        });
        
        maxField.textProperty().addListener((obs, prev, next) -> {
            maxField.setText(InputControl.IntCtrl(next));
        });
        
        priceField.textProperty().addListener((obs) -> {
            priceField = InputControl.DblCtrl(priceField);
        });
    }
}
