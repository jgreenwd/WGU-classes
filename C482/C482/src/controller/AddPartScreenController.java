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
        
        // save as InHouse part
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            InHouse partToAdd = new InHouse();
            partToAdd.setPartID(Inventory.allParts.size() + 1);
            partToAdd.setName(partNameField.getText());
            partToAdd.setInStock(Integer.parseInt(invField.getText()));
            partToAdd.setMin(Integer.parseInt(minField.getText()));
            partToAdd.setMax(Integer.parseInt(maxField.getText()));
            partToAdd.setPrice(Double.parseDouble((priceField.getText()).replace("$", "")));
            partToAdd.setMachineID(Integer.parseInt(sourceNameField.getText()));
            
            Inventory.addPart(partToAdd);
        }
        
        // save Outsourced part
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            Outsourced partToAdd = new Outsourced();
            partToAdd.setPartID(Inventory.allParts.size() + 1);
            partToAdd.setName(partNameField.getText());
            partToAdd.setInStock(Integer.parseInt(invField.getText()));
            partToAdd.setMin(Integer.parseInt(minField.getText()));
            partToAdd.setMax(Integer.parseInt(maxField.getText()));
            partToAdd.setPrice(Double.parseDouble((priceField.getText()).replace("$", "")));
            partToAdd.setCompanyName(sourceNameField.getText());
            
            Inventory.addPart(partToAdd);
        }
        
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
        
        // set default RadioButton option as InHouse
        partSource.selectToggle(inHouseRadio);
    }    
}
