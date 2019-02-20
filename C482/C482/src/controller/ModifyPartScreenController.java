/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */
package controller;

import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
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

public class ModifyPartScreenController implements Initializable {

    private boolean modify = false;
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
     *  formReset() - CLEAR entries & RESET data entry fields to prompts
     *  partSourceSelect() - change sourceTitleField to match InHouse or Outsourced
     */
    public void formReset() {
        partLabel.setText("Add Part");
        
        idField.setText(null);
        partNameField.setText(null);
        invField.setText(null);
        priceField.setText(null);
        minField.setText(null);
        maxField.setText(null);
        sourceNameField.setText(null);
        
        idField.setPromptText("Auto Gen - Disabled");
        partNameField.setPromptText("Part Name");
        invField.setPromptText("Inv");
        priceField.setPromptText("Price/Cost");
        minField.setPromptText("Min");
        maxField.setPromptText("Max");
        sourceNameField.setPromptText("Mach ID");
    }
    
    public void partSourceSelect() {
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            sourceTitleLabel.setText("Machine ID");
            sourceNameField.setPromptText("Mach ID");
        }
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            sourceTitleLabel.setText("Company Name");
            sourceNameField.setPromptText("Comp Nm");
        }
    }
    
    
    /** ---------- Load Part details to Modify ---------- 
     *  loadPart(<Part-type> @param) - set fields to derived-class type-specific values
     *  loadPartData(Part @param) - set fields to all parent-class type-specific values
     */
    public void loadPart(InHouse part) {
        partSource.selectToggle(inHouseRadio);
        sourceNameField.setText(String.valueOf(part.getMachineID()));
        loadPartData(part);
    }
    
    public void loadPart(Outsourced part) {
        partSource.selectToggle(outsourcedRadio);
        sourceNameField.setText(String.valueOf(part.getCompanyName()));
        loadPartData(part);
    }
    
    public void loadPartData(Part part) {
        modify = true;
        partLabel.setText("Modify Part");
        partSourceSelect();
        idField.setText(String.valueOf(part.getPartID()));
        partNameField.setText(part.getName());
        invField.setText(String.valueOf(part.getInStock()));
        priceField.setText(String.format("$%,.2f", part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax())); 
    }


    


    /** ---------- Press save button ----------
     *  1. Create a part from InHouse or Outsourced
     *  2. .setXXXXXX part source property (MachineID or Company)
     *  3. .add(Part) to Inventory w/ auto-generated partID
     *  4. formReset() form to initial setting
     */
    public void saveButtonPressed() {
        int ID = Inventory.allParts.size() + 1;
        String name = partNameField.getText();
        Double price = Double.parseDouble((priceField.getText()).replace("$", ""));
        int inv = Integer.parseInt(invField.getText()),
            min = Integer.parseInt(minField.getText()),
            max = Integer.parseInt(maxField.getText());
        
        // save InHouse parts
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            InHouse partToAdd = new InHouse(ID, name, price, inv, min, max);
            partToAdd.setMachineID(Integer.parseInt(sourceNameField.getText()));
            Inventory.allParts.add(partToAdd);
        }
        // save Outsourced parts
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            Outsourced partToAdd = new Outsourced(ID, name, price, inv, min, max);
            partToAdd.setCompanyName(sourceNameField.getText());
            Inventory.allParts.add(partToAdd);
        }
        
        modify = false;
        formReset();
    }
    
    
    /* ---------- Cancel page & return to Main Screen ---------- */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/MainScreen.fxml"));
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
