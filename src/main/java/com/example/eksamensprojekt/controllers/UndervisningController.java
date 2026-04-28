package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UndervisningController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ListView<?> indskoling;

    @FXML
    private ListView<?> konfirmation;

    @FXML
    private ListView<?> mellemtrin;

    @FXML
    private ListView<?> udskoling;

    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    @FXML
    void favoritterKnap(MouseEvent event) {

    }

    @FXML
    void omOsKnap(MouseEvent event) {

    }

    @FXML
    void omSamlingenKnap(MouseEvent event) {

    }

    @FXML
    void temaerKnap(MouseEvent event) {

    }

    @FXML
    void undervisningKnap(MouseEvent event) {

    }

    @FXML
    void watanabeSamlingenKnap(MouseEvent event) {

    }

}
