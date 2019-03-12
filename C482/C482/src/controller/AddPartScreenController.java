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
import javafx.scene.layout.GridPane;
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
    @FXML private Label exceptionMessage;
    private int inv, min, max, MachineID;
    private boolean isInHouse = true;
    private double price;
    
    public void saveButtonPressed(ActionEvent event) throws IOException {
        exceptionMessage.setVisible(false);
        Part partToAdd;
        
        // Validate Max/Inv/Min Input Values
        if (max >= inv && inv >= min && MachineID != 0) {
            if (isInHouse) {
                partToAdd = new InHouse(partNameField.getText(), price, inv, min, max, MachineID);
            } else {
                partToAdd = new Outsourced(partNameField.getText(), price, inv, min, max, sourceNameField.getText());
            }
            // return to Main Screen after save
            Inventory.addPart(partToAdd);
            returnToMainScreen(event);
        } else {
            exceptionMessage.setText("Invalid Part Input");
            exceptionMessage.setVisible(true);
        }
    }

    
    /* ---------- Cancel Modification & Exit ---------- */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        //TODO process validation
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
         * 3. based on toggle, validate sourceNameField
         * 4. set InHouse as default RadioButton value
         * -------------------------------- */
        this.inHouseRadio.setToggleGroup(partSource);
        this.outsourcedRadio.setToggleGroup(partSource);
        
        partSource.selectedToggleProperty().addListener((obs, prev, next) -> {
            isInHouse = (next == inHouseRadio);
            if (isInHouse) {
                sourceTitleLabel.setText("Machine ID");
                sourceNameField.setPromptText("Machine ID");
            } else {
                sourceTitleLabel.setText("Company Name");
                sourceNameField.setPromptText("Company Name");
            }
        });
        
        sourceNameField.textProperty().addListener((obs, prev, next) -> {
            if(isInHouse) {
                try {
                    if (!sourceNameField.getText().matches("[0-9]*")) {
                        sourceNameField.setText(prev);
                    } else {
                        MachineID = Integer.parseInt(next);
                        exceptionMessage.setVisible(false);
                    }
                } catch (NumberFormatException e) {
                    exceptionMessage.setText("Invalid Machine ID");
                    exceptionMessage.setVisible(true);
                }
            } else {
                MachineID = 0;
            }
        });
        
        partSource.selectToggle(inHouseRadio);
        
        
        /* ---------- TextField Listeners ---------- */
        
        invField.textProperty().addListener((obs, prev, next) -> {
            try {
                if (!invField.getText().matches("[0-9]*")) {
                    invField.setText(prev);
                } else {
                    inv = Integer.parseInt(next);
                    exceptionMessage.setVisible(false);
                }
            } catch (NumberFormatException e) {
                exceptionMessage.setText("Invalid Inventory Field Input");
                exceptionMessage.setVisible(true);
            }
        });
        
        minField.textProperty().addListener((obs, prev, next) -> {
            try {
                if (!minField.getText().matches("[0-9]*")) {
                    minField.setText(prev);
                } else {
                    min = Integer.parseInt(next);
                    exceptionMessage.setVisible(false);
                }
            } catch (NumberFormatException e) {
                exceptionMessage.setText("Invalid Min Field Input");
                exceptionMessage.setVisible(true);
            }
        });
        
        maxField.textProperty().addListener((obs, prev, next) -> {
            try {
                if (!maxField.getText().matches("[0-9]*")) {
                    maxField.setText(prev);
                } else {
                    max = Integer.parseInt(next);
                    exceptionMessage.setVisible(false);
                }
            } catch (NumberFormatException e) {
                exceptionMessage.setText("Invalid Max Field Input");
                exceptionMessage.setVisible(true);
            }
        });
        
        // NOT perfect regex, but it will do for now
        priceField.textProperty().addListener((obs, prev, next) -> {
            try {
                if (!priceField.getText().matches("[.0-9]*")) {
                    priceField.setText(prev);
                } else {
                    price = Double.parseDouble(next);
                    exceptionMessage.setVisible(false);
                }
            } catch (NumberFormatException e) {
                exceptionMessage.setText("Invalid Price Field Input");
                exceptionMessage.setVisible(true);
            }
        });
        

    }
}
