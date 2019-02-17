/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package c482;

import java.util.ArrayList;



// TODO: updateProduct & updatePart need implementation
public class Inventory {
    ArrayList<Product> products;
    ArrayList<Part> allParts;

    public Inventory() {
        this.products = new ArrayList<Product>();
        this.allParts = new ArrayList<Part>();
    }
    
    public Inventory(ArrayList<Product> products, ArrayList<Part> allParts) {
        this.products = products;
        this.allParts = allParts;
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public boolean removeProduct(Product product) {
        for(Product item: products) {
            if (item == product) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }
    
    public Product lookupProduct(int productID) {
        for(Product item: products) {
            if (item.getProductID() == productID)
                return item;
        }
        return null;
    }
    
    public void updateProduct(int productID) { }
    
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    public boolean deletePart(Part part) {
        for(Part item: allParts) {
            if (item == part) {
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }
   
    public Part lookupPart(int partID) {
        for(Part item: allParts) {
            if (item.getPartID() == partID)
                return item;
        }
        return null;
    }
    
    public void updatePart(int partID) { }
}
