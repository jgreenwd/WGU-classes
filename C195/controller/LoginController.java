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
import lib.LogGen;
import lib.Query;

// TODO: Add alert w/ test for appointment w/in 15 min of login

public class LoginController implements Initializable {
    @FXML private Label loginPromptLabel;
    @FXML private TextField loginUsername, loginPassword;
    @FXML private Button accessDbButton, exitButton;
    private ResourceBundle rb;

    
    /* ===============================================================
     * (4025.01.08) - A: Internationalize Login form
     *
     * Create a log-in form that can determine the user’s location and 
     * translate log-in and error control messages (e.g., “The username 
     * and password did not match.”) into two languages.
     * =============================================================== */
    
    // Grab appropriate login text from ResourcesBundle rb
    @Override public void initialize(URL url, ResourceBundle rb) {
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
    
    // Render login form and set current User
    public void accessButtonPressed(ActionEvent e) throws IOException, ClassNotFoundException, SQLException, NullPointerException {
        boolean validity = false;
        if (validLogin()) {
            C195.user = Query.getUser(loginUsername.getText());
            validity = true;
            Parent customerParent = FXMLLoader.load((getClass().getResource("/view/Customer.fxml")));
            Scene customerScene = new Scene(customerParent);
            C195.getPrimaryStage().setScene(customerScene);
            C195.getPrimaryStage().show();
        } else {
            C195.user = null;
            validity = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(null);
            alert.setTitle(rb.getString("Invalid_Login_Title"));
            alert.setContentText(rb.getString("Invalid_Login_Message"));
            alert.showAndWait();
        }
        
        // record user login attempt
        try {
            LogGen.log(loginUsername.getText(), validity);
        } catch (NullPointerException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        
    }
    
    // Validate login credentials
    public boolean validLogin() throws SQLException {
        String usr = loginUsername.getText();
        String pwd = loginPassword.getText();

        Query.makeQuery(
            "SELECT CASE WHEN EXISTS("
            + "SELECT * FROM user WHERE userName=\"" + usr + "\" AND password=\"" + pwd +"\") "
            + "THEN TRUE "
            + "ELSE FALSE "
            +"END AS valid;");
            
        Query.getResult().first();
        
        return Query.getResult().getBoolean("valid");
    }
    
    // Exit program
    public void exitButtonPressed(ActionEvent e) throws ClassNotFoundException, SQLException {
        DBConnection.closeConnection();
        System.exit(0);
    }
    
}   
