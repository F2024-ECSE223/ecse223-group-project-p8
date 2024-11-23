package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.User;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;

import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;


/**
 * Controller for managing admin accounts and parent accounts within the CoolSupplies system.
 *
 * @author Mary Li
 */
public class CoolSuppliesFeatureSet1Controller {

  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

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
      try {
        CoolSuppliesPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
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
    if (email.isEmpty()){
      return "The email must not be empty.";
    }

    if (name.isEmpty()){
      return "The name must not be empty.";
    }

    if (password.isEmpty()){
      return "The password must not be empty.";
    }

    if (email.equals("admin@cool.ca")){
      return "The email must not be admin@cool.ca.";
    }

    if (email.contains(" ")){
      return "The email must not contain spaces.";
    }

    String emailFormat = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    Pattern pattern = Pattern.compile(emailFormat);
    Matcher matcher = pattern.matcher(email);
    if (!matcher.matches()){
      return "The email must be well-formed.";
    }

    if (Integer.toString(phoneNumber).length() != 7){
      return "The phone number must be seven digits.";
    }

    if (User.hasWithEmail(email)) {
      return "The email must be unique.";
    }

    Parent parent = new Parent(email, password, name, phoneNumber, coolSupplies);
    coolSupplies.addParent(parent);
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }

    return "Account added successfully.";
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
      if (newName.isEmpty()){
        return "The name must not be empty.";
      }

      if (newPassword.isEmpty()){
        return "The password must not be empty.";
      }

      if (Integer.toString(newPhoneNumber).length() != 7){
        return "The phone number must be seven digits.";
      }

      parent.setName(newName);
      parent.setPassword(newPassword);
      parent.setPhoneNumber(newPhoneNumber);
      try {
        CoolSuppliesPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
      return "Account updated successfully.";
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
      try {
        CoolSuppliesPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
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
