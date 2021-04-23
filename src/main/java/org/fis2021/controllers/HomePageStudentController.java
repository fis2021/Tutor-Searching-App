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
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.models.Tutor;
import org.fis2021.services.LessonService;
import org.fis2021.services.StudentHolder;
import org.fis2021.services.TutorService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class HomePageStudentController implements Initializable {
    @FXML
    private GridPane gridPane;

    private ArrayList<Tutor> tutorArrayList = new ArrayList<>();
    private ArrayList<Lesson> lessonArrayList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tutorArrayList = TutorService.getAllTutors();
            lessonArrayList = LessonService.getAllLessons();
            int column = 0;
            int row = 1;
            StudentHolder studentHolder = StudentHolder.getInstance();
            Student student = studentHolder.getStudent();
            for (Lesson lesson : lessonArrayList) {
                if (lesson.getStudentName() != null && lesson.getStudentName().equals(student.getUsername()) && lesson.getStatus().equals("accepted")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/itemcurs.fxml"));
                    VBox vBox = loader.load();
                    ItemCursController itemCursController = loader.getController();
                    itemCursController.setData(lesson);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToTutorList() throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(loadFXML("tutorlist"), 1280, 720);
        stage.setTitle("Tutor Searching App - Tutor List");
        stage.setScene(scene);
    }

    @FXML
    void switchToRequests() throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(loadFXML("requestStudent"), 1280, 720);
        stage.setTitle("Tutor Searching App - Requests");
        stage.setScene(scene);
    }

    @FXML
    void switchToCalendar() throws IOException {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(loadFXML("calendar"), 1280, 720);
        stage.setTitle("Tutor Searching App - Calendar");
        stage.setScene(scene);
    }

    @FXML
    void logoutButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out!");
        alert.setContentText("Do you want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) gridPane.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 1280, 720);
            stage.setTitle("Tutor Searching App - Login");
            stage.setScene(scene);
        }
    }
}

