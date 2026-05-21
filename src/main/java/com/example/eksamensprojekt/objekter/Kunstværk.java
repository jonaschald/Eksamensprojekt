package com.example.eksamensprojekt.objekter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class Kunstværk
{
    private String id;
    private String serieNummer;
    private String titel;
    private String kunstner;
    private int årstal;
    private String størrelseMedRamme;
    private String størrelseUdenRamme;
    private String beskrivelse;
    private byte[] billedeData;
    private int temaId;
    private boolean favorit;

    // Vi bruger et ObjectProperty så vi kan nemt lytter efter om billedet har loadet ind eller ej
    private final ObjectProperty<Image> image = new SimpleObjectProperty<Image>();

    // Konstruktør
    public Kunstværk (String id, String serieNummer, String titel, String kunstner, int årstal,
                      String størrelseMedRamme, String størrelseUdenRamme, String beskrivelse,
                      byte[] billedeData, int  temaId, boolean favorit)
    {
        this.id = id;
        this.serieNummer = serieNummer;
        this.titel = titel;
        this.kunstner = kunstner;
        this.årstal = årstal;
        this.størrelseMedRamme = størrelseMedRamme;
        this.størrelseUdenRamme = størrelseUdenRamme;
        this.beskrivelse = beskrivelse;
        this.billedeData = billedeData;
        this.temaId = temaId;
        this.favorit = favorit;

        loadBilledeData();
    }

    private void loadBilledeData() {
        if (billedeData == null) {
            return;
        }

        // Henter billedet/kunstværket fra Databasen
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.billedeData);
        Image img = new Image(byteArrayInputStream);

        this.image.set(img);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getSerieNummer() {
        return serieNummer;
    }
    public void setSerieNummer(String serieNummer) {
        this.serieNummer = serieNummer;
    }

    public String getTitel() {
        return titel;
    }
    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getKunstner() {
        return kunstner;
    }
    public void setKunstner(String kunstner) {
        this.kunstner = kunstner;
    }

    public int getÅrstal() {
        return årstal;
    }
    public void setÅrstal(int årstal) {
        this.årstal = årstal;
    }

    public String getStørrelseMedRamme() {
        return størrelseMedRamme;
    }
    public void setStørrelseMedRamme(String størrelseMedRamme) {
        this.størrelseMedRamme = størrelseMedRamme;
    }

    public String getStørrelseUdenRamme() {
        return størrelseUdenRamme;
    }
    public void setStørrelseUdenRamme(String størrelseUdenRamme) {
        this.størrelseUdenRamme = størrelseUdenRamme;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }
    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public byte[] getBilledeData() {
        return billedeData;
    }
    public void setBilledeData(byte[] billedeData) {
        this.billedeData = billedeData;
        loadBilledeData();
    }

    public Image getImage() {
        return this.image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return this.image;
    }

    public int getTemaId() {
        return temaId;
    }
    public void setTemaId(int temaId) {
        this.temaId = temaId;
    }

    public boolean isFavorit() {
        return favorit;
    }
    public void setFavorit(boolean favorit) {
        this.favorit = favorit;
    }
}