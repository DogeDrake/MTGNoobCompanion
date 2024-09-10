package com.example.mtgcompanion

import JSON.Carta
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartasFragment : Fragment() {

    private lateinit var cartas: List<Carta>
    private lateinit var cartasAdapter: CartasAdapter

    companion object {
        private const val ARG_CARTAS = "arg_cartas"

        fun newInstance(cartas: List<Carta>): CartasFragment {
            val fragment = CartasFragment()
            val args = Bundle()
            args.putSerializable(ARG_CARTAS, ArrayList(cartas)) // Convertir a ArrayList para Serializable
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cartas, container, false)

        // Inicializar la propiedad cartas
        arguments?.let {
            cartas = it.getSerializable(ARG_CARTAS) as? List<Carta> ?: emptyList()
        } ?: run {
            // Manejar el caso en que no se pasaron argumentos
            return view
        }

        // Configurar el RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.cartasRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        cartasAdapter = CartasAdapter(cartas)
        recyclerView.adapter = cartasAdapter

        // Configurar el SearchView
        val searchView: SearchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { cartasAdapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { cartasAdapter.filter(it) }
                return true
            }
        })

        return view
    }
}
