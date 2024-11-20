package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers; /**
 * Sample Skeleton for 'AddItemToOrder.fxml' Controller Class
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
import javafx.stage.Stage;

public class AddItemToOrderController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="bundlesButtonMenu"
    private Button bundlesButtonMenu; // Value injected by FXMLLoader

    @FXML
    void goToBundlesPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/UpdateBundle.fxml"));
        Parent bundlesPage = loader.load();

        // Get the current stage (window) and set the new scene
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage stage = (Stage) currentScene.getWindow();
        stage.setScene(new Scene(bundlesPage));
        stage.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert bundlesButtonMenu != null : "fx:id=\"bundlesButtonMenu\" was not injected: check your FXML file 'AddItemToOrder.fxml'.";

    }

}
