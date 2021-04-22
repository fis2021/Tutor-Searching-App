package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis2021.models.Lesson;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequestItemController {

    @FXML
    private Label classNameField;

    @FXML
    private Label dateField;

    @FXML
    private Label startTimeField;

    @FXML
    private Label endTimeField;

    @FXML
    private Label tutorNameField;

    @FXML
    private Label statusField;

    @FXML
    private Label freqLabel;

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
        classNameField.setText(lesson.getLessonName());
        dateField.setText(localDateToDayOfWeek(lesson.getDate()));
        startTimeField.setText(lesson.getStartTime());
        endTimeField.setText(lesson.getEndTime());
        tutorNameField.setText(lesson.getTutorName());
        statusField.setText(lesson.getStatus());
        if (lesson.isWeeklyRec()){
            freqLabel.setText("weekly");
        }
    }

}
