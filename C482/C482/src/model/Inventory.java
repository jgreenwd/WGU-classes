/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

import java.util.ArrayList;

// TODO: updateProduct & updatePart need implementation
public final class Inventory {
    public static ArrayList<Part> allParts = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    
    
    /* ---------- Parts ---------- */
    public static void addPart(Part part) { allParts.add(part); }
    
    public static boolean deletePart(Part part) {
        for(Part item: allParts) {
            if (item == part) {
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }
   
    public static Part lookupPart(int partID) {
        return allParts.get(partID);
    }
    
    public static void updatePart(int partID) {
        
    }
    
    
    /* ---------- Products ---------- */
    public void addProduct(Product product) { products.add(product); }
    
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
}
