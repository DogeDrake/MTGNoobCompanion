package com.example.mtgcompanion

import JSON.Carta
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CartasAdapter(private var cartas: List<Carta>) : RecyclerView.Adapter<CartasAdapter.CartaViewHolder>() {

    private var filteredCartas: List<Carta> = cartas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carta, parent, false)
        return CartaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartaViewHolder, position: Int) {
        holder.bind(filteredCartas[position])
    }

    override fun getItemCount(): Int = filteredCartas.size

    fun filter(query: String) {
        filteredCartas = if (query.isEmpty()) {
            cartas
        } else {
            cartas.filter { it.Titulo.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    class CartaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.cartaTitulo)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.cartaDescripcion)
        private val costeTextView: TextView = itemView.findViewById(R.id.cartaCoste)

        fun bind(carta: Carta) {
            tituloTextView.text = carta.Titulo
            descripcionTextView.text = carta.Descripción
            costeTextView.text = carta.Coste
        }
    }
}
