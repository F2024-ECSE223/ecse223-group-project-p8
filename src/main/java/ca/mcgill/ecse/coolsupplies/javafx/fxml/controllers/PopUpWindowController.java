package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PopUpWindowController {

    @FXML
    private Text messageText;
    @FXML
    private Button closeButton;

    public void setMessage(String message) {
        messageText.setText(message);
    }

    @FXML
    private void closeWindow(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AccountPage.fxml"));
        Scene scene = new Scene(loader.load());
        Stage accountStage = new Stage();
        accountStage.setScene(scene);
        accountStage.show();
    }
}