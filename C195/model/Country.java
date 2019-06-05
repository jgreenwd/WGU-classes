/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Country {
    private final   SimpleIntegerProperty       countryId = new SimpleIntegerProperty();
    private final   SimpleStringProperty        countryName = new SimpleStringProperty();
    
    public  int     getCountryId()              { return countryId.get(); }
    public  Country setCountryId(int ID)        { countryId.set(ID); return this; }
    public  String  getCountryName()            { return countryName.get(); }
    public  Country setCountryName(String ctry) { countryName.set(ctry); return this; }
    
    public Country(String name) {
        this.countryName.set(name);
    }
    
    public Country(int ID, String name) {
        this(name);
        this.countryId.set(ID);
    }
}
