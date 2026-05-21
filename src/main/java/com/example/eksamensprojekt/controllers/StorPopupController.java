package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.objekter.Kunstværk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class StorPopupController
{
    @FXML
    private ImageView storPopupBillede;

    @FXML
    private Label title;

    // Opretter et SceneManeger objekt - bruges til at skifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // ObservableList der kan indeholde alle kunstværker fra Databasen
    private static ObservableList<Kunstværk> alleKunstværker = FXCollections.observableArrayList();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Viser det valgte kunstværk og dets info i Stor Pop Up
        visKunstværk(PopupController.valgtKunstværk);
    }

    // Når brugeren klikker på forrige pil vises det forrige kunstværk i PopUp'en
    @FXML
    void forrigeBilledeKnap(MouseEvent event)
    {
        // Henter index på det kunstværk som brugeren står på nu (i listen med alle kunstværker)
        int index = alleKunstværker.indexOf(PopupController.valgtKunstværk);

        // Tjekker at der findes et billede før det billede vi står på nu
        // Hvis 0, så er vi på det sidste index og så er der ikke et forrige billede
        if (index > 0)
        {
            // Henter det forrige billede ved at gå en index ned
            PopupController.valgtKunstværk = alleKunstværker.get(index - 1);
            // Viser det forrige billede i Pop-up'en
            visKunstværk(PopupController.valgtKunstværk);
        } else {
            // Hvis brugeren står på det første billede, vises det sidste billede i listen
            PopupController.valgtKunstværk = alleKunstværker.get(alleKunstværker.size() - 1);
            visKunstværk(PopupController.valgtKunstværk);
        }
    }

    // Når brugeren klikker på næste pil vises næste kunstværk i PopUp'en
    @FXML
    void næsteBilledeKnap(MouseEvent event)
    {
        // Henter index på det kunstværk som brugeren står på nu (i listen med alle kunstværker)
        int index = alleKunstværker.indexOf(PopupController.valgtKunstværk);

        // Tjekker at der findes et næste billede efter det billede vi står på nu
        // Hvis vi er på sidste index (alleKunstværker.size() - 1) så findes der ikke et næste billede
        if (index < alleKunstværker.size() - 1)
        {
            // Henter det næste billede ved at gå et index op
            PopupController.valgtKunstværk = alleKunstværker.get(index + 1);
            // Viser det næste billede i Pop-up'en
            visKunstværk(PopupController.valgtKunstværk);
        } else {
            // Hvis brugeren står på det sidste billede, vises det første billede i listen
            PopupController.valgtKunstværk = alleKunstværker.get(0);
            visKunstværk(PopupController.valgtKunstværk);
        }
    }

    // Tager brugeren tilbage til forrige FXML vindue (Pop-up)
    @FXML
    void tilbageKnap(MouseEvent event) throws IOException
    {
        sceneManeger.tilbage(event);
    }

    // Metode til at vise det valgte kunstværk og dets info i Pop-upp'en
    public void visKunstværk(Kunstværk kunstværk)
    {
        // Henter det valgte billede som binær data fra databasen
        // ByteArrayInputStream bruges til at omdanne byte data til et JavaFX billede
        // Billedet vises herefter i vores ImageView i pop-up'en
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(PopupController.valgtKunstværk.getBilledeData());
        Image image = new Image(byteArrayInputStream);
        storPopupBillede.setImage(image);

        // Titlen hentes til billedet og vises i Label i Pop-up'en
        title.setText(PopupController.valgtKunstværk.getTitel());
    }

    // Metode til at sende listen med kunstværker til Stor Pop-up - de værker der skal navigeres i
    // Listen afhænger af om brugeren er inde på hele samlingen, favoritter eller temaer
    public static void setKunstværker(ObservableList<Kunstværk> kunstværker)
    {
        alleKunstværker = kunstværker;
    }
}