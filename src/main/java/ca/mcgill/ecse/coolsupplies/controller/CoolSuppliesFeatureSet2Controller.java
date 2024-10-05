package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

import java.util.List;

public class CoolSuppliesFeatureSet2Controller {

    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    public static String addStudent(String name, String gradeLevel) {
        try {
            Grade grade;
            try {
                grade = new Grade(gradeLevel, coolSupplies);
            } catch (Exception e) {
                grade = Grade.getWithLevel(gradeLevel);
            }
            Student student = new Student(name, coolSupplies, grade);
            coolSupplies.addStudent(student);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Student " + name + " has been successfully added to the list.";
    }

    public static String updateStudent(String name, String newName, String newGradeLevel) {
        return "Student " + name + " cannot be found in the list.";
    }

    public static String deleteStudent(String name) {
        return "Student " + name + " cannot be found in the list.";
    }

    public static TOStudent getStudent(String name) {
        return null;
    }

    // returns all students
    public static List<TOStudent> getStudents() {
        return null;
    }
}
