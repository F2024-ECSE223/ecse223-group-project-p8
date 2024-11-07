package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;

import java.util.List;

public class CoolSuppliesFeatureSet8Controller {

    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
    // Update order (only purchase level and student)
    public static String updateOrder(BundleItem.PurchaseLevel purchaseLevel, Student student) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Add item to order
    public static String addItemToOrder(OrderItem item, Order order) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Update quantity of an existing item of order
    public static String updateQuantity(int newQuantity, OrderItem item) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Delete item of order
    public static String deleteOrderItem(OrderItem item) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Pay for order
    // TODO: State Machine is not implemented yet
    public static String payOrder(Order order) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Pay penalty for order
    // TODO: State Machine is not implemented yet
    public static String payPenaltyForOrder(Order order) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Cancel order
    // TODO: State Machine is not implemented yet
    public static String cancelOrder(Order order) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // View individual order (including parent, student, status, number, date, level, authorization codes,
    //individual items and items in bundles including their prices and deducted discounts, and total
    //price)
    // TODO: State Machine is not implemented yet
    public static Order viewOrder() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // View all orders
    public static List<Order> viewOrders() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Start school year
    // ?? return type
    public static String startYear(Order order) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}