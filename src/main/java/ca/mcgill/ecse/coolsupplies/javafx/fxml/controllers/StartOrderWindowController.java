package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

/**
 * Controller class for creating a new order.
 * Provides functionality to input order details such as parent, student, level, and ID,
 * and adds the order to the system.
 *
 * @author Shengyi Zhong
 */
public class StartOrderWindowController implements Initializable {

    @FXML
    private ChoiceBox<String> parentChoiceBox;

    @FXML
    private ChoiceBox<String> studentChoiceBox;

    @FXML
    private ChoiceBox<String> levelChoiceBox;

    @FXML
    private TextField idTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button NewOrderButton;

    private int id;
    private String parentEmail;
    private static final List<String> levels = Arrays.asList("Mandatory", "Recommended", "Optional");
    private List<Integer> ids = new ArrayList<>();

    /**
     * Handles the creation of a new order.
     * Validates the input fields, ensures a unique order ID, and creates the order in the system.
     * Resets the form fields and reloads the orders upon successful creation.
     *
     * @param event the event triggered by clicking the "Place Order" button
     */
    @FXML
    private void placeOrder(ActionEvent event) {
        String studentName = studentChoiceBox.getValue();
        String level = levelChoiceBox.getValue();
        String input = idTextField.getText();

        if (input.isEmpty()) {
            showAlert("Invalid Input", "Order ID cannot be empty.");
            return;
        }

        try {
            id = Integer.parseInt(input);
            if (id <= 0) {
                showAlert("Invalid Input", "Order ID must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Order ID must be a valid number.");
            idTextField.clear();
            return;
        }

        if (ids.contains(id)) {
            showAlert("Invalid Input", "Order ID already exists. Please use a unique ID.");
            return;
        }

        if (studentName != null && parentEmail != null && level != null) {
            Date date = Date.valueOf(datePicker.getValue());
            CoolSuppliesFeatureSet6Controller.startOrder(id, date, level, parentEmail, studentName);

            ids.add(id); // Add the new ID to the list
            showAlert("Order Created", "A new order has been successfully created and added to the system.");

            // Reset all fields to initial state
            parentChoiceBox.setValue(null);
            studentChoiceBox.setValue(null);
            levelChoiceBox.setValue(null);
            datePicker.setValue(LocalDate.now());
            idTextField.clear(); // Clear the ID field explicitly

            reloadOrders(); // Reload orders to keep the internal state consistent
        } else {
            showAlert("Selection Incomplete", "Please ensure all required fields are selected before placing the order.");
        }
    }

    /**
     * Populates the student choice box with students associated with the selected parent.
     *
     * @param event the event triggered when a parent is selected
     */
    private void getParent(ActionEvent event) {
        parentEmail = parentChoiceBox.getValue();
        if (parentEmail != null) {
            List<TOStudent> students = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail);
            List<String> studentNames = new ArrayList<>();
            for (TOStudent student : students) {
                studentNames.add(student.getName());
            }
            studentChoiceBox.getItems().clear();
            studentChoiceBox.getItems().addAll(studentNames);
        }
    }

    /**
     * Initializes the user interface elements and loads initial data.
     * Called automatically when the FXML file is loaded.
     *
     * @param location the location used to resolve relative paths for the root object
     * @param resources the resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        List<String> parentNames = new ArrayList<>();
        for (TOParent parent : parents) {
            parentNames.add(parent.getEmail());
        }

        reloadOrders();

        parentChoiceBox.getItems().addAll(parentNames);
        levelChoiceBox.getItems().addAll(levels);
        datePicker.setValue(LocalDate.now());

        parentChoiceBox.setOnAction(this::getParent);
    }

    /**
     * Reloads the list of orders to ensure consistency in the system.
     * Clears and updates the internal list of order IDs.
     */
    private void reloadOrders() {
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();
        ids.clear();
        for (TOOrder order : orders) {
            ids.add(order.getNumber());
        }
        Collections.sort(ids);
        idTextField.clear();
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title the title of the alert
     * @param message the message to display in the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigates to the specified page.
     *
     * @param fxmlPath the path to the FXML file of the target page
     * @throws IOException if the page fails to load
     */
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) NewOrderButton.getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * Navigates to the Accounts page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    void goToAccount() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Navigates to the Items page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    void goToItems() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
    }

    /**
     * Navigates to the Bundles page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    void goToBundles() throws IOException {
        loadPage("/pages/Bundles.fxml");
    }

    /**
     * Navigates to the New Order page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    private void goToNewOrder() throws IOException {
        loadPage("/pages/StartOrderWindow.fxml");
    }
}
