package Model;

import View_Controller.MainScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;

import java.util.List;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public Inventory() {}

    public static void addPart(Part newPart) {
        try {
            if((newPart.getMin() > newPart.getMax()) || (newPart.getStock() > newPart.getMax()) || (newPart.getStock() < newPart.getMin())) {
                throw new Exception();
            } else {
                allParts.add(newPart);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please verify your entry");
            alert.setContentText("Minimum must be less than the maximum amount" +
                    "and the inventory number must be in the range of minimum and maximum amount.");
            alert.showAndWait();
            }
        }

    public static void addProduct(Product newProduct) {
        try {
            if(newProduct.getMin() > newProduct.getMax() /** || newProduct.getPrice() < SUM OF ALL PARTS*/) {
                throw new Exception();
            } else {
                allProducts.add(newProduct);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Inventory minimum and maximum");
            alert.setContentText("Please verify that the minimum is less than or equal to the maximum and try again");
            alert.showAndWait();
        }
    }

    public static int lookupPart(String input) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(input)) {
            for (int i = 0; i < allParts.size(); i++) {
                if (Integer.parseInt(input) == allParts.get(i).getPartID()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (input.equals(allParts.get(i).getName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound = true) {
            return index;
        } else {
            System.out.println("Part not found");
            return -1;
        }
    }

    public static int lookupProduct(String input) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(input)) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (Integer.parseInt(input) == allProducts.get(i).getProductID()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (input.equals(allProducts.get(i).getName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound = true) {
            return index;
        } else {
            System.out.println("Product not found");
            return -1;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void updatePart(Part selectedPart) {
        int indexOfPartSelected = MainScreenController.indexOfPartSelected();
        allParts.set(indexOfPartSelected, selectedPart);
    }
    public static void updateProduct(Product selectedProduct) {
        int indexOfProductSelected = 0;
        allProducts.set(indexOfProductSelected, selectedProduct);
    }
    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }
    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
