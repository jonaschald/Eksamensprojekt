package com.example.eksamensprojekt.Undervisning;

import java.io.File;

public class Konfirmation {
    private String name;
    private File pdfFile;

    public Konfirmation (String name, File pdfFile) {
        this.name = name;
        this.pdfFile = pdfFile;
    }

    public String getName() {
        return name;
    }

    public File getPdfFile() {
        return pdfFile;
    }

    @Override
    public String toString() {
        return name;
    }
}
