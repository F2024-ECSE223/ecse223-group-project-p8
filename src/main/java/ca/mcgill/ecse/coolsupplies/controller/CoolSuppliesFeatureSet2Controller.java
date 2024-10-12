package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet2Controller {

    private static final CoolSupplies coolSupplies = CoolSuppliesApplication.getCoolSupplies();

    public static String addStudent(String name, String gradeLevel) {
        try {
            Grade grade;
            try {
                //new Grade is created if there is no instance with current gradeLevel.
                grade = new Grade(gradeLevel, coolSupplies);
            } catch (Exception e) {
                grade = Grade.getWithLevel(gradeLevel);
            }
            //new Student is created if there is no instance with current name, coolSupplies and grade.
            Student student = new Student(name, coolSupplies, grade);
            coolSupplies.addStudent(student);
        } catch (Exception e) {
            //return Student Add Exception message
            return e.getMessage();
        }
        return "Student " + name + " has been successfully added to the list.";
    }

    public static String updateStudent(String name, String newName, String newGradeLevel) {
        //get the student list from database
        if (Student.hasWithName(name)) {
            Student student = Student.getWithName(name);
            //check if the newName has been stored in the list, otherwise set the newName to the student
            if (!student.setName(newName)) {
                return "Cannot create due to duplicate name.";
            }
            Grade grade;
            try {
                //check if the new Grade is created if there is no instance with current gradeLevel.
                grade = new Grade(newGradeLevel, coolSupplies);
            } catch (Exception e) {
                grade = Grade.getWithLevel(newGradeLevel);
            }
            student.setGrade(grade);
            return "Student has been updated!.";
        }
        return "Student " + name + " cannot be found in the list.";
    }

    public static String deleteStudent(String name) {
        if (Student.hasWithName(name)) {
            Student student = Student.getWithName(name);
            if (coolSupplies.removeStudent(student))
                return "Student has been deleted.";
            else
                return "Student cannot be deleted.";
        }
        return "Student " + name + " cannot be found in the list.";
    }

    public static TOStudent getStudent(String name) {
        if (Student.hasWithName(name)) {
            Student student = Student.getWithName(name);
            return new TOStudent(student.getName(), student.getGrade().getLevel());
        }
        return null;
    }

    // returns all students
    public static List<TOStudent> getStudents() {
        List<TOStudent> toStudents = new ArrayList<>();
        List<Student> students = coolSupplies.getStudents();
        for (Student s : students) {
            toStudents.add(new TOStudent(s.getName(), s.getGrade().getLevel()));
        }
        return toStudents;
    }
}
