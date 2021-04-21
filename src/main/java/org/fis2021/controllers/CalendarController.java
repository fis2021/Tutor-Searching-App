package org.fis2021.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.models.Tutor;
import org.fis2021.services.LessonService;
import org.fis2021.services.StudentHolder;
import org.fis2021.services.TutorService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class CalendarController implements Initializable {
    @FXML
    private CalendarView calendarView;

    private Calendar calendar;
    private ArrayList<Lesson> lessons;

    public LocalDate stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MM yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public LocalTime stringToTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(time, formatter);
        return localTime;
    }

    @FXML
    void switchToHome() throws IOException {
        Stage stage = (Stage) calendarView.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageStudent"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Student");
        stage.setScene(scene);
    }

    @FXML
    void switchToTutors() throws IOException {
        Stage stage = (Stage) calendarView.getScene().getWindow();
        Scene scene = new Scene(loadFXML("tutorlist"), 1280, 720);
        stage.setTitle("Tutor Searching App - Tutor List");
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calendar = new Calendar("TSA");

        calendar.setStyle(Calendar.Style.STYLE2);

        calendar.setReadOnly(true);

        StudentHolder studentHolder = StudentHolder.getInstance();
        Student student = studentHolder.getStudent();
        lessons = LessonService.getAllLessons();
        for (Lesson lesson : lessons) {
            if (lesson.getStudentName() != null && lesson.getStudentName().equals(student.getUsername()) && lesson.getStatus().equals("accepted")) {
                Interval interval = new Interval(stringToDate(lesson.getDate()), stringToTime(lesson.getStartTime()), stringToDate(lesson.getDate()), stringToTime(lesson.getEndTime()));
                Entry entry = new Entry(lesson.getLessonName(), interval);
                if (lesson.isWeeklyRec()){
                    entry.setRecurrenceRule("RRULE:FREQ=WEEKLY");
                }
                calendar.addEntry(entry);
            }
        }

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());
    }
}
