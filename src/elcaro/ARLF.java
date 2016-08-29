package elcaro;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

public class ARLF {

    private String data;
    private int sizeofField;
    private int sizeofRegistry;
    private File archivo;
    private String path;
    private boolean llave;
    private RandomAccessFile RAF;
    private int address;
    private Stack<String> AvailList = new Stack();
    private int sizeofHeader = 3;

    public ARLF() {
    }

    public ARLF(int size) {
        this.sizeofField = size;
    }

    public ARLF(String path, int sizeofField, int sizeofRegistry) {
        this.sizeofField = sizeofField;
        this.sizeofRegistry = sizeofRegistry;
        this.path = path;
        archivo = new File(path);
        try {
            FileWriter writer = new FileWriter(this.path, false);
            writer.write(this.sizeofField);
            writer.write(this.sizeofRegistry);
            writer.write('0');
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RandomAccessFile getRAF() {
        return RAF;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setRAF(RandomAccessFile RAF) {
        this.RAF = RAF;
    }

    public int getAddress(int RecordNumber) {
        return RecordNumber * sizeofField + sizeofHeader;
    }

    public ARLF(File registro) {
        this.archivo = registro;
        this.path = registro.getAbsolutePath();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;

    }

    public int getSizeofField() {
        return sizeofField;
    }

    public void setSizeofField(int sizeofField) {
        this.sizeofField = sizeofField;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(String path) {
        this.path = path;

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
            FileWriter writer = new FileWriter(path, true);
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
            this.RAF = new RandomAccessFile(this.archivo, "rw");
            RAF.seek(1);
            int avail = RAF.read();
            while (avail != '0') {
                AvailList.push(Integer.toString(avail));
                RAF.seek(avail + 1);
                avail = RAF.read();
            }
            RAF.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRegistro(ArrayList<String> campos) {
        String temp = "";
        for (int i = 0; i < campos.size(); i++) {
            temp += campos.get(i);
        }
        try {
            FileWriter writer = new FileWriter(archivo.getAbsolutePath(), true);
            writer.write(temp);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrar(int fieldNumberAt){
        try {
            this.AvailList.push(Integer.toString(fieldNumberAt*this.sizeofField+3));
            this.RAF = new RandomAccessFile(this.archivo, "rw");
            RAF.seek(sizeofHeader-1);
            int inicio = RAF.read();
            RAF.seek(sizeofHeader-1);
            RAF.write(fieldNumberAt*this.sizeofField+3);
            RAF.seek(fieldNumberAt*this.sizeofField+3);
            RAF.write('*');
            RAF.write(inicio);
        } catch (Exception e) {
        } finally {
            try {
                RAF.close();
            } catch (Exception e) {
            }
        }
    }

    public void borrarCampo(String campo) {
        try {
            this.RAF = new RandomAccessFile(this.archivo, "rw");
            RAF.seek(sizeofHeader);
            String campoTmp;
            campoTmp = "";
            boolean encontro = false;
            int fieldNumberAt = 0;
            while(!encontro){
            for (int i = 0; i < this.sizeofField; i++) {
                int caracter = RAF.read();
                if ((char)caracter == ' ') {
                    if(campoTmp.equals(campo)){
                        borrar(fieldNumberAt);
                        int position = fieldNumberAt*sizeofHeader+3+1;
                        encontro = true;
                    }else{
                        campoTmp = "";
                    }
                }else{
                    campoTmp += (char)caracter;
                    //System.out.println(campoTmp+" campoTmp");
                }
            }
            fieldNumberAt++;
            }
        } catch (Exception e) {
        } finally {
            try {
                RAF.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertarCampo(String campo) {
        if (this.AvailList.isEmpty()) {
            try {
                this.RAF = new RandomAccessFile(this.archivo, "rw");
                RAF.seek(this.archivo.length());
                int contador = 0;
                while (contador < this.sizeofField) {
                    if (contador < campo.length()) {
                        RAF.write(campo.charAt(contador));
                    } else {
                        RAF.write(' ');
                    }
                    contador++;
                }
                RAF.close();
            } catch (Exception e) {
            }
            JOptionPane.showMessageDialog(null, "Campo Agregado Exitosamente");
        } else {
            System.out.println("entro al else de insertar campo");
            try {
                this.RAF = new RandomAccessFile(this.archivo, "rw");
                int pos = Integer.parseInt(AvailList.pop());
                System.out.println(pos+" position");
                RAF.seek(pos);
                int contador = 0;
                for (int i = 0; i < this.sizeofField; i++) {
                    if (contador < campo.length()) {
                        RAF.write(campo.charAt(i));
                    }else{
                        RAF.write(' ');
                    }
                    contador++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    RAF.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void modificarCampo(String campoViejo, String campoNuevo) {
        try {
            this.RAF = new RandomAccessFile(this.archivo, "rw");
            RAF.seek(sizeofHeader);
            String campoTmp;
            campoTmp = "";
            boolean encontro = false;
            int fieldNumberAt = 0;
            while(!encontro){
            for (int i = 0; i < this.sizeofField; i++) {
                int caracter = RAF.read();
                if ((char)caracter == ' ') {
                    if(campoTmp.equals(campoViejo)){
                        insertarCampo(fieldNumberAt,campoNuevo);
                        encontro = true;
                    }else{
                        campoTmp = "";
                    }
                }else{
                    campoTmp += (char)caracter;
                    System.out.println(campoTmp+" campoTmp");
                }
            }
            fieldNumberAt++;
            }
        } catch (Exception e) {
        } finally {
            try {
                RAF.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void insertarCampo(int fieldNumber, String campoNuevo){
        try {
            this.RAF = new RandomAccessFile(this.archivo, "rw");
            RAF.seek(fieldNumber*this.sizeofField+3);
            int contador = 0;
            while(contador < this.sizeofField){
                if (contador < campoNuevo.length()) {
                    RAF.write(campoNuevo.charAt(contador));
                }else{
                    RAF.write(' ');
                }
                contador++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                RAF.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
