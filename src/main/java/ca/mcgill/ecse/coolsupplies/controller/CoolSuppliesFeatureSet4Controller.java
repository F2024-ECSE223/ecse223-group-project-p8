package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.GradeBundle;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;


/**
 * This class provides the controller methods to add, update, delete, and get Bundles from the system
 *
 * @author Jyothsna Seema
 */

public class CoolSuppliesFeatureSet4Controller {

  private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();
  /**
     * Adds a bundle with the given name, discount and grade level to the system.
     *
     * @param name          The name of the bundle.
     * @param discount      The dicount applied on the bundle.
     * @param gradeLevel    The grade level of the bundle.
     * @return              A message indicating whether the bundle was successfully added.
     */

  public static String addBundle(String name, int discount, String gradeLevel) {

    Grade grade = Grade.getWithLevel(gradeLevel);

    if (GradeBundle.hasWithName(name)) {
      return ("The name must be unique.");
    }

    else if (discount<0 || discount>100) {
      return ("The discount must be greater than or equal to 0 and less than or equal to 100.");
    }

    else if (name.length() == 0) {
      return ("The name must not be empty.");
    }

    else if (!Grade.hasWithLevel(gradeLevel)) {
      return ("The grade does not exist.");
    }

    else if (grade.hasBundle()) {
      return ("A bundle already exists for the grade.");
    }

    else {
      GradeBundle bundle = new GradeBundle (name, discount, coolSupplies, grade);
      coolSupplies.addBundle(bundle);
      try {
        CoolSuppliesPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }

      return ("The bundle has been added successfully.");
    }
  }

  /**
     * Updates the information of an existing bundle, changing their name and discount.
     *
     * @param name          The current name of the bundle.
     * @param newName       The new name of the bundle.
     * @param newDiscount   The new discount of the bundle.
     * @return              A message indicating whether the bundle was successfully updated.
     */

  public static String updateBundle(String name, String newName, int newDiscount,
      String newGradeLevel) {
      
        Grade newGrade = Grade.getWithLevel(newGradeLevel);

    if (!GradeBundle.hasWithName(name)) {
      return ("The bundle does not exist.");
    }

    else if (newDiscount<0 || newDiscount>100) {
      return ("The discount must be greater than or equal to 0 and less than or equal to 100.");
    }

    else if (newName.length() == 0) {
      return ("The name must not be empty.");
    }

    else if (!Grade.hasWithLevel(newGradeLevel)) {
      return ("The grade does not exist.");
    }

    else if (newGrade.hasBundle()) {
      return ("A bundle already exists for the grade.");
    }
    
    else if (GradeBundle.hasWithName(newName)) {
      return ("The name must be unique.");
    }

    else {
      GradeBundle bundle = (GradeBundle) GradeBundle.getWithName(name);
      bundle.setName(newName);
      bundle.setDiscount(newDiscount);
      bundle.setGrade(newGrade);
      try {
        CoolSuppliesPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
      return ("The bundle has been successfully updated.");
    }

  }

  /**
     * Deletes a bundle from the system.
     *
     * @param name      The name of the bundle to be deleted.
     * @return          A message indicating whether the bundle was successfully deleted.
     */

  public static String deleteBundle(String name) {
    if (GradeBundle.hasWithName(name)) {
      GradeBundle bundle = (GradeBundle) GradeBundle.getWithName(name);
      bundle.delete();
      try {
        CoolSuppliesPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
      return "Bundle has been deleted.";
    }

    return ("The grade bundle does not exist.");
  }

  /**
     * Retrieves information about a specific bundle.
     *
     * @param name      The name of the bundle to retrieve.
     * @return          A TOGradeBundle transfer object representing the bundle,
     *                  or NULL if the bundle is not found.
     */

  public static TOGradeBundle getBundle(String name) {
    for (GradeBundle bundle : coolSupplies.getBundles()) {
      if (bundle.getName().equals(name)) {
        return new TOGradeBundle(bundle.getName(),
                bundle.getDiscount(), bundle.getGrade().getLevel());

      }
    }
    return null;
  }

  /**
     * Retrieves a list of all bundles in the system.
     *
     * @return          A list of TOGradeBundle transfer objects representing all bundles in the system.
     */

  // returns all bundles
  public static List<TOGradeBundle> getBundles() {
    List<TOGradeBundle> toGradeBundle = new ArrayList<>();
    List<GradeBundle> bundles = coolSupplies.getBundles();

    for (GradeBundle bundle : bundles) {
      toGradeBundle.add(new TOGradeBundle(bundle.getName(), bundle.getDiscount(), bundle.getGrade().getLevel()));
    }

    return toGradeBundle;
  }

  }