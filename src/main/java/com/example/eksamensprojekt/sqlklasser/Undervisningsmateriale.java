package com.example.eksamensprojekt.sqlklasser;

import java.io.File;

public class Undervisningsmateriale {
    private int id;
    private String title;
    private File pdf;
    private int målgruppeId;

    public  Undervisningsmateriale(int id, String title) {
        this.id = id;
        this.title = title;
        this.pdf = pdf;
        this.målgruppeId = målgruppeId;
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

    public File getPdf() {
        return pdf;
    }
    public void setPdf(File pdf) {
        this.pdf = pdf;
    }

    public int getMålgruppeId() {
        return målgruppeId;
    }

    public void setMålgruppeId(int målgruppeId) {
        this.målgruppeId = målgruppeId;
    }
}
