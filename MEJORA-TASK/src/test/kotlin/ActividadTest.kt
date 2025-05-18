import dominio.Actividad

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ActividadTest {

    @Test
    fun `deberia crear una actividad con descripcion y usuario validos`() {
        val actividad = object : Actividad("Descripción válida", "UsuarioPrueba") {}
        assertEquals("UsuarioPrueba", actividad.obtenerUsuario())
        assertTrue(actividad.obtenerDetalle().contains("Descripción válida"))
    }

    @Test
    fun `deberia lanzar una excepcion si la descripcion esta vacia`() {
        val exception = assertThrows<IllegalArgumentException> {
            object : Actividad("", "UsuarioPrueba") {}
        }
        assertEquals("¡La descripción no puede estar vacía!", exception.message)
    }

    @Test
    fun `deberia devolver el id de la actividad correctamente`() {
        val actividad = object : Actividad("Descripción válida", "UsuarioPrueba") {}
        assertNotNull(actividad.getIdActividad())
        assertTrue(actividad.getIdActividad().isNotBlank())
    }

    @Test
    fun `deberia devolver el detalle de la actividad en el formato correcto`() {
        val actividad = object : Actividad("Descripción válida", "UsuarioPrueba") {}
        val detalle = actividad.obtenerDetalle()
        assertTrue(detalle.contains("UsuarioPrueba"))
        assertTrue(detalle.contains("Descripción válida"))
        assertTrue(detalle.contains(actividad.getIdActividad()))
    }
}