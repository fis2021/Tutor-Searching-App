package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.fis2021.models.Student;

public class StudentListItemController {

    @FXML
    private HBox hBox;

    @FXML
    private Label numeLabel;

    @FXML
    private Label facultateLabel;

    @FXML
    private Label specializareLabel;

    private Student student;

    public void setInfo(Student student) {
        this.student = student;
        numeLabel.setText(student.getNume());
        facultateLabel.setText(student.getFacultate());
        specializareLabel.setText(student.getSpecializare());
    }

}
