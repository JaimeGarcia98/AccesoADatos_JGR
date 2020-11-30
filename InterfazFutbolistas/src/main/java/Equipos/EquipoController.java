/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Equipos;


import Equipos.Equipo;
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
public class EquipoController {
    ArrayList<Equipo> equipos;
    File fichero_equi = new File ("FicheroEqui.txt");

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    }

    public File getFichero_equi() {
        return fichero_equi;
    }

    public void setFichero_equi(File fichero_equi) {
        this.fichero_equi = fichero_equi;
    }
    public Equipo getEquipos(int i) {
        return equipos.get(i);
    }

    public EquipoController() {
        this.equipos = new ArrayList<Equipo>();
    }
    
    public void guardarEquipos(Equipo equip){
            equipos.add(equip);
    }
        
    public void guardarEquiposFichero() throws FileNotFoundException, IOException{
        FileOutputStream salida = new FileOutputStream(getFichero_equi());
        ObjectOutputStream data = new ObjectOutputStream(salida);
        data.writeObject(getEquipos());
        data.close();
    }
    
    public void LeerFichero() throws FileNotFoundException, IOException, ClassNotFoundException {
	ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(getFichero_equi()));

	int i = 1;
	try {
		while (true) { // lectura del fichero
                    this.equipos = (ArrayList<Equipo>) dataIS.readObject();
                    i++;
		}
	} catch (EOFException eo) {
		System.out.println("FIN DE LECTURA.");
	} catch (StreamCorruptedException x) {
	}

	dataIS.close(); // cerrar stream de entrada
    }
    
    public String[][] datos_equi(){         
        String contenido[][] = new String[getEquipos().size()][3];
        for(int i = 0;i < this.getEquipos().size();i++){             
            contenido[i][0] = String.valueOf(this.getEquipos(i).getNombre_equipo());
            contenido[i][1] = String.valueOf(this.getEquipos(i).getNombre_campo());
        }
        return contenido;
    }
}
