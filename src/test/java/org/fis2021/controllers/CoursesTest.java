package org.fis2021.controllers;

import com.calendarfx.view.TimeField;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fis2021.App.loadFXML;

@ExtendWith(ApplicationExtension.class)
class CoursesTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        TutorService.initTutor();
        LessonService.initLesson();
        TutorService.addTutor("Vlad", "vlad.vlad", "vlad", "FIS", "CTI-RO");
        LessonService.addLesson("Vlad", "FIS", "11 05 2021", "12:00", "14:00", true, "", "", "");
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
    void testCoursePage(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");

        robot.clickOn("#coursesButton");
        robot.clickOn("#homeButton");

        robot.clickOn("#coursesButton");
        robot.clickOn("#studentListButton");

        robot.clickOn("#coursesButton");
        robot.clickOn("#requestsButton");

        robot.clickOn("#coursesButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#coursesButton");
        robot.clickOn("#coursesButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }

    @Test
    void testAddCourse(FxRobot robot){
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");

        robot.clickOn("#coursesButton");

        robot.clickOn("#courseName");
        robot.write("TP");

        DatePicker datePicker = robot.lookup("#courseDate").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("22 05 2021", formatter);
        datePicker.setValue(localDate);

        TimeField startTimeField = robot.lookup("#courseStart").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);

        TimeField endTimeField = robot.lookup("#courseEnd").query();
        LocalTime endLocalTime = LocalTime.parse("13:50");
        endTimeField.setValue(endLocalTime);

        robot.clickOn("#weekly");

        robot.clickOn("#courseAdd");

        assertThat(robot.lookup("#courseAdded").queryLabeled().getText()).isEqualTo("Course added successfully!");
    }

    @Test
    void testNoNameCourse(FxRobot robot){
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");

        robot.clickOn("#coursesButton");

        DatePicker datePicker = robot.lookup("#courseDate").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("22 05 2021", formatter);
        datePicker.setValue(localDate);

        TimeField startTimeField = robot.lookup("#courseStart").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);

        TimeField endTimeField = robot.lookup("#courseEnd").query();
        LocalTime endLocalTime = LocalTime.parse("13:50");
        endTimeField.setValue(endLocalTime);

        robot.clickOn("#weekly");

        robot.clickOn("#courseAdd");

        assertThat(robot.lookup("#courseAdded").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testNoDateCourse(FxRobot robot){
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");

        robot.clickOn("#coursesButton");

        robot.clickOn("#courseName");
        robot.write("TP");

        TimeField startTimeField = robot.lookup("#courseStart").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);

        TimeField endTimeField = robot.lookup("#courseEnd").query();
        LocalTime endLocalTime = LocalTime.parse("13:50");
        endTimeField.setValue(endLocalTime);

        robot.clickOn("#weekly");

        robot.clickOn("#courseAdd");

        assertThat(robot.lookup("#courseAdded").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testNoWeeklyCourse(FxRobot robot){
        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");

        robot.clickOn("#coursesButton");

        robot.clickOn("#courseName");
        robot.write("TP");

        DatePicker datePicker = robot.lookup("#courseDate").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("22 05 2021", formatter);
        datePicker.setValue(localDate);

        TimeField startTimeField = robot.lookup("#courseStart").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);

        TimeField endTimeField = robot.lookup("#courseEnd").query();
        LocalTime endLocalTime = LocalTime.parse("13:50");
        endTimeField.setValue(endLocalTime);

        robot.clickOn("#courseAdd");

        assertThat(robot.lookup("#courseAdded").queryLabeled().getText()).isEqualTo("Course added successfully!");
    }
}
