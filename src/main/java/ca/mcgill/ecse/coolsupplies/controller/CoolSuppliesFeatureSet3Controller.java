package ca.mcgill.ecse.coolsupplies.controller;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

import java.util.ArrayList;


import java.util.List;

/**
 * @author Artimice Mirchi 
 * */


public class CoolSuppliesFeatureSet3Controller {

  private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * Adds a new item to the system
   *
   * @param name The name of the item to be added
   * @param price The price of the item
   * @return indicates if the item has been successfully added
   */
  public static String addItem(String name, int price) {
    if (Item.hasWithName(name)) {
      return ("The name must be unique.");
    }
    else if (price < 0) {
      return("The price must be greater than or equal to 0.");
    }
    else if (name.length() == 0) {
      return("The name must not be empty.");
    }
    else {
      Item currItem = new Item(name, price, coolSupplies);
      coolSupplies.addItem(currItem);


      return("The item has been successfully added");
    }}

  /**
   *Updates an item name and/or price
   * @param name The name of the item to be updated
   * @param newName The updated name of the item
   * @param newPrice The updated price of the item
   * @return indicates if the items name and/or price has successfully been modified
   */
  public static String updateItem(String name, String newName, int newPrice) {
    if (!Item.hasWithName(name)) {
      return ("The item does not exist.");
    }
    else if (newName.length() == 0) {
      return ("The name must not be empty.");
    }
    else if (newPrice < 0){
      return ("The price must be greater than or equal to 0.");
    }
    else if (Item.hasWithName(newName)) {
      return ("The name must be unique.");
    }
    else {
      Item currItem = (Item) Item.getWithName(name);
      currItem.setPrice(newPrice);
      currItem.setName(newName);
      return("The item has successfully been updated");
    }

  }

  /**
   *Deletes an item from the system
   * @param name The name of the item to be deleted
   * @return indicates if the item has successfully been deleted
   */

  public static String deleteItem(String name) {
    try{
      Item currItem = (Item) Item.getWithName(name);
      currItem.delete();
      return("The item has successfully been deleted");
    }
    catch (NullPointerException e) {
      return("The item does not exist.");

    }
  }

  /**
   * Retrieves a specific object from a list of all objects
   * @param name Name of the item
   * @return the TOItem object that represents the given name
   */
  public static TOItem getItem(String name) {
    for (Item item : coolSupplies.getItems()) {
      if (item.getName().equals(name)) {
        return new TOItem(item.getName(),
                item.getPrice());

      }
    }
    return null;
  }



  /**
   * @author Artimice Mirchi
   *Retrieve all the items in the system
   * @return a list of the TOItem objects currently in the system
   */

  public static List<TOItem> getItems() {
    List<TOItem> toItems = new ArrayList<>();
    List<Item> items = coolSupplies.getItems();
    for (Item item : items){
      TOItem toItem = new TOItem(item.getName(), item.getPrice());
      toItems.add(toItem);

    }
    return toItems;
  }}
