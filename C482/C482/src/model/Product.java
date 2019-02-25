/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

import java.util.ArrayList;

public class Product {
    private ArrayList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public Product() {
        this.productID = createProductID();
    };
    
    public Product(ArrayList<Part> associatedParts, int productID, String name, double price, int inStock, int min, int max) {
        this.associatedParts = associatedParts;
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public int getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getInStock() { return inStock; }
    public int getMin() { return min; }
    public int getMax() { return max; }

    public void setProductID(int productID) { this.productID = productID; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setInStock(int inStock) { this.inStock = inStock; }
    public void setMin(int min) { this.min = min; }
    public void setMax(int max) { this.max = max; }
    
    public Part lookupAssociatedPart(int partID) {
        for (Part associatedPart : associatedParts) {
            if (associatedPart.getPartID() == partID)
                return associatedPart;
        }
        return null;
    }

    public void addAssociatedPart(Part part) {
        // parts list may contain multiples of the same part
        associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int partID) {
        // removes the first instance of the part
        for (Part associatedPart: associatedParts) {
            if (associatedPart.getPartID() == partID) {
                associatedParts.remove(associatedPart);
                return true;
            }
        }
        return false;
    }
    
    /* always return a higher productID than current highest productID so
     * that deleted productIDs are still viable for historical references
     */
    public int createProductID() {
        int i = 1;
        for(Product item: Inventory.products) {
            if (item.getProductID() >= i) {
                i = item.getProductID() + 1;
            }
        }
        
        return i;
    }
}
