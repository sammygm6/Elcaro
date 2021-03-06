/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elcaro;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class ARLV {

    char tipoRegistro;
    String nombre;
    int campos;
    int posicionInicial;
    int tecnica;
    String[] listaCampos;

    private File archivo;

    private RandomAccessFile RAF;

    public ARLV() {
    }

    public ARLV(char tipoRegistro, String nombre, int campos, int tecnica, String[] listaCampos) {
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
        return posicionInicial;
    }

    public void setBorrado(int borrado) {
        this.posicionInicial = borrado;
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
            String concat = tipoRegistro + "-" + nombre + "-" + campos + "-" + tecnica + "-:" + nombreCampos + "|";
            if (concat.length() < 100) {
                wr.write(tipoRegistro + "-" + nombre + "-" + campos + "-" + tecnica + "-" + (concat.length() + 3) + ":" + nombreCampos);//escribimos en el archivo
            } else {
                wr.write(tipoRegistro + "-" + nombre + "-" + campos + "-" + tecnica + "-" + (concat.length() + 4) + ":" + nombreCampos);//escribimos en el archivo
            }
            wr.append(System.lineSeparator());
            wr.close();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error, en la creacion del Archivo");
        };
    }

    public void seleccionarArchivo(File archivo, JTable jTable) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea = br.readLine();
            if (linea.charAt(0) != 'V') {
                System.out.println("El archivo no pertenece a longitud variable");
                JOptionPane.showMessageDialog(null, "El archivo no pertenece a longitud variable");
            } else if (linea != null) {
                datosClase(linea, jTable);
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

    public void datosClase(String texto, JTable jTable) throws IOException {
        String[] especificos = texto.split(":");
        String[] codigo = especificos[0].split("-");
        listaCampos = especificos[1].split(";");
        tipoRegistro = codigo[0].charAt(0);
        nombre = codigo[1];
        campos = Integer.parseInt(codigo[2]);
        tecnica = Integer.parseInt(codigo[3]);
        posicionInicial = Integer.parseInt(codigo[4]);
        archivo = new File("./" + nombre + ".txt", "txt");
        getJTable(jTable);
    }

    public void agregarRegistro(String[] registro) throws IOException {
        String concat = "";
        System.out.println("    TECNICAA    ");
        if (tecnica == 1 || tecnica == 2) {
            for (int i = 0; i < campos; i++) {
                concat += registro[i] + ";";
            }
            concat += "\r\n";
            if (tecnica == 1) {
                File f = new File("./I" + nombre + ".txt");
                File f2 = new File("./" + nombre + ".txt");
                try {
                    FileWriter w = new FileWriter(f);
                    BufferedWriter bw = new BufferedWriter(w);
                    PrintWriter wr = new PrintWriter(bw);
                    
                    RandomAccessFile raf2 = new RandomAccessFile("./" + nombre + ".txt", "rw");
                    raf2.seek(0);
                    String linea2;
                    int num = 0;
                    while ((linea2 = raf2.readLine()) != null) {
                        num += linea2.length() + 2;
                        wr.write(nombre + "-" + num);//escribimos en el archivo

                    }
                    
                    wr.append(System.lineSeparator());
                    wr.close();
                    bw.close();
                } catch (IOException e) {
                    System.out.println("Error, en abrir el indice");
                };

            } else {
                System.out.println("Metodo 2");
                for (int i = 0; i < campos; i++) {
                    concat += registro[i] + ";";
                }
                concat += "\r\n";
                File f = new File("./" + nombre + ".txt");
                try {
                    RandomAccessFile raf2 = new RandomAccessFile(f, "rw");
                    raf2.seek(0);
                    String linea2;
                    int num = 0;
                    while ((linea2 = raf2.readLine()) != null) {
                        num += linea2.length() + 2;
                    }
                    raf2.seek(num);
                    raf2.writeBytes(concat + "\r\n ");
                } catch (IOException e) {
                    System.out.println("Error, crear key value");
                };
            }
        } else if (tecnica == 3) {
            for (int i = 0; i < campos; i++) {
                concat += listaCampos[i] + "=" + registro[i] + ";";
            }
            concat += "\r\n";
            File f = new File("./" + nombre + ".txt");
            try {
                RandomAccessFile raf2 = new RandomAccessFile(f, "rw");
                raf2.seek(0);
                String linea2;
                int num = 0;
                while ((linea2 = raf2.readLine()) != null) {
                    num += linea2.length() + 2;
                }
                raf2.seek(num);
                raf2.writeBytes(concat + "\r\n ");
            } catch (IOException e) {
                System.out.println("Error, crear key value");
            };
        }
        
    }

    public void Posicion() {
        int pos = 0;
        int nGuiones = 0;
        long size = 0;
        try {
            RAF = new RandomAccessFile(archivo, "rw");
            size = RAF.length();
            for (int i = 0; i < RAF.length(); i++) {
                RAF.seek(i);
                char delim = (char) RAF.readByte();
                if (delim == '-') {
                    nGuiones++;
                }
                if (nGuiones == 4) {
                    pos = i + 1;
                    nGuiones++;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error en Segundo try catch");
        }
    }

    private void getJTable(JTable jTable) throws IOException {
        DefaultTableModel contactTableModel = (DefaultTableModel) jTable
                .getModel();
        contactTableModel.setColumnCount(campos);
        contactTableModel.setColumnIdentifiers(listaCampos);
        jTable.setModel(contactTableModel);
        setUpTableData(jTable);
    }

    public void setUpTableData(JTable jTable) throws FileNotFoundException, IOException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        archivo = new File("./" + nombre + ".txt");
        if (tecnica == 1 || tecnica == 2) {
            RAF = new RandomAccessFile(this.archivo, "rw");
            RAF.seek(0);

            String linea = RAF.readLine();
            while ((linea = RAF.readLine()) != null) {
                String[] data = linea.split(";");
                tableModel.addRow(data);
            }
        } else if (tecnica == 3) {
            RAF = new RandomAccessFile(archivo, "rw");
            RAF.seek(0);

            String linea = RAF.readLine();
            while ((linea = RAF.readLine()) != null) {
                String[] data = linea.split(";");
                String[] camps = new String[campos];
                for (int i = 0; i < data.length; i++) {
                    camps[i] = (data[i].split("="))[1];
                }
                tableModel.addRow(data);
            }
        }
        jTable.setModel(tableModel);
    }
    //"\r\n"

}
