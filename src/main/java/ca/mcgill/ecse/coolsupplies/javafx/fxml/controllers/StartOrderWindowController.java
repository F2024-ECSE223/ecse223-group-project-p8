package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.*;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class StartOrderWindowController implements Initializable {

    @FXML
    private ChoiceBox<String> parentChoiceBox;
    @FXML
    private ChoiceBox<String> studentChoiceBox;
    @FXML
    private ChoiceBox<BundleItem.PurchaseLevel> levelChoiceBox;
    @FXML
    private TextField idTextField;
    @FXML
    private Label idLabel;

    int newID;
    TOStudent selectedStudent;
    TOParent selectedParent;
    List<TOParent> parents = CoolSuppliesFeatureSet1Controller.getParents();
    List<TOStudent> students;
    List<BundleItem.PurchaseLevel> levels = Arrays.asList(BundleItem.PurchaseLevel.values());
    List<Integer> ids = new ArrayList<>();

    @FXML
    void selectID(ActionEvent event) {
        try {
            newID = Integer.parseInt(idTextField.getText());
            for(Integer id : ids) {
                if(newID == id) {
                    System.out.println("duplicate id");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    @FXML
    void goToAccount() {

    }

    void getParent(ActionEvent event) {
        selectedParent = CoolSuppliesFeatureSet1Controller.getParent(parentChoiceBox.getValue());
        if (selectedParent != null) {
            students = CoolSuppliesFeatureSet6Controller.getStudentsOfParent(selectedParent.getEmail());
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
        String nextID = String.valueOf(ids.get(ids.size() - 1) + 1);

        idLabel.setText(nextID);
        idTextField.setText(nextID);
        parentChoiceBox.getItems().addAll(parentNames);
        levelChoiceBox.getItems().addAll(levels);
        parentChoiceBox.setOnAction(this::getParent);
    }
}
