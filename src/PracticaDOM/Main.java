package PracticaDOM;

import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Crear instancia de la clase utilitaria
        UtilsDOM utils = new UtilsDOM();

        // Ruta del archivo XML
        File fitxer = new File("LlibresXML.xml");

        // Abrir el XML con DOM
        Document doc = utils.obrirXMLAmbDOM(fitxer);
//
//        // Verificar que el documento fue cargado correctamente
//        if (doc != null) {
//            // Recorrer el DOM y mostrar el contenido
//            String resultat = utils.recorrerDOM(doc);
//            System.out.println(resultat);
//        } else {
//            System.out.println("Error al cargar el archivo XML.");
//        }


        // Obtenir tots els títols ordenats
        ArrayList<String> titols = utils.getTotsElsTitols(doc);

        // Mostrar els títols ordenats
        for (String titol : titols) {
            System.out.println(titol);
        }
    }
}