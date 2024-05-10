package model.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CentralPersistencia {

    
    public void guardar(Serializable object) {
        String path = "./Data/galeria.ser"; 
        File directory = new File("./Data/");
        if (!directory.exists()) {
            directory.mkdirs(); 
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(object);
        } catch (IOException e) {
            System.err.println("Error al guardar " + path + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Object cargar() {
        String path = "./Data/galeria.ser"; 
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading object from " + path + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
