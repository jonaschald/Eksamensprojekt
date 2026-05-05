package com.example.eksamensprojekt.Undervisning;

public class Indskoling {
    private String name;
    private String pdfFile;

    public Indskoling(String name, String pdfFile) {
        this.name = name;
        this.pdfFile = pdfFile;
    }

    public String getName() {
        return name;
    }

    public String getPdfFile() { return pdfFile; }

    @Override
    public String toString() {
        return name;
    }
}
