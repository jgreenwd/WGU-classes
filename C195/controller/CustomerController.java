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
import java.util.ArrayList;
import java.util.List;
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


public class CustomerController implements Initializable {
    
    public static final ObservableList<CustomerDelegate> CUSTOMER_LIST = FXCollections.observableArrayList();
    @FXML private TableView<CustomerDelegate> customerTable = new TableView<>(CUSTOMER_LIST);
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Address, String> phoneColumn;
    @FXML private TableColumn<Address, String> addressColumn;
    @FXML private TableColumn<City, String> cityColumn;
    @FXML private TableColumn<Country, String> countryColumn;
    
    /* ===============================================================
     * On load, build ObservableList of DataBase Intersections:
     * Country/City/Address/Customer
     * =============================================================== */
    @Override public void initialize(URL url, ResourceBundle rb) {
        try {
            // Query DB for Customer details
            Query.getAllCustomers();
            
            // build ObservableList from Query
            while(Query.getResult().next()) {
                Country o = new Country();
                o.setCountryId(Query.getResult().getInt("countryId"));
                o.setCountry(Query.getResult().getString("country"));
                
                City i = new City(o);
                i.setCityId(Query.getResult().getInt("cityId"));
                i.setCityName(Query.getResult().getString("city"));
                
                Address a = new Address(i);
                a.setAddressId(Query.getResult().getInt("addressId"));
                a.setAddress(Query.getResult().getString("address"));
                a.setAddress2(Query.getResult().getString("address2"));
                a.setPostalCode(Query.getResult().getString("postalCode"));
                a.setPhone(Query.getResult().getString("phone"));
                
                Customer c = new Customer(a);
                c.setCustomerId(Query.getResult().getInt("customerId"));
                c.setCustomerName(Query.getResult().getString("customerName"));
                
                CUSTOMER_LIST.add(new CustomerDelegate(c));
            }

            // render Query results in TableView
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            cityColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            
            customerTable.setItems( CUSTOMER_LIST );
            
        } catch (SQLException | NullPointerException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    /* ===============================================================
     * (4025.01.05) - B: Add/Update/Delete DB records
     *
     * Query DB for customer info & render results to table
     * Display Alert if login fails.
     * =============================================================== */
    @FXML private VBox customerBox;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField address2Field;
    @FXML private TextField cityField;
    @FXML private TextField zipField;
    @FXML private TextField countryField;
    private int customerId = -1, addressId = -1, cityId = -1, countryId = -1;
    private String name, phone, addr, addr2, city, zip, country;
    private enum AMD { ADD, MODIFY, DELETE };
    private AMD status;
    
    public void newCustomerButtonPressed(ActionEvent e) {
        status = AMD.ADD;
        customerBox.setDisable(false);
        
        nameField.clear();
        phoneField.clear();
        addressField.clear();
        address2Field.clear();
        cityField.clear();
        zipField.clear();
        countryField.clear();
    }
    
    public void modifyCustomerButtonPressed(ActionEvent e) {
        customerBox.setDisable(false);
        status = AMD.MODIFY;
    }
    
    public void deleteCustomerButtonPressed(ActionEvent e) {
        status = AMD.DELETE;
    }
    
    public void getAppointmentsCalendar(ActionEvent e) {
        System.out.println("calendar pressed");
    }
    
    
    /* ===============================================================
     * (4025.01.05) - B: Add/Update/Delete DB records
     *
     * On Submit, check for pre-existing country/city/address/customer.
     * If exists, return <x>Id, else DB will auto-increment.
     * =============================================================== */
    public void submitButtonPressed(ActionEvent e) throws SQLException {
        country = countryField.getText();
        city = cityField.getText();
        addr = addressField.getText();
        addr2 = address2Field.getText();
        zip = zipField.getText();
        phone = phoneField.getText();
        name = nameField.getText();

        customerBox.setDisable(true);
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
