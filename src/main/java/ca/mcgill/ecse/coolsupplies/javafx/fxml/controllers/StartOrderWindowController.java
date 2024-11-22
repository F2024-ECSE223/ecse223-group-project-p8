package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

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

    int id;
    String parentEmail;
    List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
    List<TOStudent> students;
    List<String> levels = Arrays.asList("Mandatory", "Recommended", "Optional");
    List<Integer> ids = new ArrayList<>();


    @FXML
    void placeOrder(ActionEvent event) {
        String studentName = studentChoiceBox.getValue();
        Date date = Date.valueOf(datePicker.getValue());
        String level = levelChoiceBox.getValue();
        if (studentName != null && parentEmail != null && level != null) {
            CoolSuppliesFeatureSet6Controller.startOrder(id,date,level, parentEmail,studentName);

            ids.add(id);
            id += 1;
            idLabel.setText(String.valueOf(id));
            parentChoiceBox.setValue(null);
            studentChoiceBox.setValue(null);
            levelChoiceBox.setValue(null);
            datePicker.setValue(LocalDate.now());

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Incomplete");
            alert.setContentText("Please ensure all required fields are selected before placing the order.");
            alert.showAndWait();
        }
    }



    @FXML
    void goToAccount() {

    }

    void getParent(ActionEvent event) {
        parentEmail = parentChoiceBox.getValue();
        if (parentEmail != null) {
            students = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(parentEmail);
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
        List<String> parentNames = new ArrayList<>();
        for (TOParent parent : parents) {
            parentNames.add(parent.getEmail());
        }

        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();
        for (TOOrder order : orders) {
            ids.add(order.getNumber());
        }
        Collections.sort(ids);
        id = ids.get(ids.size() - 1) + 1;

        idLabel.setText(String.valueOf(id));
        parentChoiceBox.getItems().addAll(parentNames);
        levelChoiceBox.getItems().addAll(levels);
        datePicker.setValue(LocalDate.now());

        parentChoiceBox.setOnAction(this::getParent);
    }
}
