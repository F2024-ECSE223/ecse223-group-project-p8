package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet2Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOStudent;
import ca.mcgill.ecse.coolsupplies.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.List;

public class ViewAllStudentsController {
    @FXML
    private VBox studentListVBox;

    Stage previousStage;

    static int ID = 1;

    int ID_GAP = 30;
    int NAME_GAP = 100;
    int GRADE_GAP = 120;
    int MODIFY_GAP = 100;
    int DELETE_GAP = 100;

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
        List<TOStudent> students = CoolSuppliesFeatureSet2Controller.getStudents();

        // Clear the VBox to avoid duplication if called multiple times
        studentListVBox.getChildren().clear();

        studentListVBox.getChildren().add(createHeaderRow());
        // Iterate through the orders and add them to the VBox
        for (TOStudent toStudent : students) {
            Student student = Student.getWithName(toStudent.getName());
            HBox studentRow = createOrderRow(student);
            studentListVBox.getChildren().add(studentRow);
        }
    }

    private HBox createOrderRow(Student student) {
        // Create an HBox for each order
        HBox studentRow = new HBox();
        studentRow.setSpacing(10); // Adjust spacing between columns

        // Create labels for each column
        Label id = new Label(String.valueOf(ID));
        id.setPrefWidth(ID_GAP);
        Label nameLabel = new Label(String.valueOf(student.getName()));
        nameLabel.setPrefWidth(NAME_GAP);
        Label gradeLabel = new Label(String.valueOf(student.getGrade().getLevel()));
        gradeLabel.setPrefWidth(GRADE_GAP);
        Button modifyButton = new Button("modify");
        modifyButton.setPrefWidth(MODIFY_GAP);
        Button deleteButton = new Button("delete");
        deleteButton.setPrefWidth(DELETE_GAP);

        ID = ID + 1;
        // Add all labels to the HBox
        studentRow.getChildren().addAll(
                id,
                nameLabel,
                gradeLabel,
                modifyButton,
                deleteButton
        );

        return studentRow;
    }

    private HBox createHeaderRow() {
        // Create an HBox for the header
        HBox headerRow = new HBox();
        headerRow.setSpacing(10); // Adjust spacing between columns
        headerRow.setStyle("-fx-font-weight: bold; -fx-background-color: #f0f0f0;"); // Optional styling

        // Create labels for each column header
        // Create labels for each column
        Label id = new Label("ID");
        id.setPrefWidth(ID_GAP);
        Label nameLabel = new Label("Name");
        nameLabel.setPrefWidth(NAME_GAP);
        Label gradeLabel = new Label("Grade");
        gradeLabel.setPrefWidth(GRADE_GAP);
        Label modifyLabel = new Label("+");
        modifyLabel.setPrefWidth(MODIFY_GAP);
        Label deleteLabel = new Label("+");
        deleteLabel.setPrefWidth(DELETE_GAP);


        // Add all labels to the HBox
        headerRow.getChildren().addAll(
                id,
                nameLabel,
                gradeLabel,
                modifyLabel,
                deleteLabel
        );

        return headerRow;
    }

    private void adjustContentWidth() {
        double totalWidth = ID_GAP+NAME_GAP+GRADE_GAP+MODIFY_GAP+DELETE_GAP; // Sum of all column widths
        studentListVBox.setPrefWidth(totalWidth); // Set the total width of the VBox
    }

    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    @FXML
    private void AddNewStudent(ActionEvent event) throws Exception {
        URL url = new File("src/main/java/ca/mcgill/ecse/coolsupplies/javafx/fxml/pages/AddAndUpdateStudent.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());

        AddAndUpdateStudentController controller = loader.getController();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStage(stage);
        stage.setScene(scene);
    }

    @FXML
    private void goBack(ActionEvent event) {
        if (previousStage != null) {
            previousStage.show(); // Show the previous stage
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close(); // Close the current stage
        }
    }

}
