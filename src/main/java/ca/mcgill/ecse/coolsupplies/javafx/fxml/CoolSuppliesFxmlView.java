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
//            // Load the ParentStudentPage.fxml file
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/ParentStudentPage.fxml"));
//            Parent root = loader.load();
//
//            // Set the scene and stage
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("Parent-Student Page");
//            primaryStage.show();

            // Load the ParentStudentPage.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/StartOrderWindow.fxml"));
            Parent root = loader.load();

            // Set the scene and stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Start Order Window");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
