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
import javax.swing.JFileChooser;

/**
 *
 * @author Admin
 */
public class Archivo {

    //formato de inicio (V-Tabla1-5-1)
    /*
    Numero de Tecnicas
    */
    public void crearArchivo (char tipoRegistro, String nombre, int campos, int tecnica ) {
        File f = new File("./"+nombre+".txt");
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(tipoRegistro+"-"+nombre+"-"+campos+"-"+tecnica+":");//escribimos en el archivo
            wr.close();
            bw.close();
        } catch (IOException e) {
        };
    }
    
}


