package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.model.*;

import java.util.List;

public class CoolSuppliesFeatureSet8Controller {

    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    // Update order (only purchase level and student)
    public static String updateOrder(String orderNumber, PurchaseLevel purchaseLevel, String studentName) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Add item to order
    public static String addItemToOrder(InventoryItem item, String orderNumber, int newQuantity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Update quantity of an existing item of order
    public static String updateQuantity(int newQuantity, OrderItem item) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Delete item of order
    public static String deleteOrderItem(OrderItem item, String orderNumber) {
        throw new UnsupportedOperationException("Not implemented yet.");
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
     * @return indicates if the order was successfully paid
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
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // View individual order (including parent, student, status, number, date, level, authorization codes,
    //individual items and items in bundles including their prices and deducted discounts, and total
    //price)
    // TODO: State Machine is not implemented yet
    public static TOOrder viewOrder(String index) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // View all orders
    public static List<TOOrder> viewOrders() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Start school year
    public static String startYear(String orderNumebr) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static String pickUpOrder(String orderNumber){
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}