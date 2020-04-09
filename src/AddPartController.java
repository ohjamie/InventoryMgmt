package View_Controller;

import Model.Outsourced;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.InHouse;
import Model.Inventory;
import Model.Part;


public class AddPartController implements Initializable {

    /** Objects on screen **/
    @FXML private RadioButton InHouseButton;
    @FXML private RadioButton OutsourcedButton;
    @FXML private TextField partID;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMin;
    @FXML private TextField partMax;
    @FXML private Label partLabel;
    @FXML private TextField partLabelTextfield;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();

    /** O U T E R   C O N T A I N E R **/
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
    public void addPart(MouseEvent event) throws IOException {
        String partIDinput = partID.getText();
        String partNameInput = partName.getText();
        String partInvInput = partInv.getText();
        String partPriceInput = partPrice.getText();
        String partMinInput = partMin.getText();
        String partMaxInput = partMax.getText();
        String partLabelTextfieldInput = partLabelTextfield.getText();

        if (InHouseButton.isSelected()) {
            InHouse newInHousePart = new InHouse();

            newInHousePart.setPartID(Integer.parseInt(partIDinput));
            newInHousePart.setName(partNameInput);
            newInHousePart.setPrice(Double.parseDouble(partPriceInput));
            newInHousePart.setStock(Integer.parseInt(partInvInput));
            newInHousePart.setMin(Integer.parseInt(partMinInput));
            newInHousePart.setMax(Integer.parseInt(partMaxInput));
            newInHousePart.setMachineID(Integer.parseInt(partLabelTextfieldInput));

            Inventory.addPart(newInHousePart);
        }
        if (OutsourcedButton.isSelected()){
            Outsourced newOutsourcedPart = new Outsourced();

            newOutsourcedPart.setPartID(Integer.parseInt(partIDinput));
            newOutsourcedPart.setName(partNameInput);
            newOutsourcedPart.setPrice(Double.parseDouble(partPriceInput));
            newOutsourcedPart.setStock(Integer.parseInt(partInvInput));
            newOutsourcedPart.setMin(Integer.parseInt(partMinInput));
            newOutsourcedPart.setMax(Integer.parseInt(partMaxInput));
            newOutsourcedPart.setCompanyName(partLabelTextfieldInput);

            Inventory.addPart(newOutsourcedPart);
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
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            Parent addPart = FXMLLoader.load(getClass().getResource("/View_Controller/addPart.fxml"));
            Scene scene = new Scene(addPart);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partInventory = Inventory.getAllParts();
        int partIDnumber = partInventory.size() + 1;
        partID.setText("" + partIDnumber);
    }
}