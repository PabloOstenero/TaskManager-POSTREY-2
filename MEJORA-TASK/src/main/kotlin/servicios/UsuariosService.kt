package servicios

import accesoDatos.RepoUsuarios
import presentacion.ConsolaUI

class UsuariosService(
    consola: ConsolaUI
) {
    var usuariosRepo = RepoUsuarios()

}