package org.fis2021.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.fis2021.models.Lesson;
import org.fis2021.models.Tutor;
import org.fis2021.services.LessonService;
import org.fis2021.services.TutorService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TutorInfoItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private ListView<HBox> listView;

    private ArrayList<Lesson> lessonArrayList = new ArrayList<>();

    public void setData(Tutor tutor) throws IOException {
        lessonArrayList = LessonService.getAllLessons();
        for (Lesson lesson : lessonArrayList){
            if (lesson.getTutorName().equals(tutor.getNume())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/lessonitem.fxml"));
                HBox hBox = loader.load();
                LessonItemController lessonItemController = loader.getController();
                lessonItemController.setData(lesson);
                listView.getItems().add(hBox);
            }
        }
        nameLabel.setText(tutor.getNume());
        subjectLabel.setText(tutor.getMaterie());
        departmentLabel.setText(tutor.getSpecializare());
        usernameLabel.setText(tutor.getUsername());
    }


}
