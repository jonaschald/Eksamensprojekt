package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Undervisningsmateriale;
import com.example.eksamensprojekt.undervisning.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class UndervisningController {

    SceneManeger sceneManeger = new SceneManeger();

    DAO dao = new DAOImplementation();

    @FXML
    private ListView<PdfItem> indskolingData;

    @FXML
    private ListView<PdfItem> mellemtrinData;

    @FXML
    private ListView<PdfItem> udskolingData;

    @FXML
    private ListView<PdfItem> konfirmationData;

    private ObservableList<Undervisningsmateriale> undervisningsmaterialer = FXCollections.observableArrayList();

    public void initialize() {
        DataDeling.indskolingList.clear();
        DataDeling.mellemtrinList.clear();
        DataDeling.udskolingList.clear();
        DataDeling.konfirmationList.clear();
        undervisningsmaterialer.clear();

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

        // Henter undervisningsmaterialerne fra databasen når programmet køres og kommer dem ind i de tilhørende ListViews
        try {
            dao.hentUndervisningsmateriale(undervisningsmaterialer);

            for(Undervisningsmateriale undervisningsmateriale : undervisningsmaterialer) {
                PdfItem item = new PdfItem(undervisningsmateriale.getTitle(), undervisningsmateriale.getPdf());

                switch (undervisningsmateriale.getMålgruppeId())  {
                    case 1:
                        indskolingData.getItems().add(item);
                        break;
                    case 2:
                        mellemtrinData.getItems().add(item);
                        break;
                    case 3:
                        udskolingData.getItems().add(item);
                        break;
                    case 4:
                        konfirmationData.getItems().add(item);
                        break;
                }
            }

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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