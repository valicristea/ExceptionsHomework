package org.example;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        StudentRepository repository = new StudentRepository();
        Scanner scanner = new Scanner(System.in);

        boolean shouldPrintMenu = true;
        while (shouldPrintMenu) {
            System.out.println("1. Add student");
            System.out.println("2. Delete student");
            System.out.println("3. Retrieve all students");
            System.out.println("4. List students");
            System.out.println("0. Exit");

            System.out.print("Enter option: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter second name: ");
                    String secondName = scanner.nextLine();
                    System.out.print("Enter date of birth: ");
                    String dateOfBirth = scanner.nextLine();
                    System.out.print("Enter gender: ");
                    String gender = scanner.nextLine();
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();

                    try {
                        repository.addStudent(firstName, secondName, dateOfBirth, gender, id);
                    } catch (StudentException exception) {
                        switch (exception) {
                            case NameException e -> System.out.println("Name should not be empty.");
                            case DateOfBirthException e ->
                                    System.out.println("Date of birth should be between 1900 and current year -18.");
                            case GenderException e -> System.out.println("Gender is not valid.");
                            case IdException e -> System.out.println("ID should not be empty.");
                            default -> System.out.println("Date of birth is not a number.");
                        }
                        continue;
                    }
                    System.out.println("Student added.");
                }
                case "2": {
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();

                    try {
                        repository.deleteStudent(id);
                    } catch (StudentException exception) {
                        if (exception instanceof IdException) {
                            System.out.println("Id should not be empty.");
                        } else {
                            System.out.println("Student does not exist.");
                        }
                        continue;
                    }
                    System.out.println("Student deleted.");

                }
                case "3": {
                    System.out.print("Enter age: ");
                    String age = scanner.nextLine();

                    List<Student> students;

                    try {
                        students = repository.retrieveStudentList(age);
                    } catch (StudentException exception) {
                        if (exception instanceof AgeIsNotANumberException) {
                            System.out.println("Age is not a number.");
                        } else {
                            System.out.println("Age should not be negative number.");
                        }
                        continue;
                    }
                    System.out.println("Students: ");

                    for (Student student: students){
                        System.out.println(student);
                    }
                }
                case "4": {
                    System.out.print("Enter order by name or birthdate: ");
                    String orderBy = scanner.nextLine();

                    try {
                        repository.listStudents(orderBy);
                    } catch (OrderByException e) {
                        System.out.println ("Order by should not be empty.");
                    }
                }
                case "0":
                    shouldPrintMenu = false;
                default:
            }
        }
    }
}
