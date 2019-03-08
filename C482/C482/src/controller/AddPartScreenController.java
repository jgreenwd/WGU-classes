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
    
    
    public void saveButtonPressed(ActionEvent event) throws IOException {
        boolean validPart = true;
        boolean saved = false;
        
        try {
            int inv = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            double price = Double.parseDouble((priceField.getText()).replace("$", ""));
            String partName = partNameField.getText();
            String source = sourceNameField.getText();
            
            if (invField.getText() == null || minField.getText() == null ||
                maxField.getText() == null || priceField.getText() == null ||
                partName.isEmpty() || source.isEmpty()) {
                validPart = false;
                exceptionMessage.setText("All fields must have a value");
                exceptionMessage.setVisible(true);
            } else if (max < min) {
                validPart = false;
                exceptionMessage.setText("Min value must not exceed Max value");
                exceptionMessage.setVisible(true);
            } else if (inv > max || min > inv) {
                validPart = false;
                exceptionMessage.setText("Inventory Level must be between Min and Max values");
                exceptionMessage.setVisible(true);
            } else {
                validPart = true;
                exceptionMessage.setVisible(false);
            }
            
            // save as InHouse part
            if (this.partSource.getSelectedToggle().equals(this.inHouseRadio) && validPart) {
                InHouse partToAdd = new InHouse();
                partToAdd.setName(partName);
                partToAdd.setInStock(inv);
                partToAdd.setMin(min);
                partToAdd.setMax(max);
                partToAdd.setPrice(price);
                partToAdd.setMachineID(Integer.parseInt(source));
            
                Inventory.addPart(partToAdd);
                saved = true;
            }
        
            // save Outsourced part
            if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio) && validPart) {
                Outsourced partToAdd = new Outsourced();
                partToAdd.setName(partName);
                partToAdd.setInStock(inv);
                partToAdd.setMin(min);
                partToAdd.setMax(max);
                partToAdd.setPrice(price);
                partToAdd.setCompanyName(source);
            
                Inventory.addPart(partToAdd);
                saved = true;
            }
        } catch (NumberFormatException e) {
            
        }
        
        // return to Main Screen after save
        if (saved) { returnToMainScreen(event); }
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
                sourceNameField.clear();
                sourceNameField.setPromptText("Machine ID");
            }
        });
        outsourcedRadio.selectedProperty().addListener((obs, then, now) -> {
            if (now) {
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
        
        sourceNameField.textProperty().addListener((obs, prev, next) -> {
           if (partSource.getSelectedToggle() == inHouseRadio) {
               sourceNameField.setText(InputControl.IntCtrl(next));
           } 
        });
        
        priceField.textProperty().addListener((obs) -> {
            priceField = InputControl.DblCtrl(priceField);
        });
    }
}
