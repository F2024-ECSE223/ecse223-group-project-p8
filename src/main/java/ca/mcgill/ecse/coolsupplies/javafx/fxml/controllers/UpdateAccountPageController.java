package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for managing the Update Account Page in the CoolSupplies application.
 * This page allows users to update the details (name, phone number, and password) of an existing parent account.
 * Users can also navigate to other parts of the application from this page.
 *
 * @author Mary Li
 */
public class UpdateAccountPageController {

    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Text emailText;

    private String email;


    /**
     * Initializes the Update Account Page with the existing account details.
     *
     * @param phoneNumber The current phone number of the account.
     * @param name        The current name of the account.
     * @param aEmail      The email of the account being updated.
     */
    @FXML
    public void initialize(String phoneNumber, String name, String aEmail) {
        phoneNumberField.setText(phoneNumber);
        nameField.setText(name);
        emailText.setText(aEmail);
        email = aEmail;
    }

    /**
     * Saves the updated account details (name, phone number, and password).
     * Displays an alert with the result of the update operation and navigates back to the View Accounts Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void saveAccount() throws IOException {
        String newPhoneNumber = phoneNumberField.getText();
        String newName = nameField.getText();
        String newPassword = passwordField.getText();

        String msg = CoolSuppliesFeatureSet1Controller.updateParent(email, newPassword, newName, Integer.parseInt(newPhoneNumber));

        showAlert("", msg);

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
