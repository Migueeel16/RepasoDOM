package PracticaDOM;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;

public class UtilsDOM {
    public Document obrirXMLAmbDOM(File fitxer) {
        Document doc = null; //Representació de l'arbre DOM

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //Indica que el model DOM no ha de processar comentaris dins l'XML
            factory.setIgnoringComments(true);

            //Indica que ha d'ignorar els espais en blanc que tingui el document
            factory.setIgnoringElementContentWhitespace(true);

            //Es crea un objecte DocumentBuilder per a carregar en ell
            // l'estructura d'arbre a partir de l'XML
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Parsejar el documento XML i generar el seu DOM
            doc = builder.parse(fitxer);
            //Ara doc apunta a l'arbdre DOM preparat per a ser recorregut

        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public String recorrerDOM(Document doc){
        String sortida = "";


        Node arrel = doc.getFirstChild();
        sortida += "*** " + arrel.getNodeName() + " ***\n\n";

        NodeList nodelist = arrel.getChildNodes();

        // Processar cada fill
        for (int i = 0; i < nodelist.getLength(); i++) {
            sortida += recorrerNode(nodelist.item(i));
        }

        return sortida;
    }

    private String recorrerNode(Node node) {
        String sortida = "";

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            sortida += "*" + node.getNodeName() + ":\n"; // Títol de l'element

            // Atributs
            NamedNodeMap atributs = node.getAttributes();
            String anyPublicacio = null;
            for (int j = 0; j < atributs.getLength(); j++) {
                Node atribut = atributs.item(j);
                sortida += "  " + atribut.getNodeName() + ": " + atribut.getNodeValue() + "\n"; // Indentació i format clar

                if (atribut.getNodeName().equals("publicat_el")) {
                    anyPublicacio = atribut.getNodeValue();
                }
            }

            // Calcular l'edat del llibre si tenim l'any de publicació
            if (anyPublicacio != null) {
                try {
                    int anyPublicacioInt = Integer.parseInt(anyPublicacio);
                    int anyActual = Year.now().getValue();
                    int edat = anyActual - anyPublicacioInt;
                    sortida += "  Edat del llibre: " + edat + " anys\n"; // Afegir l'edat a la sortida
                } catch (NumberFormatException e) {
                    // Si no podem convertir l'any a enter, ignorar el càlcul de l'edat
                    sortida += "  Edat del llibre: No disponible\n";
                }
            }

            // Fills (subelements)
            NodeList fills = node.getChildNodes();
            for (int i = 0; i < fills.getLength(); i++) {
                sortida += recorrerNode(fills.item(i)); // cridem recursivament els fills
            }

        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String text = node.getNodeValue().trim();
            if (!text.isEmpty()) {
                sortida += text + "\n"; // Afegim text del node (si no està buit)
            }
        }

        return sortida;
    }

    // Mètode per obtenir tots els títols dels llibres i retornar-los ordenats alfabèticament
    public ArrayList<String> getTotsElsTitols(Document doc) {
        ArrayList<String> titols = new ArrayList<>();

        // Obtenir tots els nodes amb l'etiqueta "Titol"
        NodeList nodesTitols = doc.getElementsByTagName("Titol");

        // Recórrer els nodes i afegir el seu valor (títol) a la llista
        for (int i = 0; i < nodesTitols.getLength(); i++) {
            Node node = nodesTitols.item(i);
            // Afegeix el valor del node Titol (que és el text del llibre) a la llista
            String titol = node.getTextContent().trim();
            if (!titol.isEmpty()) {
                titols.add(titol);
            }
        }

        // Ordenar alfabèticament la llista de títols
        Collections.sort(titols);

        // Retornar la llista ordenadaggghhh
        return titols;
    }
}
