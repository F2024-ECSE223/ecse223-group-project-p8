package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Controller for managing and displaying all students in the CoolSupplies system.
 * This class provides functionalities to view, add, update, and delete student records.
 * It allows users to interact with the data through a graphical user interface (GUI) and
 * integrates with the backend system to fetch, modify, and manage student data.
 *
 * @author Zhengxuan Zhao
 * @version 1.0
 */
public class ViewAllStudentsController {

    @FXML
    private TableView<TOStudent> studentTable;
    @FXML
    private TableColumn<TOStudent, String> c_name;
    @FXML
    private TableColumn<TOStudent, String> c_grade;
    @FXML
    private TableColumn<TOStudent, Void> c_edit;
    @FXML
    private TextField studentIndex;

    /**
     * Initializes the controller by setting up table columns and populating the table with student data.
     */
    public void initialize() {
        c_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        c_grade.setCellValueFactory(new PropertyValueFactory<>("GradeLevel"));
        populateStudents();
    }

    /**
     * Populates the student table with data fetched from the backend.
     */
    public void populateStudents() {
        ObservableList<TOStudent> studentList = FXCollections.observableArrayList();
        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();
        studentList.addAll(students);

        studentTable.setItems(studentList);
        addButtonToTable();
    }

    /**
     * Adds a "View" button to the edit column for each student row.
     */
    private void addButtonToTable() {
        Callback<TableColumn<TOStudent, Void>, TableCell<TOStudent, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("View");

            {
                btn.setOnAction(event -> {
                    TOStudent student = getTableView().getItems().get(getIndex());
                    try {
                        updateStudent(event, student);
                    } catch (IOException e) {
                        showAlert("", "Error: cannot update this student.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };

        c_edit.setCellFactory(cellFactory);
    }

    /**
     * Navigates to the Add New Student page.
     *
     * @param event the action event triggered by the user.
     * @throws Exception if an error occurs during navigation.
     */
    @FXML
    private void AddNewStudent(ActionEvent event) throws Exception {
        List<TOGrade> grades = CoolSuppliesFeatureSet7Controller.getGrades();
        if (grades.isEmpty()) {
            showAlert("", "There is no grade in the system. Please create a grade first.");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AddAndUpdateStudent.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }

    /**
     * Displays an alert with the specified title and message.
     *
     * @param title   the title of the alert.
     * @param message the message to display in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Updates a selected student by navigating to the update page and preloading student data.
     *
     * @param event   the action event triggered by the user.
     * @param student the student to update.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void updateStudent(ActionEvent event, TOStudent student) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AddAndUpdateStudent.fxml"));
        Scene scene = new Scene(loader.load());

        AddAndUpdateStudentController controller = loader.getController();
        controller.setCurrentStudent(student);

        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        for (TOParent parent : parents) {
            if (CoolSuppliesFeatureSet6Controller.getStudentOfParent(student.getName(), parent.getEmail()) != null) {
                controller.setCurrentParent(parent);
                break;
            }
        }

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * Deletes a student based on the input index and refreshes the view.
     *
     * @param event the action event triggered by the user.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void deleteStudent(ActionEvent event) throws IOException {
        TOStudent selStudentItem = studentTable.getSelectionModel().getSelectedItem();
        String msg = CoolSuppliesFeatureSet2Controller.deleteStudent(selStudentItem.getName());
        showAlert("", msg);
        loadPage("/pages/ViewAllStudents.fxml");
    }

    /**
     * Navigates to the View Accounts page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Navigates back to the Items Shop page.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
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
        Stage currentStage = (Stage) studentTable.getScene().getWindow();
        currentStage.setScene(scene);
    }
}
