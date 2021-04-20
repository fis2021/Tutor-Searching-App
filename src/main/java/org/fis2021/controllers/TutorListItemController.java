package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.fis2021.models.Tutor;

public class TutorListItemController {
    @FXML
    private Label numeLabel;

    @FXML
    private Label materieLabel;

    @FXML
    private Label specializareLabel;

    @FXML
    private HBox hBox;

    private Tutor tutor;

    public void setData(Tutor tutor) {
        this.tutor = tutor;
        numeLabel.setText(tutor.getNume());
        materieLabel.setText(tutor.getMaterie());
        specializareLabel.setText(tutor.getSpecializare());
    }
}
