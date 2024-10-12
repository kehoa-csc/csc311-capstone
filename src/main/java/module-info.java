module com.example.csc311capstone{
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.csc311capstone to javafx.fxml;
    exports com.example.csc311capstone;
}