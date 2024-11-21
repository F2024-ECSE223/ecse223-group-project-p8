package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class StartOrderWindowController implements Initializable {
    @FXML
    private Label parentLabel;
    @FXML
    private ChoiceBox<TOStudent> studentChoiceBox;
    @FXML
    private ChoiceBox<BundleItem.PurchaseLevel> levelChoiceBox;
    @FXML
    private TextField idTextField;

    Integer id;
    List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();
    List<BundleItem.PurchaseLevel> levels = Arrays.asList(BundleItem.PurchaseLevel.values());

    void selectID(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parentLabel.setText("");
        studentChoiceBox.getItems().addAll(students);
        levelChoiceBox.getItems().addAll(levels);
    }
}
