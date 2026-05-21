package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
import com.example.eksamensprojekt.objekter.OmOs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class FavoritterController
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
    private GridPane billedeContainer;

    // ObservableList der kan indeholde alle kunstværker som er sat til favoritter i Databasen
    private ObservableList<Kunstværk> favoritter = FXCollections.observableArrayList();

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde Om Os fra Databasen
    private ObservableList<OmOs> omOsListe = FXCollections.observableArrayList();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Henter favoritkunstværkerne fra databasen, gemmer dem i listen favoritter og viser dem på siden
        try {
            dao.hentFavoritter(favoritter);
            visFavoritter(favoritter);
        } catch (ExecutionException | InterruptedException e) {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i FavoritterController: " +
                    "Kunne ikke hente favoritter fra databasen");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente favoritter fra databasen");
            alert.show();
        }

        // Henter kontaktoplysninger til bundlinjen fra databasen
        try {
            // Henter Om Os data fra databasen som et OmOs objekt og ligger det i omOsListe
            dao.hentOmOs(omOsListe);

            // Henter OmOs objektet fra listen
            OmOs omOs = omOsListe.get(0);

            // Sætter data fra OmOs objektet ind i de forskellige labels
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

    // Metode til at vise alle favorit-kunstværkerne fra databasen i et GridPane
    public void visFavoritter(ObservableList<Kunstværk> kunstværker)
    {
        // Fjerner alle elementer i vores GridPane - for at undgå dubletter
        billedeContainer.getChildren().clear();

        // Afstand mellem elementerne i GridPane
        billedeContainer.setHgap(40);
        billedeContainer.setVgap(40);

        // Bredden på vores GridPane
        billedeContainer.setPrefWidth(1400);

        // Opretter 2 variabler der holder styr på hvor kunstværkerne placeres i GridPane
        int kolonne = 0;
        int række = 0;

        // Går alle kunstværkerne igennem og sætter hvert kunstværk op i GridPane
        for (Kunstværk kunstværk : kunstværker)
        {
            // Opretter en VBox til at indeholde billedet og informationstekst
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setPrefWidth(290);
            vBox.setAlignment(Pos.TOP_LEFT);

            // Henter billedet/kunstværket fra Databasen
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(kunstværk.getBilledeData());

            // Opretter et JavaFX billede ud fra den binær billede data
            javafx.scene.image.Image image = new Image(byteArrayInputStream);

            // Opretter et ImageView der kan vise billedet på skærmen
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(290);
            imageView.setFitHeight(390);
            imageView.setPreserveRatio(true);

            // Gør billedet klikbart for brugeren
            imageView.setStyle("-fx-cursor: hand;");

            // Når brugeren klikker på billedet, sendes informationerne videre til Pop-up siden
            imageView.setOnMouseClicked(event -> {

                // Gemmer det valgte kunstværk
                PopupController.valgtKunstværk = kunstværk;

                // Sender listen med kunstværker til PopupController
                // så brugeren kan navigere mellem billederne
                PopupController.setKunstværker(kunstværker);

                try {
                    // Skifter til Pop-up siden, hvor kunstværket vises i større format
                    sceneManeger.skiftSceneTilbage(event,
                            "/com/example/eksamensprojekt/gui/Favoritter.fxml",
                            "/com/example/eksamensprojekt/gui/Pop-Up.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Opretter labels med nummer, titel og årstal
            Label nummer = new Label(kunstværk.getId());
            Label titel = new Label(kunstværk.getTitel() + " - " + kunstværk.getÅrstal());
            titel.setWrapText(true);

            // Tilføjer billedet og labels i VBoxen
            vBox.getChildren().addAll(imageView, nummer, titel);

            // Tilføjer VBoxen i GridPane
            billedeContainer.add(vBox, kolonne, række);

            // Hopper til næste kolonne
            kolonne = kolonne + 1;

            // Når der er 4 kunstværker på en række
            if (kolonne == 4) {
                kolonne = 0;
                række = række + 1;
            }
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

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    // Skifter scenen til Om samlingen
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
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    // Skifter scenen til Samlingne
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