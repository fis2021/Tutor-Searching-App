package org.fis2021.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.models.Tutor;

import static org.fis2021.services.FileSystemService.getPathToFile;

public class DatabaseService {
    private static Nitrite database;
    private static ObjectRepository<Student> studentRepository;
    private static ObjectRepository<Tutor> tutorRepository;
    private static ObjectRepository<Lesson> lessonRepository;

    public static void initDatabase() {
        FileSystemService.initDirectory();
        database = Nitrite.builder()
                .filePath(getPathToFile("registration.db").toFile())
                .openOrCreate("test", "test");
        studentRepository = database.getRepository(Student.class);
        tutorRepository = database.getRepository(Tutor.class);
        lessonRepository = database.getRepository(Lesson.class);
    }

    public static Nitrite getDatabase(){
        return database;
    }
}
