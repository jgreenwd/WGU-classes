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

    private final String inhouse = "InHouse", outsourced = "Outsourced";
    private InHouse _in = new InHouse();
    private Outsourced _out = new Outsourced();
    private Part part = null;
    
    /* ---------- Load Part details to Screen ---------- */
    public void loadPart(int partID) {
        String partType = Inventory.lookupPart(partID).getClass().getSimpleName();
        
        if (partType.equals(inhouse)) {
            partSource.selectToggle(inHouseRadio);
            _in = (InHouse) Inventory.lookupPart(partID);
            sourceNameField.setText(String.valueOf(_in.getMachineID()));
        } else if (partType.equals(outsourced)) {
            partSource.selectToggle(outsourcedRadio);
            _out = (Outsourced) Inventory.lookupPart(partID);
            sourceNameField.setText(String.valueOf(_out.getCompanyName()));
        }
        
        part = Inventory.lookupPart(partID);
        idField.setText(String.valueOf(part.getPartID()));
        partNameField.setText(part.getName());
        invField.setText(String.valueOf(part.getInStock()));
        priceField.setText(String.format("$%,.2f", part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
    }


    public void saveButtonPressed(ActionEvent event) throws IOException {
        
        // save as InHouse part
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            _in.setPartID(Integer.parseInt(idField.getText()));
            _in.setName(partNameField.getText());
            _in.setInStock(Integer.parseInt(invField.getText()));
            _in.setMin(Integer.parseInt(minField.getText()));
            _in.setMax(Integer.parseInt(maxField.getText()));
            _in.setPrice(Double.parseDouble((priceField.getText()).replace("$", "")));
            _in.setMachineID(Integer.parseInt(sourceNameField.getText()));
            
            Inventory.updatePart(_in.getPartID(), _in);
        }
        
        // save Outsourced part
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            _out.setPartID(Integer.parseInt(idField.getText()));
            _out.setName(partNameField.getText());
            _out.setInStock(Integer.parseInt(invField.getText()));
            _out.setMin(Integer.parseInt(minField.getText()));
            _out.setMax(Integer.parseInt(maxField.getText()));
            _out.setPrice(Double.parseDouble((priceField.getText()).replace("$", "")));
            _out.setCompanyName(sourceNameField.getText());
            
            Inventory.updatePart(_out.getPartID(), _out);
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
