package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import ca.mcgill.ecse.coolsupplies.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class EdititemController {
    @FXML
    private AnchorPane ap;

    @FXML
    public Button cancelButton;

    @FXML
    private Button editButton;

    @FXML
    public TextField nameField;

    @FXML
    public TextField priceField;
   private TOItem currItem;

   public void setItem(TOItem currItem) {
       this.currItem = currItem;


   }

    //private Item currItem;

    public void initialize() {
        editButton.setOnAction(event -> handleEditButton());

        cancelButton.setOnAction(event -> {
            try {
                handleCancelButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private void handleEditButton() {
        String nameItem = nameField.getText();
        String priceItem = priceField.getText();
        //Item currItem = (Item) Item.getWithName(currentName.getText());
        if (!nameItem.isEmpty() && !priceItem.isEmpty()) {
            CoolSuppliesFeatureSet3Controller.updateItem(currItem.getName(), nameItem, Integer.parseInt(priceItem));}
    }

    private void handleCancelButton() throws IOException {
        exitWindow();
    }

    private void exitWindow() throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.hide();


    }



}