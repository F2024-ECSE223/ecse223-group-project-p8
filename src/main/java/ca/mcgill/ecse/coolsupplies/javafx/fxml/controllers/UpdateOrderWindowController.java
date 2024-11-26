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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UpdateOrderWindowController implements Initializable {
    @FXML
    private ChoiceBox<String> idChoiceBox;

    @FXML
    private ChoiceBox<String> studentChoiceBox;

    @FXML
    private ChoiceBox<String> levelChoiceBox;

    @FXML
    private Button BackButton;

    private TOOrder order;
    String studentName;
    String level;

    public void setCurrentOrder(TOOrder order) {
        Integer selOrderID = order.getNumber();
        idChoiceBox.setValue(String.valueOf(selOrderID));
    }

    @FXML
    private void updateOrder(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idChoiceBox.getValue());
        studentName = studentChoiceBox.getValue();
        level = levelChoiceBox.getValue();

        String msg = CoolSuppliesFeatureSet8Controller.updateOrder(level, id, studentName);

        showAlert("", msg);
        if (msg.equals("The order has successfully been updated.")) {
            goBack();
        }
    }

    @FXML
    private void getStudent(ActionEvent event) {

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


    private void getLevel(ActionEvent event) {

        if (order != null) {
            level = order.getLevel();
            levelChoiceBox.setValue(level);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();
        List<String> ids = new ArrayList<>();
        List<String> levels = Arrays.asList("Mandatory", "Recommended", "Optional");

        for (TOOrder order : orders) {
            ids.add(String.valueOf(order.getNumber()));
        }

        Collections.sort(ids);
        idChoiceBox.getItems().addAll(ids);

        levelChoiceBox.getItems().addAll(levels);

        idChoiceBox.setOnAction(event -> {
            order = CoolSuppliesFeatureSet8Controller.viewOrder(idChoiceBox.getValue());
            getStudent(event);
            getLevel(event);
        });
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        AnchorPane newPage = loader.load();
        Stage currentStage = (Stage) BackButton.getScene().getWindow();
        currentStage.setScene(new Scene(newPage));
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