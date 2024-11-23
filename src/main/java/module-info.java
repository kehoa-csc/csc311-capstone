module com.example.csc311capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires annotations;
    requires com.fasterxml.jackson.databind;
    requires java.sql;


    opens org.example.csc311capstone to javafx.fxml;
    exports org.example.csc311capstone;
    exports org.example.csc311capstone.db;
    opens org.example.csc311capstone.db to javafx.fxml;
    exports org.example.csc311capstone.Module;
    opens org.example.csc311capstone.Module to javafx.fxml;
}