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
import java.sql.SQLIntegrityConstraintViolationException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import lib.LocalDB;
import model.*;


public class CustomerController implements Initializable {
    @FXML private TableView<Customer> customerTable = new TableView<>();
    @FXML private TableColumn<Customer, String> customerColumn;
    @FXML private TableColumn<Address, String> phoneColumn;
    @FXML private TableColumn<Address, String> addressColumn;
    @FXML private TableColumn<Address, String> zipColumn;
    @FXML private TableColumn<City, String> cityColumn;
    @FXML private TableColumn<City, String> countryColumn;
    @FXML private final ToggleGroup radioGroup = new ToggleGroup();
    @FXML private RadioButton newRadio;
    @FXML private RadioButton editRadio;
    @FXML private RadioButton deleteRadio;
    @FXML private TextField   nameField;
    @FXML private TextField   phoneField;
    @FXML private TextField   address1Field;
    @FXML private TextField   address2Field;
    @FXML private TextField   cityNameField;
    @FXML private TextField   zipField;
    @FXML private TextField   countryField;
    private int               customerId, addressId;
    private enum NEDstate { NEW,EDIT,DELETE; }
    private NEDstate          state = NEDstate.NEW;
    
    
    public void getAppointmentScreen(ActionEvent e) throws IOException {
        Parent calendarParent = FXMLLoader.load((getClass().getResource("/view/Appointment.fxml")));
        Scene calendarScene = new Scene(calendarParent);
        C195.getPrimaryStage().setScene(calendarScene);
        C195.getPrimaryStage().show();
    }
    
    public void clearEntry() {
        nameField.clear();
        address1Field.clear();
        address2Field.clear();
        zipField.clear();
        phoneField.clear();
        cityNameField.clear();
        countryField.clear();
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
     * "Provide the ability to add, update, and delete customer records
     * in the database, including name, address, and phone number."
     *
     * NOTICE: no add/edit/delete of city/country mentioned in rubric
     * NOTICE: ERD uses phone as part of address; presents issues with
     *  referential integrity. Ignored for this project.
     *  -> new phone = new address
     * =============================================================== */
    public void submitButtonPressed(ActionEvent e) throws SQLException {
        Customer customer =  new CustomerBuilder()
            .setCustomerName(nameField.getText())
            .setActive(1)
            .setAddress1(address1Field.getText())
            .setAddress2(address2Field.getText())
            .setPostalCode(zipField.getText())
            .setPhone(phoneField.getText())
            .setCityName(cityNameField.getText())
            .setCountryName(countryField.getText())
            .createCustomer();
        Address address = customer.getAddressObj();        
        City city = address.getCityObj();
        
        
        /* =====================================================================
         * (4025.01.09) - F: Exception Control
         *
         * "Write exception controls to prevent each of the following...
         *   * entering nonexistent or INVALID customer data
         *
         * try-catch valid customer city/country entry
         * ===================================================================== */
        try {
            city.setCityId(LocalDB.getCityId(city));
            
            switch(state){
                /* *******************************************************
                                    ADD NEW CUSTOMER
                   ******************************************************* */
                case NEW:{
                    LocalDB.add(customer);
                    break;
                }
                
                /* *******************************************************
                                    EDIT EXISTING CUSTOMER
                   ******************************************************* */
                case EDIT:{
                    customer.setCustomerId(customerId);
                    int index = customerTable.getSelectionModel().getSelectedIndex();
                    LocalDB.set(index, customer);
                    
                    break;
                }
                /* *******************************************************
                                      DELETE  CUSTOMER
                    ****************************************************** */
                case DELETE:{
                    customer = customerTable.getSelectionModel().getSelectedItem();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Customer");
                    alert.setContentText("This will remove " +customer.getCustomerName()+ 
                            " and "+ customer.getCustomerName()+"'s scheduled appointments. Are you sure?");
                    alert.showAndWait();
                    
                    if (alert.getResult().getButtonData().equals(OK_DONE)) {
                        LocalDB.remove(customer);
                    } else {
                        return;
                    }
                    break;
                }
            }
            
            clearEntry();
            
            // Refresh Display
            customerTable.setItems(LocalDB.getListCustomers());
            
        } catch (SQLIntegrityConstraintViolationException ex) {
        // City/Country doesn't exist in database
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid City/Country");
            alert.setContentText("This City/Country is currently not available.");
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
     * Query DB for customer info & render results to table
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
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address1"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
            
        customerTable.setItems( LocalDB.getListCustomers() );
        
        
        /* ===============================================================
         * (4025.01.06) - G: Use Lambdas
         *
         * Justification: Using a lambda to add a listener creates more
         * succinct and readable code. It also allows a more consistently
         * functional approach to development.
         * =============================================================== */
        customerTable.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends Customer> obs, Customer prev, Customer next) -> {
                if (next != null) {
                    customerId = next.getCustomerId();
                    nameField.setText(next.getCustomerName());
                    addressId = next.getAddressObj().getAddressId();
                    address1Field.setText(next.getAddressObj().getAddress1());
                    address2Field.setText(next.getAddressObj().getAddress2());
                    phoneField.setText(next.getAddressObj().getPhone());
                    zipField.setText(next.getAddressObj().getPostalCode());
                    cityNameField.setText(next.getAddressObj().getCityObj().getCityName());
                    countryField.setText(next.getAddressObj().getCityObj().getCountryName());
                }
            }
        );

        newRadio.setToggleGroup(radioGroup);
        editRadio.setToggleGroup(radioGroup);
        deleteRadio.setToggleGroup(radioGroup);
        
        radioGroup.selectedToggleProperty().addListener((obs, prev, next) -> {
            if (next == editRadio) {
                state = NEDstate.EDIT;
                customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
            } else if (next == deleteRadio) {
                state = NEDstate.DELETE;
                customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
            } else if (next == newRadio) {
                state = NEDstate.NEW;
                clearEntry();
            }
        });
        
        
    }
}
