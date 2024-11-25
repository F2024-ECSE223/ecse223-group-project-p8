package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


/**
 * FXML controller class to add an item by setting a name and a price
 * @author Artimice Mirchi
 */
public class AddItemController {
    @FXML public TextField price;
    @FXML private AnchorPane ap;
    @FXML public Button cancelButton;
    @FXML public Button addButton;
    @FXML public TextField nameField;
    @FXML public TextField priceField;

    @FXML
    private URL location;
    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    public void initialize() {
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'AddItemPopup.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'AddItemPopup.fxml'.";
        assert ap != null : "fx:id=\"ap\" was not injected: check your FXML file 'AddItemPopup.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'AddItemPopup.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'AddItemPopup.fxml'.";
        assert priceField != null : "fx:id=\"priceField\" was not injected: check your FXML file 'AddItemPopup.fxml'.";

        addButton.setOnAction(event -> {
                handleAddButton();
        });

        cancelButton.setOnAction(event -> {
            try {
                handleCancelButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }


    @FXML
    private void handleAddButton() {
        String nameItem = nameField.getText();
        String priceItem = priceField.getText();
        if (!nameItem.isEmpty() && !priceItem.isEmpty()) {
            String message = CoolSuppliesFeatureSet3Controller.addItem(nameItem, Integer.parseInt(priceItem));

            if(!message.equals("The item has been successfully added")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.show();

            }

    }}

    @FXML
    private void handleCancelButton() throws IOException {
        exitWindow();


    }

    private void exitWindow() throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.hide();

    }
}