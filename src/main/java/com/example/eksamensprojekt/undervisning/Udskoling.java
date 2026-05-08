package com.example.eksamensprojekt.undervisning;

public class Udskoling {
    private String name;

    public Udskoling(String name) {
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
