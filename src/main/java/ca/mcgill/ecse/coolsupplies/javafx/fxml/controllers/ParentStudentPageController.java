package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.*;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ParentStudentPageController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private Button Add;
    @FXML
    private Label msgLabel;
    @FXML
    private ChoiceBox<TOStudent> studentChoiceBox;
    @FXML
    private ChoiceBox<TOParent> parentChoiceBox;

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    TOStudent selectedStudent;
    TOParent selectedParent;
    List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
    List<TOStudent> students;

    @FXML
    void addStudentToParent(ActionEvent event) {
        try {
            selectedStudent = studentChoiceBox.getValue();
            String msg = CoolSuppliesFeatureSet6Controller.addStudentToParent(selectedStudent.getName(), selectedParent.getEmail());
            msgLabel.setText(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void deleteStudentFromParent(ActionEvent event) {
        try {
            selectedStudent = studentChoiceBox.getValue();
            String msg = CoolSuppliesFeatureSet6Controller.addStudentToParent(selectedStudent.getName(), selectedParent.getEmail());
            msgLabel.setText(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void getParent(ActionEvent event) {
        selectedParent = parentChoiceBox.getValue();
        students = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(selectedParent.getEmail());
        studentChoiceBox.getItems().addAll(students);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        msgLabel.setText("");
        parentChoiceBox.getItems().addAll(parents);
    }
}