package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ViewAllOrdersController {

    @FXML
    public TableView<TOOrder> orderTable;
    @FXML
    public TextField orderIndex;
    @FXML
    private TableColumn<TOOrder,String> c_status;
    @FXML
    private TableColumn<TOOrder,String> c_number;
    @FXML
    private TableColumn<TOOrder,String> c_date;
    @FXML
    private TableColumn<TOOrder,String> c_level;
    @FXML
    private TableColumn<TOOrder,String> c_parent;
    @FXML
    private TableColumn<TOOrder,String> c_student;

    public void initialize() {
        c_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        c_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        c_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        c_level.setCellValueFactory(new PropertyValueFactory<>("level"));
        c_parent.setCellValueFactory(new PropertyValueFactory<>("parentEmail"));
        c_student.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        populateOrders();
    }

    public void populateOrders() {
        ObservableList<TOOrder> orderList = FXCollections.observableArrayList();

        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();
        orderList.addAll(orders);

        orderTable.setItems(orderList);
    }

    @FXML
    private void viewOrder(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewOrderWindow.fxml"));
        Scene scene = new Scene(loader.load());

        String index = orderIndex.getText();
        TOOrder order = CoolSuppliesFeatureSet8Controller.viewOrder(index);
        if (order == null){
            showAlert("","error: order does not exist.");
        }else {
            ViewOrderWindowController viewOrderController= loader.getController();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            viewOrderController.setCurrentOrder(order);
            stage.setScene(scene);
        }
    }

    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
    }

    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    @FXML
    private void viewAssociations() throws IOException {
        loadPage("/pages/ParentStudentPage.fxml");
    }

    @FXML
    private void viewStudents() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/GradePage.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // load start a new Order page
    @FXML
    private void startOrder() throws IOException {
        loadPage("/pages/StartOrderWindow.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) orderTable.getScene().getWindow();
        currentStage.setScene(scene);
    }
}
