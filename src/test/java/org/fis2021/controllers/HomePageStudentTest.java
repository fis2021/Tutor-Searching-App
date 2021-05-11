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
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class HomePageStudentTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StudentService.initStudent();
        TutorService.initTutor();
        LessonService.initLesson();
        TutorService.addTutor("Vlad", "vlad.vlad", "abcd", "FIS", "CTI");
        TutorService.addTutor("Alexandra", "alexandra.alexandra", "abcd", "MAC", "CTI");
        StudentService.addStudent("Ana", "AC", "CTI", "LM123", "ana.ana", "abcd");
        LessonService.addLesson("vlad.vlad", "FIs", "11 05 2021", "12:00", "14:00", true, "ana.ana", "accepted");
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
    void testHomePage(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");

        robot.clickOn("#loginButton");

        robot.clickOn("#tutorlistButton");
        robot.clickOn("#homeButton");

        robot.clickOn("#requestsButton");
        robot.clickOn("#homeButton");

        robot.clickOn("#calendarButton");
        robot.clickOn("#homeButton");

        robot.clickOn("#accountButton");
        robot.clickOn("#homeButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }
}