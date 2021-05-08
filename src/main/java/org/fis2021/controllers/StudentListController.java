package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fis2021.models.Student;
import org.fis2021.services.StudentService;

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
    private Label tutorNotFoundLabel;

    @FXML
    private VBox vBox;

    private ArrayList<Student> studentArrayList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStudents();
    }

    void addStudents(){
        try {
            headerHBox.setVisible(true);
            studentArrayList = StudentService.getAllStudents();

            for (Student student : studentArrayList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/studentListItem.fxml"));
                HBox hBox = loader.load();
                hBoxEventHandler(hBox, student);
                StudentListItemController studentListItemController = loader.getController();
                studentListItemController.setInfo(student);
                vBox.getChildren().add(hBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadStudentInfoItem(Student student) {
        try {
            headerHBox.setVisible(false);
            vBox.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/studentInfo.fxml"));
            VBox studentInfoVBox = loader.load();
            StudentInfoController studentInfoController = loader.getController();
            studentInfoController.setInfo(student);
            vBox.getChildren().add(studentInfoVBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchButtonPressed() {
        try {
            headerHBox.setVisible(true);
            if (searchTextField.getText() != null) {
                vBox.getChildren().clear();
                studentArrayList = StudentService.getAllStudents();
                for (Student student : studentArrayList) {
                    if (student.getNume().equals(searchTextField.getText())) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/studentListItem.fxml"));
                        HBox hBox = loader.load();
                        hBoxEventHandler(hBox, student);
                        StudentListItemController studentListItemController = loader.getController();
                        studentListItemController.setInfo(student);
                        vBox.getChildren().add(hBox);
                        return;
                    }
                }
                tutorNotFoundLabel.setText("Student not found");
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

    void hBoxEventHandler(HBox hBox, Student student){
        hBox.setOnMouseClicked(mouseEvent -> loadStudentInfoItem(student));
    }
}
