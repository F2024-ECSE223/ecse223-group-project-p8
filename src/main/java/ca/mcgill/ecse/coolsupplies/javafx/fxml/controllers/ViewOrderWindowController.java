package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.controller.TOOrderItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewOrderWindowController {

    @FXML private Label authorizationCodeLabel;
    
    @FXML private Label dateLabel;

    @FXML private Label itemsLabel;

    @FXML private Label levelLabel;

    @FXML private Label numberLabel;

    @FXML private Label parentEmailLabel;

    @FXML private Label penaltyAuthorizationCodeLabel;

    @FXML private Label statusLabel;

    @FXML private Label studentNameLabel;

    @FXML private Label totalPriceLabel;

    @FXML private Button cancelOrderButton;

    @FXML private Button editItemsButton;

    @FXML private Button payOrderButton;

    @FXML private Button pickUpOrderButton;

    @FXML private Button updateOrderButton;

    @FXML private TOOrder currentOrder;
    
    @FXML
    public void initialize() {
        showOrderDetails();
    }

    public void setCurrentOrder(TOOrder order) {
        this.currentOrder = order;
    }

    @FXML
    private void showOrderDetails() {
        if (currentOrder != null) {
            if (currentOrder.getParentEmail() != null) {
                parentEmailLabel.setText(currentOrder.getParentEmail());
            } else {
                parentEmailLabel.setText("");
            }
            if (currentOrder.getStudentName() != null) {
                studentNameLabel.setText(currentOrder.getStudentName());
            } else {
                studentNameLabel.setText("");
            }
            if (currentOrder.getStatus() != null) {
                statusLabel.setText(currentOrder.getStatus());
            } else {
                statusLabel.setText("");
            }
            numberLabel.setText(String.valueOf(currentOrder.getNumber()));
            if (currentOrder.getDate() != null) {
                dateLabel.setText(currentOrder.getDate().toString());
            } else {
                dateLabel.setText("");
            }
            if (currentOrder.getLevel() != null) {
                levelLabel.setText(currentOrder.getLevel());
            } else {
                levelLabel.setText("");
            }
            if (currentOrder.getAuthorizationCode() != null) {
                authorizationCodeLabel.setText(currentOrder.getAuthorizationCode());
            } else {
                authorizationCodeLabel.setText("");
            }
            if (currentOrder.getPenaltyAuthorizationCode() != null) {
                penaltyAuthorizationCodeLabel.setText(currentOrder.getPenaltyAuthorizationCode());
            } else {
                penaltyAuthorizationCodeLabel.setText("");
            }
            itemsLabel.setText(getFormattedItems());
            totalPriceLabel.setText(String.valueOf(currentOrder.getTotalPrice()));
        } else {
            parentEmailLabel.setText("");
            studentNameLabel.setText("");
            statusLabel.setText("");
            numberLabel.setText("");
            dateLabel.setText("");
            levelLabel.setText("");
            authorizationCodeLabel.setText("");
            penaltyAuthorizationCodeLabel.setText("");
            itemsLabel.setText("");
            totalPriceLabel.setText("");
        }
    }

    @FXML
    private String getFormattedItems() {
        StringBuilder formattedItems = new StringBuilder();
        List<TOOrderItem> items = currentOrder.getItems();

        if (items == null) {
            return "";
        }

        for (TOOrderItem item : items) {
            formattedItems.append(item.getQuantity())
                    .append(", ")
                    .append(item.getItemName() != null ? item.getItemName() : "")
                    .append(", ")
                    .append(item.getGradeBundleName() != null ? item.getGradeBundleName() : "")
                    .append(", $")
                    .append(item.getPrice())
                    .append(", ")
                    .append(item.getDiscount() != null ? item.getDiscount() : "")
                    .append("\n");
        }

        return formattedItems.toString().trim();
    }

    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/AddItem.fxml");
    }

    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    @FXML
    private void viewStudents() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    @FXML
    private void viewAssociations() throws IOException {
        loadPage("/pages/ParentStudentPage.fxml");
    }

    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/GradePage.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) updateOrderButton.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void cancelOrderClicked(ActionEvent event) throws IOException {
        String msg = CoolSuppliesFeatureSet8Controller.cancelOrder(String.valueOf(currentOrder.getNumber()));
        showAlert("", msg);
        if (msg.equals("Order deleted successfully")) {
            loadPage("/pages/ViewAllOrders.fxml");
        }
    }

    @FXML
    void editItemsClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/EditOrderItemsPage.fxml"));
        Scene scene = new Scene(loader.load());

        //EditOrderItemsPageController controller = loader.getController();
        //controller.setCurrentOrder(currentOrder);

        Stage stage = (Stage) updateOrderButton.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void payOrderClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/SetPenaltyCodes.fxml"));
        Scene scene = new Scene(loader.load());

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Pay for Order");
        dialogStage.initModality(Modality.APPLICATION_MODAL); 
        dialogStage.setScene(scene);

        SetPenaltyCodesController controller = loader.getController();
        //controller.setCurrentOrder(currentOrder);

        dialogStage.showAndWait();
        showOrderDetails();
    }

    @FXML
    void pickUpOrderClicked(ActionEvent event) {
        String msg = CoolSuppliesFeatureSet8Controller.pickUpOrder(String.valueOf(currentOrder.getNumber()));

        showAlert("", msg);
        showOrderDetails();
    }

    @FXML
    void updateOrderClicked(ActionEvent event) throws IOException {
        loadPage("/pages/UpdateOrderWindow.fxml");
    }
    
}
