package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Parent;

import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet1Controller {

    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    public static String updateAdmin(String password) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static String addParent(String email, String password, String name, int phoneNumber) {
        try {
            Parent newParent = new Parent(email, password, name, phoneNumber, coolSupplies);
            coolSupplies.addParent(newParent);
        } catch (Exception e) {
            return "Parent has been already added";
        }
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
        for (Parent parent : coolSupplies.getParents()) {
            if (parent.getEmail().equals(email)) {
                return new TOParent(parent.getEmail(),
                        parent.getPassword(),
                        parent.getName(),
                        parent.getPhoneNumber());
            }
        }
        return null;
    }

    // returns all parents
    public static List<TOParent> getParents() {
        List<TOParent> parents = new ArrayList<>();
        for (Parent parent : coolSupplies.getParents()) {
            System.out.println(parent);
            parents.add(new TOParent(parent.getEmail(),
                    parent.getPassword(),
                    parent.getName(),
                    parent.getPhoneNumber()));
        }
        return parents;
    }
}
