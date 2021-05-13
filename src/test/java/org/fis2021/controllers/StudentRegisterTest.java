package org.fis2021.controllers;


import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.FileSystemService;
import org.fis2021.services.StudentService;
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
class StudentRegisterTest {
    @BeforeEach
    void setUp() throws IOException{
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StudentService.initStudent();
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
    void testEveryFieldIsCompletedAndStudentIsRegistered(FxRobot robot) {
        robot.clickOn("#registerStudent");
        robot.clickOn("#nameStudent");
        robot.write("Ana");
        robot.clickOn("#uniStudent");
        robot.write("UPT");
        robot.clickOn("#specStudent");
        robot.write("CTI");
        robot.clickOn("#regNoStudent");
        robot.write("123");
        robot.clickOn("#usernameStudent");
        robot.write("ana.ana");
        robot.clickOn("#passwordStudent");
        robot.write("abcd");
        robot.clickOn("#registerStudent");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Account created successfully!");
    }

    @Test
    void testAllFieldsAreEmpty(FxRobot robot) {
        robot.clickOn("#registerStudent");
        robot.clickOn("#registerStudent");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testNameIsEmpty(FxRobot robot) {
        robot.clickOn("#registerStudent");
        robot.clickOn("#uniStudent");
        robot.write("UPT");
        robot.clickOn("#specStudent");
        robot.write("CTI");
        robot.clickOn("#regNoStudent");
        robot.write("123");
        robot.clickOn("#usernameStudent");
        robot.write("ana.ana");
        robot.clickOn("#passwordStudent");
        robot.write("abcd");
        robot.clickOn("#registerStudent");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testUniIsEmpty(FxRobot robot) {
        robot.clickOn("#registerStudent");
        robot.clickOn("#nameStudent");
        robot.write("Ana");
        robot.clickOn("#specStudent");
        robot.write("CTI");
        robot.clickOn("#regNoStudent");
        robot.write("123");
        robot.clickOn("#usernameStudent");
        robot.write("ana.ana");
        robot.clickOn("#passwordStudent");
        robot.write("abcd");
        robot.clickOn("#registerStudent");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testSpecIsEmpty(FxRobot robot){
        robot.clickOn("#registerStudent");
        robot.clickOn("#nameStudent");
        robot.write("Ana");
        robot.clickOn("#uniStudent");
        robot.write("UPT");
        robot.clickOn("#regNoStudent");
        robot.write("123");
        robot.clickOn("#usernameStudent");
        robot.write("ana.ana");
        robot.clickOn("#passwordStudent");
        robot.write("abcd");
        robot.clickOn("#registerStudent");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testRegNoIsEmpty(FxRobot robot) {
        robot.clickOn("#registerStudent");
        robot.clickOn("#nameStudent");
        robot.write("Ana");
        robot.clickOn("#uniStudent");
        robot.write("UPT");
        robot.clickOn("#specStudent");
        robot.write("CTI");
        robot.clickOn("#usernameStudent");
        robot.write("ana.ana");
        robot.clickOn("#passwordStudent");
        robot.write("abcd");
        robot.clickOn("#registerStudent");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testUsernameIsEmpty(FxRobot robot) {
        robot.clickOn("#registerStudent");
        robot.clickOn("#nameStudent");
        robot.write("Ana");
        robot.clickOn("#uniStudent");
        robot.write("UPT");
        robot.clickOn("#specStudent");
        robot.write("CTI");
        robot.clickOn("#regNoStudent");
        robot.write("123");
        robot.clickOn("#passwordStudent");
        robot.write("abcd");
        robot.clickOn("#registerStudent");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testPasswordIsEmpty(FxRobot robot) {
        robot.clickOn("#registerStudent");
        robot.clickOn("#nameStudent");
        robot.write("Ana");
        robot.clickOn("#uniStudent");
        robot.write("UPT");
        robot.clickOn("#specStudent");
        robot.write("CTI");
        robot.clickOn("#regNoStudent");
        robot.write("123");
        robot.clickOn("#usernameStudent");
        robot.write("ana.ana");
        robot.clickOn("#registerStudent");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in the empty fields!");
    }

    @Test
    void testLoginButtonWorks(FxRobot robot) {
        robot.clickOn("#registerStudent");
        robot.clickOn("#switchToLogin");
        FxAssert.verifyThat("#loginButton", NodeMatchers.isVisible());
    }
}