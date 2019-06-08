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
    private final SimpleIntegerProperty cityId = new SimpleIntegerProperty();
    private final SimpleStringProperty  cityName = new SimpleStringProperty();
    private final SimpleStringProperty  countryName = new SimpleStringProperty();
    
    public int    getCityId()           { return this.cityId.get(); }
    public String getCityName()         { return this.cityName.get(); }
    public String getCountryName()      { return this.countryName.get(); }
    public void   setCityId(int ID)     { this.cityId.set(ID); }
    public void   setCityName(String name) { this.cityName.set(name); }
    public void   setCountryName(String name) { this.countryName.set(name); }

    
    public City() {}
    
    public City(int ID, String name, String countryName) {
        this.cityId.set(ID);
        this.cityName.set(name);
        this.countryName.set(countryName);
    }
}
