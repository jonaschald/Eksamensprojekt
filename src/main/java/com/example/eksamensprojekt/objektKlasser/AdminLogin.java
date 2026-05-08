package com.example.eksamensprojekt.objektKlasser;

public class AdminLogin {
    private String email;
    private String password;
    private String passwordHash;

    public AdminLogin(String email, String password, String passwordHash) {
        this.email = email;
        this.password = password;
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
