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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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


    String msg;
    String selectedParent;
    ParentStudent selecteParentStudent;
    static ObservableList<ParentStudent> data = FXCollections.observableArrayList();

    @FXML
    private void addStudentToParent(ActionEvent event) {
        selectedParent = parentChoiceBox.getValue();
        if (selecteParentStudent != null && selectedParent != null) {
            msg = CoolSuppliesFeatureSet6Controller.addStudentToParent(selecteParentStudent.studentName, selectedParent);
            selecteParentStudent.setParentEmail(selectedParent);
            parentStudentTable.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Student");
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.showAndWait();
        }
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentLabel.setText("Not Selected");
        parentColumn.setCellValueFactory(new PropertyValueFactory<>("parentEmail")); // Match getter method
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName")); // Match getter method

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

        public static void initializeList(List<TOStudent> students, List<TOParent> parents) {
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

        public static ParentStudent getStudentWithName (String studentName) {
            for (ParentStudent parentStudent : parentStudentList) {
                if (Objects.equals(parentStudent.studentName, studentName)) {
                    return parentStudent;
                }
            }
            return null;
        }
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        AnchorPane newPage = loader.load();
        Stage currentStage = (Stage) parentStudentVBox.getScene().getWindow();
        currentStage.setScene(new Scene(newPage));
    }


    @FXML
    private void viewAccounts() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    private void goBack() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
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
}