package accesoDatos

import dominio.Actividad
import dominio.EstadoTarea
import dominio.Evento
import dominio.Tarea
import servicios.ControlDeHistorial
import java.io.File

/**
 * Esta clase sirve para poder gestionar las actividades, dando opciones para añadir actividades y guardarlas en un fichero o
 * cargar las actividades de dicho fichero, el cual se encuentra en la ruta descrita por la variable RUTA_FICHERO_ACTIVIDADES.
 *
 * También permite cambiar el estado de una tarea.
 * */
class RepoActividades(
    override val actividades: MutableList<Actividad> = mutableListOf(),
    override val tareas: MutableList<Tarea> = mutableListOf(),
    override val eventos: MutableList<Evento> = mutableListOf()
) : IActividadRepository {

    /**
     * Al instanciar la clase, se cargan las actividades del fichero de texto.
     * */
    init {
        cargarActividades()
    }

    /**
     * Esta función permite cambiar el estado de una tarea y actualizar el fichero de texto.
     *
     * @param tarea Tarea a la que se le quiere cambiar el estado.
     * @param historial Control de historial para registrar el cambio de estado.
     * @param estadoTarea Nuevo estado de la tarea.
     * */
    fun cambiarEstado(tarea: Tarea, historial: ControlDeHistorial, estadoTarea: EstadoTarea) {
        val id = tarea.getIdActividad()
        tarea.estado = estadoTarea
        val archivo = File(RUTA_FICHERO_ACTIVIDADES)

        archivo.writeText("") // Limpiar el archivo antes de escribir

        tareas.forEach { tareaPrincipal ->
            archivo.appendText("${tareaPrincipal.obtenerDetalle()}\n")

            if (tareaPrincipal.subTareas.isNotEmpty()) {
                tareaPrincipal.subTareas.forEach { subTarea ->
                    archivo.appendText("    - ${subTarea.obtenerDetalle()}\n")
                }
            }
        }

        println("¡Tarea cerrada con éxito!")
        historial.agregarHistorial("Tarea con id $id con estado cambiado a $estadoTarea con éxito")
    }

    /**
     * Esta función añade una actividad a la lista de actividades y la guarda en el fichero de texto.
     *
     * @param actividad Actividad a añadir.
     * */
    fun aniadirActividad(actividad: Actividad) {
        if (!actividades.contains(actividad)) { // Evitar duplicados en la lista de actividades
            actividades.add(actividad)

            when (actividad) {
                is Tarea -> if (!tareas.contains(actividad)) tareas.add(actividad)
                is Evento -> if (!eventos.contains(actividad)) eventos.add(actividad)
            }

            Utils.aniadirActividad(RUTA_FICHERO_ACTIVIDADES, actividad)
        } else {
            println("La actividad ya existe, no se añadirá de nuevo.")
        }
    }

    /**
     * Esta función carga las actividades del fichero de texto y las añade a la lista de actividades.
     * */
    private fun cargarActividades() {
        val ficheroActividades = Utils.leerArchivo(RUTA_FICHERO_ACTIVIDADES)
        for (linea in ficheroActividades) {
            manejarActividad(linea) // Cargar cada actividad
        }
    }

    /**
     * Esta función maneja la carga de una actividad desde el fichero de texto.
     *
     * @param linea Línea del fichero que contiene la actividad.
     * */
    private fun manejarActividad(linea: String) {
        try {
            val actividad = Utils.deserializarActividad(linea)
            if (actividad != null && !actividades.contains(actividad)) { // Verificar que no sea null y evitar duplicados
                actividades.add(actividad)

                when (actividad) {
                    is Tarea -> tareas.add(actividad)
                    is Evento -> eventos.add(actividad)
                }
            }
        } catch (e: Exception) {
            println("Error al cargar una actividad desde el fichero: ${e.message}")
        }
    }

    companion object {
        /**
         * Esta variable contiene la ruta del fichero de texto donde se guardan las actividades.
         * */
        val RUTA_FICHERO_ACTIVIDADES =
            "${System.getProperty("user.dir")}/src/main/kotlin/datos/Actividades.txt".replace(
                "/",
                File.separator
            )
    }
}
