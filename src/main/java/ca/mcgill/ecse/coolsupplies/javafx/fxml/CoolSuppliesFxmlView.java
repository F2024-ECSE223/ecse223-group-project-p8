package ca.mcgill.ecse.coolsupplies.javafx.fxml;

import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;
import ca.mcgill.ecse.coolsupplies.model.Student;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
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

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

//        Parent p1= new Parent("jane.doe@gmail.com","123","Jane",0123456,coolSupplies);
//        Parent p2= new Parent("john.doe@gmail.com","456","John",5140098,coolSupplies);
//        Parent p3= new Parent("txt@moa.ca","789","TXT",4389972,coolSupplies);
//        Parent p4= new Parent("yeonjun2@gmail.com","abc","Yeonjun",8658462,coolSupplies);
//        SchoolAdmin a = new SchoolAdmin("admin@cool.ca", "advdg", coolSupplies);
//
//        coolSupplies.addParent(p1);
//        coolSupplies.addParent(p2);
//        coolSupplies.addParent(p3);
//        coolSupplies.addParent(p4);
//        coolSupplies.setAdmin(a);

        try {
            System.out.println(getClass().getResource(""));
            File fxmlFile = new File("src/main/resources/pages/ViewOrderWindow.fxml");
            var root = (Pane) FXMLLoader.load(fxmlFile.toURI().toURL());
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

    public void registerRefreshEvent(Node node) {
        refreshableNodes.add(node);
    }

    public void registerRefreshEvent(Node... nodes) {
        for (var node: nodes) {
            refreshableNodes.add(node);
        }
    }

    public void removeRefreshableNode(Node node) {
        refreshableNodes.remove(node);
    }

    public void refresh() {
        for (Node node : refreshableNodes) {
            node.fireEvent(new Event(REFRESH_EVENT));
        }
    }

    public static CoolSuppliesFxmlView getInstance() {
        return instance;
    }

}