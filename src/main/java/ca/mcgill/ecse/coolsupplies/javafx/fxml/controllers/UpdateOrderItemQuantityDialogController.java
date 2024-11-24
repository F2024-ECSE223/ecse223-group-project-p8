package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UpdateOrderItemQuantityDialogController {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Spinner<Integer> qtySpinner;

    @FXML
    private DialogPane dialogPane;

    //TO CONNECT WITH OLD PAGE ---------------------------------------
    private Integer selOrderID;
    private String selOrderItemName;

    public void setOrderID(Integer orderID){
        this.selOrderID = orderID;
    }

    public void setOrderItem(String itemName){
        this.selOrderItemName = itemName;
    }

    public void setQty(Integer itemQty){
        qtySpinner.getValueFactory().setValue(itemQty);
    }
    //--------------------------------------------------------------------------------

    private void finish(){
        Integer updatedQty = qtySpinner.getValue();
        String message = CoolSuppliesFeatureSet8Controller.updateQuantity(selOrderItemName, updatedQty, selOrderID);

        if(!message.equals("The item's quantity has successfully been updated")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    private void cancel(){
        dialogPane.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        assert qtySpinner != null : "fx:id=\"qtySpinner\" was not injected: check your FXML file 'UpdateOrderItemQuantityDialog.fxml'.";

        //Find Finish and Cancel buttons
        Button finishButton = (Button) dialogPane.lookupButton(ButtonType.FINISH);
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);

        //If Finish or Cancel buttons clicked, proceed to their respective actions
        if(finishButton != null){
        finishButton.setOnAction(event -> {
            finish();
        });}

        else{
            System.out.println("Finish not found");
        }

        if(cancelButton != null){
        cancelButton.setOnAction(event -> {
            cancel();
        });}

        else{
            System.out.println("Cancel not found");
        }

        //Initialize Quantity Spinner
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        qtySpinner.setValueFactory(valueFactory);
    }

}
