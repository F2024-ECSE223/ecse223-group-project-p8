package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet7Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGrade;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
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

    public void initialize() {
        c_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        c_grade.setCellValueFactory(new PropertyValueFactory<>("GradeLevel"));
        populateStudents();
    }

    public void populateStudents() {
        ObservableList<TOStudent> studentList = FXCollections.observableArrayList();

        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();
        studentList.addAll(students);

        studentTable.setItems(studentList);
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<TOStudent, Void>, TableCell<TOStudent, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<TOStudent, Void> call(final TableColumn<TOStudent, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("View");

                    {
                        btn.setOnAction(event -> {
                            TOStudent student = getTableView().getItems().get(getIndex());
                            try {
                                updateStudent(event,student);
                            } catch (IOException e) {
                                showAlert("","error: can not update this student.");
                            }
                            // Add your logic here
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
            }
        };

        c_edit.setCellFactory(cellFactory);
    }

    @FXML
    private void AddNewStudent(ActionEvent event) throws Exception {
        List<TOGrade> grades = CoolSuppliesFeatureSet7Controller.getGrades();
        if(grades.isEmpty()){
            showAlert("","There is no Grade in the system, please create a grade first.");
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AddAndUpdateStudent.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBack(ActionEvent event) {
    }

    @FXML
    private void updateStudent(ActionEvent event,TOStudent student) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AddAndUpdateStudent.fxml"));
        Scene scene = new Scene(loader.load());

        // get AddAndUpdateSdtuents controller and set the current student
        AddAndUpdateStudentController controller = loader.getController();
        controller.setCurrentStudent(student);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void deleteStudent(ActionEvent event) throws IOException {
        String name = studentIndex.getText();
        String msg = CoolSuppliesFeatureSet2Controller.deleteStudent(name);
        showAlert("",msg);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewAllStudents.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

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

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) studentTable.getScene().getWindow();
        currentStage.setScene(scene);
    }
}
