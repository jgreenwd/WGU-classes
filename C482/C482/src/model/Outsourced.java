/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

public class Outsourced extends Part {
    private String companyName;
    
    public Outsourced(int PartID, String name, double price, int inStock, int min, int max) {
        super(PartID, name, price, inStock, min, max);
    }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
}
