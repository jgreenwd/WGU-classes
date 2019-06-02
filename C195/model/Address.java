/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Address {
    private         SimpleIntegerProperty           addressId = new SimpleIntegerProperty();
    private         SimpleStringProperty            address = new SimpleStringProperty();
    private         SimpleStringProperty            address2 = new SimpleStringProperty();
    private         SimpleStringProperty            postalCode = new SimpleStringProperty();
    private         SimpleStringProperty            phone = new SimpleStringProperty();
    
    public int      getAddressId()                  { return addressId.get(); }
    public void     setAddressID(int ID)            { addressId.set(ID); }
    public String   getAddress()                    { return address.get(); }
    public void     setAddress(String addr)         { address.set(addr); }
    public String   getAddress2()                   { return address2.get(); }
    public void     setAddress2(String addr2)       { address2.set(addr2); }
    public String   getPostalCode()                 { return postalCode.get(); }
    public void     setPostalCode(String zip)       { postalCode.set(zip); }
    public String   getPhone()                      { return phone.get(); }
    public void     setPhone(String num)            { phone.set(num); }
}
