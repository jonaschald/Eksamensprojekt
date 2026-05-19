package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.OmOs;
import com.example.eksamensprojekt.objekter.Undervisningsmateriale;
import com.example.eksamensprojekt.undervisning.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class UndervisningController
{
    @FXML
    private Label adresse;
    @FXML
    private Label telefon;
    @FXML
    private Label email;

    @FXML
    private Label åbningstider;

    @FXML
    private ListView<PdfItem> indskolingData;

    @FXML
    private ListView<PdfItem> mellemtrinData;

    @FXML
    private ListView<PdfItem> udskolingData;

    @FXML
    private ListView<PdfItem> konfirmationData;

    // ObservableList der kan indeholde alle Undervisningsmateriale objekter fra Databasen
    private ObservableList<Undervisningsmateriale> undervisningsmaterialer = FXCollections.observableArrayList();

    // ObservableList der kan indeholde Om Os fra Databasen
    private ObservableList<OmOs> omOsListe = FXCollections.observableArrayList();

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Nulstiller ObservableLister i DataDeling - for at undgå dubletter ved sceneskift
        DataDeling.indskolingList.clear();
        DataDeling.mellemtrinList.clear();
        DataDeling.udskolingList.clear();
        DataDeling.konfirmationList.clear();
        undervisningsmaterialer.clear();

        // Indsætter undervisningsmaterialerne fra hver liste ind i tilhørende Listview
        indskolingData.setItems(DataDeling.indskolingList);
        mellemtrinData.setItems(DataDeling.mellemtrinList);
        udskolingData.setItems(DataDeling.udskolingList);
        konfirmationData.setItems(DataDeling.konfirmationList);

        // Gør så PDF-filerne kan åbnes ved dobbeltklik i alle 4 ListViews
        setupPdfOpen(indskolingData);
        setupPdfOpen(mellemtrinData);
        setupPdfOpen(udskolingData);
        setupPdfOpen(konfirmationData);

        try
        {
            // Henter undervisningsmaterialerne fra Databasen og kommer dem ind i en ObservableList
            dao.hentUndervisningsmateriale(undervisningsmaterialer);

            // Går hvert undervisningsmateriale igennem i en for-løkke
            for (Undervisningsmateriale undervisningsmateriale : undervisningsmaterialer) {

                // Laver undervisningsmaterialerne fra databasen om til et PdfItem objekt
                PdfItem item = new PdfItem(undervisningsmateriale.getTitle(), undervisningsmateriale.getPdf());

                // Kommer objektet ind i den tilhørende ListView
                if (undervisningsmateriale.getMålgruppeId() == 1) {
                    indskolingData.getItems().add(item);
                } else if (undervisningsmateriale.getMålgruppeId() == 2) {
                    mellemtrinData.getItems().add(item);
                } else if (undervisningsmateriale.getMålgruppeId() == 3) {
                    udskolingData.getItems().add(item);
                } else if (undervisningsmateriale.getMålgruppeId() == 4) {
                    konfirmationData.getItems().add(item);
                }
            }

        }
        catch (ExecutionException | InterruptedException e)
        {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i UndervisningsController: " +
                    "Kunne ikke hente undervisningsmaterialer fra databasen");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Undervisningsmaterialerne kunne ikke hentes fra Databasen");
            alert.show();
        }

        // Henter kontaktoplysninger til bundlinjen fra databasen
        try {
            // Henter data fra databasen som et OmOs objekt og ligger det i omOsListe
            dao.hentOmOs(omOsListe);

            // Henter OmOs objektet fra listen
            OmOs omOs = omOsListe.get(0);

            // Sætter data fra objektet ind i de forskellige labels
            adresse.setText(omOs.getAdresse());
            telefon.setText(omOs.getTelefonnummer());
            email.setText(omOs.getEmail());
            åbningstider.setText(omOs.getÅbningstider());

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke hente Om Os fra databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente Om Os fra databasen");
            alert.show();
        }
    }

    // Metode til at brugeren kan åbne et Undervisningsmateriale/PDF ved at dobbelt-klikke på det
    private void setupPdfOpen(ListView<PdfItem> listView)
    {
        // Når brugeren dobbelt-klikker på listen kører denne kode
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2)
            {
                // Henter den Pdf brugeren har klikket på
                PdfItem item = listView.getSelectionModel().getSelectedItem();

                // Hvis brugeren har valgt en Pdf, så åbnes den i computerens standardprogram
                if (item != null) {
                    try {
                        Desktop.getDesktop().open(item.getpdfFile());
                    } catch (IOException e) {
                        // Udskriver fejlen i konsollen
                        System.out.println("Kunne ikke åbne Pdf'en");
                        e.printStackTrace(); // Printer hele fejlen i konsollen

                        // Giver brugeren besked om fejlen
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke åbne Pdf'en");
                        alert.show();
                    }
                }
            }
        });
    }

    // Metode til at brugeren kan åbne Kunsthal Holmens hjemmeside i bundlinjen
    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event)
    {
        try {
            // Åbner hjemmesiden i computerens standardbrowser
            Desktop.getDesktop().browse(new URI("https://kunsthalholmen.dk/"));
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke åbne Kunsthal Holmens hjemmeside");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke åbne Kunsthal Holmens hjemmeside");
            alert.show();
        }
    }

    // Skifter scene til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    // Skifter scenen til Favoritter
    @FXML
    void favoritterKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    // Skifter scenen til Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    // Skifter scenen til Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}