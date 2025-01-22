package org.example;

import java.util.*;

public class StudentRepository {
    List<Student> studentList = new ArrayList<>();

    public StudentRepository() {
    }

    public void addStudent(
            String firstName,
            String lastName,
            int dateOfBirth,
            String gender,
            String id
    ) throws NameException, DateOfBirthException, GenderException, IdException {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new NameException();
        }
        int currentYear = new GregorianCalendar().get(Calendar.YEAR);
        if (dateOfBirth < 1900 || dateOfBirth > currentYear - 18) {
            throw new DateOfBirthException();
        }
        if (!isGenderValid(gender)) {
            throw new GenderException();
        }
        if (id.isEmpty()) {
            throw new IdException();
        }
        studentList.add(new Student(firstName, lastName, dateOfBirth, gender, id));
    }

    private boolean isGenderValid(String gender) {
        String lowercaseGender = gender.toLowerCase();
        if (lowercaseGender.equals("m") || lowercaseGender.equals("f") || lowercaseGender.equals("male") || lowercaseGender.equals("female")) {
            return true;
        }
        return false;
    }

    public void deleteStudent(String id)
            throws IdException, StudentDoesNotExistException {
        if (id.isEmpty()) {
            throw new IdException();
        }
        boolean didRemove = studentList.removeIf(student -> Objects.equals(student.id, id));
        if (!didRemove) {
            throw new StudentDoesNotExistException();
        }
    }

    public List<Student> retrieveStudentList(String age)
            throws AgeIsNotANumberException, AgeIsNegativeException {
        int convertedAge;
        try {
            convertedAge = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            throw new AgeIsNotANumberException();
        }

        if (convertedAge < 0) {
            throw new AgeIsNegativeException();
        }
        int currentYear = new GregorianCalendar().get(Calendar.YEAR);
        return studentList.stream().filter(student -> currentYear - student.dateOfBirth == convertedAge).toList();

    }

    public void listStudents(String orderBy)
            throws OrderByException {
        if (orderBy.isEmpty()) {
            throw new OrderByException();
        }
        if (orderBy == "name") {
            studentList.sort(Comparator.comparing(student -> student.lastName));
        } else {
            studentList.sort(Comparator.comparing(student -> student.dateOfBirth));
        }
        for (int i = 0; i < studentList.size(); i++) {
            System.out.println(studentList.get(i));
        }

    }
}
