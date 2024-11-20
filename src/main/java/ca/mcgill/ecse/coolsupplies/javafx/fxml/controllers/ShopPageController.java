/**
 * Sample Skeleton for 'ShopPage.fxml' Controller Class
 */

/**
 * Sample Skeleton for 'ShopPage.fxml' Controller Class
 */

package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShopPageController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // fx:id="chooseItemLabel"
    private Label chooseItemLabel; // Value injected by FXMLLoader

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ap"
    private AnchorPane ap; // Value injected by FXMLLoader

    @FXML
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) chooseItemLabel.getScene().getWindow();
        currentStage.setScene(scene);
    }

    @FXML
    void goToItemsPage() throws IOException {
        loadPage("/pages/AddItems.fxml");
    }

    @FXML
    void goToShopPage(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert ap != null : "fx:id=\"ap\" was not injected: check your FXML file 'ShopPage.fxml'.";

    }

}