# Torneo Universitario de Fútbol – Modelo de dominio en POO

Aplicación de escritorio (Java + JavaFX) para administrar un torneo de fútbol universitario: categorías por rango de
edad, equipos, jugadores, partidos, resultados y tabla de posiciones. Este repositorio entrega, sobre todo, la **capa del
modelo de dominio** diseñada con los principios de la Programación Orientada a Objetos (abstracción, encapsulamiento,
herencia y polimorfismo), junto con las interfaces gráficas que la utilizan.

> Los datos viven en memoria mientras la aplicación está abierta (no hay base de datos).

## Tecnologías

- Java (JDK 17 o superior)
- JavaFX y Scene Builder
- Maven
- Apache NetBeans

## Estructura de paquetes

```
com.mycompany.torneodeportivo
├── App.java              Arranque de la aplicación
├── modelo                Clases del dominio y enumeraciones  ← objeto de esta actividad
├── servicio              DatosServicio: listas en memoria y reglas de negocio
├── controlador           Un controlador por pantalla
└── util                  Validador, Alertas, ImagenUtil, Sesion, Convertidores
src/main/resources/.../vista   Archivos FXML y hoja de estilos
```

## Modelo de dominio

### Árbol de herencia (entidades humanas)

```
Persona (abstract)                    id, nombre, edad  +  getRol() abstracto
├── Administrador (abstract)          correo, usuario, contrasena, estado
│   ├── AdministradorEquipos  (final) seccionAsignada
│   ├── AdministradorPartidos (final) turnoAsignado
│   └── AdministradorTorneo   (final) nivelPermiso
└── Jugador (final)                   numeroCamiseta, posicionCampo, equipo, rutaFoto, estado
```

Otras clases del dominio: `Categoria`, `Equipo`, `Partido`, `Resultado` y `Posicion`, más ocho enumeraciones de estado
(`EstadoCategoria`, `EstadoEquipo`, `EstadoJugador`, `EstadoPartido`, `EstadoResultado`, `EstadoPosicion`,
`EstadoAdministrador` y `PosicionCampo`).

### Cumplimiento de los requerimientos de la actividad

| Requerimiento | Cómo se cumple |
|---|---|
| Clase base `abstract` | `Persona` y `Administrador` son abstractas y no se pueden instanciar. |
| Uso justificado de `final` | Clases hoja (`AdministradorEquipos`, `AdministradorPartidos`, `AdministradorTorneo`, `Jugador`); métodos `iniciarSesion`, `cerrarSesion`, `consultarInformacion`, `equals` y `hashCode` de `Persona`; setters llamados desde constructores. |
| Atributos `private` + getters/setters | Todas las clases. Los setters validan los datos y lanzan `IllegalArgumentException`. |
| Exactamente dos constructores | Uno vacío y otro con los datos mínimos para un estado válido (los datos opcionales tienen valores por defecto). |
| `equals` y `hashCode` | Basados únicamente en el atributo entero `id`. En las personas están definidos una sola vez, como `final`, en `Persona`. |
| `toString` | Devuelve el estado completo; en las subclases concatena `super.toString()` con sus datos propios. La contraseña se muestra enmascarada. |
| Javadoc y `@author` | Todas las clases, constructores y métodos públicos documentados con `@param` y `@return`. |

Para generar la documentación HTML: `javadoc -d docs -sourcepath src/main/java -subpackages com.mycompany.torneodeportivo.modelo`.

## Cómo compilar y ejecutar

1. Abrir el proyecto en NetBeans (**File → Open Project**).
2. Clic derecho sobre el proyecto → **Clean and Build**.
3. Clic derecho sobre el proyecto → **Run** (F6).

Usuarios de prueba (contraseña `1234`): `admin.equipos`, `admin.partidos`, `admin.torneo`.

## Funcionalidades de la aplicación

- Inicio de sesión y menú según el rol del administrador.
- CRUD de categorías, equipos, jugadores y partidos, con tablas, formularios y mensajes de éxito o error.
- Estados administrados con `ComboBox`; logo de equipo y foto de jugador con vista previa.
- Registro de resultados y tabla de posiciones calculada automáticamente.

## Autor

Christian Hernandez Garcia – Programación Orientada a Objetos – Universidad Tecnológica de Jalisco
