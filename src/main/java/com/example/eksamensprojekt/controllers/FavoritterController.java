package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.undervisning.DataDeling;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
import java.io.IOException;
import java.net.URI;

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
    private VBox billedeContainer;

    @FXML
    private VBox eksempel;

    @FXML
    private ImageView eksempelBillede;

    @FXML
    private HBox eksempelKolonne;

    @FXML
    private Label eksempelNummer;

    @FXML
    private Label eksempelTitel;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Skab rækker og kolonner af malerier
    private HBox ufyldtRække;

    // Skab et nyt maleri
    private VBox nyMaleriKnap(String billedSti, String nummer, String titel) {
        VBox vBox = new VBox();
        vBox.setPrefSize(290, 417); // taget fra eksemplet i scenebuilder
        vBox.setAlignment(Pos.CENTER_LEFT);

        ImageView maleri = new ImageView();
        maleri.setPreserveRatio(true);
        maleri.setFitWidth(290); // 290px bredte er max størrelse
        //maleri.setFitHeight(357);

        maleri.setImage(new Image(billedSti));

        // Oplysninger
        Label maleriNummer = new Label();
        maleriNummer.setText(nummer);
        maleriNummer.setFont(javafx.scene.text.Font.font("System", FontWeight.BOLD, 20));
        maleriNummer.setTextAlignment(TextAlignment.LEFT);
        maleriNummer.setPrefWidth(290);

        Label maleriTitel = new Label();
        maleriTitel.setText(titel);
        maleriTitel.setFont(Font.font("System", FontWeight.BOLD, 20));
        maleriTitel.setTextAlignment(TextAlignment.LEFT);
        maleriTitel.setPrefWidth(290);

        vBox.getChildren().addAll(maleri, maleriNummer, maleriTitel);

        return vBox;
    }

    private void nyRække() {
        // Skab ny række
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.setSpacing(50);

        billedeContainer.getChildren().add(hBox); // Tilføj række til listen

        ufyldtRække = hBox;
    }

    public void initialize() {
        // Så bundlinjen viser de informationer man som Admin sætter i Om Os
        adresse.textProperty().bindBidirectional(DataDeling.omOsAdresse2());
        telefon.textProperty().bindBidirectional(DataDeling.omOsTelefon2());
        email.textProperty().bindBidirectional(DataDeling.omOsEmail2());
        if (DataDeling.omOsÅbningstider != null) { åbningstider.setText(DataDeling.omOsÅbningstider); }

        if (!billedeContainer.getChildren().isEmpty()) {
            ufyldtRække = (HBox) billedeContainer.getChildren().getFirst();
        }

        // tilføjMaleri(Objects.requireNonNull(getClass().getResource("/com/example/eksamensprojekt/Billeder/Billede.png")).toExternalForm(), "test nummer", "test titel");
    }

    private void tilføjMaleri(String billedSti, String nummer, String titel) {
        // Findes der en række?
        if (ufyldtRække == null) {
            nyRække();
        }

        // Er der 4 elementer i den nuværende række?
        if (ufyldtRække.getChildren().size() == 4) {
            nyRække();
        }

        // Tilføj maleriet til den nuværende række
        VBox maleri = nyMaleriKnap(billedSti, nummer, titel);
        ufyldtRække.getChildren().add(maleri);

        maleri.setOnMouseClicked(this::visMaleri);
    }

    private void visMaleri(MouseEvent event) {
        System.out.println("Brugeren har trykket på et maleri!");

        VBox container = (VBox) event.getSource();
        ImageView billede = (ImageView) container.getChildren().getFirst();
        Label nummer = (Label) container.getChildren().get(1);
        Label titel = (Label) container.getChildren().get(2);

        System.out.println(billede);
        System.out.println(nummer);
        System.out.println(titel);

    }

    // Skifter scenen til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://kunsthalholmen.dk/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    // Skifter scenen til Om samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    // Skifter scenen til Samlingne
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}