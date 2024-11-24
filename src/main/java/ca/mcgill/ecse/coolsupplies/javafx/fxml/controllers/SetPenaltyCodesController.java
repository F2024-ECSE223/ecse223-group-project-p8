package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.TOItem;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import java.awt.*;
import java.io.IOException;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * FXML controller class that allows to set the penalty and payment codes for an order
 * @author Artimice Mirchi
 */
public class SetPenaltyCodesController {
    @FXML
    private AnchorPane ap;

    @FXML
    public Button cancelButton;

    @FXML
    public Button confirmButton;

    @FXML
    public TextField paymentCode;

    @FXML
    public TextField penaltyCode;

    private Order currOrder;

    private TOOrder currOrder1;

    public void setItem(TOOrder currOrder1) {
        this.currOrder1 = currOrder1;


    }


    public void initialize() {
        confirmButton.setOnAction(event -> handleConfirmButton());

        cancelButton.setOnAction(event -> {
            try {
                handleCancelButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private void handleConfirmButton() {
        String payment = paymentCode.getText();
        String penalty = penaltyCode.getText();
        int orderNb = currOrder1.getNumber();
        //Order order = getOrder(orderNb);
        if (!payment.isEmpty() && currOrder.startSchoolYear() == false && payment.length() == 4) {
            currOrder1.setAuthorizationCode(payment);
        }

        else if (currOrder.startSchoolYear() == true && !payment.isEmpty() && !penalty.isEmpty() && payment.length() == 4 && penalty.length() == 4) {
            currOrder1.setPenaltyAuthorizationCode(penalty);
            currOrder1.setAuthorizationCode(payment);
        }

    }


    private void handleCancelButton() throws IOException {
        exitWindow();
    }

    private void exitWindow() throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.hide();
//change the loader...
    }


}
