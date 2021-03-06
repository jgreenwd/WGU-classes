/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

public abstract class Part {
    private int PartID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    // default Constructor: use in AddPartScreenController
    private Part() { this.PartID = createPartID(); };

    // if PartID is unknown, generate one
    protected Part(String name, double price, int inStock, int min, int max) {
        this(); 
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }
    
    // if all details are known: use in ModifyPartScreenController
    protected Part(int PartID, String name, double price, int inStock, int min, int max) {
        this.PartID = PartID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public String getName() { return name; }
    public int getPartID() { return PartID; }
    public int getInStock() { return inStock; }
    public double getPrice() { return price; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    
    
    public void setName(String name) { this.name = name; }
    public void setPartID(int PartID) { this.PartID = PartID; }
    public void setInStock(int inStock) { this.inStock = inStock; }
    public void setPrice(double price) { this.price = price; }
    public void setMin(int min) { this.min = min; }
    public void setMax(int max) { this.max = max;}
    
   /* always return a higher partID than current highest partID so   *
    * that deleted partIDs are still viable for historical reference */
    public static int createPartID() {
        int i = 1;
        for(Part item: Inventory.getAllParts()) {
            if (item.getPartID() >= i) {
                i = item.getPartID() + 1;
            }
        }
        
        return i;
    }
}
