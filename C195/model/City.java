/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

public class City {
    private int     CityID;
    private String  City;
    private int     CountryID;
    
    public int getCityID()           { return CityID; }
    public String getCity()          { return City; }
    public int getCountryID()        { return CountryID; }
    
    public void setCityID(int ID)    { CityID = ID; }
    public void setCountryID(int ID) { CountryID = ID; }
    public void setCity(String city) { City = city; }
}
