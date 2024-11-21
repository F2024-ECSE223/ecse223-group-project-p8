package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ViewAccountsPageController {

    @FXML
    private ChoiceBox<String> accountChoiceBox;
    @FXML
    private TableView<?> overviewTable;

    private ObservableList<String> accountList;

    private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    public void initialize() {
//        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
//        SchoolAdmin admin = coolSupplies.getAdmin();
//        List<String> accountNames = List.of();
//
//        accountNames.add(admin.getEmail());
//
//        for (int i = 0; i < coolSupplies.getParents().size(); i++){
//            accountNames.add(parents.get(i).getEmail());
//        }

        //need accounts present in the system to work
        List<String> accountNames = List.of("jane.doe@gmail.com", "john.doe@gmail.com", "txt@moa.ca", "yeonjun@gmail.com", "admin@cool.ca");

        accountList = FXCollections.observableArrayList(accountNames);

        accountChoiceBox.setItems(accountList);
    }

    @FXML
    private void updateAccount() throws IOException {
        //todo
        String selectedParent = accountChoiceBox.getValue();

        if (selectedParent == null) {
            showAlert("Error", "Please select a parent to update.");
            return;
        }

        if (selectedParent == "admin@cool.ca"){
            loadPage("/pages/UpdateAdminPage.fxml");
        }

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/UpdateAccountPage.fxml"));
//        Scene scene = new Scene(loader.load());
//
//        Stage stage = (Stage) accountChoiceBox.getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
        else{
            TOParent parent = CoolSuppliesFeatureSet1Controller.getParent(selectedParent);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/UpdateAccountPage.fxml"));
            Scene scene = new Scene(loader.load());

            UpdateAccountPageController controller = loader.getController();

            //controller.initialize(String.valueOf(parent.getPhoneNumber()), parent.getName(), selectedParent);
            controller.initialize("12345678", "yeonjun", selectedParent);
            Stage stage = (Stage) accountChoiceBox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void deleteAccount() {
        //todo
        String selectedParent = accountChoiceBox.getValue();

        if (selectedParent == null) {
            showAlert("Error", "Please select a parent to delete.");
            return;
        }
//
//        String msg = CoolSuppliesFeatureSet1Controller.deleteParent(selectedParent);
//
//        showAlert("", msg);
        showAlert("","Deleted");
        accountList.remove(selectedParent);
    }

    @FXML
    private void addAccount() {
        //todo

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
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
        loadPage("/pages/ViewStudents.fxml");
    }

    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/ViewSchool.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) accountChoiceBox.getScene().getWindow();
        stage.setScene(scene);
    }
}