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
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fis2021.App.loadFXML;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class TutorListTest {
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
        TutorService.addTutor("Andrei", "andrei.andrei", "abcd", "MAC", "CTI");
        StudentService.addStudent("Ana", "AC", "CTI", "LM123", "ana.ana", "abcd");
        LessonService.addLesson("Andrei", "Lab MAC", "12 05 2021", "08:00", "10:00", true, "", "", "");
        LessonService.addLesson("Andrei", "Lab MAC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "pending", "");
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "08:00", "10:00", true, "", "", "");
        LessonService.addLesson("Alexandra", "Lab MAC", "12 05 2021", "08:00", "10:00", true, "ana.ana", "declined", "too many students");
        LessonService.addLesson("Vlad", "Lab FIS", "11 05 2021", "12:00", "14:00", true, "","", "");
        LessonService.addLesson("Vlad", "Lab FIS", "11 05 2021", "12:00", "14:00", true,"ana.ana", "accepted", "");
        LessonService.addLesson("Vlad", "Lab FIS", "10 05 2021", "10:00", "12:00",true, "", "", "");
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
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#tutorlistButton");

        robot.clickOn("#requestButton");
        robot.clickOn("#tutorsButton");

        robot.clickOn("#calendarButton");
        robot.clickOn("#tutorsButton");

        robot.clickOn("#accountButton");
        robot.clickOn("#tutorsButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }

    @Test
    void testSearchButton(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#tutorlistButton");

        robot.clickOn("#tutorSearchField");
        robot.write("vl");
        robot.clickOn("#tutorSearchButton");

        assertThat(robot.lookup("#tutorName").queryLabeled().getText()).isEqualTo("Vlad");
        assertThat(robot.lookup("#className").queryLabeled().getText()).isEqualTo("FIS");
        assertThat(robot.lookup("#departmentName").queryLabeled().getText()).isEqualTo("CTI");
    }

    @Test
    void testTutorInfo(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#tutorlistButton");

        robot.clickOn("#tutorSearchField");
        robot.write("vl");
        robot.clickOn("#tutorSearchButton");

        robot.clickOn("#tutorListHBox");

        assertThat(robot.lookup("#tutorInfoName").queryLabeled().getText()).isEqualTo("Vlad");
        assertThat(robot.lookup("#tutorInfoSubject").queryLabeled().getText()).isEqualTo("FIS");
        assertThat(robot.lookup("#tutorInfoDepartment").queryLabeled().getText()).isEqualTo("CTI");
        assertThat(robot.lookup("#tutorInfoUsername").queryLabeled().getText()).isEqualTo("vlad.vlad");
        assertThat(robot.lookup("#tutorInfoRating").queryLabeled().getText()).isEqualTo("0");
    }

    @Test
    void testTutorNotFound(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#tutorlistButton");

        robot.clickOn("#tutorSearchField");
        robot.write("anc");
        robot.clickOn("#tutorSearchButton");

        assertThat(robot.lookup("#tutorNotFound").queryLabeled().getText()).isEqualTo("Tutor not found");
    }

    @Test
    void testTutorInfoLessons(FxRobot robot) throws IOException {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#tutorlistButton");

        robot.clickOn("#tutorSearchField");
        robot.write("vl");
        robot.clickOn("#tutorSearchButton");

        robot.clickOn("#tutorListHBox");

        Assertions.assertThat(robot.lookup("#lessonsListView").queryListView()).hasExactlyNumItems(2);
    }

    @Test
    void testDeclinedClass(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#tutorlistButton");

        robot.clickOn("#tutorSearchField");
        robot.write("alex");
        robot.clickOn("#tutorSearchButton");

        robot.clickOn("#tutorListHBox");

        robot.clickOn("#enrollButton");

        FxAssert.verifyThat("DECLINED.", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testPendingClass(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#tutorlistButton");

        robot.clickOn("#tutorSearchField");
        robot.write("andrei");
        robot.clickOn("#tutorSearchButton");

        robot.clickOn("#tutorListHBox");

        robot.clickOn("#enrollButton");

        FxAssert.verifyThat("Request for this class is already pending.", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }

    @Test
    void testAcceptedClass(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#tutorlistButton");

        robot.clickOn("#tutorSearchField");
        robot.write("vlad");
        robot.clickOn("#tutorSearchButton");

        robot.clickOn("#tutorListHBox");

        robot.clickOn("#enrollButton");

        FxAssert.verifyThat("You are already enrolled in this class.", NodeMatchers.isVisible());
        robot.clickOn("OK");
    }
}