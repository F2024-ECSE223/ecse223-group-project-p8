package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

import java.util.ArrayList;
import java.util.List;

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
        try {
            Grade grade;
            if(!Grade.hasWithLevel(gradeLevel)) {
                //check if the new Grade is created if there is no instance with current gradeLevel.
                grade = new Grade(gradeLevel, coolSupplies);
            }else{
                grade = Grade.getWithLevel(gradeLevel);
            }
            //new Student is created if there is no instance with current name, coolSupplies and grade.
            Student student = new Student(name, coolSupplies, grade);
            coolSupplies.addStudent(student);
            return "Student has been added.";
        } catch (Exception e) {
            //return Student Add Exception message
            return e.getMessage();
        }
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
        //get the student list from database
        if (Student.hasWithName(name)) {
            Student student = Student.getWithName(name);
            //check if the newName has been stored in the list, otherwise set the newName to the student
            if (!student.setName(newName)) {
                return "Cannot create due to duplicate name.";
            }
            Grade grade;
            if(!Grade.hasWithLevel(newGradeLevel)) {
                //check if the new Grade is created if there is no instance with current gradeLevel.
                grade = new Grade(newGradeLevel, coolSupplies);
            }else{
                grade = Grade.getWithLevel(newGradeLevel);
            }
            student.setGrade(grade);
            return "Student has been updated!.";
        }
        return "Student " + name + " cannot be found in the list.";
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
            return "Student has been deleted.";
        }
        return "Student " + name + " cannot be found in the list.";
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
