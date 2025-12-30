package com.example.nypkentseldonusumtakip;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import atlantafx.base.theme.PrimerLight;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("a.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Kentsel Dönüşüm Takip Sistemi");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        db.createNewTable();
        launch();
    }
}