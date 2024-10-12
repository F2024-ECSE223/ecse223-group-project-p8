package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;
import ca.mcgill.ecse.coolsupplies.model.User;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;
import ca.mcgill.ecse.coolsupplies.model.SchoolAdmin;

public class CoolSuppliesFeatureSet1Controller {

  private static CoolSupplies coolSupplies = new CoolSupplies();

  public static String updateAdmin(String password) {
    if (coolSupplies.hasAdmin()){
      SchoolAdmin schoolAdmin = coolSupplies.getAdmin();
      schoolAdmin.setPassword(password);
      return "Passord is updated successfully.";
    }
    else{
      return "Admin Account doesn't exist";
    }
  }

  public static String addParent(String email, String password, String name, int phoneNumber) {
    //gai
    Parent parent = new Parent(email, password, name, phoneNumber, coolSupplies);
    coolSupplies.addParent(parent);
    return "Parent is added successfully.";
  }

  public static String updateParent(String email, String newPassword, String newName,
      int newPhoneNumber) {
        //改
    Parent parent = (Parent) User.getWithEmail(email);

    if (parent != null){
      parent.setName(newName);
      parent.setPassword(newPassword);
      parent.setPhoneNumber(newPhoneNumber);
      return "Account is updated successfully.";
    }
    else{
      return "Account doesn't exist.";
    }
  }

  public static String deleteParent(String email) {
    Parent parent = (Parent) User.getWithEmail(email);
    if (parent != null){
      parent.delete();
      return "Account is deleted successfully.";
    }
    else{
      return "The parent does not exist.";
    }
  }

  public static TOParent getParent(String email) {
    Parent parent = (Parent) User.getWithEmail(email);
    TOParent toparent = new TOParent(parent.getEmail(), parent.getPassword(), parent.getName(), parent.getPhoneNumber());
    return toparent;
  }

  // returns all parents
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
