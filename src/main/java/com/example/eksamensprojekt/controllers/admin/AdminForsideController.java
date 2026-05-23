package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Forside;
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

public class AdminForsideController
{
    @FXML
    private ImageView KunsthalHolmenBundBillede;

    @FXML
    private ImageView kunsthalHolmenTopBillede;

    @FXML
    private TextArea watanabeSamlingTekst;

    @FXML
    private TextArea omOsTekst;

    @FXML
    private ImageView watanabeSamlingBillede;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde Forside data fra Databasen
    private ObservableList<Forside> forsideListe = FXCollections.observableArrayList();

    // Variabler der bruges til at gemme billeder som binær data (bytes) - så billederne kan sendes til databasen
    private byte[] billede1;
    private byte[] billede2a;
    private byte[] billede2b;

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        try {
            // Henter Forside data fra databasen som et Forside objekt og ligger det i forsideListe
            dao.hentForside(forsideListe);

            // Henter Forside objektet fra listen
            Forside forside = forsideListe.get(0);

            // Sætter data fra Forside objektet ind de 2 TextArea
            watanabeSamlingTekst.setText(forside.getBeskrivelse_1());
            omOsTekst.setText(forside.getBeskrivelse_2());

            // Henter billeder fra databasen (binær data bytes) og gemmer dem i hver deres variabel,
            // så vi kan bruge dem herinde i Controlleren
            billede1 = forside.getBillede_1();
            billede2a = forside.getBillede_2a();
            billede2b = forside.getBillede_2b();

            // Hvis der findes et billede i databasen, bliver billedets byte-data lavet om til et JavaFX billede
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

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i AdminForsideController: " +
                    "Kunne ikke hente Forsiden fra databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente Forsiden fra databasen");
            alert.show();
        }

    }

    // Metode der gemmer alle Forside oplysninger i databasen
    private void gemForsiden()
    {
        try {
            // Opretter et Forside objekt med alle de oplysninger som Admin har skrevet/lagt ind
            Forside forside = new Forside(1, "Watanabe-samlingen", watanabeSamlingTekst.getText(),
                    billede1, "Kunsthal Holmen", omOsTekst.getText(), billede2a, billede2b);

            // Gemmer Forside oplysningerne i databasen
            dao.opdaterForside(forside);

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke gemme Forsiden i databasen");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke gemme Forsiden i databasen");
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

    // Når Admin klikker på rediger knappen gemmes tekst ændringerne i databasen
    @FXML
    void rediger(ActionEvent event)
    {
        gemForsiden();
    }

    @FXML
    void redigerBundBillede(ActionEvent event)
    {
        // Det valgte billede konverteres til byte-data og gemmes i variablen billede2b,
        // hvorefter Forsiden opdateres i databasen
        billede2b = redigerBillede(KunsthalHolmenBundBillede);
        gemForsiden();
    }

    @FXML
    void redigerMidtBillede(ActionEvent event)
    {
        // Det valgte billede konverteres til byte-data og gemmes i variablen billede2a,
        // hvorefter Forsiden opdateres i databasen
        billede2a = redigerBillede(kunsthalHolmenTopBillede);
        gemForsiden();
    }

    @FXML
    void redigerTopBillede(ActionEvent event)
    {
        // Det valgte billede konverteres til byte-data og gemmes i variablen billede1,
        // hvorefter Forsiden opdateres i databasen
        billede1 = redigerBillede(watanabeSamlingBillede);
        gemForsiden();
    }


    // Skifter scenen til Admin Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Os.fxml");
    }

    // Skifter scenen til Admin Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Samlingen.fxml");
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

    // Skifter scene til Forsiden
    @FXML
    void logudKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}