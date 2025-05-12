package Examen;

import org.w3c.dom.Document;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        UtilsDOM utils = new UtilsDOM();
        File fitxer = new File("Movies.xml");

        Document doc = utils.obrirXMLAmbDOM(fitxer);

        if (doc != null) {
            // utils.afegirEdatAPellicules(doc);
            // utils.mostrarTitolsIDirectors(doc);
            // utils.eliminarPelliculesAntigues(doc);
            // utils.anonimitzarDirectors(doc);
            // System.out.println("Pel·lícules: " + utils.comptarPellicules(doc));
            // utils.afegirPellicula(doc, "Nou Títol", "Nou Director", 2024);

            // Guarda el resultat
            utils.guardarDocumentEnFitxer(doc, "updated_movies.xml");
        } else {
            System.out.println("No s'ha pogut carregar el fitxer XML.");
        }

        //Fitxers NIO.2

        Path ruta = Paths.get("entrada");
        UtilsFitxers UtilsFitxers = new UtilsFitxers();
        UtilsFitxers.filtrarICopiarFitxers(ruta);
    }
}
