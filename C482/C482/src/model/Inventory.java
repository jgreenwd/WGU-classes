/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

import java.util.ArrayList;

// TODO: updateProduct needs implementation
public final class Inventory {
    public static ArrayList<Part> allParts = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    
    
    /* ---------- Parts - UML Rubric Methods ---------- */
    public static void addPart(Part part) {
        allParts.add(part);
    }
    
    public static boolean deletePart(Part part) {
        return allParts.remove(part);
    }
   
    public static Part lookupPart(int partID) {
        for(Part part: allParts) {
            if (part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }
    
    public static void updatePart(Part part) {
        allParts.remove(lookupPart(part.getPartID()));
        allParts.add(part);
    }
    
    
    /* ---------- Products - UML Rubric Methods ---------- */
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public boolean removeProduct(Product product) {
        return products.remove(product);
    }
    
    public Product lookupProduct(int productID) {
        for(Product item: products) {
            if (item.getProductID() == productID)
                return item;
        }
        return null;
    }
    
    public void updateProduct(int productID) { }
    
    public static int product_size() {
        return products.size();
    }
}
