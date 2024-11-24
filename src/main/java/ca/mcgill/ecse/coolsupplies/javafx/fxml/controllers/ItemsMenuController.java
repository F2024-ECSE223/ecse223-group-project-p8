package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

import static ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller.deleteItem;

/**
 * FXML controller for the items menu
 * Sets up the table to view all the items, adds an items, edits an item, and remove it
 * @author Artimice Mirchi
 */
public class ItemsMenuController {
    @FXML public Button addButton;

    @FXML public Button editButton;
    @FXML
    public TableView<TOItem> itemDisplay;
    @FXML public TableColumn<Item, String> nameColumn;
    @FXML public TableColumn<Item, String> priceColumn;
    @FXML public Button bundlesButton;
    @FXML public Button shopButton;
    @FXML public Button removeButton;
    @FXML public Button profileButton;
    @FXML private Label resultLabel;
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    TOItem selectedItem;


    @FXML
    void initialize () {
        updateTable();
        assert addButton != null :  "fx:id=\"addButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert editButton != null :  "fx:id=\"editButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert itemDisplay != null :  "fx:id=\"itemDisplay\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert nameColumn != null :  "fx:id=\"nameColumn\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert priceColumn != null :  "fx:id=\"priceColumn\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert bundlesButton != null :  "fx:id=\"bundlesButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert shopButton != null :  "fx:id=\"shopButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert removeButton != null :  "fx:id=\"removeButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        profileButton.setOnAction(event -> {
            try {
                handleProfileButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        removeButton.setOnAction(event -> handleRemoveButton());

        bundlesButton.setOnAction(event -> {
            try {
                handleBundleButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        shopButton.setOnAction(event -> {
            try {
                handleShopButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });



        addButton.setOnAction(event -> {
            try {
                handleAddButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        editButton.setOnAction(event -> {
            try {
                handleEditButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }
    @FXML
    private void handleProfileButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewAccountsPage.fxml"));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) bundlesButton.getScene().getWindow();
        currentStage.setScene(scene);
    }

    private void updateTable() {
        List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();
        if (items.isEmpty() == false) {
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            itemDisplay.setItems(FXCollections.observableArrayList(items));
        }
        else {
            itemDisplay.setItems(FXCollections.observableArrayList());

        }


    }
    @FXML
    private void handleRemoveButton() {
        //try{
        int index = itemDisplay.getSelectionModel().getSelectedIndex();
        TOItem selItem = itemDisplay.getSelectionModel().getSelectedItem();
        String itemName = selItem.getName();

        if (index >= 0 && selItem != null) {
            deleteItem(itemName);
            itemDisplay.getItems().remove(selItem);
        }
    }

    @FXML
    private void handleShopButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ShopPage.fxml"));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) resultLabel.getScene().getWindow();
        currentStage.setScene(scene);


    }

    @FXML
    private void handleBundleButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/Bundles.fxml"));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) bundlesButton.getScene().getWindow();
        currentStage.setScene(scene);

    }

    @FXML
    private void handleAddButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AddItemPopup.fxml"));
        Scene popUp = new Scene(loader.load());
        Stage popUp1 = new Stage();
        popUp1.setScene(popUp);
        popUp1.showAndWait();
        updateTable();

    }

    public void handleEditButton() throws IOException {
        try {
            int index = itemDisplay.getSelectionModel().getSelectedIndex();
            TOItem currItem = itemDisplay.getSelectionModel().getSelectedItem();

            if (index >= 0 && currItem != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/EditItemsPopup.fxml"));
                Scene popUp = new Scene(loader.load());
                EdititemController controller = loader.getController();
                controller.setItem(currItem);
                Stage popUp1 = new Stage();
                popUp1.setScene(popUp);
                popUp1.showAndWait();
                updateTable();
            }
        }
        catch (NullPointerException e) {


        }

    }

    private void exitWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ItemsShop.fxml"));
        Scene scene = new Scene(loader.load());
        Stage shop = new Stage();
        shop.setScene(scene);
        shop.show();

    }
}