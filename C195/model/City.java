/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class City {
    private         SimpleIntegerProperty           cityId = new SimpleIntegerProperty();
    private         SimpleStringProperty            cityName = new SimpleStringProperty();
    
    public int      getCityId()                     { return cityId.get(); }
    public void     setCityID(int ID)               { cityId.set(ID); }
    public String   getCityName()                   { return cityName.get(); }
    public void     setCityName(String city)        { cityName.set(city); }
}
