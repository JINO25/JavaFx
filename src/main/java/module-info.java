module com.example.projectjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires kernel;
    requires layout;


    opens com.example.projectjavafx to javafx.fxml;
    exports com.example.projectjavafx;
    exports com.example.projectjavafx.Models;
}