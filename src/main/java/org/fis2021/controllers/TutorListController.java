package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fis2021.models.Student;
import org.fis2021.models.Tutor;
import org.fis2021.services.StudentHolder;
import org.fis2021.services.TutorService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.fis2021.App.loadFXML;

public class TutorListController implements Initializable {
    @FXML
    private TextField searchTextField;

    @FXML
    private Label tutorNotFoundLabel;

    @FXML
    private VBox vBox;

    @FXML
    private HBox headerHBox;

    @FXML
    private ScrollPane scrollPane;

    private ArrayList<Tutor> tutorArrayList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTutors();
        StudentHolder studentHolder = StudentHolder.getInstance();
        Student student = studentHolder.getStudent();
        System.out.println(student.getNume());
    }

    void addTutors(){
        try {
            headerHBox.setVisible(true);
            tutorArrayList = TutorService.getAllTutors();

            for (Tutor tutor : tutorArrayList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/tutorlistitem.fxml"));
                HBox hBox = loader.load();
                hBoxEventHandler(hBox, tutor);
                TutorListItemController tutorListItemController = loader.getController();
                tutorListItemController.setData(tutor);
                vBox.getChildren().add(hBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadTutorInfoItem(Tutor tutor) {
        try {
            scrollPane.setVvalue(0);
            headerHBox.setVisible(false);
            vBox.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/tutorinfoitem.fxml"));
            VBox tutorInfoVBox = loader.load();
            TutorInfoItemController tutorInfoItemController = loader.getController();
            tutorInfoItemController.setData(tutor);
            vBox.getChildren().add(tutorInfoVBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchButtonPressed() {
        try {
            scrollPane.setVvalue(0);
            tutorNotFoundLabel.setVisible(false);
            headerHBox.setVisible(true);
            if (searchTextField.getText() != null) {
                vBox.getChildren().clear();
                tutorArrayList = TutorService.getAllTutors();
                int cnt = 1;
                for (Tutor tutor : tutorArrayList) {
                    if ((tutor.getNume().toLowerCase()).indexOf(searchTextField.getText().toLowerCase()) == 0 || (tutor.getNume().toLowerCase().substring(tutor.getNume().indexOf(" ") + 1)).indexOf(searchTextField.getText()) == 0) {
                        cnt = 0;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/tutorlistitem.fxml"));
                        HBox hBox = loader.load();
                        hBoxEventHandler(hBox, tutor);
                        TutorListItemController tutorListItemController = loader.getController();
                        tutorListItemController.setData(tutor);
                        vBox.getChildren().add(hBox);
                    }
                }
                if (cnt == 1) {
                    tutorNotFoundLabel.setVisible(true);
                    tutorNotFoundLabel.setText("Tutor not found");
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    void hBoxEventHandler(HBox hBox, Tutor tutor){
        hBox.setOnMouseClicked(mouseEvent -> loadTutorInfoItem(tutor));
    }

    @FXML
    void switchToHomeStudent() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homepageStudent"), 1280, 720);
        stage.setTitle("Tutor Searching App - Home Page Student");
        stage.setScene(scene);
    }

    @FXML
    void switchToTutors() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("tutorlist"), 1280, 720);
        stage.setTitle("Tutor Searching App - Tutor List");
        stage.setScene(scene);
    }

    @FXML
    void switchToCalendar() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        Scene scene = new Scene(loadFXML("calendar"), 1280, 720);
        stage.setTitle("Tutor Searching App - Calendar");
        stage.setScene(scene);
    }
}
