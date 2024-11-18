package ca.mcgill.ecse.coolsupplies.controllers; /**
 * Sample Skeleton for 'AddItemToBundle.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;

public class AddItemToBundleController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Property"
    private Spinner<?> Property; // Value injected by FXMLLoader

    @FXML // fx:id="trash"
    private ImageView trash; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert Property != null : "fx:id=\"Property\" was not injected: check your FXML file 'AddItemToBundle.fxml'.";
        assert trash != null : "fx:id=\"trash\" was not injected: check your FXML file 'AddItemToBundle.fxml'.";

    }

}
