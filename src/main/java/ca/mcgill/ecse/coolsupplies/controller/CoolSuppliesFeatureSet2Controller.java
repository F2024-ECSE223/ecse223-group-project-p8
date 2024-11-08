package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;


/**
 * This class provides methods to add, update, delete, and retrieve students from the system.
 *
 * @author Zhengxuan Zhao
 */
public class CoolSuppliesFeatureSet2Controller {

  private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

  /**
   * Adds a student with the given name and grade level to the system.
   *
   * @param name          The name of the student.
   * @param gradeLevel    The grade level of the student.
   * @return              A message indicating whether the student was successfully added.
   */
  public static String addStudent(String name, String gradeLevel) {
    if(name.isEmpty()){
      return "The name must not be empty.";
    }
    if(Student.hasWithName(name)){
      return "The name must be unique.";
    }
    if(!Grade.hasWithLevel(gradeLevel)){
      return "The grade does not exist.";
    }
    Grade grade = Grade.getWithLevel(gradeLevel);
    Student student = new Student(name, coolSupplies, grade);
    coolSupplies.addStudent(student);
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "The student has been added to the system.";
  }

  /**
   * Updates the information of an existing student, changing their name and grade level.
   *
   * @param name          The current name of the student.
   * @param newName       The new name of the student.
   * @param newGradeLevel The new grade level of the student.
   * @return              A message indicating whether the student was successfully updated.
   */
  public static String updateStudent(String name, String newName, String newGradeLevel) {
    if(newName.isEmpty()){
      return "The name must not be empty.";
    }
    //check if there is a student entity with the newName
    if(Student.hasWithName(newName)){
      return "The name must be unique.";
    }

    Grade grade;
    //check if there is an instance with current gradeLevel.
    if(!Grade.hasWithLevel(newGradeLevel)) {
      return "The grade does not exist.";
    }else{
      grade = Grade.getWithLevel(newGradeLevel);
    }

    Student student;
    if(!Student.hasWithName(name)){
      return "The student does not exist.";
    }else{
      //get the student from database
      student = Student.getWithName(name);
    }
    student.setName(newName);
    student.setGrade(grade);
    try {
      CoolSuppliesPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "Student has been updated.";
  }

  /**
   * Deletes a student from the system.
   *
   * @param name      The name of the student to be deleted.
   * @return          A message indicating whether the student was successfully deleted.
   */
  public static String deleteStudent(String name) {
    if (Student.hasWithName(name)) {
      Student student = Student.getWithName(name);
      student.delete();
      try {
        CoolSuppliesPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
      return "The student has been deleted.";
    }
    return "The student does not exist.";
  }

  /**
   * Retrieves information about a specific student.
   *
   * @param name      The name of the student to retrieve.
   * @return          A Student transfer object containing the student's name and grade level,
   *                  or NULL if the student is not found.
   */
  public static TOStudent getStudent(String name) {
    if (Student.hasWithName(name)) {
      Student student = Student.getWithName(name);
      return new TOStudent(student.getName(), student.getGrade().getLevel());
    }
    return null;
  }

  /**
   * Retrieves a list of all students in the system.
   *
   * @return          A list of Student transfer objects containing the names and grade levels of all students.
   */
  public static List<TOStudent> getStudents() {
    List<TOStudent> toStudents = new ArrayList<>();
    List<Student> students = coolSupplies.getStudents();
    for (Student s : students) {
      toStudents.add(new TOStudent(s.getName(), s.getGrade().getLevel()));
    }
    return toStudents;
  }
}
