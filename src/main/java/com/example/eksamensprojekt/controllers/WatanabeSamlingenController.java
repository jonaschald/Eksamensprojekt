package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class WatanabeSamlingenController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ImageView kunstværk1;

    @FXML
    private ImageView kunstværk10;

    @FXML
    private ImageView kunstværk11;

    @FXML
    private ImageView kunstværk12;

    @FXML
    private ImageView kunstværk13;

    @FXML
    private ImageView kunstværk14;

    @FXML
    private ImageView kunstværk15;

    @FXML
    private ImageView kunstværk16;

    @FXML
    private ImageView kunstværk17;

    @FXML
    private ImageView kunstværk18;

    @FXML
    private ImageView kunstværk19;

    @FXML
    private ImageView kunstværk2;

    @FXML
    private ImageView kunstværk20;

    @FXML
    private ImageView kunstværk21;

    @FXML
    private ImageView kunstværk22;

    @FXML
    private ImageView kunstværk23;

    @FXML
    private ImageView kunstværk24;

    @FXML
    private ImageView kunstværk25;

    @FXML
    private ImageView kunstværk26;

    @FXML
    private ImageView kunstværk27;

    @FXML
    private ImageView kunstværk28;

    @FXML
    private ImageView kunstværk29;

    @FXML
    private ImageView kunstværk3;

    @FXML
    private ImageView kunstværk30;

    @FXML
    private ImageView kunstværk31;

    @FXML
    private ImageView kunstværk32;

    @FXML
    private ImageView kunstværk33;

    @FXML
    private ImageView kunstværk34;

    @FXML
    private ImageView kunstværk35;

    @FXML
    private ImageView kunstværk36;

    @FXML
    private ImageView kunstværk4;

    @FXML
    private ImageView kunstværk5;

    @FXML
    private ImageView kunstværk6;

    @FXML
    private ImageView kunstværk7;

    @FXML
    private ImageView kunstværk8;

    @FXML
    private ImageView kunstværk9;

    @FXML
    private Label kunstværkBeskrivelse1;

    @FXML
    private Label kunstværkBeskrivelse10;

    @FXML
    private Label kunstværkBeskrivelse11;

    @FXML
    private Label kunstværkBeskrivelse12;

    @FXML
    private Label kunstværkBeskrivelse13;

    @FXML
    private Label kunstværkBeskrivelse14;

    @FXML
    private Label kunstværkBeskrivelse15;

    @FXML
    private Label kunstværkBeskrivelse16;

    @FXML
    private Label kunstværkBeskrivelse17;

    @FXML
    private Label kunstværkBeskrivelse18;

    @FXML
    private Label kunstværkBeskrivelse19;

    @FXML
    private Label kunstværkBeskrivelse2;

    @FXML
    private Label kunstværkBeskrivelse20;

    @FXML
    private Label kunstværkBeskrivelse21;

    @FXML
    private Label kunstværkBeskrivelse22;

    @FXML
    private Label kunstværkBeskrivelse23;

    @FXML
    private Label kunstværkBeskrivelse24;

    @FXML
    private Label kunstværkBeskrivelse25;

    @FXML
    private Label kunstværkBeskrivelse26;

    @FXML
    private Label kunstværkBeskrivelse27;

    @FXML
    private Label kunstværkBeskrivelse28;

    @FXML
    private Label kunstværkBeskrivelse29;

    @FXML
    private Label kunstværkBeskrivelse3;

    @FXML
    private Label kunstværkBeskrivelse30;

    @FXML
    private Label kunstværkBeskrivelse31;

    @FXML
    private Label kunstværkBeskrivelse32;

    @FXML
    private Label kunstværkBeskrivelse33;

    @FXML
    private Label kunstværkBeskrivelse34;

    @FXML
    private Label kunstværkBeskrivelse35;

    @FXML
    private Label kunstværkBeskrivelse36;

    @FXML
    private Label kunstværkBeskrivelse4;

    @FXML
    private Label kunstværkBeskrivelse5;

    @FXML
    private Label kunstværkBeskrivelse6;

    @FXML
    private Label kunstværkBeskrivelse7;

    @FXML
    private Label kunstværkBeskrivelse8;

    @FXML
    private Label kunstværkBeskrivelse9;

    @FXML
    private TextField searchField;

    // Skifter scenen til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    @FXML
    void downloadHeleSamlingen(ActionEvent event) {

    }

    // Skifter scenent il Farvoritter
    @FXML
    void favoritterKnap(MouseEvent event) throws IOException {

    }

    @FXML
    void filterAarstalNed(ActionEvent event) {

    }

    @FXML
    void filterAarstalOp(ActionEvent event) {

    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws  IOException {

    }

    // Skifter scenen til Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {

    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {

    }

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/undervisning.fxml");
    }


    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/Forside.fxml");
    }

    @FXML
    void watanabeSamlingenKnap(MouseEvent event) {
    }
}
