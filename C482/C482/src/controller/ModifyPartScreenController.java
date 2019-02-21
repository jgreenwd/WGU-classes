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
        idField.setText(String.valueOf(part.getPartID()));
        partNameField.setText(part.getName());
        invField.setText(String.valueOf(part.getInStock()));
        priceField.setText(String.format("$%,.2f", part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax())); 
    }


    /* ---------- Press save button ----------
     *  1. .getText() input from TextFields
     *  2. Create a part from InHouse or Outsourced
     *  3. .add(Part) to Inventory w/ auto-generated partID
     * --------------------------------------- */
    public void saveButtonPressed(ActionEvent event) throws IOException {
        Part partToAdd = null;
        int ID = Inventory.allParts.size() + 1,
            inv = Integer.parseInt(invField.getText()),
            min = Integer.parseInt(minField.getText()),
            max = Integer.parseInt(minField.getText());
        double price = Double.parseDouble((priceField.getText()).replace("$", ""));
        String name = partNameField.getText();

        // save InHouse parts
        if (this.partSource.getSelectedToggle().equals(this.inHouseRadio)) {
            partToAdd = new InHouse(ID, name, price, inv, min, max,
                Integer.parseInt(sourceNameField.getText())
            );
        }
        // save Outsourced parts
        if (this.partSource.getSelectedToggle().equals(this.outsourcedRadio)) {
            partToAdd = new Outsourced(ID, name, price, inv, min, max,
                sourceNameField.getText()
            );
        }
        Inventory.addPart(partToAdd);
        
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
