/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Equipos;


import Equipos.Equipo;
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
public class EquipoController {
    ArrayList<Equipo> equipos;
    File fichero_equi = new File ("Equipos.xml");

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
    static void CrearElemento(String datoEmple, String valor, Element raiz, Document document) {
        Element elem = document.createElement(datoEmple);
        Text text = document.createTextNode(valor); //damos valor
        raiz.appendChild(elem); //pegamos el elemento hijo a la raiz
        elem.appendChild(text); //pegamos el valor		 	
    }
    public void guardarEquipos(Equipo equip){
            equipos.add(equip);
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
    
    public void guardarEquiposFichero() throws FileNotFoundException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "Equipos", null);
            document.setXmlVersion("1.0");

            for (int i = 0; i < equipos.size(); i++) {
                Element raiz = document.createElement("equipo"); 
                document.getDocumentElement().appendChild(raiz);

                CrearElemento("equipo_ID", (equipos.get(i).getEquipo_ID()), raiz, document);

                CrearElemento("nombre_equipo", equipos.get(i).getNombre_equipo(), raiz, document);

                CrearElemento("fecha_fundacion", equipos.get(i).getFecha_fundacion(), raiz, document);

                CrearElemento("nombre_campo", String.valueOf(equipos.get(i).getNombre_campo()), raiz, document);

                CrearElemento("numero_socios", Integer.toString(equipos.get(i).getNumero_socios()), raiz, document);
                
                CrearElemento("DniFut", String.valueOf(equipos.get(i).getDniFut()), raiz, document);

                Source source = new DOMSource(document);
                Result result = new StreamResult(new java.io.File("Equipos.xml"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                //transforma el don en un fichero xml
                transformer.transform(source, result);

                XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
                GestionContenido gestor = new GestionContenido();
                procesadorXML.setContentHandler(gestor);
                InputSource fileXML = new InputSource("equipos.xml");
                procesadorXML.parse(fileXML);
            }

        } catch (Exception e) {

            System.err.println("Error: " + e);
        }
    }
    
    public void LeerFichero() throws FileNotFoundException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document documento = (Document) builder.parse("Equipos.xml");
       
        documento.getDocumentElement().normalize();
        
        NodeList equipos = documento.getElementsByTagName("equipo");
        
        for(int i = 0; i < equipos.getLength(); i++){
            Node nodo = equipos.item(i);
            
            if(nodo.getNodeType() == Node.ELEMENT_NODE){
                Element e  = (Element) nodo;
                String equipoid = e.getElementsByTagName("equipo_ID").item(0).getTextContent();
                String nombre_equipo = e.getElementsByTagName("nombre_equipo").item(0).getTextContent();
                String fecha_fund = e.getElementsByTagName("fecha_fundacion").item(0).getTextContent();
                String nombre_campo = e.getElementsByTagName("nombre_campo").item(0).getTextContent();
                String nsocios = e.getElementsByTagName("numero_socios").item(0).getTextContent();
                int num_socios = Integer.valueOf(nsocios);
                String DniFut = e.getElementsByTagName("DniFut").item(0).getTextContent();
                Equipo equipo = new Equipo(equipoid, nombre_equipo, fecha_fund, nombre_campo, num_socios, DniFut);
                guardarEquipos(equipo);
            }
        }
    }
    
    public String[][] datos_equi(){         
        String contenido[][] = new String[getEquipos().size()][3];
        for(int i = 0;i < this.getEquipos().size();i++){             
            contenido[i][0] = String.valueOf(this.getEquipos(i).getEquipo_ID());
            contenido[i][1] = String.valueOf(this.getEquipos(i).getNombre_equipo());

        }
        return contenido;
    }
}
