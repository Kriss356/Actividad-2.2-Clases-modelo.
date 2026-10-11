package com.mycompany.torneodeportivo.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Estrategia de almacenamiento: la imagen elegida se COPIA a la carpeta "imagenes/"
 * del proyecto con un nombre único (UUID) y en el modelo solo se guarda la ruta relativa.
 * Así el registro no depende de la ubicación original ni de nombres repetidos.
 */
public class ImagenUtil {
    private static final Path CARPETA = Paths.get("imagenes");

    public static File elegir(Window ventana) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar imagen");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        return fc.showOpenDialog(ventana);
    }

    public static String guardar(File origen) throws IOException {
        Files.createDirectories(CARPETA);
        String nombre = origen.getName();
        String ext = nombre.contains(".") ? nombre.substring(nombre.lastIndexOf('.')) : "";
        String nuevo = UUID.randomUUID() + ext;
        Files.copy(origen.toPath(), CARPETA.resolve(nuevo));
        return "imagenes/" + nuevo;
    }

    public static Image cargar(String ruta) {
        if (ruta == null || ruta.isEmpty()) return null;
        File f = new File(ruta);
        return f.exists() ? new Image(f.toURI().toString(), 140, 140, true, true) : null;
    }

    public static void borrar(String ruta) {
        if (ruta == null || ruta.isEmpty()) return;
        try { Files.deleteIfExists(Paths.get(ruta)); } catch (IOException ignorada) { }
    }
}
