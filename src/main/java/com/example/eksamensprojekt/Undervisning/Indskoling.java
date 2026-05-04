package com.example.eksamensprojekt.Undervisning;

import java.io.File;
import java.net.URL;

public class Indskoling {
    private String name;
    private File pdfFile;

    public Indskoling(String name, File pdfFile) {
        this.name = name;
        this.pdfFile = pdfFile;
    }

    public String getName() {
        return name;
    }

    public File getPdfFile() { return pdfFile; }

    @Override
    public String toString() {
        return name;
    }
}
