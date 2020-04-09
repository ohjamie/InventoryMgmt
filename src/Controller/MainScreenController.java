package View_Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Model.Part;
import Model.Product;
import Model.Inventory;

public class MainScreenController implements Initializable {

    // Parts table
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partCostCol;

    // Product table
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Part, Integer> prodIDCol;
    @FXML private TableColumn<Part, String> prodNameCol;
    @FXML private TableColumn<Part, Integer> prodInvCol;
    @FXML private TableColumn<Part, Double> prodCostCol;

    // Textfields
    @FXML private TextField searchPartInput;
    @FXML private TextField searchProductInput;

    public static int partSelectedIndex;
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();

    public static int productSelectedIndex;
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();

    // exitButton
    public void quitProgram(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Quit program");
        alert.setContentText("Are you sure you want to quit the program?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    // lookupPartButton
    @FXML
    public void searchPart(MouseEvent event) throws IOException {
        String input = searchPartInput.getText();
        int indexOfPart = -1;
        if (Inventory.lookupPart(input) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part not found");
            alert.setHeaderText("Please try search again");
            alert.showAndWait();
        } else if (input==null || input.isEmpty()){
            partsTable.setItems(partInventory);
        } else {
            indexOfPart = Inventory.lookupPart(input);
            Part temp = Inventory.getAllParts().get(indexOfPart);
            ObservableList<Part> tempParts = FXCollections.observableArrayList();
            tempParts.add(temp);
            partsTable.setItems(tempParts);
        }
    }

    // addPartButton
    @FXML
    public void MainToAddPart(MouseEvent event) throws IOException {
        Parent addPart = FXMLLoader.load(getClass().getResource("/View_Controller/addPart.fxml"));
        Scene scene = new Scene(addPart);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    // modifyPartButton (open new screen)
    @FXML
    public void MainToModifyPart(MouseEvent event) throws IOException {
        Part partSelected = partsTable.getSelectionModel().getSelectedItem();
        partSelectedIndex = Inventory.getAllParts().indexOf(partSelected);

        Parent modifyProduct = FXMLLoader.load(getClass().getResource("/View_Controller/modifyPart.fxml"));
        Scene scene = new Scene(modifyProduct);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    // return index location of selected part
    public static int indexOfPartSelected() {
        return partSelectedIndex;
    }

    // deletePartButton
    @FXML
    public void deletePart(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("Are you sure you want to delete this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part partSelected = partsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(partSelected);
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    // lookupProductButton (search)
    @FXML
    public void searchProduct(MouseEvent event) {
        String input = searchProductInput.getText();
        int indexOfProduct = -1;
        if (Inventory.lookupProduct(input) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product not found");
            alert.setHeaderText("Please try search again");
            alert.showAndWait();
        } else if (input==null || input.isEmpty()){
            productsTable.setItems(productInventory);
        } else {
            indexOfProduct = Inventory.lookupProduct(input);
            Product temp = Inventory.getAllProducts().get(indexOfProduct);
            ObservableList<Product> tempProducts = FXCollections.observableArrayList();
            tempProducts.add(temp);
            productsTable.setItems(tempProducts);
        }
    }

    // addProductButton (open new screen)
    @FXML
    public void MainToAddProduct(MouseEvent event) throws IOException {
        Parent addProduct = FXMLLoader.load(getClass().getResource("/View_Controller/addProduct.fxml"));
        Scene scene = new Scene(addProduct);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    // modifyProductButton (open new screen)
    @FXML
    public void MainToModifyProduct(MouseEvent event) throws IOException {
        Product productSelected = productsTable.getSelectionModel().getSelectedItem();
        productSelectedIndex = Inventory.getAllProducts().indexOf(productSelected);

        Parent modifyProduct = FXMLLoader.load(getClass().getResource("/View_Controller/modifyProduct.fxml"));
        Scene scene = new Scene(modifyProduct);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    // return index location of selected product
    public static int indexOfProductSelected() {
        return productSelectedIndex;
    }

    // deleteProductButton (delete on main screen)
    @FXML
    public void deleteProduct(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("Are you sure you want to delete this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Product productSelected = productsTable.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(productSelected);
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Display parts
        if (Inventory.getAllParts() != null) {
            partInventory = Inventory.getAllParts();
            partsTable.setItems(partInventory);
            partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        // Display products
        if (Inventory.getAllProducts() != null) {
            productInventory = Inventory.getAllProducts();
            productsTable.setItems(productInventory);
            prodIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
            prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            prodCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        // For checks
        System.out.println("Size of Parts array: " + partInventory.size());
        System.out.println("Array contents: " + Inventory.getAllParts());
        System.out.println("Size of Products array: " + productInventory.size());
        System.out.println("Array contents: " + Inventory.getAllProducts());
        System.out.println();
    }
}