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

public class CoolSuppliesFeatureSet6Controller {

  private static CoolSupplies coolSupplies = new CoolSupplies();

  // private static Map<String, List<Student>> parentStudentsMap = new HashMap<>();
  // private static Map<Integer, Object> orderMap = new HashMap<>();

  public static String addStudentToParent(String studentName, String parentEmail) {
    Student student = Student.getWithName(studentName);
    Parent parent = (Parent) User.getWithEmail(parentEmail);

    // Unsuccessfully add a student that does not exist to a parent in the system
    if (student == null) {
      throw new IllegalArgumentException("The student does not exist.");
    }

    // Successfully add a student to a parent in the system
    Boolean isAdded = parent.addStudent(student);
    if (isAdded) {
      return "Student " + studentName + " added to parent " + parentEmail;
    } else {
      return "Student " + studentName + " is already associated with parent " + parentEmail;
    }
  }

  public static String deleteStudentFromParent(String studentName, String parentEmail) {
    Student student = Student.getWithName(studentName);
    if (student == null) {
      throw new IllegalArgumentException("The student does not exist.");
    }

    User user = User.getWithEmail(parentEmail);

    // Unsuccessfully remove a student from the students of a parent that does not exist in the system
    if (user == null || !(user instanceof Parent)) {
        throw new IllegalArgumentException("The parent does not exist.");
    }

    Parent parent = (Parent) user;

    Boolean isRemoved = parent.removeStudent(student);
    if (isRemoved) {
      // Successfully remove a student from the students of a parent in the system
      return "Student " + studentName + " removed from parent " + parentEmail;
    } else {
      // Unsuccessfully remove a student that does not exist in the students of a parent in the system
      throw new IllegalArgumentException("The student does not exist.");
    }
  }

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

  // returns all students of a parent
  public static List<TOStudent> getStudentsOfParent(String parentEmail) {
    Parent parent = (Parent) User.getWithEmail(parentEmail);
    // Unsuccessfully get all students of a parent that does not exist in the system
    if (parent == null) {
      return null;
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

  public static String startOrder(int number, Date date, String level, String parentEmail,
      String studentName) {

    
    PurchaseLevel purchaseLevel;
    try {
      purchaseLevel = PurchaseLevel.valueOf(level);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("The level must be Mandatory, Recommended, or Optional.");
    }

    Student student = Student.getWithName(studentName);
    Parent parent = (Parent) User.getWithEmail(parentEmail);
    if (parent == null) {
      throw new IllegalArgumentException("The parent does not exist.");
    }

    List<Student> students = parent.getStudents();
    if (student == null || !students.contains(student)) {
      throw new IllegalArgumentException("The student does not exist.");
    }

    Order newOrder = new Order(number, date, purchaseLevel, parent, student, coolSupplies);
    List<Order> orders = coolSupplies.getOrders();

    if (number <= 0) {
        throw new IllegalArgumentException("The number must be greater than 0.");
    }

    for (Order order : orders) {
      if (order.getNumber() == number) {
          throw new IllegalArgumentException("The number must be unique.");
      }
    }

    coolSupplies.addOrder(newOrder);

    return "the order " + number + " with date " + date + ", level " + level + ", parent email " + parentEmail + ", and student name " + studentName + " shall exist in the system";
  }

}
