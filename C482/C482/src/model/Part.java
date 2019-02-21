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

    // inStock needs a solid increment/decrement mechanism
    protected Part() {};
    
    protected Part(int PartID, String name, double price, int inStock, int min, int max) {
        setPartID(PartID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
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
}
