
package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.BundleItem;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import ca.mcgill.ecse.coolsupplies.model.Item;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;

/**
 * Controller for managing the bundle items of a grade bundle in the CoolSupplies system (adding, updating, deleting, and retrieving).
 * 
 * @author Snigdha Sen
 */
public class CoolSuppliesFeatureSet5Controller {
  private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * Adds a new bundle item of a grade bundle of the system
   *
   * @param quantity The quantity of the bundle item to be added to a grade bundle
   * @param level The purchase level of the bundle item to be added to a grade bundle (Mandatory, Recommended, or Optional)
   * @param itemName The name of the bundle item to be added to a grade bundle
   * @param bundleName The name of the grade bundle at which the bundle item in question is to be added
   * @return indicates if the bundle item has been successfully added to the grade bundle
   * @author Snigdha Sen
   */
  public static String addBundleItem(int quantity, String level, String itemName,
      String bundleName) {      
      GradeBundle bundle = (GradeBundle) GradeBundle.getWithName(bundleName);

      if(quantity <= 0){
        return "The quantity must be greater than 0.";
      }

      else if(level.equals("Mandatory") == false & level.equals("Recommended") == false & level.equals("Optional") == false){
        return "The level must be Mandatory, Recommended, or Optional.";
      }

      else if(!GradeBundle.hasWithName(bundleName)){
        return "The grade bundle does not exist.";
      }

      else if(!Item.hasWithName(itemName)){
        return "The item does not exist.";
      }

      else{
        List<BundleItem> itemList = bundle.getBundleItems();

        for(BundleItem item : itemList){
          String nameItemList = item.getItem().getName();
          if(itemName.equals(nameItemList)){
            return "The item already exists for the bundle.";
          }
        }
    
        Item aItem = (Item) Item.getWithName(itemName);
        PurchaseLevel purchaseLevel = PurchaseLevel.valueOf(level);
        BundleItem newItem = new BundleItem(quantity, purchaseLevel, coolSupplies, bundle, aItem); 
        coolSupplies.addBundleItem(newItem);
        return "The bundle item has been succesfully added";
    }
  }

  /**
   * Updates an existing bundle item of a grade bundle of the system
   *
   * @param itemName The name of the bundle item to be updated
   * @param bundleName The name of the grade bundle at which the bundle item in question is to be updated
   * @param newQuantity The updated quantity of the bundle item in question
   * @param newlevel The updated purchase level of the bundle item in question (Mandatory, Recommended, or Optional)
   * @return indicates if the bundle item of a grade bundle has been successfully updated
   * @author Snigdha Sen
   */
  public static String updateBundleItem(String itemName, String bundleName, int newQuantity,
      String newLevel) {
    GradeBundle bundle = (GradeBundle) GradeBundle.getWithName(bundleName);
    if(bundle == null || !GradeBundle.hasWithName(bundleName)){
      return "The grade bundle does not exist.";
    }

    else if(newQuantity <= 0){
      return "The quantity must be greater than 0.";
    }

    else if(newLevel.equals("Mandatory") == false & newLevel.equals("Recommended") == false & newLevel.equals("Optional") == false){
      return "The level must be Mandatory, Recommended, or Optional.";
    }

    List<BundleItem> itemList = bundle.getBundleItems();
    for(BundleItem item : itemList){
      String nameItemList = item.getItem().getName();
      if(itemName.equals(nameItemList)){
        item.setQuantity(newQuantity);
        PurchaseLevel newPurchaseLevel = PurchaseLevel.valueOf(newLevel);
        item.setLevel(newPurchaseLevel);  
        return "The bundle item has successfully been updated";     
      }
    }

    return "The bundle item does not exist for the grade bundle.";
  }

  /**
   * Deletes an existing bundle item of a grade bundle of the system
   *
   * @param itemName The name of the bundle item to be deleted
   * @param bundleName The name of the grade bundle at which the bundle item in question is to be deleted
   * @return indicates if the bundle item of a grade bundle has been successfully deleted
   * @author Snigdha Sen
   */
  public static String deleteBundleItem(String itemName, String bundleName) {
    GradeBundle bundle = (GradeBundle) GradeBundle.getWithName(bundleName);
    if(bundle == null || !GradeBundle.hasWithName(bundleName)){
      return "The grade bundle does not exist.";
    }

    List<BundleItem> itemList = bundle.getBundleItems();
    for(BundleItem item : itemList){
      String nameItemList = item.getItem().getName();
      if(itemName.equals(nameItemList)){
        item.delete();
        return "The bundle item has been deleted.";
      }
    }

    return "The bundle item does not exist.";
  } 

  /**
   * Retrieves a specific bundle item of a grade bundle of the system
   *
   * @param itemName The name of the bundle item to be retrieved
   * @param bundleName The name of the grade bundle containing the bundle item of interest
   * @return the TOBundleItem object representing the bundle item of a specific grade bundle
   * @author Snigdha Sen
   */
  public static TOBundleItem getBundleItem(String itemName, String bundleName) {
      GradeBundle bundle = (GradeBundle) GradeBundle.getWithName(bundleName);
      if(bundle == null || !GradeBundle.hasWithName(bundleName) || !Item.hasWithName(itemName)){
        return null;
      }

      List<BundleItem> itemList = bundle.getBundleItems();

      for(BundleItem item : itemList){       
        String name = item.getItem().getName();
        if(name.equals(itemName)){
          TOBundleItem toItem = new TOBundleItem(item.getQuantity(), item.getLevel().toString(), itemName, bundleName);
          return toItem;
        }  
      }

      return null;
  }

  // returns all bundle items of a bundle
  /**
   * Retrieves all bundle items of a grade bundle of the system
   *
   * @param bundleName The name of the grade bundle containing the bundle items of interest
   * @return a list of TOBundleItem objects representing all of the bundle items of a specific grade bundle
   * @author Snigdha Sen
   */
  public static List<TOBundleItem> getBundleItems(String bundleName) {
    GradeBundle bundle = (GradeBundle) GradeBundle.getWithName(bundleName);
    if(bundle == null || !GradeBundle.hasWithName(bundleName)){
      return null;
    }
    List<BundleItem> listBundleItems = bundle.getBundleItems();
    List<TOBundleItem> listTOBundleItems = new ArrayList<>();

    for(BundleItem item : listBundleItems){
      TOBundleItem toItem = new TOBundleItem(item.getQuantity(), item.getLevel().toString(), item.getItem().getName(), bundleName);
      listTOBundleItems.add(toItem);
    }

    return listTOBundleItems;
  }
}

