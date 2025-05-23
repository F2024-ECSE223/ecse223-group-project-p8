package ca.mcgill.ecse.coolsupplies.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertNull;


import java.util.*;

import java.sql.Date;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.controller.*;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.InventoryItem;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.Student;
import ca.mcgill.ecse.coolsupplies.model.User;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.OrderItem;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderStepDefinitions {

    private CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    private List<OrderItem> orderItems;
    List<Order> resultOrders;
    String error;
    TOOrder actualOrder;
    List<TOOrder> actualOrderList = new ArrayList<>();

    /**
     * @author Shengyi Zhong
     */
    @Given("the following parent entities exist in the system")
    public void the_following_parent_entities_exist_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> parentMap = dataTable.asMaps();
        for (Map<String, String> parent : parentMap) {
            new Parent(parent.get("email"), parent.get("password"), parent.get("name"),
                    Integer.parseInt(parent.get("phoneNumber")), coolSupplies);
        }
    }

    /**
     * @author Shengyi Zhong
     */
    @Given("the following grade entities exist in the system")
    public void the_following_grade_entities_exist_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> gradeEntities = dataTable.asMaps();
        for (Map<String, String> grade : gradeEntities) {
            new Grade(grade.get("level"), coolSupplies);
        }
    }

    /**
     * @author Zhengxuan Zhao
     */
    @Given("the following student entities exist in the system")
    public void the_following_student_entities_exist_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (var row : rows) {
            String studentName = row.get("name");
            String studentGradeLevel = row.get("gradeLevel");
            Grade grade = Grade.getWithLevel(studentGradeLevel);
            new Student(studentName, coolSupplies, grade);
        }
    }

    /**
     * @author Zhengxuan Zhao
     */
    @Given("the following student entities exist for a parent in the system")
    public void the_following_student_entities_exist_for_a_parent_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();

        for (var row : rows) {
            String name = row.get("name");
            String parentEmail = row.get("parentEmail");

            Student student = Student.getWithName(name);
            Parent parent = (Parent) User.getWithEmail(parentEmail);

            // Return an error message if the student does not exist
            if (student == null) {
                error =  "The student does not exist.";
            }

            // Return an error message if the parent does not exist
            if (parent == null) {
                error =  "The parent does not exist.";
            }

            // Successfully add a student to a parent in the system
            boolean isAdded = parent.addStudent(student);
            if (isAdded) {
                try {
                    CoolSuppliesPersistence.save();
                } catch (RuntimeException e) {
                    error = e.getMessage();
                }
                error = "Student " + student + " added to parent " + parentEmail;
            } else {
                error = "Student " + student + " is already associated with parent " + parentEmail;
            }
        }
    }

    /**
     * @author Snigdha Sen
     */
    @Given("the following item entities exist in the system")
    public void the_following_item_entities_exist_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();

        for (var row : rows) {
            String name = row.get("name");
            int price = Integer.parseInt(row.get("price"));
            new Item(name, price, coolSupplies);
        }
    }

    /**
     * @author Snigdha Sen
     */
    @Given("the following grade bundle entities exist in the system")
    public void the_following_grade_bundle_entities_exist_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            String name = row.get("name");
            int discount = Integer.parseInt(row.get("discount"));
            String gradeLevelStr = row.get("gradeLevel");

            Grade gradeLevel = Grade.getWithLevel(gradeLevelStr);
            if (gradeLevel == null) {
                gradeLevel = new Grade(gradeLevelStr, coolSupplies);
            }

            new GradeBundle(name, discount, coolSupplies, gradeLevel);
        }
    }

    /**
     * @author Jiatian Liu
     */
    @Given("the following bundle item entities exist in the system")
    public void the_following_bundle_item_entities_exist_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int quantity = Integer.parseInt(row.get("quantity"));
            PurchaseLevel level = PurchaseLevel.valueOf(row.get("level"));
            GradeBundle gradeBundle = (GradeBundle) InventoryItem.getWithName(row.get("gradeBundleName"));
            Item item = (Item) InventoryItem.getWithName(row.get("itemName"));
            coolSupplies.addBundleItem(quantity, level, gradeBundle, item);
        }
    }

    /**
     * @author Jiatian Liu
     */
    @Given("the following order entities exist in the system")
    public void the_following_order_entities_exist_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int number = Integer.parseInt(row.get("number"));
            Date date = Date.valueOf(row.get("date"));
            PurchaseLevel level = PurchaseLevel.valueOf(row.get("level"));

            Parent parent = (Parent) User.getWithEmail(row.get("parentEmail"));
            Student student = Student.getWithName(row.get("studentName"));

            Order order = new Order(number, date, level, parent, student, coolSupplies);

            String authorizationCode = row.get("authorizationCode");
            if (authorizationCode == null || authorizationCode.isEmpty()) {
                authorizationCode = null;
            }
            order.setAuthorizationCode(authorizationCode);

            String status = row.get("status");
            if (status != null && !status.isEmpty()) {
                order.setStatus(Order.Status.valueOf(status));
            }

            String penaltyAuthorizationCode = row.get("penaltyAuthorizationCode");
            if (penaltyAuthorizationCode == null || penaltyAuthorizationCode.isEmpty()) {
                penaltyAuthorizationCode = null;
            }
            order.setPenaltyAuthorizationCode(penaltyAuthorizationCode);

            coolSupplies.addOrder(order);
        }
    }

    /**
     * @author Jiatian Liu
     */
    @Given("the following order item entities exist in the system")
    public void the_following_order_item_entities_exist_in_the_system(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int quantity = Integer.parseInt(row.get("quantity"));
            Order order = Order.getWithNumber(Integer.parseInt(row.get("orderNumber")));
            InventoryItem inventoryItem = InventoryItem.getWithName(row.get("itemName"));
            coolSupplies.addOrderItem(quantity, order, inventoryItem);
        }
    }

    /**
     * @author Shengyi Zhong
     */
    @Given("the order {string} is marked as {string}")
    public void the_order_is_marked_as(String string, String string2) {
        Order order = Order.getWithNumber(Integer.parseInt(string));
        Order.Status aStatus = Order.Status.valueOf(string2);
        order.setStatus(aStatus);
    }

    /**
     * @author Shengyi Zhong
     */
    @When("the parent attempts to update an order with number {string} to purchase level {string} and student with name {string}")
    public void the_parent_attempts_to_update_an_order_with_number_to_purchase_level_and_student_with_name(
            String string, String string2, String string3) {
        int orderNumber = Integer.parseInt(string);
        error = CoolSuppliesFeatureSet8Controller.updateOrder(string2, orderNumber, string3);
    }

    /**
     * @author Snigdha Sen
     */
    @When("the parent attempts to add an item {string} with quantity {string} to the order {string}")
    public void the_parent_attempts_to_add_an_item_with_quantity_to_the_order(String string,
                                                                              String string2, String string3) {

        int qty = Integer.parseInt(string2);
        error = CoolSuppliesFeatureSet8Controller.addItemToOrder(string, string3, qty);
    }
    /**
     * @author Zhengxuan Zhao
     */
    @When("the parent attempts to update an item {string} with quantity {string} in the order {string}")
    public void the_parent_attempts_to_update_an_item_with_quantity_in_the_order(String string,
                                                                                 String string2, String string3) {
        String itemName = string;
        int newQuantity = Integer.parseInt(string2);
        int orderNumber = Integer.parseInt(string3);
        error = CoolSuppliesFeatureSet8Controller.updateQuantity(itemName, newQuantity, orderNumber);
    }

    /**
     * @author Zhengxuan Zhao
     */
    @When("the parent attempts to delete an item {string} from the order {string}")
    public void the_parent_attempts_to_delete_an_item_from_the_order( String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        error = CoolSuppliesFeatureSet8Controller.deleteOrderItem(string, string2);
    }


    /**
     * @author Artimice Mirchi, Jyothsna Seema, Mary Li
     */
    @When("the parent attempts to get from the system the order with number {string}")
    public void the_parent_attempts_to_get_from_the_system_the_order_with_number(String string) {
        // Write code here that turns the phrase above into concrete actions
        actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
    }


    /**
     * @author Shengyi Zhong
     */
    @When("the parent attempts to cancel the order {string}")
    public void the_parent_attempts_to_cancel_the_order(String string) {
        // Write code here that turns the phrase above into concrete actions
        error = CoolSuppliesFeatureSet8Controller.cancelOrder(string);
    }

    /**
     * @author Zhengxuan Zhao
     */
    @When("the parent attempts to pay for the order {string} with authorization code {string}")
    public void the_parent_attempts_to_pay_for_the_order_with_authorization_code(String string,
                                                                                 String string2) {
        error = CoolSuppliesFeatureSet8Controller.payOrder(string, string2);
    }

    /**
     * @author Snigdha Sen
     */
    @When("the admin attempts to start a school year for the order {string}")
    public void the_admin_attempts_to_start_a_school_year_for_the_order(String string) {
        // Write code here that turns the phrase above into concrete actions
        error = CoolSuppliesFeatureSet8Controller.startYear(string);
    }

    /**
     * @author Jiatian Liu
     */
    @When("the parent attempts to pay penalty for the order {string} with penalty authorization code {string} and authorization code {string}")
    public void the_parent_attempts_to_pay_penalty_for_the_order_with_penalty_authorization_code_and_authorization_code(
            String string, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        error = CoolSuppliesFeatureSet8Controller.payPenaltyForOrder(string, string2, string3);
    }

    /**
     * @author Jiatian Liu
     */
    @When("the student attempts to pickup the order {string}")
    public void the_student_attempts_to_pickup_the_order(String string) {
        // Write code here that turns the phrase above into concrete actions
        error = CoolSuppliesFeatureSet8Controller.pickUpOrder(string);
    }

    /**
     * @author Artimice Mirchi, Jyothsna Seema, Mary Li
     */
    @When("the school admin attempts to get from the system all orders")
    public void the_school_admin_attempts_to_get_from_the_system_all_orders() {
        // Write code here that turns the phrase above into concrete actions
        resultOrders = coolSupplies.getOrders();
    }


    /**
     * @author Jiatian Liu
     */
    @Then("the order {string} shall contain penalty authorization code {string}")
    public void the_order_shall_contain_penalty_authorization_code(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());
        assertEquals(string2, order.getPenaltyAuthorizationCode());
    }

    /**
     * @author Jiatian Liu
     */
    @Then("the order {string} shall not contain penalty authorization code {string}")
    public void the_order_shall_not_contain_penalty_authorization_code(String string,
                                                                       String string2) {
        // Write code here that turns the phrase above into concrete actions
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());
        assertNotEquals(string2, order.getPenaltyAuthorizationCode());
    }

    /**
     * @author Jiatian Liu
     */
    @Then("the order {string} shall not contain authorization code {string}")
    public void the_order_shall_not_contain_authorization_code(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());
        assertNotEquals(string2, order.getAuthorizationCode());
    }

    /**
     * @author Shengyi Zhong
     */
    @Then("the order {string} shall not exist in the system")
    public void the_order_shall_not_exist_in_the_system(String string) {
        // Write code here that turns the phrase above into concrete actions
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        assertNull(actualOrder);
    }

    /**
     * @author Jiatian Liu
     */
    @Then("the order {string} shall contain authorization code {string}")
    public void the_order_shall_contain_authorization_code(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());
        assertEquals(string2, order.getAuthorizationCode());
    }

    /**
     * @author Zhengxuan Zhao
     */
    @Then("the order {string} shall contain {string} item")
    public void the_order_shall_contain_item(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());

        List<OrderItem> orderItems = order.getOrderItems();
        assertEquals(string2, String.valueOf(orderItems.size()));
    }

    /**
     * @author Zhengxuan Zhao
     */
    @Then("the order {string} shall not contain {string}")
    public void the_order_shall_not_contain(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());
        List<OrderItem> orderItems = order.getOrderItems();
        boolean matchFound = false;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getItem().getName().equals(string2)) {
                matchFound = true;
                break;
            }
        }
        assertFalse(matchFound);
    }

    /**
     * @author Snigdha Sen
     */
    @Then("the number of order items in the system shall be {string}")
    public void the_number_of_order_items_in_the_system_shall_be(String string) {
        int actualNum = coolSupplies.numberOfOrderItems();
        int expectedNum = Integer.parseInt(string);
        assertEquals(expectedNum, actualNum);
    }

    /**
     * @author Snigdha Sen
     */
    @Then("the order {string} shall contain {string} items")
    public void the_order_shall_contain_items(String string, String string2) {
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());
        int actualNum = order.numberOfOrderItems();
        int expectedNum = Integer.parseInt(string2);
        assertEquals(expectedNum, actualNum);
    }

    /**
     * @author Snigdha Sen
     */
    @Then("the order {string} shall not contain {string} with quantity {string}")
    public void the_order_shall_not_contain_with_quantity(String string, String string2,
                                                          String string3) {
        boolean contains = false;
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());
        List<OrderItem> orderItemsList = order.getOrderItems();

        for (OrderItem orderItem : orderItemsList) {
            if (orderItem.getItem().getName().equals(string2)) {
                int actualQty = orderItem.getQuantity();
                int notExpectedQty = Integer.parseInt(string3);

                if (actualQty == notExpectedQty) {
                    contains = true;
                }
            }
        }

        assertEquals(false, contains);
    }

    /**
     * @author Snigdha Sen
     */
    @Then("the order {string} shall contain {string} with quantity {string}")
    public void the_order_shall_contain_with_quantity(String string, String string2, String string3) {
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());
        List<OrderItem> orderItemsList = order.getOrderItems();

        for (OrderItem orderItem : orderItemsList) {
            if (orderItem.getItem().getName().equals(string2)) {
                int actualQty = orderItem.getQuantity();
                int expectedQty = Integer.parseInt(string3);
                assertEquals(expectedQty, actualQty);
            }
        }
    }

    /**
     * @author Shengyi Zhong
     */
    @Then("the order {string} shall be marked as {string}")
    public void the_order_shall_be_marked_as(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        // Map<Order, OrderState> orderStates =
        // CoolSuppliesFeatureSet8Controller.getOrderStates();
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());

        String status = order.getStatus().toString();
        assertEquals(string2, status);
    }

    /**
     * @author Shengyi Zhong
     */
    @Then("the number of orders in the system shall be {string}")
    public void the_number_of_orders_in_the_system_shall_be(String string) {
        // Write code here that turns the phrase above into concrete actions
        List<Order> orders = coolSupplies.getOrders();
        assertEquals(string, String.valueOf(orders.size()));
    }

    /**
     * @author Shengyi Zhong
     */
    @Then("the order {string} shall contain level {string} and student {string}")
    public void the_order_shall_contain_level_and_student(String string, String string2,
                                                          String string3) {
        // Write code here that turns the phrase above into concrete actions
        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);
        Order order = Order.getWithNumber(actualOrder.getNumber());

        PurchaseLevel level = PurchaseLevel.valueOf(string2);
        Student student = Student.getWithName(string3);

        assertEquals(level, order.getLevel());
        assertEquals(student, order.getStudent());
    }

    /**
     * @author Jiatian Liu
     */
    @Then("the error {string} shall be raised")
    public void the_error_shall_be_raised(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, error);
    }

    /**
     * @author Zhengxuan Zhao
     */
    @Then("the following order entities shall be presented")
    public void the_following_order_entities_shall_be_presented(
            io.cucumber.datatable.DataTable expectedOrderDataTable) {
        List<Map<String, String>> expectedOrderList = expectedOrderDataTable.asMaps();
        actualOrderList = CoolSuppliesFeatureSet8Controller.viewOrders();

        for (Map<String, String> expectedOrder : expectedOrderList) {
            boolean matchFound = false;
            for (TOOrder actualOrder : actualOrderList) {
                if (expectedOrder.get("parentEmail").equals(actualOrder.getParentEmail()) &&
                        expectedOrder.get("studentName").equals(actualOrder.getStudentName()) &&
                        expectedOrder.get("status").equals(actualOrder.getStatus()) &&
                        (Integer.parseInt(expectedOrder.get("number")) == actualOrder.getNumber()) &&
                        (expectedOrder.get("date").equals(actualOrder.getDate().toString())) &&
                        expectedOrder.get("level").equals(actualOrder.getLevel()) &&
                        Objects.equals(expectedOrder.get("authorizationCode"), actualOrder.getAuthorizationCode()) &&
                        Objects.equals(expectedOrder.get("penaltyAuthorizationCode"), (actualOrder.getPenaltyAuthorizationCode())) &&
                        (Double.parseDouble(expectedOrder.get("totalPrice")) == actualOrder.getTotalPrice())) {
                    matchFound = true;
                    break;
                }
            }

            assertTrue(matchFound, "Expected Order not found: " + expectedOrder);
        }
    }

    /**
     * @author Zhengxuan Zhao
     */
    @Then("the following order items shall be presented for the order with number {string}")
    public void the_following_order_items_shall_be_presented_for_the_order_with_number(
            String string, io.cucumber.datatable.DataTable expectedOrderDataTable) {
        List<Map<String, String>> expectedItemList = expectedOrderDataTable.asMaps();

        TOOrder actualOrder = CoolSuppliesFeatureSet8Controller.viewOrder(string);

        // Assert that the size of the expected list matches the actual list
        assertEquals(expectedItemList.size(), actualOrder.getItems().size());

        for (Map<String, String> expectedItem : expectedItemList) {
            boolean matchFound = false;
            for (TOOrderItem actualItem : actualOrder.getItems()) {

                if (Integer.parseInt(expectedItem.get("quantity")) == actualItem.getQuantity() &&
                        Objects.equals(expectedItem.get("itemName"), actualItem.getItemName()) &&
                        Objects.equals(expectedItem.get("gradeBundleName"), actualItem.getGradeBundleName()) &&
                        Integer.parseInt(expectedItem.get("price")) == actualItem.getPrice() &&
                        Objects.equals(expectedItem.get("discount"), actualItem.getDiscount())) {
                    matchFound = true;
                    break;
                }
            }
            assertTrue(matchFound, "Expected Item not found: " + expectedItem);
        }
    }


    /**
     * @author Zhengxuan Zhao
     */
    @Then("no order entities shall be presented")
    public void no_order_entities_shall_be_presented() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(actualOrderList.isEmpty(), "Expected no orders, but found some.");
    }
}
