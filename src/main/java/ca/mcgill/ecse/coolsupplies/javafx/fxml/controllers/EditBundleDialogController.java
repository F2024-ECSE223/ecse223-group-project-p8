package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditBundleDialogController {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField bundleNameTextField;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Spinner<Integer> discountSpinner;

    @FXML
    private ChoiceBox<String> gradeDropDown;

    private TOGradeBundle selBundle;

    @FXML
    public void setBundle(TOGradeBundle selBundle){
        this.selBundle = selBundle;
        bundleNameTextField.setText(selBundle.getName());
        gradeDropDown.setValue(selBundle.getGradeLevel());
        discountSpinner.getValueFactory().setValue(selBundle.getDiscount());
    }

    private void cancel(){
        dialogPane.getScene().getWindow().hide();
    }

    private void finish(){
        String updatedGrade = gradeDropDown.getValue();
        Integer updatedDiscount = discountSpinner.getValue();
        String updatedName = bundleNameTextField.getText();
        String message = CoolSuppliesFeatureSet4Controller.updateBundle(selBundle.getName(), updatedName, updatedDiscount, updatedGrade);

        if(!message.equals("The bundle has been successfully updated.")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

    }

    @FXML
    void initialize() {
        assert bundleNameTextField != null : "fx:id=\"bundleNameTextField\" was not injected: check your FXML file 'EditBundleDialog.fxml'.";
        assert dialogPane != null : "fx:id=\"dialogPane\" was not injected: check your FXML file 'EditBundleDialog.fxml'.";
        assert discountSpinner != null : "fx:id=\"discountSpinner\" was not injected: check your FXML file 'EditBundleDialog.fxml'.";
        assert gradeDropDown != null : "fx:id=\"gradeDropDown\" was not injected: check your FXML file 'EditBundleDialog.fxml'.";

        //Find Finish and Cancel buttons
        Button finishButton = (Button) dialogPane.lookupButton(ButtonType.FINISH);
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);

        //If Finish or Cancel buttons clicked, proceed to their respective actions
        finishButton.setOnAction(event -> {
            finish();
        });

        cancelButton.setOnAction(event -> {
            cancel();
        });

        //Initialize Discount Spinner
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1); // Min 1, Max Integer.MAX_VALUE, Initial 1
        discountSpinner.setValueFactory(valueFactory);

        //Initialize Drop-down Menu for Grade
        List<Grade> gradeList = coolSupplies.getGrades();
        for (Grade grade : gradeList) {
            String gradeName = grade.getLevel();
            gradeDropDown.getItems().add(gradeName);
        }
    }

}
