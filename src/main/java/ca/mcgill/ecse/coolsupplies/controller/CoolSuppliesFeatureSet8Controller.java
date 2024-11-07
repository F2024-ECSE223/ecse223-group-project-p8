package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet8Controller {

    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    // Update order (only purchase level and student)
    public static String updateOrder(String orderNumber, PurchaseLevel purchaseLevel, String studentName) {

        boolean isValid = false;
        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));
        Student student = Student.getWithName(studentName);

        for (PurchaseLevel level : PurchaseLevel.values()) {
            if (level == purchaseLevel) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            return String.format("Purchase level %s does not exist.", purchaseLevel);
        }
        else if (order.hasWithNumber(Integer.parseInt(orderNumber))) {
            return String.format("Order %d does not exist.", orderNumber);
        }
        else if (student == null) {
            return String.format("Student %s does not exist.", student);
        }
        else if (student.getParent() != order.getParent()) {
            return String.format("Student %s is not a child of the parent %s.", student, order.getParent());
        }

        else if (order != null) {
            try {
                order.updateOrderEvent(purchaseLevel,student);
//                OrderPersistence.save();
            }
            catch (RuntimeException e) {
                return e.getMessage();
            }
        }

        return ("The order has successfully been updated.");

    }

    // Add item to order
    public static String addItemToOrder(InventoryItem item, String orderNumber, int newQuantity) {

        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));
        if (order.hasWithNumber(Integer.parseInt(orderNumber))) {
            return String.format("Order %d does not exist.", orderNumber);
        }

        int intOrderNumber = order.getNumber();

        if (item == null) {
            return String.format("Item %s does not exist. ", item);
        }

        else if (itemExists(item.getOrderItem(intOrderNumber),order.getOrderItems())) {
            return String.format("Item %s already exists in the order %d.", item, orderNumber);
        }
        else if (newQuantity < 0) {
            return ("Quantity must be greater than 0.");
        }
        else if (order.getStatusFullName() != "Started") {
            return String.format("Cannot add items from a %s order", order.getStatusFullName());
        }

        else if (order != null) {
            try {
                OrderItem thisItem = coolSupplies.addOrderItem(newQuantity,order,item);
                order.add(thisItem);
//                OrderPersistence.save();
            }
            catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        return ("The item has successfully been added");

    }

    // Update quantity of an existing item of order
    public static String updateQuantity(int newQuantity, OrderItem item) {

        if (item == null) {
            return String.format("Item %s does not exist.", item);
        }

        Order order = (Order) item.getOrder();
        int orderNumber = item.getOrder().getNumber();

        if (order.hasWithNumber(orderNumber)) {
            return String.format("Order %d does not exist ", orderNumber);
        }

        List<OrderItem> oItems = order.getOrderItems();

        if (itemExists(item, oItems)) {
            return String.format("Item %s does not exist in the order %d.", item, orderNumber);
        }
        else if (newQuantity < 0) {
            return ("Quantity must be greater than 0.");
        }
        else if (order.getStatusFullName() != "Started") {
            return String.format("Cannot delete items from a %s order", order.getStatusFullName());
        }
        else if (order != null) {
            try {
                int oItemIndex = order.indexOfOrderItem(item);
                OrderItem thisItem = order.getOrderItem(oItemIndex);
                order.updateQuantityEvent(newQuantity,thisItem);
//                OrderPersistence.save();
            }
            catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        return ("The item's quantity has successfully been updated");
    }

    // Delete item of order
    public static String deleteOrderItem(OrderItem item, String orderNumber) {
        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

        if (order.hasWithNumber(Integer.parseInt(orderNumber))) {
            return String.format("Order %d does not exist ", orderNumber);
        }
        else if (order.getStatusFullName() != "Started") {
            return String.format("Cannot delete items from a %s order", order.getStatusFullName());
        }
        else if (itemExists(item, order.getOrderItems())) {
            return String.format("Item %s does not exist in the order %d.", item, orderNumber);
        }
        else if (item == null) {
            return String.format("Item %s does not exist.", item);
        }
        else if (order != null) {
            try {
                int oItemIndex = order.indexOfOrderItem(item);
                OrderItem thisItem = order.getOrderItem(oItemIndex);
                order.delete(thisItem);
//                OrderPersistence.save();
            }
            catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        return ("The Order item has successfully been deleted.");
    }

    // Private method that checks if an item exists in a given list of OrderItems
    private static boolean itemExists(OrderItem item, List<OrderItem> orderItems) {
        boolean itemExists = false;
        for (OrderItem i : orderItems) {
            if (i == item) {
                itemExists = true;
                break;
            }
        }
        return itemExists;
    }

    /**
     * @author Artimice Mirchi
     * Pays the penalty for the order
     * @param orderNumber the number associated with the order
     * @param penaltyAuthorizationCode The authorization code for the penalty
     * @param authorizationCode The authorization code for the order
     * @return indicates if the penalty was successfully paid
     */
    public static String payPenaltyForOrder(String orderNumber, String penaltyAuthorizationCode, String authorizationCode) {
        if (Order.hasWithNumber(Integer.parseInt(orderNumber))) {
            Order order = Order.getWithNumber(Integer.parseInt(orderNumber));
            if (authorizationCode.length() == 0) {
                return ("Authorization code is invalid");
            }
            if (penaltyAuthorizationCode.length() == 0) {
                return ("Penalty authorization code is invalid");
            }
            if (!order.hasOrderItems()) {
                return ("Order " +orderNumber+ " has no items");
            }

            try {
                order.orderHasBeenPrepared(authorizationCode, penaltyAuthorizationCode);
            }
            catch (RuntimeException e){
                return (e.getMessage());
            }
        }
        else {
            return ("Order " + orderNumber+ " does not exist");
        }
        return ("Done");


    }

    /**
     * @author Artimice Mirchi
     * Pays the penalty for the order
     * @param orderNumber the number associated with the order
     * @param AuthorizationCode The authorization code for the order
     * @return indicates if the order has been successfully paid
     */
    public static String payOrder(String orderNumber, String AuthorizationCode) {
        if (Order.hasWithNumber(Integer.parseInt(orderNumber))) {
            Order order = Order.getWithNumber(Integer.parseInt(orderNumber));
            if (AuthorizationCode.length() == 0) {
                return ("Authorization code is invalid");
            }
            if (!order.hasOrderItems()) {
                return ("Order " +orderNumber+ " has no items");
            }
            String currStatus = order.getStatusFullName();
            try {
                order.orderHasBeenPaid(AuthorizationCode);}

            catch (RuntimeException e){
                return (e.getMessage());
            }
        }
        else {
            return ("Order " +orderNumber+ " does not exist");
        }
        return ("Done");

    }

    // Cancel order
    // TODO: State Machine is not implemented yet
    public static String cancelOrder(String orderNumber) {
        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

        if (order != null) {
            try {
                order.cancel();
//                OrderPersistence.save();
            }
            catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        else{
            return "Order " + orderNumber + " does not exist";
        }
        return "Order deleted successfully";

    }

    // View individual order (including parent, student, status, number, date, level, authorization codes,
    //individual items and items in bundles including their prices and deducted discounts, and total
    //price)
    // TODO: State Machine is not implemented yet
    public static TOOrder viewOrder(String index) {
        Order order = coolSupplies.getOrder(Integer.parseInt(index));

        int orderNumber = order.getNumber();
        Date orderDate = order.getDate();
        BundleItem.PurchaseLevel orderLevel = order.getLevel();
        String authorizationCode = order.getAuthorizationCode();
        String penaltyAuthorizationCode = order.getPenaltyAuthorizationCode();

        Parent parent = order.getParent();
        String parentEmail = parent.getEmail();
        Student student = order.getStudent();
        String studentName = student.getName();

        String status = order.getStatus().toString();
        int totalPrice = calculateTotalPrice(order);

        TOOrder toOrder = new TOOrder(parentEmail, studentName, status, orderNumber, orderDate, orderLevel.toString(), authorizationCode, penaltyAuthorizationCode, totalPrice);

        List<OrderItem> orderItems = order.getOrderItems();
        List<TOOrderItem> toOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            int quantity = orderItem.getQuantity();
            int price = 0;
            String itemName = "";
            String gradeBundleName = "";

            InventoryItem inventoryItem = orderItem.getItem();
            if (inventoryItem instanceof Item) {
                itemName = ((Item) inventoryItem).getName();
                price = ((Item) inventoryItem).getPrice();
            } else if (inventoryItem instanceof GradeBundle) {
                gradeBundleName = ((GradeBundle) inventoryItem).getName();
            }

            int discount = calculateDiscount(orderItem);

            TOOrderItem toOrderItem = new TOOrderItem(quantity, itemName.isEmpty() ? gradeBundleName : itemName, gradeBundleName, price, discount);
            toOrderItems.add(toOrderItem);
        }

        toOrder.setItems(toOrderItems);

        return toOrder;
    }


    private static int calculateTotalPrice(Order order) {
        int totalPrice = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            int price = 0;
            if (orderItem.getItem() instanceof Item) {
                price = ((Item) orderItem.getItem()).getPrice();
            }
            int quantity = orderItem.getQuantity();
            int discount = calculateDiscount(orderItem);
            totalPrice += ((price * quantity) * (100 -discount)) / 100;
        }
        return totalPrice;
    }

    private static int calculateDiscount(OrderItem orderItem) {
        InventoryItem inventoryItem = orderItem.getItem();
        if (inventoryItem instanceof GradeBundle && orderItem.getQuantity() > 1) {
            return ((GradeBundle) inventoryItem).getDiscount();
        }
        return 100;
    }

    // View all orders
    public static List<TOOrder> viewOrders() {
        List<TOOrder> toOrders = new ArrayList<>();

        for (Order order : coolSupplies.getOrders()) {
            int orderNumber = order.getNumber();
            Date orderDate = order.getDate();
            BundleItem.PurchaseLevel orderLevel = order.getLevel();
            String authorizationCode = order.getAuthorizationCode();
            String penaltyAuthorizationCode = order.getPenaltyAuthorizationCode();

            Parent parent = order.getParent();
            String parentEmail = parent.getEmail();
            Student student = order.getStudent();
            String studentName = student.getName();

            String status = order.getStatus().toString();
            int totalPrice = calculateTotalPrice(order);

            TOOrder toOrder = new TOOrder(parentEmail, studentName, status, orderNumber, orderDate, orderLevel.toString(), authorizationCode, penaltyAuthorizationCode, totalPrice);

            List<OrderItem> orderItems = order.getOrderItems();
            List<TOOrderItem> toOrderItems = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                int quantity = orderItem.getQuantity();
                int price = 0;
                String itemName = "";
                String gradeBundleName = "";

                InventoryItem inventoryItem = orderItem.getItem();
                if (inventoryItem instanceof Item) {
                    itemName = ((Item) inventoryItem).getName();
                    price = ((Item) inventoryItem).getPrice();
                } else if (inventoryItem instanceof GradeBundle) {
                    gradeBundleName = ((GradeBundle) inventoryItem).getName();
                }

                int discount = calculateDiscount(orderItem);

                TOOrderItem toOrderItem = new TOOrderItem(quantity, itemName.isEmpty() ? gradeBundleName : itemName, gradeBundleName, price, discount);
                toOrderItems.add(toOrderItem);
            }

            toOrder.setItems(toOrderItems);

            toOrders.add(toOrder);
        }

        return toOrders;
    }


    // Start school year
    public static String startYear(String orderNumebr) {
        Order order = coolSupplies.getOrder(Integer.parseInt(orderNumebr));

        if (order == null){
            return "Order " + orderNumebr + " does not exist";
        }
        try {
            order.startSchoolYear();
//          OrderPersistence.save();
        }
        catch (RuntimeException e) {
            return e.getMessage();
        }

        return "Successfully started school year";
    }

    public static String pickUpOrder(String orderNumber){
        Order order = coolSupplies.getOrder(Integer.parseInt(orderNumber));

        if (order == null) {
            return "Order not found.";
        }

        try {
            String status = order.getStatus().toString();
            if (status.equals("Prepared")) {
                order.setStatus(Order.Status.PickedUp);
                return "Order is picked up.";
            } else {
                return "Order cannot be picked up. It is not prepared.";
            }
            // OrderPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}