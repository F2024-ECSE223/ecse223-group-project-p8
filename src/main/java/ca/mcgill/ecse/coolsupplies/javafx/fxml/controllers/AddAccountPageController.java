package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for handling the Add Account Page functionality in the CoolSupplies application.
 * This page allows users to add a new parent account by providing email, password, name, and phone number.
 * Users can also navigate to other pages such as viewing accounts, orders, students, and associations.
 *
 * @author Mary Li
 */
public class AddAccountPageController {

    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;

    /**
     * Saves a new parent account with the provided details.
     * Validates the phone number to ensure it contains only digits.
     * If the account is added successfully, navigates to the View Accounts Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void saveAccount() throws IOException {
        String phoneNumber = phoneNumberField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        if (!phoneNumber.matches("[0-9]+")){
            showAlert("", "Phone number must be numbers only");
        }

        String msg = CoolSuppliesFeatureSet1Controller.addParent(email, password, name, Integer.parseInt(phoneNumber));

        showAlert("", msg);

        if (msg == "Account added successfully."){
            loadPage("/pages/ViewAccountsPage.fxml");
        }
    }

    /**
     * Cancels the current action and navigates back to the View Accounts Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void cancel() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Loads the specified FXML page into the current stage.
     *
     * @param fxmlPath The path of the FXML file to load.
     * @throws IOException If there is an issue loading the FXML file.
     */
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) phoneNumberField.getScene().getWindow();
        currentStage.setScene(scene);
    }

    /**
     * Displays an alert box with the specified title and message.
     *
     * @param title   The title of the alert box.
     * @param message The message to display in the alert box.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigates back to the Add Item Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/AddItem.fxml");
    }

    /**
     * Navigates to the View Accounts Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Navigates to the View All Orders Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    /**
     * Navigates to the View All Students Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void viewStudents() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    /**
     * Navigates to the View Associations Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void viewAssociations() throws IOException {
        loadPage("/pages/ParentStudentPage.fxml");
    }

    /**
     * Navigates to the View School Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/GradePage.fxml");
    }
}
