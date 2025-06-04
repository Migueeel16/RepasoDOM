package ExamenRecu;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main2 {
    public static void main(String[] args) {
        Path origen= Paths.get("C:\\Users\\llere\\IdeaProjects\\M6REPASO\\EXAMENRECUM6\\exemple");
        llegirFitxersCopiar(origen);
    }

    public static void llegirFitxersCopiar(Path origen){
        try (Stream<Path> stream = Files.walk(origen)) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("tot.txt",true));
            stream.forEach(source -> {
                if (Files.isDirectory(source)) {

                } else if (Files.isRegularFile(source)) {
                    try {
                        BufferedReader reader = Files.newBufferedReader(source);
                        reader.lines().forEach(line -> {
                            try {
                                writer.write(line + "\n");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            writer.close();
        } catch (IOException e) {
            System.out.println("Error accedint al directori: " + e.getMessage());
        }
    }
}
