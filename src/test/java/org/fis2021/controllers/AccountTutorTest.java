package org.fis2021.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.FileSystemService;
import org.fis2021.services.LessonService;
import org.fis2021.services.TutorService;
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
class AccountTutorTest {

    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        TutorService.initTutor();
        LessonService.initLesson();
        TutorService.addTutor("Bianca", "bia.bia", "bia", "CD", "IS");
        TutorService.addTutor("Vlad", "vlad", "vlad", "RC", "Info");
        LessonService.addLesson("Bianca","TAL","18 05 2021","18:00","19:50",true,"","","");
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
    void testButtonsAccountPage(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#studentListButton");
        robot.clickOn("#homeButton");

        robot.clickOn("#requestsButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#calendarButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#coursesButton");
        robot.clickOn("#homeButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }

    @Test
    void testPasswordsDoNotMatch(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#newPassword");
        robot.write("abcde");

        robot.clickOn("#confirmNewPassword");
        robot.write("abcdef");

        robot.clickOn("#changePasswordButton");
        assertThat(robot.lookup("#passwordWarning").queryLabeled().getText()).isEqualTo("Passwords do not match.");
    }

    @Test
    void testNoNewPassword(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#changePasswordButton");
        assertThat(robot.lookup("#passwordWarning").queryLabeled().getText()).isEqualTo("Please enter new password.");
    }

    @Test
    void testNoConfirmationPassword(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#newPassword");
        robot.write("abcde");

        robot.clickOn("#changePasswordButton");
        assertThat(robot.lookup("#passwordWarning").queryLabeled().getText()).isEqualTo("Please confirm your new password.");
    }

    @Test
    void testPasswordIsChangedSuccessfully(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#newPassword");
        robot.write("abcdef");

        robot.clickOn("#confirmNewPassword");
        robot.write("abcdef");

        robot.clickOn("#changePasswordButton");
        assertThat(robot.lookup("#passwordWarning").queryLabeled().getText()).isEqualTo("Password changed successfully.");
    }

    @Test
    void testNameIsChanged(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#name");
        robot.eraseText(6);
        robot.write("Bianca Popescu");
        robot.clickOn("#changeNameButton");
        assertThat(robot.lookup("#nameWarning").queryLabeled().getText()).isEqualTo("Name changed successfully.");

        robot.clickOn("#accountButton");
        FxAssert.verifyThat("Bianca Popescu", NodeMatchers.isVisible());
    }

    @Test
    void testNoNewName(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#name");
        robot.eraseText(6);
        robot.clickOn("#changeNameButton");
        assertThat(robot.lookup("#nameWarning").queryLabeled().getText()).isEqualTo("Please enter your new name.");
    }

    @Test
    void testSubjectIsChange(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#subject");
        robot.eraseText(3);
        robot.write("CI");
        robot.clickOn("#changeSubjectButton");
        assertThat(robot.lookup("#subjectWarning").queryLabeled().getText()).isEqualTo("Subject changed successfully.");
        robot.clickOn("#accountButton");
        FxAssert.verifyThat("CI", NodeMatchers.isVisible());
    }

    @Test
    void testNoNewSubject(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#subject");
        robot.eraseText(3);
        robot.clickOn("#changeSubjectButton");
        assertThat(robot.lookup("#subjectWarning").queryLabeled().getText()).isEqualTo("Please enter your new subject.");
    }

    @Test
    void testSpecializationIsChanged(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#specialization");
        robot.eraseText(6);
        robot.write("CTI-RO");
        robot.clickOn("#changeSpecializationButton");

        assertThat(robot.lookup("#specializationWarning").queryLabeled().getText()).isEqualTo("Specialization changed successfully.");
        robot.clickOn("#accountButton");
        FxAssert.verifyThat("CTI-RO", NodeMatchers.isVisible());
    }

    @Test
    void testNoNewSpecialization(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#specialization");
        robot.eraseText(6);
        robot.clickOn("#changeSpecializationButton");

        assertThat(robot.lookup("#specializationWarning").queryLabeled().getText()).isEqualTo("Please enter your new specialization.");
    }

    @Test
    void testClassNameIsChanged(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#className");
        robot.eraseText(30);
        robot.write("TTL");
        robot.clickOn("#changeClassNameButton");

        assertThat(robot.lookup("#classNameWarning").queryLabeled().getText()).isEqualTo("Class name changed successfully.");

        robot.clickOn("#accountButton");
        FxAssert.verifyThat("TTL", NodeMatchers.isVisible());
    }

    @Test
    void testNoClassName(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#className");
        robot.eraseText(30);
        robot.clickOn("#changeClassNameButton");

        assertThat(robot.lookup("#classNameWarning").queryLabeled().getText()).isEqualTo("Please enter your new class name.");
    }

    @Test
    void testLogoutAndLogin(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("bia.bia");
        robot.clickOn("#password");
        robot.write("bia");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");

        robot.clickOn("#username");
        robot.write("vlad");
        robot.clickOn("#password");
        robot.write("vlad");
        robot.clickOn("#role");
        robot.clickOn("Tutor");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");
        FxAssert.verifyThat("Vlad", NodeMatchers.isVisible());
    }
}