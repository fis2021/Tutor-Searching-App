package org.fis2021.controllers;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class CalendarControllerLogicTest {

    @Test
    void testStringToDate() {
        String date = "28 04 2021";
        LocalDate localDate = CalendarController.stringToDate(date);
        LocalDate equalLocalDate = LocalDate.of(2021, 04, 28);

        assertThat(localDate).isEqualTo(equalLocalDate);
    }

    @Test
    void testStringToTime() {
        String time = "10:00";
        LocalTime localTime = CalendarController.stringToTime(time);
        LocalTime equalLocalTime = LocalTime.of(10, 0);

        assertThat(localTime).isEqualTo(equalLocalTime);
    }
}