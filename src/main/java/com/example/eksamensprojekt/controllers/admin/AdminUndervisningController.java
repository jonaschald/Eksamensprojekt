package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.*;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Målgruppe;
import com.example.eksamensprojekt.objekter.Undervisningsmateriale;
import com.example.eksamensprojekt.undervisning.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AdminUndervisningController
{
    @FXML
    private VBox pane;

    @FXML
    private ListView<PdfItem> indskolingData;

    @FXML
    private ListView<PdfItem> mellemtrinData;

    @FXML
    private ListView<PdfItem> udskolingData;

    @FXML
    private ListView<PdfItem> konfirmationData;

    // Liste der indeholder alle 4 listviews med skoletrin
    private List<ListView<PdfItem>> allLists;

    // ObservableList der kan indeholde alle Undervisningsmateriale objekter fra Databasen
    private ObservableList<Undervisningsmateriale> undervisningsmaterialer = FXCollections.observableArrayList();

    // Variabel der gemmer den PDF-fil/undervisningsmateriale som Admin vælger i redigeringsvinduet
    private File selectedFile;

    // Variabel der husker det gamle PDF navn - vigtigt fordi Admin kan redigere i PDF navnet
    // og så skal vi stadig kunne finde elementet i databasen (som er gemt under det gamle navn),
    // sådan så elementet i databasen kan opdateres eller slettes
    private String gammeltPDFNavn;

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = DAOImplementation.getInstance();

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Nulstiller ObservableLister i DataDeling - for at undgå dubletter ved sceneskift
        DataDeling.indskolingList.clear();
        DataDeling.mellemtrinList.clear();
        DataDeling.udskolingList.clear();
        DataDeling.konfirmationList.clear();
        undervisningsmaterialer.clear();

        // Indsætter undervisningsmaterialerne fra hver liste ind i tilhørende Listview
        indskolingData.setItems(DataDeling.indskolingList);
        mellemtrinData.setItems(DataDeling.mellemtrinList);
        udskolingData.setItems(DataDeling.udskolingList);
        konfirmationData.setItems(DataDeling.konfirmationList);

        // Samler alle Listviews i en liste - så man slipper for at skrive samme kode 4 gange
        allLists = List.of(indskolingData, mellemtrinData, udskolingData, konfirmationData);

        try
        {
            // Henter undervisningsmaterialerne fra Databasen og kommer dem ind i en ObservableList
            dao.hentUndervisningsmateriale(undervisningsmaterialer);

            // Går hvert undervisningsmateriale igennem i en for-løkke
            for (Undervisningsmateriale undervisningsmateriale : undervisningsmaterialer) {

                // Laver undervisningsmaterialerne fra databasen om til et PdfItem objekt
                PdfItem item = new PdfItem(undervisningsmateriale.getTitle(), undervisningsmateriale.getPdf());

                // Kommer objektet ind i den tilhørende ListView
                if (undervisningsmateriale.getMålgruppeId() == 1) {
                    indskolingData.getItems().add(item);
                } else if (undervisningsmateriale.getMålgruppeId() == 2) {
                    mellemtrinData.getItems().add(item);
                } else if (undervisningsmateriale.getMålgruppeId() == 3) {
                    udskolingData.getItems().add(item);
                } else if (undervisningsmateriale.getMålgruppeId() == 4) {
                    konfirmationData.getItems().add(item);
                }
            }

        }
        catch (ExecutionException | InterruptedException e)
        {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i AdminUndervisningsController: " +
                    "Kunne ikke hente undervisningsmaterialer fra databasen");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver Admin besked om fejlen
            showError("Undervisningsmaterialerne kunne ikke hentes fra Databasen");
        }
    }

    // Metode der kører når Admin klikker på Tilføj knap
    @FXML
    void tilføjUndervisningsmateriale(ActionEvent event)
    {
        // Opretter et tomt PdfItem
        PdfItem item = new PdfItem("",null);

        // Åbner redigeringsvinduet med det tomme PdfIrem og fortæller vinduet at ingen checkboxes er valgt endnu
        // Editing boolean sættes til status false, da vi er igang med at oprette ny PDF
        showPdfDialog(item, false, false, false, false, false);
    }

    // Metode der kører når Admin klikker på Rediger knap
    @FXML
    void redigerUndervisningsmatriale(ActionEvent event)
    {
        // Henter den Pdf som Admin har markeret
        PdfItem item = getSelectedItem();

        // Hvis Admin ikke har valgt en PdfItem
        if (item == null) {
            showError ("Vælg en fil først"); // Brugeren får vejledning i en popup
            return; // Metoden stoppes her
        }

        // Tjekker hvilke lister PDF'en ligger i - hvis den ikke ligger i listen: false, hvis den gør: true
        boolean inInskoling = indskolingData.getItems().contains(item);
        boolean inMellemtrin = mellemtrinData.getItems().contains(item);
        boolean inUdskoling = udskolingData.getItems().contains(item);
        boolean inKonfirmation = konfirmationData.getItems().contains(item);

        // Åbner redigeringsvinduet hvori alt info om PDF'en vises, så Admin kan rette ønsket information
        // Editing boolean sættes til status true, da vi er igang med redigeringen
        showPdfDialog(item, inInskoling, inMellemtrin, inUdskoling, inKonfirmation, true);
    }

    // Metode der kører når Admin klikker på Slet knap
    @FXML
    void sletUndervisningsmatriale(ActionEvent event)
    {
        // Henter den PdfItem som Admin har markeret
        PdfItem item = getSelectedItem();

        // Hvis Admin ikke har valgt en PdfItem
        if (item == null) {
            showError("Vælg en fil først"); // Brugeren får vejledning i en popup
            return; // Metoden stoppes her
        }

        // Opretter et popup vindue der spørger Admin: er du sikker på at du vil slette filen?
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Er du sikker på at du vil slette filen?");
        alert.setHeaderText(null);
        alert.setContentText("Slet \"" + item.getName() + "\" ?");

        // Viser popup vinduet og stopper og venter på at Admin klikker OK eller Cancel
        Optional<ButtonType> resultat = alert.showAndWait();

        // Hvis Admin klikker OK i redigeringsvinduet
        if (resultat.isPresent() && resultat.get() == ButtonType.OK) {

            try {
                // Opretter en tom variabel til et Undervisnings objekt
                // Skal bruges til at finde det objekt der skal slettes fra databasen
                Undervisningsmateriale valgtUndervisningsmateriale = null;

                // Går igennem alle undervisningsmaterialerne et ad gangen og sammenligner navnene
                // mellem PdfItem (vores objekt "item") og Undervisningsmateriale
                // Hvis navnene matcher har vi fundet det rigtige objekt der skal slettes fra databasen
                for (Undervisningsmateriale undervisningsmateriale : undervisningsmaterialer) {
                    if (undervisningsmateriale.getTitle().equals(item.getName())) {
                        valgtUndervisningsmateriale = undervisningsmateriale;
                        break; // Løkken stoppes så snart det rigtige undervisningsmateriale er fundet
                    }
                }

                // Hvis der findes et valgtUndervisningsmateriale, så slettes det fra databasen og
                // fra ObservableListen med alle undervisningsmaterialer
                if(valgtUndervisningsmateriale != null) {
                    dao.sletUndervisningsmateriale(valgtUndervisningsmateriale);
                    undervisningsmaterialer.remove(valgtUndervisningsmateriale);
                }

                // Undervisningsmaterialet slettes fra Listviews
                indskolingData.getItems().remove(item);
                mellemtrinData.getItems().remove(item);
                udskolingData.getItems().remove(item);
                konfirmationData.getItems().remove(item);

            } catch (Exception e) {
                // Udskriver fejlen i konsollen
                System.out.println("Fejl i sletning af undervisningsmateriale");
                e.printStackTrace(); // Printer hele fejlen i konsollen

                // Giver Admin besked om fejlen
                showError("Fejl i sletning af undervisningsmateriale");
            }
        }
    }

    // Metode der åbner tilføj- eller redigerings-vinduet
    private void showPdfDialog(PdfItem pdfItem, boolean inIndskoling, boolean inMellemtrin,
            boolean inUdskoling, boolean inKonfirmation, boolean editing)
    {
        // Opretter popup vindue
        Dialog<ButtonType> dialog = new Dialog<>();

        // Binder vinduet til hovedvinduet, så det ikke forsvinder bag programmet
        dialog.initOwner(pane.getScene().getWindow());

        // Titlen sættes enten til at men redigere eller tilføjer en PDF
        if (editing) {
            dialog.setTitle("Rediger PDF");
        } else {
            dialog.setTitle("Tilføj PDF");
        }

        // Opretter et tekstfelt til pdf-filen
        TextField fileField = new TextField();
        fileField.setEditable(false); // Man skal ikke kunne skrive i dette felt

        // Opretter et tekstfelt til navnet på PDF'en,
        // hvor Admin selv kan bestemme hvad undervisningsmaterialet skal hedde
        TextField nameField = new TextField();

        // Opretter checkboksene og giver dem et Id,
        // så vi senere kan finde ud af hvilken målgruppe hver checkbox hører til
        CheckBox indskolingCheck = new CheckBox("Indskoling");
        indskolingCheck.setId("indskolingCheck");
        CheckBox mellemtrinCheck = new CheckBox("Mellemtrin");
        mellemtrinCheck.setId("mellemtrinCheck");
        CheckBox udskolingCheck = new CheckBox("Udskoling");
        udskolingCheck.setId("udskolingCheck");
        CheckBox konfirmationCheck = new CheckBox("Konfirmation");
        konfirmationCheck.setId("konfirmationCheck");

        // Opretter en knap til at vælge en fil
        Button browseButton = new Button("Vælg fil");

        // Hvis Admin redigerer et undervisningsmateriale,
        // så udfyldes redigeringsvinduet med de gamle oplysninger
        if(editing && pdfItem != null) {
            gammeltPDFNavn = pdfItem.getName();
            selectedFile = pdfItem.getpdfFile();
            fileField.setText(pdfItem.getpdfFile().getName());
            nameField.setText(pdfItem.getName());

            indskolingCheck.setSelected(inIndskoling);
            mellemtrinCheck.setSelected(inMellemtrin);
            udskolingCheck.setSelected(inUdskoling);
            konfirmationCheck.setSelected(inKonfirmation);
        }

        // Hvis Admin klikker på Vælg fil knap åbnes der et filvælger-vindue
        browseButton.setOnAction(e ->
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Vælg PDF");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "PDF file", "*.pdf")); // Skal være PDF
            File file = chooser.showOpenDialog(pane.getScene().getWindow());

            // Hvis der er valgt en Pdf fil
            if (file != null) {
                selectedFile = file; // Gemmer den valgte fil
                fileField.setText(file.getAbsolutePath()); // Viser i tekstfeltet at man har valgt

                // Sætter automatisk navn til filens navn
                if (nameField.getText().isBlank()) {
                    nameField.setText(file.getName());
                }
            }
        });

        // Layout til tilføj- og redigeringsvinduet
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("File:"), 0, 0);
        gridPane.add(fileField, 1, 0);
        gridPane.add(browseButton, 2, 0);

        gridPane.add(new Label("Name:"), 0, 1);
        gridPane.add(nameField, 1, 1);

        gridPane.add(indskolingCheck, 1, 2);
        gridPane.add(mellemtrinCheck, 1, 3);
        gridPane.add(udskolingCheck, 1, 4);
        gridPane.add(konfirmationCheck, 1, 5);

        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK,
                ButtonType.CANCEL);

        Optional<ButtonType> resultat = dialog.showAndWait();

        // Hvis Admin klikker ok i dialog vinduet når en PDF skal tilføjes eller redigeres
        if (resultat.isPresent() && resultat.get() == ButtonType.OK)
        {
            // Hvis der ikke er valgt en fil
            if (selectedFile == null) {
                showError("Vælg en PDF file"); // Brugeren får vejledning i en popup
                return; // Metoden stoppes her
            }

            // Hvis der ikke er valgt mindst en checkbox
            if(!indskolingCheck.isSelected() && !mellemtrinCheck.isSelected()
                    && !udskolingCheck.isSelected() && !konfirmationCheck.isSelected()) {
                showError("Vælg mindst én liste"); // Brugeren får vejledning i en popup
                return; // Metoden stoppes her
            }

            // Samler alle checkboxes i en liste,
            // så vi senere kan lave en for-løkke og slipper for at skrive 4 if-sætninger
            List<CheckBox> checkboxes = new ArrayList<>();
            checkboxes.add(indskolingCheck);
            checkboxes.add(mellemtrinCheck);
            checkboxes.add(udskolingCheck);
            checkboxes.add(konfirmationCheck);

            // Opdatere PdfItem med filen og dens navn
            pdfItem.setName(nameField.getText());
            pdfItem.setpdfFile(selectedFile);

            // Fjerner Pdf'en fra alle listerne først - for at undgå dubletter
            indskolingData.getItems().remove(pdfItem);
            mellemtrinData.getItems().remove(pdfItem);
            udskolingData.getItems().remove(pdfItem);
            konfirmationData.getItems().remove(pdfItem);

            // Tilføjer til listerne igen baseret på checkboxene
            if (indskolingCheck.isSelected()) {
                indskolingData.getItems().add(pdfItem);
            }
            if (mellemtrinCheck.isSelected()) {
                mellemtrinData.getItems().add(pdfItem);
            }
            if (udskolingCheck.isSelected()) {
                udskolingData.getItems().add(pdfItem);
            }
            if (konfirmationCheck.isSelected()) {
                konfirmationData.getItems().add(pdfItem);
            }

            // Hvis vi redigere - så skal gamle rækker fra databasen slettes først
            if (editing) {
                // Opretter en tom liste til de undervisningsmaterialer der skal slettes i databasen pga. redigering
                List<Undervisningsmateriale> undervisningsmaterialerDerSkalSlettes = new ArrayList<>();

                // Går alle undervisningsmaterialerne igennem og finder de rækker i databasen der matcher det gamle navn
                // og tilføjer dem til listen
                for (Undervisningsmateriale undervisningsmateriale : undervisningsmaterialer) {
                    if (undervisningsmateriale.getTitle().equals(gammeltPDFNavn)) {
                        undervisningsmaterialerDerSkalSlettes.add(undervisningsmateriale);
                    }
                }

                // Sletter de gamle rækker fra databasen
                for (Undervisningsmateriale undervisningsmateriale : undervisningsmaterialerDerSkalSlettes) {
                    try {
                        dao.sletUndervisningsmateriale(undervisningsmateriale);
                        undervisningsmaterialer.remove(undervisningsmateriale);
                    } catch (Exception e) {
                        // Udskriver fejlen i konsollen
                        System.out.println("Kunne ikke slette gamle undervisningsmaterialer fra databasen");
                        e.printStackTrace(); // Printer hele fejlen i konsollen

                        // Giver Admin besked om fejlen
                        showError("Kunne ikke slette gamle undervisningsmaterialer fra databasen");
                    }
                }
            }

            // Går igennem alle checkboxes og tjekker hvilke der er valgt
            // Under de checkboxes der er valgt, oprettes der et nyt undervisningsmateriale objekt
            for (CheckBox checkbox : checkboxes)
            {
                if (checkbox.isSelected())
                {
                    Undervisningsmateriale undervisningsmateriale = new Undervisningsmateriale(
                            0, pdfItem.getName(), pdfItem.getpdfFile(), Målgruppe.convertToId(checkbox.getId()));

                    // Det nye undervisningsmateriale objekt gemmes i databasen
                    try {
                        dao.gemUndervisningsmateriale(undervisningsmateriale);
                    } catch (ExecutionException | InterruptedException e) {
                        // Udskriver fejlen i konsollen
                        System.out.println("Kunne ikke gemme undervisningsmaterialer i databasen");
                        e.printStackTrace(); // Printer hele fejlen i konsollen

                        // Giver Admin besked om fejlen
                        showError("Kunne ikke gemme undervisningsmaterialer i databasen");
                    }
                }
            }

            // Henter det nyeste data fra databasen og sætter det ind i listen med alle undervisningsmaterialer
            try {
                undervisningsmaterialer.clear();
                dao.hentUndervisningsmateriale(undervisningsmaterialer);
            } catch (ExecutionException | InterruptedException e) {
                // Udskriver fejlen i konsollen
                System.out.println("Kunne ikke hente undervisningsmaterialer fra databasen");
                e.printStackTrace(); // Printer hele fejlen i konsollen

                // Giver Admin besked om fejlen
                showError("Kunne ikke hente undervisningsmaterialer fra databasen");
            }

            // Opdatere listview, så det nye data kommer derind
            indskolingData.refresh();
            mellemtrinData.refresh();
            udskolingData.refresh();
            konfirmationData.refresh();
        }
    }

    // Metode til at vise fejl-meddelelser i et popup vindue
    private void showError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Binder error-popup-vinduet til hovedvinduet, så det ikke forsvinder bag programmet
        alert.initOwner(pane.getScene().getWindow());

        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    // Metode der kigger alle vores klassetrins lister igennem og finder den Pdf brugeren har markeret
    private PdfItem getSelectedItem()
    {
        for (ListView<PdfItem> list : allLists) // Tager en liste ad gangen og kigger dem alle igennem
        {
            // Henter det objekt Admin har klikket på i en af listerne
            PdfItem pdfItem = list.getSelectionModel().getSelectedItem();

            if (pdfItem != null) {
                return pdfItem; // Returnere det objekt Admin har markeret
            }
        }
        return null; // null returneres hvis ingen pdf er valgt/fundet
    }

    // Skifter scenen til Admin Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Om.fxml");
    }

    // Skifter scenen til Admin Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Samlingen.fxml");
    }

    // Skiftet scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Temaer.fxml");
    }

    // Skifter scene til Admin Samlingen
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
}