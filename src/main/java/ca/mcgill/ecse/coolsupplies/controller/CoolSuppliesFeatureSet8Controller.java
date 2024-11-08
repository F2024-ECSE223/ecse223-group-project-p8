package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet8Controller {

    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    
    /**
     * @author Jyothsna Seema
     *         Updates the order with new quantity and level 
     * @param levelName              The purchase level name 
     * @param orderNumber            The order number of the order to be updated
     * @param studentName            The student name to be updated
     * @return indicates if the order was updated successfully 
     */

    public static String updateOrder(String levelName, int orderNumber, String studentName) {
        Order order = Order.getWithNumber(orderNumber);
        Student student = Student.getWithName(studentName);
        PurchaseLevel purchaseLevel;
        try {
            purchaseLevel = PurchaseLevel.valueOf(levelName);
        } catch (IllegalArgumentException e) {
            return String.format("Purchase level %s does not exist.", levelName);
        }

        if (!Order.hasWithNumber(orderNumber)) {
            return String.format("Order %d does not exist", orderNumber);
        }
        else if (student == null) {
            return String.format("Student %s does not exist.", studentName);
        }
        else if (student.getParent() != order.getParent()) {
            return String.format("Student %s is not a child of the parent %s.", studentName, order.getParent().getEmail());
        }

        else if (!order.getStatusFullName().equals("Started")){
            //Must separate because picked up needs to give an error message with space and lowercase
            if(order.getStatusFullName().equals("PickedUp")){
                return "Cannot update a picked up order";
            }
            else{
                return String.format("Cannot update a %s order",order.getStatusFullName().toLowerCase());
            }
        }

        else {
            try {
                order.updateOrderEvent(purchaseLevel, student);
                // OrderPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        }

        return ("The order has successfully been updated.");

    }

    // Add item to order
    public static String addItemToOrder(String invName, InventoryItem item, String orderNumber, int newQuantity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Update quantity of an existing item of order
    public static String updateQuantity(String itemName, int newQuantity, int orderNumber) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * @author Jyothsna Seema, Zhengxuan Zhao, Snigdha Sen
     *         Deletes the order with new quantity and level 
     * @param itemName              The item name of the item to be delted
     * @param orderNumber           The order number of the order to be updated
     * @return indicates if the order was updated successfully 
     */

    public static String deleteOrderItem(String itemName, String orderNumber) {
        if (!Order.hasWithNumber(Integer.parseInt(orderNumber))) {
            return String.format("Order %s does not exist", orderNumber);
        }

        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));
        if (!InventoryItem.hasWithName(itemName)) {
            return String.format("Item %s does not exist.", itemName);
        }

        List<OrderItem> oItems = order.getOrderItems();
        OrderItem orderItem = null;

        for (OrderItem o : oItems) {
            if (o.getItem().getName().equals(itemName)) {
                orderItem = o;
                break;
            }
        }

        if (orderItem == null) {
            return String.format("Item %s does not exist in the order %s.", itemName, orderNumber);
        }
        else if (order != null) {
            try {
                int oItemIndex = order.indexOfOrderItem(orderItem);
                OrderItem thisItem = order.getOrderItem(oItemIndex);
                order.delete(thisItem);
                // OrderPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        return ("The order item has successfully been deleted.");
    }


    /**
     * @author Artimice Mirchi
     *         Pays the penalty for the order
     * @param orderNumber              the number associated with the order
     * @param penaltyAuthorizationCode The authorization code for the penalty
     * @param authorizationCode        The authorization code for the order
     * @return indicates if the penalty was successfully paid
     */
    public static String payPenaltyForOrder(String orderNumber, String penaltyAuthorizationCode,
            String authorizationCode) {
                throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * @author Artimice Mirchi
     *         Pays the penalty for the order
     * @param orderNumber       the number associated with the order
     * @param AuthorizationCode The authorization code for the order
     * @return indicates if the order has been successfully paid
     */
    public static String payOrder(String orderNumber, String AuthorizationCode) {
        throw new UnsupportedOperationException("Not implemented yet.");

    }

    // Cancel order
    public static String cancelOrder(String orderNumber) {
        throw new UnsupportedOperationException("Not implemented yet.");

    }

    // View individual order (including parent, student, status, number, date,
    // level, authorization codes,
    // individual items and items in bundles including their prices and deducted
    // discounts, and total
    // price)
    // TODO: State Machine is not implemented yet
    public static TOOrder viewOrder(String index) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private static int calculateTotalPrice(Order order) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private static int calculateDiscount(OrderItem orderItem) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // View all orders
    public static List<TOOrder> viewOrders() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Start school year
    public static String startYear(String orderNumber) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}