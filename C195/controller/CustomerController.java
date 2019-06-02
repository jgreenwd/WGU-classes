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
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        try {
            // Query DB for Customer details
            Query.makeQuery(
                    "SELECT country.countryId, country, city.cityId, city, "
                    + "address.addressId, address, address2, postalCode, phone, "
                    + "customer.customerId, customerName FROM country "
                    + "JOIN city ON country.countryId = city.countryId "
                    + "JOIN address ON city.cityId = address.cityId "
                    + "JOIN customer ON address.addressId=customer.addressId "
                    + "ORDER BY customer.customerId;"
            );
            
            
            // build ObservableList from Query
            while(Query.getResult().next()) {
                Country c1 = new Country();
                c1.setCountryId(Query.getResult().getInt("countryId"));
                c1.setCountry(Query.getResult().getString("country"));
                
                City c2 = new City();
                c2.setCityID(Query.getResult().getInt("cityId"));
                c2.setCityName(Query.getResult().getString("city"));
                
                Address a = new Address();
                a.setAddressID(Query.getResult().getInt("addressId"));
                a.setAddress(Query.getResult().getString("address"));
                a.setAddress2(Query.getResult().getString("address2"));
                a.setPostalCode(Query.getResult().getString("postalCode"));
                a.setPhone(Query.getResult().getString("phone"));
                
                Customer c3 = new Customer();
                c3.setCustomerId(Query.getResult().getInt("customerId"));
                c3.setCustomerName(Query.getResult().getString("customerName"));
                
                CustomerDelegate temp = new CustomerDelegate(c3, a, c2, c1);
                CUSTOMER_LIST.add(temp);
            }

            // display Query results in window
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
    @FXML private TextField customerName;
    @FXML private TextField customerPhone;
    @FXML private TextField customerAddress;
    @FXML private TextField customerAddress2;
    @FXML private TextField customerCity;
    @FXML private TextField customerZip;
    @FXML private TextField customerCountry;
    private String name, phone, addr, addr2, city, zip, country;
    
    public void newCustomerButtonPressed(ActionEvent e) {
        customerBox.setDisable(false);
        customerName.clear();
        customerPhone.clear();
        customerAddress.clear();
        customerAddress2.clear();
        customerCity.clear();
        customerZip.clear();
        customerCountry.clear();
    }
    
    public void modifyCustomerButtonPressed(ActionEvent e) {
        System.out.println("modify pressed");
    }
    
    public void deleteCustomerButtonPressed(ActionEvent e) {
        System.out.println("delete pressed");
    }
    
    public void getAppointmentsCalendar(ActionEvent e) {
        System.out.println("calendar pressed");
    }
    
    public void populateForm(ActionEvent e) {
        
    }
    
    public void submitButtonPressed(ActionEvent e) {
        customerBox.setDisable(true);
        name = customerName.getText();
        phone = customerPhone.getText();
        addr = customerAddress.getText();
        addr2 = customerAddress2.getText();
        city = customerCity.getText();
        zip = customerZip.getText();
        country = customerCountry.getText();
    }
    
    /* ===============================================================
     * Return to Login screen
     *
     * Use ResourceBundle to maintain I18N of Login screen on all viewings
     * =============================================================== */
    public void exitButtonPressed(ActionEvent e) throws ClassNotFoundException, SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        loader.setResources(C195.getResourceBundle());
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        C195.getPrimaryStage().setTitle(C195.getResourceBundle().getString("Title"));
        C195.getPrimaryStage().setScene(scene);
        C195.getPrimaryStage().show();
    }
    
}
