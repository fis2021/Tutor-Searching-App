package org.fis2021.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class CalendarController implements Initializable {
    @FXML
    private CalendarView calendarView;

    private Calendar calendar;

    public void addEntryToCalendar(Entry entry){
        calendar.addEntry(entry);
    }

    public Entry setEntry(String name, LocalDateTime start, LocalDateTime stop){
        Entry entry = new  Entry(name,new Interval(start, stop));
        entry.setRecurrenceRule("RRULE:FREQ=WEEKLY");
        return entry;
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

        LocalDateTime ora1 = LocalDateTime.of(2021, 04, 17, 10, 0);
        LocalDateTime ora2 = LocalDateTime.of(2021, 04, 17, 12, 0);

        LocalDateTime ora3 = LocalDateTime.of(2021, 04, 20, 12, 0, 0);
        LocalDateTime ora4 = LocalDateTime.of(2021, 04, 20, 14, 0, 0);


        addEntryToCalendar(setEntry("Curs FIS", ora1, ora2));
        addEntryToCalendar(setEntry("Curs PAA", ora3, ora4));

        calendar.setReadOnly(true);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }
}
