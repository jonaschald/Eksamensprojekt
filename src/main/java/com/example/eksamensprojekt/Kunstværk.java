package com.example.eksamensprojekt;

public class Kunstværk
{
    private String nummer;
    private String trykUdAf;
    private String titel;
    private String kunstner;
    private String år;
    private String størrelseMedRamme;
    private String størrelseUdenRamme;
    private String beskrivelse;
    private String billedeSti;

    // Konstruktør
    public Kunstværk (String nummer, String trykUdAf, String titel, String kunstner, String år, String størrelseMedRamme, String størrelseUdenRamme, String beskrivelse, String billedeSti) {
        this.nummer = nummer;
        this.trykUdAf = trykUdAf;
        this.titel = titel;
        this.kunstner = kunstner;
        this.år = år;
        this.størrelseMedRamme = størrelseMedRamme;
        this.størrelseUdenRamme = størrelseUdenRamme;
        this.beskrivelse = beskrivelse;
        this.billedeSti = billedeSti;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getTrykUdAf() {
        return trykUdAf;
    }

    public void setTrykUdAf(String trykUdAf) {
        this.trykUdAf = trykUdAf;
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

    public String getÅr() {
        return år;
    }

    public void setÅr(String år) {
        this.år = år;
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

    public String getBilledeSti() {
        return billedeSti;
    }

    public void setBilledeSti(String billedeSti) {
        this.billedeSti = billedeSti;
    }
}