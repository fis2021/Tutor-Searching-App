package org.fis2021.controllers;

import com.calendarfx.view.TimeField;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.fis2021.models.Tutor;
import org.fis2021.services.LessonService;
import org.fis2021.services.TutorHolder;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static org.fis2021.App.loadFXML;

public class AddCoursesController {

    @FXML
    private TextField courseNameField;

    @FXML
    private DatePicker courseDateField;

    @FXML
    private TimeField courseStartTime;

    @FXML
    private TimeField courseEndTime;

    @FXML
    private CheckBox courseWeekly;

    @FXML
    private Button courseAddButton;

    @FXML
    private Label courseAddedLabel;

    @FXML
    void addCourse() {
        if(!courseNameField.getText().isEmpty() && courseDateField.getValue() != null && !courseDateField.getValue().toString().isEmpty()  && courseStartTime.getValue() != null && courseEndTime.getValue() != null) {
            TutorHolder tutorHolder = TutorHolder.getInstance();
            Tutor tutor = tutorHolder.getTutor();
            String date = courseDateField.getValue().format(DateTimeFormatter.ofPattern("dd MM yyyy"));
            LessonService.addLesson(tutor.getNume(), courseNameField.getText(), date, courseStartTime.getValue().toString(), courseEndTime.getValue().toString(), courseWeekly.isSelected(), "", "", "");
            courseAddedLabel.setText("Course added successfully!");
        }
        else {
            courseAddedLabel.setText("Please fill in the empty fields!");
            return;
        }
    }

    @FXML
    void logoutButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out!");
        alert.setContentText("Are you sure you want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) courseNameField.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 1280, 720);
            stage.setTitle("Tutor Searching App - Login");
            stage.setScene(scene);
        }
    }

    @FXML
    void switchToAccount() throws IOException {
        Stage stage = (Stage) courseNameField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("accountTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Account");
        stage.setScene(scene);
    }

    @FXML
    void switchToCalendar() throws IOException {
        Stage stage = (Stage) courseNameField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("calendarTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Calendar");
        stage.setScene(scene);
    }

    @FXML
    void switchToCourses() throws IOException {
        Stage stage = (Stage) courseNameField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("addCourses"), 1280, 720);
        stage.setTitle("Tutor Searching App - Add Courses");
        stage.setScene(scene);
    }

    @FXML
    void switchToHomeTutor() throws IOException {
        Stage stage = (Stage) courseNameField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToRequests() throws IOException {
        Stage stage = (Stage) courseNameField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("requestTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Requests Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToStudents() throws IOException {
        Stage stage = (Stage) courseNameField.getScene().getWindow();
        Scene scene = new Scene(loadFXML("studentList"), 1280, 720);
        stage.setTitle("Tutor Searching App - Student List");
        stage.setScene(scene);
    }

}
