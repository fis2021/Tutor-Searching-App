package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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

public class StudentListController implements Initializable{

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private HBox headerHBox;

    @FXML
    private Label studentNotFoundLabel;

    @FXML
    private VBox vBox;

    @FXML
    private ScrollPane scrollPane;

    private String courseName;

    private ArrayList<Student> studentArrayList = new ArrayList<>();
    private ArrayList<Lesson> lessonArrayList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStudents();
    }

    void addStudents(){
        try {
            headerHBox.setVisible(true);
            studentArrayList = StudentService.getAllStudents();
            lessonArrayList = LessonService.getAllLessons();
            TutorHolder tutorHolder = TutorHolder.getInstance();
            Tutor tutor = tutorHolder.getTutor();
            for (Lesson lesson : lessonArrayList) {
                if (lesson.getTutorName().equals(tutor.getNume()) && lesson.getStatus().equals("accepted")) {
                    courseName = lesson.getLessonName();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/studentListItem.fxml"));
                    HBox hBox = loader.load();
                    hBoxEventHandler(hBox, StudentService.getStudent(lesson.getStudentName()));
                    StudentListItemController studentListItemController = loader.getController();
                    studentListItemController.setInfo(StudentService.getStudent(lesson.getStudentName()));
                    vBox.getChildren().add(hBox);
                }
            }
        } catch (IOException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    void loadStudentInfoItem(Student student, String courseName) {
        try {
            scrollPane.setVvalue(0);
            headerHBox.setVisible(false);
            vBox.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/studentInfo.fxml"));
            VBox studentInfoVBox = loader.load();
            StudentInfoController studentInfoController = loader.getController();
            studentInfoController.setInfo(student, courseName);
            vBox.getChildren().add(studentInfoVBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchButtonPressed() {
        try {
            scrollPane.setVvalue(0);
            studentNotFoundLabel.setVisible(false);
            headerHBox.setVisible(true);
            if (searchTextField.getText() != null) {
                vBox.getChildren().clear();
                studentArrayList = StudentService.getAllStudents();
                int cnt = 1;
                for (Student student : studentArrayList) {
                    if ((student.getNume().toLowerCase()).indexOf(searchTextField.getText().toLowerCase()) == 0 || (student.getNume().toLowerCase().substring(student.getNume().indexOf(" ") + 1)).indexOf(searchTextField.getText()) == 0) {
                        cnt = 0;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/studentListItem.fxml"));
                        HBox hBox = loader.load();
                        hBoxEventHandler(hBox, student);
                        StudentListItemController studentListItemController = loader.getController();
                        studentListItemController.setInfo(student);
                        vBox.getChildren().add(hBox);
                    }
                }
                if (cnt == 1) {
                    studentNotFoundLabel.setVisible(true);
                    studentNotFoundLabel.setText("Student not found");
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchToHomeTutor() throws IOException{
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToStudents() throws IOException{
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("studentList"), 1280, 720);
        stage.setTitle("Tutor Searching App - Student List");
        stage.setScene(scene);
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

    void hBoxEventHandler(HBox hBox, Student student){
        hBox.setOnMouseClicked(mouseEvent -> loadStudentInfoItem(student, courseName));
    }

    @FXML
    void switchToRequests() throws IOException{
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("requestTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Requests Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToAccount() throws IOException{
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("accountTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Account");
        stage.setScene(scene);
    }

    @FXML
    void switchToCalendar() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("calendarTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Calendar");
        stage.setScene(scene);
    }

    @FXML
    void switchToCourses() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("addCourses"), 1280, 720);
        stage.setTitle("Tutor Searching App - Add Courses");
        stage.setScene(scene);
    }
}
