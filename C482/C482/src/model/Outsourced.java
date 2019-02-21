/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

public final class Outsourced extends Part {
    private String companyName;
    
    public Outsourced() {
        super();
    }
    
    public Outsourced(int PartID, String name, double price, int inStock, int min, int max, String companyName) {
        super(PartID, name, price, inStock, min, max);
        setCompanyName(companyName);
    }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}