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

    // Setter method for bundlesController
    public void setBundlesController(BundlesController controller) {
        this.bundlesController = controller;
    }

    //ERROR - THE BUNDLE SHOULD COME FROM PASSED DOWN INFO FROM THE PREVIOUS CONTROLLER INITIALLY, AND THEN THE DROPDOWN MENU
    private String currBundleName;
    public void setBundle(String selBundleName){
        this.currBundleName = selBundleName;
        BundleDropDownMenu.setValue(currBundleName);
        updateTable(BundleDropDownMenu.getValue());
    }

    //NAVIGATION
    @FXML
    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage currentStage = (Stage) AddButton.getScene().getWindow();
        currentStage.setScene(scene);
    }

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

        /**
        //----------------------TEST---------------------------------------------
        printBundleDetails();
         **/
    }

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

/**
        //-------------------------Test------------------------------------
        printBundleDetails();
 **/
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
        /**
        //----------------TEST-----------------------------------------------------------------------
        printBundleDetails();
         **/
/**
        //-------------------------------------TESTING PURPOSES ONLY--------------------------------
        BundleItem.PurchaseLevel rec = BundleItem.PurchaseLevel.valueOf("Recommended");
        BundleItem.PurchaseLevel mand = BundleItem.PurchaseLevel.valueOf("Mandatory");

        Grade grade1 = new Grade("grade1", coolSupplies);
        Grade grade2 = new Grade("grade2", coolSupplies);
        Grade grade3 = new Grade("grade3", coolSupplies);

        GradeBundle bundle1 = new GradeBundle("bundle1", 20, coolSupplies, grade1);
        GradeBundle bundle2 = new GradeBundle("bundle2", 20, coolSupplies, grade2);
        GradeBundle bundle3 = new GradeBundle("bundle3", 20, coolSupplies, grade3);

        //bundle1 = 2 rec pencils + 4 rec erasers
        //bundle2 = 4 mand erasers
        //bundle 3 = empty
        Item pencil = new Item("pencil", 1, coolSupplies);
        BundleItem bundlePencil = new BundleItem(2, rec, coolSupplies, bundle1, pencil);
        Item eraser = new Item("eraser", 2, coolSupplies);
        BundleItem bundleEraser1 = new BundleItem(4, rec, coolSupplies, bundle1, eraser);
        BundleItem bundleEraser2 = new BundleItem(4, mand, coolSupplies, bundle2, eraser);
        //-------------------------------------------------------------------------------------------
**/
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

    //Update TableView
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

    //Remove BundleItem from Table and Bundle in the System
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

                if (table.getItems().size() <= 1) {
                    bundlesController.setBundleDiscount(bundleName, 0); //set discount to 0 if
                }
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
/**
        //--------------------------------TEST---------------------------------
        printBundleDetails();
 **/
    }

//------------------------------TESTING PURPOSES----------------------------------------------------------------
/**    private void printBundleDetails() {
        // Get the list of all bundles from CoolSupplies or your model
        List<GradeBundle> allBundles = coolSupplies.getBundles(); // or however bundles are accessed

        for (GradeBundle bundle : allBundles) {
            System.out.println("Bundle: " + bundle.getName()); // Print the bundle name
            List<BundleItem> bundleItems = bundle.getBundleItems(); // Get items in the bundle

            if (bundleItems.isEmpty()) {
                System.out.println("  No items in this bundle.");
            } else {
                // Print details of each item in the bundle
                for (BundleItem item : bundleItems) {
                    String itemName = item.getItem().getName(); // Get item name
                    int quantity = item.getQuantity(); // Get item quantity
                    String purchaseLevel = item.getLevel().name(); // Get purchase level (Mandatory, Recommended, Optional)

                    // Print out the item details
                    System.out.println("  Item: " + itemName + ", Quantity: " + quantity + ", Purchase Level: " + purchaseLevel);
                }
            }
        }
    }
 **/

// Go to Bundles page when clicking BundlesMenuButton
@FXML
private void goToBundles() throws IOException{
    loadPage("/pages/Bundles.fxml");
}

}
