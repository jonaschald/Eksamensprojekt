package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AdminPopUpController
{
    @FXML
    private ImageView popupBillede;

    @FXML
    private TextField titleFelt;

    @FXML
    private TextField årstalFelt;

    @FXML
    private TextField nummerFelt;

    @FXML
    private TextArea størrelseMedRammeFelt;

    @FXML
    private TextArea størrelseUdenRammeFelt;

    @FXML
    private TextArea serieNummerFelt;

    @FXML
    private TextArea beskrivelsesFelt;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // Liste der kan indeholde alle kunstværker til databasen
    private static ObservableList<Kunstværk> alleKunstværker = FXCollections.observableArrayList();

    // Gemmer det valgte kunstværk, så informationerne kan bruges i Pop-up siden
    public static Kunstværk valgtKunstværk;

    // Variabel der indeholder billedets binære data (til når Admin vil tilføje et nyt kunstværk)
    private byte[] billedeData;

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Vejledende tekst sættes i felterne - så Admin ved hvor hvad skal skrives
        titleFelt.setPromptText("Titel");
        årstalFelt.setPromptText("Årstal");
        nummerFelt.setPromptText("Nummer f.eks. MH 1991/1365. 1");
        størrelseMedRammeFelt.setPromptText("Str. med ramme f.eks. 85x75 cm");
        størrelseUdenRammeFelt.setPromptText("Str. uden ramme f.eks. 80x70 cm");
        serieNummerFelt.setPromptText("Serienummer f.eks. Tryk 21 ud af 100");
        beskrivelsesFelt.setPromptText("Beskrivelse");

        // Viser det valgte kunstværk
        visKunstværk(valgtKunstværk);
    }

    // Metode til at vise det valgte kunstværk og dets info i Pop-upp'en
    public void visKunstværk(Kunstværk kunstværk)
    {
        // Henter det valgte billede som binær data fra databasen
        // ByteArrayInputStream bruges til at omdanne byte data til et JavaFX billede
        // Billedet vises herefter i vores ImageView i pop-up'en
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(kunstværk.getBilledeData());
        Image image = new Image(byteArrayInputStream);
        popupBillede.setImage(image);

        // Viser infotekst til kunstværket i de forskellige labels
        titleFelt.setText(kunstværk.getTitel());
        årstalFelt.setText(String.valueOf(kunstværk.getÅrstal()));
        nummerFelt.setText(kunstværk.getId());
        størrelseMedRammeFelt.setText(kunstværk.getStørrelseMedRamme());
        størrelseUdenRammeFelt.setText(kunstværk.getStørrelseUdenRamme());
        serieNummerFelt.setText(kunstværk.getSerieNummer());
        beskrivelsesFelt.setText(kunstværk.getBeskrivelse());
    }

    // Metode til at Admin kan redigere tekst-informationerne om et kunstværk
    @FXML
    void redigerKunstværk(ActionEvent event) {
        try {
            // Konvertere årstal fra tekst til et tal
            int årstal = Integer.parseInt(årstalFelt.getText());

            // Henter de oplysninger Admin har skrevet ind i tekstfelterne
            // og setter dem på valgtKunstværk
            valgtKunstværk.setTitel(titleFelt.getText());
            valgtKunstværk.setÅrstal(årstal);
            valgtKunstværk.setId(nummerFelt.getText());
            valgtKunstværk.setStørrelseMedRamme(størrelseMedRammeFelt.getText());
            valgtKunstværk.setStørrelseUdenRamme(størrelseUdenRammeFelt.getText());
            valgtKunstværk.setSerieNummer(serieNummerFelt.getText());
            valgtKunstværk.setBeskrivelse(beskrivelsesFelt.getText());

            // Gemmer ændringerne i databasen
            dao.opdaterKunstværk(valgtKunstværk);

        } catch (NumberFormatException e) {
            // Giver brugeren besked, hvis årstal ikke er et tal
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Årtal skal være et tal f.eks. 2001 og uden mellemrum");
            alert.show();

        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke opdatere kunstværket");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke opdatere kunstværket");
            alert.show();
        }
    }

    // Metode til at Admin kan redigere kunstværkets billede
    @FXML
    void redigerBillede(ActionEvent event)
    {
        // Opretter en fileChooser
        FileChooser fileChooser = new FileChooser();

        // Gør at brugeren kun kan vælge billede filer
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Billedfiler", "*.jpg", "*.png", "*.jpeg"));

        // Åbner filvælgeren
        File valgtFil = fileChooser.showOpenDialog(null);

        // Hvis Admin har valgt en fil
        if (valgtFil != null) {
            try {
                // Konverterer billedet til binær data
                billedeData = Files.readAllBytes(valgtFil.toPath());

                // Gemmer det nye billede i valgtKunstværk
                valgtKunstværk.setBilledeData(billedeData);

                // Gemmer det nye billede i databasen
                dao.opdaterKunstværk(valgtKunstværk);

                // Opretter og indsætter et billede i ImageView
                Image image = new Image(valgtFil.toURI().toString());
                popupBillede.setImage(image);

            } catch (Exception exception) {
                // Udskriver fejlen i konsollen
                System.out.println("Kunne ikke vælge billedet");
                exception.printStackTrace();

                // Giver brugeren besked om fejlen
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke vælge billedet");
                alert.show();
            }
        }
    }

    // Når brugeren klikker på forrige pil vises det forrige kunstværk i PopUp'en
    @FXML
    void forrigeBilledeKnap(MouseEvent event)
    {
        // Henter index på det kunstværk som brugeren står på nu (i listen med alle kunstværker)
        int index = alleKunstværker.indexOf(valgtKunstværk);

        // Tjekker at der findes et billede før det billede vi står på nu
        // Hvis 0, så er vi på det første index og så er der ikke et forrige billede
        if (index > 0)
        {
            // Henter det forrige billede ved at gå en index ned
            valgtKunstværk = alleKunstværker.get(index - 1);
            // Viser det forrige billede i Pop-up'en
            visKunstværk(valgtKunstværk);
        } else {
            // Hvis brugeren står på det første billede, vises det sidste billede i listen
            valgtKunstværk = alleKunstværker.get(alleKunstværker.size() - 1);
            visKunstværk(valgtKunstværk);
        }
    }

    // Når brugeren klikker på næste pil vises næste kunstværk i PopUp'en
    @FXML
    void næsteBilledeKnap(MouseEvent event)
    {
        // Henter index på det kunstværk som brugeren står på nu (i listen med alle kunstværker)
        int index = alleKunstværker.indexOf(valgtKunstværk);

        // Tjekker at der findes et næste billede efter det billede vi står på nu
        // Hvis vi er på sidste index (alleKunstværker.size() - 1) så findes der ikke et næste billede
        if (index < alleKunstværker.size() - 1)
        {
            // Henter det næste billede ved at gå et index op
            valgtKunstværk = alleKunstværker.get(index + 1);
            // Viser det næste billede i Pop-up'en
            visKunstværk(valgtKunstværk);
        } else {
            // Hvis brugeren står på det sidste billede, vises det første billede i listen
            valgtKunstværk = alleKunstværker.get(0);
            visKunstværk(valgtKunstværk);
        }
    }

    @FXML
    void tilbageTilOversigtKnap(MouseEvent event) throws IOException {
        sceneManeger.tilbage(event);
    }

    // Metode til at sende listen med kunstværker til pop-up'en - de værker der skal navigeres i
    public static void setKunstværker(ObservableList<Kunstværk> kunstværker)
    {
        alleKunstværker = kunstværker;
    }
}