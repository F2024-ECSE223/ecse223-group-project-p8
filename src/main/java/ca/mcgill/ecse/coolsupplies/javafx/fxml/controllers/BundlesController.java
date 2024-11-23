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

    @FXML
    void goToAddBundleItemDialog() throws IOException {
        String fxmlPath = "/pages/AddBundleDialog.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        DialogPane addBundleDialogPane = loader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addBundleDialogPane);
        dialog.showAndWait();
        updateTable();

        /**
         //----------------------TEST---------------------------------------------
         printBundleDetails();
         **/
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

/**
 //-------------------------Test------------------------------------
 printBundleDetails();
 **/
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

        /**
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
**/
        updateTable();
    }

    private void updateTable() {
        List<TOGradeBundle> bundles = ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet4Controller.getBundles();

        // Debugging: Print details of each bundle
//        if (bundles != null && !bundles.isEmpty()) {
//            System.out.println("Bundles retrieved:");
//            for (TOGradeBundle bundle : bundles) {
//                System.out.println("Bundle Name: " + bundle.getName());
//                System.out.println("Grade Level: " + bundle.getGradeLevel());
//                System.out.println("Discount: " + bundle.getDiscount());
//            }
//        }

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

    @FXML
    void goToAccount() throws IOException {
        loadPage("/pages/ViewAccountsPage.fxml");
    }

    @FXML
    void goToItems() throws IOException {
        loadPage("/pages/AddItems.fxml");
    }

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

    @FXML
    private void goToNewOrder() throws IOException{
        loadPage("/pages/StartOrderWindow.fxml");
    }

    private void loadPage(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) AddBundleButton.getScene().getWindow();
        stage.setScene(scene);
    }

    public void setBundleDiscount(String bundleName, int newDiscount) {
        CoolSuppliesApplication.getCoolSupplies().getBundles().stream().filter(b -> b.getName().equals(bundleName)).findFirst().ifPresent(bundle -> bundle.setDiscount(newDiscount));
        updateTable();
    }
}
