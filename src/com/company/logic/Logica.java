package com.company.logic;

import com.company.models.Partido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Logica {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static Logica INSTANCE = null;

    private Logica() {
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();
        return INSTANCE;
    }

    //creacion de listas sobre las que vamos a trabajar
    private ObservableList<Partido> listaPartidos = FXCollections.observableArrayList();
    private ArrayList<Partido> lista_ficheroL = new ArrayList<Partido>();
    private ArrayList<Partido> lista_ficheroE = new ArrayList<Partido>();

    FileInputStream fis;
    FileOutputStream fos;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    //metodo que añada partidos a una lista
    public void addPartido(Partido partido) {
        listaPartidos.add(partido);
    }

    //metodo que modifica partidos
    public void modificarPartido(Partido p, int indice) {
        listaPartidos.set(indice, p);
    }

    //metodo que borra partidos
    public void borrarPartido(int indice) {
        listaPartidos.remove(indice);
    }

    //metodo que devulve la lista principal
    public ObservableList getLista() {
        return listaPartidos;
    }

    //metodo que filtra partido por division
    public ObservableList filtrar(String filtro) {
        ObservableList<Partido> lista_filtro = FXCollections.observableArrayList();
        if (filtro.equals("Primera") || filtro.equals("Segunda") || filtro.equals("Tercera")) {
            for (int i = 0; i < listaPartidos.size(); i++) {
                if (listaPartidos.get(i).getDivision().equals(filtro)) {
                    lista_filtro.add(listaPartidos.get(i));
                }
            }
            return lista_filtro;
        } else {
            return listaPartidos;
        }
    }

    //metodo que escribe el fichero
    public void escribir_fichero(File fichero)  {

        for(int i = 0; i<listaPartidos.size(); i++){
            lista_ficheroE.add(listaPartidos.get(i));
        }

        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichero));
            oos.writeObject(lista_ficheroE);
        }catch (FileNotFoundException e){
        }catch (IOException e) {
        }finally{
            try {
                if (oos != null)
                oos.close();
                if (fos != null)
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //metodo que lee el fichero en caso de haberlo
    public void leer_fichero (File fichero){

            try {
                ois = new ObjectInputStream(new FileInputStream(fichero));
                lista_ficheroL = (ArrayList<Partido>)ois.readObject();
            } catch (EOFException e){
                e.printStackTrace();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                //todo mensaje
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (ois != null)
                    ois.close();
                    if (fis != null)
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for(int i = 0; i< lista_ficheroL.size(); i++){
            listaPartidos.add(lista_ficheroL.get(i));
        }
    }

}
