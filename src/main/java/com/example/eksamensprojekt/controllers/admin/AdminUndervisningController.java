package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdminUndervisningController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ListView<?> indskoling;

    @FXML
    private ListView<?> konfirmation;

    @FXML
    private ListView<?> mellemtrin;

    @FXML
    private Button redigerUndervisningsmateriale;

    @FXML
    private ListView<?> udskoling;

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    // Skifter scenen til Admin Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {

    }

    // Skifter scenen til Admin Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {

    }

    // Skiftet scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {

    }

    // Skifter scenen til Admin Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/AdminUndervisning.fxml");
    }

    // Skifter scene til Admin Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/Admin-Watanabe-samlingen.fxml");
    }

    @FXML
    void favoritterKnap(MouseEvent mouseEvent) {
    }

    @FXML
    void adminKnap(MouseEvent mouseEvent) {
    }
}
