package com.example.eksamensprojekt.undervisning;

public class Indskoling {
    private String name;

    public Indskoling(String name) {
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
