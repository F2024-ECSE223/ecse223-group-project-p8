package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import ca.mcgill.ecse.coolsupplies.controller.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentsPageController {

    @FXML
    private AnchorPane ap;

    @FXML
    private Button selectButton;

    @FXML
    private ComboBox<String> parentComboBox;

    @FXML
    private TableView<TOStudent> studentTableView;

    @FXML
    private TableColumn<TOStudent, String> nameColumn;

    @FXML
    private TableColumn<TOStudent, String> gradeLevelColumn;

    private ObservableList<TOStudent> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gradeLevelColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));

        // Load parent emails into ComboBox
        loadParents();

        // Add listener to ComboBox selection
        parentComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadStudentsForParent(newValue);
            }
        });
    }

    private void loadParents() {
        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        for (TOParent parent : parents) {
            parentComboBox.getItems().add(parent.getEmail());
        }
    }

    private void loadStudentsForParent(String parentEmail) {
        // Clear existing data
        studentList.clear();

        // Fetch students associated with the selected parent
        List<TOStudent> students = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail);
        studentList.addAll(students);

        // Set items to TableView
        studentTableView.setItems(studentList);
    }

    @FXML
    void handleSelectButtonClick(MouseEvent event) {
        // Get selected student
        TOStudent selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();
        List<TOOrder> studentOrders = new ArrayList<>();

        if (selectedStudent == null) {
            return; // No student selected
        }

        // Check if the student already has an order
        boolean hasOrder = false;
        for (TOOrder order : orders) {
            if (order.getStudentName() == selectedStudent.getName()) {
                hasOrder = true;
                studentOrders.add(order);
            }
        }
        try {
            if (hasOrder) {
                // Navigate to Update/Cancel Order page
                navigateToPage(
                        "ca/mcgill/ecse/coolsupplies/javafx/fxml/pages/UpdateCancelOrderWindow.fxml");
            } else {
                // Navigate to New Order page
                navigateToPage(
                        "ca/mcgill/ecse/coolsupplies/javafx/fxml/pages/StartOrderWindowController.fxml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to navigate to the desired FXML page
    private void navigateToPage(String fxmlPath) throws IOException {
        Stage stage = (Stage) ap.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void UpdateAccountPage(MouseEvent mouseEvent) {
    }

    public void ViewParentsPage(MouseEvent mouseEvent) {
    }
}
