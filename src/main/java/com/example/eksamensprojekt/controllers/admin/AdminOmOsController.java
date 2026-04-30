package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdminOmOsController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private Label adresse;

    @FXML
    private ImageView billedeBund;

    @FXML
    private ImageView billedeMidt;

    @FXML
    private ImageView billedeTop;

    @FXML
    private Label email;

    @FXML
    private Label omOsTekst;

    @FXML
    private Label telefon;

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }


    // Skifter scene til Admin Om samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Samlingen.fxml");
    }

    @FXML
    void redigerKontaktOplysninger(ActionEvent event) {

    }

    @FXML
    void redigerOmOsTekst(ActionEvent event) {

    }

    // Skifter scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Temaer.fxml");
    }

    // Skifter scenen til Admin Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminUndervisning.fxml");
    }

    // Skifter scenen til Admin Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Admin Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminForside.fxml");
    }
}
