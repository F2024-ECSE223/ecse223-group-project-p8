package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.model.Item;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class EditItemController {
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

    private Item currItem;

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
        if (!nameItem.isEmpty() && !priceItem.isEmpty()) {
            CoolSuppliesFeatureSet3Controller.addItem(nameItem, Integer.parseInt(priceItem));}
        }

    private void handleCancelButton() throws IOException {
        exitWindow();
    }

    private void exitWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ItemsShop.fxml"));
        Scene scene = new Scene(loader.load());
        Stage shop = new Stage();
        shop.setScene(scene);
        shop.show();

    }



}
