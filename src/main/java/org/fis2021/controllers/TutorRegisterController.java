package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.models.Tutor;
import org.fis2021.services.StudentService;
import org.fis2021.services.TutorService;

import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.fis2021.services.StudentService.initStudent;
import static org.fis2021.services.TutorService.initTutor;

public class TutorRegisterController {

    @FXML
    private Button registerButton;

    @FXML
    private TextField numeField;

    @FXML
    private TextField materieField;

    @FXML
    private TextField specializareField;

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
    void registerTutor() {
        initTutor();
        try {
            if (numeField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || materieField.getText().isEmpty() || specializareField.getText().isEmpty()){
                invalidCredentialsLabel.setText("Please fill in the required fields!");
                return;
            }
            TutorService.addTutor(numeField.getText(), usernameField.getText(), passwordField.getText(), materieField.getText(), specializareField.getText());
            invalidCredentialsLabel.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            invalidCredentialsLabel.setText(e.getMessage());
        }
    }

}
