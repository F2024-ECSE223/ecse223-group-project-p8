package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers; /**
 * Sample Skeleton for 'AddItemToBundle.fxml' Controller Class
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AddItemToBundleController {
    @FXML
    private Button ItemsButtonMenu;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Property"
    private Spinner<?> Property; // Value injected by FXMLLoader

    @FXML // fx:id="bundlesButtonMenu"
    private Button bundlesButtonMenu; // Value injected by FXMLLoader

    @FXML // fx:id="trash"
    private ImageView trash; // Value injected by FXMLLoader

    @FXML
    void goToBundlesPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BundlesPage.fxml"));
        Parent bundlesPage = loader.load();

        // Get the current stage (window) and set the new scene
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage stage = (Stage) currentScene.getWindow();
        stage.setScene(new Scene(bundlesPage));
        stage.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert Property != null : "fx:id=\"Property\" was not injected: check your FXML file 'AddItemToBundle.fxml'.";
        assert bundlesButtonMenu != null : "fx:id=\"bundlesButtonMenu\" was not injected: check your FXML file 'AddItemToBundle.fxml'.";
        assert trash != null : "fx:id=\"trash\" was not injected: check your FXML file 'AddItemToBundle.fxml'.";

    }

}
