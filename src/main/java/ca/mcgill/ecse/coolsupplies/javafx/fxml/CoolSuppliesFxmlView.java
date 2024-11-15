package ca.mcgill.ecse.coolsupplies.javafx.fxml;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

public class CoolSuppliesFxmlView extends Application {

    public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
    private static CoolSuppliesFxmlView instance;
    private List<Node> refreshableNodes = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;

        try {
            var root = (Pane) FXMLLoader.load(getClass().getResource("pages/RegisterParentPage.fxml"));
            root.setStyle(CoolSuppliesApplication.DARK_MODE ? "-fx-base: rgba(20, 20, 20, 255);" : "");
            var scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            primaryStage.setTitle("CoolSupplies");
            primaryStage.show();
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        for (Node node : refreshableNodes) {
            node.fireEvent(new Event(REFRESH_EVENT));
        }
    }

}
