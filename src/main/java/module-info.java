module com.example.eksamensprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.eksamensprojekt to javafx.fxml;
    exports com.example.eksamensprojekt;
}