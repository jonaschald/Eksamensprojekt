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

    // Skifter scene til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    // Skifter scenen til Farvoritter
    @FXML
    void favoritterKnap(MouseEvent event) throws IOException {

    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {

    }

    // Skifter scenen til Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {

    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {

    }

    // Skifter scenen til Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/Watanabe-samlingen.fxml");
    }

    @FXML
    void undervisningKnap(MouseEvent event) {
    }
}
