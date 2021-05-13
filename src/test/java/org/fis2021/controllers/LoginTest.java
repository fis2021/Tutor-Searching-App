package org.fis2021.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.FileSystemService;
import org.fis2021.services.StudentService;
import org.fis2021.services.TutorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class LoginTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StudentService.initStudent();
        TutorService.initTutor();
        TutorService.addTutor("Vlad", "vlad.vlad", "abcd", "FIS", "CTI");
        StudentService.addStudent("Ana", "AC", "CTI", "LM123", "ana.ana", "abcd");
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
    void testNoUsername(FxRobot robot) {
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please type in your username!");
    }

    @Test
    void testNoPassword(FxRobot robot) {
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#username");
        robot.write("aaa");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please type in your password!");
    }

    @Test
    void testNoRole(FxRobot robot) {
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please choose your role!");
    }

    @Test
    void testInvalidCredentialsStudent(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcde");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Invalid credentials!");
    }

    @Test
    void testNoRegisterStudent(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana1");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("The username ana.ana1 is not registered!");
    }

    @Test
    void testInvalidCredentialsTutor(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("abcde");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Invalid credentials!");
    }

    @Test
    void  testNoRegisterTutor(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad1");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("The username vlad.vlad1 is not registered!");
    }

    @Test
    void testCorrectStudentIsLoggedIn(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#studentHomePage").queryLabeled().getText()).isEqualTo("      My Classes");
    }

    @Test
    void testCorrectTutorIsLoggedIn(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#tutorHomePage").queryLabeled().getText()).isEqualTo("            My Students");
    }
}