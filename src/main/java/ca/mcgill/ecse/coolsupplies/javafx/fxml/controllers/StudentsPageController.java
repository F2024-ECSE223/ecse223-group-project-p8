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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentsPageController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private TextField studentTextField;
    @FXML
    private Button Add;
    @FXML
    private Label msgLabel;
    @FXML
    private ChoiceBox<TOParent> parentChoiceBox;

    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    String studentName;
    String parentEmail;
    //    TOStudent selectedStudent;
//    TOParent selectedParent;
    List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();

    @FXML
    void addStudentToParent(ActionEvent event) {
        try {
            studentName = studentTextField.getText();
//            selectedStudent = CoolSuppliesFeatureSet2Controller.getStudent(studentName);
            String msg = CoolSuppliesFeatureSet6Controller.addStudentToParent(studentName, parentEmail);
            msgLabel.setText(msg);
//            System.out.println(studentName);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void deleteStudentFromParent(ActionEvent event) {
        try {
            studentName = studentTextField.getText();
            String msg = CoolSuppliesFeatureSet6Controller.deleteStudentFromParent(studentName, parentEmail);
            msgLabel.setText(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void getParent(ActionEvent event) {
        TOParent selectedParent = parentChoiceBox.getValue();
        parentEmail = selectedParent.getEmail();
    }

    @FXML
    void UpdateAccountPage(MouseEvent event) {

    }

    @FXML
    void ViewParentsPage(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parentChoiceBox.getItems().addAll(parents);
    }
}