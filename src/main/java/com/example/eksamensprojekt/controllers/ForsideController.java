package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.AsyncTask;
import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.OmOs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

public class ForsideController
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
    private ImageView KunsthalHolmenBundBillede;

    @FXML
    private ImageView kunsthalHolmenTopBillede;

    @FXML
    private Label omOsTekst;

    @FXML
    private ImageView watanabeSamlingBillede;

    @FXML
    private Label watanabeSamlingTekst;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = DAOImplementation.getInstance();

    // ObservableList der kan indeholde Om Os fra Databasen
    private ObservableList<OmOs> omOsListe = FXCollections.observableArrayList();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Henter kontaktoplysninger til bundlinjen fra databasen
        AsyncTask.run(
                () -> {
                    try {
                        return dao.hentOmOs();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                result -> {
                    // Set data
                    omOsListe.setAll(result);

                    // Henter OmOs objektet fra listen
                    OmOs omOs = omOsListe.getFirst();

                    // Sætter data fra OmOs objektet ind i de forskellige labels
                    adresse.setText(omOs.getAdresse());
                    telefon.setText(omOs.getTelefonnummer());
                    email.setText(omOs.getEmail());
                    åbningstider.setText(omOs.getÅbningstider());
                },
                error -> {
                    System.out.println("Kunne ikke hente Om Os fra databasen");
                    error.printStackTrace();

                    // Giver brugeren besked om fejlen
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente Om Os fra databasen");
                    alert.show();
                }
        );
    }

    // Skifter scenen til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Login.fxml");
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

    // Skifter scenen til Farvoritter
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
    void omSamlingenKnap(MouseEvent event)  throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    // Skifter scenen til Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }
}