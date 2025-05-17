package dominio

enum class EtiquetasTareas {
    URGENTE,
    DOCUMENTACION,
    REVISION,
    SENCILLA;

    companion object {
        fun getEtiqueta(eti:String): EtiquetasTareas?{
            return when(eti.uppercase()){
                URGENTE.toString().uppercase()-> return URGENTE
                DOCUMENTACION.toString().uppercase()-> return DOCUMENTACION
                REVISION.toString().uppercase()-> return REVISION
                SENCILLA.toString().uppercase()-> return SENCILLA
                else-> null
            }
        }
    }
}
