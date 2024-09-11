package com.example.mtgcompanion

import JSON.TitulosDeBaraja
import Paso
import PasosAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PasosFragment : Fragment() {

    private lateinit var pasos: List<Paso>
    private lateinit var tituloDeBaraja: TitulosDeBaraja
    private var currentPasoIndex = -1

    companion object {
        private const val ARG_TITULO_DE_BARJA = "arg_titulo_de_baraja"

        fun newInstance(tituloDeBaraja: TitulosDeBaraja): PasosFragment {
            val fragment = PasosFragment()
            val args = Bundle()
            args.putSerializable(ARG_TITULO_DE_BARJA, tituloDeBaraja) // Pasar el objeto completo
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pasos, container, false)

        // Inicializar la propiedad pasos y tituloDeBaraja
        arguments?.let {
            tituloDeBaraja = it.getSerializable(ARG_TITULO_DE_BARJA) as? TitulosDeBaraja
                ?: TitulosDeBaraja(0, "", "","", emptyList(), emptyList())
            pasos = tituloDeBaraja.Pasos
        } ?: run {
            return view
        }

        // Restaurar estado guardado si existe
        savedInstanceState?.let {
            currentPasoIndex = it.getInt("currentPasoIndex", -1)
        }

        // Configurar el RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.pasosRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = PasosAdapter(pasos)
        recyclerView.adapter = adapter

        // Configurar los botones
        val botonSiguiente: Button = view.findViewById(R.id.botonSiguiente)
        val botonMiTurno: Button = view.findViewById(R.id.botonMiTurno)
        val botonVerCartas: Button = view.findViewById(R.id.botonVerCartas)

        botonSiguiente.isEnabled = currentPasoIndex >= 0
        botonMiTurno.isEnabled = currentPasoIndex < 0

        botonMiTurno.setOnClickListener {
            botonMiTurno.isEnabled = false
            botonSiguiente.isEnabled = true
            moveToNextPaso()
        }

        botonSiguiente.setOnClickListener {
            moveToNextPaso()
        }

        botonVerCartas.setOnClickListener {
            val cartas = tituloDeBaraja.Cartas
            val fragment = CartasFragment.newInstance(cartas)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }

        // Resaltar el paso actual si es necesario
        (recyclerView.adapter as? PasosAdapter)?.setPasoHighlighted(currentPasoIndex, true)
        recyclerView.scrollToPosition(currentPasoIndex)

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentPasoIndex", currentPasoIndex)
    }

    private fun moveToNextPaso() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.pasosRecyclerView)
        val adapter = recyclerView?.adapter as? PasosAdapter
        val botonSiguiente = view?.findViewById<Button>(R.id.botonSiguiente)
        val botonMiTurno = view?.findViewById<Button>(R.id.botonMiTurno)

        if (currentPasoIndex >= 0) {
            // Resetear el paso anterior
            adapter?.setPasoHighlighted(currentPasoIndex, false)
        }

        currentPasoIndex++
        if (currentPasoIndex < pasos.size) {
            // Destacar el paso actual
            adapter?.setPasoHighlighted(currentPasoIndex, true)
            recyclerView?.scrollToPosition(currentPasoIndex)
        } else {
            // No hay más pasos
            botonSiguiente?.isEnabled = false
            botonMiTurno?.isEnabled = true
            currentPasoIndex = -1 // Reiniciar el índice
        }
    }
}
