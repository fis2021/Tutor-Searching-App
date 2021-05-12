package org.fis2021.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
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

import static org.fis2021.App.loadFXML;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class RequestStudentTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StudentService.initStudent();
        TutorService.initTutor();
        LessonService.initLesson();
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
    void testAcceptedRequestIsDisplayed(FxRobot robot) {
        LessonService.addLesson("Vlad", "Lab OC", "12 05 2021", "12:00", "14:00", true, "ana.ana", "accepted", "");
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("Lab OC");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#tutorNameRequest").queryLabeled().getText()).isEqualTo("Vlad");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("accepted");

        robot.clickOn("#requestItemHBox");
    }

    @Test
    void testPendingRequestIsDisplayed(FxRobot robot) {
        LessonService.addLesson("Ion", "Lab BD", "12 05 2021", "14:00", "16:00", true, "ana.ana", "pending", "");
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("Lab BD");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#tutorNameRequest").queryLabeled().getText()).isEqualTo("Ion");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("pending");

        robot.clickOn("#requestItemHBox");
    }

    @Test
    void testAlertBoxDeclined(FxRobot robot) {
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "10:00", "12:00", true, "ana.ana", "declined", "too many students");
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("Lab MAC");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#tutorNameRequest").queryLabeled().getText()).isEqualTo("Alexandra");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("declined");

        robot.clickOn("#requestItemHBox");
        FxAssert.verifyThat("DECLINED", NodeMatchers.isVisible());
        FxAssert.verifyThat("too many students", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testAlertBoxRemoved(FxRobot robot) {
        LessonService.addLesson("Alex", "Lab AC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "removed", "grade < 5");
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        Assertions.assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("Lab AC");
        Assertions.assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        Assertions.assertThat(robot.lookup("#tutorNameRequest").queryLabeled().getText()).isEqualTo("Alex");
        Assertions.assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("removed");

        robot.clickOn("#requestItemHBox");
        FxAssert.verifyThat("REMOVED", NodeMatchers.isVisible());
        FxAssert.verifyThat("grade < 5", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testAcceptedChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad", "Lab OC", "12 05 2021", "12:00", "14:00", true, "ana.ana", "accepted", "");
        LessonService.addLesson("Ion", "Lab BD", "12 05 2021", "14:00", "16:00", true, "ana.ana", "pending", "");
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "10:00", "12:00", true, "ana.ana", "declined", "too many students");
        LessonService.addLesson("Alex", "Lab AC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "removed", "grade < 5");

        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");
        robot.clickOn("#statusChoice");
        robot.clickOn("Accepted");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("Lab OC");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#tutorNameRequest").queryLabeled().getText()).isEqualTo("Vlad");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("accepted");

        robot.clickOn("#requestItemHBox");
    }

    @Test
    void testPendingChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad", "Lab OC", "12 05 2021", "12:00", "14:00", true, "ana.ana", "accepted", "");
        LessonService.addLesson("Ion", "Lab BD", "12 05 2021", "14:00", "16:00", true, "ana.ana", "pending", "");
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "10:00", "12:00", true, "ana.ana", "declined", "too many students");
        LessonService.addLesson("Alex", "Lab AC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "removed", "grade < 5");

        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");
        robot.clickOn("#statusChoice");
        robot.clickOn("Pending");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("Lab BD");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#tutorNameRequest").queryLabeled().getText()).isEqualTo("Ion");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("pending");

        robot.clickOn("#requestItemHBox");
    }

    @Test
    void testDeclinedChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad", "Lab OC", "12 05 2021", "12:00", "14:00", true, "ana.ana", "accepted", "");
        LessonService.addLesson("Ion", "Lab BD", "12 05 2021", "14:00", "16:00", true, "ana.ana", "pending", "");
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "10:00", "12:00", true, "ana.ana", "declined", "too many students");
        LessonService.addLesson("Alex", "Lab AC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "removed", "grade < 5");

        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");
        robot.clickOn("#statusChoice");
        robot.clickOn("Declined");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("Lab MAC");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#tutorNameRequest").queryLabeled().getText()).isEqualTo("Alexandra");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("declined");

        robot.clickOn("#requestItemHBox");
        FxAssert.verifyThat("DECLINED", NodeMatchers.isVisible());
        FxAssert.verifyThat("too many students", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testRemovedChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad", "Lab OC", "12 05 2021", "12:00", "14:00", true, "ana.ana", "accepted", "");
        LessonService.addLesson("Ion", "Lab BD", "12 05 2021", "14:00", "16:00", true, "ana.ana", "pending", "");
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "10:00", "12:00", true, "ana.ana", "declined", "too many students");
        LessonService.addLesson("Alex", "Lab AC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "removed", "grade < 5");

        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");
        robot.clickOn("#statusChoice");
        robot.clickOn("Removed");

        Assertions.assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("Lab AC");
        Assertions.assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        Assertions.assertThat(robot.lookup("#tutorNameRequest").queryLabeled().getText()).isEqualTo("Alex");
        Assertions.assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("removed");

        robot.clickOn("#requestItemHBox");
        FxAssert.verifyThat("REMOVED", NodeMatchers.isVisible());
        FxAssert.verifyThat("grade < 5", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testDisplayAllAndAllChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad", "Lab OC", "12 05 2021", "12:00", "14:00", true, "ana.ana", "accepted", "");
        LessonService.addLesson("Ion", "Lab BD", "12 05 2021", "14:00", "16:00", true, "ana.ana", "pending", "");
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "10:00", "12:00", true, "ana.ana", "declined", "too many students");
        LessonService.addLesson("Alex", "Lab AC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "removed", "grade < 5");

        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        FxAssert.verifyThat("Vlad", NodeMatchers.isVisible());
        FxAssert.verifyThat("Ion", NodeMatchers.isVisible());
        FxAssert.verifyThat("Alex", NodeMatchers.isVisible());
        FxAssert.verifyThat("Alexandra", NodeMatchers.isVisible());

        robot.clickOn("#statusChoice");
        robot.clickOn("Removed");

        robot.clickOn("#statusChoice");
        robot.clickOn("All");

        FxAssert.verifyThat("Vlad", NodeMatchers.isVisible());
        FxAssert.verifyThat("Ion", NodeMatchers.isVisible());
        FxAssert.verifyThat("Alex", NodeMatchers.isVisible());
        FxAssert.verifyThat("Alexandra", NodeMatchers.isVisible());
    }

    @Test
    void testButtonsRequest(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }

    @Test
    void testRequestsAreUpdated(FxRobot robot) {
        LessonService.addLesson("Vlad", "Lab OC", "12 05 2021", "12:00", "14:00", true, "ana.ana", "accepted", "");
        LessonService.addLesson("Ion", "Lab BD", "12 05 2021", "14:00", "16:00", true, "ana.ana", "pending", "");
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "10:00", "12:00", true, "ana.ana", "declined", "too many students");
        LessonService.addLesson("Alex", "Lab AC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "removed", "grade < 5");

        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        FxAssert.verifyThat("Vlad", NodeMatchers.isVisible());
        FxAssert.verifyThat("Ion", NodeMatchers.isVisible());
        FxAssert.verifyThat("Alex", NodeMatchers.isVisible());
        FxAssert.verifyThat("Alexandra", NodeMatchers.isVisible());

        LessonService.addLesson("Ioana", "Lab TP", "11 05 2021", "08:00", "10:00", true, "ana.ana", "removed", "grade < 5");
        robot.clickOn("#requestsButton");

        FxAssert.verifyThat("Vlad", NodeMatchers.isVisible());
        FxAssert.verifyThat("Ion", NodeMatchers.isVisible());
        FxAssert.verifyThat("Alex", NodeMatchers.isVisible());
        FxAssert.verifyThat("Alexandra", NodeMatchers.isVisible());
        FxAssert.verifyThat("Ioana", NodeMatchers.isVisible());

    }
}