package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller class for managing parent-student associations.
 * Allows adding and removing students from parents and displaying existing associations.
 *
 * @author Shengyi Zhong
 */
public class ParentStudentPageController implements Initializable {

    @FXML
    private AnchorPane ap;

    @FXML
    private VBox parentStudentVBox;

    @FXML
    private ChoiceBox<String> parentChoiceBox;

    @FXML
    private TableView<ParentStudent> parentStudentTable;

    @FXML
    private TableColumn<ParentStudent, String> parentColumn;

    @FXML
    private TableColumn<ParentStudent, String> studentColumn;

    @FXML
    private Label studentLabel;

    private String msg;
    private String selectedParent;
    private ParentStudent selecteParentStudent;
    static ObservableList<ParentStudent> data = FXCollections.observableArrayList();

    /**
     * Handles the action of adding a student to a parent.
     * Displays a warning if the student is already assigned to a parent.
     *
     * @param event the event triggered by clicking the "Add Student" button
     */
    @FXML
    private void addStudentToParent(ActionEvent event) {
        selectedParent = parentChoiceBox.getValue();
        if (selecteParentStudent != null && selectedParent != null) {
            if (selecteParentStudent.parentEmail == null) {
                msg = CoolSuppliesFeatureSet6Controller.addStudentToParent(selecteParentStudent.studentName, selectedParent);
                selecteParentStudent.setParentEmail(selectedParent);
                parentStudentTable.refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Student");
                alert.setHeaderText(null);
                alert.setContentText(msg);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Add Student Failed");
                alert.setHeaderText(null);
                alert.setContentText("Student is already assigned to a parent.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Handles the action of removing a student from a parent.
     * Displays a confirmation message after successful removal.
     *
     * @param event the event triggered by clicking the "Remove Student" button
     */
    @FXML
    private void deleteStudentFromParent(ActionEvent event) {
        msg = CoolSuppliesFeatureSet6Controller.deleteStudentFromParent(selecteParentStudent.studentName, selecteParentStudent.parentEmail);
        selecteParentStudent.setParentEmail(null);
        parentStudentTable.refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Remove Student");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Initializes the controller by populating the table and choice box with data.
     * Called automatically when the FXML file is loaded.
     *
     * @param location the location used to resolve relative paths for the root object
     * @param resources the resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentLabel.setText("Not Selected");
        parentColumn.setCellValueFactory(new PropertyValueFactory<>("parentEmail"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();
        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();

        // Populate parentStudentList
        ParentStudent.initializeList(students, parents);

        // Populate parentChoiceBox
        List<String> parentEmails = new ArrayList<>();
        for (TOParent parent : parents) {
            parentEmails.add(parent.getEmail());
        }
        parentChoiceBox.getItems().addAll(parentEmails);

        // Populate parentStudentTable
        data.setAll(ParentStudent.parentStudentList);
        parentStudentTable.setItems(data);

        parentStudentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selecteParentStudent = parentStudentTable.getSelectionModel().getSelectedItem();
                studentLabel.setText(selecteParentStudent.studentName);
            }
        });
    }

    /**
     * Inner class representing a parent-student relationship.
     * Contains fields for the parent's email and the student's name.
     */
    public static class ParentStudent {
        private String parentEmail;
        private String studentName;
        private static List<ParentStudent> parentStudentList = new ArrayList<>();

        public ParentStudent(String parentEmail, String studentName) {
            this.parentEmail = parentEmail;
            this.studentName = studentName;
        }

        public String getParentEmail() {
            return parentEmail;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setParentEmail(String newEmail) {
            parentEmail = newEmail;
        }

        /**
         * Initializes the list of parent-student relationships based on the given data.
         *
         * @param students the list of students
         * @param parents the list of parents
         */
        public static void initializeList(List<TOStudent> students, List<TOParent> parents) {
            parentStudentList.clear();
            for (TOStudent student : students) {
                if (null == ParentStudent.getStudentWithName(student.getName())) {
                    ParentStudent newStudent = new ParentStudent(null, student.getName());
                    ParentStudent.parentStudentList.add(newStudent);
                }
            }

            for (TOParent parent : parents) {
                List<TOStudent> theirKids = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parent.getEmail());
                for (TOStudent student : theirKids) {
                    ParentStudent thisStudent = ParentStudent.getStudentWithName(student.getName());
                    if (thisStudent != null) {
                        thisStudent.setParentEmail(parent.getEmail());
                    }
                }
            }
        }

        /**
         * Finds a student in the list by name.
         *
         * @param studentName the name of the student
         * @return the matching ParentStudent object, or null if not found
         */
        public static ParentStudent getStudentWithName(String studentName) {
            for (ParentStudent parentStudent : parentStudentList) {
                if (Objects.equals(parentStudent.studentName, studentName)) {
                    return parentStudent;
                }
            }
            return null;
        }
    }

    /**
     * Navigates to the specified page.
     *
     * @param fxmlPath the path to the FXML file of the target page
     * @throws IOException if the page fails to load
     */
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        AnchorPane newPage = loader.load();
        Stage currentStage = (Stage) parentStudentVBox.getScene().getWindow();
        currentStage.setScene(new Scene(newPage));
    }

    /**
     * Navigates to the Accounts page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Navigates to the previous page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
    }

    /**
     * Navigates to the Orders page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    private void viewOrders() throws IOException {
        loadPage("/pages/ViewAllOrders.fxml");
    }

    /**
     * Reloads the current page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    private void viewAssociations() throws IOException {
        loadPage("/pages/ParentStudentPage.fxml");
    }

    /**
     * Navigates to the Students page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    private void viewStudents() throws IOException {
        loadPage("/pages/ViewAllStudents.fxml");
    }

    /**
     * Navigates to the School page.
     *
     * @throws IOException if the navigation fails
     */
    @FXML
    private void viewSchool() throws IOException {
        loadPage("/pages/GradePage.fxml");
    }
}
