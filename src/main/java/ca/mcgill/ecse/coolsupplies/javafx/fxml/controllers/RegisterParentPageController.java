package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class RegisterParentPageController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField passwordField;

    @FXML
    private void saveAccount(ActionEvent event) throws IOException {
        //todo
        String email = emailField.getText();
        String name = nameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        boolean isSaved = saveParentAccount(email, name, phoneNumber, password);

        if (isSaved) {
            showAlert("Success", "Account created successfully!");

            clearFields();

            goToUpdateItemPage();
        } else {
            showAlert("Error", "Failed to create account. Please try again.");
        }
    }

    private boolean saveParentAccount(String email, String name, String phoneNumber, String password) {
        //todo
        System.out.println("Saving account: " + email + ", " + name);
        return true;
    }

    private void clearFields() {
        emailField.clear();
        nameField.clear();
        phoneNumberField.clear();
        passwordField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void goToUpdateItemPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/UpdateItemPage.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}