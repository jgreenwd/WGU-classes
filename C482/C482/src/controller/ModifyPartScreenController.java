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

// TODO: confirmation dialog for cancel click

public class ModifyPartScreenController implements Initializable {

    private final ToggleGroup partSource = new ToggleGroup();
    @FXML private RadioButton inHouseRadio; 
    @FXML private RadioButton outsourcedRadio;
    @FXML private TextField partNameField;
    @FXML private TextField idField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private Label sourceTitleLabel;
    @FXML private TextField sourceNameField;
    private int inv, min, max, MachineID;
    private boolean isInHouse = true;
    private double price;
    private Part partToUpdate;
    
    /* ---------- Load Part details to Screen ---------- */
    /*  assuming that valid data gets passed in on load  */
    public final void loadPart(InHouse param) {
        partToUpdate = (InHouse) param;
        partSource.selectToggle(inHouseRadio);
        sourceNameField.setText(String.valueOf(param.getMachineID()));
        MachineID = param.getMachineID();
        idField.setText(String.valueOf(param.getPartID()));
        partNameField.setText(param.getName());
        invField.setText(String.valueOf(param.getInStock()));
        priceField.setText(String.valueOf(param.getPrice()));
        minField.setText(String.valueOf(param.getMin()));
        maxField.setText(String.valueOf(param.getMax()));
    }
    
    public final void loadPart(Outsourced param) {
        partToUpdate = (Outsourced) param;
        partSource.selectToggle(outsourcedRadio);
        sourceNameField.setText(String.valueOf(param.getCompanyName()));
        idField.setText(String.valueOf(param.getPartID()));
        partNameField.setText(param.getName());
        invField.setText(String.valueOf(param.getInStock()));
        priceField.setText(String.valueOf(param.getPrice()));
        minField.setText(String.valueOf(param.getMin()));
        maxField.setText(String.valueOf(param.getMax()));
    }
        

    /* ---------- Save changes to Inventory ---------- */
    public void saveButtonPressed(ActionEvent event) throws IOException {
        if (max >= inv && inv >= min && ((MachineID != 0 && isInHouse) || (MachineID == 0 && !isInHouse))) {
            if (isInHouse) {
                partToUpdate = new InHouse( Integer.parseInt(idField.getText()), 
                        partNameField.getText(), price, inv, min, max, MachineID );
            } else {
                partToUpdate = new Outsourced( Integer.parseInt(idField.getText()), 
                        partNameField.getText(), price, inv, min, max, sourceNameField.getText() );
            }
        
            Inventory.updatePart(Integer.parseInt(idField.getText()) -1, partToUpdate);
            returnToMainScreen(event);
            
        } else {
//            exceptionMessage.setText("Invalid Part Input");
        }
    }
    
    
    /* ---------- Cancel Modification & Exit ---------- */
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
         * 3. based on toggle, validate sourceNameField
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
                // validate MachineID
                try {
                    if (!sourceNameField.getText().matches("[0-9]*")) {
                        sourceNameField.setText(prev);
                    } else {
                        MachineID = Integer.parseInt(next);
                    }
                } catch (NumberFormatException e) {
//                    exceptionMessage.setText("Invalid Machine ID");
                }
            } else {
                MachineID = 0;
            }
        });
        
        /* ---------- TextField Listeners ---------- *
         * === copied from AddPartScreenController ===
         * ===      same issues here as there      ===
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
