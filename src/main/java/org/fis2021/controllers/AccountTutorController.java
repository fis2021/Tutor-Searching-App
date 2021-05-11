package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.fis2021.models.Lesson;
import org.fis2021.models.Tutor;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.LessonService;
import org.fis2021.services.TutorService;
import org.fis2021.services.TutorHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class AccountTutorController implements Initializable {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label passwordWarningLabel;

    @FXML
    private TextField nameTextfield;

    @FXML
    private Label nameWarningLabel;

    @FXML
    private TextField subjectTextfield;

    @FXML
    private Label subjectWarningLabel;

    @FXML
    private TextField specializationTextield;

    @FXML
    private Label specializationWarningLabel;

    @FXML
    private TextField classNameTextfield;

    @FXML
    private Label classNameWarningLabel;

    private Tutor tutor;
    private ArrayList<Lesson> lessons = LessonService.getAllLessons();

    @FXML
    void changeClassName() {
        if (classNameTextfield.getText() == null || classNameTextfield.getText().isEmpty()){
            classNameWarningLabel.setText("Please enter your new class name.");
            return;
        }
        for (Lesson lesson : lessons) {
            if(lesson.getTutorName().equals(tutor.getNume()) && lesson.getStudentName().equals("") && lesson.getStatus().equals("")) {
                lesson.setLessonName(classNameTextfield.getText());
                DatabaseService.getDatabase().getRepository(Lesson.class).update(lesson);
            }
            break;
        }
        classNameWarningLabel.setText("Class name changed successfully.");
        return;
    }

    @FXML
    void changeName() {
        if (nameTextfield == null || nameTextfield.getText().isEmpty()){
            nameWarningLabel.setText("Please enter your new name.");
            return;
        }
        tutor.setNume(nameTextfield.getText());
        DatabaseService.getDatabase().getRepository(Tutor.class).update(tutor);
        nameWarningLabel.setText("Name changed successfully.");
        return;
    }

    @FXML
    void changePassword() {
        if (passwordField == null || passwordField.getText().isEmpty()) {
            passwordWarningLabel.setText("Please enter new password.");
            return;
        }
        if (confirmPasswordField == null || confirmPasswordField.getText().isEmpty()) {
            passwordWarningLabel.setText("Please confirm your new password.");
            return;
        }
        if (!TutorService.encodePassword(tutor.getUsername(), passwordField.getText()).equals(TutorService.encodePassword(tutor.getUsername(), confirmPasswordField.getText()))) {
            passwordWarningLabel.setText("Passwords do not match.");
            return;
        }else {
            tutor.setParola(TutorService.encodePassword(tutor.getUsername(), passwordField.getText()));
            DatabaseService.getDatabase().getRepository(Tutor.class).update(tutor);
            passwordWarningLabel.setText("Password changed successfully.");
            return;
        }
    }

    @FXML
    void changeSpecialization() {
        if (specializationTextield == null || specializationTextield.getText().isEmpty()) {
            specializationWarningLabel.setText("Please enter your new specialization.");
            return;
        }
        tutor.setSpecializare(specializationTextield.getText());
        DatabaseService.getDatabase().getRepository(Tutor.class).update(tutor);
        specializationWarningLabel.setText("Specialization changed successfully.");
        return;
    }

    @FXML
    void changeSubject() {
        if (subjectTextfield == null || subjectTextfield.getText().isEmpty()) {
            subjectWarningLabel.setText("Please enter your new subject.");
            return;
        }
        tutor.setMaterie(subjectTextfield.getText());
        DatabaseService.getDatabase().getRepository(Tutor.class).update(tutor);
        subjectWarningLabel.setText("Subject changed successfully.");
        return;
    }

    @FXML
    void logoutButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out!");
        alert.setContentText("Are you sure you want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) passwordField.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 1280, 720);
            stage.setTitle("Tutor Searching App - Login");
            stage.setScene(scene);
        }
    }

    @FXML
    void switchToAccount() throws IOException {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("accountTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Account");
        stage.setScene(scene);
    }

    @FXML
    void switchToHome() throws IOException {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToStudentList() throws IOException {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("studentList"), 1280, 720);
        stage.setTitle("Tutor Searching App - Student List");
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TutorHolder tutorHolder = TutorHolder.getInstance();
        tutor = tutorHolder.getTutor();
        nameTextfield.setText(tutor.getNume());
        subjectTextfield.setText(tutor.getMaterie());
        specializationTextield.setText(tutor.getSpecializare());
        for (Lesson lesson : lessons) {
            if(lesson.getTutorName().equals(tutor.getNume()) && lesson.getStudentName().equals("") && lesson.getStatus().equals("")) {
                classNameTextfield.setText(lesson.getLessonName());
            }
            break;
        }
    }

}
