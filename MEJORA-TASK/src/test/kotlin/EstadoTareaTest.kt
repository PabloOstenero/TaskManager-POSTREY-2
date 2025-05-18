
import dominio.EstadoTarea
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EstadoTareaTest {

    @Test
    fun `deberia devolver ABIERTA cuando el estado es ABIERTA`() {
        val estado = EstadoTarea.getEstado("ABIERTA")
        assertEquals(EstadoTarea.ABIERTA, estado)
    }

    @Test
    fun `deberia devolver FINALIZADA cuando el estado es FINALIZADA`() {
        val estado = EstadoTarea.getEstado("FINALIZADA")
        assertEquals(EstadoTarea.FINALIZADA, estado)
    }

    @Test
    fun `deberia devolver EN_PROGRESO cuando el estado es EN_PROGRESO`() {
        val estado = EstadoTarea.getEstado("EN_PROGRESO")
        assertEquals(EstadoTarea.EN_PROGRESO, estado)
    }

    @Test
    fun `deberia devolver EN_PROGRESO cuando el estado es EN PROGRESO con espacio`() {
        val estado = EstadoTarea.getEstado("EN PROGRESO")
        assertEquals(EstadoTarea.EN_PROGRESO, estado)
    }

    @Test
    fun `deberia devolver null cuando el estado no es valido`() {
        val estado = EstadoTarea.getEstado("INVALIDO")
        assertNull(estado)
    }

    @Test
    fun `deberia ignorar mayusculas y minusculas`() {
        val estado = EstadoTarea.getEstado("abierta")
        assertEquals(EstadoTarea.ABIERTA, estado)
    }
}
