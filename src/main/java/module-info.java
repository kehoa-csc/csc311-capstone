module com.example.csc311capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires annotations;


    opens org.example.csc311capstone to javafx.fxml;
    exports org.example.csc311capstone;
    exports org.example.csc311capstone.db;
    opens org.example.csc311capstone.db to javafx.fxml;
    exports org.example.csc311capstone.Module;
    opens org.example.csc311capstone.Module to javafx.fxml;
}