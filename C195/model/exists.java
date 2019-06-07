/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

import javafx.beans.property.SimpleBooleanProperty;
import static model.CustomerList.CUSTOMER_LIST;

public interface exists<T> {
    SimpleBooleanProperty FOUND = new SimpleBooleanProperty(false);
    default boolean exists() {
        if (this instanceof Country) {
            CUSTOMER_LIST.forEach( (cust) -> {
                if (cust.getCountryName().equals(((Country) this).getCountryName())) {
                    FOUND.set(true);
                }
            });
        }
        
        if (this instanceof City) {
            CUSTOMER_LIST.forEach( (cust) -> {
                if (cust.getCityName().equals(((City) this).getCityName())) {
                    FOUND.set(true);
                }
            });
        }
        
        if (this instanceof Address) {
            CUSTOMER_LIST.forEach( (cust) -> {
                if (cust.getAddress1().equals(((Address) this).getAddress1()) &&
                    cust.getAddress2().equals(((Address) this).getAddress2()) &&
                    cust.getPostalCode().equals(((Address) this).getPostalCode()) &&
                    cust.getPhone().equals(((Address) this).getPhone())) {
                    FOUND.set(true);
                }
            });
        }
        
        if (this instanceof Customer) {
            CUSTOMER_LIST.forEach( (cust) -> {
                if (cust.getCustomerName().equals(((Customer) this).getCustomerName()) &&
                    cust.getActive() == ((Customer) this).getActive()) {
                    FOUND.set(true);
                }
            });
        }
        return FOUND.get();
    };
}
