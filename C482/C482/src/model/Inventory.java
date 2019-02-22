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
    
    
    /* ---------- Parts - UML Rubric Methods ---------- */
    public static void addPart(Part part) {
        allParts.add(part);
    }
    
    public static boolean deletePart(Part part) {
        for(Part item: allParts) {
            if (item == part) {
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }
   
    public static Part lookupPart(int index) {
        return allParts.get(index);
    }
    
    public static void updatePart(int partID, Part part) {
        allParts.set(partID - 1, part);
    }
    
    
    /* ---------- Parts - My Utility Methods ---------- */
    public static boolean deletePart(int index) {
        if (!allParts.isEmpty()) {
            allParts.remove(index);
            return true;
        }
        return false;
    }

    public static Part part_pop() {
        Part temp = allParts.get(allParts.size() - 1);
        allParts.remove(allParts.size() - 1);
        return temp;
    }
    
    public static void part_push(Part part) { allParts.add(part); }
    
    public static Part part_shift() {
        Part temp = allParts.get(0);
        allParts.remove(0);
        return temp;
    }
    
    public static void part_unshift(Part part) { allParts.add(0, part); }
    
    public static int part_size() {
        return allParts.size();
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
    
    public static int product_size() {
        return products.size();
    }
}
