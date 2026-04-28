package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
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
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/Undervisning.fxml");
    }

    @FXML
    void watanabeSamlingenKnap(MouseEvent event) {

    }

    @FXML
    void rediger(ActionEvent actionEvent) {

    }
}
