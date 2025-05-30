package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * This class provides the controller methods for the Add Bundle Dialog on the Bundles Page.
 *
 * @author Jyothsna Seema
 */

public class AddBundleDialogController {
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
    private ChoiceBox<String> gradeDropDown;

    @FXML
    private Spinner<Integer> discountSpinner;

    /**
     * (Cancel Button) Discards the current population of the fields and closes the dialog window.
     */
    private void cancel(){dialogPane.getScene().getWindow().hide();}

    /**
     * (Finish Button) Finalizes the fields entered for adding a new bundle and adds that bundle to the system in the backend.
     */
    private void finish(){
        String grade = gradeDropDown.getValue();
        Integer discount = discountSpinner.getValue();
        String bundleName = bundleNameTextField.getText();
        String message = CoolSuppliesFeatureSet4Controller.addBundle( bundleName, discount, grade);

        if(!message.equals("The bundle has been added successfully.")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller by setting up the dropdown, spinner, text field and the finish and cancel buttons.
     */
    @FXML
    void initialize() {
        assert bundleNameTextField != null : "fx:id=\"bundleNameTextField\" was not injected: check your FXML file 'AddBundleDialog.fxml'.";
        assert dialogPane != null : "fx:id=\"dialogPane\" was not injected: check your FXML file 'AddBundleDialog.fxml'.";
        assert gradeDropDown != null : "fx:id=\"gradeDropDown\" was not injected: check your FXML file 'AddBundleDialog.fxml'.";

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
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        discountSpinner.setValueFactory(valueFactory);

        //Initialize Drop-down Menu for Grade
        List<Grade> gradeList = coolSupplies.getGrades();
        for (Grade grade : gradeList) {
            String gradeName = grade.getLevel();
            gradeDropDown.getItems().add(gradeName);
        }
    }

}

