/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

public class City {
    private int     cityId;
    private String  cityName;
    private Country country;

    public  int     getCityId()                 { return cityId; }
    public  void    setCityId(int ID)           { cityId = ID; }
    public  String  getCityName()               { return cityName; }
    public  void    setCityName(String city)    { cityName = city; }
    public  Country getCountryObj()             { return country; }
    public  void    setCountryObj(Country ctry) { this.country = ctry; }
    
    public City(Country country) {
        this.country = country;
    };
    
    public City(String name, Country country) {
        this(country);
        this.cityName = name;
    }
    
    public City(int ID, String name, Country country) {
        this(name, country);
        this.cityId = ID;
    }
}
