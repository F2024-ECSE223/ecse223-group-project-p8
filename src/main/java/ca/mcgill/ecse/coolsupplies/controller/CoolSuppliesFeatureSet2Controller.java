package ca.mcgill.ecse.coolsupplies.controller;

import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet2Controller {

  private static List<TOStudent> students = new ArrayList<>();

  public static String addStudent(String name, String gradeLevel) {
    for(TOStudent student: students){
      if(student.getName().equals(name)){
        return "Student "+ name + " has been already added to the list.";
      }
    }
    students.add(new TOStudent(name,gradeLevel));
    return "Student "+ name + " has been successfully added to the list.";
  }

  public static String updateStudent(String name, String newName, String newGradeLevel) {
    for(TOStudent student: students){
      if(student.getName().equals(name)){
        students.remove(student);
        students.add(new TOStudent(newName,newGradeLevel));
        return "Student "+ name + " has been successfully removed from the list.";
      }
    }
    return "Student "+name+" cannot be found in the list.";
  }

  public static String deleteStudent(String name) {
    for(TOStudent student: students){
      if(student.getName().equals(name)){
        students.remove(student);
        return "Student "+ name + " has been successfully removed from the list.";
      }
    }
    return "Student "+name+" cannot be found in the list.";
  }

  public static TOStudent getStudent(String name) {
    for(TOStudent student: students){
      if(student.getName().equals(name)){
        return student;
      }
    }
    return null;
  }

  // returns all students
  public static List<TOStudent> getStudents() {
    return students;
  }

}
