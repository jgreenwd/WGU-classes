/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */
package c482;

import static com.sun.deploy.util.ReflectionUtil.instanceOf;
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

public class PartScreenController implements Initializable {

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

    //TODO fill out all part data on init(part)
    public void loadPart(InHouse part) {
        partSource.selectToggle(inHouseRadio);
        partSourceSelect();
        idField.setText(String.valueOf(part.getPartID()));
        partNameField.setText(part.getName());
        invField.setText(String.valueOf(part.getInStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
        sourceNameField.setText(String.valueOf(part.getMachineID()));
    }
    
    public void loadPart(Outsourced part) {
        partSource.selectToggle(outsourcedRadio);
        partSourceSelect();
        idField.setText(String.valueOf(part.getPartID()));
        partNameField.setText(part.getName());
        invField.setText(String.valueOf(part.getInStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
        sourceNameField.setText(String.valueOf(part.getCompanyName()));
    }

    /* ---------- Change sourceTitle to match partSource toggle ---------- */
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
    
    /* ---------- Prep/Reset Part entry form ---------- */
    public void formReset() {
        idField.setPromptText("Auto Gen - Disabled");
        partNameField.setPromptText("Part Name");
        invField.setPromptText("Inv");
        priceField.setPromptText("Price/Cost");
        minField.setPromptText("Min");
        maxField.setPromptText("Max");
        sourceNameField.setPromptText("Mach ID");
        
        idField.setText(null);
        partNameField.setText(null);
        invField.setText(null);
        priceField.setText(null);
        minField.setText(null);
        maxField.setText(null);
        sourceNameField.setText(null);
    }

    /** ---------- Press save button ----------
     *  1. Create a part from InHouse or Outsourced
     *  2. .setXXXXXX part source property (MachineID or Company)
     *  3. .add(Part) to Inventory w/ auto-generated partID
     *  4. formReset() form to initial setting
     */
    public void saveButtonPressed() {
        // save InHouse parts
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            InHouse partToAdd = new InHouse(
                Inventory.allParts.size() + 1,
                partNameField.getText(),
                Double.parseDouble(priceField.getText()),
                Integer.parseInt(invField.getText()),
                Integer.parseInt(minField.getText()),
                Integer.parseInt(maxField.getText())
            );
            partToAdd.setMachineID(Integer.parseInt(sourceNameField.getText()));
            Inventory.allParts.add(partToAdd);
            formReset();
        }
        // save Outsourced parts
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            Outsourced partToAdd = new Outsourced(
                Inventory.allParts.size() + 1,
                partNameField.getText(),
                Double.parseDouble(priceField.getText()),
                Integer.parseInt(invField.getText()),
                Integer.parseInt(minField.getText()),
                Integer.parseInt(maxField.getText())
            );
            partToAdd.setCompanyName(sourceNameField.getText().toString());
            Inventory.allParts.add(partToAdd);
            formReset();
        }
    }
    
    /* ---------- Cancel page & return to Main Screen ---------- */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    
    /* ---------- Initialize Part Screen ---------- */
    @Override public void initialize(URL url, ResourceBundle rb) {
        // toggle radiobuttons between "in-house" or "outsourced"
        partSource = new ToggleGroup();
        this.inHouseRadio.setToggleGroup(partSource);
        this.outsourcedRadio.setToggleGroup(partSource);
        partSource.selectToggle(inHouseRadio); // set default
        partSourceSelect();
    }    
}
