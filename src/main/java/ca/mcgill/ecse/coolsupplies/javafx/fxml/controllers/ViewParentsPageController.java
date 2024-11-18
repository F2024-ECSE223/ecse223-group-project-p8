package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ViewParentsPageController {

    @FXML
    private ChoiceBox<String> busChoiceBox;
    @FXML
    private TableView<?> overviewTable;

    private ObservableList<String> parentList;

    private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    public void initialize() {
        //todo
        List<Parent> parents = coolSupplies.getParents();
        parentList = FXCollections.observableArrayList();
        for (Parent parent : parents) {
            parentList.add(parent.getName());
        }
        busChoiceBox.setItems(parentList);
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
    private void updateParent(ActionEvent event) throws IOException {
        //todo
        String selectedParent = busChoiceBox.getValue();
        if (selectedParent == null) {
            showAlert("Error", "Please select a parent to update.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/UpdateParentPage.fxml"));
        Scene scene = new Scene(loader.load());

        //pdateParentController controller = loader.getController();
        //controller.setParentName(selectedParent);

        Stage stage = (Stage) busChoiceBox.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteParent() {
        //todo
        String selectedParent = busChoiceBox.getValue();
        if (selectedParent == null) {
            showAlert("Error", "Please select a parent to delete.");
            return;
        }

//        boolean isDeleted = CoolSuppliesFeatureSet2Controller.deleteParent(selectedParent);
//        if (isDeleted) {
//            showAlert("Success", "Parent deleted successfully.");
//            parentList.remove(selectedParent);
//        } else {
//            showAlert("Error", "Failed to delete parent.");
//        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) busChoiceBox.getScene().getWindow();
        stage.setScene(scene);
    }
}