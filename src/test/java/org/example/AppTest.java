package org.example;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class AppTest {
    StudentRepository repository;

    /**
     * Sets up the repository before each test.
     */
    @Before
    public void setup() {
        repository = new StudentRepository();
    }

    /**
     * This tests the add student method.
     */
    @Test
    public void testAddStudent() {
        Assert.assertThrows(NameException.class, () -> {
            repository.addStudent("", "", "", "", "");
        });
        Assert.assertThrows(DateOfBirthException.class, () -> {
            repository.addStudent("Gheorghe", "Ion", "1875", "", "");
        });
        Assert.assertThrows(DateOfBirthException.class, () -> {
            repository.addStudent("Gheorghe", "Ion", "2020", "", "");
        });
        Assert.assertThrows(AgeIsNotANumberException.class, () -> {
            repository.addStudent("Gheorghe", "Ion", "jshvdbcjsvbdj", "", "");
        });
        Assert.assertThrows(GenderException.class, () -> {
            repository.addStudent("Gheorghe", "Ion", "1990", "s", "");
        });
        Assert.assertThrows(IdException.class, () -> {
            repository.addStudent("Gheorghe", "Ion", "1990", "M", "");
        });
        Assert.assertThrows(IdException.class, () -> {
            repository.addStudent("Gheorghe", "Ion", "1990", "M", "");
        });
        try {
            repository.addStudent("Gheorghe", "Ion", "1990", "M", "1234");
        } catch (StudentException exception) {
            fail();
        }
    }

    /**
     * This tests delete student method.
     */
    @Test
    public void testDeleteStudent() {
        try {
            repository.addStudent("Gheorghe", "Ion", "1990", "M", "1234");
        } catch (StudentException exception) {
            fail();
        }
        Assert.assertThrows(IdException.class, () -> {
            repository.deleteStudent("");
        });
        Assert.assertThrows(StudentDoesNotExistException.class, () -> {
            repository.deleteStudent("12");
        });
        try {
            repository.deleteStudent("1234");
        } catch (StudentException exception) {
            fail();
        }
    }

    /**
     * This tests retrieve student list method.
     */
    @Test
    public void testRetrieveStudentList() {
        try {
            repository.addStudent("Gheorghe", "Ion", "1990", "M", "1234");
            repository.addStudent("Gheorghe", "Ion", "1980", "M", "1234");
        } catch (StudentException exception) {
            fail();
        }
        Assert.assertThrows(AgeIsNotANumberException.class, () -> {
            repository.retrieveStudentList("nr");
        });
        Assert.assertThrows(AgeIsNegativeException.class, () -> {
            repository.retrieveStudentList("-22");
        });
        try {
            List<Student> list = repository.retrieveStudentList("35");
            Assert.assertEquals(1, list.size());
        } catch (StudentException exception) {
            fail();
        }
    }

    /**
     * This tests list students method.
     */
    @Test
    public void testListStudents() {
        Student student1 = new Student("Gheorghe", "Ion", 1990, "M", "1234");
        Student student2 = new Student ("Mircea", "Pop", 1980, "M", "2354");
        try {
            repository.addStudent("Gheorghe", "Ion", "1990", "M", "1234");
            repository.addStudent("Mircea", "Pop", "1980", "M", "2354");
        } catch (StudentException exception) {
            fail();
        }
        Assert.assertThrows(OrderByException.class, () -> {
            repository.listStudents("");
        });

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        try {
            repository.listStudents("name");
            Assert.assertEquals(
                   student1 + "\r\n" + student2 + "\r\n",
                   outputStream.toString()
            );
            outputStream.reset();
            repository.listStudents("birthdate");
            Assert.assertEquals(
                    student2 + "\r\n" + student1 + "\r\n",
                    outputStream.toString()
            );

        } catch (StudentException exception) {
            fail();
        } finally {
            System.setOut(originalOut);
        }
    }
}
