package com.example.eksamensprojekt.Undervisning;

import java.io.File;

public class Mellemtrin {
    private String name;
    private File pdfFile;

    public Mellemtrin(String name, File pdfFile) {
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
