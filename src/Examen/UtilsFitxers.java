package Examen;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class UtilsFitxers {

    // Llista per guardar fitxers vàlids
    private List<Path> fitxersValids = new ArrayList<>();

    // Funció principal
    public void filtrarICopiarFitxers(Path rutaInicial) {
        try {
            // Carpeta de sortida: mateix nivell que l'original
            Path carpetaSortida = rutaInicial.getParent().resolve("filtrats_copia");

            // 1. Esborrem la carpeta si ja existeix
            if (Files.exists(carpetaSortida)) {
                esborrarCarpeta(carpetaSortida);
            }
            Files.createDirectory(carpetaSortida);

            // 2. Buscar fitxers vàlids dins la carpeta
            buscarFitxers(rutaInicial);

            // 3. Copiar fitxers vàlids a la nova carpeta
            copiarFitxers(fitxersValids, carpetaSortida);

        } catch (IOException e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }

    // Busca fitxers dins una carpeta (recursivament)
    private void buscarFitxers(Path carpeta) throws IOException {
        DirectoryStream<Path> stream = Files.newDirectoryStream(carpeta);
        for (Path path : stream) {
            if (Files.isDirectory(path)) {
                buscarFitxers(path); // crida recursiva per subcarpetes
            } else {
                Path validat = comprovarFitxer(path);
                if (validat != null) {
                    fitxersValids.add(validat);
                    mostrarInfo(validat);
                }
            }
        }
    }

    // Comprova si un fitxer és gran i recent (i el retorna si ho és)
    private Path comprovarFitxer(Path fitxer) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(fitxer, BasicFileAttributes.class);
            long mida = attrs.size(); // mida en bytes
            long modificat = attrs.lastModifiedTime().toMillis();
            long fa7dies = System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000);

            if (mida > 1_000_000 && modificat > fa7dies) {
                return fitxer; // és vàlid
            }
        } catch (IOException e) {
            System.out.println("Error llegint: " + fitxer.getFileName());
        }
        return null; // no és vàlid
    }

    // Mostra per pantalla la info d’un fitxer
    private void mostrarInfo(Path fitxer) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(fitxer, BasicFileAttributes.class);
        long mida = attrs.size();
        Date data = new Date(attrs.lastModifiedTime().toMillis());

        System.out.println(mida + " bytes " + data + " " + fitxer.toAbsolutePath());
    }

    // Esborra una carpeta i tot el que hi ha dins (fitxers simples)
    private void esborrarCarpeta(Path carpeta) throws IOException {
        File[] arxius = carpeta.toFile().listFiles();
        if (arxius != null) {
            for (File f : arxius) {
                f.delete(); // elimina cada fitxer
            }
        }
        Files.delete(carpeta); // esborra la carpeta buida
    }

    // Copia fitxers a la carpeta de sortida, evitant duplicats
    private void copiarFitxers(List<Path> fitxers, Path carpetaSortida) throws IOException {
        Map<String, Integer> nomsUsats = new HashMap<>();

        for (Path fitxer : fitxers) {
            String nomOriginal = fitxer.getFileName().toString();
            String nomFinal = nomOriginal;

            // Si ja existeix un fitxer amb aquest nom, afegim _1, _2, etc.
            while (Files.exists(carpetaSortida.resolve(nomFinal))) {
                int num = nomsUsats.getOrDefault(nomOriginal, 1);
                nomsUsats.put(nomOriginal, num + 1);

                String base = nomOriginal.contains(".") ?
                        nomOriginal.substring(0, nomOriginal.lastIndexOf(".")) :
                        nomOriginal;
                String extensio = nomOriginal.contains(".") ?
                        nomOriginal.substring(nomOriginal.lastIndexOf(".")) :
                        "";

                nomFinal = base + "_" + num + extensio;
            }

            Path desti = carpetaSortida.resolve(nomFinal);
            Files.copy(fitxer, desti);
        }
    }

}
