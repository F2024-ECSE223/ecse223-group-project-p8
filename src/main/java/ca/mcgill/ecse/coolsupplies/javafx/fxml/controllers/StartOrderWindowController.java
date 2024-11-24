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

    @FXML
    private Label idLabel;

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
        Date date = Date.valueOf(datePicker.getValue());
        String level = levelChoiceBox.getValue();

        if (studentName != null && parentEmail != null && level != null) {
            CoolSuppliesFeatureSet6Controller.startOrder(id, date, level, parentEmail, studentName);

            ids.add(id);
            id += 1;

            idLabel.setText(String.valueOf(id));
            parentChoiceBox.setValue(null);
            studentChoiceBox.setValue(null);
            levelChoiceBox.setValue(null);
            datePicker.setValue(LocalDate.now());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Created");
            alert.setHeaderText(null);
            alert.setContentText("A new order has been successfully created and added to the system.");
            alert.showAndWait();

            reloadOrders();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Incomplete");
            alert.setHeaderText(null);
            alert.setContentText("Please ensure all required fields are selected before placing the order.");
            alert.showAndWait();
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

        if (!ids.isEmpty()) {
            id = ids.get(ids.size() - 1) + 1;
        } else {
            id = 1;
        }

        idLabel.setText(String.valueOf(id));
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
