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
public class FutbolistaController {
    ArrayList<Futbolista> futbolistas;
    File fichero_fut = new File ("Futbolistas.xml");
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
    
    public void guardarFutbolistasFichero() throws FileNotFoundException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "Futbolistas", null);
            document.setXmlVersion("1.0");

            for (int i = 0; i < futbolistas.size(); i++) {
                Element raiz = document.createElement("futbolista"); 
                document.getDocumentElement().appendChild(raiz);

                CrearElemento("dni", (futbolistas.get(i).getDni()), raiz, document);

                CrearElemento("nombre", futbolistas.get(i).getNombre(), raiz, document);

                CrearElemento("apellido1", futbolistas.get(i).getApellido1(), raiz, document);

                CrearElemento("apellido2", String.valueOf(futbolistas.get(i).getApellido2()), raiz, document);

                CrearElemento("edad", Integer.toString(futbolistas.get(i).getEdad()), raiz, document);

                Source source = new DOMSource(document);
                Result result = new StreamResult(new java.io.File("Futbolistas.xml"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                //transforma el don en un fichero xml
                transformer.transform(source, result);

                XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
                GestionContenido gestor = new GestionContenido();
                procesadorXML.setContentHandler(gestor);
                InputSource fileXML = new InputSource("futbolistas.xml");
                procesadorXML.parse(fileXML);
            }

        } catch (Exception e) {

            System.err.println("Error: " + e);
        }
    }
    
    public void LeerFichero() throws FileNotFoundException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document documento = (Document) builder.parse("Futbolistas.xml");
       
        documento.getDocumentElement().normalize();
        
        NodeList NodeFut = documento.getElementsByTagName("futbolista");
        
        for(int i = 0; i < NodeFut.getLength(); i++){
            Node nodo = NodeFut.item(i);
            
            if(nodo.getNodeType() == Node.ELEMENT_NODE){
                Element e  = (Element) nodo;
                String dni = e.getElementsByTagName("dni").item(0).getTextContent();
                String nombre = e.getElementsByTagName("nombre").item(0).getTextContent();
                String apellido1 = e.getElementsByTagName("apellido1").item(0).getTextContent();
                String apellido2 = e.getElementsByTagName("apellido2").item(0).getTextContent();
                String eda = e.getElementsByTagName("edad").item(0).getTextContent();
                int edad = Integer.valueOf(eda);
                Futbolista futbolista = new Futbolista(dni, nombre, apellido1, apellido2, edad);
                guardarFutbolistas(futbolista);
            }
        }
    }
    
    public String[][] datos_fut(){         
        String contenido[][] = new String[getFutbolistas().size()][5];
        for(int i = 0;i < this.getFutbolistas().size();i++){             
            contenido[i][0] = String.valueOf(this.getFutbolistas(i).getDni());
            contenido[i][1] = String.valueOf(this.getFutbolistas(i).getNombre());
            contenido[i][2] = String.valueOf(this.getFutbolistas(i).getApellido1()); 
            contenido[i][3] = String.valueOf(this.getFutbolistas(i).getApellido2());             
            contenido[i][4] = String.valueOf(this.getFutbolistas(i).getEdad());
        }
        return contenido;
    }
}
