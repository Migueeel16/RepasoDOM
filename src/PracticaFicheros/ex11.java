package PracticaFicheros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

//Copia recursiva de un directorio
public class ex11 {
    public static void main(String[] args) {
        Path origen = Paths.get("C:\\Users\\usuario\\IdeaProjects\\RepasoDOM\\exemple");
        Path destino = Paths.get("C:\\Users\\usuario\\IdeaProjects\\RepasoDOM\\exemple_backup");

        try (Stream<Path> paths = Files.walk(origen)) {
            paths.forEach(source -> {
                Path target = destino.resolve(origen.relativize(source));
                try {
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(target);
                    } else {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    System.out.println("Error copiando: " + source + " -> " + e.getMessage());
                }
            });

            System.out.println("Copia completa de directorio con contenido.");
        } catch (IOException e) {
            System.out.println("Error durante la copia recursiva: " + e.getMessage());
        }
    }
}
