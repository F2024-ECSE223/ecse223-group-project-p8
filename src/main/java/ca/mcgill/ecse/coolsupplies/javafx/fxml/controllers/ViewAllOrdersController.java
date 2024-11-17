package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet8Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOOrder;
import ca.mcgill.ecse.coolsupplies.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.util.List;

public class ViewAllOrdersController {
    @FXML
    private VBox orderListVBox;

    static int ID = 1;

    int ID_GAP = 30;
    int NUMBER_GAP = 100;
    int DATE_GAP = 120;
    int LEVEL_GAP = 100;
    int PARENT_GAP = 100;
    int STUDENT_GAP = 100;
    int STATUS_GAP = 80;
    int AUTHCODE_GAP = 150;
    int PENCODE_GAP = 150;

    public void initialize() {
        Date date = new Date(2023, 4, 20);
        CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
        Parent p1= new Parent("","","",1,coolSupplies);
        Student s1 = new Student("",coolSupplies,new Grade("1",coolSupplies));
        Order order1 = new Order(111, date, BundleItem.PurchaseLevel.Mandatory,p1,s1,coolSupplies);
        Order order2 = new Order(112, date, BundleItem.PurchaseLevel.Mandatory,p1,s1,coolSupplies);
        Order order3 = new Order(113, date, BundleItem.PurchaseLevel.Mandatory,p1,s1,coolSupplies);
        Order order4 = new Order(114, date, BundleItem.PurchaseLevel.Mandatory,p1,s1,coolSupplies);
        coolSupplies.addOrder(order1);
        coolSupplies.addOrder(order2);
        coolSupplies.addOrder(order3);
        coolSupplies.addOrder(order4);

//        List<TOOrder> toOrders = CoolSuppliesFeatureSet8Controller.viewOrders();
//        for(TOOrder order : toOrders){
//            System.out.println(order.getNumber());
//        }
        populateOrders();

    }

    public void populateOrders() {
        adjustContentWidth();
        // Fetch the list of orders from the OrderController
        List<TOOrder> orders = CoolSuppliesFeatureSet8Controller.viewOrders();

        // Clear the VBox to avoid duplication if called multiple times
        orderListVBox.getChildren().clear();

        orderListVBox.getChildren().add(createHeaderRow());
        // Iterate through the orders and add them to the VBox
        for (TOOrder toOrder : orders) {
            Order order = Order.getWithNumber(toOrder.getNumber());
            HBox orderRow = createOrderRow(order);
            orderListVBox.getChildren().add(orderRow);
        }
    }

    private HBox createOrderRow(Order order) {
        // Create an HBox for each order
        HBox orderRow = new HBox();
        orderRow.setSpacing(10); // Adjust spacing between columns

        // Create labels for each column
        Label id = new Label(String.valueOf(ID));
        id.setPrefWidth(ID_GAP);
        Label numberLabel = new Label(String.valueOf(order.getNumber()));
        numberLabel.setPrefWidth(NUMBER_GAP);
        Label dateLabel = new Label(order.getDate().toString());
        dateLabel.setPrefWidth(DATE_GAP);
        Label levelLabel = new Label(order.getLevel().toString());
        levelLabel.setPrefWidth(LEVEL_GAP);
        Label parentEmailLabel = new Label(order.getParent().getEmail());
        parentEmailLabel.setPrefWidth(PARENT_GAP);
        Label studentNameLabel = new Label(order.getStudent().getName());
        studentNameLabel.setPrefWidth(STUDENT_GAP);
        Label statusLabel = new Label(order.getStatus().toString());
        statusLabel.setPrefWidth(STATUS_GAP);
        Label authCodeLabel = new Label(order.getAuthorizationCode());
        authCodeLabel.setPrefWidth(AUTHCODE_GAP);
        Label penaltyAuthCodeLabel = new Label(order.getPenaltyAuthorizationCode());
        penaltyAuthCodeLabel.setPrefWidth(PENCODE_GAP);

        ID = ID + 1;

        // Add all labels to the HBox
        orderRow.getChildren().addAll(
                id,
                numberLabel,
                dateLabel,
                levelLabel,
                parentEmailLabel,
                studentNameLabel,
                statusLabel,
                authCodeLabel,
                penaltyAuthCodeLabel
        );

        return orderRow;
    }

    private HBox createHeaderRow() {
        // Create an HBox for the header
        HBox headerRow = new HBox();
        headerRow.setSpacing(10); // Adjust spacing between columns
        headerRow.setStyle("-fx-font-weight: bold; -fx-background-color: #f0f0f0;"); // Optional styling

        // Create labels for each column header
        Label id = new Label("ID");
        id.setPrefWidth(ID_GAP);
        Label numberLabel = new Label("Number");
        numberLabel.setPrefWidth(NUMBER_GAP);
        Label dateLabel = new Label("Date");
        dateLabel.setPrefWidth(DATE_GAP);
        Label levelLabel = new Label("Level");
        levelLabel.setPrefWidth(LEVEL_GAP);
        Label parentEmailLabel = new Label("Parent Email");
        parentEmailLabel.setPrefWidth(PARENT_GAP);
        Label studentNameLabel = new Label("Student Email");
        studentNameLabel.setPrefWidth(STUDENT_GAP);
        Label statusLabel = new Label("Status");
        statusLabel.setPrefWidth(STATUS_GAP);
        Label authCodeLabel = new Label("Authorization Code");
        authCodeLabel.setPrefWidth(AUTHCODE_GAP);
        Label penaltyAuthCodeLabel = new Label("Penalty Authorization Code");
        penaltyAuthCodeLabel.setPrefWidth(PENCODE_GAP);


        // Add all labels to the HBox
        headerRow.getChildren().addAll(
                id,
                numberLabel,
                dateLabel,
                levelLabel,
                parentEmailLabel,
                studentNameLabel,
                statusLabel,
                authCodeLabel,
                penaltyAuthCodeLabel
        );

        return headerRow;
    }

    private void adjustContentWidth() {
        double totalWidth = ID_GAP+NUMBER_GAP+DATE_GAP+LEVEL_GAP+PARENT_GAP+STATUS_GAP+STATUS_GAP+AUTHCODE_GAP+PENCODE_GAP; // Sum of all column widths
        orderListVBox.setPrefWidth(totalWidth); // Set the total width of the VBox
    }

}
