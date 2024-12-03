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
 * Controller for managing the Update Admin Page in the CoolSupplies application.
 * This page allows the admin user to update their account password.
 * It includes validation for the password and provides navigation to other pages.
 *
 * @author Mary
 */
public class UpdateAdminPageController {

    @FXML
    private TextField passwordField;

    private String email;

    /**
     * Initializes the Update Admin Page with the admin email.
     *
     * @param aEmail The email of the admin account (optional, currently unused).
     */
    @FXML
    public void initialize(String aEmail) {
        email = aEmail;
    }

    /**
     * Saves the updated admin password after validation.
     * Displays an alert with the result of the update operation.
     * Navigates back to the View Accounts Page after saving.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void saveAccount() throws IOException {
        String newPassword = passwordField.getText();

        if (newPassword != null) {
            String msg = CoolSuppliesFeatureSet1Controller.updateAdmin(newPassword);
            showAlert("", msg);
        }
        else {
            showAlert("", "Password cannot be null.");
        }

        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Cancels the update operation and navigates back to the View Accounts Page.
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
        Stage currentStage = (Stage) passwordField.getScene().getWindow();
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
