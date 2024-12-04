package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller class for updating an order.
 * This class handles interactions between the user interface and the backend for updating order details.
 * The user can update the student associated with the order and the order level.
 *
 * @author Shengyi Zhong
 */
public class UpdateOrderWindowController implements Initializable {
    @FXML
    private Label idLabel;

    @FXML
    private ChoiceBox<String> studentChoiceBox;

    @FXML
    private ChoiceBox<String> levelChoiceBox;

    @FXML
    private Button BackButton;

    private TOOrder order;
    private String studentName;
    private String level;

    /**
     * Sets the current order to be updated.
     * Populates the fields in the user interface with the order's current details.
     *
     * @param selOrder the selected order to update
     */
    public void setCurrentOrder(TOOrder selOrder) {
        if (selOrder == null) {
            showAlert("Error", "Order not found");
            return;
        }
        order = selOrder;
        idLabel.setText(String.valueOf(order.getNumber()));
        getStudent(order);
        getLevel(order);
    }

    /**
     * Updates the current order with the selected values from the user interface.
     * Sends the updated order details to the backend and navigates back to the View Order page if successful.
     *
     * @param event the event triggered by the update order button
     * @throws IOException if the navigation to the View Order page fails
     */
    @FXML
    private void updateOrder(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idLabel.getText());
        studentName = studentChoiceBox.getValue();
        level = levelChoiceBox.getValue();

        String msg = CoolSuppliesFeatureSet8Controller.updateOrder(level, id, studentName);

        showAlert("", msg);
        if (msg.equals("The order has successfully been updated.")) {
            goBack();
        }
    }

    /**
     * Retrieves the students associated with the parent of the order and populates the student choice box.
     * Preselects the student currently associated with the order.
     *
     * @param order the current order being updated
     */
    @FXML
    private void getStudent(TOOrder order) {
        if (order != null) {
            List<TOStudent> students = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(order.getParentEmail());
            List<String> studentNames = new ArrayList<>();

            for (TOStudent student : students) {
                studentNames.add(student.getName());
            }

            studentChoiceBox.getItems().clear();
            studentChoiceBox.getItems().addAll(studentNames);

            studentName = order.getStudentName();
            studentChoiceBox.setValue(studentName);
        }
    }

    /**
     * Retrieves the level of the order and sets it in the level choice box.
     * Preselects the level currently associated with the order.
     *
     * @param order the current order being updated
     */
    private void getLevel(TOOrder order) {
        if (order != null) {
            level = order.getLevel();
            levelChoiceBox.setValue(level);
        }
    }

    /**
     * Initializes the user interface with available options for the level choice box.
     * Called automatically when the FXML file is loaded.
     *
     * @param location the location used to resolve relative paths for the root object
     * @param resources the resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> levels = Arrays.asList("Mandatory", "Recommended", "Optional");
        levelChoiceBox.getItems().addAll(levels);
    }

    /**
     * Navigates back to the View Order page.
     * Reloads the current order details on the View Order page.
     *
     * @throws IOException if the navigation to the View Order page fails
     */
    @FXML
    private void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewOrderWindow.fxml"));
        Scene scene = new Scene(loader.load());

        ViewOrderWindowController viewOrderController = loader.getController();
        Stage stage = (Stage) BackButton.getScene().getWindow();
        viewOrderController.setCurrentOrder(CoolSuppliesFeatureSet8Controller.viewOrder(String.valueOf(order.getNumber())));
        stage.setScene(scene);
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title the title of the alert
     * @param message the message to display in the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}