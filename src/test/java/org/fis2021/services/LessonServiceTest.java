package org.fis2021.services;

import org.apache.commons.io.FileUtils;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class LessonServiceTest {
    @BeforeEach
    void setUp() throws IOException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        LessonService.initLesson();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, there are no lessons persisted")
    void testDatabaseIsInitializedAndNoLessonIsPersisted() {
        assertThat(LessonService.getAllLessons()).isNotNull();
        assertThat(LessonService.getAllLessons()).isEmpty();
    }

    @Test
    @DisplayName("Lesson is added to database")
    void testLessonIsAddedToDatabase() {
        LessonService.addLesson("ana", "fis", "1", "0", "0", true, "vlad", "accepted", "");
        assertThat(LessonService.getAllLessons()).isNotEmpty();
        assertThat(LessonService.getAllLessons().size()).isEqualTo(1);
        Lesson lesson = LessonService.getAllLessons().get(0);
        assertThat(lesson.getTutorName()).isEqualTo("ana");
        assertThat(lesson.getLessonName()).isEqualTo("fis");
        assertThat(lesson.getDate()).isEqualTo("1");
        assertThat(lesson.getStartTime()).isEqualTo("0");
        assertThat(lesson.getEndTime()).isEqualTo("0");
        assertTrue(lesson.isWeeklyRec());
        assertThat(lesson.getStudentName()).isEqualTo("vlad");
        assertThat(lesson.getStatus()).isEqualTo("accepted");
    }


}