package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.TOBundleItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller.deleteBundleItem;

/**
 * This class provides the controller methods for the Bundle Items Page for a single bundle
 * This page allows users to view bundle items of any bundle selected from the Bundles page or through the bundles drop-down
 * It also allows users to add/remove a bundle item to a given bundle or update a bundle item's quantity, or purchase level
 *
 * @author Snigdha Sen
 */

public class BundleItemsPageController {
    CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    @FXML
    private Button AddButton;

    @FXML
    private ChoiceBox<String> BundleDropDownMenu;

    @FXML
    private Button BundlesMenuButton;

    @FXML
    private Button ItemsMenuButton;

    @FXML
    private Button ProfileButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button RemoveButton;

    @FXML
    private Button ShopMenuButton;

    @FXML
    private Button UpdateSelectedRowButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private TableColumn<TOBundleItem, String> itemName;

    @FXML
    private TableColumn<TOBundleItem, String> purchaseLvl;

    @FXML
    private TableColumn<TOBundleItem, Integer> qty;

    @FXML
    private TableView<TOBundleItem> table;

    private BundlesController bundlesController;
    private String currBundleName;

    //TO CONNECT WITH OLD PAGE/ SCENE, BUNDLE PAGE ----------------------------------------------------
    /**
     * Sets the bundlesController global variable from the previous "Bundle" controller
     *
     * @param controller BundlesController controller
     */
    public void setBundlesController(BundlesController controller) {
        this.bundlesController = controller;
    }

    /**
     * Sets the name of the bundle we want to edit and puts it in the bundle drop down.
     *
     * @param selBundleName The name of the bundle whose items we want to view/edit
     */
    public void setBundle(String selBundleName){
        this.currBundleName = selBundleName;
        BundleDropDownMenu.setValue(currBundleName);
        updateTable(BundleDropDownMenu.getValue());
    }
    //--------------------------------------------------------------------------------------------------

    //NAVIGATION

    /**
     * Loads a new page
     *
     * @param fxmlPath The relative path to the FXML file in question
     *
     * @throws IOException If there is an error loading the file
     */
    @FXML
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) AddButton.getScene().getWindow();
        currentStage.setScene(scene);
    }

    /**
     * Opens up the dialog to add a bundle item to the current bundle
     *
     * @throws IOException If there is an error loading the file
     *
     */
    @FXML
    void goToAddBundleItemDialog() throws IOException {
        String fxmlPath = "/pages/AddBundleItemDialog.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        DialogPane addBundleItemDialogPane = loader.load();
        AddBundleItemDialogController controller = loader.getController();
        controller.setBundleName(BundleDropDownMenu.getValue());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addBundleItemDialogPane);
        dialog.showAndWait();
        updateTable(BundleDropDownMenu.getValue());
    }

    /**
     * Opens up the dialog to update a bundle item from the current bundle
     *
     * @throws IOException If there is an error loading the file
     * @throws NullPointerException If no row is selected in the table.
     *
     */
    @FXML
    void goToUpdateBundleItemDialog() throws IOException {
        String fxmlPath = "/pages/UpdateBundleItemDialog.fxml";

        try {
            int selIndex = table.getSelectionModel().getSelectedIndex();
            TOBundleItem selBundleItem = table.getSelectionModel().getSelectedItem();

            if (selIndex >= 0 && selBundleItem != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                DialogPane updateBundleItemDialogPane = loader.load();

                UpdateBundleItemDialogController controller = loader.getController();
                controller.setBundleItem(selBundleItem);

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(updateBundleItemDialogPane);
                dialog.showAndWait();
                updateTable(BundleDropDownMenu.getValue());
            }
        }

        //No row was selected
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No row was selected to update.");
            alert.showAndWait();
        }

    }

    /**
     * Go to the Items Page
     *
     * @throws IOException If there is an error loading the file
     */
    @FXML
    void goToItemsPage() throws IOException {
        loadPage("/pages/ItemsShop.fxml");
    }

    /**
     * Go to the New Order Page
     *
     * @throws IOException If there is an error loading the file
     */
    @FXML
    void goToNewOrderPage() throws IOException {
        loadPage("/pages/StartOrderWindow.fxml");
    }



    /**
     * Go to the Profile Section (Account Page) by clicking on the Profile button
     *
     * @throws IOException If there is an error loading the file
     */
    @FXML
    void goToProfilePage() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    /**
     * Go to the back to the Bundles page
     *
     * @throws IOException If there is an error loading the file
     */
    @FXML
    void goBack() throws IOException {
        loadPage("/pages/Bundles.fxml");
    }

    @FXML
    void initialize() {
        assert AddButton != null : "fx:id=\"AddButton\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert BundleDropDownMenu != null : "fx:id=\"BundleDropDownMenu\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert BundlesMenuButton != null : "fx:id=\"BundlesMenuButton\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert ItemsMenuButton != null : "fx:id=\"ItemsMenuButton\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert ProfileButton != null : "fx:id=\"ProfileButton\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert RemoveButton != null : "fx:id=\"RemoveButton\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert ShopMenuButton != null : "fx:id=\"ShopMenuButton\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert UpdateSelectedRowButton != null : "fx:id=\"UpdateSelectedRowButton\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert ap != null : "fx:id=\"ap\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert itemName != null : "fx:id=\"itemName\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert purchaseLvl != null : "fx:id=\"purchaseLvl\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";
        assert qty != null : "fx:id=\"qty\" was not injected: check your FXML file 'BundleItemsPage.fxml'.";

        //Initialize Drop-down Menu for BundleItems
        List<GradeBundle> bundlesList = coolSupplies.getBundles();
        for (GradeBundle bundle : bundlesList) {
            String name = bundle.getName();
            BundleDropDownMenu.getItems().add(name);
        }

        //Listener for Drop-down menu
        BundleDropDownMenu.setOnAction((event) -> {
            int selIndex = BundleDropDownMenu.getSelectionModel().getSelectedIndex();
            Object selBundle = BundleDropDownMenu.getSelectionModel().getSelectedItem();

            if(selIndex >= 0 && selBundle != null) {
                updateTable(BundleDropDownMenu.getValue());
            }
        });
    }

    /**
     * Updates the TableView to show all bundle items of a given bundle.
     *
     * @param bundleName The name of the bundle in question
     *
     */
    private void updateTable(String bundleName) {
        List<TOBundleItem> bundleItems = ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet5Controller.getBundleItems(bundleName);

        if (!bundleItems.isEmpty()) {
            itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            purchaseLvl.setCellValueFactory(new PropertyValueFactory<>("level"));
            qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            table.setItems(FXCollections.observableArrayList(bundleItems));
        }

        //CLEAR IF EMPTY BUNDLE SELECTED
        else {
            table.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * Removes the selected bundle item from the current bundle and the tableview.
     *
     * @throws NullPointerException No row was selected to be removed from the tableview
     *
     */
    @FXML
    public void removeSelectedItem() {
        try {
            int selIndex = table.getSelectionModel().getSelectedIndex();
            TOBundleItem selBundleItem = table.getSelectionModel().getSelectedItem();
            String bundleItemName = selBundleItem.getItemName();
            String bundleName = selBundleItem.getGradeBundleName();

            //selected row is valid
            if (selIndex >= 0 && selBundleItem != null) {
                deleteBundleItem(bundleItemName, bundleName);
                table.getItems().remove(selBundleItem);
            }

        }

        //no row was selected --> show alert
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No row was selected to remove.");
            alert.showAndWait();
        }
    }
}