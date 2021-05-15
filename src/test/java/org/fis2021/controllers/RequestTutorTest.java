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
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class RequestTutorTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StudentService.initStudent();
        TutorService.initTutor();
        LessonService.initLesson();
        StudentService.addStudent("Ana", "AC", "CTI-RO", "AC123", "ana.ana", "ana");
        StudentService.addStudent("Maria", "AC", "CTI-RO", "AC420", "maria", "maria");
        StudentService.addStudent("Ioana", "AC", "CTI-RO", "IS240", "ioana", "ioana");
        StudentService.addStudent("Dan","AC","CTI-RO","DN123","dan","dan");
        TutorService.addTutor("Vlad","vlad","vlad","FIS","CTI-RO");
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
    void testDeclinedRequestIsDisplayed(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"maria","declined","Too many students.");
        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("TestFX");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#studentNameRequest").queryLabeled().getText()).isEqualTo("maria");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("declined");

        robot.clickOn("#requestItemHBox");
    }

    @Test
    void testPendingRequestIsDisplayed(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ana.ana","pending","");
        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("TestFX");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#studentNameRequest").queryLabeled().getText()).isEqualTo("ana.ana");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("pending");

        robot.clickOn("#requestItemHBox");
    }

    @Test
    void testAlertBoxDeclined(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"maria","declined","Too many students.");
        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("TestFX");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#studentNameRequest").queryLabeled().getText()).isEqualTo("maria");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("declined");

        robot.clickOn("#requestItemHBox");
        FxAssert.verifyThat("DECLINED", NodeMatchers.isVisible());
        FxAssert.verifyThat("Too many students.", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testAlertBoxRemoved(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ioana","removed","Grades under 5.");
        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        Assertions.assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("TestFX");
        Assertions.assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        Assertions.assertThat(robot.lookup("#studentNameRequest").queryLabeled().getText()).isEqualTo("ioana");
        Assertions.assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("removed");

        robot.clickOn("#requestItemHBox");
        FxAssert.verifyThat("REMOVED", NodeMatchers.isVisible());
        FxAssert.verifyThat("Grades under 5.", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testPendingChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ana.ana","pending","");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"maria","declined","Too many students.");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ioana","removed","Grades under 5.");

        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");
        robot.clickOn("#statusChoice");
        robot.clickOn("Pending");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("TestFX");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#studentNameRequest").queryLabeled().getText()).isEqualTo("ana.ana");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("pending");

        robot.clickOn("#requestItemHBox");
    }

    @Test
    void testDeclinedChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ana.ana","pending","");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"maria","declined","Too many students.");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ioana","removed","Grades under 5.");

        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");
        robot.clickOn("#statusChoice");
        robot.clickOn("Declined");

        assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("TestFX");
        assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        assertThat(robot.lookup("#studentNameRequest").queryLabeled().getText()).isEqualTo("maria");
        assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("declined");

        robot.clickOn("#requestItemHBox");
        FxAssert.verifyThat("DECLINED", NodeMatchers.isVisible());
        FxAssert.verifyThat("Too many students.", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testRemovedChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ana.ana","pending","");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"maria","declined","Too many students.");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ioana","removed","Grades under 5.");

        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");
        robot.clickOn("#statusChoice");
        robot.clickOn("Removed");

        Assertions.assertThat(robot.lookup("#classNameRequest").queryLabeled().getText()).isEqualTo("TestFX");
        Assertions.assertThat(robot.lookup("#freqLabel").queryLabeled().getText()).isEqualTo("weekly");
        Assertions.assertThat(robot.lookup("#studentNameRequest").queryLabeled().getText()).isEqualTo("ioana");
        Assertions.assertThat(robot.lookup("#statusRequest").queryLabeled().getText()).isEqualTo("removed");

        robot.clickOn("#requestItemHBox");
        FxAssert.verifyThat("REMOVED", NodeMatchers.isVisible());
        FxAssert.verifyThat("Grades under 5.", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testDisplayAllAndAllChoiceBox(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ana.ana","pending","");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"maria","declined","Too many students.");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ioana","removed","Grades under 5.");

        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        FxAssert.verifyThat("ana.ana", NodeMatchers.isVisible());
        FxAssert.verifyThat("maria", NodeMatchers.isVisible());
        FxAssert.verifyThat("ioana", NodeMatchers.isVisible());

        robot.clickOn("#statusChoice");
        robot.clickOn("Removed");

        robot.clickOn("#statusChoice");
        robot.clickOn("All");

        FxAssert.verifyThat("ana.ana", NodeMatchers.isVisible());
        FxAssert.verifyThat("maria", NodeMatchers.isVisible());
        FxAssert.verifyThat("ioana", NodeMatchers.isVisible());
    }

    @Test
    void testLogoutRequest(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }

    @Test
    void testRequestsAreUpdated(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ana.ana","pending","");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"maria","declined","Too many students.");
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ioana","removed","Grades under 5.");

        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        FxAssert.verifyThat("ana.ana", NodeMatchers.isVisible());
        FxAssert.verifyThat("maria", NodeMatchers.isVisible());
        FxAssert.verifyThat("ioana", NodeMatchers.isVisible());

        LessonService.addLesson("Vlad", "TestFX", "21 05 2021", "19:00", "20:40", true, "dan", "removed", "Grade under 5.");
        robot.clickOn("#requestsButton");

        FxAssert.verifyThat("ana.ana", NodeMatchers.isVisible());
        FxAssert.verifyThat("maria", NodeMatchers.isVisible());
        FxAssert.verifyThat("ioana", NodeMatchers.isVisible());
        FxAssert.verifyThat("dan", NodeMatchers.isVisible());

    }

    @Test
    void testAcceptRequests(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ana.ana","pending","");

        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        robot.clickOn("#acceptButton");

        robot.clickOn("#homeButton");

        FxAssert.verifyThat("Ana", NodeMatchers.isVisible());
    }

    @Test
    void testDeclineRequests(FxRobot robot) {
        LessonService.addLesson("Vlad","TestFX","21 05 2021","19:00","20:40",true,"ana.ana","pending","");

        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#requestsButton");

        robot.clickOn("#declineButton");
        robot.clickOn("#declineText");
        robot.write("Too many students.");
        robot.clickOn("#submitButton");

        robot.clickOn("#requestsButton");
        robot.clickOn("#statusChoice");
        robot.clickOn("Declined");

        FxAssert.verifyThat("ana.ana", NodeMatchers.isVisible());
    }
}