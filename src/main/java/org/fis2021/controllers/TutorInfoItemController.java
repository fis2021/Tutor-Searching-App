package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis2021.models.Tutor;

public class TutorInfoItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label usernameLabel;

    public void setData(Tutor tutor) {
        nameLabel.setText(tutor.getNume());
        subjectLabel.setText(tutor.getMaterie());
        departmentLabel.setText(tutor.getSpecializare());
        usernameLabel.setText(tutor.getUsername());
    }
}
