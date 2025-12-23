module com.example.nypkentseldonusumtakip {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.nypkentseldonusumtakip to javafx.fxml;
    exports com.example.nypkentseldonusumtakip;
}