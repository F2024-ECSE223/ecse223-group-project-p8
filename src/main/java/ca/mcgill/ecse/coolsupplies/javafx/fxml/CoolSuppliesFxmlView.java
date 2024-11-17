package ca.mcgill.ecse.coolsupplies.javafx.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class CoolSuppliesFxmlView extends Application {

    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/java/ca/mcgill/ecse/coolsupplies/javafx/fxml/pages/ViewAllStudents.fxml"));
//            System.out.println(getClass().getResource("/pages/ViewAllStudentsController.fxml"));
            URL url = new File("src/main/java/ca/mcgill/ecse/coolsupplies/javafx/fxml/pages/ViewAllStudents.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            // Set up the scene and stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}