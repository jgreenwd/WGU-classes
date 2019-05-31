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
import javafx.scene.control.cell.PropertyValueFactory;
import lib.Query;
import model.Customer;


public class CustomerController implements Initializable {
    
    @FXML private TableView<Customer> customerTable = new TableView<>();
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, String> cityColumn;
    @FXML private TableColumn<Customer, String> countryColumn;
    public static final ObservableList<Customer> CUSTOMER_LIST = FXCollections.observableArrayList();
    
    
    /* ===============================================================
     * (4025.01.05) - B: Add/Update/Delete DB records
     *
     * Query DB for customer info & render results to table
     * Display Alert if login fails.
     * =============================================================== */
    @Override public void initialize(URL url, ResourceBundle rb) {
        try {
            // Query DB for Customer details
            Query.makeQuery("SELECT customerId, customerName, phone, address, address2, postalCode, city, country "
                    + "FROM customer JOIN address ON customer.addressId=address.addressId "
                    + "JOIN city ON address.cityId=city.cityId "
                    + "JOIN country ON city.countryId=country.countryId "
                    + "ORDER BY customerId; ");
        
            // build ObservableList from Query
            while(Query.getResult().next()) {
                Customer temp = new Customer(
                    Query.getResult().getInt("customerId"),
                    Query.getResult().getString("customerName"),
                    Query.getResult().getString("phone"),
                    Query.getResult().getString("address"),
                    Query.getResult().getString("address2"),
                    Query.getResult().getString("postalCode"),
                    Query.getResult().getString("city"),
                    Query.getResult().getString("country")
            );
                
            CustomerController.CUSTOMER_LIST.add(temp);
            
        }
            // display Query results in window
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            
            customerTable.setItems( this.CUSTOMER_LIST );
            
        } catch (SQLException | NullPointerException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
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
