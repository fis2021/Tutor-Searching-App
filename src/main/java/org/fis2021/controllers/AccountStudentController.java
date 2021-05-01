package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.fis2021.models.Student;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.StudentHolder;
import org.fis2021.services.StudentService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class AccountStudentController implements Initializable {
    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label passwordWarningLabel;

    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField universityTextfield;

    @FXML
    private TextField departmentTextield;

    @FXML
    private TextField registrationTextfield;

    @FXML
    private Label nameWarningLabel;

    @FXML
    private Label universityWarningLabel;

    @FXML
    private Label departmentWarningLabel;

    @FXML
    private Label registrationNrWarningLabel;

    private Student student;

    @FXML
    void changeDepartment() {
        if (departmentTextield == null || departmentTextield.getText().isEmpty()) {
            departmentWarningLabel.setText("Please enter your new department.");
            return;
        }
        student.setSpecializare(departmentTextield.getText());
        DatabaseService.getDatabase().getRepository(Student.class).update(student);
        departmentWarningLabel.setText("Department changed successfully.");
        return;
    }

    @FXML
    void changeName() {
        if (nameTextfield == null || nameTextfield.getText().isEmpty()){
            nameWarningLabel.setText("Please enter your new name.");
            return;
        }
        student.setNume(nameTextfield.getText());
        DatabaseService.getDatabase().getRepository(Student.class).update(student);
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
        if (!StudentService.encodePassword(student.getUsername(), passwordField.getText()).equals(StudentService.encodePassword(student.getUsername(), confirmPasswordField.getText()))) {
            passwordWarningLabel.setText("Passwords do not match.");
            return;
        }else {
            student.setParola(StudentService.encodePassword(student.getUsername(), passwordField.getText()));
            DatabaseService.getDatabase().getRepository(Student.class).update(student);
            passwordWarningLabel.setText("Password changed successfully.");
            return;
        }
    }

    @FXML
    void changeRegistrationNumber() {
        if (registrationTextfield == null || registrationTextfield.getText().isEmpty()) {
            registrationNrWarningLabel.setText("Please enter your new registration number.");
            return;
        }
        student.setNrMatricol(registrationTextfield.getText());
        DatabaseService.getDatabase().getRepository(Student.class).update(student);
        registrationNrWarningLabel.setText("Registration number changed successfully.");
        return;
    }

    @FXML
    void changeUniversity() {
        if (universityTextfield == null || universityTextfield.getText().isEmpty()) {
            universityWarningLabel.setText("Please enter your new university.");
            return;
        }
        student.setFacultate(universityTextfield.getText());
        DatabaseService.getDatabase().getRepository(Student.class).update(student);
        universityWarningLabel.setText("University changed successfully.");
        return;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentHolder studentHolder = StudentHolder.getInstance();
        student = studentHolder.getStudent();
        nameTextfield.setText(student.getNume());
        universityTextfield.setText(student.getFacultate());
        departmentTextield.setText(student.getSpecializare());
        registrationTextfield.setText(student.getNrMatricol());
    }

    @FXML
    void logoutbuttonPressed() throws IOException {
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
        Scene scene = new Scene(loadFXML("accountStudent"), 1280, 720);
        stage.setTitle("Tutor Searching App - Account");
        stage.setScene(scene);
    }

    @FXML
    void switchToCalendar() throws IOException {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("calendar"), 1280, 720);
        stage.setTitle("Tutor Searching App - Calendar");
        stage.setScene(scene);
    }

    @FXML
    void switchToRequests() throws IOException {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("requestStudent"), 1280, 720);
        stage.setTitle("Tutor Searching App - Requests");
        stage.setScene(scene);
    }

    @FXML
    void switchToTutor() throws IOException {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("tutorlist"), 1280, 720);
        stage.setTitle("Tutor Searching App - Tutor List");
        stage.setScene(scene);
    }

    @FXML
    void switchToHome() throws IOException {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageStudent"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Student");
        stage.setScene(scene);
    }
}
