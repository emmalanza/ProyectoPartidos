package com.company.models;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    Scanner entradaT = new Scanner(System.in);
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {

        System.out.println("APLICACIÓN PARA LA GESTIÓN DE PARTIDOS DE RUGBY.");

        Main interfaz = new Main();

    }


    public Main() {

        Partido partido;
        ArrayList<Partido> partidos = new ArrayList<Partido>();


        File fichero = new File("C:\\Users\\Emma\\Desktop\\partidos.obj");

        try {
            if (fichero.exists()) {
                FileInputStream fis = new FileInputStream(fichero);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<Partido> lista_fichero = (ArrayList<Partido>) ois.readObject();
                mostrar_partidos(lista_fichero);
                ois.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        int opc;

        do {

            System.out.println("Elige una opción: "
                    + "\n1.Dar de alta un partido "
                    + "\n2.Mostrar partidos "
                    + "\n3.Borrar partidos"
                    + "\n4.Mostrar partidos ordenados por fecha "
                    + "\n5.Mostrar partidos de una división"
                    + "\n6.Salir");


            opc = entradaT.nextInt();
            entradaT.nextLine();

            switch (opc) {

                case 1:
                    partido = alta_partido();
                    partidos.add(partido);
                    break;

                case 2:
                    mostrar_partidos(partidos);
                    break;

                case 3:
                    borrar_partidos(partidos);
                    break;

                case 4:
                    mostrar_orden(partidos);
                    break;

                case 5:
                    mostrar_division(partidos);
                    break;


            }

        } while (opc != 6);

        System.out.println("GRACIAS");


        try {
            FileOutputStream fos = new FileOutputStream(fichero);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(partidos);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Partido alta_partido() {

        String local, visitante, div, fecha;
        int puntos1, puntos2;
        Date date = null;


        System.out.println("Dar de alta un partido.");

        System.out.println("Introduzca el nombre del equipo local: ");
        local = entradaT.nextLine();
        //entradaT.next();

        System.out.println("Introduzca el nombre del equipo visitante: ");
        visitante = entradaT.nextLine();


        do {
            System.out.println("Indique división: (PRIMERA/SEGUNDA/TERCERA)");
            div = entradaT.nextLine();

        } while (!div.equalsIgnoreCase("primera") && !div.equalsIgnoreCase("segunda")
                && !div.equalsIgnoreCase("tercera"));


        System.out.println("Indique resultado. ");
        System.out.println("Puntos del equipo local: ");
        puntos1 = entradaT.nextInt();
        System.out.println("Puntos del equipo visitante: ");
        puntos2 = entradaT.nextInt();
        entradaT.nextLine();
        System.out.println("Introduzca la fecha del partido. (Ejemplo: 6/06/2016) ");
        fecha = entradaT.nextLine();


        try {
            date = sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Partido partido = new Partido(local, visitante, div, puntos1, puntos2, date);

        return partido;
    }


    public void mostrar_partidos(ArrayList<Partido> partidos) {

        int size = partidos.size();

        if (size == 0) {

            System.out.println("No hay partidos que mostrar.");

        } else {

            System.out.println("Lista de partidos.");

            for (int i = 0; i < partidos.size(); i++) {

                System.out.println("Partido " + (i + 1));
                System.out.println("Equipo local: " + partidos.get(i).getLocal());
                System.out.println("Equipo visitante: " + partidos.get(i).getVisitante());
                System.out.println("División: " + partidos.get(i).getDivision());
                System.out.println("Marcador: " + partidos.get(i).getResul1() + "-" + partidos.get(i).getResul2());
                System.out.println("Fecha: " + sdf.format(partidos.get(i).getFecha()));

            }

        }
    }

    public void borrar_partidos(ArrayList<Partido> partidos) {

        mostrar_partidos(partidos);

        System.out.println("¿Qué partido desea borrar?");
        int partido = entradaT.nextInt();

        partidos.remove(partido - 1);

    }

    public void mostrar_orden(ArrayList<Partido> partidos) {

        String opcion;

        do {
            System.out.println("¿Desea ordenar la lista de manera descendente o ascendente?");
            opcion = entradaT.nextLine();
        } while (!opcion.equalsIgnoreCase("ascendente") && !opcion.equalsIgnoreCase("descendente"));

        if (opcion.equalsIgnoreCase("ascendente")) {

            Collections.sort(partidos);

            for (int i = 0; i < partidos.size(); i++) {

                System.out.println("Partido " + (i + 1));
                System.out.println("Equipo local: " + partidos.get(i).getLocal());
                System.out.println("Equipo visitante: " + partidos.get(i).getVisitante());
                System.out.println("División: " + partidos.get(i).getDivision());
                System.out.println("Marcador: " + partidos.get(i).getResul1() + "-" + partidos.get(i).getResul2());
                System.out.println("Fecha: " + sdf.format(partidos.get(i).getFecha()));

            }
        } else {

            Comparator<Partido> comparador = Collections.reverseOrder();
            Collections.sort(partidos, comparador);

            for (int i = 0; i < partidos.size(); i++) {

                System.out.println("Partido " + (i + 1));
                System.out.println("Equipo local: " + partidos.get(i).getLocal());
                System.out.println("Equipo visitante: " + partidos.get(i).getVisitante());
                System.out.println("División: " + partidos.get(i).getDivision());
                System.out.println("Marcador: " + partidos.get(i).getResul1() + "-" + partidos.get(i).getResul2());
                System.out.println("Fecha: " + partidos.get(i).getFecha());

            }
        }

    }

    public void mostrar_division(ArrayList<Partido> partidos) {

        System.out.println("Mostrar partidos de una división.");

        String div, div2;
        int x = 0;

        do {
            System.out.println("Introduzca la división de la que se desean mostrar los partidos:"
                    + "(PRIMERA/SEGUNDA/TERCERA)");
            div = entradaT.nextLine();

        } while (!div.equalsIgnoreCase("primera") && !div.equalsIgnoreCase("segunda")
                && !div.equalsIgnoreCase("tercera"));


        for (int i = 0; i < partidos.size(); i++) {

            div2 = partidos.get(i).getDivision();

            if (div.equalsIgnoreCase(div2)) {

                System.out.println("Partido " + (i + 1));
                System.out.println("Equipo local: " + partidos.get(i).getLocal());
                System.out.println("Equipo visitante: " + partidos.get(i).getVisitante());
                System.out.println("División: " + partidos.get(i).getDivision());
                System.out.println("Marcador: " + partidos.get(i).getResul1() + "-" + partidos.get(i).getResul2());
                System.out.println("Fecha: " + sdf.format(partidos.get(i).getFecha()));

                x++;

            }

        }

        if (x == 0)
            System.out.println("No hay partidos correspondientes a esa división.");

    }

}



