/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

public final class InHouse extends Part {
    private int machineID;
    
    public InHouse(int PartID, String name, double price, int inStock, int min, int max, int machineID) {
        super(PartID, name, price, inStock, min, max);
        setMachineID(machineID);
    }
    
    public int getMachineID() { return machineID; }
    public void setMachineID(int ID) { this.machineID = ID; }
}
