package PracticaFicheros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

//Mostrar tamaño y fecha de modificación
public class ex9 {
    public static void main(String[] args) {
        Path archivo = Paths.get("C:\\Users\\usuario\\IdeaProjects\\RepasoDOM\\exemple");

        try {
            BasicFileAttributes attrs = Files.readAttributes(archivo, BasicFileAttributes.class);
            int tamanyo = (int) attrs.size();
            FileTime modificacion = attrs.lastModifiedTime();

            String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(modificacion.toMillis()));
            System.out.println("Tamaño: " + tamanyo + " bytes");
            System.out.println("Fecha modificación: " + fechaFormateada);
        } catch (IOException e) {
            System.out.println("Error leyendo atributos: " + e.getMessage());
        }
    }
}
