package com.mycompany.torneodeportivo.servicio;

import com.mycompany.torneodeportivo.modelo.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Capa de servicio: guarda todo en listas en memoria (sin base de datos)
 * y contiene las reglas de negocio. Los controladores solo llaman a estos métodos.
 * Todas las reglas lanzan IllegalArgumentException con un mensaje para el usuario.
 */
public class DatosServicio {

    private static final DatosServicio INSTANCIA = new DatosServicio();
    public static DatosServicio getInstancia() { return INSTANCIA; }

    private final ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    private final ObservableList<Equipo> equipos = FXCollections.observableArrayList();
    private final ObservableList<Jugador> jugadores = FXCollections.observableArrayList();
    private final ObservableList<Partido> partidos = FXCollections.observableArrayList();
    private final ObservableList<Posicion> posiciones = FXCollections.observableArrayList();
    private final List<Administrador> administradores = new ArrayList<>();

    private int sigCategoria = 1, sigEquipo = 1, sigJugador = 1,
                sigPartido = 1, sigResultado = 1, sigPosicion = 1;

    private DatosServicio() {
        administradores.add(new AdministradorEquipos(1, "Ana Torres", 35, "ana@uni.mx",
                "admin.equipos", "1234", "Equipos y jugadores"));
        administradores.add(new AdministradorPartidos(2, "Luis Mora", 40, "luis@uni.mx",
                "admin.partidos", "1234", "Matutino"));
        administradores.add(new AdministradorTorneo(3, "Sofía Ruiz", 38, "sofia@uni.mx",
                "admin.torneo", "1234", "Total"));
    }

    // ---------- Acceso a las listas ----------
    public ObservableList<Categoria> getCategorias() { return categorias; }
    public ObservableList<Equipo> getEquipos() { return equipos; }
    public ObservableList<Jugador> getJugadores() { return jugadores; }
    public ObservableList<Partido> getPartidos() { return partidos; }
    public ObservableList<Posicion> getPosiciones() { return posiciones; }

    public Administrador autenticar(String usuario, String contrasena) {
        for (Administrador a : administradores) {
            if (a.iniciarSesion(usuario, contrasena)) return a;
        }
        return null;
    }

    // ---------- Categorías ----------
    public Categoria agregarCategoria(String nombre, int min, int max, EstadoCategoria estado) {
        validarCategoria(null, nombre, min, max);
        Categoria c = new Categoria(sigCategoria++, nombre, min, max);
        c.setEstado(estado);
        categorias.add(c);
        return c;
    }

    public void actualizarCategoria(Categoria c, String nombre, int min, int max, EstadoCategoria estado) {
        validarCategoria(c, nombre, min, max);
        for (Jugador j : jugadores) {
            if (j.getEquipo().getCategoria() == c && (j.getEdad() < min || j.getEdad() > max)) {
                throw new IllegalArgumentException("El jugador " + j.getNombre()
                        + " (" + j.getEdad() + " años) quedaría fuera del nuevo rango.");
            }
        }
        c.setNombreCategoria(nombre);
        c.setEdadMinima(min);
        c.setEdadMaxima(max);
        c.setEstado(estado);
    }

    public void eliminarCategoria(Categoria c) {
        for (Equipo e : equipos) {
            if (e.getCategoria() == c) {
                throw new IllegalArgumentException("No se puede eliminar: hay equipos en esta categoría.");
            }
        }
        categorias.remove(c);
    }

    private void validarCategoria(Categoria actual, String nombre, int min, int max) {
        if (min > max) throw new IllegalArgumentException("La edad mínima no puede ser mayor que la máxima.");
        for (Categoria c : categorias) {
            if (c != actual && c.getNombreCategoria().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
            }
        }
    }

    // ---------- Equipos ----------
    public Equipo agregarEquipo(String nombre, String universidad, String entrenador,
                                Categoria categoria, String rutaLogo, EstadoEquipo estado) {
        validarEquipo(null, nombre);
        Equipo e = new Equipo(sigEquipo++, nombre, universidad, categoria);
        e.setEntrenador(entrenador);
        e.setRutaLogo(rutaLogo);
        e.setEstado(estado);
        equipos.add(e);
        posiciones.add(new Posicion(sigPosicion++, e));
        return e;
    }

    public void actualizarEquipo(Equipo e, String nombre, String universidad, String entrenador,
                                 Categoria categoria, String rutaLogo, EstadoEquipo estado) {
        validarEquipo(e, nombre);
        for (Jugador j : jugadoresDe(e)) {
            if (!categoria.admiteEdad(j.getEdad())) {
                throw new IllegalArgumentException("El jugador " + j.getNombre()
                        + " no cumple el rango de edad de la categoría elegida.");
            }
        }
        e.setNombreEquipo(nombre);
        e.setUniversidad(universidad);
        e.setEntrenador(entrenador);
        e.setCategoria(categoria);
        e.setRutaLogo(rutaLogo);
        e.setEstado(estado);
    }

    public void eliminarEquipo(Equipo e) {
        if (!jugadoresDe(e).isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar: el equipo tiene jugadores registrados.");
        }
        for (Partido p : partidos) {
            if (p.getEquipoLocal() == e || p.getEquipoVisitante() == e) {
                throw new IllegalArgumentException("No se puede eliminar: el equipo tiene partidos programados.");
            }
        }
        equipos.remove(e);
        posiciones.removeIf(p -> p.getEquipo() == e);
    }

    private void validarEquipo(Equipo actual, String nombre) {
        for (Equipo e : equipos) {
            if (e != actual && e.getNombreEquipo().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("Ya existe un equipo con ese nombre.");
            }
        }
    }

