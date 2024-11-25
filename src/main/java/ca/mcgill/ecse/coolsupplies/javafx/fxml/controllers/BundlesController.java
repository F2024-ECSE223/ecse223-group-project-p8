flyingfish
        flyingfish277
        Invisible

        flyingfish — Yesterday at 3:14 PM
        ohh i see what you mean
        the same thing in other pages worked?
        _snigdha — Yesterday at 3:14 PM
        Yaa TT
        flyingfish — Yesterday at 3:14 PM
        okieee im looking at it
        _snigdha — Yesterday at 3:15 PM
        Also, chatgpt told me to put the little print statements that oh, finish button is not found, if u cluck it
        But if u check ur terminal, when u cluck it, nothing happens right?
        So it's as if it doesn't consider it a button, like it never goes into that whole if-else statement
        flyingfish — Yesterday at 3:16 PM
        yeahyeah
        do u have this dialog page copied from someone else's?
        _snigdha — Yesterday at 3:17 PM
        From mines actually, another page
        If u look at bundleitems page
        flyingfish — Yesterday at 3:17 PM
        cuz from in scene builder i see that there is no bottoms
        for finish and cancel
        _snigdha — Yesterday at 3:18 PM
        Wait what seriously??
        That's weirddd, but it shows it in the view??
        Why would it not be un the scene builder??
        flyingfish — Yesterday at 3:18 PM
        /
        _snigdha — Yesterday at 3:18 PM
        It's on the scene builder on my end 100%
        OHHH
        No no, Mary that's normal
        I think it's in properties or smtg? If u scroll in one of the sections on the right, ull see CANCEL, FINISH
        It's default buttons pre implemented by JavaFX for dialog panes
        I think there's another page called update bundle item dialog?
        Do u see that? That's the one I wrote too
        It goes from the bundleitems page to that, and ya, ull see it follows rhe same thing :/
        flyingfish — Yesterday at 3:21 PM
        okay i see
        oh and btw the back button on EditOrderItemsPage doesnt work
        _snigdha — Yesterday at 3:22 PM
        Hein really?? It worked on mines.. wdym it doesnt work, it lesds to the wrong page? TT
        flyingfish — Yesterday at 3:22 PM
        like nothing happened
        _snigdha — Yesterday at 3:23 PM
        Hmm ok, either u don't have artimices all items page?
        On the pages folder or its written differently
        flyingfish — Yesterday at 3:23 PM
        ah u have the wrong name
        i think artimice changes the name of the file (ig
        she told us during yesterday's meeting
        _snigdha — Yesterday at 3:24 PM
        Ohhh mb, can u change it then?
        flyingfish — Yesterday at 3:24 PM
        i could but im not pushing the file to github tho
        _snigdha — Yesterday at 3:24 PM
        Ohhh, it's ok, u can just send it here
        So I can copy paste
        flyingfish — Yesterday at 3:24 PM
        ItemsShop.fxml
        i thinkits this one
        _snigdha — Yesterday at 3:25 PM
        Ah ok, thaya fine too, I'll change it rn!
        Thx!
        flyingfish — Yesterday at 3:40 PM
        im not sure what the issue is TT
        did you also adk jyothsna?
        _snigdha — Yesterday at 3:42 PM
        Nooo, I'll ask her then
        It's ok, worse comes to worse, I'll add regular buttons... it will just look different 🫠
        flyingfish — Yesterday at 4:27 PM
        okkk yeah TT
        _snigdha — Today at 2:05 AM
        I decided I'd just do it in one go in the page, instead of the extra dialog..
        Image
        flyingfish — Today at 7:53 AM
        Omg 2am??😭😭😭😭
        The hard work😭😭
        _snigdha — Today at 10:35 AM
        Loll also, we're walking to trottier
        flyingfish — Today at 10:37 AM
        there's errors in your controller TT
        _snigdha — Today at 10:55 AM
        #cc0000
        flyingfish — Today at 11:43 AM
        bundle items page to bundle's page
        add item page, you have to tab to enter second field
        add item page, click add doesn't go back
        step 9, missing error message
        bundles page, manage bundle, (grade doesn't exist message) error was not printing
        _snigdha — Today at 12:35 PM
        This is the updated Bundles Controller! Could you please update it?

        Thank you,
        Jyothsna 💗
        package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
Expand
        BundlesController.java
        9 KB
        ﻿
        _snigdha
        _snigdha.

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
BundlesController.java
9 KB