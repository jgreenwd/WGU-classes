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

public class ModifyPartScreenController implements Initializable {

    private final ToggleGroup partSource = new ToggleGroup();
    @FXML private RadioButton inHouseRadio; 
    @FXML private RadioButton outsourcedRadio;
    @FXML private TextField idField;
    @FXML private TextField partNameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private Label sourceTitleLabel;
    @FXML private TextField sourceNameField;

    private Part part = null;
    
    /* ---------- Load Part details to Screen ---------- */
    public void loadPart(InHouse param) {
        part = (InHouse) param;
        partSource.selectToggle(inHouseRadio);
        sourceNameField.setText(String.valueOf(param.getMachineID()));
        idField.setText(String.valueOf(param.getPartID()));
        partNameField.setText(part.getName());
        invField.setText(String.valueOf(part.getInStock()));
        priceField.setText(String.format("$%,.2f", part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
    }
    
    public void loadPart(Outsourced param) {
        part = (Outsourced) param;
        partSource.selectToggle(outsourcedRadio);
        sourceNameField.setText(String.valueOf(param.getCompanyName()));
        idField.setText(String.valueOf(param.getPartID()));
        partNameField.setText(part.getName());
        invField.setText(String.valueOf(part.getInStock()));
        priceField.setText(String.format("$%,.2f", part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
    }
        

    /* ---------- Save changes to Inventory ---------- */
    public void saveButtonPressed(ActionEvent event) throws IOException {
        // InHouse
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            part = new InHouse(
                Integer.parseInt(idField.getText()),
                partNameField.getText(),
                Double.parseDouble((priceField.getText()).replace("$", "")),
                Integer.parseInt(invField.getText()),
                Integer.parseInt(minField.getText()),
                Integer.parseInt(maxField.getText()),
                Integer.parseInt(sourceNameField.getText())
            );
        }
        // Outsourced
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            part = new Outsourced(
                Integer.parseInt(idField.getText()),
                partNameField.getText(),
                Double.parseDouble((priceField.getText()).replace("$", "")),
                Integer.parseInt(invField.getText()),
                Integer.parseInt(minField.getText()),
                Integer.parseInt(maxField.getText()),
                sourceNameField.getText());
        }
        
        Inventory.updatePart(part);
        returnToMainScreen(event);
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
         * 2. add listener to each RadioButton
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
