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
                CoolSuppliesPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        }

        return ("The order has successfully been updated.");
    }

    /**
     * Adds item to an order
     * @author Jyothsna Seema, Snigdha Sen
     * @param invName the name of item
     * @param item the Inventory item object with that name
     * @param orderNumber the order number of the order
     * @param newQuantity the quantity of the item of interest
     * @return indicates if the item was succesfully added to a specific order
     */
    public static String addItemToOrder(String invName, InventoryItem item, String orderNumber, int newQuantity) {
        if (newQuantity <= 0) {
            return "Quantity must be greater than 0.";
        }

        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));
        if (order == null) {
            return String.format("Order %s does not exist", orderNumber);
        }

        if (!order.getStatusFullName().equals("Started")) {
            if (order.getStatusFullName().equals("PickedUp")) {
                return "Cannot add items to a picked up order";
            } else {
                return String.format("Cannot add items to a %s order", order.getStatusFullName().toLowerCase());
            }
        }

        if (item == null || !InventoryItem.hasWithName(invName)) {
            return String.format("Item %s does not exist.", invName);
        }

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem existingItem : orderItems) {
            if (existingItem.getItem().getName().equals(item.getName())) {
                return String.format("Item %s already exists in the order %d.", invName, Integer.parseInt(orderNumber));
            }
        }

        double unitPrice = 0;
        if (item instanceof Item) {
            Item aItem = (Item) item;
            unitPrice = aItem.getPrice();
            double itemTotalPrice = unitPrice * newQuantity;
            order.setTotalPrice(order.getTotalPrice() + itemTotalPrice);

        } else if (item instanceof GradeBundle) {
            GradeBundle gradeBundle = (GradeBundle) item;
            String gradeBundleName = gradeBundle.getName() != null ? gradeBundle.getName() : "";

            int numBundleItemsSelected = 0;
            double bundleItemsTotal = 0;

            for (BundleItem bundleItem : gradeBundle.getBundleItems()) {
                if (isLevelEligibleForOrder(order.getLevel(), bundleItem.getLevel())) {
                    numBundleItemsSelected++;
                    Item bundleItemItem = bundleItem.getItem();
                    unitPrice = bundleItemItem.getPrice();
                    int bundleItemQuantity = bundleItem.getQuantity();

                    double itemTotalPrice = unitPrice * newQuantity * bundleItemQuantity;
                    bundleItemsTotal += itemTotalPrice;
                }
            }

            if (numBundleItemsSelected > 1) {
                int discountPercentage = gradeBundle.getDiscount();
                double discountValue = bundleItemsTotal * discountPercentage / 100.0;
                bundleItemsTotal -= discountValue;
            }

            order.setTotalPrice(order.getTotalPrice() + bundleItemsTotal);
        }

        try {
            OrderItem thisItem = coolSupplies.addOrderItem(newQuantity, order, item);
            order.add(thisItem);
            CoolSuppliesPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }

        return "The item has successfully been added.";
    }

    /**
     * Updates the quantity of an existing item in a specified order.
     *
     * @param itemName    the name of the item to update
     * @param newQuantity the new quantity to set for the item
     * @param orderNumber the number of the order containing the item
     * @return a message indicating the result of the operation
     * @author Jyothsna Seema
     * @author Jiatian Liu
     */
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
                CoolSuppliesPersistence.save();
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
    /**
     * Cancels an order.
     *
     * @authoer Zhengxuan Zhao
     * @param orderNumber the number of the order
     * @return a message indicating the result of the cancellation operation
     */
    public static String cancelOrder(String orderNumber) {
        Order order = Order.getWithNumber(Integer.parseInt(orderNumber));

        if (order != null) {
            try {
                order.cancel();

                order.delete();
                CoolSuppliesPersistence.save();
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

    /**
     * A Helpeer method that determines if a bundle item is eligible to be included in an order
     * based on the order's purchase level.
     *
     * This method checks if the item's level (Mandatory, Recommended, or Optional)
     * matches the purchase level of the order. The eligibility criteria are as follows:
     * - If the order level is "Mandatory", only items with the "Mandatory" level are included.
     * - If the order level is "Recommended", both "Mandatory" and "Recommended" items are included.
     * - If the order level is "Optional", all items are included regardless of their level.
     *
     * @author Mary Li
     * @param orderLevel the purchase level of the order (Mandatory, Recommended, or Optional)
     * @param itemLevel the purchase level of the item to be checked
     * @return true if the item's level is eligible for inclusion in the order, false otherwise
     */
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

    /**
     * Retrieves the details of a specific order using its order number.
     * This method handles both regular items and GradeBundle items within the order.
     * For GradeBundle items, it adds each eligible bundle item separately and applies
     * a discount if more than one unique item is included from the bundle.
     *
     * The method returns a `TOOrder` object containing high-level information about the order,
     * including:
     * - Order number
     * - Order date
     * - Purchase level (Mandatory, Recommended, Optional)
     * - Parent's email associated with the order
     * - Student's name associated with the order
     *
     * If the specified order does not exist, the method returns `null`.
     *
     * @author Mary Li, Zhengxuan Zhao
     * @param index the order number (1-based index) to retrieve the order details
     * @return a `TOOrder` object containing the details of the specified order, or `null` if the order is not found
     */
    public static TOOrder viewOrder(String index) {
        Order order = Order.getWithNumber(Integer.parseInt(index));
        if (order == null) {
            return null;
        }

        int orderNumber = order.getNumber();
        Date orderDate = order.getDate();
        BundleItem.PurchaseLevel orderLevel = order.getLevel();

        String parentEmail = order.getParent().getEmail();
        String studentName = order.getStudent().getName();

        return new TOOrder(orderNumber,orderDate,orderLevel.toString(),parentEmail,studentName);
    }

    /**
     * Retrieves a list of all existing orders in the system.
     * This method processes both regular items and GradeBundle items within each order.
     * For GradeBundle items, each eligible bundle item is added separately to the order's details.
     * Additionally, a discount is applied if more than one unique item is included from the same bundle.
     *
     * Each order is represented as a `TOOrder` object, containing details such as:
     * - Order number
     * - Order date
     * - Purchase level (Mandatory, Recommended, Optional)
     * - Parent's email associated with the order
     * - Student's name associated with the order
     *
     * @author Mary Li, Zhengxuan Zhao
     * @return a list of `TOOrder` objects, each representing an order's basic information in the system
     */
    public static List<TOOrder> viewOrders() {
        List<TOOrder> toOrders = new ArrayList<>();

        for (Order order : coolSupplies.getOrders()) {
            int orderNumber = order.getNumber();
            Date orderDate = order.getDate();
            BundleItem.PurchaseLevel orderLevel = order.getLevel();

            String parentEmail = order.getParent().getEmail();
            String studentName = order.getStudent().getName();

            toOrders.add(new TOOrder(orderNumber,orderDate,orderLevel.toString(),parentEmail,studentName));
        }

        return toOrders;
    }

    /**
     * Starts the school year for the specified order.
     * Changes the order status based on its current state:
     * - 'Started' orders become 'Penalized'
     * - 'Paid' orders become 'Prepared'
     *
     * @param orderNumber the number of the order to process
     * @return a message indicating the result of the operation
     * @throws RuntimeException if the school year has already been started for the order or the order does not exist
     *
     * @author Mary Li, Shengyi Zhong
     */
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

    /**
     * Picks up an order by changing its status from 'Prepared' to 'PickedUp'.
     *
     * @param orderNumber the number of the order to pick up
     * @return a message indicating the result of the operation
     * @throws RuntimeException if the order cannot be picked up due to its current status
     *
     * @author Shengyi Zhong
     */
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
