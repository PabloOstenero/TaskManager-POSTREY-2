package accesoDatos
import dominio.Usuario
import java.io.File

/**
 * Esta clase sirve para poder gestionar los usuarios, dando opciones para añadir usuarios y guardarlos en un fichero y
 * cargar los usuarios de dicho fichero, el cual se encuentra en la ruta descrita por la variable rutaFicheroUsuario.
 * */
class RepoUsuarios(
    val usuarios: MutableList<Usuario> = mutableListOf()
) {
    /**
     * Al instanciar la clase, se cargan los usuarios del fichero de texto.
     * */
    init{
        cargarUsuarios()
    }

    /**
     * Esta función añade un usuario a la lista de usuarios y lo guarda en el fichero de texto.
     * @param usuario Usuario a añadir.
     * */
    fun agregarUsuario(usuario:Usuario){
        usuarios.add(usuario)
        Utils.aniadirUsuario(rutaFicheroUsuario,usuario)
    }

    /**
     * Esta función carga los usuarios del fichero de texto y los añade a la lista de usuarios.
     * */
    private fun cargarUsuarios(){
        val usuariosFichero = Utils.leerArchivo(rutaFicheroUsuario)
        for(usuario in usuariosFichero){
            usuarios.add(Utils.deserializarUsuario(usuario))
        }
    }

    companion object{
        /**
         * Esta variable contiene la ruta del fichero de texto donde se guardan los usuarios.
         * */
        val rutaFicheroUsuario =
            "${System.getProperty("user.dir")}/src/main/kotlin/datos/Usuarios.txt".replace(
                "/",
                File.separator
            )
    }
}
