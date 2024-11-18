package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateAccountPageController {

    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField nameField;
    private Stage stage;

    @FXML
    public void initialize() {
        //todo
        phoneNumberField.setText("123-456-7890");
        nameField.setText("John Doe");
    }

    @FXML
    private void viewAccount() throws IOException {
        loadPage("/pages/AccountPage.fxml");
    }

    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/UpdateItemPage.fxml");
    }

    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    @FXML
    private void viewParents() throws IOException {
        loadPage("/pages/ViewParentsPage.fxml");
    }

    @FXML
    private void viewStudents() throws IOException {
        loadPage("/pages/ViewStudents.fxml");
    }

    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/ViewSchool.fxml");
    }

    @FXML
    private void saveAccount() throws IOException {
        //todo
        String newPhoneNumber = phoneNumberField.getText();
        String newName = nameField.getText();

        if (newPhoneNumber.isEmpty() || newName.isEmpty()) {
            showPopUp("Error: Both fields are required!");
        } else {
            showPopUp("Account updated successfully!");
        }
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AccountPage.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) phoneNumberField.getScene().getWindow();
        stage.setScene(scene);
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) phoneNumberField.getScene().getWindow();
        currentStage.setScene(scene);
    }

    private void showPopUp(String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/PopUpWindow.fxml"));
        Scene scene = new Scene(loader.load());

        PopUpWindowController controller = loader.getController();
        controller.setMessage(message);

        Stage popUpStage = new Stage();
        popUpStage.setScene(scene);
        popUpStage.show();
    }

    private void showAlert(String title, String message) {
        //todo
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
