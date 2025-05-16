package AccesoDatos
import Dominio.Usuario
import java.io.File

class RepoUsuarios(
    val usuarios: MutableList<Usuario> = mutableListOf()
) {

    init{
        cargarUsuarios()
    }

    fun agregarUsuario(usuario:Usuario){
        usuarios.add(usuario)
        Utils.aniadirUsuario(rutaFicheroUsuario,usuario)
    }

    private fun cargarUsuarios(){
        val usuariosFichero = Utils.leerArchivo(rutaFicheroUsuario)
        for(usuario in usuariosFichero){
            usuarios.add(Utils.deserializarUsuario(usuario))
        }
    }

    companion object{
        val rutaFicheroUsuario =
            "${System.getProperty("user.dir")}/src/main/kotlin/Datos/Usuarios.txt".replace(
                "/",
                File.separator
            )
    }
}