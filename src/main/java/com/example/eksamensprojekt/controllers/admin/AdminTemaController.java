package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
import com.example.eksamensprojekt.objekter.Tema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AdminTemaController
{
    @FXML
    private GridPane billedeContainer;

    @FXML
    private HBox temaKnapper;

    // Opretter et SceneManeger objekt - bruges til at skifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // Liste der kan indeholde Temaerne
    private ObservableList<Tema> temaer = FXCollections.observableArrayList();

    // Variabel der holder styr på hvilket tema Admin har valgt
    private Tema valgtTema;

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Henter alle temaer fra databasen og opretter en knap til hver tema
        hentTemaer();
    }

    // Metode til at lave et Pop-up vindue, hvor Admin kan tilføje et nyt Tema
    @FXML
    void nytTema(ActionEvent event)
    {
        // Opretter et pop-up vindue
        Dialog<ButtonType> dialogVindue = new Dialog<>();
        dialogVindue.setTitle("Tilføj Tema");
        dialogVindue.setHeaderText("Indtast navn på Tema");
        dialogVindue.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Opretter et tekstfelt hvor admin kan indtaste navnet på det nye tema
        TextField temaFelt = new TextField();
        temaFelt.setPromptText("Tema navn");

        // Opretter en VBox der indeholder tekstfeltet og sætter det ind i pop-vinduet
        VBox vBox = new VBox(10, temaFelt);
        dialogVindue.getDialogPane().setContent(vBox);

        // Viser pop-up, og stopper og venter på at brugeren klikker OK eller Cancel
        Optional<ButtonType> resultat = dialogVindue.showAndWait();

        // Hvis Admin klikker ok, oprettes der et nyt tema, som gemmes i databasen
        if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
            try {
                // Opretter et nyt Tema objekt med det indtastede navn
                Tema tema = new Tema(0, temaFelt.getText()); // Id sættes til 0, da databasen automatisk generer id

                // Gemmer det nye Tema i databasen
                dao.gemTema(tema);

                // Opdatere tema-knapperne, så man kan se ændringerne på siden
                hentTemaer();

            } catch (Exception e) {
                // Udskriver fejlen i konsollen
                System.out.println("Kunne ikke gemme Tema i databasen");
                e.printStackTrace();

                // Giver brugeren besked om fejlen
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke gemme Tema i databasen");
                alert.show();
            }
        }
    }

    // Metode til at lave et Pop-up vindue, hvor Admin kan redigere et Tema
    @FXML
    void redigerTema(ActionEvent event)
    {
        // Opretter et pop-up vindue
        Dialog<ButtonType> dialogVindue = new Dialog<>();
        dialogVindue.setTitle("Rediger Tema");
        dialogVindue.setHeaderText("Rediger Tema");
        dialogVindue.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Opretter en VBox der skal indeholde tekstfelter til alle temaerne
        VBox vBox = new VBox(10); // 10 pixels mellemrum mellem alle elementerne

        // Liste der indeholder alle tekstfelterne til redigering af temaer - for at gøre antal temaer dynamisk
        ArrayList<TextField> tekstFelter = new ArrayList<>();

        try {
            // Tømmer listen med temaer - for at undgå dubletter
            temaer.clear();

            // Henter alle temaerne fra databasen og gemmer dem i listen temaer
            dao.hentAlleTemaer(temaer);

            // Går alle temaerne igennem og opretter et tekstfelt til hvert tema
            for (Tema tema : temaer) {
                TextField tekstFelt = new TextField();
                tekstFelt.setText(tema.getNavn());
                tekstFelter.add(tekstFelt);
                vBox.getChildren().add(tekstFelt);
            }

            // Sætter VBoxen med alle tekstfelterne ind i pop-up vinduet
            dialogVindue.getDialogPane().setContent(vBox);

            // Viser pop-up, og stopper og venter på at brugeren klikker OK eller Cancel
            Optional<ButtonType> resultat = dialogVindue.showAndWait();

            // Hvis Admin klikker ok, opdateres alle temanavnene i databasen og tema-knapperne opdateres
            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                // Kører hele ArrayListen med temaer igennem
                for (int i = 0; i < tekstFelter.size(); i++)
                {
                    Tema tema = temaer.get(i); // Henter et tema
                    tema.setNavn(tekstFelter.get(i).getText()); // Sætter navnet på temaet til det Admin har skrevet i tekstfeltet
                    dao.opdaterTema(tema); // Gemmer det nye tema navn i databasen
                }
                hentTemaer(); // Opdatere tema-knapperne, så man kan se ændringerne på siden
            }
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke redigere Tema i databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke redigere Tema i databasen");
            alert.show();
        }
    }

    // Metode til at lave et Pop-up vindue, hvor Admin kan slette et Tema
    @FXML
    void sletTema(ActionEvent event)
    {
        // Opretter et pop-up vindue
        Dialog<ButtonType> dialogVindue = new Dialog<>();
        dialogVindue.setTitle("Slet Tema");
        dialogVindue.setHeaderText("Vælg temaer der skal slettes");
        dialogVindue.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Opretter en VBox der skal indeholde Checkboxes med hvert tema,
        // så admin kan vælge hvilke temaer der skal slettes
        VBox vBox = new VBox(10); // 10 pixels mellemrum mellem alle elementerne

        // Liste der indeholder alle CheckBoxes til de forskellige temaer
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

        try {
            // Tømmer listen med temaer - for at undgå dubletter
            temaer.clear();

            // Henter alle temaerne fra databasen og gemmer dem i listen temaer
            dao.hentAlleTemaer(temaer);

            // Går alle temaerne igennem og opretter en CheckBox til hvert tema
            for (Tema tema : temaer) {
                CheckBox checkBox = new CheckBox(tema.getNavn());
                checkBoxes.add(checkBox);
                vBox.getChildren().add(checkBox);
            }

            // Sætter VBoxen med alle CheckBoxes ind i pop-up vinduet
            dialogVindue.getDialogPane().setContent(vBox);

            // Viser pop-up, og stopper og venter på at brugeren klikker OK eller Cancel
            Optional<ButtonType> resultat = dialogVindue.showAndWait();

            // Hvis Admin klikker ok, slettes de markerede temaer i databasen og tema-knapperne opdateres
            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                // Går alle temaerne igennem
                for (int i = 0; i < temaer.size(); i++)
                {
                    // Hvis en CheckBox er markeret
                    if (checkBoxes.get(i).isSelected())
                    {
                        // Henter det tema der er markeret
                        Tema tema = temaer.get(i);

                        // Temaet "Øvrige Kunstværker" kan ikke slettes, da den bruges af
                        // databasen til alle de kunstværker der ikke tilhører et tema
                        if (tema.getNavn().equals("Øvrige værker"))
                        {
                            // Giver Admin besked
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Temaet Øvrige værker kan ikke slettes");
                            alert.show();
                        } else {
                            // Sletter det markerede tema
                            dao.sletTema(tema);
                        }
                    }
                }
                hentTemaer(); // Opdatere tema-knapperne, så man kan se ændringerne på siden
            }
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke slette Tema i databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke slette Tema i databasen");
            alert.show();
        }
    }

    // Henter alle temaer fra databasen og opretter en knap til hver tema
    public void hentTemaer()
    {
        try {
            // Fjerner temaknapper i HBoxen - for at undgå dubletter
            temaKnapper.getChildren().clear();

            // Tømmer listen med temaer - for at undgå dubletter
            temaer.clear();

            // Henter alle temaerne fra databasen og gemmer dem i listen temaer
            dao.hentAlleTemaer(temaer);

            // Variabel der bruges til at vise det første tema automatisk når siden åbnes
            boolean førsteTema = true;

            // Går alle temaerne igennem og opretter en knap til hvert tema
            for (Tema tema : temaer) {
                Button temaKnap = new Button();
                temaKnap.setText(tema.getNavn());
                temaKnap.setPrefWidth(230);
                temaKnap.setPrefHeight(70);
                temaKnap.setFont(Font.font("System", FontWeight.NORMAL, 22));

                // Gør knappen klikbar, så knappen viser de kunstværker der tilhører temaet
                temaKnap.setOnAction(event ->
                {
                    // Gemmer det tema Admin har valgt
                    valgtTema = tema;

                    try {
                        // Opretter en liste til kunstværkerne
                        ObservableList<Kunstværk> kunstværker = FXCollections.observableArrayList();

                        // Henter de kunstværker der tilhører det valgte tema fra databasen
                        dao.hentKunstværkerEfterTema(tema.getId(), kunstværker);

                        // Viser de kunstværker der tilhører temaet
                        visKunstværker(kunstværker);

                    } catch (Exception e) {
                        // Udskriver fejlen i konsollen
                        System.out.println("Kunne ikke hente kunstværker tilhørende temaet fra databasen");
                        e.printStackTrace();

                        // Giver brugeren besked om fejlen
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente kunstværker tilhørende temaet fra databasen");
                        alert.show();
                    }
                });

                // Tilføjer tema-knappen til HBoxen
                temaKnapper.getChildren().add(temaKnap);

                //Viser automatisk kunstværkerne fra det første tema når siden åbnes
                // Hvis der findes et første tema og det ikke er "Øvrige værker"
                if (førsteTema == true && !tema.getNavn().equals("Øvrige værker"))
                {
                    // Gemmer det første tema som det tema der er valgt nu
                    valgtTema = tema;

                    // Opretter en liste til kunstværkerne
                    ObservableList<Kunstværk> kunstværker = FXCollections.observableArrayList();

                    // Henter de kunstværker der tilhører det valgte tema fra databasen og kommer dem i listen
                    dao.hentKunstværkerEfterTema(tema.getId(), kunstværker);

                    // Viser de kunstværker der tilhører temaet
                    visKunstværker(kunstværker);

                    // Sætter førsteTema til false, så det kun er det første tema der vises automatisk
                    førsteTema = false;
                }
            }
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke hente alle Temaer fra databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente alle Temaer fra databasen");
            alert.show();
        }
    }

    // Metode til at vise alle kunstværkerne i et GridPane
    public void visKunstværker(ObservableList<Kunstværk> kunstværker)
    {
        // Fjerner alle elementer i vores GridPane - for at undgå dubletter
        billedeContainer.getChildren().clear();

        // Afstand mellem felterne
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

            // Opretter et JavaFX billede ud fra billeddataene
            Image image = new Image(byteArrayInputStream);

            // Opretter et ImageView der kan vise billedet på skærmen
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(290);
            imageView.setFitHeight(390);
            imageView.setPreserveRatio(true);

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

    // Metode der åbner et pop-up vindue, hvor Admin kan vælge hvilke kunstværker der skal tilhøre det valgte tema
    @FXML
    void redigerKunstværker(ActionEvent event)
    {
        // Hvis Admin ikke hat valgt et tema endnu, vises en fejlbesked
        if (valgtTema == null) {
            // Admin får vejledning
            Alert alert = new Alert(Alert.AlertType.ERROR, "Du skal vælge et tema først");
            alert.show();
            return; // Metoden stoppes her
        }

        // Opretter et pop-up vindue
        Dialog<ButtonType> dialogVindue = new Dialog<>();
        dialogVindue.setTitle("Rediger Kunstværker");
        dialogVindue.setHeaderText("Vælg hvilke kunstværker der hører til temaet: \n"
                + valgtTema.getNavn());
        dialogVindue.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Opretter en VBox der skal indeholde Checkboxes til hvert kunstværk,
        // så admin kan vælge hvilke kunstværker der skal tilhøre temaet
        VBox vBox = new VBox(10); // 10 pixels mellemrum mellem alle elementerne

        // Liste der indeholder alle CheckBoxes til hvert kunstværk
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

        // Opretter en liste til kunstværkerne
        ObservableList<Kunstværk> alleKunstværker = FXCollections.observableArrayList();

        try {
            // Henter alle kunstværker fra databasen og kommer dem i listen
            dao.hentAlleKunstværker(alleKunstværker);

            // Går alle kunstværkerne igennem og laver en CheckBox til hver
            for (Kunstværk kunstværk : alleKunstværker)
            {
                CheckBox checkBox = new CheckBox(kunstværk.getTitel());

                // Hvis kunstværket tilhører det valgte tema, så sættes der flueben i CheckBoxen
                if (kunstværk.getTemaId() == valgtTema.getId()) {
                    checkBox.setSelected(true);
                }

                // Tilføjer CheckBoxen til listen med CheckBoxes
                checkBoxes.add(checkBox);

                // Tilføjer CheckBoxen til vBoxen
                vBox.getChildren().add(checkBox);
            }

            // Opretter en ScrollPane til vBoxen med alle CheckBoxes
            ScrollPane scrollPane = new ScrollPane(vBox);
            scrollPane.setContent(vBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(400);

            // Sætter ScrollPane ind i Pop-up vinduet
            dialogVindue.getDialogPane().setContent(scrollPane);

            // Viser pop-up, og stopper og venter på at brugeren klikker OK eller Cancel
            Optional<ButtonType> resultat = dialogVindue.showAndWait();

            // Hvis Admin klikker ok, opdateres kunstværkernes temaer ud fra hvilke CheckBoxes der er markeret
            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                // Går alle kunstværkerne igennem
                for (int i = 0; i < alleKunstværker.size(); i++)
                {
                    // Henter et kunstværk
                    Kunstværk kunstværk = alleKunstværker.get(i);

                    // Hvis CheckBoxen er markeret tilknyttes det til det valgte tema
                    if (checkBoxes.get(i).isSelected())
                    {
                        kunstværk.setTemaId(valgtTema.getId());
                    } else {
                        // Hvis CheckBoxen ikke er markeret tilknyttes kunstværket til temaet "Øvrige Værker"
                        kunstværk.setTemaId(4);
                    }
                    // Opdatere Kunstværket med det nye tema i databasen
                    dao.opdaterKunstværk(kunstværk);
                }

                // Henter kunstværkerne til det valgte tema igen og opdatere visningen på siden
                ObservableList<Kunstværk> kunstværker = FXCollections.observableArrayList();
                dao.hentKunstværkerEfterTema(valgtTema.getId(), kunstværker);
                visKunstværker(kunstværker);
            }
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke redigere kunstværker");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke redigere kunstværker");
            alert.show();
        }
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
}