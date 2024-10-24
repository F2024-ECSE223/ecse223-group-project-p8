package ca.mcgill.ecse.coolsupplies.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Student;
import ca.mcgill.ecse.coolsupplies.model.User;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import ca.mcgill.ecse.coolsupplies.model.Order;
import ca.mcgill.ecse.coolsupplies.model.Parent;

/**
 * This Controller provides methods to manage students and start orders from the parent
 * @author Shengyi Zhong
 */
public class CoolSuppliesFeatureSet6Controller {

  /**
   * Adds a student to the parent's list of students.
   * 
   * @param studentName The name of the student to add.
   * @param parentEmail The email of the parent.
   * @return A message indicating whether the student was successfully added or if the student is already associated with the parent.
   * @throws IllegalArgumentException If the student does not exist.
   */
  public static String addStudentToParent(String studentName, String parentEmail) {
    Student student = Student.getWithName(studentName);
    Parent parent = (Parent) User.getWithEmail(parentEmail);

    // Return an error message if the student does not exist
    if (student == null) {
        return "The student does not exist.";
    }

    // Return an error message if the parent does not exist
    if (parent == null) {
        return "The parent does not exist.";
    }

    // Successfully add a student to a parent in the system
    Boolean isAdded = parent.addStudent(student);
    if (isAdded) {
        return "Student " + studentName + " added to parent " + parentEmail;
    } else {
        return "Student " + studentName + " is already associated with parent " + parentEmail;
    }
  }

  /**
   * Removes a student from the parent's list of students.
   * 
   * @param studentName The name of the student to remove.
   * @param parentEmail The email of the parent.
   * @return A message indicating whether the student was successfully removed.
   * @throws IllegalArgumentException If the student or parent does not exist or the student is not associated with the parent.
   */
  public static String deleteStudentFromParent(String studentName, String parentEmail) {
    Student student = Student.getWithName(studentName);

    // Return an error message if the student does not exist
    if (student == null) {
        return "The student does not exist.";
    }

    User user = User.getWithEmail(parentEmail);

    // Return an error message if the parent does not exist
    if (user == null || !(user instanceof Parent)) {
        return "The parent does not exist.";
    }

    Parent parent = (Parent) user;

    Boolean isRemoved = parent.removeStudent(student);
    if (isRemoved) {
        // Successfully remove a student from the students of a parent in the system
        return "Student " + studentName + " removed from parent " + parentEmail;
    } else {
        // Return an error message if the student is not associated with the parent
        return "The student does not exist for this parent.";
    }
  }

  /**
   * Retrieves a specific student from a parent's list of students.
   * 
   * @param studentName The name of the student to retrieve.
   * @param parentEmail The email of the parent.
   * @return The TOStudent object representing the student if found, otherwise null.
   */
  public static TOStudent getStudentOfParent(String studentName, String parentEmail) {
    Student student = Student.getWithName(studentName);
    Parent parent = (Parent) User.getWithEmail(parentEmail);
    // Unsuccessfully get a student from the students of a parent that does not exist in the system
    if (parent == null) {
      return null;
    }
    List<Student> students= parent.getStudents();
    
    if (students != null && students.contains(student)) {
      // Successfully get a student from the students of a parent in the system
      return new TOStudent(student.getName(), student.getGrade().getLevel());
    } else {
      // Unsuccessfully get a student that does not exist in the students of a parent in the system
      return null;
    }
  }

  /**
   * Retrieves all students associated with a parent.
   * 
   * @param parentEmail The email of the parent.
   * @return A list of TOStudent objects representing all students of the parent, or null if the parent does not exist.
   */
  // returns all students of a parent
  public static List<TOStudent> getStudentsOfParent(String parentEmail) {
    Parent parent = (Parent) User.getWithEmail(parentEmail);
    // Unsuccessfully get all students of a parent that does not exist in the system
    if (parent == null) {
      return new ArrayList<>();
    }

    List<Student> students= parent.getStudents();
    List<TOStudent> toStudents = new ArrayList<>();
    
    for (Student student : students) {
        TOStudent toStudent = new TOStudent(student.getName(), student.getGrade().getLevel());
        toStudents.add(toStudent);
    }
    
    // Successfully get all students of a parent in the system
    return toStudents;
  }

  /**
   * Creates and starts a new order for a parent and student.
   * 
   * @param number The unique order number.
   * @param date The date of the order.
   * @param level The purchase level (Mandatory, Recommended, or Optional).
   * @param parentEmail The email of the parent placing the order.
   * @param studentName The name of the student associated with the order.
   * @return A message indicating success, or an error message if any validation fails.
   */
  public static String startOrder(int number, Date date, String level, String parentEmail,
      String studentName) {

    // Validate purchase level
    PurchaseLevel purchaseLevel;
    try {
      purchaseLevel = PurchaseLevel.valueOf(level);
    } catch (IllegalArgumentException e) {
      return "The level must be Mandatory, Recommended, or Optional.";
    }

    // Validate parent existence
    Parent parent = (Parent) User.getWithEmail(parentEmail);
    if (parent == null) {
      return "The parent does not exist.";
    }

    // Validate student existence and association with the parent
    Student student = Student.getWithName(studentName);
    if (student == null) {
      return "The student does not exist.";
    }
    if (!parent.getStudents().contains(student)) {
      return "The student is not associated with the parent.";
    }

    // Validate order number (greater than 0)
    if (number <= 0) {
      return "The number must be greater than 0.";
    }

    // Validate order number uniqueness
    CoolSupplies coolSupplies = parent.getCoolSupplies();
    List<Order> orders = coolSupplies.getOrders();
    for (Order order : orders) {
      if (order.getNumber() == number) {
        return "The number must be unique.";
      }
    }

    // Create and add the new order
    Order newOrder = new Order(number, date, purchaseLevel, parent, student, coolSupplies);
    coolSupplies.addOrder(newOrder);
    parent.addOrder(newOrder);
    student.addOrder(newOrder);

    return "The order " + number + " with date " + date + ", level " + level + ", parent email " + parentEmail + ", and student name " + studentName + " has been successfully added.";
  }

}