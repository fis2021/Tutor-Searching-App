package org.fis2021.services;

import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;

import java.util.ArrayList;

public class LessonService {

    private static ObjectRepository<Lesson> lessonRepository;

    public static void initLesson(){
        lessonRepository = DatabaseService.getDatabase().getRepository(Lesson.class);
    }

    public static void addLesson(String tutorName, String lessonName,String date, String startTime, String endTime, boolean weeklyRec, String studentName, String status){
        lessonRepository.insert(new Lesson(tutorName, lessonName, date, startTime, endTime, weeklyRec, studentName, status));
    }

    public static ArrayList<Lesson> getAllLessons(){
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Lesson lesson : lessonRepository.find()){
            lessons.add(lesson);
        }
        return lessons;
    }

    public static boolean findStudentInLesson(String studentName, Lesson lesson) {
        ArrayList<Lesson> lessons = getAllLessons();
        for (Lesson lessontemp : lessons) {
            if(lessontemp.equals(lesson) && lessontemp.getStudentName().equals(studentName) && lessontemp.getStatus().equals("accepted")) {
                return true;
            }
        }
        return false;
    }

    public static void setStatus(Lesson lesson, String status) {
        for (Lesson lesson1 : lessonRepository.find()) {
            if (lesson.getLessonName().equals(lesson1.getLessonName()) &&
                    lesson.getTutorName().equals(lesson1.getTutorName()) &&
                    lesson.getDate().equals(lesson1.getDate()) &&
                    lesson.getStartTime().equals(lesson1.getStartTime()) &&
                    lesson.getEndTime().equals(lesson1.getEndTime())) {
                StudentHolder studentHolder = StudentHolder.getInstance();
                Student student = studentHolder.getStudent();
                System.out.println(student.getUsername());
                lesson1.setStudentName(student.getUsername());
                lesson1.setStatus(status);
                lessonRepository.update(lesson1);
            }
        }
    }
}
