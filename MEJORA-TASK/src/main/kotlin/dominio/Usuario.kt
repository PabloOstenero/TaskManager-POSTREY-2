package dominio

import accesoDatos.RepoActividades

class Usuario private constructor(
    val nombre: String = "Anónimo"
){
    val repoActividades = RepoActividades()

    companion object{
        fun creaInstancia(nombre:String):Usuario{
            return Usuario(nombre)
        }
    }
}
