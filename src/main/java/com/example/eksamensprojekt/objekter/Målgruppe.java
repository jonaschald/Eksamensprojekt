package com.example.eksamensprojekt.objekter;

public class Målgruppe
{
    private int id;
    private String type;

    public Målgruppe(int id, String type)
    {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    // Metode til at konvertere Id mellem Java og databasen
    // Finder målgruppe-ID (database) ud fra checkboxens ID (Java)
    public static int convertToId(String checkboxId)
    {
        if (checkboxId.equals("indskolingCheck")) {
            return 1;
        }
        if (checkboxId.equals("mellemtrinCheck")) {
            return 2;
        }
        if (checkboxId.equals("udskolingCheck")) {
            return 3;
        }
        if (checkboxId.equals("konfirmationCheck")) {
            return 4;
        }
        return 0;
    }
}