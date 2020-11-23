/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Liga;

import Futbolista.Futbolista;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author jaime
 */
public class LigaController {
    ArrayList<Liga> ligas;
    File fichero_lig = new File ("Ligas.xml");

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
    
    static void CrearElemento(String datoEmple, String valor, Element raiz, Document document) {
        Element elem = document.createElement(datoEmple);
        Text text = document.createTextNode(valor); //damos valor
        raiz.appendChild(elem); //pegamos el elemento hijo a la raiz
        elem.appendChild(text); //pegamos el valor		 	
    }
        
    static class GestionContenido extends DefaultHandler {

        public GestionContenido() {
            super();
        }

        public void startDocument() {
            System.out.println("Comienzo del Documento XML");
        }

        public void endDocument() {
            System.out.println("Final del Documento XML");
        }

        public void startElement(String uri, String nombre,
                String nombreC, Attributes atts) {
            System.out.printf("\t Principio Elemento: %s %n", nombre);
        }

        public void endElement(String uri, String nombre,
                String nombreC) {
            System.out.printf("\tFin Elemento: %s %n", nombre);
        }

        public void characters(char[] ch, int inicio, int longitud)
                throws SAXException {
            String car = new String(ch, inicio, longitud);
            //quitar saltos de línea	
            car = car.replaceAll("[\t\n]", "");
            System.out.printf("\t Caracteres: %s %n", car);
        }

    }
    
    public void guardarLigasFichero() throws FileNotFoundException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "Ligas", null);
            document.setXmlVersion("1.0");

            for (int i = 0; i < ligas.size(); i++) {
                Element raiz = document.createElement("liga"); 
                document.getDocumentElement().appendChild(raiz);

                CrearElemento("liga_ID", (ligas.get(i).getLiga_ID()), raiz, document);

                CrearElemento("nombre_liga", ligas.get(i).getNombre_liga(), raiz, document);
                
                CrearElemento("numero_equipos", Integer.toString(ligas.get(i).getNumero_equipos()), raiz, document);

                CrearElemento("fecha_inicio", ligas.get(i).getFecha_inicio(), raiz, document);

                CrearElemento("fecha_fin", String.valueOf(ligas.get(i).getFecha_fin()), raiz, document);
                
                CrearElemento("Id_equipos", String.valueOf(ligas.get(i).getId_equipos()), raiz, document);

                Source source = new DOMSource(document);
                Result result = new StreamResult(new java.io.File("Ligas.xml"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                //transforma el don en un fichero xml
                transformer.transform(source, result);

                XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
                GestionContenido gestor = new GestionContenido();
                procesadorXML.setContentHandler(gestor);
                InputSource fileXML = new InputSource("ligas.xml");
                procesadorXML.parse(fileXML);
            }

        } catch (Exception e) {

            System.err.println("Error: " + e);
        }
    }
    
    public void LeerFichero() throws FileNotFoundException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document documento = (Document) builder.parse("Ligas.xml");
       
        documento.getDocumentElement().normalize();
        
        NodeList ligas = documento.getElementsByTagName("liga");
        
        for(int i = 0; i < ligas.getLength(); i++){
            Node nodo = ligas.item(i);
            
            if(nodo.getNodeType() == Node.ELEMENT_NODE){
                Element e  = (Element) nodo;
                String ligaId = e.getElementsByTagName("liga_ID").item(0).getTextContent();
                String nombreliga = e.getElementsByTagName("nombre_liga").item(0).getTextContent();
                String nequi = e.getElementsByTagName("numero_equipos").item(0).getTextContent();
                int num_Equi = Integer.valueOf(nequi);
                String fechaInicio = e.getElementsByTagName("fecha_inicio").item(0).getTextContent();
                String fechaFin = e.getElementsByTagName("fecha_fin").item(0).getTextContent();
                String IdEquipos = e.getElementsByTagName("Id_equipos").item(0).getTextContent();
                Liga liga = new Liga(ligaId, nombreliga, num_Equi, fechaInicio, fechaFin, IdEquipos);
                this.ligas.add(liga);
            }
        }
    }
    
    public String[][] datos_liga(){         
        String contenido[][] = new String[getLigas().size()][5];
        for(int i = 0;i < this.getLigas().size();i++){             
            contenido[i][0] = String.valueOf(this.getLigas(i).getLiga_ID());
            contenido[i][1] = String.valueOf(this.getLigas(i).getNombre_liga());
            contenido[i][2] = String.valueOf(this.getLigas(i).getNumero_equipos()); 
            contenido[i][3] = String.valueOf(this.getLigas(i).getFecha_inicio());             
            contenido[i][4] = String.valueOf(this.getLigas(i).getFecha_fin());
        }
        return contenido;
    }
}
