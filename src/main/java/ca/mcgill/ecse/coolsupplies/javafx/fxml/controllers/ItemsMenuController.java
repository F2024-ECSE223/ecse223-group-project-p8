package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ResourceBundle;

public class ItemsMenuController {
    @FXML Button addButton;

    //gridpane, pane, button, vbox,
    void initialize () {
        addButton.setOnAction(event -> {
            try {
                handleAddButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

    @FXML
    private void handleAddButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/AddItemPopup.fxml"));
        Scene popUp = new Scene(loader.load());
        Stage popUp1 = new Stage();
        popUp1.setScene(popUp);
        popUp1.show();

    }
}
