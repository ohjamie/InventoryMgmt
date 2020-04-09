package View_Controller;

import Model.*;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import View_Controller.MainScreenController;
import java.lang.*;

public class ModifyPartController implements Initializable {

    /** Objects on screen **/
    @FXML private RadioButton InHouseButton;
    @FXML private RadioButton OutsourcedButton;
    @FXML private TextField partID;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMin;
    @FXML private TextField partMax;
    @FXML private TextField partLabelTextfield;
    @FXML private Label partLabel;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private int indexOfPartSelected = MainScreenController.indexOfPartSelected();

    // InHouse
    @FXML
    public void InHouseSelected(MouseEvent event) {
        OutsourcedButton.setSelected(false);
        partLabel.setText("Machine ID");
    }

    // Outsourced
    @FXML
    public void OutsourcedSelected(MouseEvent event) {
        InHouseButton.setSelected(false);
        partLabel.setText("Company Name");
    }

    // saveButton
    @FXML
    public void modifyPart(MouseEvent event) throws IOException {
        String partNameInput = partName.getText();
        String partInvInput = partInv.getText();
        String partPriceInput = partPrice.getText();
        String partMinInput = partMin.getText();
        String partMaxInput = partMax.getText();
        String partLabelTextfieldInput = partLabelTextfield.getText();

        partInventory = Inventory.getAllParts();
        Part partSelected = partInventory.get(indexOfPartSelected);

        // inhouse to inhouse
        if (InHouseButton.isSelected() && (partSelected instanceof InHouse)) {
            int partSelectedID = partSelected.getPartID();
            partSelected.setPartID(partSelectedID);
            partSelected.setName(partNameInput);
            partSelected.setStock(Integer.parseInt(partInvInput));
            partSelected.setPrice(Double.parseDouble(partPriceInput));
            partSelected.setMin(Integer.parseInt(partMinInput));
            partSelected.setMax(Integer.parseInt(partMaxInput));
            ((InHouse) partSelected).setMachineID(Integer.parseInt(partLabelTextfieldInput));

            Inventory.updatePart(partSelected);
        } // CAST: outsourced to inhouse
        else if (InHouseButton.isSelected() && (partSelected instanceof Outsourced)) {
            InHouse part = new InHouse();

            int partSelectedID = partSelected.getPartID();
            part.setPartID(partSelectedID);
            part.setName(partNameInput);
            part.setStock(Integer.parseInt(partInvInput));
            part.setPrice(Double.parseDouble(partPriceInput));
            part.setMin(Integer.parseInt(partMinInput));
            part.setMax(Integer.parseInt(partMaxInput));
            part.setMachineID(Integer.parseInt(partLabelTextfieldInput));

            Inventory.updatePart(part);
        } // outsourced to outsourced
        else if (OutsourcedButton.isSelected() && (partSelected instanceof Outsourced)){
            int partSelectedID = partSelected.getPartID();
            partSelected.setPartID(partSelectedID);
            partSelected.setName(partNameInput);
            partSelected.setStock(Integer.parseInt(partInvInput));
            partSelected.setPrice(Double.parseDouble(partPriceInput));
            partSelected.setMin(Integer.parseInt(partMinInput));
            partSelected.setMax(Integer.parseInt(partMaxInput));
            ((Outsourced) partSelected).setCompanyName(partLabelTextfieldInput);

            Inventory.updatePart(partSelected);
        } // CAST: inhouse to outsourced
        else if (OutsourcedButton.isSelected() && (partSelected instanceof InHouse)) {
            Outsourced part = new Outsourced();

            int partSelectedID = partSelected.getPartID();
            part.setPartID(partSelectedID);
            part.setName(partNameInput);
            part.setStock(Integer.parseInt(partInvInput));
            part.setPrice(Double.parseDouble(partPriceInput));
            part.setMin(Integer.parseInt(partMinInput));
            part.setMax(Integer.parseInt(partMaxInput));
            part.setCompanyName(partLabelTextfieldInput);

            Inventory.updatePart(part);
        }
        Parent save = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
        Scene scene = new Scene(save);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    // cancelButton
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
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/modifyPart.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Get part selected from inventory
        partInventory = Inventory.getAllParts();
        Part part = partInventory.get(indexOfPartSelected);

        // Check
        System.out.println("Index of selected part to modify: " + MainScreenController.indexOfPartSelected());

        // From part object selected, extract object contents as strings
        partID.setText("AUTO GEN: " + part.getPartID());
        partName.setText("" + part.getName());
        partInv.setText("" + part.getStock());
        partPrice.setText("" + part.getPrice());
        partMin.setText("" + part.getMin());
        partMax.setText("" + part.getMax());

        if (part instanceof InHouse) {
            InHouseButton.setSelected(true);
            partLabelTextfield.setText("" + ((InHouse) part).getMachineID());
        } else {
            OutsourcedButton.setSelected(true);
            partLabelTextfield.setText(((Outsourced) part).getCompanyName());
        }
    }
}
