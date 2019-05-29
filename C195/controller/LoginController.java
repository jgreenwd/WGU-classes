/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lib.DBConnection;


public class LoginController implements Initializable {
    @FXML private Label loginPromptLabel;
    @FXML private TextField loginUsername, loginPassword;
    @FXML private Button accessDB;
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
            accessDB.setText(rb.getString("Access_Button"));
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    /* ===============================================================
     * Should NOT write DB credentials directly into code, but this is
     * necessary to achieve requirement: "You must use “test” as 
     * the username and password to log-in."
     * 
     * Ideally, would read user entry and validate on server side:
     * DBConnection.makeConnection(loginUsername.getText(), loginPassword.getText());
     *
     * Display Alert if login fails.
     * =============================================================== */
    public void accessButtonPressed(ActionEvent e) {
        if (validLogin()) {
            try {
                DBConnection.makeConnection("U05sep", "53688595689");
            
                DBConnection.closeConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle(rb.getString("Invalid_Login_Title"));
            alert.setContentText(rb.getString("Invalid_Login_Message"));
            alert.showAndWait();
        }
    }
    

    public boolean validLogin() {
        return loginUsername.getText().equals(USERNAME) & loginPassword.getText().equals(PASSWORD);
    }
}   
