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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lib.Query;
import model.*;

// TODO Move CUSTOMER_LIST to appropriate external
// TODO implement .exists for Customer/Address/City/Country types
// TODO Add Customer Record
// TODO Modify Customer Record
// TODO Delete Customer Record

public class CustomerController implements Initializable {
    public static final ObservableList<ObservableCustomer> CUSTOMER_LIST = FXCollections.observableArrayList();
    @FXML private TableView<ObservableCustomer> customerTable = new TableView<>();
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Address, String> phoneColumn;
    @FXML private TableColumn<Address, String> addressColumn;
    @FXML private TableColumn<City, String> cityColumn;
    @FXML private TableColumn<Country, String> countryColumn;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField address1Field;
    @FXML private TextField address2Field;
    @FXML private TextField cityField;
    @FXML private TextField zipField;
    @FXML private TextField countryField;
    
    /* ===============================================================
     * On load, build ObservableList of DataBase Intersections
     *
     * Add listener for list selection
     * - store IDs for selection
     * - fill out form on selection
     * =============================================================== */
    @Override public void initialize(URL url, ResourceBundle rb) {
        try {
            // Query DB for Customer details
            Query.getAllCustomers();
            
            // build ObservableList of Customer objects
            while(Query.getResult().next()) {
                Customer customer =  new CustomerBuilder()
                    .setCountryName(Query.getResult().getString("country"))
                    .setCountryId(Query.getResult().getInt("countryId"))
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
                    .createCustomer();
                
                CUSTOMER_LIST.add( new ObservableCustomer(customer) );
                
            }

            // render Query results in TableView
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address1"));
            cityColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
            
            customerTable.setItems( CUSTOMER_LIST );
            
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        try {
            /* ===============================================================
             * (4025.01.06) - G: Use Lambdas
             *
             * #1 Justification: Using a lambda to add a listener creates more
             * succinct and readable code. It also allows one to maintain a
             * more consistently functional approach to development.
             * =============================================================== */
            // on Click populate form & store IDs
            customerTable.getSelectionModel().selectedItemProperty().addListener((obs, prev, next) -> {
                nameField.setText(next.getCustomerName());
                phoneField.setText(next.getPhone());
                address1Field.setText(next.getAddress1());
                address2Field.setText(next.getAddress2());
                zipField.setText(next.getPostalCode());
                cityField.setText(next.getCityName());
                countryField.setText(next.getCountryName());
            });
        } catch(NullPointerException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    /* ===============================================================
     * (4025.01.05) - B: Add/Update/Delete DB records
     *
     * Query DB for customer info & render results to table
     * Display Alert if login fails.
     * =============================================================== */
    
    // Press button to clear fields & reset *Ids
    public void newCustomerButtonPressed(ActionEvent e) {
        System.out.println("new pressed");
        nameField.clear();
        phoneField.clear();
        address1Field.clear();
        address2Field.clear();
        cityField.clear();
        zipField.clear();
        countryField.clear();
    }
    
    public void deleteCustomerButtonPressed(ActionEvent e) {
        System.out.println("delete pressed");
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
     * If exists, return <x>Id, else DB will auto-increment.
     * =============================================================== */
    public void submitButtonPressed(ActionEvent e) throws SQLException {
//        Country o = new Country();
//        o.setCountry(countryField.getText());
//        
//        
//        City i = new City(o);
//        i.setCityName(cityField.getText());
//        CUSTOMER_LIST.forEach((item) ->  {
//            if (item.getCityName().equals(cityField.getText()) && countryId > -1) {
//                i.setCityId(item.getCityId());
//                return;
//            }
//        });
//        
//        Address a = new Address(i);
//        a.setAddress(addressField.getText());
//        a.setAddress2(address2Field.getText());
//        a.setPostalCode(zipField.getText());
//        a.setPhone(phoneField.getText());
//        CUSTOMER_LIST.forEach((item) ->  {
//            if (item.getAddress().equals(addressField.getText()) && 
//                item.getAddress2().equals(address2Field.getText()) &&
//                item.getPostalCode().equals(zipField.getText()) &&
//                item.getPhone().equals(phoneField.getText()) &&
//                cityId > -1) {
//                a.setAddressId(item.getAddressId());
//                return;
//            }
//        });
//        

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
