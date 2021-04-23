package org.fis2021.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.controlsfx.control.Rating;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.models.Lesson;
import org.fis2021.models.Student;
import org.fis2021.models.Tutor;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.LessonService;
import org.fis2021.services.StudentHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TutorInfoItemController implements Initializable {
    @FXML
    private Label nameLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private HBox ratingView;

    @FXML
    private Rating ratingStars;

    @FXML
    private Button submitButton;

    @FXML
    private ListView<HBox> listView;

    private ArrayList<Lesson> lessonArrayList = new ArrayList<>();
    private Tutor tutor;

    public void setData(Tutor tutor) throws IOException {
        this.tutor = tutor;
        StudentHolder studentHolder = StudentHolder.getInstance();
        Student student = studentHolder.getStudent();
        lessonArrayList = LessonService.getAllLessons();
        for (Lesson lesson : lessonArrayList){
            if (lesson.getTutorName().equals(tutor.getNume()) && lesson.getStudentName().equals("") && lesson.getStatus().equals("")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/lessonitem.fxml"));
                HBox hBox = loader.load();
                LessonItemController lessonItemController = loader.getController();
                lessonItemController.setData(lesson);
                listView.getItems().add(hBox);
                if(LessonService.findStudentInLesson(student.getUsername(), lesson) == true){
                    ratingView.setVisible(true);
                }
            }
        }
        nameLabel.setText(tutor.getNume());
        subjectLabel.setText(tutor.getMaterie());
        departmentLabel.setText(tutor.getSpecializare());
        usernameLabel.setText(tutor.getUsername());
        if(tutor.getCntRating() == 0) {
            ratingLabel.setText("0");
        }else {
            ratingLabel.setText(String.valueOf(tutor.getRating()/tutor.getCntRating()));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ratingView.setVisible(false);
        final double[] rating = new double[1];
        ratingStars.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                rating[0] = t1.doubleValue();
            }
        });
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tutor.setRating(tutor.getRating() + rating[0]);
                tutor.setCntRating(tutor.getCntRating() + 1);
                ObjectRepository<Tutor> tutorObjectRepository = DatabaseService.getDatabase().getRepository(Tutor.class);
                tutorObjectRepository.update(tutor);
            }
        });
    }
}
