package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class AccountPageController {

    @FXML
    private Text accountNameField;
    @FXML
    private Text phoneNumField;
    @FXML
    private Text nameField;

//    private Stage stage;

    @FXML
    public void initialize() {
        //todo, just a sample
        accountNameField.setText("JohnDoe123");
        phoneNumField.setText("123-456-7890");
        nameField.setText("John Doe");
    }

//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }

    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/AddItem.fxml");
    }

    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/ViewSchool.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) accountNameField.getScene().getWindow();
        currentStage.setScene(scene);
    }
}
