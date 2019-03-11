/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

public final class Outsourced extends Part {
    private String companyName;
    
    // Outsourced with UNknown PartID
    public Outsourced(String name, double price, int inStock, int min, int max, String source) {
        super(name, price, inStock, min, max);
        setCompanyName(source);
    }
    
    // Outsourced with KNOWN PartID
    public Outsourced(int PartID, String name, double price, int inStock, int min, int max, String source) {
        this(name, price, inStock, min, max, source);
        this.setPartID(PartID);
    }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
