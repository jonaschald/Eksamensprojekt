package com.example.eksamensprojekt.controllers.admin;

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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AdminOmOsController
{
    @FXML
    private Label adresseLabel;
    @FXML
    private Label telefonLabel;
    @FXML
    private Label emailLabel;

    @FXML
    private TextArea adresseTextArea;
    @FXML
    private TextArea telefonTextArea;
    @FXML
    private TextArea emailTextArea;

    @FXML
    private ImageView billedeBund;
    @FXML
    private ImageView billedeMidt;
    @FXML
    private ImageView billedeTop;

    @FXML
    private TextArea omOsTekst;

    @FXML
    private TextArea åbningstiderFelt;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = DAOImplementation.getInstance();

    // ObservableList der kan indeholde Om Os fra Databasen
    private ObservableList<OmOs> omOsListe = FXCollections.observableArrayList();

    // Variabler der bruges til at gemme billeder som binær data (bytes) - så billederne kan sendes til databasen
    private byte[] billedeDataTop;
    private byte[] billedeDataMidt;
    private byte[] billedeDataBund;

    // Kører automatisk når FXML siden åbnes
    public void initialize ()
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
                    OmOs omOs = omOsListe.get(0);

                    // Sætter data fra OmOs objektet ind i de forskellige labels
                    omOsTekst.setText(omOs.getBeskrivelse());
                    adresseTextArea.setText(omOs.getAdresse());
                    telefonTextArea.setText(omOs.getTelefonnummer());
                    emailTextArea.setText(omOs.getEmail());
                    åbningstiderFelt.setText(omOs.getÅbningstider());

                    // Henter billeder fra databasen (binær data bytes) og gemmer dem i hver deres variabel,
                    // så vi kan bruge dem herinde i Controlleren
                    billedeDataTop = omOs.getImage1();
                    billedeDataMidt = omOs.getImage2();
                    billedeDataBund = omOs.getImage3();

                    // Hvis der findes et billede i databasen, bliver billedets byte-data lavet om til et JavaFX billede
                    // som vises i hver deres ImageView
                    if (omOs.getImage1() != null) {
                        billedeTop.setImage(new Image(new ByteArrayInputStream(omOs.getImage1())));
                    }
                    if (omOs.getImage2() != null) {
                        billedeMidt.setImage(new Image(new ByteArrayInputStream(omOs.getImage2())));
                    }
                    if (omOs.getImage3() != null) {
                        billedeBund.setImage(new Image(new ByteArrayInputStream(omOs.getImage3())));
                    }
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

    // Når der klikkes på knappen Gem Om Os, opdateres Om Os oplysningerne i databasen
    @FXML
    void gemOmOsTekst(MouseEvent event)
    {
        gemOmOs();
    }

    // Når der klikkes på knappen Kontakt Oplysninger, opdateres Om Os oplysningerne i databasen
    @FXML
    void gemKontaktOplysninger(MouseEvent event)
    {
        gemOmOs();
    }

    // Når der klikkes på knappen Rediger TopBillede, åbnes der en fileChooser, hvor admin kan indsætte et nyt billede
    @FXML
    void redigerTopBillede(MouseEvent event)
    {
        // Det valgte billede konverteres til byte-data og gemmes i variablen billedeDataTop,
        // hvorefter Om Os opdateres i databasen
        billedeDataTop = redigerBillede(billedeTop);
        gemOmOs();
    }

    // Når der klikkes på knappen Rediger MidtBillede, åbnes der en fileChooser, hvor admin kan indsætte et nyt billede
    @FXML
    void redigerMidtBillede(MouseEvent event)
    {
        // Det valgte billede konverteres til byte-data og gemmes i variablen billedeDataMidt,
        // hvorefter Om Os opdateres i databasen
        billedeDataMidt = redigerBillede(billedeMidt);
        gemOmOs();
    }

    // Når der klikkes på knappen Rediger Bundbillede, åbnes der en fileChooser, hvor admin kan indsætte et nyt billede
    @FXML
    void redigerBundBillede(MouseEvent event)
    {
        // Det valgte billede konverteres til byte-data og gemmes i variablen billedeDataBund,
        // hvorefter Om Os opdateres i databasen
        billedeDataBund = redigerBillede(billedeBund);
        gemOmOs();
    }

    // Når der klikkes på knappen Gem Åbningstider, opdateres Om Os oplysningerne i databasen
    @FXML
    void gemÅbningstider(MouseEvent event)
    {
        gemOmOs();
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

    // Skifter scene til Admin Om samlingen
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

    // Metode der gemmer alle Om Os oplysninger i databasen
    private void gemOmOs()
    {
        try {
            // Opretter et OmOs objekt med alle de oplysninger som Admin har skrevet/lagt ind
            OmOs omOs = new OmOs(1, "Om Os", omOsTekst.getText(), adresseTextArea.getText(),
                    telefonTextArea.getText(), emailTextArea.getText(), åbningstiderFelt.getText(), billedeDataTop,
                    billedeDataMidt, billedeDataBund);

            // Gemmer Om Os oplysningerne i databasen
            dao.opdaterOmOs(omOs);

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke gemme Om Os i databasen");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke gemme Om Os i databasen");
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