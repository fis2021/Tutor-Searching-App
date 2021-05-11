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
import org.junit.jupiter.api.Assertions;
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
class AccountStudentTest {

    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StudentService.initStudent();
        StudentService.addStudent("Ana", "AC", "CTI", "LM123", "ana.ana", "abcd");
        StudentService.addStudent("Vlad", "AC", "IS", "LM222", "vlad.vlad", "abcd");
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
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#requestButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#calendarButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
    }

    @Test
    void testPasswordsDoNotMatch(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
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
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#changePasswordButton");
        assertThat(robot.lookup("#passwordWarning").queryLabeled().getText()).isEqualTo("Please enter new password.");
    }

    @Test
    void testNoConfirmationPassword(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
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
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
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
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#name");
        robot.eraseText(3);
        robot.write("Ana Maria");
        robot.clickOn("#changeNameButton");
        assertThat(robot.lookup("#nameWarning").queryLabeled().getText()).isEqualTo("Name changed successfully.");

        robot.clickOn("#accountButton");
        FxAssert.verifyThat("Ana Maria", NodeMatchers.isVisible());
    }

    @Test
    void testNoNewName(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#name");
        robot.eraseText(3);
        robot.clickOn("#changeNameButton");
        assertThat(robot.lookup("#nameWarning").queryLabeled().getText()).isEqualTo("Please enter your new name.");
    }

    @Test
    void testUniversityIsChange(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#university");
        robot.eraseText(3);
        robot.write("UVT");
        robot.clickOn("#changeUniversityButton");
        assertThat(robot.lookup("#universityWarning").queryLabeled().getText()).isEqualTo("University changed successfully.");
        robot.clickOn("#accountButton");
        FxAssert.verifyThat("UVT", NodeMatchers.isVisible());
    }

    @Test
    void testNoNewUniversity(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#university");
        robot.eraseText(3);
        robot.clickOn("#changeUniversityButton");
        assertThat(robot.lookup("#universityWarning").queryLabeled().getText()).isEqualTo("Please enter your new university.");
    }

    @Test
    void testDepartmentIsChanged(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#department");
        robot.eraseText(3);
        robot.write("IS");
        robot.clickOn("#departmentButton");

        assertThat(robot.lookup("#departmentWarning").queryLabeled().getText()).isEqualTo("Department changed successfully.");
        robot.clickOn("#accountButton");
        FxAssert.verifyThat("IS", NodeMatchers.isVisible());
    }

    @Test
    void testNoNewDepartment(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#department");
        robot.eraseText(3);
        robot.clickOn("#departmentButton");

        assertThat(robot.lookup("#departmentWarning").queryLabeled().getText()).isEqualTo("Please enter your new department.");
    }

    @Test
    void testRegistrationNumberIsChanged(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#registrationNumber");
        robot.eraseText(5);
        robot.write("LM111");
        robot.clickOn("#registrationNumberButton");

        assertThat(robot.lookup("#registrationNumberWarning").queryLabeled().getText()).isEqualTo("Registration number changed successfully.");

        robot.clickOn("#accountButton");
        FxAssert.verifyThat("LM111", NodeMatchers.isVisible());
    }

    @Test
    void testNoRegistrationNumber(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#registrationNumber");
        robot.eraseText(5);
        robot.clickOn("#registrationNumberButton");

        assertThat(robot.lookup("#registrationNumberWarning").queryLabeled().getText()).isEqualTo("Please enter your new registration number.");
    }

    @Test
    void testLogoutAndLogin(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("ana.ana");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");

        robot.clickOn("#logoutButton");
        robot.clickOn("OK");

        robot.clickOn("#username");
        robot.write("vlad.vlad");
        robot.clickOn("#password");
        robot.write("abcd");
        robot.clickOn("#role");
        robot.clickOn("Student");
        robot.clickOn("#loginButton");
        robot.clickOn("#accountButton");
        FxAssert.verifyThat("LM222", NodeMatchers.isVisible());
    }
}