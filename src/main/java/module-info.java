module com.example.eksamensprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;

    opens com.example.eksamensprojekt to javafx.fxml;
    exports com.example.eksamensprojekt;
    exports com.example.eksamensprojekt.controllers;
    opens com.example.eksamensprojekt.controllers to javafx.fxml;
    exports com.example.eksamensprojekt.controllers.admin;
    opens com.example.eksamensprojekt.controllers.admin to javafx.fxml;
    exports com.example.eksamensprojekt.undervisning;
    opens com.example.eksamensprojekt.undervisning to javafx.fxml;
    exports com.example.eksamensprojekt.ObjektKlasser;
    opens com.example.eksamensprojekt.ObjektKlasser to javafx.fxml;
    exports com.example.eksamensprojekt.Database;
    opens com.example.eksamensprojekt.Database to javafx.fxml;
}