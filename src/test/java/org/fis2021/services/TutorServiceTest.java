package org.fis2021.services;

import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.models.Tutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TutorServiceTest {
    @BeforeEach
    void setUp() throws IOException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        TutorService.initTutor();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, there are no tutors persisted")
    void testDatabaseIsInitializedAndNoTutorIsPersisted() {
        assertThat(TutorService.getAllTutors()).isNotNull();
        assertThat(TutorService.getAllTutors()).isEmpty();
    }

    @Test
    @DisplayName("Tutor is added to database")
    void testTutorIsAdded() throws UsernameAlreadyExistsException {
        TutorService.addTutor("Ana", "ana.ana", "abcd", "fis", "cti");
        assertThat(TutorService.getAllTutors()).isNotEmpty();
        assertThat(TutorService.getAllTutors().size()).isEqualTo(1);
        Tutor tutor = TutorService.getAllTutors().get(0);
        assertThat(tutor.getNume()).isEqualTo("Ana");
        assertThat(tutor.getUsername()).isEqualTo("ana.ana");
        assertThat(tutor.getParola()).isEqualTo(TutorService.encodePassword("ana.ana", "abcd"));
        assertThat(tutor.getMaterie()).isEqualTo("fis");
        assertThat(tutor.getSpecializare()).isEqualTo("cti");
    }

    @Test
    @DisplayName("Two tutors with the same username cannot be added")
    void testTutorCannotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            TutorService.addTutor("Ana", "ana.ana", "abcd", "fis", "cti");
            TutorService.addTutor("Ana", "ana.ana", "abcd", "fis", "cti");
        });
    }

    @Test
    @DisplayName("Tutor cannot be found")
    void testTutorCannotBeFound() {
        assertThrows(UserNotFoundException.class, () -> {
            TutorService.addTutor("Ana", "ana.ana1", "abcd", "fis", "cti");
            TutorService.addTutor("Ana", "ana.ana2", "abcd", "fis", "cti");
            TutorService.getTutor("ana.ana");
        });
    }

    @Test
    @DisplayName("Tutor already exists")
    void testTutorAlreadyExists() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            TutorService.addTutor("Ana", "ana.ana1", "abcd", "fis", "cti");
            TutorService.checkTutorDoesNotAlreadyExist("ana.ana1");
        });
    }

    @Test
    @DisplayName("Password is encoded")
    void testPasswordIsEncode() throws UsernameAlreadyExistsException {
        TutorService.addTutor("Ana", "ana.ana1", "abcd", "fis", "cti");
        Tutor tutor = TutorService.getAllTutors().get(0);
        assertThat(tutor.getParola()).isEqualTo(TutorService.encodePassword("ana.ana1", "abcd"));
    }
}