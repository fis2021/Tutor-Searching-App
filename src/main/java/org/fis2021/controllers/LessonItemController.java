package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.LessonService;
import org.fis2021.services.StudentHolder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @FXML
    private Label freqLabel;

    private Lesson lesson;


    @FXML
    void enrollButtonPressed() {
        ArrayList<Lesson> lessons = LessonService.getAllLessons();
        StudentHolder studentHolder = StudentHolder.getInstance();
        Student student = studentHolder.getStudent();
        System.out.println(student.getUsername());
        int cnt = 0;
        for (Lesson lessontemp : lessons) {
            if (lessontemp.equals(lesson) && !lessontemp.getStudentName().equals("") && !lessontemp.getStatus().equals("")) {
                if (lessontemp.getStudentName().equals(student.getUsername()) && lessontemp.getStatus().equals("pending")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Request is pending");
                    alert.setHeaderText("Pending Request");
                    alert.setContentText("Request for this class is already pending.");
                    alert.showAndWait();
                    return;
                } else if (lessontemp.getStudentName().equals(student.getUsername()) && lessontemp.getStatus().equals("accepted")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Request is accepted");
                    alert.setHeaderText("Request already accepted");
                    alert.setContentText("You are already enrolled in this class.");
                    alert.showAndWait();
                    return;
                } else if (lessontemp.getStudentName().equals(student.getUsername()) && lessontemp.getStatus().equals("declined")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Request has been already declined");
                    alert.setHeaderText("Request already declined");
                    alert.setContentText("DECLINED.");
                    alert.showAndWait();
                    return;
                }
            }
            cnt += 1;
        }
        if (cnt == lessons.size()) {
            LessonService.addLesson(lesson.getTutorName(), lesson.getLessonName(), lesson.getDate(), lesson.getStartTime(), lesson.getEndTime(), lesson.isWeeklyRec(), student.getUsername(), "pending", "");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Request is pending");
            alert.setHeaderText("Request is pending.");
            alert.setContentText("Your request has been sent.");
            alert.showAndWait();
            return;
        }

    }

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
        lessonNameLabel.setText(lesson.getLessonName());
        dateLabel.setText(localDateToDayOfWeek(lesson.getDate()));
        startTimeLabel.setText(lesson.getStartTime());
        endTimeLabel.setText(lesson.getEndTime());
        if (lesson.isWeeklyRec()){
            freqLabel.setText("weekly");
        }
    }

}
