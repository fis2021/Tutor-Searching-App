package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.StudentService;

import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.fis2021.services.StudentService.initStudent;

public class StudentRegisterController {

    @FXML
    private Button registerButton;

    @FXML
    private TextField numeField;

    @FXML
    private TextField facultateField;

    @FXML
    private TextField specializareField;

    @FXML
    private TextField nr_matricolField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label invalidCredentialsLabel;

    @FXML
    private Button loginButton;

    @FXML
    void backToLogin() {
        try {
            Stage stage = (Stage) invalidCredentialsLabel.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 1280, 720);
            stage.setTitle("Tutor Searching App - Login");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registerStudent() {
        initStudent();
        try {
            if (numeField.getText() == null || facultateField.getText() == null || specializareField.getText() == null || nr_matricolField.getText() == null || usernameField.getText() == null || passwordField.getText() == null
                    ||numeField.getText().isEmpty() || facultateField.getText().isEmpty() || specializareField.getText().isEmpty() || nr_matricolField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                invalidCredentialsLabel.setText("Please fill in the empty fields!");
                return;
            }
            StudentService.addStudent(numeField.getText(), facultateField.getText(), specializareField.getText(), nr_matricolField.getText(), usernameField.getText(), passwordField.getText());
            invalidCredentialsLabel.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            invalidCredentialsLabel.setText(e.getMessage());
        }
    }

}
