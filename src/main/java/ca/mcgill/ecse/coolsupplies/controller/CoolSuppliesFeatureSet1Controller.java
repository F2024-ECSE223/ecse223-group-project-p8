package ca.mcgill.ecse.coolsupplies.controller;

import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet1Controller {


  private static List<TOParent> parents = new ArrayList<>();
	
  public static String updateAdmin(String password) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public static String addParent(String email, String password, String name, int phoneNumber) {
    TOParent newParent = new TOParent(email, password, name, phoneNumber);
    parents.add(newParent);
    return "Parent has been added";
  }

  public static String updateParent(String email, String newPassword, String newName,
      int newPhoneNumber) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public static String deleteParent(String email) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public static TOParent getParent(String email) {
    for (TOParent parent : parents) {
    	if (parent.getEmail().equals(email)) {
    		return parent;
    	}
    }
    return null;
  }

  // returns all parents
  public static List<TOParent> getParents() {
    return parents;
  }
}
