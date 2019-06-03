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
    private final SimpleIntegerProperty             countryId = new SimpleIntegerProperty();
    private final SimpleStringProperty              countryName = new SimpleStringProperty();
    
    public int      getCountryId()                  { return countryId.get(); }
    public void     setCountryId(int ID)            { countryId.set(ID); }
    public String   getCountry()                    { return countryName.get(); }
    public void     setCountry(String country)      { countryName.set(country); }
}
