package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.OmSamlingen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

public class OmSamlingenController
{
    @FXML
    private Label adresseLabel;
    @FXML
    private Label telefonLabel;
    @FXML
    private Label emailLabel;

    @FXML
    private Label åbningstider;

    @FXML
    private ImageView billedeBund;
    @FXML
    private ImageView billedeTop;

    @FXML
    private Label omSadaoWatanabeTekst;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde Om Samlingen data fra Databasen
    private ObservableList<OmSamlingen> omSamlingenListe = FXCollections.observableArrayList();


    // Kører automatisk når FXML siden åbnes
    @FXML
    public void initialize()
    {
        try {
            // Henter Om Samlingen data fra databasen som et OmSamlingen objekt og ligger det i omSamlingenListe
            dao.hentOmSamlingen(omSamlingenListe);

            // Henter OmSamlingen objektet fra listen
            OmSamlingen omSamlingen = omSamlingenListe.get(0);

            // Sætter data fra OmSamlingen objektet ind i de forskellige labels
            omSadaoWatanabeTekst.setText(omSamlingen.getBeskrivelse());

            // Hvis der findes et billede i databasen, bliver billedets byte-data lavet om til et JavaFX billede
            // som vises i hver deres ImageView
            if(omSamlingen.getImage1() != null) {
                billedeTop.setImage(new Image(new ByteArrayInputStream(omSamlingen.getImage1())));
            }
            if(omSamlingen.getImage2() != null) {
                billedeBund.setImage(new Image(new ByteArrayInputStream(omSamlingen.getImage2())));
            }

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i OmSamlingenController: " +
                    "Kunne ikke hente Om Samlingen fra databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente Om Samlingen fra databasen");
            alert.show();
        }

    }

    // Skifter scenen til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Login.fxml");
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

    // Skifter scenen til Favoritter
    @FXML
    void favoritterKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    // Skifter scenen til Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }
}