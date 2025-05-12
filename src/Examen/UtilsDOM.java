package Examen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.Year;

public class UtilsDOM {

    //Llegir i carregar l'arbre DOM del fitxer XML anterior.
    public Document obrirXMLAmbDOM(File fitxer) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(fitxer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }


    //Calcular l'edat de cada pel·lícula (any actual menys any d'estrena) i afegir aquesta
    //informació com un atribut nou anomenat edat a cada node <Movie>.
    public void afegirEdatAPellicules(Document doc) {
        NodeList pelicules = doc.getElementsByTagName("Movie");
        int anyActual = Year.now().getValue();

        for (int i = 0; i < pelicules.getLength(); i++) {
            Element movie = (Element) pelicules.item(i);
            String anyPublicacio = movie.getAttribute("release");

            try {
                int any = Integer.parseInt(anyPublicacio);
                int edat = anyActual - any;
                movie.setAttribute("age", String.valueOf(edat));
            } catch (NumberFormatException e) {
                System.out.println("Any de publicació no vàlid: " + anyPublicacio);
            }
        }
    }

    //Guardar l'arbre DOM actualitzat en un nou fitxer XML amb el nom updated_movies.xml.
    public void guardarDocumentEnFitxer(Document doc, String nomFitxer) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nomFitxer));
            transformer.transform(source, result);
            System.out.println("Fitxer guardat com a: " + nomFitxer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Mostrar Titulos y dorectores de las movies
    public void mostrarTitolsIDirectors(Document doc) {
        NodeList pelicules = doc.getElementsByTagName("Movie");

        for (int i = 0; i < pelicules.getLength(); i++) {
            Element movie = (Element) pelicules.item(i);
            String titol = movie.getElementsByTagName("Title").item(0).getTextContent();
            String director = movie.getElementsByTagName("Director").item(0).getTextContent();

            System.out.println("Títol: " + titol + ", Director: " + director);
        }
    }

    //Eliminar peliculas antiguas al año 2000
    public void eliminarPelliculesAntigues(Document doc) {
        NodeList pelicules = doc.getElementsByTagName("Movie");

        for (int i = pelicules.getLength() - 1; i >= 0; i--) { // de darrere a davant per evitar errors
            Element movie = (Element) pelicules.item(i);
            String release = movie.getAttribute("release");
            try {
                int any = Integer.parseInt(release);
                if (any < 2000) {
                    movie.getParentNode().removeChild(movie);
                }
            } catch (NumberFormatException ignored) {
            }
        }
    }

    //Canviar el nom del director de totes les pel·lícules a “Anònim”
    public void anonimitzarDirectors(Document doc) {
        NodeList directors = doc.getElementsByTagName("Director");

        for (int i = 0; i < directors.getLength(); i++) {
            directors.item(i).setTextContent("Anònim");
        }
    }

    //Comptar quantes pel·lícules hi ha al fitxer
    public int comptarPellicules(Document doc) {
        return doc.getElementsByTagName("Movie").getLength();
    }

    //Afegir una nova pel·lícula
    public void afegirPellicula(Document doc, String titol, String director, int any) {
        Element novaPeli = doc.createElement("Movie");
        novaPeli.setAttribute("release", String.valueOf(any));

        Element titolElem = doc.createElement("Title");
        titolElem.setTextContent(titol);
        Element directorElem = doc.createElement("Director");
        directorElem.setTextContent(director);

        novaPeli.appendChild(titolElem);
        novaPeli.appendChild(directorElem);

        doc.getDocumentElement().appendChild(novaPeli); // Afegim a <Movies>
    }

    //Buscar director y mostrar titulos
    public void mostrarTitolsPerDirector(Document doc, String nomDirector) {
        NodeList pelicules = doc.getElementsByTagName("Movie");

        for (int i = 0; i < pelicules.getLength(); i++) {
            Element movie = (Element) pelicules.item(i);
            String director = movie.getElementsByTagName("Director").item(0).getTextContent();
            if (director.equalsIgnoreCase(nomDirector)) {
                String titol = movie.getElementsByTagName("Title").item(0).getTextContent();
                System.out.println("Pel·lícula de " + nomDirector + ": " + titol);
            }
        }
    }
}
