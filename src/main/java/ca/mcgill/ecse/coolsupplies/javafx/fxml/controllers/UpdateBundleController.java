package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;
/**
 * Sample Skeleton for 'UpdateBundle.fxml' Controller Class
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UpdateBundleController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="AddBundleButton"
    private Button AddBundleButton; // Value injected by FXMLLoader

    @FXML // fx:id="DeleteBundleButton"
    private Button DeleteBundleButton; // Value injected by FXMLLoader

    @FXML // fx:id="EditBundleButton"
    private Button EditBundleButton; // Value injected by FXMLLoader

    @FXML // fx:id="ItemsButton"
    private Button ItemsButton; // Value injected by FXMLLoader

    @FXML // fx:id="ShopButton"
    private Button ShopButton; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        assert AddBundleButton != null : "fx:id=\"AddBundleButton\" was not injected: check your FXML file 'UpdateBundle.fxml'.";
        assert DeleteBundleButton != null : "fx:id=\"DeleteBundleButton\" was not injected: check your FXML file 'UpdateBundle.fxml'.";
        assert EditBundleButton != null : "fx:id=\"EditBundleButton\" was not injected: check your FXML file 'UpdateBundle.fxml'.";
        assert ItemsButton != null : "fx:id=\"ItemsButton\" was not injected: check your FXML file 'UpdateBundle.fxml'.";
        assert ShopButton != null : "fx:id=\"ShopButton\" was not injected: check your FXML file 'UpdateBundle.fxml'.";

    }

    @FXML
    private void addBundle(ActionEvent event) {

    }

    @FXML
    private void deleteBundle(ActionEvent event) {

    }

    @FXML
    private void editBundle(ActionEvent event) {

    }

    @FXML
    void goToItemsPage(ActionEvent event) {

    }

    @FXML
    public void goToShopPage(ActionEvent event) throws IOException {
        loadPage("/pages/AddItemToOrder.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) AddBundleButton.getScene().getWindow();
        stage.setScene(scene);
    }

}
