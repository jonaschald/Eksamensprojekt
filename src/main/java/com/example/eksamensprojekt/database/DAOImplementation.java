package com.example.eksamensprojekt.database;

import com.example.eksamensprojekt.objekter.AdminLogin;
import com.example.eksamensprojekt.objekter.Kunstværk;
import com.example.eksamensprojekt.objekter.Tema;
import com.example.eksamensprojekt.objekter.Undervisningsmateriale;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.ObservableList;

import java.io.*;
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
                    throw new RuntimeException(e);
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
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Art_pieces");
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
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
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("DELETE FROM Art_Pieces WHERE ID = ?");
                    preparedStatement.setString(1, kunstværk.getId());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Sletning af kunstværket i databasen lykkes ikke");
                    resultat.set(false);
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public void opdaterKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Art_Pieces SET " + "Serial_Number = ?, " +
                            "Title = ?, " + "Year = ?, " + "Artist = ?, " + "Size_With_Frame = ?, " + "Size_Without_Frame = ?, " +
                            "Description = ?, " + "Image_Data = ?, " + "ThemeID = ?, " + "Favorite = ? " + "WHERE ID = ?");

                    preparedStatement.setString(1, kunstværk.getSerieNummer());
                    preparedStatement.setString(2, kunstværk.getTitel());
                    preparedStatement.setInt(3, kunstværk.getÅrstal());
                    preparedStatement.setString(4, kunstværk.getKunstner());
                    preparedStatement.setString(5, kunstværk.getStørrelseMedRamme());
                    preparedStatement.setString(6, kunstværk.getStørrelseUdenRamme());
                    preparedStatement.setString(7, kunstværk.getBeskrivelse());
                    preparedStatement.setBytes(8, kunstværk.getBilledeData());
                    preparedStatement.setInt(9, kunstværk.getTemaId());
                    preparedStatement.setBoolean(10, kunstværk.isFavorit());
                    preparedStatement.setString(11, kunstværk.getId());

                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Opdatering af kunstværket i databasen lykkes ikke");
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
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
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("INSERT INTO Themes (ID, Theme_Name) VALUES (?, ?)");
                    preparedStatement.setInt(1, tema.getId());
                    preparedStatement.setString(2, tema.getNavn());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Oprettelse af tema i databasen lykkes ikke");
                    resultat.set(false);
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public void hentAlleTemaer(ObservableList<Tema> temaer) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Themes");
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String temaNavn = resultSet.getString("Theme_Name");

                        Tema tema = new Tema(id, temaNavn);
                        temaer.add(tema);
                    }
                } catch (SQLException e) {
                    System.out.println("Fejl ved indlæsning af temaer fra databasen");
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public boolean sletTema(Tema tema) throws ExecutionException, InterruptedException {

        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("DELETE FROM Themes WHERE ID = ?");
                    preparedStatement.setInt(1, tema.getId());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Sletning af tema lykkes ikke i databasen");
                    resultat.set(false);
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public void opdaterTema(Tema tema) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Themes SET " + "Theme_Name = ? " + "WHERE ID = ?");
                    preparedStatement.setString(1, tema.getNavn());
                    preparedStatement.setInt(2, tema.getId());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Opdatering af tema lykkes ikke i databasen");
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public boolean gemUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("INSERT INTO Teaching_Materials " +
                            "(Title, PDF_Data, Target_Group_ID) " + "VALUES (?, ?, ?)");

                    FileInputStream inputStream = new FileInputStream(undervisningsmateriale.getPdf());


                    preparedStatement.setString(1, undervisningsmateriale.getTitle());
                    preparedStatement.setBinaryStream(2, inputStream, undervisningsmateriale.getPdf().length());
                    preparedStatement.setInt(3, undervisningsmateriale.getMålgruppeId());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Fejl ved oprettelse af pdf i databasen");
                    resultat.set(false);
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public boolean sletUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("DELETE FROM Teaching_Materials WHERE ID = ?");
                    preparedStatement.setInt(1, undervisningsmateriale.getId());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Sletning af undervisningsmateriale i databasen lykkes ikke");
                    resultat.set(false);
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public void hentUndervisningsmateriale(ObservableList<Undervisningsmateriale> undervisningsmaterialer) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Teaching_Materials");

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String title = resultSet.getString("Title");
                        byte[] pdfData = resultSet.getBytes("PDF_Data");
                        int målgruppeId = resultSet.getInt("Target_Group_ID");

                        File pdfFile = File.createTempFile("pdf_", ".pdf");
                        FileOutputStream outputStream = new FileOutputStream(pdfFile);
                        outputStream.write(pdfData);
                        outputStream.close();

                        Undervisningsmateriale undervisningsmateriale = new Undervisningsmateriale(id, title, pdfFile, målgruppeId);
                        undervisningsmaterialer.add(undervisningsmateriale);
                    }
                } catch (SQLException e) {
                    System.out.println("Fejl ved indlæsning af undervisningsmaterialer fra databasen");
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public void opdaterUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Teaching_Materials SET " + "Title = ?, "
                    + "PDF_Data = ?, " + "Target_Group_ID = ? " + "WHERE ID = ?");

                    FileInputStream inputStream = new FileInputStream(undervisningsmateriale.getPdf());

                    preparedStatement.setString(1, undervisningsmateriale.getTitle());
                    preparedStatement.setBinaryStream(2, inputStream, undervisningsmateriale.getPdf().length());
                    preparedStatement.setInt(3, undervisningsmateriale.getMålgruppeId());
                    preparedStatement.setInt(4, undervisningsmateriale.getId());
                    preparedStatement.executeUpdate();

                } catch( SQLException e) {
                    System.out.println("Opdatering af undervisningsmateriale i databasen lykkes ikke");
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
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
    public void tilføjFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Art_Pieces SET Favorite = ? WHERE ID = ?");
                    preparedStatement.setBoolean(1, true);
                    preparedStatement.setString(2, kunstværk.getId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Fejl ved tilføjelse af favorit i databasen");
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public void fjernFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Art_Pieces SET Favorite = ? WHERE ID = ?");
                    preparedStatement.setBoolean(1, false);
                    preparedStatement.setString(2, kunstværk.getId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Fejl ved fjernelse af favorit i databasen");
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public void hentFavoritter( ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try(Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Art_Pieces WHERE Favorite = 1");
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
                    System.out.println("Fejl ved hentning af favoritter i databasen");
                    throw new RuntimeException(e);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }
}