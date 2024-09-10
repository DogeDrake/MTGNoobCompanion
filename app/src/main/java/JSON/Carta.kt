package JSON

import java.io.Serializable

data class Carta(
    val Coste: String,
    val Descripción: String,
    val ID: Int,
    val Imagen: String,
    val Titulo: String
):Serializable