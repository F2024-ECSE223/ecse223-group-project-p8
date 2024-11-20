package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers; /**
 * Sample Skeleton for 'Bundles.fxml' Controller Class
 */

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BundlesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML
    private Button AddBundleButton;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    void addDialog() throws IOException{
        loadPage("/pages/AddBundleDialog.fxml");
    }

    @FXML
    void goToAccount() {

    }

    @FXML
    void goToItems() {

    }

    @FXML
    private void goToShop() throws IOException{
        loadPage("/pages/AddItemToOrder.fxml");
    }

    @FXML
    void manageDialog() {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) AddBundleButton.getScene().getWindow();
        stage.setScene(scene);
    }

}
