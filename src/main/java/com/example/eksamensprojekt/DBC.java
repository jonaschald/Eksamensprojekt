package com.example.eksamensprojekt;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBC {

    private SQLServerDataSource kilde;
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public DBC() {
        kilde = new SQLServerDataSource();
        kilde.setDatabaseName("EJMM_2SEM_EKSAMEN_2026");
        kilde.setUser("CS2025a_s_2");
        kilde.setPassword("CS2025aS2#23");
        kilde.setPortNumber(1433);
        kilde.setServerName("10.176.111.34");
        kilde.setTrustServerCertificate(true);
    }

}
