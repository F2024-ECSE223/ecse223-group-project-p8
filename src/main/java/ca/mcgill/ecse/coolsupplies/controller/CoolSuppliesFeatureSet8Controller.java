package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.*;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;

public class CoolSuppliesFeatureSet8Controller {

    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

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
                CoolSuppliesPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        }

        return ("The order has successfully been updated.");

    }

    // Add item to order
    public static String addItemToOrder(String invName, InventoryItem item, String orderNumber, int newQuantity) {
        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));
        String itemName;

        //For NotExist Test case - if item doesn't exist in systems
        try{ itemName = item.getName();}
        catch(NullPointerException e){ return String.format("Item %s does not exist.", invName);}


        if (order == null) {
            return String.format("Order %s does not exist", orderNumber);
        }
        else if (!InventoryItem.hasWithName(itemName)) {
            return String.format("Item %s does not exist.", invName);
        }

        List<OrderItem> orderItems = order.getOrderItems();
        for(OrderItem orderitem : orderItems){
            if(orderitem.getItem().getName().equals(itemName)){
                return String.format("Item %s already exists in the order %d.", invName, Integer.parseInt(orderNumber));
            }
        }

        if (newQuantity <= 0) {
            return ("Quantity must be greater than 0.");
        }
        else if (!order.getStatusFullName().equals("Started")){
            //Must separate because PickedUp needs to give an error message with space and lowercase
            if(order.getStatusFullName().equals("PickedUp")){
                return "Cannot add items to a picked up order";
            }
            else{
                return String.format("Cannot add items to a %s order",order.getStatusFullName().toLowerCase());
            }
        }
        else {
            try {
                OrderItem thisItem = coolSupplies.addOrderItem(newQuantity, order, item);
                order.add(thisItem);
                CoolSuppliesPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        return ("The item has successfully been added");
    }

    // Update quantity of an existing item of order
    public static String updateQuantity(String itemName, int newQuantity, int orderNumber) {

        if (!InventoryItem.hasWithName(itemName)) {
            return String.format("Item %s does not exist.", itemName);
        }

        Order order = Order.getWithNumber(orderNumber);

        if (!Order.hasWithNumber(orderNumber)) {
            return String.format("Order %d does not exist", orderNumber);
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
            return String.format("Item %s does not exist in the order %d.", itemName, orderNumber);
        }

        if (newQuantity <= 0) {
            return ("Quantity must be greater than 0.");
        }

        try {
            order.updateQuantityEvent(newQuantity, orderItem);
            CoolSuppliesPersistence.save();
        }
        catch (RuntimeException e) {
            return e.getMessage();
        }

        return ("The item's quantity has successfully been updated");
    }


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
                CoolSuppliesPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        return ("The order item has successfully been deleted.");
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
     *         Pays the penalty for the order
     * @param orderNumber              the number associated with the order
     * @param penaltyAuthorizationCode The authorization code for the penalty
     * @param authorizationCode        The authorization code for the order
     * @return indicates if the penalty was successfully paid
     */
    public static String payPenaltyForOrder(String orderNumber, String penaltyAuthorizationCode,
                                            String authorizationCode) {
        if (Order.hasWithNumber(Integer.parseInt(orderNumber))) {
            Order order = Order.getWithNumber(Integer.parseInt(orderNumber));
            if (authorizationCode.length() == 0) {
                return ("Authorization code is invalid");
            }
            if (penaltyAuthorizationCode.length() == 0) {
                return ("Penalty authorization code is invalid");
            }
            if (!order.hasOrderItems()) {
                return ("Order " + orderNumber + " has no items");
            }

            try {
                order.orderHasBeenPrepared(authorizationCode, penaltyAuthorizationCode);
                CoolSuppliesPersistence.save();
            } catch (RuntimeException e) {
                return (e.getMessage());
            }
        } else {
            return ("Order " + orderNumber + " does not exist");
        }
        return ("Done");

    }

    /**
     * @author Artimice Mirchi
     *         Pays the penalty for the order
     * @param orderNumber       the number associated with the order
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
                return ("Order " + orderNumber + " has no items");
            }
            String currStatus = order.getStatusFullName();
            try {
                order.orderHasBeenPaid(AuthorizationCode);
                CoolSuppliesPersistence.save();
            }

            catch (RuntimeException e) {
                return (e.getMessage());
            }
        } else {
            return ("Order " + orderNumber + " does not exist");
        }
        return ("Done");

    }

    // Cancel order
    public static String cancelOrder(String orderNumber) {
        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

        if (order != null) {
            try {
                order.cancel();
                CoolSuppliesPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        } else {
            return "Order " + orderNumber + " does not exist";
        }
        return "Order deleted successfully";

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
    public static String startYear(String orderNumber) {
        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

        if (order == null) {
            return "Order " + orderNumber + " does not exist";
        }

        try {
            order.startSchoolYear();
            CoolSuppliesPersistence.save();
            return "Successfully started school year";
        } catch (RuntimeException e) {
            return e.getMessage();
        }

    }

    public static String pickUpOrder(String orderNumber) {
        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

        if (order == null) {
            return "Order " + orderNumber + " does not exist";
        }

        Order.Status currentStatus = order.getStatus();

        if (currentStatus == Order.Status.Prepared) {
            order.setStatus(Order.Status.PickedUp);
            try {
                CoolSuppliesPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
            return "Order is picked up.";
        } else if (currentStatus == Order.Status.PickedUp) {
            return "The order is already picked up";
        } else {
            return "Cannot pickup a " + currentStatus.toString().toLowerCase() + " order";
        }
    }
}