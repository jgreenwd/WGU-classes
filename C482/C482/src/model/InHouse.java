/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

public final class InHouse extends Part {
    private int machineID;
    
    // InHouse with UNknown PartID
    public InHouse(String name, double price, int inStock, int min, int max, int source) {
        super(name, price, inStock, min, max);
        setMachineID(source);
    }
    
    // InHouse with KNOWN PartID
    public InHouse(int PartID, String name, double price, int inStock, int min, int max, int source) {
        this(name, price, inStock, min, max, source);
        this.setPartID(PartID);
    }
    
    public int getMachineID() { return machineID; }
    public void setMachineID(int ID) { this.machineID = ID; }
}
