package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * Controller for managing the View Accounts Page in the CoolSupplies application.
 * This page allows users to view all parent accounts, update and delete accounts, and navigate to other sections of the application.
 * The accounts are displayed in a table, and additional actions can be performed via a choice box and action buttons.
 *
 * @author Mary
 */
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

    private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    /**
     * Initializes the View Accounts Page.
     * Populates the table and choice box with account data and sets up the table column bindings.
     */
    @FXML
    public void initialize() {
        refreshChoiceBox();
        refreshList();

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    /**
     * Refreshes the list of parent accounts displayed in the table.
     *
     * @return An ObservableList of TOParent objects representing the accounts.
     */
    private ObservableList<TOParent> refreshList() {
        ObservableList<TOParent> userList = FXCollections.observableArrayList();

        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        userList.addAll(parents);

        accounntTable.setItems(userList);

        return userList;
    }

    /**
     * Handles updating a selected account.
     * Loads the Update Account or Update Admin Page based on the account type.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void updateAccount() throws IOException {
        String selectedParent = accountChoiceBox.getValue();

        if (selectedParent == null) {
            showAlert("Error", "Please select a parent to update.");
            return;
        }

        if (selectedParent.equals("admin@cool.ca")) {
            loadPage("/pages/UpdateAdminPage.fxml");
        }
        else {
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

    /**
     * Handles deleting a selected account.
     * Displays an alert with the result of the deletion operation and refreshes the table and choice box.
     */
    @FXML
    private void deleteAccount() {
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

    /**
     * Navigates to the Add Account Page and refreshes the table and choice box after returning.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void addAccount() throws IOException {
        loadPage("/pages/AddAccountPage.fxml");
        refreshChoiceBox();
        refreshList();
    }

    /**
     * Refreshes the list of accounts displayed in the choice box.
     */
    private void refreshChoiceBox() {
        accountChoiceBox.getItems().clear();

        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        SchoolAdmin admin = coolSupplies.getAdmin();

        accountChoiceBox.getItems().add(admin.getEmail());
        for (TOParent parent : parents) {
            accountChoiceBox.getItems().add(parent.getEmail());
        }
    }

    /**
     * Displays an alert box with the specified title and message.
     *
     * @param title   The title of the alert box.
     * @param message The message to display in the alert box.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigates back to the Items Shop Page.
     *
     * @throws IOException If there is an issue loading the next page.
     */
    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
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

    /**
     * Loads the specified FXML page into the current stage.
     *
     * @param fxmlPath The path of the FXML file to load.
     * @throws IOException If there is an issue loading the FXML file.
     */
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) accountChoiceBox.getScene().getWindow();
        stage.setScene(scene);
    }
}
