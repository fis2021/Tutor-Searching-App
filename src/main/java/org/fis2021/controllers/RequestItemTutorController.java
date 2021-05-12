package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.fis2021.models.Lesson;
import org.fis2021.services.DatabaseService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequestItemTutorController {

    @FXML
    private AnchorPane requestAnchorPane;

    @FXML
    private Label classNameField;

    @FXML
    private Label dateField;

    @FXML
    private Label startTimeField;

    @FXML
    private Label endTimeField;

    @FXML
    private Label freqLabel;

    @FXML
    private Label studentNameField;

    @FXML
    private Label statusField;

    @FXML
    private AnchorPane declineAnchorPane;

    @FXML
    private Button declineButton;

    @FXML
    private Button acceptButton;

    @FXML
    private TextField declineTextField;

    @FXML
    private Button submitButton;

    @FXML
    private Label declineLabel;

    private Lesson lesson;

    public LocalDate stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MM yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public String localDateToDayOfWeek(String date) {
        LocalDate localDate = stringToDate(date);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.toString();
    }

    public void setData(Lesson lesson) {
        this.lesson = lesson;
        classNameField.setText(lesson.getLessonName());
        dateField.setText(localDateToDayOfWeek(lesson.getDate()));
        startTimeField.setText(lesson.getStartTime());
        endTimeField.setText(lesson.getEndTime());
        studentNameField.setText(lesson.getStudentName());
        statusField.setText(lesson.getStatus());
        if(!lesson.getStatus().equals("pending")) {
            declineAnchorPane.setVisible(false);
        }
        else {
            declineLabel.setVisible(false);
            submitButton.setVisible(false);
            declineTextField.setVisible(false);
        }
        if (lesson.isWeeklyRec()) {
            freqLabel.setText("weekly");
        }
    }

    @FXML
    void showDeclinedMessage() {
        if (lesson.getStatus().equals("declined") || lesson.getStatus().equals("removed")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reason");
            alert.setHeaderText(lesson.getStatus().toUpperCase());
            alert.setContentText(lesson.getDeclinedMessage());
            alert.showAndWait();
            return;
        }
    }

    @FXML
    void declineButtonClicked() {
        declineLabel.setVisible(true);
        submitButton.setVisible(true);
        declineTextField.setVisible(true);
        declineButton.setVisible(false);
        acceptButton.setVisible(false);
    }

    @FXML
    void acceptButtonClicked() {
        this.lesson.setStatus("accepted");
        DatabaseService.getDatabase().getRepository(Lesson.class).update(lesson);
    }

    @FXML
    void submitButtonClicked() {
        this.lesson.setStatus("declined");
        this.lesson.setDeclinedMessage(declineTextField.getText());
        DatabaseService.getDatabase().getRepository(Lesson.class).update(lesson);
        declineAnchorPane.setVisible(false);
    }
}
