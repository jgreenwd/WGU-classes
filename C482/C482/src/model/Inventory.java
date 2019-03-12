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

    public static Part lookupPart(int partID) {
        for(Part part: allParts) {
            if (part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }
    
    public static void updatePart(int index) {
        System.out.println("Updating Part: " + allParts.get(index).getName());
    }
    
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    
    
    /* ---------- Products - Management Methods ---------- */
    public static ObservableList<Product> getAllProducts() { return products; }
    public static void addProduct(Product product) { products.add(product); }
    public static boolean removeProduct(Product product) { return products.remove(product); }
    
    public static Product lookupProduct(int productID) {
        for(Product item: products) {
            if (item.getProductID() == productID)
                return item;
        }
        return null;
    }    
    
    public static void updateProduct(int index) {
        System.out.println("Updating Product: " + products.get(index).getName());
    }
    
    public static void updateProduct(int index, Product product) {
        products.set(index, product);
    }
}
