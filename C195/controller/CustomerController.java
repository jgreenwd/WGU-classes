/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */

package controller;

import c195.C195;
import c195.C195.ProcessState;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML private TableColumn<Address, String> phoneColumn;
    @FXML private TableColumn<Address, String> addressColumn;
    @FXML private TableColumn<City, String> cityColumn;
    @FXML private TableColumn<Country, String> countryColumn;
    @FXML private VBox      customerBox;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField address1Field;
    @FXML private TextField address2Field;
    @FXML private TextField cityField;
    @FXML private TextField zipField;
    @FXML private TextField countryField;
    private int customerId, addressId, cityId, countryId;
    private ProcessState state = ProcessState.EDIT;
    
    /* ===============================================================
     * On load, build ObservableList of DataBase Intersections
     *
     * Add listener for list selection
     * - fill out form on selection
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
                    .setCountryId(Query.getResult().getInt("countryId"))
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
            System.out.println("Error (CustomerController:Initialize:GetAllCustomers): " + ex.getMessage());
        }
        
        try {
            /* ===============================================================
             * (4025.01.06) - G: Use Lambdas
             *
             * Justification: Using a lambda to add a listener creates more
             * succinct and readable code. It also allows one to maintain a
             * more consistently functional approach to development.
             * =============================================================== */
            // on Click populate form
            customerTable.getSelectionModel().selectedItemProperty().addListener((obs, prev, next) -> {
                customerId = next.getCustomerId();
                nameField.setText(next.getCustomerName());
                addressId = next.getAddressId();
                phoneField.setText(next.getPhone());
                address1Field.setText(next.getAddress1());
                address2Field.setText(next.getAddress2());
                zipField.setText(next.getPostalCode());
                cityId = next.getCityId();
                cityField.setText(next.getCityName());
                countryId = next.getCountryId();
                countryField.setText(next.getCountryName());
            });
        } catch(NullPointerException ex) {
            System.out.println("Error (CustomerController:Initialize:AddListener): " + ex.getMessage());
        }
    }
    
    
    /* ===============================================================
     * (4025.01.05) - B: Add/Update/Delete DB records
     *
     * Query DB for customer info & render results to table
     * Display Alert if login fails.
     * =============================================================== */
    
    // Press button to clear fields
    public void newCustomerButtonPressed(ActionEvent e) {
        customerBox.setDisable(false);
        state = ProcessState.ADD;
        customerId = 0;
        nameField.clear();
        addressId = 0;
        phoneField.clear();
        address1Field.clear();
        address2Field.clear();
        cityId = 0;
        cityField.clear();
        zipField.clear();
        countryId = 0;
        countryField.clear();
    }
    
    public void editCustomerButtonPressed(ActionEvent e) {
        state = ProcessState.EDIT;
    }
    
    public void deleteCustomerButtonPressed(ActionEvent e) {
        state = ProcessState.DELETE;
    }
    
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
        customerBox.setDisable(true);
        
        Customer customer =  new CustomerBuilder()
            .setCustomerId(customerId)
            .setCustomerName(nameField.getText())
            .setAddressId(addressId)
            .setAddress1(address1Field.getText())
            .setAddress2(address2Field.getText())
            .setPostalCode(zipField.getText())
            .setPhone(phoneField.getText())
            .setCityId(cityId)
            .setCityName(cityField.getText())
            .setCountryId(countryId)
            .setCountryName(countryField.getText())
            .createCustomer();

        // new country/city/address/customer
        Country o = new Country(countryField.getText());
        if (Query.getCountryId(countryField.getText()) == 0) {
            Query.insertCountry(o, C195.user);
        }
        o.setCountryId(Query.getCountryId(countryField.getText()));       
        
        City i = new City(o);
        i.setCityName(cityField.getText());
        if (Query.getCityId(cityField.getText()) > 0) {
            Query.insertCity(i, C195.user);
        }
        i.setCityId(Query.getCityId(cityField.getText()));
        
        Address a = new Address(i);
        a.setAddress1(address1Field.getText());
        a.setAddress2(address2Field.getText());
        a.setPostalCode(zipField.getText());
        a.setPhone(phoneField.getText());
        if (Query.getAddressId(address1Field.getText(), address2Field.getText(), 
                zipField.getText(), phoneField.getText()) == 0) {
            Query.insertAddress(a, C195.user);
        }
        a.setAddressId(Query.getAddressId(address1Field.getText(), 
                address2Field.getText(), zipField.getText(), phoneField.getText()));
        
        Customer u = new Customer(a);
        u.setCustomerName(nameField.getText());
        u.setActive(1);
        Query.insertCustomer(u, C195.user);
        u.setCustomerId(Query.getCustomerId(nameField.getText()));
        
        CUSTOMER_LIST.add(new ObservableCustomer(u));
    }
    
    /* ===============================================================
     * Return to Login screen
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

}
