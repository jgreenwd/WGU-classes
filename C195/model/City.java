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
    private final   SimpleIntegerProperty       cityId = new SimpleIntegerProperty();
    private final   SimpleStringProperty        cityName = new SimpleStringProperty();
    private         Country                     country;

    public  int     getCityId()                 { return cityId.get(); }
    public  City    setCityId(int ID)           { cityId.set(ID); return this; }
    public  String  getCityName()               { return cityName.get(); }
    public  City    setCityName(String city)    { cityName.set(city); return this;  }
    public  Country getCountryObj()             { return country; }
    public  void    setCountryObj(Country ctry) { this.country = ctry; }
    
    public City(Country country) {
        this.country = country;
    };
    
    public City(String name, Country country) {
        this(country);
        this.cityName.set(name);
    }
    
    public City(int ID, String name, Country country) {
        this(name, country);
        this.cityId.set(ID);
    }
}
