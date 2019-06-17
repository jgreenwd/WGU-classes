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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import lib.LocalDB;
import lib.LogGen;
import lib.Query;
import model.Appointment;


public class LoginController implements Initializable {
    @FXML private Label loginPromptLabel;
    @FXML private TextField loginUsername, loginPassword;
    @FXML private Button accessDbButton, exitButton;
    private ResourceBundle rb;

    
    /* =========================================================================
     * (4025.01.08) - A: Internationalize Login form
     *
     * "Create a log-in form that can determine the user’s location and 
     * translate log-in and error control messages (e.g., “The username 
     * and password did not match.”) into two languages."
     * ========================================================================= */
    
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
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    // Render login form and set current User
    public void accessButtonPressed(ActionEvent e) throws IOException, ClassNotFoundException, SQLException, NullPointerException {
        boolean validity = false;
        if (Query.login(loginUsername.getText().trim(), loginPassword.getText().trim())) {
            C195.user = Query.getUser(loginUsername.getText());
            validity = true;
            Parent customerParent = FXMLLoader.load((getClass().getResource("/view/Customer.fxml")));
            Scene customerScene = new Scene(customerParent);
            C195.getPrimaryStage().setScene(customerScene);
            C195.getPrimaryStage().show();
            

            /* =================================================================
             * (4025.01.08) - H: Pending Appointmet Alert
             *
             * "Write code to provide an alert if there is an appointment within 
             * 15 minutes of the user’s log-in."
             * 
             * I am operating under the assumption that this refers to the
             * CURRENT user. If an appointment is scheduled for a different user,
             * it is still visible in the tableview, but will not fire an alert.
             * ================================================================= */
            ArrayList<Appointment> list = new ArrayList<>();
            
            // filter for appointments with current user
            // filter for appointments starting between now and now+15 min
            LocalDB.getAllAppointments()
                    .stream()
                    .filter((temp) -> (temp.getContact().equals(C195.user.getUsername())))
                    .filter((temp) -> (
                            temp.getStart().isBefore(LocalDateTime.now().plusMinutes(15)) &&
                            temp.getStart().isAfter(LocalDateTime.now())))
                    .forEachOrdered((temp) -> {
                        list.add(temp);
            });
            
            // if appointments exist, display an alert for the 1st in the list
            // list will never be larger than 1 element, due to 15 minute
            // scheduling increments
            if (list.size() > 0) {
                Appointment temp = list.get(0);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setContentText("An appointment with " + temp.getCustomerName()
                        + " is scheduled in "
                        + LocalDateTime.now().until(temp.getStart(), ChronoUnit.MINUTES)
                        + " minutes.");
                    alert.showAndWait();
            }
            
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
            System.out.println("ERROR: " + ex.getMessage());
        }
        
    }
    
    // Exit program
    public void exitButtonPressed(ActionEvent e) throws ClassNotFoundException, SQLException {
        DBConnection.closeConnection();
        System.exit(0);
    }
    
}   
