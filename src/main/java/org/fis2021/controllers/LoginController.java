package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.services.StudentService;
import org.fis2021.services.TutorService;

import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.fis2021.services.StudentService.initStudent;
import static org.fis2021.services.TutorService.initTutor;

public class LoginController {

    @FXML
    private ImageView imageView;

    @FXML
    private Button loginButton;

    @FXML
    private ChoiceBox roleBox;

    public void initialize() {
        roleBox.getItems().addAll("Tutor", "Student");
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordFiled;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label tutorRegisterLabel;

    @FXML
    private Label studentRegisterLabel;

    @FXML
    private Label invalidCredentialsLabel;

    @FXML
    void login() {
        initStudent();
        initTutor();
        if(((String) roleBox.getValue()).equals("Student")) {
            initStudent();

            if (usernameField.getText() == null || usernameField.getText().isEmpty()) {
                invalidCredentialsLabel.setText("Please type in your username!");
                return;
            }

            if (passwordFiled.getText() == null || passwordFiled.getText().isEmpty()) {
                invalidCredentialsLabel.setText("Please type in your password!");
                return;
            }

            String encoded_password = StudentService.encodePassword(usernameField.getText(), passwordFiled.getText());

            try{
                String stored_password = StudentService.getHashedUserPassword(usernameField.getText());
                if(stored_password.equals(encoded_password)){
                    invalidCredentialsLabel.setText(String.format("Successfully logged in as %s!", usernameField.getText()));
                    return;
                }

            } catch(UserNotFoundException e){
                invalidCredentialsLabel.setText(e.getMessage());
                return;
            }

            invalidCredentialsLabel.setText("Invalid credentials!");
        }
        if(((String) roleBox.getValue()).equals("Tutor")) {
            initTutor();

            if (usernameField.getText() == null || usernameField.getText().isEmpty()) {
                invalidCredentialsLabel.setText("Please type in your username!");
                return;
            }

            if (passwordFiled.getText() == null || passwordFiled.getText().isEmpty()) {
                invalidCredentialsLabel.setText("Please type in your password!");
                return;
            }

            String encoded_password = TutorService.encodePassword(usernameField.getText(), passwordFiled.getText());

            try {
                String stored_password = TutorService.getHashedUserPassword(usernameField.getText());
                if (stored_password.equals(encoded_password)) {
                    invalidCredentialsLabel.setText(String.format("Successfully logged in as %s!", usernameField.getText()));
                    return;
                }
            } catch (UserNotFoundException e) {
                invalidCredentialsLabel.setText(e.getMessage());
                return;
            }

            invalidCredentialsLabel.setText("Invalid credentials!");
        }
    }

    @FXML
    void registerStudent() {
        try {
            Stage stage = (Stage) invalidCredentialsLabel.getScene().getWindow();
            Scene scene = new Scene(loadFXML("registerStudent"), 1280, 720);
            stage.setTitle("Tutor Searching App - Student Register");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registerTutor() {
        try {
            Stage stage = (Stage) invalidCredentialsLabel.getScene().getWindow();
            Scene scene = new Scene(loadFXML("registerTutor"), 1280, 720);
            stage.setTitle("Tutor Searching App - Tutor Register");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}