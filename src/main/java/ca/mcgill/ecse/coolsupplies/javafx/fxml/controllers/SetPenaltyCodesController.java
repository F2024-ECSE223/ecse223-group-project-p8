package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    public Button payButton;

    @FXML
    public Button payPenaltyButton;

    @FXML
    public TextField paymentCode;

    @FXML
    public TextField penaltyCode;

    private TOOrder currOrder;

    public void setItem(TOOrder currOrder) {
        this.currOrder = currOrder;

        payButton.setOnAction(event -> {
            try {
                handlePayButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        payPenaltyButton.setOnAction(event -> {
            try {
                handlePayPenaltyButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        cancelButton.setOnAction(event -> {
            try {
                handleCancelButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void initialize() {
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handlePayButton() throws IOException {
        String payment = paymentCode.getText();
        int orderNb = currOrder.getNumber();

        if (payment.length() != 4) {
            showAlert("Error", "Authorization code invalid.");
            return;
        }

        String msg = CoolSuppliesFeatureSet8Controller.payOrder(String.valueOf(orderNb), payment);

        showAlert("", msg);
        paymentCode.setText("");
        penaltyCode.setText("");

        if (msg.equals("Done")) {
            exitWindow();
        }
    }

    private void handlePayPenaltyButton() throws IOException {
        String payment = paymentCode.getText();
        String penalty = penaltyCode.getText();
        int orderNb = currOrder.getNumber();

        if (payment.length() != 4) {
            showAlert("Error", "Authorization code invalid.");
            return;
        }

        if (penalty.length() != 4) {
            showAlert("Error", "Penalty authorization code invalid.");
            return;
        }
        
        String msg = CoolSuppliesFeatureSet8Controller.payPenaltyForOrder(String.valueOf(orderNb), penalty, payment);

        showAlert("", msg);
        paymentCode.setText("");
        penaltyCode.setText("");

        if (msg.equals("Done")) {
            exitWindow();
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