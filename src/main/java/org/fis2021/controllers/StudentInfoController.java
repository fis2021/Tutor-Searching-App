package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.models.Tutor;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.LessonService;
import org.fis2021.services.TutorHolder;

import java.util.ArrayList;

public class StudentInfoController {

    @FXML
    private Button submitButton;

    @FXML
    private Label stateLabel;

    @FXML
    private TextField removeTextField;

    @FXML
    private Label succesLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label facultyLabel;

    @FXML
    private Label specializationLabel;

    @FXML
    private Label registrationNumberLabel;

    @FXML
    private Label usernameLabel;

    private Student student;
    private ArrayList<Lesson> lessonArrayList = LessonService.getAllLessons();

    public void setInfo(Student student) {
        this.student=student;
        nameLabel.setText(student.getNume());
        facultyLabel.setText(student.getFacultate());
        specializationLabel.setText(student.getSpecializare());
        registrationNumberLabel.setText(student.getNrMatricol());
        usernameLabel.setText(student.getUsername());
        submitButton.setVisible(false);
        removeTextField.setVisible(false);
        succesLabel.setVisible(false);
        stateLabel.setVisible(false);
    }

    @FXML
    void enableRemoveStudent() {
        submitButton.setVisible(true);
        removeTextField.setVisible(true);
        stateLabel.setVisible(true);
    }

    @FXML
    void removeStudent() {
        TutorHolder tutorHolder = TutorHolder.getInstance();
        Tutor tutor = tutorHolder.getTutor();
        for (Lesson lesson : lessonArrayList) {
            if (lesson.getTutorName().equals(tutor.getNume()) && lesson.getStudentName().equals((student.getUsername()))) {
                System.out.println(lesson.getTutorName()+" "+lesson.getStudentName()+" "+lesson.getStatus());
                lesson.setStatus("removed");
                lesson.setDeclinedMessage(removeTextField.getText());
                DatabaseService.getDatabase().getRepository(Lesson.class).update(lesson);
                break;
            }
        }
        succesLabel.setVisible(true);
    }

}
