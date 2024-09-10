package com.example.mtgcompanion

import JSON.MTGJSON
import JSON.TitulosDeBaraja
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BarajasAdapter(
    private val barajas: List<TitulosDeBaraja>,
    private val onBarajaClick: (TitulosDeBaraja) -> Unit
) : RecyclerView.Adapter<BarajasAdapter.BarajaViewHolder>() {

    inner class BarajaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo = itemView.findViewById<TextView>(R.id.barajaTitulo)

        fun bind(baraja: TitulosDeBaraja) {
            titulo.text = baraja.Titulo
            itemView.setOnClickListener { onBarajaClick(baraja) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarajaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_baraja, parent, false)
        return BarajaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarajaViewHolder, position: Int) {
        holder.bind(barajas[position])
    }

    override fun getItemCount() = barajas.size
}