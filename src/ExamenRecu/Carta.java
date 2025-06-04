import ExamenRecu.Pizza;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Carta {
    static List<Pizza> pizzes = new ArrayList<Pizza>();
    public static void main(String[] args) {
        File fixterOriginal = new File("C:\\Users\\llere\\IdeaProjects\\M6REPASO\\EXAMENRECUM6\\pizzes.xml");
        Document doc = obrirXMLAmbDOM(fixterOriginal);
        try {
            System.out.println(recorrerDOM(doc));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        System.out.println(pizzes);
    }

    public static String recorrerDOM(Document doc) throws IOException, TransformerException {
        String sortida="";
        // Crea primer nodo de arrel el "padre de todos los nodes"
        Node arrel = doc.getFirstChild();

        sortida += "***" + arrel.getNodeName() + "***\n";
//      Crea lista de nodes por debajo de la arrel
        NodeList nodelist = arrel.getChildNodes();

        for (int i = 0; i < nodelist.getLength(); i++) {
            sortida += recorrerNODE(doc,nodelist.item(i));

        }
        return sortida;
    }
    public static String recorrerNODE(Document doc,Node node) throws IOException, TransformerException {
        int edat = 0;
        String sortida = "";
        Pizza pizza = new Pizza();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            sortida += "\n*" + node.getNodeName().trim() + ":";

            NamedNodeMap atributs = node.getAttributes();
            Node atribut;
            for (int j = 0; j < atributs.getLength(); j++) {
                atribut = atributs.item(j);
                if (node.getNodeName().trim().equals("pizza")) {
                    if (atribut.getNodeName().equals("nom")){
                        pizza.setNom(atribut.getNodeValue());
                    } else if (atribut.getNodeName().equals("preu")) {
                        pizza.setPreu(Integer.parseInt(atribut.getNodeValue()));
                    }
                    NodeList ingredientes = node.getChildNodes();
                    for (int i = 0; i < ingredientes.getLength(); i++) {
                        if (ingredientes.item(i).getNodeName().equals("ingredient")) {
                            NodeList subIngredientes = ingredientes.item(i).getChildNodes();
                            for (int k = 0; k < subIngredientes.getLength(); k++) {
                                pizza.addIngredient(subIngredientes.item(k).getTextContent());
                            }
                        }
                        continue;
                    }
                }
                System.out.println("Pizza: " + pizza);
                pizzes.add(pizza);
                sortida += atribut.getNodeName() + " " + atribut.getNodeValue();

                sortida += " ";
            }
            sortida += "\n";
            //recorrer hijos recursivo
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                sortida += recorrerNODE(doc,nodeList.item(i));
            }
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            pizza.addIngredient(node.getNodeValue());
            sortida += node.getNodeValue().trim().replaceAll("[\n]", "");
        }
        return sortida;
    }
    public static Document obrirXMLAmbDOM(File fitxer){
        Document doc = null;
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(fitxer);
            return doc;


        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
