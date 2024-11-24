package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

//import static ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller.deleteItem;

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



    //private final ObservableList<Item> items = FXCollections.observableArrayList();
    //private final TOItem<Item> items =

    @FXML private Label resultLabel;
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    TOItem selectedItem;
    //TOParent selectedParent;
    //List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
    //List<TOStudent> students;
    //gridpane, pane, button, vbox,

   /* private void populateItems() {
        List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();
        TableColumn<Item, String> nameColumn = null;
        TableColumn<Item, String> priceColumn = null;
        for (TOItem toItem : items) {
            Item item = (Item) Item.getWithName(toItem.getName());
            //TableColumn<Item, String> nameColumn = createNameItem(item);
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject().asString());
        }
       // nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        //priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject().asString());

    }*/

    /*private TableColumn<Item, String> createNameItem(Item item) {
        TableColumn<Item, String> nameColumn = new TableColumn<>();

    }*/

    void initialize () {
        //List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();

       // populateItems();
        //nameColumn.setCellValueFactory (cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));

        assert addButton != null :  "fx:id=\"addButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert editButton != null :  "fx:id=\"editButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert itemDisplay != null :  "fx:id=\"itemDisplay\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert nameColumn != null :  "fx:id=\"nameColumn\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert priceColumn != null :  "fx:id=\"priceColumn\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert bundlesButton != null :  "fx:id=\"bundlesButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert shopButton != null :  "fx:id=\"shopButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";
        assert removeButton != null :  "fx:id=\"removeButton\" was not injected: check your FXML file 'ItemsShop.fxml'.";

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

        updateTable();


    }

    private void updateTable() {
        List<TOItem> items = CoolSuppliesFeatureSet3Controller.getItems();
        if (!items.isEmpty()) {
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            itemDisplay.setItems(FXCollections.observableArrayList(items));
           // table.setItems(FXCollections.observableArrayList(bundles));
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
            System.out.println(3);

            if (index >= 0 && selItem != null) {
                deleteItem(itemName);
                itemDisplay.getItems().remove(selItem);
                System.out.println(2);
                //updateTable();

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BundleItemsPage.fxml"));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) resultLabel.getScene().getWindow();
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