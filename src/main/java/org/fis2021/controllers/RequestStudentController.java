package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.services.LessonService;
import org.fis2021.services.StudentHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class RequestStudentController implements Initializable {
    @FXML
    private VBox vBox;

    @FXML
    private ChoiceBox diplayChoiceBox;

    @FXML
    private ScrollPane scrollPanel;

    private ArrayList<Lesson> lessons = LessonService.getAllLessons();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        diplayChoiceBox.getItems().addAll("All", "Accepted", "Pending", "Declined");
        displayAll();
        diplayChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            try {
                displayCategory(newValue.toString().toLowerCase(Locale.ROOT));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void addHBox(Lesson lesson) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/requestitem.fxml"));
        HBox hBox = loader.load();
        RequestItemController requestItemController = loader.getController();
        requestItemController.setData(lesson);
        vBox.getChildren().add(hBox);
    }

    void displayCategory(String status) throws IOException {
        try {
            scrollPanel.setVvalue(0);
            vBox.getChildren().clear();
            StudentHolder studentHolder = StudentHolder.getInstance();
            Student student = studentHolder.getStudent();
            if (status.equals("all")){
                displayAll();
                return;
            }
            for (Lesson lesson : lessons) {
                if (lesson.getStudentName() != null && lesson.getStudentName().equals(student.getUsername()) && lesson.getStatus().equals(status)) {
                    addHBox(lesson);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    void displayAll(){
        try {
            scrollPanel.setVvalue(0);
            StudentHolder studentHolder = StudentHolder.getInstance();
            Student student = studentHolder.getStudent();
            System.out.println(student.getUsername());
            for (Lesson lesson : lessons) {
                if (lesson.getStudentName() != null && lesson.getStudentName().equals(student.getUsername())) {
                    System.out.println(lesson.getStudentName());
                    addHBox(lesson);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToCalendar() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("calendar"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Student");
        stage.setScene(scene);
    }

    @FXML
    void switchToHome() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageStudent"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Student");
        stage.setScene(scene);
    }

    @FXML
    void switchToRequests() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("requestStudent"), 1280, 720);
        stage.setTitle("Tutor Searching App - Requests");
        stage.setScene(scene);
    }

    @FXML
    void switchToTutors() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("tutorlist"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Student");
        stage.setScene(scene);
    }

    @FXML
    void logoutButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out!");
        alert.setContentText("Do you want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) vBox.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 1280, 720);
            stage.setTitle("Tutor Searching App - Login");
            stage.setScene(scene);
        }
    }
}

