package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdminForsideController {

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

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    // Skifter scenen til Admin Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Admin-Om-Os.fxml");
    }

    // Skifter scenen til Admin Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Admin-Om-Samlingen.fxml");

    }

    @FXML
    void rediger(ActionEvent event) {

    }

    // Skifter scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Admin-Temaer.fxml");
    }

    // Skifter scenen til Admin Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/AdminUndervisning.fxml");
    }

    // Skifter scenen til Admin Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/Admin-Watanabe-samlingen.fxml");
    }

}
