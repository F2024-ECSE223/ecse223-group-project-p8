/**
 * Sample Skeleton for 'EditOrderItemsPage.fxml' Controller Class
 */

package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.io.IOException;
import java.util.List;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import ca.mcgill.ecse.coolsupplies.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller.deleteOrderItem;

public class EditOrderItemsPageController {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    @FXML
    private Button AddButton;

    @FXML
    private Button BackButton;

    @FXML
    private TableColumn<TOOrderItem, Integer> QuantityColumn;

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
    private TableColumn<TOOrderItem, String> itemNameColumn;

    @FXML
    private Button ordersButtonMenu;

    @FXML
    private Button parentStudentButtonMenu;

    @FXML
    private Spinner<Integer> qtySpinner;

    @FXML
    private Button schoolButtonMenu;

    @FXML
    private Button studentsButtonMenu;

    @FXML
    private TableView<TOOrderItem> table;

    @FXML
    private Button updateQtyButton;

    //TO CONNECT WITH OLD PAGES ---------------------------------------
    private TOOrder selOrder;
    private Integer selOrderID;

    public void setCurrentOrder(TOOrder order){
        this.selOrder = order;
        setOrderName(String.valueOf(order.getNumber()));
    }

    public void setOrderName(String orderName){
        this.selOrderID = Integer.parseInt(orderName);
    }
    //--------------------------------------------------------------------------------


    // NAVIGATION TO OTHER PAGES
    @FXML
    void goToAccountPage() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    void goToItemsPage() throws IOException {
        loadPage("/pages/AddItems.fxml");
    }

    @FXML
    void goToOrdersPage() throws IOException {
        loadPage("ViewAllOrders.fxml");
    }

    @FXML
    void goToParentStudentPage() throws IOException {
        loadPage("ParentStudentPage.fxml");
    }

    @FXML
    void goToSchoolPage() throws IOException {
        loadPage("/pages/ViewSchool.fxml");
    }

    @FXML
    void goToStudentsPage() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    //Go to UpdateOrderItemDialog Page
    @FXML
    void goToUpdateQtyDialog() throws IOException {
        String fxmlPath = "/pages/UpdateOrderItemQuantityDialog.fxml";

        try {
            int selIndex = table.getSelectionModel().getSelectedIndex();
            TOOrderItem selOrderItem = table.getSelectionModel().getSelectedItem();

            if (selIndex >= 0 && selOrderItem != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                DialogPane updateOrderItemQtyDialogPane = loader.load();

                //Pass down OrderID, Order Item and Quantity we want to update
                UpdateOrderItemQuantityDialogController controller = loader.getController();
                controller.setOrderID(this.selOrderID);
                controller.setOrderItem(selOrderItem.getItemName());
                Order order = Order.getWithNumber(this.selOrderID);
                List<OrderItem> orderItems = order.getOrderItems();

                for(OrderItem orderItem : orderItems){
                    if(orderItem.getItem().getName().equals(selOrderItem.getItemName())){
                        controller.setQty(orderItem.getQuantity());
                    }
                }

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(updateOrderItemQtyDialogPane);
                dialog.showAndWait();
                updateTable(this.selOrderID);
            }
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


    //Add Order Item from an Order and add it to the table
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
            updateTable(selOrderID); //or just add to table?????
        }
    }

    //Remove Order Item from an Order and remove it from the table
    @FXML
    void remove() {
        try {
            int selIndex = table.getSelectionModel().getSelectedIndex();
            TOOrderItem selOrderItem = table.getSelectionModel().getSelectedItem();
            String orderItemName = selOrderItem.getItemName();

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

    //Update TableView
    private void updateTable(Integer orderID){
        List<TOOrderItem> orderItems = selOrder.getItems();

        if (!orderItems.isEmpty()) {
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            table.setItems(FXCollections.observableArrayList(orderItems));

        }

        //CLEAR IF EMPTY BUNDLE SELECTED
        else {
            table.setItems(FXCollections.observableArrayList());
        }
    }

    //load new page
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

        //Initialize Quantity Spinner
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        qtySpinner.setValueFactory(valueFactory);

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
        updateTable(selOrderID);
    }
}