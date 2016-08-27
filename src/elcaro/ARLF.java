package elcaro;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public class ARLF {

    private String data;
    private int size;
    private File archivo;
    private boolean llave;
    private RandomAccessFile RAF;
    private int address;
    private Stack<String> AvailList;

    public ARLF() {
    }

    public ARLF(int size) {
        this.size = size;
    }

    public RandomAccessFile getRAF() {
        return RAF;
    }

    public void setRAF(RandomAccessFile RAF) {
        this.RAF = RAF;
    }

    public int getAddress(int RecordNumber) {
        return RecordNumber * size + 1;
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

    public boolean isUpperCase(String Temp) {
        String Temp2 = Temp;
        if (Temp.equals(Temp2.toUpperCase())) {
            return true;
        }
        return false;
    }

    public void BuildAvailList() {
        this.AvailList = new Stack();
            try {
            this.RAF = new RandomAccessFile(this.archivo,"rw");
            RAF.seek(1);
            int avail = RAF.read();
                while(avail != -1){
                    AvailList.push(Integer.toString(avail));
                    RAF.seek(avail);
                    avail = RAF.read();
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
