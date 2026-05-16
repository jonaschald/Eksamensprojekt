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

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Liste der indeholder alle 4 listviews med skoletrin
    private List<ListView<PdfItem>> allLists;

    // ObservableList der kan indeholde alle Undervisningsmateriale objekter fra Databasen
    private ObservableList<Undervisningsmateriale> undervisningsmaterialer = FXCollections.observableArrayList();

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

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Undervisningsmaterialerne kunne ikke hentes fra Databasen");
            alert.show();
        }
    }

    // Metode der kører når Admin klikker på Tilføj knap
    @FXML
    void tilføjUndervisningsmateriale(ActionEvent event)
    {
        // Opretter et tomt PdfItem
        PdfItem item = new PdfItem("",null);

        // Åbner redigeringsvinduet og fortæller vinduet at ingen checkboxes er valgt endnu
        // Editing boolean sættes til status false, da vi er igang med at oprette ny PDF
        showPdfDialog(item, false, false, false, false, false);
    }

    // Metode der kører når Admin klikker på Rediger knap
    @FXML
    void redigerUndervisningsmatriale(ActionEvent event)
    {
        // Henter den PdfItem som brugeren har markeret
        PdfItem item = getSelectedItem();

        // Hvis brugeren ikke har valgt en PdfItem
        if (item == null) {
            showError ("Vælg en fil først"); // Brugeren får vejledning i en popup
            return; // Metoden stoppes her
        }

        // Tjekker hvilke lister PDF'en ligger i - hvis den ikke ligger i listen: false, hvis den gør: true
        boolean inInskoling = indskolingData.getItems().contains(item);
        boolean inMellemtrin = mellemtrinData.getItems().contains(item);
        boolean inUdskoling = udskolingData.getItems().contains(item);
        boolean inKonfirmation = konfirmationData.getItems().contains(item);

        // Åbner redigeringsvinduet hvori alt info om PDF'en vises, så brugeren kan rette ønsket information
        // Editing boolean sættes til status true, da vi er igang med redigeringen
        showPdfDialog(item, inInskoling, inMellemtrin, inUdskoling, inKonfirmation, true);
    }

    // Metode der kører når Admin klikker på Slet knap
    @FXML
    void sletUndervisningsmatriale(ActionEvent event)
    {
        // Henter den PdfItem som brugeren har markeret
        PdfItem item = getSelectedItem();

        // Hvis brugeren ikke har valgt en PdfItem
        if (item == null) {
            showError("Vælg en fil først"); // Brugeren får vejledning i en popup
            return; // Metoden stoppes her
        }

        // Opretter et popup vindue der spørger Admin om du er sikker på at du vil slette filen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Er du sikker på at du vil slette filen?");
        alert.setHeaderText(null);
        alert.setContentText("Slet \"" + item.getName() + "\" ?");

        Optional<ButtonType> resultat = alert.showAndWait();
        if(resultat.isPresent() && resultat.get() == ButtonType.OK) {

            try {
                // Tom variabel
                Undervisningsmateriale valgtUndervisningsmateriale = null;

                // Går igennem alle undervisningsmaterialerne en ad gangen og sammenligner navne
                // mellem PdfItem (vores kode "item") og Undervisningsmateriale (Databasens kode "undervisningsmateriale")
                // Hvis de matcher har vi fundet det rigtige objekt der skal slettes fra Databasen
                for(Undervisningsmateriale undervisningsmateriale : undervisningsmaterialer) {
                    if(undervisningsmateriale.getTitle().equals(item.getName())) {
                        valgtUndervisningsmateriale = undervisningsmateriale;
                        break; // Løkken stoppes så snart det rigtige undervisningsmateriale er fundet
                    }
                }

                // Slettes fra databasen
                if(valgtUndervisningsmateriale != null) {
                    dao.sletUndervisningsmateriale(valgtUndervisningsmateriale);
                    undervisningsmaterialer.remove(valgtUndervisningsmateriale);
                }

                // Slettes fra listviews
                indskolingData.getItems().remove(item);
                mellemtrinData.getItems().remove(item);
                udskolingData.getItems().remove(item);
                konfirmationData.getItems().remove(item);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showPdfDialog(PdfItem pdfItem, boolean inIndskoling, boolean inMellemtrin,
            boolean inUdskoling, boolean inKonfirmation, boolean editing)
    {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(pane.getScene().getWindow()); // Binder vinduet til hovedvinduet
        dialog.setTitle(editing ? "Rediger PDF" : "Tilføj PDF");


        // Fil sti felt
        TextField fileField = new TextField();
        fileField.setEditable(false);

        // Navn på filen
        TextField nameField = new TextField();

        // Checkboksene
        CheckBox indskolingCheck = new CheckBox("Indskoling");
        indskolingCheck.setId("indskolingCheck");
        CheckBox mellemtrinCheck = new CheckBox("Mellemtrin");
        mellemtrinCheck.setId("mellemtrinCheck");
        CheckBox udskolingCheck = new CheckBox("Udskoling");
        udskolingCheck.setId("udskolingCheck");
        CheckBox konfirmationCheck = new CheckBox("Konfirmation");
        konfirmationCheck.setId("konfirmationCheck");

        // Knap til at vælge fil
        Button browseButton = new Button("Vælg fil");

        final File[] selectedFile = new File[1];
        final String[] gammeltNavnPDF = new String[1];

        // Hvis rediger, udfylder felterne for dig
        if(editing && pdfItem !=null) {
            gammeltNavnPDF[0] = pdfItem.getName();
            selectedFile[0] = pdfItem.getpdfFile();
            fileField.setText(pdfItem.getpdfFile().getName());
            nameField.setText(pdfItem.getName());

            indskolingCheck.setSelected(inIndskoling);
            mellemtrinCheck.setSelected(inMellemtrin);
            udskolingCheck.setSelected(inUdskoling);
            konfirmationCheck.setSelected(inKonfirmation);
        }

        browseButton.setOnAction(e -> {
            FileChooser chooser = new FileChooser();

            chooser.setTitle("Vælg PDF");

            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "PDF file", "*.pdf"));

            File file = chooser.showOpenDialog(pane.getScene().getWindow());

            if (file != null) {
                selectedFile[0] = file;
                fileField.setText(file.getAbsolutePath());

                // Sætter automatisk navn til filens navn
                if(nameField.getText().isBlank()) {
                    nameField.setText(file.getName());
                }
            }
        });

        // Layout
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

        if (resultat.isPresent() && resultat.get() == ButtonType.OK)
        {

            // Fortæller at du skal vælger en fil
            if(selectedFile[0] == null) {
                showError("Vælg en PDF file");
                return;
            }

            if(!indskolingCheck.isSelected()
                    && !mellemtrinCheck.isSelected()
                    && !udskolingCheck.isSelected()
                    && !konfirmationCheck.isSelected()) {
                showError("Vælg mindst én liste");
                return;
            }

            List<CheckBox> checkboxes = new ArrayList<>();
            checkboxes.add(indskolingCheck);
            checkboxes.add(mellemtrinCheck);
            checkboxes.add(udskolingCheck);
            checkboxes.add(konfirmationCheck);

            pdfItem.setName(nameField.getText());
            pdfItem.setpdfFile(selectedFile[0]);

            // Fjerner fra alle listerne først
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

            // Slet gamle rækker fra databasen
            if (editing) {
                List<Undervisningsmateriale> undervisningsmaterialerDerSkalSlettes = new ArrayList<>();

                for (Undervisningsmateriale undervisningsmateriale : undervisningsmaterialer) {
                    if (undervisningsmateriale.getTitle().equals(gammeltNavnPDF[0])) {
                        undervisningsmaterialerDerSkalSlettes.add(undervisningsmateriale);
                    }
                }

                for (Undervisningsmateriale undervisningsmateriale : undervisningsmaterialerDerSkalSlettes) {
                    try {
                        dao.sletUndervisningsmateriale(undervisningsmateriale);
                        undervisningsmaterialer.remove(undervisningsmateriale);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            for (CheckBox checkbox : checkboxes) {
                if (checkbox.isSelected()) {
                    Undervisningsmateriale undervisningsmateriale = new Undervisningsmateriale(
                            0, pdfItem.getName(), pdfItem.getpdfFile(), Målgruppe.convertToId(checkbox.getId()));

                    try {
                        dao.gemUndervisningsmateriale(undervisningsmateriale);
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            try {
                undervisningsmaterialer.clear();
                dao.hentUndervisningsmateriale(undervisningsmaterialer);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            indskolingData.refresh();
            mellemtrinData.refresh();
            udskolingData.refresh();
            konfirmationData.refresh();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private PdfItem getSelectedItem() {
        for (ListView<PdfItem> list :  allLists) {
            PdfItem pdfItem = list.getSelectionModel().getSelectedItem();

            if (pdfItem != null) {
                return pdfItem;
            }
        }
        return null;
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    // Skifter scenen til Admin Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Om.fxml");
    }

    // Skifter scenen til Admin Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Samlingen.fxml");
    }

    // Skiftet scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Temaer.fxml");
    }


    // Skifter scene til Admin Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Admin Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminForside.fxml");
    }

    // Skifter scene til Forsiden
    @FXML
    void logudKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}