package org.fis2021.controllers;

import com.calendarfx.view.TimeField;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.fis2021.App.loadFXML;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class TutorRegisterTest {
    @BeforeEach
    void setUp() throws IOException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        TutorService.initTutor();
        LessonService.initLesson();
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
    void testEveryFieldIsCompletedAndTutorIsRegistered(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#nameTutor");
        robot.write("Vlad");
        robot.clickOn("#classTutor");
        robot.write("FIS");
        robot.clickOn("#specTutor");
        robot.write("CTI");
        robot.clickOn("#usernameTutor");
        robot.write("vlad.vlad");
        robot.clickOn("#passwordTutor");
        robot.write("abcd");
        robot.clickOn("#classNameTutor");
        robot.write("Lab FIS");
        DatePicker datePicker = robot.lookup("#datePicker").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("13 05 2021", formatter);
        datePicker.setValue(localDate);
        TimeField startTimeField = robot.lookup("#startTime").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);
        TimeField endTimeField = robot.lookup("#endTime").query();
        LocalTime endLocalTime = LocalTime.parse("14:00");
        endTimeField.setValue(endLocalTime);
        robot.clickOn("#reccBox");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Account created successfully!");
    }

    @Test
    void testAllFieldsAreEmpty(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testNameIsEmpty(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#classTutor");
        robot.write("FIS");
        robot.clickOn("#specTutor");
        robot.write("CTI");
        robot.clickOn("#usernameTutor");
        robot.write("vlad.vlad");
        robot.clickOn("#passwordTutor");
        robot.write("abcd");
        robot.clickOn("#classNameTutor");
        robot.write("Lab FIS");
        DatePicker datePicker = robot.lookup("#datePicker").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("13 05 2021", formatter);
        datePicker.setValue(localDate);
        TimeField startTimeField = robot.lookup("#startTime").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);
        TimeField endTimeField = robot.lookup("#endTime").query();
        LocalTime endLocalTime = LocalTime.parse("14:00");
        endTimeField.setValue(endLocalTime);
        robot.clickOn("#reccBox");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testClassNameIsEmpty(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#nameTutor");
        robot.write("Vlad");
        robot.clickOn("#specTutor");
        robot.write("CTI");
        robot.clickOn("#usernameTutor");
        robot.write("vlad.vlad");
        robot.clickOn("#passwordTutor");
        robot.write("abcd");
        robot.clickOn("#classNameTutor");
        robot.write("Lab FIS");
        DatePicker datePicker = robot.lookup("#datePicker").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("13 05 2021", formatter);
        datePicker.setValue(localDate);
        TimeField startTimeField = robot.lookup("#startTime").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);
        TimeField endTimeField = robot.lookup("#endTime").query();
        LocalTime endLocalTime = LocalTime.parse("14:00");
        endTimeField.setValue(endLocalTime);
        robot.clickOn("#reccBox");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testSpecIsEmpty(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#nameTutor");
        robot.write("Vlad");
        robot.clickOn("#classTutor");
        robot.write("FIS");
        robot.clickOn("#usernameTutor");
        robot.write("vlad.vlad");
        robot.clickOn("#passwordTutor");
        robot.write("abcd");
        robot.clickOn("#classNameTutor");
        robot.write("Lab FIS");
        DatePicker datePicker = robot.lookup("#datePicker").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("13 05 2021", formatter);
        datePicker.setValue(localDate);
        TimeField startTimeField = robot.lookup("#startTime").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);
        TimeField endTimeField = robot.lookup("#endTime").query();
        LocalTime endLocalTime = LocalTime.parse("14:00");
        endTimeField.setValue(endLocalTime);
        robot.clickOn("#reccBox");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testUsernameIsEmpty(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#nameTutor");
        robot.write("Vlad");
        robot.clickOn("#classTutor");
        robot.write("FIS");
        robot.clickOn("#specTutor");
        robot.write("CTI");
        robot.clickOn("#passwordTutor");
        robot.write("abcd");
        robot.clickOn("#classNameTutor");
        robot.write("Lab FIS");
        DatePicker datePicker = robot.lookup("#datePicker").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("13 05 2021", formatter);
        datePicker.setValue(localDate);
        TimeField startTimeField = robot.lookup("#startTime").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);
        TimeField endTimeField = robot.lookup("#endTime").query();
        LocalTime endLocalTime = LocalTime.parse("14:00");
        endTimeField.setValue(endLocalTime);
        robot.clickOn("#reccBox");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testPasswordIsEmpty(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#nameTutor");
        robot.write("Vlad");
        robot.clickOn("#classTutor");
        robot.write("FIS");
        robot.clickOn("#specTutor");
        robot.write("CTI");
        robot.clickOn("#usernameTutor");
        robot.write("vlad.vlad");
        robot.clickOn("#classNameTutor");
        robot.write("Lab FIS");
        DatePicker datePicker = robot.lookup("#datePicker").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("13 05 2021", formatter);
        datePicker.setValue(localDate);
        TimeField startTimeField = robot.lookup("#startTime").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);
        TimeField endTimeField = robot.lookup("#endTime").query();
        LocalTime endLocalTime = LocalTime.parse("14:00");
        endTimeField.setValue(endLocalTime);
        robot.clickOn("#reccBox");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testCourseNameIsEmpty(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#nameTutor");
        robot.write("Vlad");
        robot.clickOn("#classTutor");
        robot.write("FIS");
        robot.clickOn("#specTutor");
        robot.write("CTI");
        robot.clickOn("#usernameTutor");
        robot.write("vlad.vlad");
        robot.clickOn("#passwordTutor");
        robot.write("abcd");
        DatePicker datePicker = robot.lookup("#datePicker").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate localDate = LocalDate.parse("13 05 2021", formatter);
        datePicker.setValue(localDate);
        TimeField startTimeField = robot.lookup("#startTime").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);
        TimeField endTimeField = robot.lookup("#endTime").query();
        LocalTime endLocalTime = LocalTime.parse("14:00");
        endTimeField.setValue(endLocalTime);
        robot.clickOn("#reccBox");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testDatePickerIsEmpty(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#nameTutor");
        robot.write("Vlad");
        robot.clickOn("#classTutor");
        robot.write("FIS");
        robot.clickOn("#specTutor");
        robot.write("CTI");
        robot.clickOn("#usernameTutor");
        robot.write("vlad.vlad");
        robot.clickOn("#passwordTutor");
        robot.write("abcd");
        TimeField startTimeField = robot.lookup("#startTime").query();
        LocalTime startLocalTime = LocalTime.parse("12:00");
        startTimeField.setValue(startLocalTime);
        TimeField endTimeField = robot.lookup("#endTime").query();
        LocalTime endLocalTime = LocalTime.parse("14:00");
        endTimeField.setValue(endLocalTime);
        robot.clickOn("#reccBox");
        robot.clickOn("#registerTutor");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testLoginButtonWorks(FxRobot robot) {
        robot.clickOn("#registerTutor");
        robot.clickOn("#switchToLogin");
        FxAssert.verifyThat("#loginButton", NodeMatchers.isVisible());
    }
}