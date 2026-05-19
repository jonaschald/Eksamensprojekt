package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdminTemaController
{
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

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {

    }

    @FXML
    void nytTema(ActionEvent event) {

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

    @FXML
    void redigerTema(ActionEvent event) {

    }

    @FXML
    void sletTema(ActionEvent event) {

    }

    @FXML
    void temaFilter(ActionEvent event) {

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

    // Skifter scenen til Admin Startside
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
}