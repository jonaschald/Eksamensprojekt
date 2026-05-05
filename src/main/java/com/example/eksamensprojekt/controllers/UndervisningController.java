package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.undervisning.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;

public class UndervisningController {

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
    // Gør så listerne viser undervisningsmaterialet
        indskolingData.setItems(DataDeling.indskolingList);
        mellemtrinData.setItems(DataDeling.mellemtrinList);
        udskolingData.setItems(DataDeling.udskolingList);
        konfirmationData.setItems(DataDeling.konfirmationList);

    // Gør så man kan klikke på undervisningsmaterialet til indskoling, og åbne det for at se det
        indskolingData.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Indskoling selected =
                        indskolingData.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    try {
                    // Finder pdf inde i projektet resource mappe og laver en url til den
                        var url = getClass().getResource(selected.getPdfFile());

                    // Åbner filen i computerens standart program
                        Desktop.getDesktop().browse(url.toURI());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Gør så man kan klikke på undervisningsmaterialet til mellemtrin, og åbne det for at se det
        mellemtrinData.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Mellemtrin selected =
                        mellemtrinData.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    try {
                        // Finder pdf inde i projektet resource mappe og laver en url til den
                        var url = getClass().getResource(selected.getPdfFile());

                        // Åbner filen i computerens standart program
                        Desktop.getDesktop().browse(url.toURI());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Gør så man kan klikke på undervisningsmaterialet til udskoling, og åbne det for at se det
        udskolingData.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Udskoling selected =
                        udskolingData.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    try {
                        // Finder pdf inde i projektet resource mappe og laver en url til den
                        var url = getClass().getResource(selected.getPdfFile());

                        // Åbner filen i computerens standart program
                        Desktop.getDesktop().browse(url.toURI());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Gør så man kan klikke på undervisningsmaterialet til konfirmation, og åbne det for at se det
        konfirmationData.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Konfirmation selected =
                        konfirmationData.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    try {
                        // Finder pdf inde i projektet resource mappe og laver en url til den
                        var url = getClass().getResource(selected.getPdfFile());

                        // Åbner filen i computerens standart program
                        Desktop.getDesktop().browse(url.toURI());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

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
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
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

    // Skifter scenen til Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}
