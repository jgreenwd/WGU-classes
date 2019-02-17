/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package c482;

public class InHouse extends Part {
    private int machineID;
    
    public InHouse(int PartID, String name, double price, int inStock, int min, int max) {
        super(PartID, name, price, inStock, min, max);
    }
    
    public int getMachineID() { return machineID; }
    public void setMachineID(int ID) { this.machineID = ID; }
}
