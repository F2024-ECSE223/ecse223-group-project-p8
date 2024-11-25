package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.io.IOException;
import java.util.List;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet3Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
* Controller for adding, updating quantity, and deleting items in an order.
*
*@author Jiatian Liu
*@author Snigdha Sen
*/
public class EditOrderItemsPageController {
    @FXML
    private Button AddButton;

    @FXML
    private Button BackButton;

    @FXML
    private TableColumn<TOOrderItem, Integer> QuantityColumn;

    @FXML
    private Button RemoveButton;

    @FXML
    private ChoiceBox<String> itemDropDownMenu;

    @FXML
    private TableColumn<TOOrderItem, String> itemNameColumn;

    @FXML
    private Spinner<Integer> qtySpinner;

    @FXML
    private Spinner<Integer> newQtySpinner;

    @FXML
    private TableView<TOOrderItem> table;

    @FXML
    private Button updateQtyButton;

    // TO CONTINUE AND TO CONNECT WITH OLD PAGES 
    private TOOrder selOrder;
    private Integer selOrderID;

    public void setCurrentOrder(TOOrder order) {
        this.selOrder = order;
        this.selOrderID = order.getNumber();
        updateTable(selOrderID);
    }

    // NAVIGATION TO OTHER PAGES
    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewOrderWindow.fxml"));
        Scene scene = new Scene(loader.load());

        ViewOrderWindowController viewOrderController= loader.getController();
        Stage stage = (Stage) BackButton.getScene().getWindow();
        viewOrderController.setCurrentOrder(CoolSuppliesFeatureSet8Controller.viewOrder(String.valueOf(selOrder.getNumber())));
        stage.setScene(scene);
    }

    /**
    * Adds an item to the order.
    *
    *@author Jiatian Liu
    *@author Snigdha Sen
    */
    @FXML
    void add(ActionEvent event) {
        Integer qty = qtySpinner.getValue();
        String itemName = itemDropDownMenu.getValue();

        if (itemName == null) {
            showAlert("Error", "Please select an item to add.");
            return;
        }

        String msg = CoolSuppliesFeatureSet8Controller.addItemToOrder(itemName, String.valueOf(selOrderID), qty);

        showAlert("", msg);
        itemDropDownMenu.setValue(null);
        qtySpinner.getValueFactory().setValue(1);
        newQtySpinner.getValueFactory().setValue(1);
        updateTable(selOrderID); 
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
    * Removes the selected item from the order.
    *
    *@author Jiatian Liu
    *@author Snigdha Sen
    */
    @FXML
    void remove(ActionEvent event) {
        TOOrderItem selOrderItem = table.getSelectionModel().getSelectedItem();

        if (selOrderItem == null) {
            showAlert("Error", "Please select an item to delete.");
            return;
        }
        
        String msg = CoolSuppliesFeatureSet8Controller.deleteOrderItem(selOrderItem.getItemName(), String.valueOf(selOrderID));
        
        showAlert("", msg);
        itemDropDownMenu.setValue(null);
        qtySpinner.getValueFactory().setValue(1);
        newQtySpinner.getValueFactory().setValue(1);
        updateTable(selOrderID); 
    }

    /**
    * Updates the quantity of the selected item in the order.
    *
    *@author Jiatian Liu
    *@author Snigdha Sen
    */
    @FXML
    void updateQty(ActionEvent event) {
        TOOrderItem selOrderItem = table.getSelectionModel().getSelectedItem();
        Integer newQty = newQtySpinner.getValue();

        if (selOrderItem == null) {
            showAlert("Error", "Please select an item to update quantity.");
            return;
        }
        
        String msg = CoolSuppliesFeatureSet8Controller.updateQuantity(selOrderItem.getItemName(), newQty, selOrderID);
        
        showAlert("", msg);
        itemDropDownMenu.setValue(null);
        qtySpinner.getValueFactory().setValue(1);
        newQtySpinner.getValueFactory().setValue(1);
        updateTable(selOrderID); 
    }

    /**
    * Updates the TableView with the items from the specified order.
    *
    *@author Jiatian Liu
    *@author Snigdha Sen
    */
    private void updateTable(Integer orderID) {
        ObservableList<TOOrderItem> itemList = FXCollections.observableArrayList();
        List<TOOrderItem> orderItems = CoolSuppliesFeatureSet8Controller.getTemporaryItemList(String.valueOf(orderID));

        itemList.addAll(orderItems);
        table.setItems(itemList);
    }

    @FXML
    void initialize() {
        // Initialize Quantity Spinners
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        qtySpinner.setValueFactory(valueFactory);
        newQtySpinner.setValueFactory(valueFactory);

        // Initialize Item Drop-down Menu for Items and Bundles
        List<TOItem> itemList = CoolSuppliesFeatureSet3Controller.getItems();
        List<TOGradeBundle> bundleList = CoolSuppliesFeatureSet4Controller.getBundles();

        for (TOItem item : itemList) {
            itemDropDownMenu.getItems().add(item.getName());
        }

        for (TOGradeBundle bundle : bundleList) {
            itemDropDownMenu.getItems().add(bundle.getName());
        }

        // Initialize TableView 
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

}