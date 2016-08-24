package elcaro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ARLF {

    private String data;
    private int size;
    private File archivo;
    private boolean llave;

    public ARLF() {
    }

    public ARLF(int size) {
        this.size = size;
    }

    public ARLF(File registro) {
        this.archivo = registro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public boolean isLlave() {
        return llave;
    }

    public void setLlave(boolean llave) {
        this.llave = llave;
    }

    public void agregarRegistro(ArrayList campos) {
        String datos = "";
        for (int i = 0; i < campos.size(); i++) {
            datos += campos.get(i).toString();
        }
        appendArchivo(datos);
    }

    public void appendArchivo(String datos) {
        try {
            FileWriter writer = new FileWriter(archivo.getAbsolutePath(), true);
            writer.write(datos);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
