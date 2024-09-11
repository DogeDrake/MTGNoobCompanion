import JSON.TitulosDeBaraja
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mtgcompanion.R

class BarajasAdapter(
    private val barajas: List<TitulosDeBaraja>,
    private val onBarajaClick: (TitulosDeBaraja) -> Unit
) : RecyclerView.Adapter<BarajasAdapter.BarajaViewHolder>() {

    override fun getItemCount(): Int = barajas.size

    inner class BarajaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.barajaNombre)
        private val imagenFondoImageView: ImageView = itemView.findViewById(R.id.barajaImagenFondo)
        private val colorContainer: LinearLayout = itemView.findViewById(R.id.barajaColorContainer)
        private val itemContainer: LinearLayout = itemView.findViewById(R.id.itemContainer)

        fun bind(baraja: TitulosDeBaraja) {
            nombreTextView.text = baraja.Titulo

            // Cargar la imagen de fondo usando Glide
            Glide.with(itemView.context)
                .load(baraja.Imagen)
                .into(imagenFondoImageView)

            // Limpiar el contenedor de colores
            colorContainer.removeAllViews()

            // Procesar el string de colores y agregar iconos
            val colorCodes = baraja.Color // Asegúrate de que esto es una cadena como "{W}{U}{G}"
            val colorPattern = "\\{(W|U|R|B|G)\\}".toRegex() // Regex para encontrar los colores

            colorPattern.findAll(colorCodes).forEach { matchResult ->
                val colorCode = matchResult.value
                val drawableRes = when (colorCode) {
                    "{W}" -> R.drawable.w
                    "{U}" -> R.drawable.u
                    "{R}" -> R.drawable.r
                    "{B}" -> R.drawable.b
                    "{G}" -> R.drawable.g
                    else -> null
                }

                drawableRes?.let {
                    val imageView = ImageView(itemView.context)
                    imageView.setImageResource(it)
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(4, 0, 4, 0)
                    imageView.layoutParams = layoutParams
                    colorContainer.addView(imageView)
                }
            }

            // Hacer todo el item clicable
            itemContainer.setOnClickListener {
                onBarajaClick(baraja)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarajaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_baraja, parent, false)
        return BarajaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarajaViewHolder, position: Int) {
        holder.bind(barajas[position])
    }
}
