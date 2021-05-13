package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.models.Tutor;
import org.fis2021.services.LessonService;
import org.fis2021.services.StudentService;
import org.fis2021.services.TutorHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class HomePageTutorController implements Initializable {
    @FXML
    private GridPane gridPane;

    private ArrayList<Student> studentArrayList = new ArrayList<>();
    private ArrayList<Lesson> lessonArrayList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            studentArrayList = StudentService.getAllStudents();
            lessonArrayList = LessonService.getAllLessons();
            int column = 0;
            int row = 1;
            TutorHolder tutorHolder = TutorHolder.getInstance();
            Tutor tutor = tutorHolder.getTutor();
            for (Lesson lesson : lessonArrayList) {
                if (lesson.getTutorName().equals(tutor.getNume()) && lesson.getStatus().equals("accepted")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/itemStudent.fxml"));
                    VBox vBox = loader.load();
                    ItemStudentController itemStudentController = loader.getController();
                    itemStudentController.setInfo(StudentService.getStudent(lesson.getStudentName()));
                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    gridPane.add(vBox, column++, row);

                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMinWidth(Region.USE_PREF_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMinHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(vBox, new Insets(20));
                }
            }
        } catch (IOException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToStudentList() throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(loadFXML("studentList"), 1280, 720);
        stage.setTitle("Tutor Searching App - Student List");
        stage.setScene(scene);
    }

    @FXML
    void switchToAccount() throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(loadFXML("accountTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Account");
        stage.setScene(scene);
    }

    @FXML
    void logoutButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out!");
        alert.setContentText("Are you sure you want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) gridPane.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 1280, 720);
            stage.setTitle("Tutor Searching App - Login");
            stage.setScene(scene);
        }
    }

    @FXML
    void switchToRequests() throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(loadFXML("requestTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Requests Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToCalendar() throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(loadFXML("calendarTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Calendar");
        stage.setScene(scene);
    }

    @FXML
    void switchToCourses() throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(loadFXML("addCourses"), 1280, 720);
        stage.setTitle("Tutor Searching App - Add Courses");
        stage.setScene(scene);
    }
}
