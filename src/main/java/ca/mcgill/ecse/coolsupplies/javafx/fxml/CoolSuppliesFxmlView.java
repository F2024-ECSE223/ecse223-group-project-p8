package ca.mcgill.ecse.coolsupplies.javafx.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CoolSuppliesFxmlView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the StudentsPage.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/StudentsPage.fxml"));
            Parent root = loader.load();

            // Set the scene and stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Students Page");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
