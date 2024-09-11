import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mtgcompanion.R

class PasosAdapter(private val pasos: List<Paso>) : RecyclerView.Adapter<PasosAdapter.PasoViewHolder>() {

    private var highlightedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paso, parent, false)
        return PasoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasoViewHolder, position: Int) {
        holder.bind(pasos[position], position == highlightedPosition)
    }

    override fun getItemCount(): Int = pasos.size

    fun setPasoHighlighted(position: Int, highlighted: Boolean) {
        highlightedPosition = if (highlighted) position else -1
        notifyDataSetChanged() // Actualiza la UI
    }

    class PasoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.pasoNombre)

        fun bind(paso: Paso, isHighlighted: Boolean) {
            nombreTextView.text = paso.Nombre
            val backgroundColor = if (isHighlighted) R.color.darkmode_custom_color else R.color.default_background
            nombreTextView.setBackgroundColor(ContextCompat.getColor(itemView.context, backgroundColor))
        }
    }
}

