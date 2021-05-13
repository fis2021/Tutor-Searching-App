package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.models.Tutor;
import org.fis2021.services.LessonService;
import org.fis2021.services.StudentHolder;
import org.fis2021.services.TutorHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class RequestTutorController implements Initializable {
    @FXML
    private ChoiceBox diplayChoiceBox;

    @FXML
    private ScrollPane scrollPanel;

    @FXML
    private VBox vBox;

    private ArrayList<Lesson> lessons = LessonService.getAllLessons();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        diplayChoiceBox.getItems().addAll("All", "Pending", "Declined", "Removed");
        displayAll();
        diplayChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            try {
                displayCategory(newValue.toString().toLowerCase(Locale.ROOT));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void addAnchorPane(Lesson lesson) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/requestItemTutor.fxml"));
        AnchorPane anchorPane = loader.load();
        RequestItemTutorController requestItemTutorController = loader.getController();
        requestItemTutorController.setData(lesson);
        vBox.getChildren().add(anchorPane);
    }

    void displayCategory(String status) throws IOException {
        try {
            scrollPanel.setVvalue(0);
            vBox.getChildren().clear();
            TutorHolder tutorHolder = TutorHolder.getInstance();
            Tutor tutor = tutorHolder.getTutor();
            if (status.equals("all")){
                displayAll();
                return;
            }
            for (Lesson lesson : lessons) {
                if (lesson.getTutorName().equals(tutor.getNume()) &&  (lesson.getStatus().equals("pending") || lesson.getStatus().equals("declined") || lesson.getStatus().equals("removed"))) {
                    addAnchorPane(lesson);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    void displayAll(){
        try {
            scrollPanel.setVvalue(0);
            TutorHolder tutorHolder = TutorHolder.getInstance();
            Tutor tutor = tutorHolder.getTutor();
            for (Lesson lesson : lessons) {
                if (lesson.getTutorName().equals(tutor.getNume()) && !lesson.getStatus().equals("")) {
                    addAnchorPane(lesson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logoutButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out!");
        alert.setContentText("Are you sure you want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) vBox.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 1280, 720);
            stage.setTitle("Tutor Searching App - Login");
            stage.setScene(scene);
        }
    }

    @FXML
    void switchToAccount() throws IOException {
        Stage stage = (Stage) scrollPanel.getScene().getWindow();
        Scene scene = new Scene(loadFXML("accountTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Account");
        stage.setScene(scene);
    }

    @FXML
    void switchToHome() throws IOException {
        Stage stage = (Stage) scrollPanel.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToRequests() throws IOException {
        Stage stage = (Stage) scrollPanel.getScene().getWindow();
        Scene scene = new Scene(loadFXML("requestTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Requests Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToStudents() throws IOException {
        Stage stage = (Stage) scrollPanel.getScene().getWindow();
        Scene scene = new Scene(loadFXML("studentList"), 1280, 720);
        stage.setTitle("Tutor Searching App - Student List");
        stage.setScene(scene);
    }

}
