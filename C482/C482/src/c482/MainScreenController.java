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
import javafx.stage.Stage;

public class MainScreenController implements Initializable {
    
    /* ---------- Parts Management ---------- */
    @FXML private Button searchParts;
    @FXML private Button addParts;
    @FXML private Button modifyParts;
    @FXML private Button deleteParts;
    
    public void searchPartsButton(ActionEvent event) throws IOException {
        System.out.println("Parts: Search button pressed");
    }
    
    public void addPartsButton(ActionEvent event) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("PartScreen.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addPartsScene);
        window.show();
    }
    
    public void modifyPartsButton(ActionEvent event) throws IOException {
        Parent modifyPartsParent = FXMLLoader.load(getClass().getResource("PartScreen.fxml"));
        Scene modifyPartsScene = new Scene(modifyPartsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(modifyPartsScene);
        window.show();
    }
    
    public void deletePartsButton(ActionEvent event) throws IOException {
        System.out.println("Parts: Delete button pressed");
    }
    
    
    /* ---------- Products Management ---------- */
    @FXML private Button searchProducts;
    @FXML private Button addProducts;
    @FXML private Button modifyProducts;
    @FXML private Button deleteProducts;
    
    public void searchProductsButton(ActionEvent event) throws IOException {
        System.out.println("Products: Search button pressed");
    }
    
    public void addProductsButton(ActionEvent event) throws IOException {
        Parent addProductsParent = FXMLLoader.load(getClass().getResource("ProductScreen.fxml"));
        Scene addProductsScene = new Scene(addProductsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addProductsScene);
        window.show();
    }
    
    public void modifyProductsButton(ActionEvent event) throws IOException {
        Parent modifyProductsParent = FXMLLoader.load(getClass().getResource("ProductScreen.fxml"));
        Scene modifyProductsScene = new Scene(modifyProductsParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(modifyProductsScene);
        window.show();
    }
    
    public void deleteProductsButton(ActionEvent event) throws IOException {
        System.out.println("Products: Delete button pressed");
    }
    
    
    /* ---------- Exit ---------- */
    @FXML private Button exitButton;
    
    public void exitButton() {
        System.exit(0);
    }
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
