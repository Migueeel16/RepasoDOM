package PracticaFicheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Leer archivo con Files.newBufferedReader() y escribir líneas senars/parells
public class ex7 {
    public static void main(String[] args) {
        Path entrada = Paths.get("C:\\Users\\usuario\\IdeaProjects\\RepasoDOM\\exemple\\hola1.txt");
        Path senars = Paths.get("linies_senars.txt");
        Path parells = Paths.get("linies_parells.txt");

        try {
            BufferedReader reader = Files.newBufferedReader(entrada);
            BufferedWriter writerSenars = Files.newBufferedWriter(senars);
            BufferedWriter writerParells = Files.newBufferedWriter(parells);

            String linea;
            int numeroLinea = 1;
            while ((linea = reader.readLine()) != null) {
                if (numeroLinea % 2 == 0) {
                    writerParells.write(linea);
                    writerParells.newLine();
                } else {
                    writerSenars.write(linea);
                    writerSenars.newLine();
                }
                numeroLinea++;
            }

            System.out.println("Líneas separadas correctamente en archivos senars y parells.");

        } catch (IOException e) {
            System.out.println("Error leyendo o escribiendo archivo: " + e.getMessage());
        }
    }
}
