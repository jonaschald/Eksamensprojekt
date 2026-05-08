package com.example.eksamensprojekt.undervisning;

import java.io.File;

public class PdfItem {
    private String name;
    private File pdfFile;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public File getpdfFile() {
        return pdfFile;
    }

    public void setpdfFile(File pdfFile) {
        this.pdfFile = pdfFile;
    }

    public PdfItem(String name, File pdfFile) {
        this.name = name;
        this.pdfFile = pdfFile;
    }

    @Override
    public String toString() {
        return name;
    }
}
