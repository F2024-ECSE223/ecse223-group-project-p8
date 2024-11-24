package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOBundleItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UpdateBundleItemDialogController {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ChoiceBox<String> purchaseLvlDropDown;

    @FXML
    private Spinner<Integer> qtySpinner;

    private TOBundleItem selBundleItem;

    @FXML
    public void setBundleItem(TOBundleItem selBundleItem){
        this.selBundleItem = selBundleItem;
        purchaseLvlDropDown.setValue(selBundleItem.getLevel());
        qtySpinner.getValueFactory().setValue(selBundleItem.getQuantity());
    }

    private void finish(){
        String updatedLvl = purchaseLvlDropDown.getValue();
        Integer updatedQty = qtySpinner.getValue();
        CoolSuppliesFeatureSet5Controller.updateBundleItem(selBundleItem.getItemName(), selBundleItem.getGradeBundleName(), updatedQty, updatedLvl);
    }

    private void cancel(){
        dialogPane.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
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
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1); // Min 1, Max Integer.MAX_VALUE, Initial 1
        qtySpinner.setValueFactory(valueFactory);


        //Initialize Drop-down Menu for Purchase Level
        purchaseLvlDropDown.getItems().add("Mandatory");
        purchaseLvlDropDown.getItems().add("Recommended");
        purchaseLvlDropDown.getItems().add("Optional");
    }

}
