package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UpdateOrderWindowController implements Initializable {
    @FXML
    private Label idLabel;

    @FXML
    private ChoiceBox<String> studentChoiceBox;

    @FXML
    private ChoiceBox<String> levelChoiceBox;

    @FXML
    private Button BackButton;

    private TOOrder order;
    private String studentName;
    private String level;

    public void setCurrentOrder(TOOrder selOrder) {
        if (selOrder == null) {
            showAlert("Error", "Order not found");
            return;
        }
        order = selOrder;
        idLabel.setText(String.valueOf(order.getNumber()));
        getStudent(order);
        getLevel(order);
    }

    @FXML
    private void updateOrder(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idLabel.getText());
        studentName = studentChoiceBox.getValue();
        level = levelChoiceBox.getValue();

        String msg = CoolSuppliesFeatureSet8Controller.updateOrder(level, id, studentName);

        showAlert("", msg);
        if (msg.equals("The order has successfully been updated.")) {
            goBack();
        }
    }

    @FXML
    private void getStudent(TOOrder order) {
        if (order != null) {
            List<TOStudent> students = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(order.getParentEmail());
            List<String> studentNames = new ArrayList<>();

            for (TOStudent student : students) {
                studentNames.add(student.getName());
            }

            studentChoiceBox.getItems().clear();
            studentChoiceBox.getItems().addAll(studentNames);

            studentName = order.getStudentName();
            studentChoiceBox.setValue(studentName);
        }
    }


    private void getLevel(TOOrder order) {
        if (order != null) {
            level = order.getLevel();
            levelChoiceBox.setValue(level);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> levels = Arrays.asList("Mandatory", "Recommended", "Optional");
        levelChoiceBox.getItems().addAll(levels);
    }

    @FXML
    private void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewOrderWindow.fxml"));
        Scene scene = new Scene(loader.load());

        ViewOrderWindowController viewOrderController= loader.getController();
        Stage stage = (Stage) BackButton.getScene().getWindow();
        viewOrderController.setCurrentOrder(CoolSuppliesFeatureSet8Controller.viewOrder(String.valueOf(order.getNumber())));
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}