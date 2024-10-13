package ca.mcgill.ecse.coolsupplies.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;

public class CoolSuppliesFeatureSet6Controller {

  // private static CoolSupplies coolSupplies = new CoolSupplies();

  private static Map<String, List<TOStudent>> parentStudentsMap = new HashMap<>();
  private static Map<Integer, Object> orderMap = new HashMap<>();

  public static String addStudentToParent(String studentName, String parentEmail) {
    TOStudent student = CoolSuppliesFeatureSet2Controller.getStudent(studentName);

    // Unsuccessfully add a student that does not exist to a parent in the system
    if (student == null) {
      throw new IllegalArgumentException("The student does not exist.");
    }

    List<TOStudent> students = parentStudentsMap.getOrDefault(parentEmail, new ArrayList<>());
    // Successfully add a student to a parent in the system
    if (!students.contains(student)) {
      students.add(student);
      parentStudentsMap.put(parentEmail, students);
      return "Student " + studentName + " added to parent " + parentEmail;
    } else {
      return "Student " + studentName + " is already associated with parent " + parentEmail;
    }
  }

  public static String deleteStudentFromParent(String studentName, String parentEmail) {
    TOStudent student = CoolSuppliesFeatureSet2Controller.getStudent(studentName);

    if (!parentStudentsMap.containsKey(parentEmail)) {
      throw new IllegalArgumentException("The parent does not exist.");
    }
    List<TOStudent> students = parentStudentsMap.get(parentEmail);


    if (students != null && students.contains(student)) {
      students.remove(student);
      parentStudentsMap.put(parentEmail, students);
      return "Student " + studentName + " removed from parent " + parentEmail;
    } else {
      throw new IllegalArgumentException("The student does not exist.");
    }
  }

  public static TOStudent getStudentOfParent(String studentName, String parentEmail) {
    TOStudent student = CoolSuppliesFeatureSet2Controller.getStudent(studentName);

    List<TOStudent> students = parentStudentsMap.get(parentEmail);
    
    if (students != null && students.contains(student)) {
      return student;
    } else {
      return null;
    }
  }

  // returns all students of a parent
  public static List<TOStudent> getStudentsOfParent(String parentEmail) {
    List<TOStudent> students = parentStudentsMap.get(parentEmail);
    return students;
  }

  public static String startOrder(int number, Date date, String level, String parentEmail,
      String studentName) {
    
    if (number <= 0) {
        throw new IllegalArgumentException("The number must be greater than 0.");
    }

    List<String> validLevels = List.of("Mandatory", "Recommended", "Optional");
    if (!validLevels.contains(level)) {
        throw new IllegalArgumentException("The level must be Mandatory, Recommended, or Optional.");
    }

    if (!parentStudentsMap.containsKey(parentEmail)) {
        throw new IllegalArgumentException("The parent does not exist.");
    }

    List<TOStudent> students = parentStudentsMap.get(parentEmail);
    TOStudent student = CoolSuppliesFeatureSet2Controller.getStudent(studentName);
    if (student == null || !students.contains(student)) {
        throw new IllegalArgumentException("The student does not exist or is not associated with the parent.");
    }

    if (orderMap.containsKey(number) && (int) orderMap.get(number) == number) {
        throw new IllegalArgumentException("The number must be unique.");
    }

    Map<String, Object> orderDetails = new HashMap<>();
    orderDetails.put("date", date);
    orderDetails.put("level", level);
    orderDetails.put("parentEmail", parentEmail);
    orderDetails.put("studentName", studentName);

    orderMap.put(number, orderDetails);

    return "the order " + number + " with date " + date + ", level " + level + ", parent email " + parentEmail + ", and student name " + studentName + " shall exist in the system";
  }

}
