package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.*;
import com.example.eksamensprojekt.undervisning.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminUndervisningController
{

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ListView<PdfItem> indskolingData;

    @FXML
    private ListView<PdfItem> mellemtrinData;

    @FXML
    private ListView<PdfItem> udskolingData;

    @FXML
    private ListView<PdfItem> konfirmationData;

    private List<ListView<PdfItem>> allLists;

    public void initialize() {
    // Gør så listerne viser undervisningsmaterialet
        indskolingData.setItems(DataDeling.indskolingList);
        mellemtrinData.setItems(DataDeling.mellemtrinList);
        udskolingData.setItems(DataDeling.udskolingList);
        konfirmationData.setItems(DataDeling.konfirmationList);

        allLists = List.of(
                indskolingData,
                mellemtrinData,
                udskolingData,
                konfirmationData
        );
    }

    @FXML
    void tilføjUndervisningsmateriale(ActionEvent event) {
        PdfItem item = new PdfItem("",null);

        showPdfDialog(item, false, false, false, false, false);
    }

    @FXML
    void redigerUndervisningsmatriale(ActionEvent event) {
        PdfItem item = getSelectedItem();
        if (item == null) {
            showError ("Vælg en fil først");
            return;
        }
        boolean inInskoling = indskolingData.getItems().contains(item);
        boolean inMellemtrin = mellemtrinData.getItems().contains(item);
        boolean inUdskoling = udskolingData.getItems().contains(item);
        boolean inKonfirmation = konfirmationData.getItems().contains(item);
        showPdfDialog(item, inInskoling, inMellemtrin, inUdskoling, inKonfirmation, true);
    }

    @FXML
    void sletUndervisningsmatriale(ActionEvent event) {
        PdfItem item = getSelectedItem();

        if(item == null) {
            showError("Vælg en fil først");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Er du sikker på at du vil slette filen?");
        alert.setHeaderText(null);

        alert.setContentText("Slet \"" + item.getName() + "\" ?");

        alert.showAndWait().ifPresent(result -> {
            if(result == ButtonType.OK) {
                indskolingData.getItems().remove(item);
                mellemtrinData.getItems().remove(item);
                udskolingData.getItems().remove(item);
                konfirmationData.getItems().remove(item);
            }
        });
    }

    private void showPdfDialog(
            PdfItem pdfItem,
            boolean inIndskoling,
            boolean inMellemtrin,
            boolean inUdskoling,
            boolean inKonfirmation,
            boolean editing
    )   {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(editing ? "Rediger PDF" : "Tilføj PDF");


        // Fil sti felt
        TextField fileField = new TextField();
        fileField.setEditable(false);

        // Navn på filen
        TextField nameField = new TextField();

        // Checkboksene
        CheckBox indskolingCheck = new CheckBox("Indskoling");
        CheckBox mellemtrinCheck = new CheckBox("Mellemtrin");
        CheckBox udskolingCheck = new CheckBox("Udskoling");
        CheckBox konfirmationCheck = new CheckBox("Konfirmation");

        // Knap til at vælge fil
        Button browseButton = new Button("Vælg fil");

        final File[] selectedFile = new File[1];

        // Hvis rediger, udfylder felterne for dig
        if(editing && pdfItem !=null) {
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

            Stage stage = (Stage) indskolingData.getScene().getWindow();

            File file = chooser.showOpenDialog(stage);

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

        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {

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

                indskolingData.refresh();
                mellemtrinData.refresh();
                udskolingData.refresh();
                konfirmationData.refresh();
            }
        });
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
}
