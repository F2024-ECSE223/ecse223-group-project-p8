package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

public class StartOrderWindowController implements Initializable {

    @FXML
    private ChoiceBox<String> parentChoiceBox;

    @FXML
    private ChoiceBox<String> studentChoiceBox;

    @FXML
    private ChoiceBox<String> levelChoiceBox;

//    @FXML
//    private Label idLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button NewOrderButton;

    private int id;
    private String parentEmail;
    private static final List<String> levels = Arrays.asList("Mandatory", "Recommended", "Optional");
    private List<Integer> ids = new ArrayList<>();

    @FXML
    private void placeOrder(ActionEvent event) {
        String studentName = studentChoiceBox.getValue();
        String level = levelChoiceBox.getValue();
        String input = idTextField.getText();

        if (input.isEmpty()) {
            showAlert("Invalid Input", "Order ID cannot be empty.");
            return;
        }

        try {
            id = Integer.parseInt(input);
            if (id <= 0) {
                showAlert("Invalid Input", "Order ID must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Order ID must be a valid number.");
            idTextField.clear();
            return;
        }

        if (ids.contains(id)) {
            showAlert("Invalid Input", "Order ID already exists. Please use a unique ID.");
            return;
        }

        if (studentName != null && parentEmail != null && level != null) {
            Date date = Date.valueOf(datePicker.getValue());
            CoolSuppliesFeatureSet6Controller.startOrder(id, date, level, parentEmail, studentName);

            ids.add(id); // Add the new ID to the list
            showAlert("Order Created", "A new order has been successfully created and added to the system.");

            // Reset all fields to initial state
            parentChoiceBox.setValue(null);
            studentChoiceBox.setValue(null);
            levelChoiceBox.setValue(null);
            datePicker.setValue(LocalDate.now());
            idTextField.clear(); // Clear the ID field explicitly

            reloadOrders(); // Reload orders to keep the internal state consistent
        } else {
            showAlert("Selection Incomplete", "Please ensure all required fields are selected before placing the order.");
        }
    }



    private void getParent(ActionEvent event) {
        parentEmail = parentChoiceBox.getValue();
        if (parentEmail != null) {
            List<TOStudent> students = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail);
            List<String> studentNames = new ArrayList<>();
            for (TOStudent student : students) {
                studentNames.add(student.getName());
            }
            studentChoiceBox.getItems().clear();
            studentChoiceBox.getItems().addAll(studentNames);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
        List<String> parentNames = new ArrayList<>();
        for (TOParent parent : parents) {
            parentNames.add(parent.getEmail());
        }

        reloadOrders();

        parentChoiceBox.getItems().addAll(parentNames);
        levelChoiceBox.getItems().addAll(levels);
        datePicker.setValue(LocalDate.now());

        parentChoiceBox.setOnAction(this::getParent);
    }

    private void reloadOrders() {
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();
        ids.clear();
        for (TOOrder order : orders) {
            ids.add(order.getNumber());
        }
        Collections.sort(ids);

//        if (!ids.isEmpty()) {
//            id = ids.get(ids.size() - 1) + 1;
//        } else {
//            id = 1;
//        }
//
//        idLabel.setText(String.valueOf(id));
        idTextField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) NewOrderButton.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void goToAccount() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    void goToItems() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
    }

    @FXML
    void goToBundles() throws IOException {
        loadPage("/pages/Bundles.fxml");
    }

    @FXML
    private void goToNewOrder() throws IOException{
        loadPage("/pages/StartOrderWindow.fxml");
    }
}
