package com.example.eksamensprojekt.database;

import com.example.eksamensprojekt.objekter.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

// Interface klasse der beskriver hvilke database metoder DAOImplementation skal have
public interface DAO
{
    // Kunstværker
    public List<Kunstværk> hentAlleKunstværker() throws SQLException;

    // Temaer
    public boolean gemTema(Tema tema) throws ExecutionException, InterruptedException;
    public void hentAlleTemaer(ObservableList<Tema> temaer) throws ExecutionException, InterruptedException;
    public boolean sletTema(Tema tema) throws ExecutionException, InterruptedException;
    public void opdaterTema(Tema tema) throws ExecutionException, InterruptedException;
    public void hentKunstværkerEfterTema(int temaId, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;

    // Undervisningsmaterialer
    public void gemUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException;
    public void sletUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException;
    public void hentUndervisningsmateriale(ObservableList<Undervisningsmateriale> undervisningsmaterialer) throws ExecutionException, InterruptedException;

    // Brugere/Login
    public boolean login(String username, String password) throws ExecutionException, InterruptedException;
    public boolean opretBruger(AdminLogin adminLogin) throws ExecutionException, InterruptedException;

    // Favoritter
    public void tilføjFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public void fjernFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public List<Kunstværk> hentFavoritter() throws SQLException;

    // Metoder til redigering af tekst og billeder på Samlingens oversigt side i Admin
    public boolean gemKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public boolean sletKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;

    // Metoder til redigering af tekst og billeder på Forsiden Admin
    // Der skal laves database tabeller og database metoder til det

    // Metoder til redigering af tekst og billeder på Pop-up siden Admin (det enkelte kunstværks nummer, titel osv.)
    // Der skal laves database tabeller og database metoder til det
    public void opdaterKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;

    // Metoder til redigering af tekst og billeder i Om Samlingen Admin
    public void hentOmSamlingen(ObservableList<OmSamlingen> omSamlingen) throws ExecutionException, InterruptedException;
    public void opdaterOmSamlingen(OmSamlingen omSamlingen) throws ExecutionException, InterruptedException;

    // Metoder til redigering af tekst og billeder i Om Os Admin
    public List<OmOs> hentOmOs() throws SQLException;
    public void opdaterOmOs(OmOs omOs) throws ExecutionException, InterruptedException;
}