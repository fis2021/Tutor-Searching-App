package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.fis2021.models.Student;

public class StudentListItemController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private HBox hBox;

    @FXML
    private Label numeLabel;

    @FXML
    private Label facultateLabel;

    @FXML
    private Label specializareLabel;

    @FXML
    private AnchorPane removeAnchorPane;

    @FXML
    private TextField removeTextField;

    @FXML
    private Button submitButton;

    @FXML
    private Label removeLabel;


    private Student student;

    public void setInfo(Student student) {
        this.student = student;
        numeLabel.setText(student.getNume());
        facultateLabel.setText(student.getFacultate());
        specializareLabel.setText(student.getSpecializare());
    }

}
