package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddAndUpdateStudentController {

    @FXML
    private Label parentLabel;
    @FXML
    private ComboBox<String> gradeBox;
    @FXML
    private Label resultLabel;
    @FXML
    private TextField studentName;
    @FXML
    private Label title;

    private TOStudent currentStudent;
    private TOParent currentParent;

    public void initialize() {
        List<TOGrade> grades = CoolSuppliesFeatureSet7Controller.getGrades();
        for(TOGrade grade: grades){
            gradeBox.getItems().add(grade.getLevel());
        }
        gradeBox.setValue(grades.get(0).getLevel());
        parentLabel.setText("No parent associated");
    }

    public void setCurrentStudent(TOStudent student) {
        this.currentStudent = student;
        if(currentStudent == null){
            title.setText("Create a new student");
            parentLabel.setText("No parent associated");
        }else{
            ParentStudentPageController.ParentStudent parentStudent = ParentStudentPageController.ParentStudent.getStudentWithName(currentStudent.getName());
            if(parentStudent == null || parentStudent.getParentEmail() == null){
                parentLabel.setText("No parent associated");
            }else{
                parentLabel.setText(parentStudent.getParentEmail());
            }
            title.setText(currentStudent.getName());
            studentName.setText(currentStudent.getName());
        }
    }

    @FXML
    private void addAndUpdateStudent(ActionEvent event){
        String msg = "";
        if(currentStudent != null){
            msg = CoolSuppliesFeatureSet2Controller.updateStudent(
                    currentStudent.getName(),
                    studentName.getText(),
                    gradeBox.getValue());

        }else {
            msg = CoolSuppliesFeatureSet2Controller.addStudent(
                            studentName.getText(), gradeBox.getValue());
        }
        showAlert("",msg);
    }

    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/viewAccountsPage.fxml");
    }

    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    @FXML
    private void viewAssociations() throws IOException {
        loadPage("/pages/ParentStudentPage.fxml");
    }

    @FXML
    private void viewStudents() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/GradePage.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) resultLabel.getScene().getWindow();
        currentStage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
