package com.example.eksamensprojekt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class TemaController {

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

    @FXML
    private Button temaKnap;

    @FXML
    private Button temaKnap2;

    @FXML
    private Button temaKnap3;

    @FXML
    private HBox temaKnapper;

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
        maleriNummer.setFont(Font.font("System", FontWeight.BOLD, 20));
        maleriNummer.setTextAlignment(TextAlignment.LEFT);
        maleriNummer.setPrefWidth(290);

        Label maleriTitel = new Label();
        maleriTitel.setText(titel);
        maleriTitel.setFont(Font.font("System", FontWeight.BOLD, 20));
        maleriTitel.setTextAlignment(TextAlignment.LEFT);
        maleriTitel.setPrefWidth(290);

        vBox.getChildren().addAll(maleri, maleriNummer, maleriTitel);

        return vBox; // TODO: Måske skab og return et objekt fra en klasse i stedet for?
    }

    private void tilføjMaleri(String billedSti, String nummer, String titel) {
        // Er der 4 elementer i den nuværende række?
        if (ufyldtRække.getChildren().size() == 4) {
            // Skab ny række
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.TOP_LEFT);
            hBox.setSpacing(50);

            billedeContainer.getChildren().add(hBox); // Tilføj række til listen

            ufyldtRække = hBox;
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

    public void initialize() {
        ufyldtRække = eksempelKolonne;
    }

    @FXML
    void adminKnap(MouseEvent event) {

    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    @FXML
    void favoritterKnap(MouseEvent event) {

    }

    @FXML
    void omOsKnap(MouseEvent event) {

    }

    @FXML
    void omSamlingenKnap(MouseEvent event) {

    }

    @FXML
    void temaFilter(ActionEvent event) {
        tilføjMaleri(Objects.requireNonNull(getClass().getResource("/com/example/eksamensprojekt/Billeder/Billede.png")).toExternalForm(), "test nummer", "test titel");
    }

    @FXML
    void temaerKnap(MouseEvent event) {

    }

    @FXML
    void undervisningKnap(MouseEvent event) {

    }

    @FXML
    void watanabeSamlingenKnap(MouseEvent event) {

    }

    @FXML
    void tilStartSide(MouseEvent mouseEvent) {
    }
}
