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
    // Pay for order
    // TODO: State Machine is not implemented yet
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
    // TODO: State Machine is not implemented yet
    public static String cancelOrder(String orderNumber) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    // View individual order (including parent, student, status, number, date, level, authorization codes,
    //individual items and items in bundles including their prices and deducted discounts, and total
    //price)
    // TODO: State Machine is not implemented yet
    // View individual order (including parent, student, status, number, date,
    // level, authorization codes,
    // individual items and items in bundles including their prices and deducted
    // discounts, and total
    // price)
    // TODO: State Machine is not implemented yet
    public static TOOrder viewOrder(String index) {
        Order order = null;
        for(Order o: coolSupplies.getOrders()){
            if(o.getNumber() == Integer.parseInt(index)){
                order = o;
                break;
            }
        }

        if (order == null) {
            return null;
        }

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

        TOOrder toOrder = new TOOrder(parentEmail, studentName, status, orderNumber, orderDate, orderLevel.toString(),
                authorizationCode, penaltyAuthorizationCode, totalPrice);

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

            TOOrderItem toOrderItem = new TOOrderItem(quantity, itemName.isEmpty() ? gradeBundleName : itemName,
                    gradeBundleName, price, discount);
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
            totalPrice += ((price * quantity) * (100 - discount)) / 100;
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

            TOOrder toOrder = new TOOrder(parentEmail, studentName, status, orderNumber, orderDate,
                    orderLevel.toString(), authorizationCode, penaltyAuthorizationCode, totalPrice);

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

                TOOrderItem toOrderItem = new TOOrderItem(quantity, itemName.isEmpty() ? gradeBundleName : itemName,
                        gradeBundleName, price, discount);
                toOrderItems.add(toOrderItem);
            }

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
=======
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
                order.updateOrderEvent(purchaseLevel,student);
                OrderPersistence.save();
            }
            catch (RuntimeException e) {
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
//                OrderPersistence.save();
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
//                OrderPersistence.save();
            }
            catch (RuntimeException e) {
                return e.getMessage();
            }
        }
        return ("The order item has successfully been deleted.");
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
              
                order.delete();
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

    public static TOOrder viewOrder(String index) {
        Order order = Order.getWithNumber(Integer.parseInt(index));
        if(order == null)
            return null;

        int orderNumber = order.getNumber();
        Date orderDate = order.getDate();
        PurchaseLevel orderLevel = order.getLevel();
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

            TOOrderItem toOrderItem = new TOOrderItem(quantity, itemName.isEmpty() ? gradeBundleName : itemName, gradeBundleName, price, String.valueOf(discount));
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
            PurchaseLevel orderLevel = order.getLevel();
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

                TOOrderItem toOrderItem = new TOOrderItem(quantity, itemName.isEmpty() ? gradeBundleName : itemName, gradeBundleName, price, String.valueOf(discount));
                toOrderItems.add(toOrderItem);
            }

            toOrder.setItems(toOrderItems);

            toOrders.add(toOrder);
        }

        return toOrders;
    }


    // Start school year
    public static String startYear(String orderNumebr) {
        Order order = Order.getWithNumber(Integer.parseInt(orderNumebr));

        if (order == null){
            return "Order " + orderNumebr + " does not exist";
        }
        try {
            order.startSchoolYear();
            //OrderPersistence.save();
        }
        catch (RuntimeException e) {
            return e.getMessage();
        }

        return "Successfully started school year";
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

