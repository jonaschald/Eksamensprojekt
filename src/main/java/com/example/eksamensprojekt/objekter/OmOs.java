package com.example.eksamensprojekt.objekter;

public class OmOs
{
    private int id;
    private String titel;
    private String beskrivelse;
    private String adresse;
    private String telefonnummer;
    private String email;
    private String åbningstider;
    private byte[] image1;
    private byte[] image2;
    private byte[] image3;

    public OmOs(int id, String titel, String beskrivelse, String adresse,
                String telefonnummer, String email, String åbningstider, byte[] image1, byte[] image2, byte[] image3)
    {
        this.id = id;
        this.titel = titel;
        this.beskrivelse = beskrivelse;
        this.adresse = adresse;
        this.telefonnummer = telefonnummer;
        this.åbningstider = åbningstider;
        this.email = email;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getÅbningstider() {return åbningstider;}

    public void setÅbningstider(String åbningstider) {this.åbningstider = åbningstider;}

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public byte[] getImage3() {
        return image3;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }
}