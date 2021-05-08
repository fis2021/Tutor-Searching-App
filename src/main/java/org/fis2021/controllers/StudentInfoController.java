package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis2021.models.Student;

public class StudentInfoController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label facultyLabel;

    @FXML
    private Label specializationLabel;

    @FXML
    private Label registrationNumberLabel;

    @FXML
    private Label usernameLabel;

    public void setInfo(Student student) {
        nameLabel.setText(student.getNume());
        facultyLabel.setText(student.getFacultate());
        specializationLabel.setText(student.getSpecializare());
        registrationNumberLabel.setText(student.getNrMatricol());
        usernameLabel.setText(student.getUsername());
    }

}
