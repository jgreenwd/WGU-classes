/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */
package c482;

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
    @FXML private RadioButton inHouse; 
    @FXML private RadioButton outsourced;
    private ToggleGroup partSource;
    @FXML private TextField id;
    @FXML private TextField partName;
    @FXML private TextField inv;
    @FXML private TextField price;
    @FXML private TextField min;
    @FXML private TextField max;
    @FXML private Label sourceTitle;
    @FXML private TextField sourceName;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;


    /* ---------- Change sourceTitle to match partSource toggle ---------- */
    public void partSourceSelect() {
        if (this.partSource.getSelectedToggle().equals(this.inHouse)) {
            sourceTitle.setText("Machine ID");
            sourceName.setPromptText("Mach ID");
        }
        if (this.partSource.getSelectedToggle().equals(this.outsourced)) {
            sourceTitle.setText("Company Name");
            sourceName.setPromptText("Comp Nm");
        }
    }
    
    /* ---------- Prep/Reset Part entry form ---------- */
    public void formReset() {
        id.setPromptText("Auto Gen - Disabled");
        partName.setPromptText("Part Name");
        inv.setPromptText("Inv");
        price.setPromptText("Price/Cost");
        min.setPromptText("Min");
        max.setPromptText("Max");
        sourceName.setPromptText("Mach ID");
        
        id.setText(null);
        partName.setText(null);
        inv.setText(null);
        price.setText(null);
        min.setText(null);
        max.setText(null);
        sourceName.setText(null);
    }

    /** ---------- Press save button ----------
     *  1. Create a part from InHouse or Outsourced
     *  2. .setXXXXXX part source property (MachineID or Company)
     *  3. .add(Part) to Inventory w/ auto-generated partID
     *  4. formReset() form to initial setting
     */
    public void saveButtonPressed() {
        // save InHouse parts
        if (this.partSource.getSelectedToggle().equals(this.inHouse)) {
            InHouse partToAdd = new InHouse(
                Inventory.allParts.size() + 1,
                partName.getText(),
                Double.parseDouble(price.getText()),
                Integer.parseInt(inv.getText()),
                Integer.parseInt(min.getText()),
                Integer.parseInt(max.getText())
            );
            partToAdd.setMachineID(Integer.parseInt(sourceName.getText()));
            Inventory.allParts.add(partToAdd);
            formReset();
        }
        // save Outsourced parts
        if (this.partSource.getSelectedToggle().equals(this.outsourced)) {
            Outsourced partToAdd = new Outsourced(
                Inventory.allParts.size() + 1,
                partName.getText(),
                Double.parseDouble(price.getText()),
                Integer.parseInt(inv.getText()),
                Integer.parseInt(min.getText()),
                Integer.parseInt(max.getText())
            );
            partToAdd.setCompanyName(sourceName.getText().toString());
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
        this.inHouse.setToggleGroup(partSource);
        this.outsourced.setToggleGroup(partSource);
        partSource.selectToggle(inHouse);
        partSourceSelect();
    }    
}
