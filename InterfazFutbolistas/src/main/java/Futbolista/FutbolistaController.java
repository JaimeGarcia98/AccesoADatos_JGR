/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Futbolista;


import Futbolista.Futbolista;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 *
 * @author jaime
 */
public class FutbolistaController {
    ArrayList<Futbolista> futbolistas;
    File fichero_fut = new File ("FicheroFut.txt");

    public ArrayList<Futbolista> getFutbolistas() {
        return futbolistas;
    }

    public void setFutbolistas(ArrayList<Futbolista> futbolistas) {
        this.futbolistas = futbolistas;
    }

    public File getFichero_fut() {
        return fichero_fut;
    }

    public void setFichero_fut(File fichero_fut) {
        this.fichero_fut = fichero_fut;
    }
    
    public Futbolista getFutbolistas(int i) {
        return futbolistas.get(i);
    }
    public FutbolistaController() {
        this.futbolistas = new ArrayList<Futbolista>();
    }
    
    public void guardarFutbolistas(Futbolista fut){
            futbolistas.add(fut);
    }
    
    public void guardarFutbolistasFichero() throws FileNotFoundException, IOException{
        FileOutputStream salida = new FileOutputStream(getFichero_fut());
        ObjectOutputStream data = new ObjectOutputStream(salida);
        data.writeObject(getFutbolistas());
        data.close();
    }
    
    public void LeerFichero() throws FileNotFoundException, IOException, ClassNotFoundException {
	ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(getFichero_fut()));

	int i = 1;
	try {
		while (true) { // lectura del fichero
                    this.futbolistas = (ArrayList<Futbolista>) dataIS.readObject();
                    i++;
		}
	} catch (EOFException eo) {
		System.out.println("FIN DE LECTURA.");
	} catch (StreamCorruptedException x) {
	}

	dataIS.close(); // cerrar stream de entrada
    }
    
    public String[][] datos_fut(){         
        String contenido[][] = new String[getFutbolistas().size()][5];
        for(int i = 0;i < this.getFutbolistas().size();i++){             
            contenido[i][0] = String.valueOf(this.getFutbolistas(i).getNombre());
            contenido[i][1] = String.valueOf(this.getFutbolistas(i).getApellido1()); 
        }
        return contenido;
    }
}
