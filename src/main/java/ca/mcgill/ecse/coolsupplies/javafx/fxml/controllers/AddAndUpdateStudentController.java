package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddAndUpdateStudentController {

    @FXML private ComboBox gradeBox;
    @FXML private Label resultLabel;
    @FXML private TextField studentName;

    @FXML private Label title;

    private Student currentStudent;

    public void initialize() {
        List<TOGrade> grades = CoolSuppliesFeatureSet7Controller.getGrades();
        for(TOGrade grade: grades){
            gradeBox.getItems().add(grade.getLevel());
        }
        gradeBox.setValue(grades.get(0).getLevel());
    }

    public void setCurrentStudent(Student student) {
        this.currentStudent = student;
        if(currentStudent == null){
            title.setText("Create a new student");
        }else{
            title.setText(currentStudent.getName());
            studentName.setText(currentStudent.getName());
        }
    }

    @FXML
    private void addAndUpdateStudent(ActionEvent event){
        if(currentStudent != null){
            resultLabel.setText(CoolSuppliesFeatureSet2Controller.updateStudent(
                    currentStudent.getName(),
                    studentName.getText(),
                    gradeBox.getValue().toString()
            ));
        }else {
            resultLabel.setText(
                    CoolSuppliesFeatureSet2Controller.addStudent(
                            studentName.getText(), gradeBox.getValue().toString()
                    ));
        }
    }

    @FXML
    private void viewAccount() throws IOException {
        loadPage("/pages/AccountPage.fxml");
    }

    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    @FXML
    private void updateAccount() throws IOException {
        loadPage("/pages/UpdateAccountPage.fxml");
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
        loadPage("/pages/ViewAllStudents.fxml");
    }

    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/ViewSchool.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) resultLabel.getScene().getWindow();
        currentStage.setScene(scene);
    }
}
