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

/**
 * Controller for managing the addition and updating of student information in the CoolSupplies system.
 * This class interacts with the GUI elements defined in the associated FXML file to handle user actions
 * and updates the underlying data models using controllers from the CoolSupplies backend.
 * It supports functionalities such as adding new students, updating existing students, and navigating
 * to different views related to the system.
 *
 * @author Zhengxuan Zhao
 *
 */
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

    /**
     * Initializes the controller by populating the grade options and resetting parent information.
     */
    public void initialize() {
        List<TOGrade> grades = CoolSuppliesFeatureSet7Controller.getGrades();
        for (TOGrade grade : grades) {
            gradeBox.getItems().add(grade.getLevel());
        }
        gradeBox.setValue(grades.get(0).getLevel());
        parentLabel.setText("No parent associated");
    }

    /**
     * Sets the current student for editing or updating.
     *
     * @param student the student to set, or {@code null} for creating a new student.
     */
    public void setCurrentStudent(TOStudent student) {
        this.currentStudent = student;
        if (currentStudent == null) {
            title.setText("Create a new student");
        } else {
            title.setText(currentStudent.getName());
            studentName.setText(currentStudent.getName());
        }
    }

    /**
     * Sets the current parent associated with the student.
     *
     * @param parent the parent to set, or {@code null} if no parent is associated.
     */
    public void setCurrentParent(TOParent parent) {
        this.currentParent = parent;
        if (currentParent == null) {
            parentLabel.setText("No parent associated");
        } else {
            parentLabel.setText(parent.getEmail());
        }
    }

    /**
     * Handles the action of adding or updating a student.
     *
     * @param event the action event triggering this method.
     */
    @FXML
    private void addAndUpdateStudent(ActionEvent event) {
        String msg = "";
        if (currentStudent != null) {
            msg = CoolSuppliesFeatureSet2Controller.updateStudent(
                    currentStudent.getName(),
                    studentName.getText(),
                    gradeBox.getValue());
        } else {
            msg = CoolSuppliesFeatureSet2Controller.addStudent(
                    studentName.getText(), gradeBox.getValue());
        }
        showAlert("", msg);
    }

    /**
     * Navigates to the View Accounts page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/viewAccountsPage.fxml");
    }

    /**
     * Navigates back to the View All Students page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    /**
     * Navigates to the View All Orders page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    /**
     * Navigates to the Parent-Student Associations page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewAssociations() throws IOException {
        loadPage("/pages/ParentStudentPage.fxml");
    }

    /**
     * Navigates to the View All Students page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewStudents() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    /**
     * Navigates to the School Grades page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/GradePage.fxml");
    }

    /**
     * Loads a new page into the current stage.
     *
     * @param fxmlPath the path to the FXML file.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) resultLabel.getScene().getWindow();
        currentStage.setScene(scene);
    }

    /**
     * Displays an alert with the specified title and message.
     *
     * @param title the title of the alert.
     * @param message the message to display in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
