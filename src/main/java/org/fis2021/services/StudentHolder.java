package org.fis2021.services;

import org.fis2021.models.Student;

public class StudentHolder {
    private static volatile StudentHolder instance = null;
    private static Student student;

    private StudentHolder(){}

    public static StudentHolder getInstance(){
        if(instance == null){
            instance = new StudentHolder();
        }
        return instance;
    }

    public void setStudent(Student student){
        StudentHolder.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void resetStudent() {
        this.instance = null;
        this.student = null;
    }
}
