package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    protected int productID;
    protected String name;
    protected int stock;
    protected double price;
    protected int min;
    protected int max;

    // CONSTRUCTOR: COMPLETE
    public Product () {
        setProductID(productID);
        setName(name);
        setStock(stock);
        setPrice(price);
        setMin(min);
        setMax(max);
    }

    // SETTERS: COMPLETE
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }

    // GETTERS: COMPLETE
    public int getProductID() {
        return productID;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }

    // OTHER FUNCTIONS: INCOMPLETE
    public void addAssociatedPart (Part associatedPart) {
        associatedParts.add(associatedPart);
    }
    public void deleteAssociatedPart(Part associatedpPart) {
        associatedParts.remove(associatedpPart);
    }
    public static ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
