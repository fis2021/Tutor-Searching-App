package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis2021.models.Student;

public class ItemStudentController {

    @FXML
    private Label numeLabel;

    @FXML
    private Label facultateLabel;

    public void setInfo(Student student) {
        facultateLabel.setText(student.getSpecializare());
        numeLabel.setText(student.getNume());
    }

}
