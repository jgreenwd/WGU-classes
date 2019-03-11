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
    /* ---------- Display Exceptions on this Label ---------- */
    @FXML private Label exceptionMessage;

    
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
    boolean validPart = false;    
    
    public void saveButtonPressed(ActionEvent event) throws IOException {
        exceptionMessage.setVisible(false);
        
        try {
            int inv = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            double price = Double.parseDouble((priceField.getText()).replace("$", ""));
            String partName = partNameField.getText();
            String source = sourceNameField.getText();
            
            if (min > max) { validPart = false; }
            
            // save as InHouse part
            if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
                InHouse partToAdd = new InHouse();
                partToAdd.setName(partName);
                partToAdd.setInStock(inv);
                partToAdd.setMin(min);
                partToAdd.setMax(max);
                partToAdd.setPrice(price);
                partToAdd.setMachineID(Integer.parseInt(source));
            
                Inventory.addPart(partToAdd);
                validPart = true;
            }
        
            // save Outsourced part
            if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
                Outsourced partToAdd = new Outsourced();
                partToAdd.setName(partName);
                partToAdd.setInStock(inv);
                partToAdd.setMin(min);
                partToAdd.setMax(max);
                partToAdd.setPrice(price);
                partToAdd.setCompanyName(source);
            
                Inventory.addPart(partToAdd);
                validPart = true;
            }
        } catch (NumberFormatException e) {
            exceptionMessage.setText("Invalid Part Input");
            exceptionMessage.setVisible(true);
            validPart = false;
        }
        
        // return to Main Screen after save
        if (validPart) { returnToMainScreen(event); }
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
         * 2. add listener to RadioButton ToggleGroup
         * 3. set InHouse as default RadioButton value
         * -------------------------------- */
        this.inHouseRadio.setToggleGroup(partSource);
        this.outsourcedRadio.setToggleGroup(partSource);
        
        partSource.selectedToggleProperty().addListener((obs, prev, next) -> {
            if (next == inHouseRadio) {
                sourceTitleLabel.setText("Machine ID");
                sourceNameField.clear();
                sourceNameField.setPromptText("Machine ID");
            } else {
                sourceTitleLabel.setText("Company Name");
                sourceNameField.clear();
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
