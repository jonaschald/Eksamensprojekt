package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.*;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
import com.example.eksamensprojekt.objekter.OmOs;
import com.example.eksamensprojekt.objekter.Tema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

public class TemaController
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
    private VBox billedeContainer;

    @FXML
    private HBox temaKnapper;

    // Opretter et sceneManager objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManager = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde Om Os fra Databasen
    private ObservableList<OmOs> omOsListe = FXCollections.observableArrayList();

    // Liste der kan indeholde alle kunstværker fra Databasen
    private ObservableList<Kunstværk> kunstværker = FXCollections.observableArrayList();

    // Liste der kan indeholde Temaerne
    private ObservableList<Tema> temaer = FXCollections.observableArrayList();

    // Variabel der holder styr på den nuværende række af kunstværker
    private HBox ufyldtRække;

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Henter temaerne fra databasen og gemmer dem i ObservableList temaer og viser temaknapperne
        try {
            dao.hentAlleTemaer(temaer);
            visTemaKnapper();
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke hente Temaer fra databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente Temaer fra databasen");
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

    // Skaber et nyt kunstværk-kort med billede, nummer og titel
    private VBox nyMaleriKnap(Kunstværk kunstværk)
    {
        // Opretter en VBox til kunstværk-kortet
        VBox vBox = new VBox();

        // Sætter størrelse på boksen
        vBox.setPrefSize(290, 417);

        // Placerer indholdet øverst til venstre
        vBox.setAlignment(Pos.CENTER_LEFT);

        // Opretter ImageView til kunstværket
        ImageView maleri = new ImageView();

        maleri.setPreserveRatio(true);

        // Maksimal bredde på billedet
        maleri.setFitWidth(290);

        // Indlæser billedet
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(kunstværk.getBilledeData());
        Image image = new Image(byteArrayInputStream);
        maleri.setImage(image);

        // Opretter label til kunstværkets nummer
        Label maleriNummer = new Label();
        maleriNummer.setText(kunstværk.getId());
        maleriNummer.setFont(Font.font("System", FontWeight.BOLD, 20));
        maleriNummer.setTextAlignment(TextAlignment.LEFT);
        maleriNummer.setPrefWidth(290);

        // Opretter label til kunstværkets titel
        Label maleriTitel = new Label();
        maleriTitel.setText(kunstværk.getTitel());
        maleriTitel.setFont(Font.font("System", FontWeight.BOLD, 20));
        maleriTitel.setTextAlignment(TextAlignment.LEFT);
        maleriTitel.setPrefWidth(290);

        // Tilføjer billede og labels til VBoxen
        vBox.getChildren().addAll(maleri, maleriNummer, maleriTitel);

        // Returnere VBoxen med kunstværkets billede og labels
        return vBox;
    }

    // Opretter en ny række til kunstværkerne
    private void nyRække()
    {
        // Opretter en ny HBox
        HBox hBox = new HBox();

        // Placering af indholdet
        hBox.setAlignment(Pos.TOP_LEFT);

        // Afstand mellem kunstværkerne
        hBox.setSpacing(50);

        // Tilføjer rækken til containeren
        billedeContainer.getChildren().add(hBox);

        // Gemmer rækken som den aktuelle række
        ufyldtRække = hBox;
    }

    // Tilføjer et kunstværk til den nuværende række
    private void tilføjMaleri(Kunstværk kunstværk)
    {
        // Hvis der endnu ikke findes en række, oprettes en ny
        if (ufyldtRække == null) {
            nyRække();
        }

        // Hvis rækken allerede har 4 kunstværker, oprettes en ny række
        if (ufyldtRække.getChildren().size() == 4) {
            nyRække();
        }

        // Opretter et nyt kunstværk-kort
        VBox maleri = nyMaleriKnap(kunstværk);

        // Tilføjer kunstværket til rækken
        ufyldtRække.getChildren().add(maleri);

        // Gør kunstværket klikbart
        maleri.setOnMouseClicked(event ->
        {
            // Gemmer det valgte kunstværkm så brugeren se det i Pop-up'en
            PopupController.valgtKunstværk = kunstværk;
            // Gemmer listen af kunstværker, så brugeren kan navigere frem og tilbage i Pop-up'en
            PopupController.setKunstværker(kunstværker);

            try {
                // Skifter til Pop-up siden, så brugeren kan se kunstværket i større format
                sceneManager.skiftSceneTilbage(event,
                        "/com/example/eksamensprojekt/gui/Temaer.fxml",
                        "/com/example/eksamensprojekt/gui/Pop-Up.fxml");
            } catch (IOException e) {
                // Udskriver fejlen i konsollen
                System.out.println("Kunne ikke vise kunstværket på Pop-up siden");
                e.printStackTrace(); // Printer hele fejlen i konsollen

                // Giver brugeren besked om fejlen
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke vise kunstværket på Pop-up siden");
                alert.show();
            }
        });
    }

    // Metode der opretter og viser en knap for hvert tema
    public void visTemaKnapper()
    {
        // Fjerner alle elementer i vores HBox - for at undgå dubletter
        temaKnapper.getChildren().clear();

        // Kører alle temaerne fra databasen igennem og laver en knap til hver
        for (Tema tema : temaer)
        {
            Button temaKnap = new Button();
            temaKnap.setText(tema.getNavn()); // Tema navnet sættes på knappen

            temaKnap.setPrefWidth(230); // Knappens bredde
            temaKnap.setPrefHeight(70); // Knappens højde

            // Sætter tekstens størrelse og tykkelse
            temaKnap.setFont(Font.font("System", FontWeight.NORMAL, 22));

            // Når brugeren klikker på knappen vises alle kunstværkerne under det tema
            temaKnap.setOnMouseClicked((event) -> {
                visTema(tema);
            });

            // Tilføjer knappen til HBoxen med tema knapper
            temaKnapper.getChildren().add(temaKnap);
        }
    }

    // Viser alle kunstværker der tilhører det valgte tema
    public void visTema(Tema tema)
    {
        try {
            // Fjerner gamle billeder - for at undgå dubletter
            billedeContainer.getChildren().clear();

            // Nulstiller rækken
            ufyldtRække = null;

            // Tømmer listen med kunstværker
            kunstværker.clear();

            // Henter kunstværkerne der tilhører temaet fra databasen og sætter dem ind i listen kunstværker
            dao.hentKunstværkerEfterTema(tema.getId(), kunstværker);

            // Kører listen med kunstværker igennem og viser dem
            for (Kunstværk kunstværk : kunstværker) {
                tilføjMaleri(kunstværk);
            }

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke vise Temaer fra databasen");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke vise Temaer fra databasen");
            alert.show();
        }
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

    // Skifter scenen til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException
    {
        sceneManager.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    // Skifter scenen til Favoritter
    @FXML
    void favoritterKnap(MouseEvent event) throws IOException
    {
        sceneManager.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManager.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    // Skifter scenen til Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManager.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException
    {
        sceneManager.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    // Skifter scenen til Watanabe-samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManager.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }

    // Skifter scenen tilbage til Forsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException
    {
        sceneManager.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}