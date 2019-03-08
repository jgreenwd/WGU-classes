/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */

package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class Inventory {
    private final static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private final static ObservableList<Product> products = FXCollections.observableArrayList();

    
    /* ---------- Parts - Management Methods ---------- */
    public static ObservableList<Part> getAllParts() { return allParts; }
    public static void addPart(Part part) { allParts.add(part); }
    public static boolean deletePart(Part part) { return allParts.remove(part); }
   
    public static void updatePart(Part part) {
        allParts.remove(lookupPart(part.getPartID()));
        allParts.add(part);
    }
    
    public static Part lookupPart(int partID) {
        for(Part part: allParts) {
            if (part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }
    
    
    /* ---------- Products - Management Methods ---------- */
    public static ObservableList<Product> getAllProducts() { return products; }
    public static void addProduct(Product product) { products.add(product); }
    public static boolean removeProduct(Product product) { return products.remove(product); }
    
    public static void updateProduct(Product product) {
        products.remove(lookupProduct(product.getProductID()));
        products.add(product);
    }
    
    public static Product lookupProduct(int productID) {
        for(Product item: products) {
            if (item.getProductID() == productID)
                return item;
        }
        return null;
    }
    
    
    

}
