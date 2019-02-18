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
    private ToggleGroup makeInOrOut;
    @FXML private TextField id;
    @FXML private TextField partName;
    @FXML private TextField inv;
    @FXML private TextField price;
    @FXML private TextField min;
    @FXML private TextField max;
    @FXML private TextField companyName;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    public void makeInOrOutSelect() { }
    
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        // toggle radiobuttons between "in-house" or "outsourced"
        makeInOrOut = new ToggleGroup();
        this.inHouse.setToggleGroup(makeInOrOut);
        this.outsourced.setToggleGroup(makeInOrOut);
    }    
}
