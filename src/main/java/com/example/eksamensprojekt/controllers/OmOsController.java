package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.OmOs;
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

public class OmOsController
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

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde Om Os Data fra Databasen
    private ObservableList<OmOs> omOsListe = FXCollections.observableArrayList();

    // Kører automatisk når FXML siden åbnes
    public void initialize ()
    {
        try {
            // Henter Om Os data fra databasen som et OmOs objekt og ligger det i omOsListe
            dao.hentOmOs(omOsListe);

            // Henter OmOs objektet fra listen
            OmOs omOs = omOsListe.get(0);

            // Sætter data fra OmOs objektet ind i de forskellige labels
            omOsTekst.setText(omOs.getBeskrivelse());
            adresse.setText(omOs.getAdresse());
            adresseLabel.setText(omOs.getAdresse());
            telefon.setText(omOs.getTelefonnummer());
            telefonLabel.setText(omOs.getTelefonnummer());
            email.setText(omOs.getEmail());
            emailLabel.setText(omOs.getEmail());
            åbningstider.setText(omOs.getÅbningstider());

            // Hvis der findes et billede i databasen, bliver billedets byte-data lavet om til et JavaFX billede
            // som vises i hver deres ImageView
            if(omOs.getImage1() != null) {
                billedeTop.setImage(new Image(new ByteArrayInputStream(omOs.getImage1())));
            }
            if(omOs.getImage2() != null) {
                billedeMidt.setImage(new Image(new ByteArrayInputStream(omOs.getImage2())));
            }
            if(omOs.getImage3() != null) {
                billedeBund.setImage(new Image(new ByteArrayInputStream(omOs.getImage3())));
            }

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i OmOsController: " +
                    "Kunne ikke hente Om Os fra databasen");
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

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/undervisning.fxml");
    }

    // Skifter scenen til Samlingnen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}