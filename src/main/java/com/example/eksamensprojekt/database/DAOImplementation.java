package com.example.eksamensprojekt.database;

import com.example.eksamensprojekt.objekter.AdminLogin;
import com.example.eksamensprojekt.objekter.Kunstværk;
import com.example.eksamensprojekt.objekter.Tema;
import com.example.eksamensprojekt.objekter.Undervisningsmateriale;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class DAOImplementation implements DAO {

    private SQLServerDataSource kilde;
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public DAOImplementation() {
        kilde = new SQLServerDataSource();
        kilde.setDatabaseName("EJMM_2SEM_EKSAMEN_2026");
        kilde.setUser("CS2025a_s_2");
        kilde.setPassword("CS2025aS2#23");
        kilde.setPortNumber(1433);
        kilde.setServerName("10.176.111.34");
        kilde.setTrustServerCertificate(true);

        System.out.println("Database: " + kilde.getDatabaseName());
    }


    @Override
    public boolean gemKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;

                    preparedStatement = forbindelse.prepareStatement("INSERT INTO Art_Pieces " +
                            "(ID, Serial_Number, Title, Year, Artist, Size_With_Frame, Size_Without_Frame, Description, Image_Data, ThemeID, Favorite) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    preparedStatement.setString(1, kunstværk.getId());
                    preparedStatement.setString(2, kunstværk.getSerieNummer());
                    preparedStatement.setString(3, kunstværk.getTitel());
                    preparedStatement.setInt(4, kunstværk.getÅrstal());
                    preparedStatement.setString(5, kunstværk.getKunstner());
                    preparedStatement.setString(6, kunstværk.getStørrelseMedRamme());
                    preparedStatement.setString(7, kunstværk.getStørrelseUdenRamme());
                    preparedStatement.setString(8, kunstværk.getBeskrivelse());
                    preparedStatement.setBytes(9, kunstværk.getBilledeData());
                    preparedStatement.setInt(10, kunstværk.getTemaId());
                    preparedStatement.setBoolean(11, kunstværk.isFavorit());

                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Fejl ved oprettelse af kunstværk i Databasen");
                    resultat.set(false);
                }
            }
        };

        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public void hentAlleKunstværker(ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Art_pieces");
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()) {
                        String id = resultSet.getString("ID");
                        String serieNummer = resultSet.getString("Serial_Number");
                        String titel = resultSet.getString("Title");
                        int årstal = resultSet.getInt("Year");
                        String kunstner = resultSet.getString("Artist");
                        String størrelseMedRamme = resultSet.getString("Size_With_Frame");
                        String størrelseUdenRamme = resultSet.getString("Size_Without_Frame");
                        String beskrivelse = resultSet.getString("Description");
                        byte[] billedeData = resultSet.getBytes("Image_Data");
                        int temaId = resultSet.getInt("ThemeID");
                        boolean favorit = resultSet.getBoolean("Favorite");

                        Kunstværk kunstværk = new Kunstværk(id, serieNummer, titel, kunstner, årstal,
                                størrelseMedRamme, størrelseUdenRamme, beskrivelse, billedeData, temaId, favorit);

                        kunstværker.add(kunstværk);
                    }

                    } catch (SQLException e) {
                        System.out.println("Fejl ved indlæsning af kunstværker fra Databasen");
                        throw new RuntimeException(e);
                    }
                }
            };

            Future future = executor.submit(runnable);
            future.get();
        }

    @Override
    public boolean sletKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void opdaterKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {

    }

    @Override
    public void søgKunstværk(String søgeTekst, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }

    @Override
    public void filtrerKunstværkerEfterÅrstal(int årstal, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }

    @Override
    public void hentKunstværkerEfterTema(int temaId, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean gemTema(Tema tema) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void hentAlleTemaer(ObservableList<Tema> temaer) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean sletTema(Tema tema) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void opdaterTema(Tema tema) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean gemUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void hentAlleUndervisningsmaterialer(ObservableList<Undervisningsmateriale> undervisningsmaterialer) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean sletUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void opdaterUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean login(String username, String password) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public boolean opretBruger(AdminLogin adminLogin) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void tilføjFavorit(int brugerID, int kunstværkID) throws ExecutionException, InterruptedException {

    }

    @Override
    public void fjernFavorit(int brugerID, int kunstværkID) throws ExecutionException, InterruptedException {

    }

    @Override
    public void hentFavoritter(int userID, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }
}
