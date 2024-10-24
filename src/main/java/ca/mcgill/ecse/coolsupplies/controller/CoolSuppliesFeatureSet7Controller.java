package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;

/**
 * Controller for managing grades in the CoolSupplies system.
 * This class includes methods for adding, updating, deleting, and retrieving grades.
 *
 * @author Jiatian Liu
 */
public class CoolSuppliesFeatureSet7Controller {
  // Reference to the CoolSupplies system
  private static CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  // Private constructor to prevent instantiation
  private CoolSuppliesFeatureSet7Controller() {}

  /**
   * Adds a new grade with the specified level.
   *
   * @param level the level of the grade to add
   * @return an error message if the grade cannot be added, or an empty string if successful
   * @author Jiatian Liu
   */
  public static String addGrade(String level) {
    if (level.isEmpty()) {
      return "The level must not be empty.";
    }
    if (Grade.hasWithLevel(level)) {
      return "The level must be unique.";
    }
    try {
      coolSupplies.addGrade(level);
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * Updates the grade with the specified level to a new level.
   *
   * @param level the current level of the grade to update
   * @param newLevel the new level for the grade
   * @return an error message if the grade cannot be updated, or an empty string if successful
   * @author Jiatian Liu
   */
  public static String updateGrade(String level, String newLevel) {
    if (!Grade.hasWithLevel(level)) {
      return "The grade does not exist.";
    }
    if (newLevel.isEmpty()) {
      return "The level must not be empty.";
    }
    if (Grade.hasWithLevel(newLevel)) {
      return "The level must be unique.";
    }
    Grade grade = Grade.getWithLevel(level);
    try {
      grade.setLevel(newLevel);
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * Deletes the grade with the specified level.
   *
   * @param level the level of the grade to delete
   * @return an error message if the grade cannot be deleted, or an empty string if successful
   * @author Jiatian Liu
   */
  public static String deleteGrade(String level) {
    Grade grade = Grade.getWithLevel(level);
    if (grade == null) {
      return "The grade does not exist.";
    }
    try {
      grade.delete();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * Retrieves the grade with the specified level.
   *
   * @param level the level of the grade to retrieve
   * @return a TOGrade object representing the requested grade, or null if not found
   * @author Jiatian Liu
   */
  public static TOGrade getGrade(String level) {
    Grade grade = Grade.getWithLevel(level);
    if (grade == null) {
      return null;
    }
    return new TOGrade(level);
  }

  /**
   * Retrieves a list of all grades in the system.
   *
   * @return a list of TOGrade objects representing all grades
   * @author Jiatian Liu
   */
  public static List<TOGrade> getGrades() {
    var grades = new ArrayList<TOGrade>();
    for (var grade : coolSupplies.getGrades()) {
      grades.add(new TOGrade(grade.getLevel()));
    }
    return grades;
  }

}
