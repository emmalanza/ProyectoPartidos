package com.company;

import javafx.application.Application;
import javafx.stage.Stage;


public class PantallaPrincipal extends Application {

    public static void Main(String[] args) {

        launch(args);

    }

    public void start(Stage stage) throws Exception {

        stage = new Tabla();
        stage.setTitle("PARTIDOS DE RUGBY");
        stage.show();

    }
}
