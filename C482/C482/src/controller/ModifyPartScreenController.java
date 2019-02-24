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

    @FXML private RadioButton inHouseRadio; 
    @FXML private RadioButton outsourcedRadio;
    private ToggleGroup partSource;
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
        
        // update Inventory
        Inventory.updatePart(part.getPartID(), part);
        
        // return to Main Screen after save
        cancelButtonPressed(event);
    }
    
    
    /* ---------- Cancel page & return to Main Screen ---------- */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    
    /* ---------- Initialize Part Screen ---------- */
    @Override public void initialize(URL url, ResourceBundle rb) {
        // create ToggleGroup for RadioButtons
        partSource = new ToggleGroup();
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
    }    
}
