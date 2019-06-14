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
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import lib.LocalDB;
import model.*;


public class AppointmentController implements Initializable {
    @FXML private TableView<Appointment> appointmentTable = new TableView<>();
    @FXML private TableColumn<Appointment, String> customerColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> urlColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> dateColumn;
    @FXML private final ToggleGroup radioGroup = new ToggleGroup();
    @FXML private RadioButton       newRadio;
    @FXML private RadioButton       editRadio;
    @FXML private RadioButton       deleteRadio;
    @FXML private TextField         customerField;
    @FXML private TextField         titleField;
    @FXML private TextField         typeField;
    @FXML private TextField         urlField;
    @FXML private ChoiceBox<String> hourStart = new ChoiceBox<>();
    @FXML private ChoiceBox<String> minStart = new ChoiceBox<>();
    @FXML private ChoiceBox<String> hourEnd = new ChoiceBox<>();
    @FXML private ChoiceBox<String> minEnd = new ChoiceBox<>();
    @FXML private DatePicker        datePicker;
    private int                     customerId, appointmentId;
    private enum NEDstate { NEW,EDIT,DELETE; }
    private NEDstate                state = NEDstate.NEW;
    
    public void getCustomerScreen(ActionEvent e) throws IOException {
        Parent customerParent = FXMLLoader.load((getClass().getResource("/view/Customer.fxml")));
        Scene customerScene = new Scene(customerParent);
        C195.getPrimaryStage().setScene(customerScene);
        C195.getPrimaryStage().show();
    }
    
    public void clearEntry() {
        customerField.clear();
        titleField.clear();
        typeField.clear();
        urlField.clear();
        radioGroup.selectToggle(newRadio);
        hourStart.getSelectionModel().clearSelection();
        minStart.getSelectionModel().clearSelection();
        hourEnd.getSelectionModel().clearSelection();
        minEnd.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }
    
    public void displayCalendarWeekly() {
        System.out.println("Weekly Calendar");
    }
    
    public void displayCalendarMonthly() {
        System.out.println("Monthly Calendar");
    }
    
    public void generateReportAppointments() {
        System.out.println("Generate Reports: Appointments");
    }
    
    public void generateReportConsultantSchedules() {
        System.out.println("Generate Reports: Consultant Schedules");
    }
    
    public void generateReportCustomerSchedules() {
        System.out.println("Generate Reports: Customer Schedules");
    }
    
    
    /* ===============================================================
     * (4025.01.05) - B: Add/Update/Delete DB records
     *
     * "Provide the ability to add, update, and delete appointments, 
     * capturing the type of appointment and a link to the specific 
     * customer record in the database."
     * =============================================================== */
    public void submitButtonPressed(ActionEvent e) throws SQLException {
        Customer cust = LocalDB.get(customerField.getText());
        Appointment appt = new AppointmentBuilder()
            .setCustomerObj(cust)
            .setTitle(titleField.getText())
            .setType(typeField.getText())
            .setUrl(urlField.getText())
            .setStart(LocalDateTime.of(
                datePicker.getValue().getYear(),
                datePicker.getValue().getMonth(),
                datePicker.getValue().getDayOfMonth(),
                Integer.parseInt(hourStart.getValue()),
                Integer.parseInt(minStart.getValue())))
            .setEnd(LocalDateTime.of(
                datePicker.getValue().getYear(),
                datePicker.getValue().getMonth(),
                datePicker.getValue().getDayOfMonth(),
                Integer.parseInt(hourEnd.getValue()),
                Integer.parseInt(minEnd.getValue())))
            .createAppointment();
        
        if (LocalDB.contains(cust)) {
            switch(state){
                /* *******************************************************
                                    ADD NEW APPOINTMENT
                   ******************************************************* */
                case NEW:{
                    LocalDB.add(appt);
                    break;
                }
                
                /* *******************************************************
                                    EDIT EXISTING APPOINTMENT
                   ******************************************************* */
                case EDIT:{
                    appt.setAppointmentId(appointmentId);
                    int index = appointmentTable.getSelectionModel().getSelectedIndex();
                    LocalDB.set(index, appt);
                    
                    break;
                }
                /* *******************************************************
                                      DELETE APPOINTMENT
                   ****************************************************** */
                case DELETE:{
                    appt = appointmentTable.getSelectionModel().getSelectedItem();
                    
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Appointment");
                    alert.setContentText("Are you sure?");
                    alert.showAndWait();
                    
                    if (alert.getResult().getButtonData().equals(OK_DONE)) {
                        LocalDB.remove(appt);
                    } else {
                        return;
                    }
                    break;
                }
            }
            
            clearEntry();
            
            // Refresh Display
            appointmentTable.setItems(LocalDB.getListAppointments());
            
        } else {
        // Customer doesn't exist in database
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Customer");
            alert.setContentText("This Customer is not currently available.");
            alert.showAndWait();
        }
    }
    
    /* ===============================================================
     * *** Return to Login screen ***
     *
     * Clear current user. Load Login screen.
     * Use ResourceBundle to maintain I18N of Login screen on all viewings
     * =============================================================== */
    public void exitButtonPressed(ActionEvent e) throws ClassNotFoundException, SQLException, IOException {
        C195.user = null;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        loader.setResources(C195.getResourceBundle());
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        C195.getPrimaryStage().setTitle(C195.getResourceBundle().getString("Title"));
        C195.getPrimaryStage().setScene(scene);
        C195.getPrimaryStage().show();
    }
    
    
    /* ===============================================================
     * (4025.01.05) - B: Add/Update/Delete DB records
     *
     * Query DB for appointment info & render results to table
     * On load, build ObservableList of DataBase Intersections
     * Add listener for list selection
     * - populate form on selection
     * Add listener for New/Edit/Delete state
     * =============================================================== */
    @Override public void initialize(URL url, ResourceBundle rb) {
        // Query for customers
        try {
            LocalDB.init();
        } catch(SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        // render Query results in TableView
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        appointmentTable.setItems( LocalDB.getListAppointments() );
        
        
        /* ===============================================================
         * (4025.01.06) - G: Use Lambdas
         *
         * Justification: Using a lambda to add a listener creates more
         * succinct and readable code. It also allows a more consistently
         * functional approach to development.
         * =============================================================== */
        appointmentTable.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends Appointment> obs, Appointment prev, Appointment next) -> {
                if (next != null) {
                    appointmentId = next.getAppointmentId();
                    customerId = next.getCustomerId();
                    customerField.setText(next.getCustomerName());
                    titleField.setText(next.getTitle());
                    typeField.setText(next.getType());
                    urlField.setText(next.getUrl());
                    hourStart.setValue(next.getStartTime().substring(0,2));
                    minStart.setValue(next.getStartTime().substring(3));
                    hourEnd.setValue(next.getEndTime().substring(0,2));
                    minEnd.setValue(next.getEndTime().substring(3));
                    datePicker.setValue(next.getStart().toLocalDate());
                }
            }
        );
        
        newRadio.setToggleGroup(radioGroup);
        editRadio.setToggleGroup(radioGroup);
        deleteRadio.setToggleGroup(radioGroup);
        
        radioGroup.selectedToggleProperty().addListener((obs, prev, next) -> {
            if (next == editRadio) {
                state = NEDstate.EDIT;
                appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
            } else if (next == deleteRadio) {
                state = NEDstate.DELETE;
                appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
            } else if (next == newRadio) {
                state = NEDstate.NEW;
                clearEntry();
            }
        });
    }
}
