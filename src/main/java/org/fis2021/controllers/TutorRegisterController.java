package org.fis2021.controllers;

import com.calendarfx.view.TimeField;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.LessonService;
import org.fis2021.services.TutorService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static org.fis2021.App.loadFXML;
import static org.fis2021.services.LessonService.initLesson;
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
    private DatePicker datePicker;

    @FXML
    private TimeField startTime;

    @FXML
    private TimeField endTime;

    @FXML
    private TextField classNameField;

    @FXML
    private CheckBox recurrenceCheckBox;

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
        initLesson();
        try {
            if (numeField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || materieField.getText().isEmpty() || specializareField.getText().isEmpty() || classNameField.getText().isEmpty() || datePicker.getValue() == null){
                invalidCredentialsLabel.setText("Please fill in the required fields!");
                return;
            }
            String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd MM yyyy"));
            TutorService.addTutor(numeField.getText(), usernameField.getText(), passwordField.getText(), materieField.getText(), specializareField.getText());
            LessonService.addLesson(numeField.getText(), classNameField.getText(), date, startTime.getValue().toString(), endTime.getValue().toString(), recurrenceCheckBox.isSelected(), "", "");
            invalidCredentialsLabel.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            invalidCredentialsLabel.setText(e.getMessage());
        }
    }

}
