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

public class UpdateAdminPageController {
    @FXML
    private TextField passwordField;

    private Stage stage;

    private String email;


    @FXML
    public void initialize(String aEmail) {
        email = aEmail;
    }

    @FXML
    private void saveAccount() throws IOException {
        String newPassword = passwordField.getText();

        if (newPassword != null){
            String msg = CoolSuppliesFeatureSet1Controller.updateAdmin(newPassword);
            showAlert("", msg);
        }
        else{
            showAlert("", "Password cannot be null.");
        }

        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    private void cancel() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) passwordField.getScene().getWindow();
        currentStage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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
}
