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
import java.util.List;

import static org.fis2021.App.loadFXML;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class CalendarTutorTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        TutorService.initTutor();
        LessonService.initLesson();
        TutorService.addTutor("Vlad", "vlad.vlad", "vlad", "FIS", "CTI-RO");
        LessonService.addLesson("Vlad", "Lab FIS", "12 05 2021", "10:00", "12:00", true, "", "", "");
        LessonService.addLesson("Vlad", "Lab OC", "12 05 2021", "12:00", "14:00", true, "", "", "");
        LessonService.addLesson("Andreea", "Lab BD", "12 05 2021", "14:00", "16:00", true, "", "", "");
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
    void testButtonsCalendar(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");

        robot.clickOn("#calendarButton");

        robot.clickOn("#requestsButton");
        robot.clickOn("#calendarButton");

        robot.clickOn("Week");
        robot.clickOn("Month");
        robot.clickOn("Year");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }

    @Test
    void testCourseIsOnCalendar(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");

        robot.clickOn("#calendarButton");

        FxAssert.verifyThat("Lab FIS", NodeMatchers.isVisible());
        FxAssert.verifyThat("Lab OC", NodeMatchers.isVisible());
    }

    @Test
    void testCourseIsNotOnCalendar(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");

        robot.clickOn("#calendarButton");
        List sources = CalendarTutorController.getCalendar().findEntries("Lab BD");
        assertThat(sources.size()).isEqualTo(0);

    }

}