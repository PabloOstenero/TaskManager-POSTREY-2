package dominio

/**
 * Enum class que contiene los estados en los que se puede encontrar la tarea.
 * La tarea puede ser ABIERTA, EN_PROGRESO o FINALIZADA.
 */
enum class EstadoTarea {
    ABIERTA,
    EN_PROGRESO,
    FINALIZADA;

    companion object{
        fun getEstado(estado:String): EstadoTarea?{
            return when(estado.uppercase()){
                "ABIERTA"-> ABIERTA
                "FINALIZADA"-> FINALIZADA
                "EN_PROGRESO"-> EN_PROGRESO
                "EN PROGRESO"-> EN_PROGRESO
                else -> null
            }
        }
    }
}
