package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class ViewAllStudentsController {

    @FXML private VBox studentListVBox;

    private static int ID = 1;

    private static final int ID_GAP = 30;
    private static final int NAME_GAP = 100;
    private static final int GRADE_GAP = 120;
    private static final int MODIFY_GAP = 100;
    private static final int DELETE_GAP = 100;

    public void initialize() {
        ID = 1;
        populateOrders();

    }

    public void populateOrders() {
        adjustContentWidth();
        // Fetch the list of orders from the OrderController
        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();

        // Clear the VBox to avoid duplication if called multiple times
        studentListVBox.getChildren().clear();

        studentListVBox.getChildren().add(createHeaderRow());
        // Iterate through the orders and add them to the VBox
        for (TOStudent toStudent : students) {
            Student student = Student.getWithName(toStudent.getName());
            HBox studentRow = createOrderRow(student);
            studentListVBox.getChildren().add(studentRow);
        }
    }

    private HBox createOrderRow(Student student) {
        // Create an HBox for each order
        HBox studentRow = new HBox();
        studentRow.setSpacing(10); // Adjust spacing between columns

        // Create labels for each column
        Label id = new Label(String.valueOf(ID));
        id.setPrefWidth(ID_GAP);
        Label nameLabel = new Label(String.valueOf(student.getName()));
        nameLabel.setPrefWidth(NAME_GAP);
        Label gradeLabel = new Label(String.valueOf(student.getGrade().getLevel()));
        gradeLabel.setPrefWidth(GRADE_GAP);
        Button modifyButton = new Button("modify");
        modifyButton.setPrefWidth(MODIFY_GAP);
        Button deleteButton = new Button("delete");
        deleteButton.setPrefWidth(DELETE_GAP);

        modifyButton.setOnAction(event ->
        {
            try {
                modifyStudent(event, student);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        deleteButton.setOnAction(event ->
        {
            try {
                deleteStudent(event,student);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        ID = ID + 1;
        // Add all labels to the HBox
        studentRow.getChildren().addAll(
                id,
                nameLabel,
                gradeLabel,
                modifyButton,
                deleteButton
        );

        return studentRow;
    }

    private HBox createHeaderRow() {
        // Create an HBox for the header
        HBox headerRow = new HBox();
        headerRow.setSpacing(10); // Adjust spacing between columns
        headerRow.setStyle("-fx-font-weight: bold; -fx-background-color: #f0f0f0;"); // Optional styling

        // Create labels for each column header
        Label id = new Label("ID");
        id.setPrefWidth(ID_GAP);
        Label nameLabel = new Label("Name");
        nameLabel.setPrefWidth(NAME_GAP);
        Label gradeLabel = new Label("Grade");
        gradeLabel.setPrefWidth(GRADE_GAP);
        Label modifyLabel = new Label("+");
        modifyLabel.setPrefWidth(MODIFY_GAP);
        Label deleteLabel = new Label("+");
        deleteLabel.setPrefWidth(DELETE_GAP);


        // Add all labels to the HBox
        headerRow.getChildren().addAll(
                id,
                nameLabel,
                gradeLabel,
                modifyLabel,
                deleteLabel
        );

        return headerRow;
    }

    private void adjustContentWidth() {
        double totalWidth = ID_GAP+NAME_GAP+GRADE_GAP+MODIFY_GAP+DELETE_GAP; // Sum of all column widths
        studentListVBox.setPrefWidth(totalWidth); // Set the total width of the VBox
    }


    @FXML
    private void AddNewStudent(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AddAndUpdateStudent.fxml"));
        Scene scene = new Scene(loader.load());

        // get AddAndUpdateStudents controller and set the previous stage
        AddAndUpdateStudentController controller = loader.getController();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    @FXML
    private void goBack(ActionEvent event) {
    }

    @FXML
    private void modifyStudent(ActionEvent event,Student student) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AddAndUpdateStudent.fxml"));
        Scene scene = new Scene(loader.load());

        // get AddAndUpdateSdtuents controller and set the current student
        AddAndUpdateStudentController controller = loader.getController();
        controller.setCurrentStudent(student);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void deleteStudent(ActionEvent event,Student student) throws IOException {
        CoolSuppliesFeatureSet2Controller.deleteStudent(student.getName());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewAllStudents.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void viewAccounts() throws IOException {
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
    private void viewAssociations() throws IOException {
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
        Stage currentStage = (Stage) studentListVBox.getScene().getWindow();
        currentStage.setScene(scene);
    }


}
