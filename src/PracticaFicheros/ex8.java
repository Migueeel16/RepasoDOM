package PracticaFicheros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class ex8 {
    public static void main(String[] args) {
        Path entrada = Paths.get("C:\\Users\\usuario\\IdeaProjects\\RepasoDOM\\exemple\\hola1.txt");
        Path salida = Paths.get("prueba.txt");

        try {
            List<String> lineas = Files.readAllLines(entrada);
            Collections.shuffle(lineas);
            Files.write(salida, lineas);
            System.out.println("Archivo generado con líneas desordenadas aleatoriamente.");
        } catch (IOException e) {
            System.out.println("Error procesando archivo: " + e.getMessage());
        }
    }
}
