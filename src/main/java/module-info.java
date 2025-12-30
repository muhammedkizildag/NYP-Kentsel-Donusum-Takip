module com.example.nypkentseldonusumtakip {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5; // <--- BU SATIRI EKLEYİN
    requires org.kordamp.bootstrapfx.core;
    requires atlantafx.base;
    requires java.sql;

    opens com.example.nypkentseldonusumtakip to javafx.fxml;
    exports com.example.nypkentseldonusumtakip;
}