package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.fis2021.models.Lesson;
import org.fis2021.services.LessonService;

import java.util.ArrayList;

public class LessonItemController {
    @FXML
    private HBox hBox;

    @FXML
    private Label lessonNameLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label endTimeLabel;

    private Lesson lesson;


    @FXML
    void enrollButtonPressed() {
        if (lesson.getStatus() == null) {
            LessonService.setStatus(lesson, "pending");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sent pending request");
            alert.setHeaderText("Request is pending");
            alert.setContentText("Your request to enroll this class has been sent.");
            alert.showAndWait();
        }
        if (lesson.getStatus().equals("pending")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Request is pending");
            alert.setHeaderText("Pending Request");
            alert.setContentText("Request for this class is already pending.");
            alert.showAndWait();
        }
        if (lesson.getStatus().equals("accepted")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Request is accepted");
            alert.setHeaderText("Request already accepted");
            alert.setContentText("You are already enrolled in this class.");
            alert.showAndWait();
        }
    }

    public void setData(Lesson lesson) {
        this.lesson = lesson;
        lessonNameLabel.setText(lesson.getLessonName());
        dateLabel.setText(lesson.getDate());
        startTimeLabel.setText(lesson.getStartTime());
        endTimeLabel.setText(lesson.getEndTime());
    }

}
