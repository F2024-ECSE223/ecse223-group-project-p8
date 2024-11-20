package ca.mcgill.ecse.coolsupplies.javafx.fxml.controllers;

import ca.mcgill.ecse.coolsupplies.model.Order;

public class ViewOrderWindowController {
    private Order currentOrder;

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
        System.out.println(order);
    }

}
