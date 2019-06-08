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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lib.Query;
import model.*;

// TODO Add Customer Record
// TODO Modify Customer Record
// TODO Delete Customer Record
// TODO Move CUSTOMER_LIST to appropriate external
public class CustomerController implements Initializable {
    public static final ObservableList<ObservableCustomer> CUSTOMER_LIST = FXCollections.observableArrayList();
    @FXML private TableView<ObservableCustomer> customerTable = new TableView<>();
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, String> cityColumn;
    @FXML private TableColumn<Customer, String> countryColumn;
    @FXML private ToggleGroup radioGroup = new ToggleGroup();
    @FXML private RadioButton newRadio;
    @FXML private RadioButton editRadio;
    @FXML private RadioButton deleteRadio;
    @FXML private VBox        customerBox;
    @FXML private TextField   nameField;
    @FXML private TextField   phoneField;
    @FXML private TextField   address1Field;
    @FXML private TextField   address2Field;
    @FXML private TextField   cityNameField;
    @FXML private TextField   zipField;
    @FXML private TextField   countryField;
    private int customerId, addressId, cityId;
    private enum NEDstate { NEW,EDIT,DELETE; }
    private NEDstate state = NEDstate.NEW;
    
    
    public void getAppointmentsCalendar(ActionEvent e) throws IOException {
        Parent calendarParent = FXMLLoader.load((getClass().getResource("/view/Appointment.fxml")));
        Scene calendarScene = new Scene(calendarParent);
        C195.getPrimaryStage().setScene(calendarScene);
        C195.getPrimaryStage().show();
    }
    
    
    /* ===============================================================
     * (4025.01.05) - B: Add/Update/Delete DB records
     *
     * On Submit, check for pre-existing country/city/address/customer.
     * =============================================================== */
    public void submitButtonPressed(ActionEvent e) throws SQLException {
        switch(state){
            case NEW:{
                System.out.println("new submitted");
                break;
            }
            case EDIT:{
            /* *******************************************************
               Using CUSTOMER_LIST.set() throws a NullPointerException
               that appears to originate within the table listener. It
               will not catch within this try-catch block.
            
               Use .remove() & .add() instead for now.
               .add()ed element appears at bottom of list 
               ******************************************************* */
                try {
                    Customer customer =  new CustomerBuilder()
                        .setCustomerId(customerId)
                        .setCustomerName(nameField.getText())
                        .setAddressId(addressId)
                        .setAddress1(address1Field.getText())
                        .setAddress2(address2Field.getText())
                        .setPostalCode(zipField.getText())
                        .setPhone(phoneField.getText())
                        .setCityId(cityId)
                        .setCityName(cityNameField.getText())
                        .setCountryName(countryField.getText())
                    .createCustomer();
        
                    Query.updateAddress(customer.getAddressObj(), C195.user);
                    Query.updateCustomer(customer, C195.user);

                    CUSTOMER_LIST.remove(customerTable.getSelectionModel().getSelectedIndex());
                    CUSTOMER_LIST.add(new ObservableCustomer(customer));
                } catch(NullPointerException ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                }
                break;
            }
            case DELETE:{
                System.out.println("delete submitted");
                break;
            }
        }
    }
    
    
    /* ===============================================================
     * *** Return to Login screen ***
     *
     * Clear current user.
     * Load Login screen.
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
        try {
            // Query DB for Customer details
            Query.getAllCustomers();

            // build ObservableList of Customer objects
            if (Query.getResult().next() == false) {
                System.out.println("Empty CUSTOMER_LIST");
            } else {
                do {
                    Customer customer =  new CustomerBuilder()
                    .setCustomerId(Query.getResult().getInt("customerId"))
                    .setCustomerName(Query.getResult().getString("customerName"))
                    .setActive(Query.getResult().getInt("active"))
                    .setAddressId(Query.getResult().getInt("addressId"))
                    .setAddress1(Query.getResult().getString("address"))
                    .setAddress2(Query.getResult().getString("address2"))
                    .setPostalCode(Query.getResult().getString("postalCode"))
                    .setPhone(Query.getResult().getString("phone"))
                    .setCityId(Query.getResult().getInt("cityId"))
                    .setCityName(Query.getResult().getString("city"))
                    .setCountryName(Query.getResult().getString("country"))
                .createCustomer();
                
                CUSTOMER_LIST.add( new ObservableCustomer(customer) );
                } while(Query.getResult().next());
            }
            
            // render Query results in TableView
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address1"));
            cityColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
            
            customerTable.setItems( CUSTOMER_LIST );
            
        } catch (SQLException ex) {
            System.out.println("ERROR (Initialize): " + ex.getMessage());
        }
        
        
        /* ===============================================================
         * (4025.01.06) - G: Use Lambdas
         *
         * Justification: Using a lambda to add a listener creates more
         * succinct and readable code. It also allows one to maintain a
         * more consistently functional approach to development.
         * =============================================================== */
        try {
            customerTable.getSelectionModel().selectedItemProperty().addListener((obs, prev, next) -> {
                customerId = next.getCustomerId();
                nameField.setText(next.getCustomerName());
                addressId = next.getAddressId();
                phoneField.setText(next.getPhone());
                address1Field.setText(next.getAddress1());
                address2Field.setText(next.getAddress2());
                zipField.setText(next.getPostalCode());
                cityId = next.getCityId();
                cityNameField.setText(next.getCityName());
                countryField.setText(next.getCountryName());
            });
        } catch(Exception ex) {
            System.out.println("ERROR (TableViewListener): " + ex.getMessage());
        }
        
        try {
            newRadio.setToggleGroup(radioGroup);
            editRadio.setToggleGroup(radioGroup);
            deleteRadio.setToggleGroup(radioGroup);
        
            radioGroup.selectedToggleProperty().addListener((obs, prev, next) -> {
                if (next == editRadio) {
                    state = NEDstate.EDIT;
                } else if (next == deleteRadio) {
                    state = NEDstate.DELETE;
                } else if (next == newRadio) {
                    state = NEDstate.NEW;
                }
            });
        } catch(Exception ex) {
            System.out.println("ERROR (RadioListener): " + ex.getMessage());
        }
    }
}