    // ---------- Jugadores ----------
    public List<Jugador> jugadoresDe(Equipo e) {
        return jugadores.stream().filter(j -> j.getEquipo() == e).collect(Collectors.toList());
    }

    public Jugador agregarJugador(String nombre, int edad, int numero, PosicionCampo posicion,
                                  Equipo equipo, String rutaFoto, EstadoJugador estado) {
        validarJugador(null, edad, numero, equipo);
        Jugador j = new Jugador(sigJugador++, nombre, edad, numero, posicion, equipo);
        j.setRutaFoto(rutaFoto);
        j.setEstado(estado);
        jugadores.add(j);
        return j;
    }

    public void actualizarJugador(Jugador j, String nombre, int edad, int numero, PosicionCampo posicion,
                                  Equipo equipo, String rutaFoto, EstadoJugador estado) {
        validarJugador(j, edad, numero, equipo);
        j.setNombre(nombre);
        j.setEdad(edad);
        j.setNumeroCamiseta(numero);
        j.setPosicionCampo(posicion);
        j.setEquipo(equipo);
        j.setRutaFoto(rutaFoto);
        j.setEstado(estado);
    }

    public void eliminarJugador(Jugador j) { jugadores.remove(j); }

    private void validarJugador(Jugador actual, int edad, int numero, Equipo equipo) {
        Categoria c = equipo.getCategoria();
        if (!c.admiteEdad(edad)) {
            throw new IllegalArgumentException("La edad " + edad + " no corresponde a la categoría "
                    + c.getNombreCategoria() + " (" + c.getEdadMinima() + "-" + c.getEdadMaxima() + ").");
        }
        for (Jugador j : jugadoresDe(equipo)) {
            if (j != actual && j.getNumeroCamiseta() == numero) {
                throw new IllegalArgumentException("El número " + numero + " ya lo usa " + j.getNombre() + ".");
            }
        }
    }

    // ---------- Partidos y resultados ----------
    public Partido agregarPartido(Equipo local, Equipo visitante, LocalDate fecha,
                                  LocalTime hora, String lugar, EstadoPartido estado) {
        validarPartido(local, visitante);
        Partido p = new Partido(sigPartido++, local, visitante, fecha, hora);
        p.setLugar(lugar);
        p.setEstado(estado);
        partidos.add(p);
        return p;
    }

    public void actualizarPartido(Partido p, Equipo local, Equipo visitante, LocalDate fecha,
                                  LocalTime hora, String lugar, EstadoPartido estado) {
        validarPartido(local, visitante);
        if (p.getResultado() != null) {
            if (local != p.getEquipoLocal() || visitante != p.getEquipoVisitante()) {
                throw new IllegalArgumentException("No se pueden cambiar los equipos de un partido con resultado.");
            }
            if (estado != EstadoPartido.FINALIZADO) {
                throw new IllegalArgumentException("Un partido con resultado debe permanecer Finalizado.");
            }
        }
        p.setEquipoLocal(local);
        p.setEquipoVisitante(visitante);
        p.setFecha(fecha);
        p.setHora(hora);
        p.setLugar(lugar);
        p.setEstado(estado);
    }

    public void eliminarPartido(Partido p) {
        partidos.remove(p);
        recalcularPosiciones();
    }

    /** Guarda el marcador, marca el partido como Finalizado y actualiza la tabla. */
    public void registrarResultado(Partido p, int golesLocal, int golesVisitante, EstadoResultado estado) {
        if (golesLocal < 0 || golesVisitante < 0) {
            throw new IllegalArgumentException("Los goles no pueden ser negativos.");
        }
        int id = p.getResultado() == null ? sigResultado++ : p.getResultado().getId();
        Resultado r = new Resultado(id, golesLocal, golesVisitante);
        r.determinarGanador(p.getEquipoLocal(), p.getEquipoVisitante());
        r.setEstado(estado);
        p.setResultado(r);
        p.setEstado(EstadoPartido.FINALIZADO);
        recalcularPosiciones();
    }

    private void validarPartido(Equipo local, Equipo visitante) {
        if (local == visitante) throw new IllegalArgumentException("El equipo local y el visitante deben ser distintos.");
        if (local.getCategoria() != visitante.getCategoria()) {
            throw new IllegalArgumentException("Ambos equipos deben ser de la misma categoría.");
        }
        for (Equipo e : new Equipo[]{local, visitante}) {
            if (e.getEstado() == EstadoEquipo.ELIMINADO || e.getEstado() == EstadoEquipo.RETIRADO) {
                throw new IllegalArgumentException("El equipo " + e.getNombreEquipo() + " está " + e.getEstado() + ".");
            }
        }
    }

    // ---------- Tabla de posiciones ----------
    public void recalcularPosiciones() {
        for (Posicion pos : posiciones) pos.reiniciar();
        for (Partido p : partidos) {
            Resultado r = p.getResultado();
            if (r == null) continue;
            Posicion l = buscarPosicion(p.getEquipoLocal());
            Posicion v = buscarPosicion(p.getEquipoVisitante());
            if (l != null) l.registrar(r.getGolesLocal(), r.getGolesVisitante());
            if (v != null) v.registrar(r.getGolesVisitante(), r.getGolesLocal());
        }
        Comparator<Posicion> orden = Comparator.comparingInt(Posicion::getPuntos).reversed()
                .thenComparing(Comparator.comparingInt(Posicion::getDiferenciaGoles).reversed())
                .thenComparing(Comparator.comparingInt(Posicion::getGolesAFavor).reversed());
        FXCollections.sort(posiciones, orden);
    }

    private Posicion buscarPosicion(Equipo e) {
        for (Posicion p : posiciones) if (p.getEquipo() == e) return p;
        return null;
    }
}
