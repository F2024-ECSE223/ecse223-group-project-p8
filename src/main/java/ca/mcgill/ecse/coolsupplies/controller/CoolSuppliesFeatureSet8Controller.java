package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.model.Order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoolSuppliesFeatureSet8Controller {

    private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    // Update order (only purchase level and student)
    public static String updateOrder(String levelName, int orderNumber, String studentName) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Add item to order
    public static String addItemToOrder(InventoryItem item, String orderNumber, int newQuantity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }


    // Update quantity of an existing item of order
    public static String updateQuantity(String itemName, int newQuantity, int orderNumber) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Delete item of order
    public static String deleteOrderItem(String itemName, String orderNumber) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Pay for order
    // TODO: State Machine is not implemented yet
    public static String payOrder(String orderNumber, String AuthorizationCode) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Pay penalty for order
    // TODO: State Machine is not implemented yet
    public static String payPenaltyForOrder(String orderNumber, String authorizationCode, String penaltyAuthorizationCode) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Cancel order
    // TODO: State Machine is not implemented yet
    public static String cancelOrder(String orderNumber) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private static boolean isLevelEligibleForOrder(BundleItem.PurchaseLevel orderLevel, BundleItem.PurchaseLevel itemLevel) {
        switch (orderLevel) {
            case Mandatory:
                return itemLevel == BundleItem.PurchaseLevel.Mandatory;
            case Recommended:
                return itemLevel == BundleItem.PurchaseLevel.Mandatory || itemLevel == BundleItem.PurchaseLevel.Recommended;
            case Optional:
                return true;
            default:
                return false;
        }
    }

    public static TOOrder viewOrder(String index) {
        if (Integer.parseInt(index) > coolSupplies.getOrders().size()) {
            return null;
        }
        Order order = coolSupplies.getOrder(Integer.parseInt(index) - 1);

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
        double totalPrice = 0;

        List<OrderItem> orderItems = order.getOrderItems();
        List<TOOrderItem> toOrderItems = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            InventoryItem inventoryItem = orderItem.getItem();
            int orderItemQuantity = orderItem.getQuantity();
            int unitPrice = 0;
            String itemName = "";
            String gradeBundleName = "";
            List<TOOrderItem> bundleOrderItems = new ArrayList<>();

            if (inventoryItem instanceof Item) {
                Item item = (Item) inventoryItem;
                itemName = item.getName() != null ? item.getName() : "";
                unitPrice = item.getPrice();
                totalPrice += unitPrice * orderItemQuantity;

                TOOrderItem toOrderItem = new TOOrderItem(
                        orderItemQuantity,
                        itemName,
                        null,
                        unitPrice,
                        null
                );
                toOrderItems.add(toOrderItem);

            } else if (inventoryItem instanceof GradeBundle) {
                GradeBundle gradeBundle = (GradeBundle) inventoryItem;
                gradeBundleName = gradeBundle.getName() != null ? gradeBundle.getName() : "";

                int bundleItemsTotal = 0;
                int numBundleItemsSelected = 0;

                for (BundleItem bundleItem : gradeBundle.getBundleItems()) {
                    if (isLevelEligibleForOrder(orderLevel, bundleItem.getLevel())) {
                        numBundleItemsSelected++;
                        Item bundleItemItem = bundleItem.getItem();
                        unitPrice = bundleItemItem.getPrice();
                        int bundleItemQuantity = bundleItem.getQuantity();
                        itemName = bundleItemItem.getName();

                        double itemTotalPrice = unitPrice * orderItemQuantity * bundleItemQuantity;
                        bundleItemsTotal += itemTotalPrice;

                        TOOrderItem toOrderItem = new TOOrderItem(
                                bundleItemQuantity * orderItemQuantity,
                                itemName,
                                gradeBundleName.isEmpty() ? null : gradeBundleName,
                                unitPrice,
                                null
                        );
                        bundleOrderItems.add(toOrderItem);
                    }
                }

                totalPrice += bundleItemsTotal;

                if (numBundleItemsSelected > 1) {
                    double discountPercentage = gradeBundle.getDiscount() / 100.0;
                    double discountValue = bundleItemsTotal * discountPercentage;
                    totalPrice -= discountValue;

                    for (TOOrderItem bundleItem : bundleOrderItems) {
                        double thediscount = bundleItem.getPrice() * discountPercentage;
                        String discountAmount;
                        if (thediscount == Math.floor(thediscount)) {
                            discountAmount = "-" + String.valueOf((int) thediscount);
                        } else {
                            discountAmount = "-" + String.valueOf(thediscount);
                        }
                        bundleItem.setDiscount(discountAmount);
                        toOrderItems.add(bundleItem);
                    }
                } else {
                    toOrderItems.addAll(bundleOrderItems);
                }
            }
        }

        TOOrder toOrder = new TOOrder(
                parentEmail, studentName, status, orderNumber, orderDate,
                orderLevel.toString(), authorizationCode, penaltyAuthorizationCode, totalPrice
        );
        toOrder.setItems(toOrderItems);

        return toOrder;
    }

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
            double totalPrice = 0;

            List<OrderItem> orderItems = order.getOrderItems();
            List<TOOrderItem> toOrderItems = new ArrayList<>();

            for (OrderItem orderItem : orderItems) {
                InventoryItem inventoryItem = orderItem.getItem();
                int orderItemQuantity = orderItem.getQuantity();
                int unitPrice = 0;
                String itemName = "";
                String gradeBundleName = "";
                List<TOOrderItem> bundleOrderItems = new ArrayList<>();

                if (inventoryItem instanceof Item) {
                    // Regular Item
                    Item item = (Item) inventoryItem;
                    itemName = item.getName() != null ? item.getName() : "";
                    unitPrice = item.getPrice();
                    totalPrice += unitPrice * orderItemQuantity;

                    TOOrderItem toOrderItem = new TOOrderItem(
                            orderItemQuantity,
                            itemName,
                            null,
                            unitPrice,
                            null
                    );
                    toOrderItems.add(toOrderItem);

                } else if (inventoryItem instanceof GradeBundle) {
                    GradeBundle gradeBundle = (GradeBundle) inventoryItem;
                    gradeBundleName = gradeBundle.getName() != null ? gradeBundle.getName() : "";

                    int bundleItemsTotal = 0;
                    int numBundleItemsSelected = 0;

                    for (BundleItem bundleItem : gradeBundle.getBundleItems()) {
                        if (isLevelEligibleForOrder(orderLevel, bundleItem.getLevel())) {
                            numBundleItemsSelected++;
                            Item bundleItemItem = bundleItem.getItem();
                            unitPrice = bundleItemItem.getPrice();
                            int bundleItemQuantity = bundleItem.getQuantity();
                            itemName = bundleItemItem.getName();

                            double itemTotalPrice = unitPrice * orderItemQuantity * bundleItemQuantity;
                            bundleItemsTotal += itemTotalPrice;

                            TOOrderItem toOrderItem = new TOOrderItem(
                                    bundleItemQuantity * orderItemQuantity,
                                    itemName,
                                    gradeBundleName.isEmpty() ? null : gradeBundleName,
                                    unitPrice,
                                    null
                            );
                            bundleOrderItems.add(toOrderItem);
                        }
                    }

                    totalPrice += bundleItemsTotal;

                    if (numBundleItemsSelected > 1) {
                        double discountPercentage = gradeBundle.getDiscount() / 100.0;
                        double discountValue = bundleItemsTotal * discountPercentage;
                        totalPrice -= discountValue;

                        for (TOOrderItem bundleItem : bundleOrderItems) {
                            double theDiscount = bundleItem.getPrice() * discountPercentage;
                            String discountAmount;

                            if (theDiscount == Math.floor(theDiscount)) {
                                discountAmount = "-" + String.valueOf((int) theDiscount);
                            } else {
                                discountAmount = "-" + String.valueOf(theDiscount);
                            }
                            bundleItem.setDiscount(discountAmount);
                            toOrderItems.add(bundleItem);
                        }
                    } else {
                        toOrderItems.addAll(bundleOrderItems);
                    }
                }
            }

            TOOrder toOrder = new TOOrder(
                    parentEmail, studentName, status, orderNumber, orderDate,
                    orderLevel.toString(), authorizationCode, penaltyAuthorizationCode, totalPrice
            );
            toOrder.setItems(toOrderItems);
            toOrders.add(toOrder);
        }

        return toOrders;
    }


    // Start school year
    public static String startYear(String orderNumebr) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }


    public static String pickUpOrder(String orderNumber){
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}