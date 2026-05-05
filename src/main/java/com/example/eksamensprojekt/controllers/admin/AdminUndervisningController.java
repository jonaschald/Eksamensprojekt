package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.*;
import com.example.eksamensprojekt.Undervisning.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AdminUndervisningController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ListView<Indskoling> indskolingData;

    @FXML
    private ListView<Mellemtrin> mellemtrinData;

    @FXML
    private ListView<Udskoling> udskolingData;

    @FXML
    private ListView<Konfirmation> konfirmationData;

    public void initialize() {
        System.out.println("AdminUndervisningController initialized");

        indskolingData.setItems(DataDeling.indskolingList);
        mellemtrinData.setItems(DataDeling.mellemtrinList);
        udskolingData.setItems(DataDeling.udskolingList);
        konfirmationData.setItems(DataDeling.konfirmationList);

    }

    @FXML
    void tilføjUndervisningsmateriale(ActionEvent event) {

    }

    @FXML
    void redigerUndervisningsmatriale(ActionEvent event) {

    }

    @FXML
    void sletUndervisningsmatriale(ActionEvent event) {

    }


    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    // Skifter scenen til Admin Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Om.fxml");
    }

    // Skifter scenen til Admin Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Samlingen.fxml");
    }

    // Skiftet scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Temaer.fxml");
    }


    // Skifter scene til Admin Samlingen
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
