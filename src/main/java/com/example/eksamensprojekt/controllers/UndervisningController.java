package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.undervisning.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class UndervisningController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ListView<PdfItem> indskolingData;

    @FXML
    private ListView<PdfItem> mellemtrinData;

    @FXML
    private ListView<PdfItem> udskolingData;

    @FXML
    private ListView<PdfItem> konfirmationData;

    public void initialize() {
    // Gør så listerne viser undervisningsmaterialet
        indskolingData.setItems(DataDeling.indskolingList);
        mellemtrinData.setItems(DataDeling.mellemtrinList);
        udskolingData.setItems(DataDeling.udskolingList);
        konfirmationData.setItems(DataDeling.konfirmationList);

        // Gør så man kan åbne PDF filerne
        setupPdfOpen(indskolingData);
        setupPdfOpen(mellemtrinData);
        setupPdfOpen(udskolingData);
        setupPdfOpen(konfirmationData);
    }

    // Gør så man kan åbne PDF filerne i computerens standard program
    private void setupPdfOpen(ListView<PdfItem> listView) {
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                PdfItem item = listView.getSelectionModel().getSelectedItem();
                if (item != null) {
                    try {
                        Desktop.getDesktop().open(item.getpdfFile());
                    } catch (IOException e) {
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
