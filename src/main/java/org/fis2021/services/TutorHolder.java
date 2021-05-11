package org.fis2021.services;

import org.fis2021.models.Tutor;

public class TutorHolder {
    private static volatile TutorHolder instance = null;
    private static Tutor tutor;

    private TutorHolder(){}

    public static TutorHolder getInstance(){
        if(instance == null){
            instance = new TutorHolder();
        }
        return instance;
    }

    public void setTutor(Tutor tutor){
        TutorHolder.tutor = tutor;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void resetTutor() {
        this.instance = null;
        this.tutor = null;
    }
}
