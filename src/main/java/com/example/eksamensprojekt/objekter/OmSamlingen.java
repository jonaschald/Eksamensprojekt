package com.example.eksamensprojekt.objekter;

public class OmSamlingen
{
    private int id;
    private String title;
    private String beskrivelse;
    private byte[] image1;
    private byte[] image2;

    public OmSamlingen(int id, String title, String beskrivelse, byte[] image1, byte[] image2)
    {
        this.id = id;
        this.title = title;
        this.beskrivelse = beskrivelse;
        this.image1 = image1;
        this.image2 = image2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

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
}