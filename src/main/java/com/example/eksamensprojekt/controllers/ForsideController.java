package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ForsideController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ImageView KunsthalHolmenBundBillede;

    @FXML
    private ImageView kunsthalHolmenTopBillede;

    @FXML
    private Label omOsTekst;

    @FXML
    private ImageView watanabeSamlingBillede;

    @FXML
    private Label watanabeSamlingTekst;

    // Skifter scenen til Admin Login
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
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    // Skifter scenen til Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event)  throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    // Skifter scenen til Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }
}
