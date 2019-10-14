package com.company;

import com.company.logic.Logica;
import com.company.models.Partido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;

public class Tabla extends Stage {

    public Tabla() {

        Logica logica = Logica.getInstance();
        TableView tabla;
        File fichero;

        fichero = new File("/Users/mipc/Desktop/fichero.dat");

        //logica.leer_fichero(fichero);

        ComboBox cb_filtrar;
        ObservableList<String> listaDivision = FXCollections.observableArrayList();
        listaDivision.add("Todas");
        listaDivision.add("Primera");
        listaDivision.add("Segunda");
        listaDivision.add("Tercera");
        cb_filtrar = new ComboBox<String>(listaDivision);
        cb_filtrar.getSelectionModel().selectFirst();


        tabla = new TableView(logica.getLista());
        TableColumn<String, Partido> column1 = new TableColumn<>("Equipo local");
        column1.setCellValueFactory(new PropertyValueFactory<>("local"));
        TableColumn<String, Partido> column2 = new TableColumn<>("Equipo visitante");
        column2.setCellValueFactory(new PropertyValueFactory<>("visitante"));
        TableColumn<String, Partido> column3 = new TableColumn<>("Marcador");
        column3.setCellValueFactory(new PropertyValueFactory<>("marcador"));
        TableColumn<String, Partido> column4 = new TableColumn<>("Fecha");
        column4.setCellValueFactory(new PropertyValueFactory<>("Sfecha"));
        TableColumn<String, Partido> column5 = new TableColumn<>("División");
        column5.setCellValueFactory(new PropertyValueFactory<>("division"));
        tabla.getColumns().add(column1);
        tabla.getColumns().add(column2);
        tabla.getColumns().add(column3);
        tabla.getColumns().add(column4);
        tabla.getColumns().add(column5);
        column1.setMinWidth(100);
        column1.setMaxWidth(100);
        column2.setMinWidth(110);
        column2.setMaxWidth(110);
        column3.setMinWidth(100);
        column3.setMaxWidth(100);
        column4.setMinWidth(100);
        column4.setMaxWidth(100);
        column5.setMinWidth(100);
        column5.setMaxWidth(100);

        Button bttn_alta = new Button("ALTA PARTIDO");
        bttn_alta.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage dialogo_partido = new DialogoPartido();
                dialogo_partido.show();
            }
        });

        Button bttn_modif = new Button("MODIFICAR PARTIDO");
        bttn_modif.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int indiceM = tabla.getSelectionModel().getSelectedIndex();
                Partido partido_seleccionado = (Partido) Logica.getInstance().getLista().get(indiceM);
                Stage dialogo_partido = new DialogoPartido(indiceM,partido_seleccionado);
                dialogo_partido.show();
            }
        });

        Button bttn_baja = new Button("BAJA PARTIDO");
        bttn_baja.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int indiceBorrar = tabla.getSelectionModel().getSelectedIndex();
                if(indiceBorrar>=0)
                    logica.borrarPartido(indiceBorrar);
                    //todo mostrar mensaje al usuario
            }
        });

       cb_filtrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String filtro = (String) cb_filtrar.getSelectionModel().getSelectedItem();
                tabla.setItems(logica.filtrar(filtro));
                //todo solucionar error borrado
            }
        });

        Button bttn_salir = new Button("SALIR");
        bttn_salir.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                close();
                logica.escribir_fichero(fichero);
            }
        });


        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setBottomAnchor(tabla,50d);
        AnchorPane.setTopAnchor(tabla, 50d);
        AnchorPane.setLeftAnchor(tabla, 20d);
        AnchorPane.setRightAnchor(tabla, 20d);
        AnchorPane.setBottomAnchor(bttn_alta,20d);
        AnchorPane.setLeftAnchor(bttn_alta, 20d);
        AnchorPane.setBottomAnchor(bttn_modif,20d);
        AnchorPane.setLeftAnchor(bttn_modif, 200d);
        AnchorPane.setBottomAnchor(bttn_baja,20d);
        AnchorPane.setRightAnchor(bttn_baja, 20d);
        AnchorPane.setLeftAnchor(cb_filtrar, 20d);
        AnchorPane.setTopAnchor(cb_filtrar, 20d);
        AnchorPane.setBottomAnchor(bttn_salir,20d);
        AnchorPane.setRightAnchor(bttn_salir, 20d);

        anchorPane.getChildren().add(tabla);
        anchorPane.getChildren().add(cb_filtrar);
        anchorPane.getChildren().add(bttn_alta);
        anchorPane.getChildren().add(bttn_baja);
        anchorPane.getChildren().add(bttn_modif);
        anchorPane.getChildren().add(bttn_salir);

        Scene scene = new Scene(anchorPane, 800,500);
        setScene(scene);



    }
}