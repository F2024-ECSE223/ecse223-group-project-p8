package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.TOGradeBundle;
import ca.mcgill.ecse.coolsupplies.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller.*;

/**
 * This class provides the controller methods for the Bundles Page
 *
 * @author Jyothsna Seema
 */

public class BundlesController {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddBundleButton;

    @FXML
    private Button EditBundleButton;

    @FXML
    private Button ItemsMenuButton;

    @FXML
    private Button ManageItemsButton;

    @FXML
    private Button NewOrderMenuButton;

    @FXML
    private Button RemoveBundleButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private TableColumn<TOGradeBundle, String> bundleName;

    @FXML
    private TableColumn<TOGradeBundle, Integer> discount;

    @FXML
    private TableColumn<TOGradeBundle, String> gradeLvl;

    @FXML
    private Button ProfileButton;

    @FXML
    private TableView<TOGradeBundle> table;

    // method that sets the discount of a bundle
    public void setBundleDiscount() {
        //set discount 0 if single-item or empty bundle
        List<GradeBundle> bList = coolSupplies.getBundles();
        for(GradeBundle b : bList){
            if(b.getBundleItems().size() <= 1){
                b.setDiscount(0);
            }
        }
    }



    // helper method for loading pages
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) AddBundleButton.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void goToAddBundleDialog() throws IOException {
        String fxmlPath = "/pages/AddBundleDialog.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        DialogPane addBundleDialogPane = loader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addBundleDialogPane);
        dialog.showAndWait();
        updateTable();
    }

    @FXML
    void goToEditBundleDialog() throws IOException {
        String fxmlPath = "/pages/EditBundleDialog.fxml";

        try {
            int selIndex = table.getSelectionModel().getSelectedIndex();
            TOGradeBundle selBundle = table.getSelectionModel().getSelectedItem();

            if (selIndex >= 0 && selBundle != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                DialogPane EditBundleDialogPane = loader.load();

                EditBundleDialogController controller = loader.getController();
                controller.setBundle(selBundle);

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(EditBundleDialogPane);
                dialog.showAndWait();
                updateTable();
            }
        }

        //No row was selected
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No row was selected to edit.");
            alert.showAndWait();
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        assert AddBundleButton != null : "fx:id=\"AddBundleButton\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert EditBundleButton != null : "fx:id=\"EditBundleButton\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert ItemsMenuButton != null : "fx:id=\"ItemsMenuButton\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert ManageItemsButton != null : "fx:id=\"ManageItemsButton\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert NewOrderMenuButton != null : "fx:id=\"NewOrderMenuButton\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert RemoveBundleButton != null : "fx:id=\"RemoveBundleButton\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert ap != null : "fx:id=\"ap\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert bundleName != null : "fx:id=\"bundleName\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert discount != null : "fx:id=\"discount\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert gradeLvl != null : "fx:id=\"gradeLvl\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert ProfileButton != null : "fx:id=\"ProfileButton\" was not injected: check your FXML file 'Bundles.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Bundles.fxml'.";

        setBundleDiscount();
        updateTable();
    }

    private void updateTable() {
        setBundleDiscount();

        List<TOGradeBundle> bundles = ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller.getBundles();

        if (!bundles.isEmpty()) {
            bundleName.setCellValueFactory(new PropertyValueFactory<>("name"));
            gradeLvl.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
            discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

            table.setItems(FXCollections.observableArrayList(bundles));
        }

        //CLEAR IF EMPTY BUNDLE SELECTED
        else {
            table.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    public void removeSelectedBundle() {
        try {
            int selIndex = table.getSelectionModel().getSelectedIndex();
            TOGradeBundle selBundle = table.getSelectionModel().getSelectedItem();
            String bundleName = selBundle.getName();

            //selected row is valid
            if (selIndex >= 0 && selBundle != null) {
                deleteBundle(bundleName);
                table.getItems().remove(selBundle);
            }
        }

        //no row was selected --> show alert
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No row was selected to remove.");
            alert.showAndWait();
        }
    }

    // handle "Manage Items" button
    @FXML
    private void goToBundleItems() throws IOException {
        TOGradeBundle selBundle = table.getSelectionModel().getSelectedItem();
        int selIndex = table.getSelectionModel().getSelectedIndex();

        if(selIndex >= 0 && selBundle != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/BundleItemsPage.fxml"));
            Scene scene = new Scene(loader.load());

            BundleItemsPageController controller = loader.getController();
            controller.setBundle(selBundle.getName());
            controller.setBundlesController(this);

            Stage stage = (Stage) AddBundleButton.getScene().getWindow();
            stage.setScene(scene);
        }

        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No row was selected to manage.");
            alert.showAndWait();
        }
    }

    // handle menu buttons
    @FXML
    void goToAccount() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    void goToItems() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
    }

    @FXML
    private void goToNewOrder() throws IOException{
        loadPage("/pages/StartOrderWindow.fxml");
    }

}
