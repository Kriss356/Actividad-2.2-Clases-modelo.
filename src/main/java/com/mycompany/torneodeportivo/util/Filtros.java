package com.mycompany.torneodeportivo.util;

import java.util.function.Consumer;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;

/**
 * Restringe en tiempo real los caracteres que se pueden escribir o pegar en un campo de texto.
 * Usa dos mecanismos complementarios:
 *  - un filtro de KEY_TYPED, que bloquea cada tecla no permitida antes de que llegue al campo;
 *  - un TextFormatter, que filtra lo que se pega o se inserta por otros medios.
 */
public class Filtros {

    /** Propiedad interna: indica que el texto lo cambia el programa (limpiar o cargar), no el usuario. */
    static final String PROGRAMATICO = "cambioProgramatico";

    private Filtros() { }

    /**
     * @param campo          campo a restringir
     * @param patronCaracter expresión regular que debe cumplir CADA carácter permitido
     * @param max            longitud máxima del texto
     * @param mensaje        mensaje a mostrar cuando se rechaza un carácter
     * @param alRechazar     acción que recibe el mensaje (normalmente lo muestra bajo el campo)
     */
    public static void restringir(TextInputControl campo, String patronCaracter, int max,
                                  String mensaje, Consumer<String> alRechazar) {
        Pattern permitido = Pattern.compile(patronCaracter);

        // 1) Cada tecla escrita (KEY_TYPED)
        campo.addEventFilter(KeyEvent.KEY_TYPED, evento -> {
            String escrito = evento.getCharacter();
            if (escrito.isEmpty() || Character.isISOControl(escrito.charAt(0))) {
                return; // borrar, tabulador, enter, Ctrl+V... no se filtran aquí
            }
            if (!permitido.matcher(escrito).matches()) {
                evento.consume();
                alRechazar.accept(mensaje);
            } else if (campo.getLength() - campo.getSelection().getLength() >= max) {
                evento.consume();
                alRechazar.accept("Máximo " + max + " caracteres.");
            }
        });

        // 2) Texto pegado o insertado (TextFormatter)
        campo.setTextFormatter(new TextFormatter<String>(cambio -> {
            if (campo.getProperties().containsKey(PROGRAMATICO) || !cambio.isContentChange()) {
                return cambio;
            }
            for (char c : cambio.getText().toCharArray()) {
                if (!permitido.matcher(String.valueOf(c)).matches()) {
                    alRechazar.accept(mensaje);
                    return null;
                }
            }
            if (cambio.getControlNewText().length() > max) {
                alRechazar.accept("Máximo " + max + " caracteres.");
                return null;
            }
            return cambio;
        }));
    }
}
