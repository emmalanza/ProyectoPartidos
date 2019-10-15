package com.company;

import com.company.logic.Logica;
import com.company.models.Partido;
import com.company.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DialogoPartido extends Stage {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private TextField txt_local;
    private TextField txt_visitante;
    private TextField txt_r1;
    private TextField txt_r2;
    private ComboBox division_cb;
    private DatePicker fecha_dp;
    private Button bttn_aceptar;

    //constructor para dar de alta un partido
    public DialogoPartido() {
        inicializaVista();
        bttn_aceptar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                addPartido(txt_local,txt_visitante,txt_r1,txt_r2,fecha_dp,division_cb);
                close();
            }
        });
    }

    //constructor para moodificar un partido
    public DialogoPartido(int indice, Partido partido){

        inicializaVista();
        txt_local.setText(partido.getLocal());
        txt_visitante.setText(partido.getVisitante());
        txt_r1.setText(String.valueOf(partido.getResul1()));
        txt_r2.setText(String.valueOf(partido.getResul2()));
        division_cb.getSelectionModel().select(partido.getDivision());
        LocalDate localD = Utils.convertiDate2LocalDate(partido.getFecha());
        fecha_dp.setValue(localD);

        bttn_aceptar.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {

            String local, visitante, div;
            int resul1, resul2;
            Date fecha = null;

            local = txt_local.getText();
            visitante = txt_visitante.getText();
            fecha = Utils.convertirLocalDate2Date(fecha_dp.getValue());
            resul1 = Integer.parseInt(txt_r1.getText());
            resul2 = Integer.parseInt(txt_r2.getText());
            div = (String) division_cb.getSelectionModel().getSelectedItem();

            Partido partidoM = new Partido(local,visitante,div,resul1,resul2,fecha);
            Logica.getInstance().modificarPartido(partidoM,indice);

            close();
        }
    });
}

    //metodo comun
    public void inicializaVista(){

        Label e_local = new Label("Nombre del equipo local:");
        txt_local = new TextField();

        Label e_visitante = new Label("Nombre del equipo visitante:");
        txt_visitante = new TextField();

        Label r1 = new Label("Resultado del equipo local:");
        txt_r1 = new TextField();

        Label r2 = new Label("Resultado del equipo visitante:");
        txt_r2 = new TextField();

        Label division = new Label("División:");
        ObservableList<String> listaDivision = FXCollections.observableArrayList();
        listaDivision.add("Primera");
        listaDivision.add("Segunda");
        listaDivision.add("Tercera");
        division_cb = new ComboBox<String>(listaDivision);

        Label lbl_fecha = new Label("Fecha:");
        fecha_dp = new DatePicker();

        //boton aceptar que tendra diferentes funcionalidades dependiendo
        //del constructor
        bttn_aceptar = new Button("ACEPTAR");

        Button bttn_cancelar = new Button("CANCELAR");
        bttn_cancelar.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                close();
            }
        });


        GridPane gridPane = new GridPane();

        gridPane.add(e_local,0,0,1,1);
        gridPane.add(txt_local, 1,0,1,1);

        gridPane.add(e_visitante, 0,1,1,1);
        gridPane.add(txt_visitante, 1,1,1,1);

        gridPane.add(r1, 0,2,1,1);
        gridPane.add(txt_r1, 1,2,1,1);

        gridPane.add(r2, 0,3,1,1);
        gridPane.add(txt_r2, 1,3,1,1);

        gridPane.add(division, 0,4,1,1);
        gridPane.add(division_cb, 1,4,1,1);

        gridPane.add(lbl_fecha, 0,5,1,1);
        gridPane.add(fecha_dp, 1,5,1,1);

        gridPane.add(bttn_aceptar, 0,6,1,1);
        gridPane.add(bttn_cancelar, 1,6,1,1);

        gridPane.setVgap(10);
        gridPane.setHgap(10);

        ImageView imageView = new ImageView(getClass().getResource("resources/images.jpg").toExternalForm());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100);

        VBox vBox = new VBox(imageView, gridPane);
        Scene scene = new Scene(vBox, 800, 800);
        setScene(scene);

    }

    //metodo que añade partidos a una lista
    public void addPartido(TextField txt_local, TextField txt_visitante, TextField txt_r1, TextField txt_r2,
                           DatePicker fecha_dp, ComboBox division_cb){

        String local, visitante, div;
        int resul1, resul2;
        Date fecha = null;

        local = txt_local.getText();
        visitante = txt_visitante.getText();
        fecha = Utils.convertirLocalDate2Date(fecha_dp.getValue());
        resul1 = Integer.parseInt(txt_r1.getText());
        resul2 = Integer.parseInt(txt_r2.getText());

        div = (String) division_cb.getSelectionModel().getSelectedItem();

        Partido p = new Partido(local, visitante, div, resul1, resul2, fecha);

        //a su vez llama al metodo de la clase Logica
        Logica.getInstance().addPartido(p);

    }
}


