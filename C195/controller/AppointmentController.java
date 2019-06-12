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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

// TODO clear: clears date & time info

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
    private int                     customerId;
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
        System.out.println(minStart.getSelectionModel());
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
            LocalDB.initAppointment();
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
                customerId = next.getCustomerId();
                customerField.setText(next.getCustomerName());
                titleField.setText(next.getTitle());
                typeField.setText(next.getType());
                urlField.setText(next.getUrl());
                hourStart.setValue(next.getStart().toString().substring(11,13));
                minStart.setValue(next.getStart().toString().substring(14,16));
                hourEnd.setValue(next.getEnd().toString().substring(11,13));
                minEnd.setValue(next.getEnd().toString().substring(14,16));
                datePicker.setValue(next.getStart().toLocalDate());
                ;
            }
        );
        
//        String startHour = start.substring(11, 13);
//        String startMinute = start.substring(14, 16);
//        String endHour = start.substring(11, 13);
//        String endMinute = start.substring(14, 16);
//        LocalDate ldDate = LocalDate.of(year, month, day);
//        comboHourStart.getSelectionModel().select(hour);

//        comboMinStart.getSelectionModel().select(minute);
//        comboMinEnd.getSelectionModel().select(minute);
        
        newRadio.setToggleGroup(radioGroup);
        editRadio.setToggleGroup(radioGroup);
        deleteRadio.setToggleGroup(radioGroup);
        
        radioGroup.selectedToggleProperty().addListener((obs, prev, next) -> {
            if (next == editRadio) {
                state = NEDstate.EDIT;
//                customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
            } else if (next == deleteRadio) {
                state = NEDstate.DELETE;
//                customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
            } else if (next == newRadio) {
                state = NEDstate.NEW;
//                clearEntry();
            }
        });
    }
}
