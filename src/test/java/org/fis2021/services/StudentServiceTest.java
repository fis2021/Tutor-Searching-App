package org.fis2021.services;

import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.models.Student;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class StudentServiceTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StudentService.initStudent();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, there are no students persisted")
    void testDatabaseIsInitializedAndNoStudentIsPersisted() {
        assertThat(StudentService.getAllStudents()).isNotNull();
        assertThat(StudentService.getAllStudents()).isEmpty();
    }

    @Test
    @DisplayName("Student is added to database")
    void testStudentIsAddedToDatabase() throws UsernameAlreadyExistsException {
        StudentService.addStudent("Ana", "AC", "CTI", "123", "ana.ana", "abcd");
        assertThat(StudentService.getAllStudents()).isNotEmpty();
        assertThat(StudentService.getAllStudents().size()).isEqualTo(1);
        Student student = StudentService.getAllStudents().get(0);
        assertThat(student.getNume()).isEqualTo("Ana");
        assertThat(student.getFacultate()).isEqualTo("AC");
        assertThat(student.getSpecializare()).isEqualTo("CTI");
        assertThat(student.getNrMatricol()).isEqualTo("123");
        assertThat(student.getUsername()).isEqualTo("ana.ana");
        assertThat(student.getParola()).isEqualTo(StudentService.encodePassword("ana.ana", "abcd"));
    }

    @Test
    @DisplayName("Two students with the same username cannot be added")
    void testStudentCannotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            StudentService.addStudent("Ana", "AC", "CTI", "123", "ana.ana", "abcd");
            StudentService.addStudent("Ana", "AC", "CTI", "123", "ana.ana", "abcd");
        });
    }

    @Test
    @DisplayName("Student cannot be found")
    void testStudentCannotBeFound() {
        assertThrows(UserNotFoundException.class, () -> {
            StudentService.addStudent("Ana", "AC", "CTI", "123", "ana.ana1", "abcd");
            StudentService.addStudent("Ana", "AC", "CTI", "123", "ana.ana2", "abcd");
            StudentService.getStudent("ana.ana");
        });
    }
}