/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Liga;

import Liga.Liga;
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
public class LigaController {
    ArrayList<Liga> ligas;
    File fichero_lig = new File ("FicheroLig.txt");

    public ArrayList<Liga> getLigas() {
        return ligas;
    }

    public void setLigas(ArrayList<Liga> ligas) {
        this.ligas = ligas;
    }

    public File getFichero_lig() {
        return fichero_lig;
    }

    public void setFichero_lig(File fichero_lig) {
        this.fichero_lig = fichero_lig;
    }
    
    public Liga getLigas(int i) {
        return ligas.get(i);
    }
    public LigaController() {
        this.ligas = new ArrayList<Liga>();
    }
    
    public void guardarLigas(Liga lig){
            ligas.add(lig);
    }
    
    public void guardarLigasFichero() throws FileNotFoundException, IOException{
        FileOutputStream salida = new FileOutputStream(getFichero_lig());
        ObjectOutputStream data = new ObjectOutputStream(salida);
        data.writeObject(getLigas());
        data.close();
    }
    
    public void LeerFichero() throws FileNotFoundException, IOException, ClassNotFoundException {
	ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(getFichero_lig()));

	int i = 1;
	try {
		while (true) { // lectura del fichero
                    this.ligas = (ArrayList<Liga>) dataIS.readObject();
                    i++;
		}
	} catch (EOFException eo) {
		System.out.println("FIN DE LECTURA.");
	} catch (StreamCorruptedException x) {
	}

	dataIS.close(); // cerrar stream de entrada
    }
    
    public String[][] datos_liga(){         
        String contenido[][] = new String[getLigas().size()][5];
        for(int i = 0;i < this.getLigas().size();i++){             
            contenido[i][0] = String.valueOf(this.getLigas(i).getNombre_liga());
            contenido[i][1] = String.valueOf(this.getLigas(i).getNumero_equipos()); 
        }
        return contenido;
    }
}
