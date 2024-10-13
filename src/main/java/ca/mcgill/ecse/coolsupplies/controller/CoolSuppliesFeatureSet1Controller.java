package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;
import ca.mcgill.ecse.coolsupplies.model.User;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;

/**
 * Controller for managing admin accounts and parent accounts within the CoolSupplies system.
 */
public class CoolSuppliesFeatureSet1Controller {

  private static CoolSupplies coolSupplies = new CoolSupplies();

  /**
   * Updates the password of the admin account.
   *
   * @param password The new password to be set for the admin account.
   * @return A message indicating whether the password was updated successfully or if the admin account does not exist.
   */
  public static String updateAdmin(String password) {
    if (coolSupplies.hasAdmin()){
      SchoolAdmin schoolAdmin = coolSupplies.getAdmin();
      schoolAdmin.setPassword(password);
      return "Admin password is updated successfully.";
    }
    else{
      return "Admin Account doesn't exist";
    }
  }

  /**
   * Adds a new parent to the system.
   *
   * @param email       The email of the new parent.
   * @param password    The password of the new parent.
   * @param name        The name of the new parent.
   * @param phoneNumber The phone number of the new parent.
   * @return A message indicating if the parent was added successfully or if a parent with the given email already exists.
   */
  public static String addParent(String email, String password, String name, int phoneNumber) {
    if (User.hasWithEmail(email)) {
      return "A parent with this email already exists.";
    }

    Parent parent = new Parent(email, password, name, phoneNumber, coolSupplies);
    coolSupplies.addParent(parent);
    return "Parent added successfully.";
  }

  /**
   * Updates the details of an existing parent in the system.
   *
   * @param email         The email of the parent to update.
   * @param newPassword   The new password for the parent.
   * @param newName       The new name for the parent.
   * @param newPhoneNumber The new phone number for the parent.
   * @return A message indicating if the parent account was updated successfully or if the parent does not exist.
   */
  public static String updateParent(String email, String newPassword, String newName,
      int newPhoneNumber) {
    Parent parent = (Parent) User.getWithEmail(email);

    if (parent != null){
      parent.setName(newName);
      parent.setPassword(newPassword);
      parent.setPhoneNumber(newPhoneNumber);
      return "Parent account updated successfully.";
    }
    else {
      return "The parent does not exist.";
    }
  }

  /**
   * Deletes a parent account from the system.
   *
   * @param email The email of the parent to delete.
   * @return A message indicating if the parent account was deleted successfully or if the parent does not exist.
   */
  public static String deleteParent(String email) {
    Parent parent = (Parent) User.getWithEmail(email);
    if (parent != null){
      parent.delete();
      return "Parent account deleted successfully.";
    }
    else{
      return "The parent does not exist.";
    }
  }

  /**
   * Retrieves a parent account from the system based on their email.
   *
   * @param email The email of the parent to retrieve.
   * @return A TOParent object representing the parent or null if the parent does not exist.
   */
  public static TOParent getParent(String email) {
    Parent parent = (Parent) User.getWithEmail(email);
    if (parent == null) {
      return null;
    }
    TOParent toparent = new TOParent(parent.getEmail(), parent.getPassword(), parent.getName(), parent.getPhoneNumber());
    return toparent;
  }

  // returns all parents
  /**
   * Retrieves all parent accounts in the system.
   *
   * @return A list of TOParent objects representing all parents in the system.
   */
  public static List<TOParent> getParents() {
    List<Parent> parents = coolSupplies.getParents();
    List<TOParent> toParents = new ArrayList<>();

    for (Parent parent : parents) {
      TOParent toParent = new TOParent(parent.getEmail(), parent.getPassword(), parent.getName(), parent.getPhoneNumber());
      toParents.add(toParent);
    }

    return toParents;
  }

}
