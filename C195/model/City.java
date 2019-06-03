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
    private final   SimpleIntegerProperty           cityId = new SimpleIntegerProperty();
    private final   SimpleStringProperty            cityName = new SimpleStringProperty();
    private         Country                         country;

    public int      getCityId()                     { return cityId.get(); }
    public void     setCityId(int ID)               { cityId.set(ID); }
    public String   getCityName()                   { return cityName.get(); }
    public void     setCityName(String city)        { cityName.set(city); }
    public Country  getCountry()                    { return country; }
    public void     setCountry(Country country)     { this.country = country; }
    
    public City(Country country) {
        this.country = country;
    };
}
