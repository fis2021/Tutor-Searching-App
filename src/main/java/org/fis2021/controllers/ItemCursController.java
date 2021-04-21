package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis2021.models.Lesson;
import org.fis2021.models.Tutor;

public class ItemCursController {
    @FXML
    private Label materieLabel;

    @FXML
    private Label tutorLabel;

    public void setData(Lesson lesson) {
        materieLabel.setText(lesson.getLessonName());
        tutorLabel.setText(lesson.getTutorName());
    }

}
