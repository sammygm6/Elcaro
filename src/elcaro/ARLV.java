/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elcaro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
char tipoRegistro;
    String nombre;
    int campos;
    int borrado;
    int tecnica;
    String [] listaCampos;
    
    private File archivo;

    private RandomAccessFile RAF;
    
    public ARLV(){
    }

    public ARLV(char tipoRegistro, String nombre, int campos,  int tecnica, String [] listaCampos) {
        this.tipoRegistro = tipoRegistro;
        this.nombre = nombre;
        this.campos = campos;
        this.tecnica = tecnica;
        this.listaCampos = listaCampos;
    }

    public char getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(char tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCampos() {
        return campos;
    }

    public void setCampos(int campos) {
        this.campos = campos;
    }

    public int getBorrado() {
        return borrado;
    }

    public void setBorrado(int borrado) {
        this.borrado = borrado;
    }

    public int getTecnica() {
        return tecnica;
    }

    public void setTecnica(int tecnica) {
        this.tecnica = tecnica;
    }

    public String[] getListaCampos() {
        return listaCampos;
    }

    public void setListaCampos(String[] listaCampos) {
        this.listaCampos = listaCampos;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public RandomAccessFile getRAF() {
        return RAF;
    }

    public void setRAF(RandomAccessFile RAF) {
        this.RAF = RAF;
    }
    
    public void crearArchivoLV() {
        File f = new File("./" + nombre + ".txt");
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            String nombreCampos = "";
            for (int i = 0; i < campos; i++) {
                nombreCampos += listaCampos[i] + ";";
            }
            nombreCampos = nombreCampos.substring(0, nombreCampos.length() - 1);
            wr.write(tipoRegistro + "-" + nombre + "-" + campos + "-" + tecnica + "-00:");//escribimos en el archivo
            wr.append(nombreCampos + "\n");
            wr.close();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error, en la creacion del Archivo");
        };
    }

    public void seleccionarArchivo(File archivo) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            if ((linea = br.readLine()) != null) {
                insertarRegistro(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertarRegistro(String texto) {
        String[] especificos = texto.split(":");
        String[] codigo = especificos[0].split("-");
        listaCampos = especificos[1].split(";");

        tipoRegistro = codigo[0].charAt(0);
        nombre = codigo[1];
        campos = Integer.parseInt(codigo[2]);
        tecnica = Integer.parseInt(codigo[3]);
        borrado = Integer.parseInt(codigo[4]);
        archivo = new File("./"+nombre+".txt", "txt");
        String registro = "";
        for (int i = 0; i < campos; i++) {
            registro+= JOptionPane.showInputDialog(this, "Ingresar " + listaCampos[i]);
        }
        
        try {
            FileWriter w = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(registro + "\n");//escribimos en el archivo
            wr.close();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error, en la creacion del Archivo");
        };
        
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
