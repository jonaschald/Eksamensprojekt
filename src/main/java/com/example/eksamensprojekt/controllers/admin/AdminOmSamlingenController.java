package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.OmSamlingen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AdminOmSamlingenController
{
    @FXML
    private ImageView billedeBund;

    @FXML
    private ImageView billedeTop;

    @FXML
    private TextArea redigerSadaoWatanabeText;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde Om Samlingen data fra Databasen
    private ObservableList<OmSamlingen> omSamlingenListe = FXCollections.observableArrayList();

    // Variabler der bruges til at gemme billeder som binær data (bytes) - så billederne kan sendes til databasen
    private byte[] billedeDataTop;
    private byte[] billedeDataBund;

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        try {
            // Henter Om Samlingen data fra databasen som et OmSamlingen objekt og ligger det i omSamlingenListe
            dao.hentOmSamlingen(omSamlingenListe);

            // Henter OmSamlingen objektet fra listen
            OmSamlingen omSamlingen = omSamlingenListe.get(0);

            // Sætter data fra OmSamlingen objektet ind i vores TextArea
            redigerSadaoWatanabeText.setText(omSamlingen.getBeskrivelse());

            // Henter billeder fra databasen (binær data bytes) og gemmer dem i hver deres variabel,
            // så vi kan bruge dem herinde i Controlleren
            billedeDataTop = omSamlingen.getImage1();
            billedeDataBund = omSamlingen.getImage2();

            // Hvis der findes et billede i databasen, bliver billedets byte-data lavet om til et JavaFX billede
            // som vises i hver deres ImageView
            if (omSamlingen.getImage1() != null) {
                billedeTop.setImage(new Image(new ByteArrayInputStream(omSamlingen.getImage1())));
            }
            if (omSamlingen.getImage2() != null) {
                billedeBund.setImage(new Image(new ByteArrayInputStream(omSamlingen.getImage2())));
            }

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i AdminOmSamlingenController: " +
                    "Kunne ikke hente Om Samlingen fra databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente Om Samlingen fra databasen");
            alert.show();
        }
    }

    @FXML
    void gemOmSadaoWatanabeTekst(ActionEvent event)
    {
        gemOmSamlingen();
    }
    @FXML
    void redigerTopBillede(MouseEvent event)
    {
        // Det valgte billede konverteres til byte-data og gemmes i variablen billedeDataTop,
        // hvorefter Om Samlingen opdateres i databasen
        billedeDataTop = redigerBillede(billedeTop);
        gemOmSamlingen();
    }

    @FXML
    void redigerBundBillede(MouseEvent event)
    {
        // Det valgte billede konverteres til byte-data og gemmes i variablen billedeDataBund,
        // hvorefter Om Samlingen opdateres i databasen
        billedeDataBund = redigerBillede(billedeBund);
        gemOmSamlingen();
    }

    // Skifter scenen til Admin Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Os.fxml");
    }

    // Skifter scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Temaer.fxml");
    }

    // Skifter scenen til Admin Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminUndervisning.fxml");
    }

    // Skifter scenen til Admin Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Admin Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminForside.fxml");
    }

    // Skifter scene til Forsiden
    @FXML
    void logudKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }

    // Metode der gemmer alle Om Samlingen oplysninger i databasen
    private void gemOmSamlingen()
    {
        try {
            // Opretter et OmSamlingen objekt med alle de oplysninger som Admin har skrevet/lagt ind
            OmSamlingen omSamlingen = new OmSamlingen(1, "Om samlingen", redigerSadaoWatanabeText.getText(),
                    billedeDataTop, billedeDataBund);

            // Gemmer Om Samlingen oplysningerne i databasen
            dao.opdaterOmSamlingen(omSamlingen);

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke gemme Om Samlingen i databasen");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke gemme Om Samlingen i databasen");
            alert.show();
        }
    }

    // Metode til at Admin kan vælge et billede fra computeren som så konverteres til bytes til databasen
    private byte[] redigerBillede (ImageView imageView)
    {
        // Opretter en vindue hvor Admin kan vælge en fil fra computeren
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // Finder det JavaFX vindue der kørers nu og åbner fileChooser vinduet deri
        Stage stage = (Stage) imageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage); // Hvis Admin vælger en fil, gemmes den som file

        // Hvis Admin har valgt en fil
        if (file != null)
        {
            // Oprettes der et JavaFX Image med den valgte fil og billedet vises i ImageView
            Image image = new Image (file.toURI().toString());
            imageView.setImage(image);

            // Konvertere billedet til bytes/binær data til databasen
            try {
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace(); // Printer fejlen i konsollen
            }
        }
        return null; // Hvis brugeren annullere fileChooser, så returneres null
    }
}