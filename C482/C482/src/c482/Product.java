package c482;

import java.util.ArrayList;

/**
 * @author Jeremy Greenwood
 * @mentor Rebekah Coggin
 * @ID     000917613
 * @class  C482
 */
public class Product {
    private ArrayList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

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
        // removes ALL instances of the part
        boolean partFound = false;
        for (Part associatedPart: associatedParts) {
            if (associatedPart.getPartID() == partID) {
                partFound = true;
                associatedParts.remove(associatedPart);
            }
        }
        return partFound;
    }   
}
