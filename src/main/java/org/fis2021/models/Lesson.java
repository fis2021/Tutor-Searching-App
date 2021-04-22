package org.fis2021.models;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;
import org.fis2021.services.LessonService;

public class Lesson {
    @Id
    String id;

    private String lessonName, date, startTime, endTime, tutorName, studentName, status;
    //status: pending, accepted, declined
    boolean weeklyRec;

    Lesson(){
    }

    public Lesson(String tutorName, String lessonName, String date, String startTime, String endTime, boolean weeklyRec) {
        this.tutorName = tutorName;
        this.lessonName = lessonName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = NitriteId.newId().toString();
        this.weeklyRec = weeklyRec;
    }

    public boolean isWeeklyRec() {
        return weeklyRec;
    }

    public void setWeeklyRec(boolean weeklyRec) {
        this.weeklyRec = weeklyRec;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return lessonName+" "+date+" "+startTime+" "+endTime+" "+studentName+" "+status;
    }


}
