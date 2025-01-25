package org.example;

import java.util.*;

public class StudentRepository {
    List<Student> studentList = new ArrayList<>();

    public StudentRepository() {
    }

    /**
     * This method add a Student to the repository.
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param gender
     * @param id
     * @throws NameException
     * @throws DateOfBirthException
     * @throws GenderException
     * @throws IdException
     * @throws AgeIsNotANumberException
     */
    public void addStudent(
            String firstName,
            String lastName,
            String dateOfBirth,
            String gender,
            String id
    ) throws NameException, DateOfBirthException, GenderException, IdException, AgeIsNotANumberException {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new NameException();
        }
        int convertedDateOfBirth;
        try {
            convertedDateOfBirth = Integer.parseInt(dateOfBirth);
        } catch (NumberFormatException e) {
            throw new AgeIsNotANumberException();
        }
        int currentYear = new GregorianCalendar().get(Calendar.YEAR);
        if (convertedDateOfBirth < 1900 || convertedDateOfBirth > currentYear - 18) {
            throw new DateOfBirthException();
        }
        if (!isGenderValid(gender)) {
            throw new GenderException();
        }
        if (id.isEmpty()) {
            throw new IdException();
        }
        studentList.add(new Student(firstName, lastName, convertedDateOfBirth, gender, id));
    }

    /**
     * This method verify the gender validity.
     * @param gender
     * @return
     */
    private boolean isGenderValid(String gender) {
        String lowercaseGender = gender.toLowerCase();
        if (lowercaseGender.equals("m") || lowercaseGender.equals("f") || lowercaseGender.equals("male") || lowercaseGender.equals("female")) {
            return true;
        }
        return false;
    }

    /**
     * This method delete a student from repository.
     * @param id
     * @throws IdException
     * @throws StudentDoesNotExistException
     */
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

    /**
     * This method retrieve a list of students by age.
     * @param age
     * @return
     * @throws AgeIsNotANumberException
     * @throws AgeIsNegativeException
     */
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

    /**
     * This method list the students ordered by name.
     * @param orderBy
     * @throws OrderByException
     */
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
        for (Student student : studentList) {
            System.out.println(student);
        }

    }
}
