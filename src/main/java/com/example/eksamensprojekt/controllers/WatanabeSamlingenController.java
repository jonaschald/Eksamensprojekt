package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class WatanabeSamlingenController {

    SceneManeger sceneManeger = new SceneManeger();

    private ImageView[] billeder;
    private Label[] beskrivelser;

    @FXML private ImageView kunstværk1;
    @FXML private ImageView kunstværk2;
    @FXML private ImageView kunstværk3;
    @FXML private ImageView kunstværk4;
    @FXML private ImageView kunstværk5;
    @FXML private ImageView kunstværk6;
    @FXML private ImageView kunstværk7;
    @FXML private ImageView kunstværk8;
    @FXML private ImageView kunstværk9;
    @FXML private ImageView kunstværk10;
    @FXML private ImageView kunstværk11;
    @FXML private ImageView kunstværk12;
    @FXML private ImageView kunstværk13;
    @FXML private ImageView kunstværk14;
    @FXML private ImageView kunstværk15;
    @FXML private ImageView kunstværk16;
    @FXML private ImageView kunstværk17;
    @FXML private ImageView kunstværk18;
    @FXML private ImageView kunstværk19;
    @FXML private ImageView kunstværk20;
    @FXML private ImageView kunstværk21;
    @FXML private ImageView kunstværk22;
    @FXML private ImageView kunstværk23;
    @FXML private ImageView kunstværk24;
    @FXML private ImageView kunstværk25;
    @FXML private ImageView kunstværk26;
    @FXML private ImageView kunstværk27;
    @FXML private ImageView kunstværk28;
    @FXML private ImageView kunstværk29;
    @FXML private ImageView kunstværk30;
    @FXML private ImageView kunstværk31;
    @FXML private ImageView kunstværk32;
    @FXML private ImageView kunstværk33;
    @FXML private ImageView kunstværk34;
    @FXML private ImageView kunstværk35;
    @FXML private ImageView kunstværk36;

    @FXML private Label kunstværkBeskrivelse1;
    @FXML private Label kunstværkBeskrivelse2;
    @FXML private Label kunstværkBeskrivelse3;
    @FXML private Label kunstværkBeskrivelse4;
    @FXML private Label kunstværkBeskrivelse5;
    @FXML private Label kunstværkBeskrivelse6;
    @FXML private Label kunstværkBeskrivelse7;
    @FXML private Label kunstværkBeskrivelse8;
    @FXML private Label kunstværkBeskrivelse9;
    @FXML private Label kunstværkBeskrivelse10;
    @FXML private Label kunstværkBeskrivelse11;
    @FXML private Label kunstværkBeskrivelse12;
    @FXML private Label kunstværkBeskrivelse13;
    @FXML private Label kunstværkBeskrivelse14;
    @FXML private Label kunstværkBeskrivelse15;
    @FXML private Label kunstværkBeskrivelse16;
    @FXML private Label kunstværkBeskrivelse17;
    @FXML private Label kunstværkBeskrivelse18;
    @FXML private Label kunstværkBeskrivelse19;
    @FXML private Label kunstværkBeskrivelse20;
    @FXML private Label kunstværkBeskrivelse21;
    @FXML private Label kunstværkBeskrivelse22;
    @FXML private Label kunstværkBeskrivelse23;
    @FXML private Label kunstværkBeskrivelse24;
    @FXML private Label kunstværkBeskrivelse25;
    @FXML private Label kunstværkBeskrivelse26;
    @FXML private Label kunstværkBeskrivelse27;
    @FXML private Label kunstværkBeskrivelse28;
    @FXML private Label kunstværkBeskrivelse29;
    @FXML private Label kunstværkBeskrivelse30;
    @FXML private Label kunstværkBeskrivelse31;
    @FXML private Label kunstværkBeskrivelse32;
    @FXML private Label kunstværkBeskrivelse33;
    @FXML private Label kunstværkBeskrivelse34;
    @FXML private Label kunstværkBeskrivelse35;
    @FXML private Label kunstværkBeskrivelse36;

    @FXML private TextField searchField;

    @FXML
    public void initialize() {
        billeder = new ImageView[]{
                kunstværk1, kunstværk2, kunstværk3, kunstværk4,
                kunstværk5, kunstværk6, kunstværk7, kunstværk8,
                kunstværk9, kunstværk10, kunstværk11, kunstværk12,
                kunstværk13, kunstværk14, kunstværk15, kunstværk16,
                kunstværk17, kunstværk18, kunstværk19, kunstværk20,
                kunstværk21, kunstværk22, kunstværk23, kunstværk24,
                kunstværk25, kunstværk26, kunstværk27, kunstværk28,
                kunstværk29, kunstværk30, kunstværk31, kunstværk32,
                kunstværk33, kunstværk34, kunstværk35, kunstværk36
        };

        beskrivelser = new Label[]{
                kunstværkBeskrivelse1, kunstværkBeskrivelse2,
                kunstværkBeskrivelse3, kunstværkBeskrivelse4,
                kunstværkBeskrivelse5, kunstværkBeskrivelse6,
                kunstværkBeskrivelse7, kunstværkBeskrivelse8,
                kunstværkBeskrivelse9, kunstværkBeskrivelse10,
                kunstværkBeskrivelse11, kunstværkBeskrivelse12,
                kunstværkBeskrivelse13, kunstværkBeskrivelse14,
                kunstværkBeskrivelse15, kunstværkBeskrivelse16,
                kunstværkBeskrivelse17, kunstværkBeskrivelse18,
                kunstværkBeskrivelse19, kunstværkBeskrivelse20,
                kunstværkBeskrivelse21, kunstværkBeskrivelse22,
                kunstværkBeskrivelse23, kunstværkBeskrivelse24,
                kunstværkBeskrivelse25, kunstværkBeskrivelse26,
                kunstværkBeskrivelse27, kunstværkBeskrivelse28,
                kunstværkBeskrivelse29, kunstværkBeskrivelse30,
                kunstværkBeskrivelse31, kunstværkBeskrivelse32,
                kunstværkBeskrivelse33, kunstværkBeskrivelse34,
                kunstværkBeskrivelse35, kunstværkBeskrivelse36
        };

        opdaterVisningNormal();
        indlaesBilleder();
    }

    private void indlaesBilleder() {
        var stream = getClass().getResourceAsStream(
                "/com/example/eksamensprojekt/Billeder/watanabe.jpg"
        );

        if (stream == null) {
            System.out.println("Billede blev ikke fundet: watanabe.jpg");
            return;
        }

        Image billede = new Image(stream);

        for (ImageView imageView : billeder) {
            imageView.setImage(billede);
        }
    }

    private void opdaterVisningNormal() {
        for (int i = 0; i < beskrivelser.length; i++) {
            beskrivelser[i].setText("Kunstværk nr. " + (i + 1));
        }
    }

    private void opdaterVisningOmvendt() {
        for (int i = 0; i < beskrivelser.length; i++) {
            beskrivelser[i].setText("Kunstværk nr. " + (36 - i));
        }
    }

    @FXML
    void filterAarstalOp(ActionEvent event) {
        opdaterVisningNormal();
    }

    @FXML
    void filterAarstalNed(ActionEvent event) {
        opdaterVisningOmvendt();
    }

    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://kunsthalholmen.dk/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void downloadHeleSamlingen(ActionEvent event) {
    }

    @FXML
    void favoritterKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    @FXML
    void omOsKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}