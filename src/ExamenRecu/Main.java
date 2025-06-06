package ExamenRecu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    static int numDirectoris = 0;
    static int numFitxers = 0;
    public static void main(String[] args) {
        Path origen = Paths.get("C:\\Users\\usuario\\IdeaProjects\\RepasoDOM\\exemple"); // directori a copiar
        llistarFitxers(origen);
        System.out.println("Directoris: " + numDirectoris + " Fitxers: " + numFitxers);
    }
    public static void llistarFitxers(Path origen) {
        if (!Files.exists(origen) || !Files.isDirectory(origen)) {
            System.out.println("El directori d'origen no existeix o no és un directori: " + origen);
            return;
        }
        try (Stream<Path> stream = Files.walk(origen)) {
            stream.forEach(source -> {
                if (Files.isDirectory(source)) {
                    numDirectoris++;
                    System.out.println("Se ha sumado el directori: " + source.getFileName());

                } else if (Files.isRegularFile(source)) {
                    numFitxers++;
                    System.out.println("Se ha sumado el fichero: " + source.getFileName());
                }
            });
        } catch (IOException e) {
            System.out.println("Error accedint al directori: " + e.getMessage());
        }

    }
}