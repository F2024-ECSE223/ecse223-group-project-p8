package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddBundleItemDialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ChoiceBox<String> itemDropDown;

    @FXML
    private ChoiceBox<String> purchaseLvlDropDown;

    @FXML
    private Spinner<Integer> qtySpinner;

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    private String selBundleName;

    public void setBundleName(String name){
        this.selBundleName = name;
    }

    private void cancel(){
        dialogPane.getScene().getWindow().hide();
    }

    private void finish(){
        String lvl = purchaseLvlDropDown.getValue();
        Integer qty = qtySpinner.getValue();
        String itemName = itemDropDown.getValue();
        String message = CoolSuppliesFeatureSet5Controller.addBundleItem(qty, lvl, itemName, selBundleName);

        if(!message.equals("The bundle item has been succesfully added")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        assert itemDropDown != null : "fx:id=\"itemDropDown\" was not injected: check your FXML file 'AddBundleItemDialog.fxml'.";
        assert purchaseLvlDropDown != null : "fx:id=\"purchaseLvlDropDown\" was not injected: check your FXML file 'AddBundleItemDialog.fxml'.";
        assert qtySpinner != null : "fx:id=\"qtySpinner\" was not injected: check your FXML file 'AddBundleItemDialog.fxml'.";

        //Find Finish and Cancel buttons
        Button finishButton = (Button) dialogPane.lookupButton(ButtonType.FINISH);
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);

        //If Finish or Cancel buttons clicked, proceed to their respective actions
        finishButton.setOnAction(event -> {
            finish();
        });

        cancelButton.setOnAction(event -> {
            cancel();
        });


        //Initialize Quantity Spinner
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        qtySpinner.setValueFactory(valueFactory);

        //Initialize Drop-down Menu for Items
        List<Item> itemList = coolSupplies.getItems();
        for (Item item : itemList) {
            String name = item.getName();
            itemDropDown.getItems().add(name);
        }

        //Initialize Drop-down Menu for Purchase Level
        purchaseLvlDropDown.getItems().add("Mandatory");
        purchaseLvlDropDown.getItems().add("Recommended");
        purchaseLvlDropDown.getItems().add("Optional");
    }

}
