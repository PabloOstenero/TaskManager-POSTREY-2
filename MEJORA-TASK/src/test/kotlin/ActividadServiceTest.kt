import AccesoDatos.RepoActividades
import Dominio.*
import Presentacion.ConsolaUI
import Servicios.*
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*

class ActividadServiceTest : DescribeSpec({
    val mockRepoActividades = mockk<RepoActividades>(relaxed = true)
    val mockUsuariosService = mockk<UsuariosService>(relaxed = true)
    val mockConsola = mockk<ConsolaUI>(relaxed = true)
    val mockHistorial = mockk<ControlDeHistorial>(relaxed = true)
    val actividadService = ActividadService(
        mockConsola,
        mockRepoActividades,
        mockUsuariosService,
        mockHistorial
    )

    beforeEach {
        clearAllMocks()
    }

    describe("agregarSubtarea") {
        it("debería agregar una subtarea a una tarea madre existente") {
            val tareaMadre = mockk<Tarea>(relaxed = true)
            every { mockRepoActividades.tareas } returns mutableListOf(tareaMadre)
            every { mockConsola.pedirInfo(any()) } returns "1"
            every { mockConsola.pedirEtiqueta() } returns EtiquetasTareas.URGENTE
            every { tareaMadre.getIdActividad() } returns "1"

            actividadService.agregarSubtarea()

            verify { tareaMadre.agregarSubTarea(any()) }
            verify { mockHistorial.agregarHistorial(any()) }
        }

        it("debería mostrar un error si no se encuentra la tarea madre") {
            every { mockRepoActividades.tareas } returns mutableListOf()
            every { mockConsola.pedirInfo(any()) } returns "1"
            every { mockRepoActividades.tareas.find { it.getIdActividad().toInt() == 1 } } returns null

            actividadService.agregarSubtarea()

            verify(exactly = 0) { mockHistorial.agregarHistorial(any()) }
        }
    }

    describe("usuariosConActividades") {
        it("debería asociar actividades a usuarios correctamente") {
            val usuario = mockk<Usuario>(relaxed = true)
            val actividad = mockk<Actividad>(relaxed = true)
            every { mockUsuariosService.usuariosRepo.usuarios } returns mutableListOf(usuario)
            every { mockRepoActividades.actividades } returns mutableListOf(actividad)
            every { actividad.obtenerUsuario() } returns "usuario1"
            every { usuario.nombre } returns "usuario1"
            every { usuario.repoActividades.actividades.contains(actividad) } returns false

            actividadService.usuariosConActividades()

            verify { usuario.repoActividades.actividades.add(actividad) }
        }

        it("debería mostrar un error si no se encuentra el usuario") {
            val actividad = mockk<Actividad>(relaxed = true)
            every { mockUsuariosService.usuariosRepo.usuarios } returns mutableListOf()
            every { mockRepoActividades.actividades } returns mutableListOf(actividad)
            every { actividad.obtenerUsuario() } returns "usuario1"

            actividadService.usuariosConActividades()

            verify(exactly = 0) { mockHistorial.agregarHistorial(any()) }
        }
    }

    describe("filtrar") {
        it("debería manejar la opción de filtrado por estado correctamente") {
            every { mockConsola.pedirOpcion(any(), any(), any()) } returns 2 andThen 0

            actividadService.filtrar()

            verify { actividadService.filtrarPorEstado() }
        }

        it("debería manejar los errores") {
            every { mockConsola.pedirOpcion(any(), any(), any()) } returns 6 andThen 0

            actividadService.filtrar()

            verify(exactly = 0) { actividadService.filtrarPorEstado() }
        }
    }

    describe("filtrarPorEstado") {
        it("debería filtrar tareas por estado ABIERTA") {
            val tareaAbierta = mockk<Tarea>(relaxed = true)
            every { tareaAbierta.estado } returns EstadoTarea.ABIERTA

            every { mockRepoActividades.tareas } returns mutableListOf(tareaAbierta)

            every { mockConsola.pedirOpcion(any(), any(), any()) } returns 1 andThen 0

            actividadService.filtrarPorEstado()

            verify { mockConsola.pedirOpcion(any(), 0, 3) }
            verify { tareaAbierta.obtenerDetalle() }
        }

        it("debería filtrar tareas por estado EN_PROGRESO") {
            val tareaCerrada = mockk<Tarea>(relaxed = true)
            every { tareaCerrada.estado } returns EstadoTarea.EN_PROGRESO

            every { mockRepoActividades.tareas } returns mutableListOf(tareaCerrada)

            every { mockConsola.pedirOpcion(any(), any(), any()) } returns 2 andThen 0

            actividadService.filtrarPorEstado()

            verify { mockConsola.pedirOpcion(any(), 0, 3) }
            verify { tareaCerrada.obtenerDetalle() }
        }

        it("debería filtrar tareas por estado FINALIZADA") {
            val tareaFinalizada = mockk<Tarea>(relaxed = true)
            every { tareaFinalizada.estado } returns EstadoTarea.FINALIZADA

            every { mockRepoActividades.tareas } returns mutableListOf(tareaFinalizada)

            every { mockConsola.pedirOpcion(any(), any(), any()) } returns 3 andThen 0

            actividadService.filtrarPorEstado()

            verify { mockConsola.pedirOpcion(any(), 0, 3) }
            verify { tareaFinalizada.obtenerDetalle() }
        }

        it("debería manejar errores") {
            val tarea = mockk<Tarea>(relaxed = true)
            every { mockRepoActividades.tareas } returns mutableListOf(tarea)
            every { mockConsola.pedirOpcion(any(), any(), any()) } returns 4 andThen 0

            actividadService.filtrarPorEstado()

            verify(exactly = 0) { tarea.estado }
        }

    }
})