import dominio.Actividad
import dominio.Usuario

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class UtilsTest {

    @Test
    fun `deberia devolver la fecha actual en el formato correcto`() {
        val fechaActual = Utils.obtenerFechaActual()
        assertTrue(Utils.esFechaValida(fechaActual))
    }

    @Test
    fun `deberia validar una fecha correcta`() {
        val fechaValida = "15-10-2023"
        assertTrue(Utils.esFechaValida(fechaValida))
    }

    @Test
    fun `deberia invalidar una fecha incorrecta`() {
        val fechaInvalida = "Hola"
        assertFalse(Utils.esFechaValida(fechaInvalida))
    }

    @Test
    fun `deberia devolver null al deserializar una actividad con formato invalido`() {
        val actividad = Utils.deserializarActividad("usuario;id;descripcion;fechaInvalida")
        assertNull(actividad)
    }

    @Test
    fun `deberia leer un archivo existente y devolver las lineas no vacias`() {
        val archivo = File("test.txt")
        archivo.writeText("Linea 1\n\nLinea 2\n")
        val lineas = Utils.leerArchivo("test.txt")
        assertEquals(listOf("Linea 1", "Linea 2"), lineas)
        archivo.delete()
    }

    @Test
    fun `deberia devolver una lista vacia al leer un archivo inexistente`() {
        val lineas = Utils.leerArchivo("archivo_inexistente.txt")
        assertTrue(lineas.isEmpty())
    }

    @Test
    fun `deberia aniadir una actividad al archivo`() {
        val archivo = File("actividades.txt")
        archivo.writeText("")
        val actividad = object : Actividad("Descripcion", "Usuario") {}
        Utils.aniadirActividad("actividades.txt", actividad)
        val contenido = archivo.readText()
        assertTrue(contenido.contains("Usuario;"))
        archivo.delete()
    }

    @Test
    fun `deberia aniadir un usuario al archivo`() {
        val archivo = File("usuarios.txt")
        archivo.writeText("")
        val usuario = Usuario.creaInstancia("UsuarioPrueba")
        Utils.aniadirUsuario("usuarios.txt", usuario)
        val contenido = archivo.readText()
        assertTrue(contenido.contains("UsuarioPrueba"))
        archivo.delete()
    }
}