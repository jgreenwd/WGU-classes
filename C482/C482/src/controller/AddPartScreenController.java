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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartScreenController implements Initializable {

    @FXML private Label partLabel;
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
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    /** ---------- UI/UX functions ---------- 
     *  partSourceSelect() - change sourceTitleLabel to match InHouse or Outsourced
     */
    public void partSourceSelect() {
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            sourceTitleLabel.setText("Machine ID");
            sourceNameField.setPromptText("Machine ID");
        }
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            sourceTitleLabel.setText("Company Name");
            sourceNameField.setPromptText("Company Name");
        }
    }

    /** ---------- Press save button ----------
     *  1. Create a part from InHouse or Outsourced
     *  3. .add(Part) to Inventory w/ auto-generated partID
     */
    public void saveButtonPressed() {
        int ID = Inventory.allParts.size() + 1;
        String name = partNameField.getText(),
               companyName = sourceNameField.getText();
        Double price = Double.parseDouble((priceField.getText()).replace("$", ""));
        int inv = Integer.parseInt(invField.getText()),
            min = Integer.parseInt(minField.getText()),
            max = Integer.parseInt(maxField.getText()),
            machineID = Integer.parseInt(sourceNameField.getText());
        
        
        // save InHouse parts
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            InHouse partToAdd = new InHouse(ID, name, price, inv, min, max, machineID);
            Inventory.addPart(partToAdd);
        }
        // save Outsourced parts
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            Outsourced partToAdd = new Outsourced(ID, name, price, inv, min, max, companyName);
            Inventory.addPart(partToAdd);
        }
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
        
        // set default RadioButton option as InHouse
        partSource.selectToggle(inHouseRadio);
        partSourceSelect();
    }    
}
