package com.example.eksamensprojekt.undervisning;

public class Konfirmation {
    private String name;

    public Konfirmation (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
