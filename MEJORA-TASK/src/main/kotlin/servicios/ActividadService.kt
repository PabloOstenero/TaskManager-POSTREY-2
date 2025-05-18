package servicios

import accesoDatos.RepoActividades
import dominio.Tarea
import dominio.Evento
import dominio.Actividad
import dominio.EtiquetasTareas
import dominio.EstadoTarea
import presentacion.ConsolaUI
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * Clase que gestiona las actividades del programa.
 * Permite añadir, listar, cambiar el estado de las actividades y filtrar por diferentes criterios.
 * Esta clase también muestra los resúmenes de las actividades y gestiona la creación de subtareas.
 * Permite la interacción con el usuario a través de la consola.
 *
 * @param consola ConsolaUI para la interacción con el usuario.
 * @param repo RepoActividades para gestionar las actividades.
 * @param servicioUsuario UsuariosService para gestionar los usuarios.
 * @param historial ControlDeHistorial para gestionar el historial de actividades.
 * @param logger Logger para registrar eventos y errores.
 * */
class ActividadService (
    private val consola: ConsolaUI = ConsolaUI(),
    private val repo: RepoActividades = RepoActividades(),
    private val servicioUsuario: UsuariosService = UsuariosService(),
    private val historial: ControlDeHistorial = ControlDeHistorial(),
    private val logger: Logger = LoggerFactory.getLogger(ActividadService::class.java))
{

    /**
     * Método principal que gestiona el programa. Muestra el menú y permite al usuario elegir opciones.
     * */
    fun gestionarPrograma() {
        try {
            usuariosConActividades()
            do {
                consola.mostrarMenu()
                val opcion = consola.pedirOpcion("Elige una opción", 0, 6)
                gestionarOpcion(opcion)
            } while (opcion != 0)
        } catch (e: Exception) {
            logger.error("Error inesperado al gestionar el programa: ${e.message}", e)
        }
    }

    /**
     * Método que permite agregar una subtarea a una tarea madre.
     * Primero lista las tareas disponibles y luego pide al usuario que seleccione la tarea madre por ID.
     * Luego crea la subtarea y la asigna a la tarea madre.
     * Finalmente actualiza el fichero para incluir la tarea madre con sus subtareas.
     * */
    fun agregarSubtarea() {
        try {
            // Listar las tareas disponibles para elegir la tarea madre
            consola.listarTareas(repo.tareas)

            // Pedir al usuario que seleccione la tarea madre por ID
            val idTareaMadre = consola.pedirInfo("Introduce el ID de la tarea madre a la que deseas agregar una subtarea:").toIntOrNull()
            val tareaMadre = repo.tareas.find { it.getIdActividad().toInt() == idTareaMadre }

            if (tareaMadre != null) {
                // Crear la subtarea
                val subtarea = Tarea.creaInstancia(
                    consola.pedirInfo("Descripción de la subtarea:"),
                    consola.pedirInfo("Usuario asignado:"),
                    consola.pedirEtiqueta()
                )

                // Asignar la subtarea a la tarea madre
                tareaMadre.agregarSubTarea(subtarea)

                // Actualizar el fichero para incluir la tarea madre con sus subtareas
                Utils.actualizarTareaEnFichero(tareaMadre)

                println("¡Subtarea añadida con éxito!")
                historial.agregarHistorial("Subtarea agregada a la tarea ${tareaMadre.getIdActividad()}")
            } else {
                println("Error: No se encontró la tarea madre con el ID proporcionado.")
            }
        } catch (e: Exception) {
            println("¡Error! Detalle: ${e.message}")
        }
    }

    /**
     * Método que asocia los usuarios con sus respectivas actividades.
     * Recorre todos los usuarios y actividades, y si la actividad pertenece al usuario, la añade a su repositorio de actividades.
     * */
    fun usuariosConActividades() {
        try {
            for (usuario in servicioUsuario.usuariosRepo.usuarios) {
                for (actividad in repo.actividades) {
                    if (actividad.obtenerUsuario() == usuario.nombre && !usuario.repoActividades.actividades.contains(actividad)) {
                        usuario.repoActividades.actividades.add(actividad)
                        when (actividad) {
                            is Tarea -> usuario.repoActividades.tareas.add(actividad)
                            is Evento -> usuario.repoActividades.eventos.add(actividad)
                        }
                    }
                }
            }
            logger.trace("Usuarios asociados con actividades correctamente.")
        } catch (e: Exception) {
            logger.error("Error al asociar usuarios con actividades: ${e.message}", e)
        }
    }

    /**
     * Método que cambia el estado de una tarea.
     * Pide al usuario que introduzca el nuevo estado y actualiza la tarea y su subtarea.
     * */
    private fun cambiarEstado(tarea: Tarea) {
        val estadoNuevo = consola.pedirInfo("CAMBIE EL ESTADO DE LA TAREA: ABIERTA, EN_PROGRESO, FINALIZADA")
        val estado = EstadoTarea.getEstado(estadoNuevo)
        if (estado != null) {
            tarea.actualizarEstado(estado)
            repo.cambiarEstado(tarea, historial, estado)
            println("¡Estado de la tarea y su subtarea actualizado con éxito!")
        } else {
            println("¡Error! Estado no válido.")
        }
    }

    /**
     * Método que filtra las actividades por tipo (Tarea o Evento).
     * Muestra un menú al usuario para que elija qué tipo de actividad desea ver.
     * */
    private fun filtradoPorTipo(){
        var opcion = -1

        do{
            try{
                println("1) Mostrar tareas")
                println("2) Mostrar eventos")
                println("0) SALIR")
                opcion = consola.pedirOpcion("Introduce opción",0,2)
                when(opcion){
                    1-> consola.listarTareas(repo.tareas)
                    2-> consola.listarEventos(repo.eventos)
                    else-> throw Exception("El valor introducido se sale del rango")
                }
            }catch(e: Exception){
                println("¡Error! Detalle: $e")
            }
        }while(opcion != 0)
    }

    /**
     * Método que filtra las tareas por su estado (Abierta, En Progreso, Finalizada).
     * Muestra un menú al usuario para que elija qué estado desea ver.
     * */
    fun filtrarPorEstado(){
        var opcion = -1

        do{
            try{
                println("1) MOSTRAR ABIERTAS")
                println("2) MOSTRAR EN PROGRESO")
                println("3) MOSTRAR FINALIZADAS")
                println("0) SALIR")
                opcion = consola.pedirOpcion("Introduce opción",0,3)

                var filtrado: EstadoTarea?

                if (opcion != 0) {
                    filtrado = when(opcion){
                        1-> EstadoTarea.ABIERTA
                        2-> EstadoTarea.EN_PROGRESO
                        3-> EstadoTarea.FINALIZADA
                        else-> throw Exception("El valor introducido se sale del rango")
                    }

                    for(tarea in repo.tareas){
                        if(tarea.estado == filtrado){
                            println(tarea.obtenerDetalle())
                        }
                    }
                }

            }catch(e: Exception){
                println("¡Error! Detalle: $e")
            }
        }while(opcion != 0)
    }

    /**
     * Método que filtra las actividades por usuario.
     * Pide al usuario que introduzca el nombre del usuario y muestra las actividades asociadas a ese usuario.
     * Si no se encuentra el usuario, se le pregunta si desea continuar buscando.
     * */
    private fun filtradoPorUsuarios(){
        var seguir = true
        do{
            var encontrado = false
            try{
                println("Introduzca el nombre del usuario")
                val nombre = readln().trim()

                for(usuario in servicioUsuario.usuariosRepo.usuarios){
                    if(usuario.nombre == nombre){
                        encontrado = true
                        for(actividad in usuario.repoActividades.actividades){
                            println(actividad.obtenerDetalle())
                        }
                    }
                }

                if(!encontrado){
                    println("El usuario introducido no ha sido encontrado")
                    seguir = consola.preguntarSeguir()
                }

                else{
                    seguir = consola.preguntarSeguir()
                }
            }catch(e: Exception){
                println("¡Error! Detalle: $e")
            }
        }while(seguir)
    }

    /**
     * Método que filtra las tareas por etiquetas (Urgente, Sencilla, Documentación, Revisión).
     * Muestra un menú al usuario para que elija qué etiqueta desea ver.
     * */
    private fun filtradoPorEtiquetas(){
        var opcion = -1

        do{
            try{
                println("1) Mostrar tareas URGENTES")
                println("2) Mostrar tareas SENCILLAS")
                println("3) Mostrar tareas de DOCUMENTACIÓN")
                println("4) Mostrar tareas de REVISIÓN")
                println("0) SALIR")
                opcion = consola.pedirOpcion("Introduce opción",0,4)

                var filtrado: EtiquetasTareas?

                if (opcion != 0) {
                    filtrado = when (opcion) {
                        1 -> EtiquetasTareas.URGENTE
                        2 -> EtiquetasTareas.SENCILLA
                        3 -> EtiquetasTareas.DOCUMENTACION
                        4 -> EtiquetasTareas.REVISION
                        else -> throw Exception("El valor introducido se sale del rango")
                    }
                    for (tarea in repo.tareas) {
                        if (tarea.etiqueta == filtrado) {
                            println(tarea.obtenerDetalle())
                        }
                    }
                }

            }catch(e: Exception){
                println("¡Error! Detalle: $e")
            }
        }while(opcion != 0)
    }

    /**
     * Método que filtra las actividades por fecha (hoy, mañana, esta semana, este mes).
     * Muestra un menú al usuario para que elija qué tipo de filtro desea aplicar.
     * */
    private fun filtradoPorFechas() {
        var opcion: Int
        do {
            println("Selecciona una opción para filtrar actividades:")
            println("1) Enseñar actividades para hoy")
            println("2) Enseñar actividades para mañana")
            println("3) Enseñar actividades de esta semana")
            println("4) Enseñar actividades de este mes")
            println("0) SALIR")

            opcion = consola.pedirOpcion("Elige una opción", 0, 4)

            when (opcion) {
                1 -> filtrarActividades { it.fecha== Utils.obtenerFechaActual() }
                2 -> filtrarActividades { it.fecha == LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) }
                3 -> filtrarActividades { fechaDentroDeSemana(it.fecha) }
                4 -> filtrarActividades { fechaDentroDeMes(it.fecha) }
                0 -> println("Saliendo del filtrado por fechas...")
                else -> println("Opción no válida, intenta de nuevo.")
            }
        } while (opcion != 0)
    }

    /**
     * Método que filtra las actividades según una condición dada.
     * Si no se encuentran actividades que coincidan con la condición, se muestra un mensaje al usuario.
     * */
    private fun filtrarActividades(condicion: (Actividad) -> Boolean) {
        val actividadesFiltradas = repo.actividades.filter(condicion)
        if (actividadesFiltradas.isEmpty()) {
            println("No hay actividades que coincidan con el filtro.")
        } else {
            println("Actividades encontradas:")
            actividadesFiltradas.forEach { println(it.obtenerDetalle()) }
        }
    }

    /**
     * Método que verifica si una fecha está dentro de la semana actual.
     * @param fecha Fecha a verificar.
     * @return true si la fecha está dentro de la semana actual, false en caso contrario.
     * */
    private fun fechaDentroDeSemana(fecha: String): Boolean {
        val hoy = LocalDate.now()
        val inicioSemana = hoy.with(DayOfWeek.MONDAY)
        val finSemana = hoy.with(DayOfWeek.SUNDAY)
        val fechaActividad = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        return !fechaActividad.isBefore(inicioSemana) && !fechaActividad.isAfter(finSemana)
    }

    /**
     * Método que verifica si una fecha está dentro del mes actual.
     * @param fecha Fecha a verificar.
     * @return true si la fecha está dentro del mes actual, false en caso contrario.
     * */
    private fun fechaDentroDeMes(fecha: String): Boolean {
        val hoy = LocalDate.now()
        val fechaActividad = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        return fechaActividad.month == hoy.month && fechaActividad.year == hoy.year
    }

    /**
     * Método que filtra las actividades según el tipo de filtrado elegido por el usuario.
     * Muestra un menú al usuario para que elija qué tipo de filtrado desea aplicar.
     * */
    fun filtrar(){
        var opcion = -1
        do {
            try {
                println("SELECCIONE UN MODO DE FILTRADO")
                println("1.- Por tipo (Tarea/Evento)")
                println("2.- Por estado")
                println("3.- Por etiquetas")
                println("4.- Por usuarios")
                println("5.- Por fechas")
                println("0) SALIR")
                opcion = consola.pedirOpcion(">> ",0,5)

                if (opcion != 0) {
                    when(opcion){
                        1-> filtradoPorTipo()
                        2-> filtrarPorEstado()
                        3-> filtradoPorEtiquetas()
                        4-> filtradoPorUsuarios()
                        5-> filtradoPorFechas()
                        else -> throw Exception("El valor introducido se sale del rango")
                    }
                }
            }catch(e: Exception){
                println("¡Error! Vuelve a introducir Detalle: $e")
            }
        }while(opcion != 0)
    }

    /**
     * Método que muestra un resumen de las actividades.
     * Muestra el total de tareas, eventos y actividades realizadas.
     * También muestra el número de actividades para hoy, mañana, esta semana y este mes.
     * */
    private fun resumen() {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val hoy = LocalDate.now()
        val manana = hoy.plusDays(1)
        val semana = hoy.get(WeekFields.of(Locale.getDefault()).weekOfYear())
        val mes = hoy.monthValue

        // Filtrar actividades por fecha
        val actividadesHoy = repo.actividades.filter {
            LocalDate.parse(it.fecha, formatter) == hoy
        }
        val actividadesManana = repo.actividades.filter {
            LocalDate.parse(it.fecha, formatter) == manana
        }
        val actividadesSemana = repo.actividades.filter {
            val fechaActividad = LocalDate.parse(it.fecha, formatter)
            fechaActividad.get(WeekFields.of(Locale.getDefault()).weekOfYear()) == semana
        }
        val actividadesMes = repo.actividades.filter {
            val fechaActividad = LocalDate.parse(it.fecha, formatter)
            fechaActividad.monthValue == mes
        }

        // Imprimir resumen
        println("--------------------------------------------------")
        println("TOTAL DE TAREAS MADRES: ${repo.actividades.size}")
        println("TOTAL DE EVENTOS: ${repo.eventos.size}")
        println("EN TOTAL ${repo.actividades.size} ACTIVIDADES REALIZADAS")
        println("--------------------------------------------------")
        println("ACTIVIDADES PARA HOY: ${actividadesHoy.size}")
        println("ACTIVIDADES PARA MAÑANA: ${actividadesManana.size}")
        println("ACTIVIDADES PARA ESTA SEMANA: ${actividadesSemana.size}")
        println("ACTIVIDADES PARA ESTE MES: ${actividadesMes.size}")
        println("--------------------------------------------------")
        historial.agregarHistorial("Se ha mirado el resumen del programa")
    }

    /**
     * Método que gestiona la opción elegida por el usuario en el menú principal.
     * Llama a los métodos correspondientes según la opción elegida.
     * */
    private fun gestionarOpcion(opcion: Int) {
        when (opcion) {
            1 -> anadirActividad()
            2 -> listarActividades()
            3 -> cambiarEstado(pedirIdTarea())
            4 -> filtrar()
            5-> agregarSubtarea()
            6-> resumen()
        }
    }

    /**
     * Método que añade una actividad (Tarea o Evento) al repositorio de actividades.
     * Pide al usuario que elija qué tipo de actividad desea crear y llama al método correspondiente para crearla.
     * */
    private fun anadirActividad() {
        val opcion = consola.pedirOpcion("¿Qué quieres crear?\n1) Tarea\n2) Evento\n0) Cancelar", 0, 2)
        if (opcion == 0) return

        val actividad = consola.crearActividad(opcion, repo, servicioUsuario.usuariosRepo)
        if (actividad != null) {
            repo.aniadirActividad(actividad)
            println("¡Actividad añadida con éxito!")
            historial.agregarHistorial("Actividad agregada con éxito")
        } else {
            println("¡Error al crear la actividad!")
            historial.agregarHistorial("Error al crear actividad")
        }
    }

    /**
     * Método que lista todas las actividades disponibles en el repositorio.
     * Elimina duplicados y muestra la lista al usuario.
     * */
    private fun listarActividades() {
        val actividadesUnicas = repo.actividades.distinct() // Eliminar duplicados
        consola.listarActividades(actividadesUnicas.toMutableList())
        historial.agregarHistorial("Se listan todas las actividades")
    }

    /**
     * Método que pide al usuario el ID de una tarea y devuelve la tarea correspondiente.
     * Si no se encuentra la tarea, se le pide al usuario que lo intente de nuevo.
     * */
    private fun pedirIdTarea(): Tarea {
        var tareaEncontrada: Tarea? = null
        var tareaValida = false
        do {
            try {
                println("Introduzca el ID de la tarea:")
                val id = readln().toInt()
                tareaEncontrada = repo.tareas.first { it.getIdActividad().toInt() == id }
                tareaValida = true
            } catch (e: NoSuchElementException) {
                println("¡Error! ID no encontrado, inténtelo de nuevo.")
                historial.agregarHistorial("ID de la actividad no encontrado, se intenta de nuevo")
            }
        } while (!tareaValida)
        return tareaEncontrada!!
    }

    companion object {
        /**
         * Método principal que inicia el programa.
         * Crea una instancia de ActividadService y llama al método gestionarPrograma.
         * */
        fun iniciarPrograma() {
            ActividadService().gestionarPrograma()
        }
    }
}
