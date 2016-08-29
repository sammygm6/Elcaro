/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elcaro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ARLV {

    ArrayList<String> listaCampos;
    String nombre;
    char tipoRegistro;
    int campos;
    String tecn;
    private File archivo;

    private RandomAccessFile RAF;

    public void crearArchivoLV(char tipoRegistro, String nombre, int campos, int tecnica, ArrayList<String> camposL) {
        File f = new File("./" + nombre + ".txt");
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            String nombreCampos = "";
            for (int i = 0; i < campos; i++) {
                nombreCampos += camposL.get(i) + ";";
            }
            nombreCampos = nombreCampos.substring(0, nombreCampos.length() - 1);
            wr.write(tipoRegistro + "-" + nombre + "-" + campos + "-" + tecnica + "-" + (nombreCampos.length() + 2) + ":");//escribimos en el archivo
            wr.append(nombreCampos + "\n");
            wr.close();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error, en la creacion del Archivo");
        };
    }

    public void insertarRegistro(String texto) {
        String[] especificos = texto.split(":");
        String[] codigo = especificos[0].split("-");
        String[] campos = especificos[1].split(";");
        /*
         formato codigo de inicio (V-Tabla1-5-1-0)
         ---Significado---
         1)Si es longitud variable o fija
         2)Nombre de la Tabla
         3)Numero de campos
         4)Tecnica de Registro
         1. Indice
         2. Delimitadores
         3. Key Value
         5)Primera posicion borrada
         Numero de Tecnicas
         */
        char tipoRegistro = codigo[0].charAt(0);
        String nombre = codigo[1];
        int Ncampos = Integer.parseInt(codigo[2]);
        int tecnica = Integer.parseInt(codigo[3]);
        int borrado = Integer.parseInt(codigo[4]);
        String[] registro = new String[Ncampos];
        for (int i = 0; i < Ncampos; i++) {
            registro[i] = JOptionPane.showInputDialog(this, "Ingresar " + campos[i]);
        }

        switch (tecnica) {
            case 1: {//indice

                break;
            }
            case 2: {//delimitadores

                break;
            }
            case 3: {//key value

                break;
            }
        }
    }

    /*
        File archivo = null;
        Scanner sc = null;
        try {
            archivo = new File("./"+nombre+".txt");
            sc = new Scanner(archivo);
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                System.out.println(sc.next());
                sc.nextInt();
                sc.next();
            }
        } catch (Exception e) {
        } finally {
            sc.close();
        }
     */
}
