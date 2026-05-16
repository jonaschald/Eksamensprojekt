module com.example.eksamensprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires java.naming;
    requires javafx.base;

    opens com.example.eksamensprojekt to javafx.fxml;
    exports com.example.eksamensprojekt;
    exports com.example.eksamensprojekt.controllers;
    opens com.example.eksamensprojekt.controllers to javafx.fxml;
    exports com.example.eksamensprojekt.controllers.admin;
    opens com.example.eksamensprojekt.controllers.admin to javafx.fxml;
    exports com.example.eksamensprojekt.undervisning;
    opens com.example.eksamensprojekt.undervisning to javafx.fxml;
    exports com.example.eksamensprojekt.objekter;
    opens com.example.eksamensprojekt.objekter to javafx.fxml;
    exports com.example.eksamensprojekt.database;
    opens com.example.eksamensprojekt.database to javafx.fxml;
    exports com.example.eksamensprojekt.midlertidigeKlasser;
    opens com.example.eksamensprojekt.midlertidigeKlasser to javafx.fxml;
}