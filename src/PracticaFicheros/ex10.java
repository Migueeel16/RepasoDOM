package PracticaFicheros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

//Calcular tamaño de un directorio
public class ex10 {
    public static void main(String[] args) {
        Path directorio = Paths.get("C:\\Users\\usuario\\IdeaProjects\\RepasoDOM\\exemple");

        try (Stream<Path> stream = Files.walk(directorio)) {
            long total = stream
                    .filter(Files::isRegularFile)
                    .mapToLong(path -> {
                        try {
                            return Files.size(path);
                        } catch (IOException e) {
                            return 0;
                        }
                    })
                    .sum();

            System.out.println("Tamaño total del directorio: " + total + " bytes");

        } catch (IOException e) {
            System.out.println("Error al recorrer el directorio: " + e.getMessage());
        }
    }
}
