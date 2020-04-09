package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

public class ModifyProductController implements Initializable {

    /** Objects on screen **/
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    int indexOfProductSelected = MainScreenController.indexOfProductSelected();

    // Parts table 1 (top)
    @FXML private TableView<Part> partsTable1;
    @FXML private TextField searchPartInput;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvCol;
    @FXML private TableColumn<Part, Double> partCostCol;

    // Parts table 2 (bottom)
    @FXML private TableView<Part> partsTable2;
    @FXML private TableColumn<Part, Integer> partIDCol2;
    @FXML private TableColumn<Part, String> partNameCol2;
    @FXML private TableColumn<Part, Integer> partInvCol2;
    @FXML private TableColumn<Part, Double> partCostCol2;

    // Textfields
    @FXML private TextField productID;
    @FXML private TextField productName;
    @FXML private TextField productInv;
    @FXML private TextField productPrice;
    @FXML private TextField productMin;
    @FXML private TextField productMax;

    //saveProductButton
    public void saveChangesToInventory(MouseEvent event) throws IOException {

        productInventory = Inventory.getAllProducts();
        Product productSelected = productInventory.get(indexOfProductSelected);

        int productSelectedID = productSelected.getProductID();
        String productNameInput = productName.getText();
        String productInvInput = productInv.getText();
        String productPriceInput = productPrice.getText();
        String productMinInput = productMin.getText();
        String productMaxInput = productMax.getText();

        productSelected.setProductID(productSelectedID);
        productSelected.setName(productNameInput);
        productSelected.setStock(Integer.parseInt(productInvInput));
        productSelected.setPrice(Double.parseDouble(productPriceInput));
        productSelected.setMin(Integer.parseInt(productMinInput));
        productSelected.setMax(Integer.parseInt(productMaxInput));

        Inventory.updateProduct(productSelected);
        System.out.println("Product modified added!");

        Parent save = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
        Scene scene = new Scene(save);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //cancelButton
    @FXML
    public void returnToMain(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // go to main screen
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            //stay on original screen
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/modifyProduct.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    //searchPartButton
    public void searchPart(MouseEvent event) throws IOException {
        String input = searchPartInput.getText();
        int indexOfPart = -1;
        if (Inventory.lookupPart(input) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part not found");
            alert.setHeaderText("Please try search again");
            alert.showAndWait();
        } else if (input==null || input.isEmpty()){
            partsTable1.setItems(partInventory);
        } else {
            indexOfPart = Inventory.lookupPart(input);
            Part temp = Inventory.getAllParts().get(indexOfPart);
            ObservableList<Part> tempParts = FXCollections.observableArrayList();
            tempParts.add(temp);
            partsTable1.setItems(tempParts);
        }
    }

    //addPartButton
    public void addToPartsTable2(MouseEvent event) {
        Part newAssociatedPart = partsTable1.getSelectionModel().getSelectedItem();
        associatedPartsList.add(newAssociatedPart);
        System.out.println("Part has been associated with this product!");
        System.out.println();
    }

    //deletePartButton
    public void deleteFromPartsTable2(MouseEvent event) {
        Part associatedPart = partsTable2.getSelectionModel().getSelectedItem();
        associatedPartsList.remove(associatedPart);
        System.out.println("Part has been removed from this product!");
        System.out.println();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Fill textfields with product info
        productInventory = Inventory.getAllProducts();
        Product product = productInventory.get(indexOfProductSelected);

        // Check
        System.out.println("Index of selected product to modify: " + MainScreenController.indexOfProductSelected());

        productID.setText("AUTO GEN: " + product.getProductID());
        productName.setText("" + product.getName());
        productInv.setText("" + product.getStock());
        productPrice.setText("" + product.getPrice());
        productMin.setText("" + product.getMin());
        productMax.setText("" + product.getMax());

        // Display parts (top)
        if (Inventory.getAllParts() != null) {
            partInventory = Inventory.getAllParts();
            partsTable1.setItems(partInventory);
            partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        // Display parts associated with product (bottom)
        if (Product.getAllAssociatedParts() != null) {
            associatedPartsList = Product.getAllAssociatedParts();
            partsTable2.setItems(associatedPartsList);
            partIDCol2.setCellValueFactory(new PropertyValueFactory<>("partID"));
            partNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partCostCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }
}
