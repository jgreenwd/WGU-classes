/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

public class Country {
    private int     countryId;
    private String  countryName;
    
    public  int     getCountryId()              { return countryId; }
    public  void    setCountryId(int ID)        { countryId = ID; }
    public  String  getCountryName()            { return countryName; }
    public  void    setCountryName(String ctry) { countryName = ctry; }
    
    public Country(String name) {
        this.countryName = name;
    }
    
    public Country(int ID, String name) {
        this(name);
        this.countryId = ID;
    }
}
