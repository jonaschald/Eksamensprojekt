module com.example.eksamensprojekt {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.eksamensprojekt to javafx.fxml;
    exports com.example.eksamensprojekt;
    exports com.example.eksamensprojekt.controllers;
    opens com.example.eksamensprojekt.controllers to javafx.fxml;
    exports com.example.eksamensprojekt.controllers.admin;
    opens com.example.eksamensprojekt.controllers.admin to javafx.fxml;
}