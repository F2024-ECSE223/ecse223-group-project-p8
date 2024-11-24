package ca.mcgill.ecse.coolsupplies.javafx.fxml;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.List;

public class CoolSuppliesFxmlView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Date date = new Date(2023, 4, 20);
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

        // uncomment these two lines when you first run the program, comment afterwards
        //Parent p1= new ca.mcgill.ecse.coolsupplies.model.Parent("1","","",1,coolSupplies);
        //Student s1 = new Student("2",coolSupplies,new Grade("9",coolSupplies));

//        List<Student> s = coolSupplies.getStudents();
//        List<ca.mcgill.ecse.coolsupplies.model.Parent> p = coolSupplies.getParents();
//        Order order1 = new Order(111, date, BundleItem.PurchaseLevel.Mandatory,p.get(0),s.get(0),coolSupplies);
//        Order order2 = new Order(112, date, BundleItem.PurchaseLevel.Mandatory,p.get(0),s.get(0),coolSupplies);
//        Order order3 = new Order(113, date, BundleItem.PurchaseLevel.Mandatory,p.get(0),s.get(0),coolSupplies);
//        Order order4 = new Order(114, date, BundleItem.PurchaseLevel.Mandatory,p.get(0),s.get(0),coolSupplies);
//        coolSupplies.addOrder(order1);
//        coolSupplies.addOrder(order2);
//        coolSupplies.addOrder(order3);
//        coolSupplies.addOrder(order4);
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ViewAllOrders.fxml"));
            Parent root = loader.load();

            // Set up the scene and stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}