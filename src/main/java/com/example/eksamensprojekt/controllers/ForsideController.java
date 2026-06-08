package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Forside;
import com.example.eksamensprojekt.objekter.OmOs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

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
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde Om Os fra Databasen
    private ObservableList<OmOs> omOsListe = FXCollections.observableArrayList();

    // ObservableList der kan indeholde Forside fra Databasen
    private ObservableList<Forside> forsideListe = FXCollections.observableArrayList();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Henter forsiden og kontaktoplysninger til bundlinjen fra databasen
        try {
            // Tømmer listen der indholder Forsiden fra databasen - for at ungå dubletter
            forsideListe.clear();

            // Henter Forside data fra databasen som et Forside objekt og ligger det i forsideListe
            dao.hentForside(forsideListe);

            // Henter Forside objektet fra listen
            Forside forside = forsideListe.get(0);

            // Hvis der findes billeder i databasen, bliver billedernes byte-data lavet om til et JavaFX billede
            // som vises i hver deres ImageView
            if (forside.getBillede_1() != null) {
                watanabeSamlingBillede.setImage(new Image(new ByteArrayInputStream(forside.getBillede_1())));
            }
            if (forside.getBillede_2a() != null) {
                kunsthalHolmenTopBillede.setImage(new Image(new ByteArrayInputStream(forside.getBillede_2a())));
            }
            if (forside.getBillede_2b() != null) {
                KunsthalHolmenBundBillede.setImage(new Image(new ByteArrayInputStream(forside.getBillede_2b())));
            }

            // Sætter data fra Forside objektet ind i de forskellige labels
            watanabeSamlingTekst.setText(forside.getBeskrivelse_1());
            omOsTekst.setText(forside.getBeskrivelse_2());

            // Henter data fra databasen som et OmOs objekt og ligger det i omOsListe til kontaktoplysningerne
            dao.hentOmOs(omOsListe);

            // Henter OmOs objektet fra listen til kontaktoplysningerne
            OmOs omOs = omOsListe.get(0);

            // Sætter data fra objektet ind i de forskellige labels til kontaktoplysningerne
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