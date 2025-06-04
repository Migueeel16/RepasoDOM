package Examen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class MainFitxeros {


    public static void main(String[] args) {
        String ruta = "entrada";
        File carpeta = new File(ruta);

        // Crear subdirectorio 'filtrats_copia' en el directorio principal
        File directoriPare = carpeta.getParentFile();
        File subdirectoriFiltrats = new File(directoriPare, "filtrats_copia");

        // Si el subdirectorio ya existe, lo borramos y lo recreamos
        if (subdirectoriFiltrats.exists()) {
            borrarDirectori(subdirectoriFiltrats);
        }

        // Crear la carpeta 'filtrats_copia' nuevamente
        subdirectoriFiltrats.mkdir();

        long ara = System.currentTimeMillis();
        long fa7dies = ara - (7L * 24 * 60 * 60 * 1000);

        buscarFitxers(carpeta, fa7dies, subdirectoriFiltrats);

    }


    public static void buscarFitxers(File carpeta, long fa7dies, File subdirectoriFiltrats) {
        File[] arxius = carpeta.listFiles();

        if (arxius != null) {
            for (int i = 0; i < arxius.length; i++) {
                File f = arxius[i];

                if (f.isDirectory()) {
                    buscarFitxers(f, fa7dies, subdirectoriFiltrats);  // Llamada recursiva
                } else {
                    long mida = f.length();
                    long modificacio = f.lastModified();

                    if (mida > 1024 * 1024 && modificacio > fa7dies) {
                        System.out.println(mida + " bytes " + new Date(modificacio) + " " + f.getAbsolutePath());

                        copiarFitxer(f, subdirectoriFiltrats);
                    }
                }
            }
        }
    }

    public static void copiarFitxer(File fitxer, File subdirectoriFiltrats) {
        try {
            // Copiar el archivo al subdirectorio 'filtrats_copia'
            File destino = new File(subdirectoriFiltrats, fitxer.getName());

            // Usamos Files.copy para copiar el archivo
            Files.copy(fitxer.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Archivo copiado: " + fitxer.getName());
        } catch (IOException e) {
            System.out.println("Error al copiar el archivo: " + fitxer.getName());
            e.printStackTrace();
        }
    }

    // Función para borrar el subdirectorio y su contenido
    public static void borrarDirectori(File directori) {
        File[] arxius = directori.listFiles();
        if (arxius != null) {
            for (File f : arxius) {
                if (f.isDirectory()) {
                    borrarDirectori(f);  // Llamada recursiva para eliminar subdirectorios
                }
                f.delete();  // Borrar el archivo o subdirectorio
            }
        }
        directori.delete();  // Finalmente, borrar el directorio vacío
    }
}
