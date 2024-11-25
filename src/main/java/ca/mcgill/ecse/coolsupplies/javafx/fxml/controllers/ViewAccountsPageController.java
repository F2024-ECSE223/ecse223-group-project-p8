package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;
import ca.mcgill.ecse.coolsupplies.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class ViewAccountsPageController {

    @FXML
    private ChoiceBox<String> accountChoiceBox;
    @FXML
    private TableView<TOParent> accounntTable;
    @FXML
    private TableColumn<TOParent, String> emailColumn;
    @FXML
    private TableColumn<TOParent, String> nameColumn;
    @FXML
    private TableColumn<TOParent, String> phoneNumberColumn;

    private ObservableList<String> emailList;


    private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    public void initialize() {
        refreshChoiceBox();
        refreshList();

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    private ObservableList<TOParent> refreshList() {
        ObservableList<TOParent> userList = FXCollections.observableArrayList();

        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        userList.addAll(parents);

        accounntTable.setItems(userList);

        return userList;
    }


    @FXML
    private void updateAccount() throws IOException {
        String selectedParent = accountChoiceBox.getValue();

        if (selectedParent == null) {
            showAlert("Error", "Please select a parent to update.");
            return;
        }

        if (selectedParent.equals("admin@cool.ca")){
            loadPage("/pages/UpdateAdminPage.fxml");
        }

        else{
            TOParent parent = CoolSuppliesFeatureSet1Controller.getParent(selectedParent);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/UpdateAccountPage.fxml"));
            Scene scene = new Scene(loader.load());

            UpdateAccountPageController controller = loader.getController();

            controller.initialize(String.valueOf(parent.getPhoneNumber()), parent.getName(), selectedParent);
            Stage stage = (Stage) accountChoiceBox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        refreshList();
    }

    @FXML
    private void deleteAccount() {
        //todo
        String selectedParent = accountChoiceBox.getValue();

        if (selectedParent == null) {
            showAlert("Error", "Please select a parent to delete.");
            return;
        }

        String msg = CoolSuppliesFeatureSet1Controller.deleteParent(selectedParent);

        showAlert("", msg);
        refreshChoiceBox();
        refreshList();
    }

    @FXML
    private void addAccount() throws IOException {
        loadPage("/pages/AddAccountPage.fxml");
        refreshChoiceBox();
        refreshList();
    }

    private void refreshChoiceBox() {
        accountChoiceBox.getItems().clear();

        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        SchoolAdmin admin = coolSupplies.getAdmin();

        accountChoiceBox.getItems().add(admin.getEmail());
        for (TOParent parent : parents) {
            accountChoiceBox.getItems().add(parent.getEmail());
        }
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
        loadPage("/pages/ItemsShop.fxml");
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

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) accountChoiceBox.getScene().getWindow();
        stage.setScene(scene);
    }
}