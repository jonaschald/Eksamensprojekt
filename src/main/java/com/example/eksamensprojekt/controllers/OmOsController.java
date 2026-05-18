package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.undervisning.DataDeling;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class OmOsController
{

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private Label adresseLabel;
    @FXML
    private Label telefonLabel;
    @FXML
    private Label emailLabel;

    @FXML
    private Label åbningstider;

    @FXML
    private Label adresse;
    @FXML
    private Label telefon;
    @FXML
    private Label email;

    @FXML
    private ImageView billedeBund;
    @FXML
    private ImageView billedeMidt;
    @FXML
    private ImageView billedeTop;

    @FXML
    private Label omOsTekst;

    public void initialize () {
        if (DataDeling.omOsTekst != null) { omOsTekst.setText(DataDeling.omOsTekst); }
        if (DataDeling.omOsTopBilled != null) { billedeTop.setImage(DataDeling.omOsTopBilled); }
        if (DataDeling.omOsMidtBilled != null) { billedeMidt.setImage(DataDeling.omOsMidtBilled); }
        if (DataDeling.omOsBundBilled != null) { billedeBund.setImage(DataDeling.omOsBundBilled); }
        if (DataDeling.omOsÅbningstider != null) { åbningstider.setText(DataDeling.omOsÅbningstider); }

        adresse.textProperty().bind(DataDeling.omOsAdresse2());
        adresseLabel.textProperty().bind(DataDeling.omOsAdresse2());
        telefon.textProperty().bind(DataDeling.omOsTelefon2());
        telefonLabel.textProperty().bind(DataDeling.omOsTelefon2());
        email.textProperty().bind(DataDeling.omOsEmail2());
        emailLabel.textProperty().bind(DataDeling.omOsEmail2());
    }

    // Skifter scenen til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://kunsthalholmen.dk/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Skifter scenen til Farvoritter
    @FXML
    void favoritterKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    // Skifter scenen til Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
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
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/undervisning.fxml");
    }

    // Skifter scenen til Samlingnen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}