/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package controller;

import c195.C195;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lib.DBConnection;


public class LoginController implements Initializable {
    @FXML private Label loginPromptLabel;
    @FXML private TextField loginUsername, loginPassword;
    @FXML private Button accessDbButton, exitButton;
    private ResourceBundle rb;
    private final String USERNAME = "test", PASSWORD = "test";

    
    /* ===============================================================
     * (4025.01.08) - A: Internationalize Login form
     *
     * Render appropriate login text from ResourcesBundle rb
     * =============================================================== */
    @Override public void initialize(URL url, ResourceBundle rb) throws NullPointerException {
        this.rb = rb;
        try{
            loginPromptLabel.setText(rb.getString("Login_Prompt"));
            loginUsername.setPromptText(rb.getString("Username_Field"));
            loginPassword.setPromptText(rb.getString("Password_Field"));
            accessDbButton.setText(rb.getString("Access_Button"));
            exitButton.setText(rb.getString("Exit"));
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    public void accessButtonPressed(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
        if (validLogin()) {
            Parent customerParent = FXMLLoader.load((getClass().getResource("/view/Customer.fxml")));

            Scene customerScene = new Scene(customerParent);
            C195.getPrimaryStage().setScene(customerScene);
            C195.getPrimaryStage().show();    
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(null);
            alert.setTitle(rb.getString("Invalid_Login_Title"));
            alert.setContentText(rb.getString("Invalid_Login_Message"));
            alert.showAndWait();
        }
    }
    
    
    public void exitButtonPressed(ActionEvent e) throws ClassNotFoundException, SQLException {
        DBConnection.closeConnection();
        System.exit(0);
    }
    

    public boolean validLogin() {
        return loginUsername.getText().equals(USERNAME) & loginPassword.getText().equals(PASSWORD);
    }
}   
