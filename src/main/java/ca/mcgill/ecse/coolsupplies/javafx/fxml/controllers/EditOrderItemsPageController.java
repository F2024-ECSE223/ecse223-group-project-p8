/**
 * Sample Skeleton for 'EditOrderItemsPage.fxml' Controller Class
 */

package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller.deleteOrderItem;

/**
 * This class provides the controller methods for the Edit Order Items Page for a single order (found in the Order page, once you view a unique order)
 * This page allows users to view order items of an order, add/remove an item or update an order item's quantity
 *
 * @author Snigdha Sen
 */


public class EditOrderItemsPageController {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    @FXML
    private Button AddButton;

    @FXML
    private Button BackButton;

    @FXML
    private TableColumn<OrderItem, Integer> QuantityColumn;

    @FXML
    private Button RemoveButton;

    @FXML
    private Button accountButtonMenu;

    @FXML
    private AnchorPane ap;

    @FXML
    private Label chooseItemLabel;

    @FXML
    private ChoiceBox<String> itemDropDownMenu;

    @FXML
    private TableColumn<OrderItem, String> itemNameColumn;

    @FXML
    private Button ordersButtonMenu;

    @FXML
    private Button parentStudentButtonMenu;

    @FXML
    private Spinner<Integer> qtySpinner;

    @FXML
    private Spinner<Integer> updateQtySpinner;

    @FXML
    private Button schoolButtonMenu;

    @FXML
    private Button studentsButtonMenu;

    @FXML
    private TableView<OrderItem> table;

    @FXML
    private Button updateQtyButton;

    //TO CONNECT WITH OLD PAGE/ SCENE, VIEW ORDER WINDOW ----------------------------------------------------
    private Integer selOrderID;

    /**
     * Sets the current order whose items we want to edit from the previous "View an order" controller
     *
     * @param order Transfer object of the order whose items we want to edit
     */
    public void setCurrentOrder(TOOrder order){
        this.selOrderID = order.getNumber();
    }
    //-------------------------------------------------------------------------------------------------------

    //NAVIGATION TO OTHER PAGES

