package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.model.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

//for the additem popup
public class AddItemController {
    @FXML public TextField price;
    @FXML private AnchorPane ap;
    @FXML public Button cancelButton;
    @FXML public Button addButton;
    @FXML public TextField nameField;
    @FXML public TextField priceField;

    private Item currItem;

    @FXML
    private URL location;

    @FXML
    public void initialize() {
        addButton.setOnAction(event -> handleAddButton());

        cancelButton.setOnAction(event -> {
            try {
                handleCancelButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    void goToBundlesPage() throws IOException {
        loadPage

    }

    @FXML
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) chooseItemLabel.getScene().getWindow();
        currentStage.setScene(scene);
    }

    @FXML
    private void handleAddButton() {
        String nameItem = nameField.getText();
        String priceItem = priceField.getText();
        if (!nameItem.isEmpty() && !priceItem.isEmpty()) {
        CoolSuppliesFeatureSet3Controller.addItem(nameItem, Integer.parseInt(priceItem));}
        //exitWindow();
    }

    @FXML
    private void handleCancelButton() throws IOException {
        exitWindow();


    }

    private void exitWindow() throws IOException {
       Stage stage = (Stage) cancelButton.getScene().getWindow();
       stage.close();

    }

}

//tableview: cant edit the fields within each row, but u can sleect a row, can click a row, click the button, can do whatever there
