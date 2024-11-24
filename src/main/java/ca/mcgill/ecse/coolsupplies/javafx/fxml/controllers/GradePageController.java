package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.io.IOException;
import java.util.List;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GradePageController {

    @FXML private Button addGradeButton;

    @FXML private Button deleteGradeButton;

    @FXML private ChoiceBox<String> gradeChoiceBox;

    @FXML private TextField gradeNameTextField;

    @FXML private TextField newGradeNameTextField;

    @FXML private Button startSchoolYearButton;

    @FXML private Button updateGradeButton;

    @FXML
    public void initialize() {
        refreshChoiceBox();
    }

    private void refreshChoiceBox() {
        gradeChoiceBox.getItems().clear();

        List<TOGrade> grades = CoolSuppliesFeatureSet7Controller.getGrades();
        
        for (TOGrade grade : grades) {
            gradeChoiceBox.getItems().add(grade.getLevel());
        }
    }

    @FXML
    public void addGradeClicked(ActionEvent event) {
        String name = gradeNameTextField.getText();

        if (name == null || name.trim().isEmpty()) {
            showAlert("Error", "Please input a valid grade name");
        } else {
            String msg = CoolSuppliesFeatureSet7Controller.addGrade(name);
            showAlert("", msg);
            refreshChoiceBox();
            gradeNameTextField.setText("");
            newGradeNameTextField.setText("");
        }
    }

    @FXML
    void deleteGradeClicked(ActionEvent event) {
        String grade = gradeChoiceBox.getValue();

        if (grade == null) {
            showAlert("Error", "Please select a valid grade");
        } else {
            String msg = CoolSuppliesFeatureSet7Controller.deleteGrade(grade);
            showAlert("", msg);
            refreshChoiceBox();
            gradeNameTextField.setText("");
            newGradeNameTextField.setText("");
        }
    }

    @FXML
    void updateGradeClicked(ActionEvent event) {
        String grade = gradeChoiceBox.getValue();
        String newName = newGradeNameTextField.getText();

        if (grade == null) {
            showAlert("Error", "Please select a valid grade");
        } else if (newName == null || newName.trim().isEmpty()) {
            showAlert("Error", "Please input a valid new name");
        } else {
            String msg = CoolSuppliesFeatureSet7Controller.updateGrade(grade, newName);
            showAlert("", msg);
            refreshChoiceBox();
            gradeNameTextField.setText("");
            newGradeNameTextField.setText("");
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
        loadPage("/pages/ViewSchool.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) gradeChoiceBox.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void startSchoolYearClicked(ActionEvent event) throws IOException {
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();
        for (TOOrder order : orders) {
            CoolSuppliesFeatureSet8Controller.startYear(String.valueOf(order.getNumber()));
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/StartSchoolYearPopUp.fxml"));
        Scene scene = new Scene(loader.load());

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Start School Year");
        dialogStage.initModality(Modality.APPLICATION_MODAL); 
        dialogStage.setScene(scene);

        dialogStage.showAndWait();
    }

}
