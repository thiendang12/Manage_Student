/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.Menu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.Student;

/**
 *
 * @author ADMIN
 */
public class ManageStudentController {

    private ArrayList<Student> students = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean exitProgram = false;

        while (!exitProgram) {
            Menu.displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createStudent();
                    break;
                case 2:
                    findAndSortStudents();
                    break;
                case 3:
                    updateOrDeleteStudent();
                    break;
                case 4:
                    generateReport();
                    break;
                case 5:
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Exiting program. Goodbye!");
        scanner.close();
    }

    private void createStudent() {
        do {
            System.out.print("Enter student ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter full name: ");
            String fullName = scanner.nextLine();

            System.out.print("Enter semester: ");
            int semester = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter course name (Java/.Net/C/C++): ");
            String courseName = scanner.nextLine();

            Student student = new Student(id, fullName, semester, courseName);
            students.add(student);

            System.out.println("Student added successfully.");

            if (students.size() >= 1) {
                System.out.print("Do you want to continue (Y/N)? ");
                String continueChoice = scanner.next();
                if (!continueChoice.equalsIgnoreCase("Y")) {
                    break;
                }
            }
        } while (true);
    }

    private void findAndSortStudents() {
        System.out.print("Enter a part of student name to search: ");
        String searchName = scanner.nextLine();

        ArrayList<Student> searchResults = new ArrayList<>();
        for (Student student : students) {
            if (student.getFullName().toLowerCase().contains(searchName.toLowerCase())) {
                searchResults.add(student);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No matching students found.");
        } else {
            Collections.sort(searchResults, Comparator.comparing(Student::getFullName));
            for (Student student : searchResults) {
                System.out.println("Student name: " + student.getFullName()
                        + ", Semester: " + student.getSemester()
                        + ", Course: " + student.getCourseName());
            }
        }
    }

private void updateOrDeleteStudent() {
    System.out.print("Enter student ID to update/delete: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Student studentToUpdate = null;
    for (Student student : students) {
        if (student.getId() == id) {
            studentToUpdate = student;
            break;
        }
    }

    if (studentToUpdate == null) {
        System.out.println("Student with ID " + id + " not found.");
        return;
    }

    System.out.print("Do you want to update (U) or delete (D) this student? ");
    String choice = scanner.next();
    if (choice.equalsIgnoreCase("U")) {
        System.out.print("Enter new full name: ");
        String newFullName = scanner.next();
        scanner.nextLine();

        System.out.print("Enter new semester: ");
        int newSemester = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new course name: ");
        String newCourseName = scanner.nextLine();

        studentToUpdate.setFullName(newFullName);
        studentToUpdate.setSemester(newSemester);
        studentToUpdate.setCourseName(newCourseName);

        System.out.println("Student updated successfully.");
    } else if (choice.equalsIgnoreCase("D")) {
        System.out.print("Are you sure to delete this student (Y/N)? ");
        String confirmChoice = scanner.next();
        if (confirmChoice.equalsIgnoreCase("Y")) {
            students.remove(studentToUpdate);
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    } else {
        System.out.println("Invalid choice.");
    }
}


private void generateReport() {
    System.out.println("Full name | Course | Total of Course");
    HashMap<String, Integer> courseCounts = new HashMap<>();

    for (Student student : students) {
        String fullName = student.getFullName();
        String courseName = student.getCourseName();
        String key = fullName + " | " + courseName;
        
        if (courseCounts.containsKey(key)) {
            // Nếu đã tồn tại, tăng giá trị lên 1
            int count = courseCounts.get(key);
            courseCounts.put(key, count + 1);
        } else {
            // Nếu chưa tồn tại, thêm key mới vào courseCounts với giá trị là 1
            courseCounts.put(key, 1);
        }
    }

    for (Map.Entry<String, Integer> entry : courseCounts.entrySet()) {
        String key = entry.getKey();
        int totalCourses = entry.getValue();
        System.out.println(key + " | " + totalCourses);
    }
}

}
