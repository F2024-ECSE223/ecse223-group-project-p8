package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Controller for managing and displaying all orders in the CoolSupplies system.
 * This class provides functionalities to view, manage, and navigate through order-related pages. It interacts with the
 * CoolSupplies backend to fetch and display orders in a table format, view details of a specific order, and navigate
 * to other related views such as starting a new order or viewing accounts.
 *
 * @author Zhengxuan Zhao
 */
public class ViewAllOrdersController {

    @FXML
    public TableView<TOOrder> orderTable;
    @FXML
    public TextField orderIndex;
    @FXML
    private TableColumn<TOOrder, String> c_status;
    @FXML
    private TableColumn<TOOrder, String> c_number;
    @FXML
    private TableColumn<TOOrder, String> c_date;
    @FXML
    private TableColumn<TOOrder, String> c_level;
    @FXML
    private TableColumn<TOOrder, String> c_parent;
    @FXML
    private TableColumn<TOOrder, String> c_student;
    @FXML
    private TableColumn<TOOrder, Void> c_view;


    /**
     * Initializes the controller by setting up the table columns and populating the table with order data.
     */
    public void initialize() {
        c_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        c_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        c_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        c_level.setCellValueFactory(new PropertyValueFactory<>("level"));
        c_parent.setCellValueFactory(new PropertyValueFactory<>("parentEmail"));
        c_student.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        populateOrders();
    }

    /**
     * Populates the order table with data fetched from the backend.
     */
    public void populateOrders() {
        ObservableList<TOOrder> orderList = FXCollections.observableArrayList();
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();
        orderList.addAll(orders);
        orderTable.setItems(orderList);
        addButtonToTable();
    }

    /**
     * Adds a "View" button to the edit column for each student row.
     */
    private void addButtonToTable() {
        Callback<TableColumn<TOOrder, Void>, TableCell<TOOrder, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("View");

            {
                btn.setOnAction(event -> {
                    TOOrder order = getTableView().getItems().get(getIndex());
                    try {
                        viewOrder(event, order);
                    }catch (Exception e) {
                        showAlert("","Error: order does not exist.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };

        c_view.setCellFactory(cellFactory);
    }

    /**
     * Views the details of a specific order based on the provided index.
     *
     * @param event the action event triggered by the user.
     * @throws Exception if an error occurs during the process.
     */
    @FXML
    private void viewOrder(ActionEvent event, TOOrder order) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewOrderWindow.fxml"));
        Scene scene = new Scene(loader.load());
        if (order == null) {
            showAlert("", "Error: order does not exist.");
        } else {
            ViewOrderWindowController viewOrderController = loader.getController();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            viewOrderController.setCurrentOrder(order);
            stage.setScene(scene);
        }
    }

    /**
     * Navigates to the View Accounts page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Navigates back to the Items Shop page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
    }

    /**
     * Navigates to the View All Orders page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    /**
     * Navigates to the Parent-Student Associations page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewAssociations() throws IOException {
        loadPage("/pages/ParentStudentPage.fxml");
    }

    /**
     * Navigates to the View All Students page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewStudents() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    /**
     * Navigates to the School Grades page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/GradePage.fxml");
    }

    /**
     * Starts the process of creating a new order.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void startOrder() throws IOException {
        loadPage("/pages/StartOrderWindow.fxml");
    }

    /**
     * Displays an alert with the specified title and message.
     *
     * @param title   the title of the alert.
     * @param message the message to display in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Loads a new page into the current stage.
     *
     * @param fxmlPath the path to the FXML file.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) orderTable.getScene().getWindow();
        currentStage.setScene(scene);
    }
}
