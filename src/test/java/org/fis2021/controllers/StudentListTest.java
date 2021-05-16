package org.fis2021.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fis2021.App.loadFXML;

@ExtendWith(ApplicationExtension.class)
class StudentListTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StudentService.initStudent();
        TutorService.initTutor();
        LessonService.initLesson();
        TutorService.addTutor("Vlad", "vlad.vlad", "vlad", "FIS", "CTI");
        StudentService.addStudent("Alexandra", "AC", "CTI-RO", "AC123", "ale.ale", "ale");
        StudentService.addStudent("Andrei", "AC", "CTI-EN", "EN123", "andy.andy", "andy");
        StudentService.addStudent("Ana", "AC", "CTI-RO", "LM123", "ana.ana", "ana");
        LessonService.addLesson("Vlad", "Lab FIS", "11 05 2021", "12:00", "14:00", true, "ale.ale","accepted", "");
        LessonService.addLesson("Vlad", "Lab FIS", "11 05 2021", "12:00", "14:00", true,"ana.ana", "declined", "Too many students");
        LessonService.addLesson("Vlad", "Lab FIS", "10 05 2021", "10:00", "12:00",true, "andy.andy", "pending", "");
    }

    @Start
    void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("login"), 1280, 720);
        stage.setTitle("Tutor Searching App - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.getDatabase().close();
    }

    @Test
    void testTutorListButtons(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#requestsButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#calendarButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#accountButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#coursesButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }

    @Test
    void testSearchButton(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#studentSearchField");
        robot.write("al");
        robot.clickOn("#studentSearchButton");

        assertThat(robot.lookup("#studentName").queryLabeled().getText()).isEqualTo("Alexandra");
        assertThat(robot.lookup("#studentFaculty").queryLabeled().getText()).isEqualTo("AC");
        assertThat(robot.lookup("#studentSpecialization").queryLabeled().getText()).isEqualTo("CTI-RO");
    }

    @Test
    void testStudentInfo(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#studentSearchField");
        robot.write("al");
        robot.clickOn("#studentSearchButton");

        robot.clickOn("#studentListHBox");

        assertThat(robot.lookup("#studentInfoName").queryLabeled().getText()).isEqualTo("Alexandra");
        assertThat(robot.lookup("#studentInfoFaculty").queryLabeled().getText()).isEqualTo("AC");
        assertThat(robot.lookup("#studentInfoSpecialization").queryLabeled().getText()).isEqualTo("CTI-RO");
        assertThat(robot.lookup("#studentInfoRegistrationNumber").queryLabeled().getText()).isEqualTo("AC123");
        assertThat(robot.lookup("#studentInfoUsername").queryLabeled().getText()).isEqualTo("ale.ale");
    }

    @Test
    void testStudentNotFound(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#studentSearchField");
        robot.write("anc");
        robot.clickOn("#studentSearchButton");

        assertThat(robot.lookup("#studentNotFound").queryLabeled().getText()).isEqualTo("Student not found");
    }

    @Test
    void testRemoveStudent(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#studentListHBox");
        robot.clickOn("#enableRemove");
        robot.clickOn("#removeText");
        robot.write("Grades under 5.");
        robot.clickOn("#submitButton");
        FxAssert.verifyThat("#successLabel", NodeMatchers.isVisible());
    }

}