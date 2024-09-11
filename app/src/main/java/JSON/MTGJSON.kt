package JSON

import Paso
import java.io.Serializable


data class MTGJSON(
    val Titulos_de_Barajas: List<TitulosDeBaraja>
) : Serializable

data class TitulosDeBaraja(
    val ID: Int,
    val Titulo: String,
    val Imagen: String,
    val Color: String,
    val Pasos: List<Paso>,
    val Cartas: List<Carta>
) : Serializable

