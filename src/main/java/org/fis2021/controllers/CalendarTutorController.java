package org.fis2021.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.fis2021.models.Lesson;
import org.fis2021.models.Tutor;
import org.fis2021.services.LessonService;
import org.fis2021.services.TutorHolder;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class CalendarTutorController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calendar = new Calendar("TSA");

        calendar.setStyle(Calendar.Style.STYLE2);

        calendar.setReadOnly(true);
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowSourceTrayButton(false);

        TutorHolder tutorHolder = TutorHolder.getInstance();
        Tutor tutor = tutorHolder.getTutor();
        lessons = LessonService.getAllLessons();
        for (Lesson lesson : lessons) {
            if (lesson.getTutorName().equals(tutor.getNume()) && lesson.getStatus().equals("")) {
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

    @FXML
    void switchToHome() throws IOException {
        Stage stage = (Stage) calendarView.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToStudentList() throws IOException {
        Stage stage = (Stage) calendarView.getScene().getWindow();
        Scene scene = new Scene(loadFXML("studentList"), 1280, 720);
        stage.setTitle("Tutor Searching App - Student List");
        stage.setScene(scene);
    }

    @FXML
    void switchToCalendar() throws IOException {
        Stage stage = (Stage) calendarView.getScene().getWindow();
        Scene scene = new Scene(loadFXML("calendarTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Calendar");
        stage.setScene(scene);
    }

    @FXML
    void switchToRequests() throws IOException {
        Stage stage = (Stage) calendarView.getScene().getWindow();
        Scene scene = new Scene(loadFXML("requestTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Requests Tutor");
        stage.setScene(scene);
    }

    @FXML
    void switchToAccount() throws IOException {
        Stage stage = (Stage) calendarView.getScene().getWindow();
        Scene scene = new Scene(loadFXML("accountTutor"), 1280, 720);
        stage.setTitle("Tutor Searching App - Account");
        stage.setScene(scene);
    }

    @FXML
    void logoutButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out!");
        alert.setContentText("Are you sure you want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) calendarView.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 1280, 720);
            stage.setTitle("Tutor Searching App - Login");
            stage.setScene(scene);
        }
    }

    @FXML
    void switchToCourses() throws IOException {
        Stage stage = (Stage) calendarView.getScene().getWindow();
        Scene scene = new Scene(loadFXML("addCourses"), 1280, 720);
        stage.setTitle("Tutor Searching App - Add Courses");
        stage.setScene(scene);
    }

}