    /**
     * Go to the Account Page
     *
     * @throws IOException If there is an error loading the file
     */
    @FXML
    void goToAccountPage() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Go to the Home Page by clicking on Back.
     *
     * @throws IOException If there is an error loading the file.
     */
    @FXML
    void goToItemsPage() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
    }

    /**
     * Go to the Orders Page.
     *
     * @throws IOException If there is an error loading the file.
     */
    @FXML
    void goToOrdersPage() throws IOException {
        loadPage("ViewAllOrders.fxml");
    }

    /**
     * Go to the Parent-Student Page.
     *
     * @throws IOException If there is an error loading the file.
     */
    @FXML
    void goToParentStudentPage() throws IOException {
        loadPage("ParentStudentPage.fxml");
    }

    /**
     * Go to the School Page.
     *
     * @throws IOException If there is an error loading the file.
     */
    @FXML
    void goToSchoolPage() throws IOException {
        loadPage("/pages/ViewSchool.fxml");
    }

    /**
     * Go to the Students Page.
     *
     * @throws IOException If there is an error loading the file.
     */
    @FXML
    void goToStudentsPage() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }



    //ACTIONS

    /**
     * Updates the quantity of a selected order item in a given order.
     *
     * @throws NullPointerException If no row is selected in the table.
     */
    @FXML
    void update() {
        try {
            OrderItem selOrderItem = table.getSelectionModel().getSelectedItem();
            String itemName = selOrderItem.getItem().getName();
            Integer updatedQty = updateQtySpinner.getValue();

            String message = CoolSuppliesFeatureSet8Controller.updateQuantity(itemName, updatedQty, selOrderID);

            if(!message.equals("The item's quantity has successfully been updated")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            }

            updateTable();
            table.refresh();
        }

        //No row was selected
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No row was selected to update.");
            alert.showAndWait();
        }
    }

    /**
     * Adds an order item in a given order from a list of items and bundles and add it to the tableview.
     *
     */
    @FXML
    void add() {
        Integer qty = qtySpinner.getValue();
        String itemName = itemDropDownMenu.getValue();
        InventoryItem invItem = InventoryItem.getWithName(itemName);
        String selOrderName = String.valueOf(selOrderID);
        String message = CoolSuppliesFeatureSet8Controller.addItemToOrder(itemName, invItem, selOrderName, qty);

        if(!message.equals("The item has successfully been added.")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        else {
            updateTable();
        }
    }

    /**
     * Removes an order item in a given order and remove it from the tableview.
     *
     * @throws NullPointerException If no row is selected in the table.
     */
    @FXML
    void remove() {
        try {
            int selIndex = table.getSelectionModel().getSelectedIndex();
            OrderItem selOrderItem = table.getSelectionModel().getSelectedItem();
            String orderItemName = selOrderItem.getItem().getName();

            //selected row is valid
            if (selIndex >= 0 && selOrderItem != null) {
                deleteOrderItem(orderItemName, selOrderID.toString());
                table.getItems().remove(selOrderItem);
            }
        }

        //no row was selected --> show alert
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No row was selected to remove.");
            alert.showAndWait();
        }
    }



    //PAGE TABLE AND LOADING
    /**
     * Updates the TableView to show all order items of a given order.
     *
     */
    private void updateTable(){
        List<OrderItem> orderItems = Order.getWithNumber(this.selOrderID).getOrderItems();

        if (!orderItems.isEmpty()) {
            itemNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getName()));
            QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            table.setItems(FXCollections.observableArrayList(orderItems));

        }

        //CLEAR IF EMPTY BUNDLE SELECTED
        else {
            table.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * Loads a new page
     *
     * @param fxmlPath The relative path to the FXML file in question
     *
     * @throws IOException If there is an error loading the file
     */
    @FXML
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) AddButton.getScene().getWindow();
        currentStage.setScene(scene);
    }

    @FXML
    void initialize() {
        assert AddButton != null : "fx:id=\"AddButton\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert BackButton != null : "fx:id=\"BackButton\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert QuantityColumn != null : "fx:id=\"QuantityColumn\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert RemoveButton != null : "fx:id=\"RemoveButton\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert accountButtonMenu != null : "fx:id=\"accountButtonMenu\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert ap != null : "fx:id=\"ap\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert chooseItemLabel != null : "fx:id=\"chooseItemLabel\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert itemDropDownMenu != null : "fx:id=\"itemDropDownMenu\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert itemNameColumn != null : "fx:id=\"itemNameColumn\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert ordersButtonMenu != null : "fx:id=\"ordersButtonMenu\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert parentStudentButtonMenu != null : "fx:id=\"parentStudentButtonMenu\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert qtySpinner != null : "fx:id=\"qtySpinner\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert schoolButtonMenu != null : "fx:id=\"schoolButtonMenu\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert studentsButtonMenu != null : "fx:id=\"studentsButtonMenu\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";
        assert updateQtyButton != null : "fx:id=\"updateQtyButton\" was not injected: check your FXML file 'EditOrderItemsPage.fxml'.";

        //Initialize Quantity Spinner for Add
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        qtySpinner.setValueFactory(valueFactory);

        //Initialize Quantity Spinner for Update
        updateQtySpinner.setValueFactory(valueFactory);

        //Initialize Item Drop-down Menu for Items and Bundles
        List<Item> itemList = coolSupplies.getItems();
        for (Item item : itemList) {
            String name = item.getName();
            itemDropDownMenu.getItems().add(name);
        }

        List<GradeBundle> bundleList = coolSupplies.getBundles();
        for (GradeBundle bundle : bundleList) {
            String name = bundle.getName();
            itemDropDownMenu.getItems().add(name);
        }

        //Update TableView to Selected Order's order items
        updateTable();
    }
}